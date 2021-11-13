package com.github.leftpathlane.mapgen.schematic;

import com.github.leftpathlane.jnbt.types.NbtCompound;
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
		if (schematic.tileEntities != null) 
			for (NbtCompound nbt : schematic.tileEntities) {
				int ex = nbt.getNbt("x").asInt().getValue();
				int ey = nbt.getNbt("y").asInt().getValue();
				int ez = nbt.getNbt("z").asInt().getValue();
				nbt.addNbt("x", ex + x);
				nbt.addNbt("y", ey + y );
				nbt.addNbt("z", ez + z);
				world.addTileEntity(ex + x, ey + y, ez + z, nbt);
			}
	}

	public void addBlock(Block block) {
		world.addBlock(block);
	}

	public World getWorld() {
		return world;
	}
}
