package com.github.leftpathlane.mapgen.util;

import java.util.Arrays;

public class BlockByteArray {

	private static final int BLOCK_SIZE = 4096,
			MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	protected byte buf[];

	protected int pointer;

	public BlockByteArray() {
		this(1);
	}

	public BlockByteArray(int initialBlocks) {
		buf = new byte[BLOCK_SIZE * initialBlocks];
	}

	private void ensureCapacity(int minCapacity) {
		if (minCapacity - buf.length > 0)
			grow(minCapacity);
	}

	private void grow(int minCapacity) {
		// overflow-conscious code
		int oldCapacity = buf.length;
		int newBlocks = 1;
		if (minCapacity > BLOCK_SIZE)
			newBlocks = (minCapacity / BLOCK_SIZE) + 1;
		int newCapacity = oldCapacity + (newBlocks * BLOCK_SIZE);
		if (newCapacity > MAX_ARRAY_SIZE)
			throw new OutOfMemoryError();
		buf = Arrays.copyOf(buf, newCapacity);
	}

	public synchronized void write(int b) {
		ensureCapacity(pointer + 1);
		buf[pointer] = (byte) b;
		pointer += 1;
	}

	public synchronized void write(byte b[], int off, int len) {
		if ((off < 0) || (off > b.length) || (len < 0) ||
				((off + len) - b.length > 0)) {
			throw new IndexOutOfBoundsException();
		}
		ensureCapacity(pointer + len);
		System.arraycopy(b, off, buf, pointer, len);
		pointer += len;
	}

	public synchronized void write(byte b[]) {
		write(b, 0, b.length);
	}

	public synchronized int read() {
		byte temp = buf[pointer];
		pointer += 1;
		return temp;
	}

	public synchronized int read(byte[] b) {
		return read(b, 0, b.length);
	}

	public synchronized int read(byte[] b, int offset, int length) {
		int available = buf.length - pointer;
		if (length > available) length = available;
		if (pointer >= buf.length) return -1;
		if (length <= 0) return 0;
		System.arraycopy(buf, pointer, b, offset, length);
		pointer += length;
		return length;
	}

	public synchronized void resetPointer() {
		pointer = 0;
	}

	public synchronized void skip(int bytes) {
		skipTo(pointer + bytes);
	}

	public synchronized void skipTo(int point) {
		ensureCapacity(point);
		pointer = point;
	}

	public synchronized void skipToBlock(int block) {
		int blockBytes = block * BLOCK_SIZE;
		skipTo(blockBytes);
	}

	public synchronized int size() {
		return buf.length;
	}

	public synchronized byte[] toByteArray() {
		return buf;
	}
}
