package org.frostedflakes.test.proxy.cglib;

public class Original {
	
	private String state = "init";
	
	public String setState(String state) {
		this.state = state;
		return "Hello " + this.state;
	}
	
	public String getState() {
		return this.state;
	}
}