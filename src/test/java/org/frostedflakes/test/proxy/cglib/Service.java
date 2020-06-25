package org.frostedflakes.test.proxy.cglib;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;

public class Service {

	private Original service;

	public Service() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Original.class);
		enhancer.setCallback((FixedValue) () -> "Hello Tom!");
		service = (Original) enhancer.create();
	}

	public String setState(String state) {
		return service.setState(state);
	}

	public String getState() {
		return service.getState();
	}

}
