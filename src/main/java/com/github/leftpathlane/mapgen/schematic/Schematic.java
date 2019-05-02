package com.github.leftpathlane.mapgen.schematic;

import com.github.leftpathlane.jnbt.types.NbtCompound;
import com.github.leftpathlane.mapgen.Block;

public class Schematic {
	public Block[] blocks;
	public short width, length, height;

	public Schematic(NbtCompound nbt) {
		this(nbt, false);
	}

	public Schematic(NbtCompound nbt, boolean storeAir) {
		byte[] blocks = nbt.getNbt("Blocks").asByteArray().getValue();
		byte[] datas = nbt.getNbt("Data").asByteArray().getValue();
		width = nbt.getNbt("Width").asShort().getValue();
		length = nbt.getNbt("Length").asShort().getValue();
		height = nbt.getNbt("Height").asShort().getValue();
		this.blocks = new Block[blocks.length];
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < length; z++) {
				for (int y = 0; y < height; y++) {
					int index = (y * length + z) * width + x;
					if (!storeAir && blocks[index] == 0) continue;
					this.blocks[index] = new Block(blocks[index], datas[index], x, y, z);
				}
			}
		}
	}
}
