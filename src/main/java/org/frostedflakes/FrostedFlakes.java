package org.frostedflakes;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

import org.frostedflakes.processor.TypeProcessor;
import org.frostedflakes.processor.TypeProcessorRegistry;
import org.frostedflakes.processor.impl.CglibProxyTypeProcessor;
import org.frostedflakes.serialization.Deserializer;
import org.frostedflakes.serialization.Serializer;
import org.frostedflakes.types.ReflectedType;

public final class FrostedFlakes <T> implements IFrostedFlakes<T> {
	
	public static boolean USE_SERIALIZABLE = false;
	
	private Serializer<T> serializer = new Serializer<>();

	private Deserializer<T> deserializer = new Deserializer<>();
	
	static {
		TypeProcessorRegistry.register(new CglibProxyTypeProcessor());
	}

	public static void registerTypeProcessor(TypeProcessor processor) {
		TypeProcessorRegistry.register(processor);
	}
	
	@Override
	public void save(T o, String filepath) throws IOException {
		write(get(filepath), toBytes(o));
	}
	
	@Override
	public T open(String filepath) throws IOException {
		return (T) fromBytes(readAllBytes(get(filepath)));
	}
	
	@Override
	public T deepCopy(T target) {
		return (T) fromBytes(toBytes(target));
	}
	
	@Override
	public Callable<byte[]> toBytesAsync(Object target) {
		return () -> {
		    return toBytes(target);
		};
	}
	
	@Override
	public Callable<T> fromBytesAsync(byte[] bytes) {
		return () -> {
		    return fromBytes(bytes);
		};
	}

	@Override
	public byte[] toBytes(Object target) {

		ObjectOutputStream out = null;
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			ReflectedType<T> r = serializer.serialize(target);
			System.out.println(r);
			out = new ObjectOutputStream(bos);
			out.writeObject(r);
			out.flush();
			return bos.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public T fromBytes(byte[] bytes) {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		try (ObjectInput in = new ObjectInputStream(bis)) {
			@SuppressWarnings("unchecked")
			ReflectedType<T> r = (ReflectedType<T>) in.readObject();
			System.out.println(r);
			T o = deserializer.deserialize(r);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
