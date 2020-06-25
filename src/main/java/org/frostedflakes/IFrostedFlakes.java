package org.frostedflakes;

import java.io.IOException;
import java.util.concurrent.Callable;

public interface IFrostedFlakes<T> {

	void save(T o, String filepath) throws IOException;

	T open(String filepath) throws IOException;

	T deepCopy(T target);

	Callable<byte[]> toBytesAsync(Object target);

	Callable<T> fromBytesAsync(byte[] bytes);

	byte[] toBytes(Object target);

	T fromBytes(byte[] bytes);

}