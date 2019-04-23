package com.github.leftpathlane.mapgen.util;

public class Position {
	private final int x, z;

	public Position(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	@Override
	public String toString() {
		return "Position{x=" + x + "z=" + z + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position)
			return x == ((Position) obj).x && z == ((Position) obj).z;
		return false;
	}

	@Override
	public int hashCode() {
		return x * 31 + z;
	}
}
