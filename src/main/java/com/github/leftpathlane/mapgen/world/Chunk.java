package com.github.leftpathlane.mapgen.world;

import com.github.leftpathlane.jnbt.types.NbtCompound;
import com.github.leftpathlane.jnbt.types.NbtList;
import com.github.leftpathlane.jnbt.types.NbtTag;
import com.github.leftpathlane.mapgen.Block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Chunk implements Iterable<ChunkSection> {
	private final int x, z;
	private final int[] heightMap;
	public ChunkSection[] sections = new ChunkSection[16];
	private long inhabitedTime, lastUpdate;
	private boolean lightPopulated, terrainPopulated;
	private List<NbtCompound> entities, tileEntities;

	public Chunk(int x, int z) {
		this.x = x;
		this.z = z;
		this.heightMap = new int[256];
		this.entities = new ArrayList<>();
		this.tileEntities = new ArrayList<>();
	}

	public Chunk(NbtCompound chunk) {
		NbtCompound level = chunk.getNbt("Level").asCompound();
		this.x = level.getNbt("xPos").asInt().getValue();
		this.z = level.getNbt("zPos").asInt().getValue();
		this.heightMap = level.getNbt("HeightMap").asIntArray().getValue();
		this.inhabitedTime = level.getNbt("InhabitedTime").asLong().getValue();
		this.lastUpdate = level.getNbt("LastUpdate").asLong().getValue();
		this.lightPopulated = level.getNbt("LightPopulated").asByte().asBoolean();
		this.terrainPopulated = level.getNbt("TerrainPopulated").asByte().asBoolean();
		this.entities = level.getNbt("Entities").asList().getValue();
		this.tileEntities = level.getNbt("TileEntities").asList().getValue();
		NbtList<NbtCompound> sectionList = level.getNbt("Sections").asList();
		for (NbtCompound section : sectionList.getValue()) {
			byte y = section.getNbt("Y").asByte().getValue();
			sections[y] = new ChunkSection(section, x, z);
		}
	}

	public int getX() {
		return this.x;
	}

	public int getZ() {
		return this.z;
	}

	public void addBlock(Block block) {
		ChunkSection section = getChunkSection(block.getX(), block.getY(), block.getZ());
		section.addBlock(block);
		addToHeightMap(block.getX(), block.getY(), block.getZ());
	}

	public void addBlock(byte material, byte data, int x, int y, int z) {
		ChunkSection section = getChunkSection(x, y, z);
		section.addBlock(material, data, x, y, z);
		addToHeightMap(x, y, z);
	}

	private void addToHeightMap(int x, int y, int z) {
		int heightMapIndex = (x & 0xF) + (z & 0xF) * 16;
		if (y > heightMap[heightMapIndex]) heightMap[heightMapIndex] = y;
	}

	public void addTileEntity(NbtCompound entity) {
		this.tileEntities.add(entity);
	}

	public Block getBlock(int x, int y, int z) {
		ChunkSection section = getLoadedChunkSection(x, y, z);
		if (section == null) return null;
		return section.getBlock(x, y, z);
	}

	public ChunkSection getLoadedChunkSection(int x, int y, int z) {
		int ypos = y >> 4;
		return sections[ypos];
	}

	public ChunkSection getChunkSection(int x, int y, int z) {
		int ypos = y >> 4;
		ChunkSection section = sections[ypos];
		if (section == null) {
			section = new ChunkSection((byte) ypos, this.x, this.z);
			sections[ypos] = section;
		}
		return section;
	}

	public List<NbtCompound> getEntities() {
		return this.entities;
	}

	public List<NbtCompound> getTileEntities() {
		return this.tileEntities;
	}

	public NbtCompound toNbt() {
		NbtCompound nbt = new NbtCompound("Chunk [" + (x & 31) + ", " + (z & 31) + "]");
		NbtCompound level = new NbtCompound("Level");
		NbtList<NbtCompound> list = new NbtList<NbtCompound>("Sections", NbtTag.NBT_TAG_COMPOUND);
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
		level.addNbt(new NbtList<>("Entities", NbtTag.NBT_TAG_COMPOUND, entities));
		level.addNbt(new NbtList<>("TileEntities", NbtTag.NBT_TAG_COMPOUND, tileEntities));
		nbt.addNbt(level);
		return nbt;
	}

	@Override
	public Iterator<ChunkSection> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<ChunkSection> {
		int cursor;
		ChunkSection next;

		public Itr() {
			getNext();
		}

		private void getNext() {
			for (; cursor < sections.length; cursor++) {
				next = sections[cursor];
				cursor++;
				if (next != null) return;
			}
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public ChunkSection next() {
			ChunkSection ret = next;
			getNext();
			return ret;
		}
	}
}
