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

	public void write(byte b[]) {
		write(b, 0, b.length);
	}

	public synchronized void resetPointer() {
		pointer = 0;
	}

	public synchronized void skip(int bytes) {
		ensureCapacity(pointer + bytes);
		pointer += bytes;
	}

	public synchronized void skipTo(int point) {
		ensureCapacity(point);
		pointer = point;
	}

	public synchronized int size() {
		return buf.length;
	}

	public synchronized byte[] toByteArray() {
		return buf;
	}
}
