package com.github.leftpathlane.mapgen.world;

import com.github.leftpathlane.jnbt.types.NbtCompound;
import com.github.leftpathlane.jnbt.types.NbtType;
import com.github.leftpathlane.mapgen.Block;

import java.util.HashMap;

public class ChunkSection {
	private final byte y;
	private final byte[] blockLight = new byte[2048];
	private final byte[] blocks = new byte[4096];
	private final byte[] data = new byte[2048];
	private final byte[] skylight = new byte[2048];

	public ChunkSection(byte y) {
		this.y = y;
	}

	public void addBlock(Block block) {
		int index = getIndex(block);
		blocks[index] = block.getMaterial();
		if (block.getData() != 0) setHalfByte(data, index, block.getData());
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
		return (block.getY() & 0x0F) * 16 * 16 + (block.getZ() & 0x0F) * 16 + (block.getX() & 0x0F);
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
}