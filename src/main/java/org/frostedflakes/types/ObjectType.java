package org.frostedflakes.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectType<T> extends ReflectedType<T> {

	private static final long serialVersionUID = 1L;

	private int identifier;

	private List<ReflectedType<?>> fields = new ArrayList<>();

	public ObjectType(Class<T> type, int identifier) {
		super(type);
		this.identifier = identifier;
	}

	public void addField(ReflectedType<?> type) {
		fields.add(type);
	}

	public List<ReflectedType<?>> getFields() {
		return fields;
	}

	public void setFields(List<ReflectedType<?>> fields) {
		this.fields = fields;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	/*
	 * @Override public String toString() { return new
	 * ReflectionToStringBuilder(this, new
	 * MultilineRecursiveToStringStyle()).toString(); }
	 */

}