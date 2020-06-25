package org.frostedflakes.processor;

import org.frostedflakes.serialization.Deserializer;
import org.frostedflakes.serialization.Serializer;
import org.frostedflakes.types.ReflectedType;

public interface TypeProcessor {

	ReflectedType<?> serialize(Object value, Serializer<?> serializer);

	boolean ignore(Class<?> value);

	Object deserialize(ReflectedType<?> type, Deserializer<?> deserializer);
}