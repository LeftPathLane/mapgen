package com.github.leftpathlane.mapgen.world;

import com.github.leftpathlane.jnbt.NbtReader;
import com.github.leftpathlane.jnbt.NbtWriter;
import com.github.leftpathlane.jnbt.types.NbtCompound;
import com.github.leftpathlane.mapgen.Block;
import com.github.leftpathlane.mapgen.LevelData;
import com.github.leftpathlane.mapgen.util.Position;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPOutputStream;

public class World implements Iterable<Region> {
	public final LevelData levelData;
	public HashMap<Position, Region> regions = new HashMap<>();

	public World(LevelData levelData) {
		this.levelData = levelData;
	}

	public World() {
		this.levelData = new LevelData();
	}

	public World(File root) throws IOException {
		if (!root.exists()) throw new IllegalArgumentException();
		File levelDataFile = new File(root, "level.dat");
		if (!levelDataFile.exists()) throw new IllegalArgumentException();

		NbtCompound levelDataNbt = new NbtReader(levelDataFile).readAll();
		this.levelData = new LevelData(levelDataNbt);


		File regionFolder = new File(root, "region");
		if (!regionFolder.exists() || regionFolder.listFiles() == null) return;
		for (File regionFile : regionFolder.listFiles()) {
			Region region = new Region(regionFile);
			if (region == null) continue;
			regions.put(new Position(region.getRegionX(), region.getRegionZ()), region);
		}

	}

	public void addBlock(Block block) {
		int regionX = block.getX() >> 9;
		int regionZ = block.getZ() >> 9;
		Position pos = new Position(regionX, regionZ);
		Region region = regions.get(pos);
		if (region == null) {
			region = new Region(regionX, regionZ);
			regions.put(pos, region);
		}
		region.addBlock(block);
	}

	public Block getBlock(int x, int y, int z) {
		int regionX = x >> 9;
		int regionZ = z >> 9;
		Position pos = new Position(regionX, regionZ);
		Region region = regions.get(pos);
		if (region == null) return null;
		return region.getBlock(x, y, z);
	}

	public void saveWorld(File file) {
		if (!file.exists()) file.mkdir();
		File levelData = new File(file, "level.dat");
		try {
			if (!levelData.exists()) levelData.createNewFile();
			try (FileOutputStream outputStream = new FileOutputStream(levelData);
				 GZIPOutputStream gzipout = new GZIPOutputStream(outputStream)) {
				gzipout.write(new NbtWriter().write(this.levelData.toNbt()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		File regionFolder = new File(file, "region");
		if (!regionFolder.exists()) regionFolder.mkdir();
		for (Region region : regions.values()) {
			try {
				File regionFile = new File(regionFolder, "r." + region.getRegionX() + "." + region.getRegionZ() + ".mca");
				regionFile.createNewFile();
				try (FileOutputStream outputStream = new FileOutputStream(regionFile)) {
					outputStream.write(region.toBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Iterator<Region> iterator() {
		return regions.values().iterator();
	}
}
