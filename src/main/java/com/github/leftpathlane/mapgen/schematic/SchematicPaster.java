package com.github.leftpathlane.mapgen.schematic;

import com.github.leftpathlane.mapgen.Block;
import com.github.leftpathlane.mapgen.LevelData;
import com.github.leftpathlane.mapgen.world.World;

public class SchematicPaster {

	protected final World world;

	public SchematicPaster(LevelData levelData) {
		world = new World(levelData);
	}

	public void pasteSchematic(Schematic schematic, int x, int y, int z) {
		for (Block block : schematic.blocks) {
			if (block == null) continue;
			addBlock(block.move(x, y, z));
		}
	}

	public void addBlock(Block block) {
		world.addBlock(block);
	}

	public World getWorld() {
		return world;
	}
}
