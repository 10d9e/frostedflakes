package org.frostedflakes.types;

import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ReferenceType<T> extends ReflectedType<T> {

	private static final long serialVersionUID = 1L;

	private int referenceId;

	public ReferenceType(Class<T> type, int referenceId) {
		super(type);
		this.referenceId = referenceId;
	}

	public int getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}

	
}