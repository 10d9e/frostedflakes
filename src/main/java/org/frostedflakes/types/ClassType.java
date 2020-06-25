package org.frostedflakes.types;

import java.io.Serializable;

public class ClassType<T> extends ReflectedType<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public ClassType(Class<T> type, String name) {
		super(type);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ClassType [name=" + name + "]";
	}

}