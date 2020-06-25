package org.frostedflakes.classloader;

import java.util.function.Consumer;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;

public class Context {
	
	/*
	public void init(Consumer<IFrostedFlakes<T>> func) {
		IFrostedFlakes<T> flakes = new FrostedFlakes<>();
		
		func.accept(flakes);
		
	}
	*/
	
	public void init(Consumer<Object> p) {
		IFrostedFlakes<?> flakes = new FrostedFlakes<>();
		p.accept(flakes);
	}

}
