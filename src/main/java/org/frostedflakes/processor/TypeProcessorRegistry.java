package org.frostedflakes.processor;

import java.util.HashSet;
import java.util.Set;

public final class TypeProcessorRegistry {

	private static Set<TypeProcessor> processors = new HashSet<>();

	public static void register(TypeProcessor processor) {
		processors.add(processor);
	}

	public static Set<TypeProcessor> getProcessors() {
		return processors;
	}

}
