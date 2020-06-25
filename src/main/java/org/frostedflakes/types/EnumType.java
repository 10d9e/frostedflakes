package org.frostedflakes.types;

public class EnumType<T> extends ReflectedType<T> {

	private static final long serialVersionUID = 1L;

	private int ordinal;

	private String name;

	public EnumType(String name, int ordinal, Class<T> type) {
		super(type);
		this.name = name;
		this.ordinal = ordinal;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "EnumType [ordinal=" + ordinal + ", name=" + name + "]";
	}

}