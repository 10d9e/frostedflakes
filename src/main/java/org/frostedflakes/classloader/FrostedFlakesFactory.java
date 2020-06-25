package org.frostedflakes.classloader;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;

public class FrostedFlakesFactory {
	
	private static final FrostedFlakesFactory instance = new FrostedFlakesFactory();
	
	private static DynamicClassLoader dcl = new DynamicClassLoader();
	
	
	static Object frostedFlakes;
	
	static {
		Class<?> cls = dcl.load(FrostedFlakes.class.getName());
		
		try {
			frostedFlakes = cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static FrostedFlakesFactory getInstance(){
		return instance;
	}
	
	public static IFrostedFlakes getFrostedFlakes() {
		return (IFrostedFlakes) frostedFlakes; 
	}

}
