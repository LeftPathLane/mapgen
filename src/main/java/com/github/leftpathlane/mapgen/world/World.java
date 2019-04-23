package com.github.leftpathlane.mapgen.world;

import com.github.leftpathlane.jnbt.NbtWriter;
import com.github.leftpathlane.mapgen.Block;
import com.github.leftpathlane.mapgen.LevelData;
import com.github.leftpathlane.mapgen.util.Position;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;

public class World {
	public final LevelData levelData;
	public HashMap<Position, Region> regions = new HashMap<>();

	public World(LevelData levelData) {
		this.levelData = levelData;
	}

	public World() {
		this.levelData = new LevelData();
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
}
