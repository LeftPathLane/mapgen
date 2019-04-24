package com.github.leftpathlane.mapgen.world;

import com.github.leftpathlane.mapgen.Block;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorldBlockTest {
	private World world;

	@Before
	public void BeforeBlockTest() {
		world = new World();
	}

	@Test
	public void BlockGetMaterialTest() {
		world.addBlock(new Block((byte) 1, (byte) 2, 1, 1, 1));
		Block block = world.getBlock(1, 1, 1);
		assertNotNull(block);
		assertEquals(1, block.getMaterial());
		assertEquals(2, block.getData());
	}

	@Test
	public void BlockNullTest() {
		Block block = world.getBlock(1, 1, 1);
		assertNull(block);
	}
}