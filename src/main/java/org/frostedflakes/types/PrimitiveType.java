package org.frostedflakes.types;

public class PrimitiveType<T> extends ReflectedType<T> {

	private static final long serialVersionUID = 1L;

	private byte[] bytes;

	public PrimitiveType(byte[] bytes, Class<T> type) {
		super(type);
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	/*
	 * @Override public String toString() { return
	 * String.format("PrimitiveType: %s [bytes=%s]", getType().getCanonicalName(),
	 * Arrays.toString(bytes)); }
	 */

}