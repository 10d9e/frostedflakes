package org.frostedflakes.types;

import java.io.Serializable;

import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ReflectedType<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Class<T> type;

	public ReflectedType(Class<T> type) {
		super();
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}
	
	

}