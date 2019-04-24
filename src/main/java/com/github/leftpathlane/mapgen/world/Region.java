package com.github.leftpathlane.mapgen.world;

import com.github.leftpathlane.jnbt.NbtWriter;
import com.github.leftpathlane.jnbt.types.NbtCompound;
import com.github.leftpathlane.mapgen.Block;
import com.github.leftpathlane.mapgen.util.BlockByteArray;
import com.github.leftpathlane.mapgen.util.Position;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.Deflater;

public class Region {
	private static final int REGION_HEADER = 8192,
			CHUNK_SIZE = 4096;
	private final int regionX, regionZ;
	HashMap<Position, Chunk> chunks = new HashMap<>();

	public Region(int regionX, int regionZ) {
		this.regionX = regionX;
		this.regionZ = regionZ;
	}

	public void addBlock(Block block) {
		int chunkX = block.getX() >> 4;
		int chunkZ = block.getZ() >> 4;

		Position pos = new Position(chunkX, chunkZ);
		Chunk chunk = chunks.get(pos);
		if (chunk == null) {
			chunk = new Chunk(chunkX, chunkZ);
			chunks.put(pos, chunk);
		}
		chunk.addBlock(block);

	}

	public Block getBlock(int x, int y, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		Position pos = new Position(chunkX, chunkZ);
		Chunk chunk = chunks.get(pos);
		if (chunk == null) return null;
		return chunk.getBlock(x, y, z);
	}

	public byte[] toBytes() {
		BlockByteArray data = new BlockByteArray(3);
		int offset = 2;
		for (Chunk chunk : chunks.values()) {
			try {

				NbtCompound nbt = chunk.toNbt();
				byte[] chunkData = new NbtWriter().write(nbt);

				Deflater deflater = new Deflater();
				deflater.setInput(chunkData);
				deflater.finish();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] biff = new byte[1024];
				while (!deflater.finished()) {
					int count = deflater.deflate(biff);
					out.write(biff, 0, count);
				}
				byte[] compressedData = out.toByteArray();

				int sectorCount = 1;
				if (compressedData.length + 5 > CHUNK_SIZE) {
					sectorCount = (compressedData.length + 5) % CHUNK_SIZE;
				}

				int locationData = 4 * ((chunk.getX() & 31) + (chunk.getZ() & 31) * 32);
				data.skipTo(locationData);
				data.write((offset >> 16) & 0xFF);
				data.write((offset >> 8) & 0xFF);
				data.write(offset & 0xFF);
				data.write(sectorCount);

				data.skipTo((offset * CHUNK_SIZE));
				int compressedLength = compressedData.length + 1;
				data.write((compressedLength >> 24) & 0xFF);
				data.write((compressedLength >> 16) & 0xFF);
				data.write((compressedLength >> 8) & 0xFF);
				data.write(compressedLength & 0xFF);
				data.write(2);
				data.write(compressedData);

				offset += sectorCount;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return data.toByteArray();
	}

	public int getRegionX() {
		return regionX;
	}

	public int getRegionZ() {
		return regionZ;
	}
}
