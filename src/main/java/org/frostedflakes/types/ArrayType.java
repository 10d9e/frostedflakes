package org.frostedflakes.types;

import java.util.ArrayList;
import java.util.List;

public class ArrayType<T> extends ReflectedType<T> {

	private static final long serialVersionUID = 1L;

	private List<ReflectedType<?>> items = new ArrayList<>();

	public ArrayType(Class<T> type) {
		super(type);
	}

	public boolean add(ReflectedType<?> e) {
		return items.add(e);
	}

	public List<ReflectedType<?>> getItems() {
		return items;
	}

	/*
	 * @Override public String toString() { return
	 * String.format("ArrayType: [%s] [items=%s]", getType().getCanonicalName(),
	 * items); }
	 */

}