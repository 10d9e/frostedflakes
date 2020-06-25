package org.frostedflakes.test.object;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class A {

	public B b;
	
	public static final Set<String> SPEC_DEFINED_EXTRACTORS;

	static {
		LinkedHashSet<String> specDefinedExtractors = new LinkedHashSet<>();
		specDefinedExtractors.add("first");
		specDefinedExtractors.add("second");
		specDefinedExtractors.add("third");
		SPEC_DEFINED_EXTRACTORS = Collections.unmodifiableSet(specDefinedExtractors);
	}

	public List<String> list;

	public A(List<String> slist) {
		this();
		for ( String s : slist ) {
			System.out.println(s);
		}
		this.list = slist;
		b = new B(10, 20);
	}
	
	private A() {}

}