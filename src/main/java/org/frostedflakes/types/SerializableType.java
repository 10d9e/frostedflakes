package org.frostedflakes.types;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class SerializableType<T> extends ReflectedType<T> {

	private static final long serialVersionUID = 1L;

	private byte[] bytes;

	public SerializableType(T instance) {
		super((Class<T>) instance.getClass());
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(instance);
			out.flush();
			bytes = bos.toByteArray();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	/*
	 * @Override public String toString() { return new
	 * ReflectionToStringBuilder(this, new
	 * MultilineRecursiveToStringStyle()).toString(); }
	 */

}