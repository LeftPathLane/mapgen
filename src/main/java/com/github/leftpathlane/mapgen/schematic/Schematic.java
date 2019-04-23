package com.github.leftpathlane.mapgen.schematic;

import com.github.leftpathlane.jnbt.types.NbtByteArray;
import com.github.leftpathlane.jnbt.types.NbtCompound;
import com.github.leftpathlane.jnbt.types.NbtShort;
import com.github.leftpathlane.mapgen.Block;

public class Schematic {
	public Block[] blocks;
	public short width, length, height;

	public Schematic(NbtCompound nbt) {
		this(nbt, false);
	}

	public Schematic(NbtCompound nbt, boolean storeAir) {
		byte[] blocks = ((NbtByteArray) nbt.getValue().get("Blocks")).getValue();
		byte[] datas = ((NbtByteArray) nbt.getValue().get("Data")).getValue();
		width = ((NbtShort) nbt.getValue().get("Width")).getValue();
		length = ((NbtShort) nbt.getValue().get("Length")).getValue();
		height = ((NbtShort) nbt.getValue().get("Height")).getValue();
		this.blocks = new Block[blocks.length];
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < length; z++) {
				for (int y = 0; y < height; y++) {
					int index = (y * length + z) * width + x;
					if (blocks[index] == 0) continue;
					this.blocks[index] = new Block(blocks[index], datas[index], x, y, z);
				}
			}
		}
	}
}
