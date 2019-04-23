package com.github.leftpathlane.mapgen;

public class Block {
	private final byte material, data;
	private final int x, y, z;

	public Block(byte material, byte data, int x, int y, int z) {
		this.material = material;
		this.data = data;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public byte getMaterial() {
		return material;
	}

	public byte getData() {
		return data;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public Block move(int x, int y, int z) {
		return new Block(this.material, this.data, this.x + x, this.y + y, this.z + z);
	}
}
