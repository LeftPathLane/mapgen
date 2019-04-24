package com.github.leftpathlane.mapgen;

import com.github.leftpathlane.jnbt.NbtReader;
import com.github.leftpathlane.jnbt.types.NbtCompound;
import com.github.leftpathlane.mapgen.schematic.Schematic;
import com.github.leftpathlane.mapgen.schematic.SchematicPaster;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SchematicTest {
	@Test
	public void SchematicTest() throws IOException {
		File file = new File(this.getClass().getClassLoader().getResource("test.schematic").getFile());
		NbtReader reader = new NbtReader(file);
		NbtCompound nbt = reader.readAll();

		Schematic schematic = new Schematic(nbt, false);

		SchematicPaster paster = new SchematicPaster(new LevelData());
		paster.pasteSchematic(schematic, 10, 10, 10);

		Block block = paster.getWorld().getBlock(10, 10, 10);
		assertNotNull(block);
		assertEquals(57, block.getMaterial());

		block = paster.getWorld().getBlock(11, 10, 11);
		assertNotNull(block);
		assertEquals(35, block.getMaterial());
		assertEquals(2, block.getData());

		block = paster.getWorld().getBlock(12, 10, 11);
		assertNotNull(block);
		assertEquals(35, block.getMaterial());
		assertEquals(1, block.getData());

		block = paster.getWorld().getBlock(13, 10, 11);
		assertNotNull(block);
		assertEquals(35, block.getMaterial());
		assertEquals(0, block.getData());

		block = paster.getWorld().getBlock(11, 10, 12);
		assertNotNull(block);
		assertEquals(35, block.getMaterial());
		assertEquals(5, block.getData());

		block = paster.getWorld().getBlock(12, 10, 12);
		assertNotNull(block);
		assertEquals(35, block.getMaterial());
		assertEquals(4, block.getData());

		block = paster.getWorld().getBlock(13, 10, 12);
		assertNotNull(block);
		assertEquals(35, block.getMaterial());
		assertEquals(3, block.getData());

		block = paster.getWorld().getBlock(11, 10, 13);
		assertNotNull(block);
		assertEquals(35, block.getMaterial());
		assertEquals(8, block.getData());

		block = paster.getWorld().getBlock(12, 10, 13);
		assertNotNull(block);
		assertEquals(35, block.getMaterial());
		assertEquals(7, block.getData());


		block = paster.getWorld().getBlock(13, 10, 13);
		assertNotNull(block);
		assertEquals(35, block.getMaterial());
		assertEquals(6, block.getData());
	}
}