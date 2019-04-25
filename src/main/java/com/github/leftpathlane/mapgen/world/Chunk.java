package com.github.leftpathlane.mapgen.world;

import com.github.leftpathlane.jnbt.types.NbtCompound;
import com.github.leftpathlane.jnbt.types.NbtList;
import com.github.leftpathlane.jnbt.types.NbtType;
import com.github.leftpathlane.mapgen.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class Chunk {
	private final int x, z;
	private final int[] heightMap;
	public ChunkSection[] sections = new ChunkSection[16];
	private long inhabitedTime, lastUpdate;
	private boolean lightPopulated, terrainPopulated;

	public Chunk(int x, int z) {
		this.x = x;
		this.z = z;
		this.heightMap = new int[256];
	}

	public Chunk(NbtCompound chunk) {
		NbtCompound level = chunk.getValue().get("Level").asCompound();
		this.x = level.getValue().get("xPos").asInt().getValue();
		this.z = level.getValue().get("zPos").asInt().getValue();
		this.heightMap = level.getValue().get("HeightMap").asIntArray().getValue();
		this.inhabitedTime = level.getValue().get("InhabitedTime").asLong().getValue();
		this.lastUpdate = level.getValue().get("LastUpdate").asLong().getValue();
		this.lightPopulated = level.getValue().get("LightPopulated").asByte().asBoolean();
		this.terrainPopulated = level.getValue().get("TerrainPopulated").asByte().asBoolean();
		NbtList sectionList = level.getValue().get("Sections").asList();
		for (NbtType t : sectionList.getValue()) {
			NbtCompound section = t.asCompound();
			byte y = section.getValue().get("Y").asByte().getValue();
			sections[y] = new ChunkSection(section);
		}
	}

	public int getX() {
		return this.x;
	}

	public int getZ() {
		return this.z;
	}

	public void addBlock(Block block) {
		int ypos = block.getY() >> 4;
		ChunkSection section = sections[ypos];
		if (section == null) {
			section = new ChunkSection((byte) ypos);
			sections[ypos] = section;
		}
		section.addBlock(block);
		int heightMapIndex = (block.getX() >> 4) + (block.getZ() >> 4) * 16;
		if (block.getY() > heightMap[heightMapIndex]) heightMap[heightMapIndex] = block.getY();
	}

	public Block getBlock(int x, int y, int z) {
		int ypos = y >> 4;
		ChunkSection section = sections[ypos];
		if (section == null) return null;
		return section.getBlock(x, y, z);
	}

	public NbtCompound toNbt() {
		NbtCompound nbt = new NbtCompound("Chunk [" + (x & 31) + ", " + (z & 31) + "]", new HashMap<String, NbtType>());
		NbtCompound level = new NbtCompound("Level", new HashMap<String, NbtType>());
		NbtList list = new NbtList("Sections", NbtType.NBT_TAG_COMPOUND, new ArrayList<NbtType>());
		for (ChunkSection section : sections) {
			if (section != null) list.addNbt(section.toNbt());
		}
		level.addNbt(list);
		level.addNbt("xPos", x);
		level.addNbt("zPos", z);
		level.addNbt("InhabitedTime", inhabitedTime);
		level.addNbt("LastUpdate", lastUpdate);
		level.addNbt("LightPopulated", lightPopulated ? (byte) 1 : (byte) 0);
		level.addNbt("TerrainPopulated", terrainPopulated ? (byte) 1 : (byte) 0);
		level.addNbt("HeightMap", heightMap);
		level.addNbt("Entities", NbtType.NBT_TAG_COMPOUND, new ArrayList<NbtType>());
		level.addNbt("TileEntities", NbtType.NBT_TAG_COMPOUND, new ArrayList<NbtType>());
		nbt.addNbt(level);
		return nbt;
	}
}