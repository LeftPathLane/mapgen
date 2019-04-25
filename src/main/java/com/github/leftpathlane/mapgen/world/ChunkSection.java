package com.github.leftpathlane.mapgen.world;

import com.github.leftpathlane.jnbt.types.NbtCompound;
import com.github.leftpathlane.jnbt.types.NbtType;
import com.github.leftpathlane.mapgen.Block;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

public class ChunkSection implements Iterable<Block> {
	private final byte y;
	private final byte[] blockLight;
	private final byte[] blocks;
	private final byte[] data;
	private final byte[] skylight;

	public ChunkSection(byte y) {
		this.y = y;
		this.blockLight = new byte[2048];
		this.blocks = new byte[4096];
		this.data = new byte[2048];
		this.skylight = new byte[2048];
	}

	public ChunkSection(NbtCompound section) {
		this.y = section.getValue().get("Y").asByte().getValue();
		this.blockLight = section.getValue().get("BlockLight").asByteArray().getValue();
		this.blocks = section.getValue().get("Blocks").asByteArray().getValue();
		this.data = section.getValue().get("Data").asByteArray().getValue();
		this.skylight = section.getValue().get("SkyLight").asByteArray().getValue();
	}

	public byte getY() {
		return y;
	}

	public void addBlock(Block block) {
		int index = getIndex(block);
		blocks[index] = block.getMaterial();
		if (block.getData() != 0) setHalfByte(data, index, block.getData());
	}

	public Block getBlock(int x, int y, int z) {
		int index = getIndex(x, y, z);
		byte material = blocks[index];
		if (material == 0) return null;
		byte blockData = getHalfByte(data, index);
		return new Block(material, blockData, x, y, z);
	}

	private void setHalfByte(byte[] arr, int index, byte value) {
		byte old = arr[index / 2];
		if (index % 2 == 0) {
			arr[index / 2] = (byte) ((old & 0xF0) | (value & 0x0F));
		} else {
			arr[index / 2] = (byte) (((value & 0x0F) << 4) | (old & 0x0F));
		}
	}

	private byte getHalfByte(byte[] arr, int index) {
		return (byte) (index % 2 == 0 ? arr[index / 2] & 0x0F : (arr[index / 2] >> 4) & 0x0F);
	}

	private int getIndex(Block block) {
		return getIndex(block.getX(), block.getY(), block.getZ());
	}

	private int getIndex(int x, int y, int z) {
		return (y & 0x0F) * 16 * 16 + (z & 0x0F) * 16 + (x & 0x0F);
	}

	public NbtCompound toNbt() {
		NbtCompound nbt = new NbtCompound("", new HashMap<String, NbtType>());
		nbt.addNbt("Y", y);
		nbt.addNbt("BlockLight", blockLight);
		nbt.addNbt("Blocks", blocks);
		nbt.addNbt("Data", data);
		nbt.addNbt("SkyLight", skylight);
		return nbt;
	}

	@Override
	public Iterator<Block> iterator() {
		return new BlockIterator();
	}

	private class BlockIterator implements Iterator<Block> {
		int index = 0;
		Block cur;

		public BlockIterator() {
			cur = nextBlock();
		}

		@Override
		public boolean hasNext() {
			return cur != null;
		}

		@Override
		public Block next() {
			Block ret = cur;

			cur = nextBlock();

			return ret;
		}

		private Block nextBlock() {
			byte mat;
			while (index < 4096) {
				mat = blocks[index];
				if (mat != 0) {
					byte blockData = getHalfByte(data, index);
					int yz = index/16;
					int x = index & 0x0F;
					int z = yz & 0x0F;
					int y = (yz/16) & 0x0F;
					index++;
					return new Block(mat, blockData, x, y, z);
				}
				index++;
			}
			return null;
		}
	}
}