package com.github.leftpathlane.mapgen.world;

import com.github.leftpathlane.jnbt.NbtReader;
import com.github.leftpathlane.jnbt.NbtWriter;
import com.github.leftpathlane.jnbt.types.NbtCompound;
import com.github.leftpathlane.mapgen.Block;
import com.github.leftpathlane.mapgen.util.BlockByteArray;
import com.github.leftpathlane.mapgen.util.Position;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.InflaterInputStream;

public class Region implements Iterable<Chunk> {
	private static final int REGION_HEADER = 8192,
			CHUNK_SIZE = 4096;
	private static final Pattern pattern = Pattern.compile("r\\.(-?\\d)\\.(-?\\d)\\.mca");

	private final int regionX, regionZ;
	HashMap<Position, Chunk> chunks = new HashMap<>();

	public Region(int regionX, int regionZ) {
		this.regionX = regionX;
		this.regionZ = regionZ;
	}

	public Region(File file) {
		Matcher matcher = pattern.matcher(file.getName());
		if (!matcher.find()) throw new IllegalArgumentException();
		this.regionX = Integer.parseInt(matcher.group(1));
		this.regionZ = Integer.parseInt(matcher.group(2));
		BlockByteArray data = new BlockByteArray(3);
		try (FileInputStream fileIn = new FileInputStream(file)) {
			int read;
			byte[] buf = new byte[1024];
			while ((read = fileIn.read(buf)) > 0) {
				data.write(buf, 0, read);
			}
			for (int i = 0; i < 1024; i++) {
				data.skipTo(i * 4);
				byte[] chunkBlockLocation = new byte[4];
				data.read(chunkBlockLocation);
				int chunkOffset = ((chunkBlockLocation[0] & 0xFF << 16)
						+ ((chunkBlockLocation[1] & 0xFF) << 8)
						+ (chunkBlockLocation[2] & 0xFF));
				byte chunkLength = chunkBlockLocation[3];
				if (chunkOffset == 0 || chunkLength == 0) continue;
				data.skipToBlock(chunkOffset);
				byte[] chunkData = new byte[5];
				data.read(chunkData);
				int dataLength = (((chunkData[0] & 0xFF) << 24) + ((chunkData[1] & 0xFF) << 16) + ((chunkData[2] & 0xFF) << 8) + (chunkData[3] & 0xFF)) - 1;
				byte compressionType = chunkData[4];
				byte[] compressedData = new byte[dataLength];
				data.read(compressedData);
				try (InflaterInputStream inflaterInput = new InflaterInputStream(new ByteArrayInputStream(compressedData))) {
					ByteArrayOutputStream bo = new ByteArrayOutputStream();
					int r;
					byte b[] = new byte[1024];
					while ((r = inflaterInput.read(b)) > 0) {
						bo.write(b, 0, r);
					}
					NbtReader reader = new NbtReader(bo.toByteArray());
					NbtCompound compound = reader.readAll();
					addChunk(compound);
				} catch (EOFException e) {
					int x = i & 31;
					int z = i / 31;
					try (InflaterInputStream inflaterInput = new InflaterInputStream(new ByteArrayInputStream(compressedData))) {
						ByteArrayOutputStream bo = new ByteArrayOutputStream();
						int r;
						byte b[] = new byte[1024];
						while ((r = inflaterInput.read(b)) > 0) {
							bo.write(b, 0, r);
						}
						File out = new File(x + "" + z);
						FileOutputStream outputStream = new FileOutputStream(out);
						outputStream.write(bo.toByteArray());
					}


					System.out.println(file.getName() + " " + x + " " + z);
					e.printStackTrace();
				} catch (NbtReader.NbtTagException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addBlock(Block block) {
		Chunk chunk = getChunk(block.getX(), block.getY(), block.getZ());
		chunk.addBlock(block);

	}

	public void addBlock(byte material, byte data, int x, int y, int z) {
		Chunk chunk = getChunk(x, y, z);
		chunk.addBlock(material, data, x, y, z);
	}

	public Block getBlock(int x, int y, int z) {
		Chunk chunk = getLoadedChunk(x, y, z);
		if (chunk == null) return null;
		return chunk.getBlock(x, y, z);
	}

	public Chunk getLoadedChunk(int x, int y, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		Position pos = new Position(chunkX, chunkZ);
		return chunks.get(pos);
	}

	public Chunk getChunk(int x, int y, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;

		Position pos = new Position(chunkX, chunkZ);
		Chunk chunk = chunks.get(pos);
		if (chunk == null) {
			chunk = new Chunk(chunkX, chunkZ);
			chunks.put(pos, chunk);
		}
		return chunk;
	}

	public ChunkSection getLoadedChunkSection(int x, int y, int z) {
		Chunk chunk = getLoadedChunk(x, y, z);
		if (chunk == null) return null;
		return chunk.getLoadedChunkSection(x, y, z);
	}

	public ChunkSection getChunkSection(int x, int y, int z) {
		Chunk chunk = getChunk(x, y, z);
		if (chunk == null) return null;
		return chunk.getChunkSection(x, y, z);
	}

	public int getRegionX() {
		return regionX;
	}

	public int getRegionZ() {
		return regionZ;
	}

	public byte[] toBytes() {
		BlockByteArray data = new BlockByteArray(3);
		int offset = 2;
		for (Chunk chunk : chunks.values()) {
			try {
				NbtCompound nbt = chunk.toNbt();
				ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
				new NbtWriter(nbt, byteOut);
				byte[] chunkData = byteOut.toByteArray();

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
					sectorCount = (compressedData.length + 5) / CHUNK_SIZE;
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

	private void addChunk(NbtCompound chunk) {
		NbtCompound level = chunk.getNbt("Level").asCompound();
		int xPos = level.getNbt("xPos").asInt().getValue();
		int zPos = level.getNbt("zPos").asInt().getValue();
		Position position = new Position(xPos, zPos);
		chunks.put(position, new Chunk(chunk));
	}

	@Override
	public Iterator<Chunk> iterator() {
		return chunks.values().iterator();
	}
}
