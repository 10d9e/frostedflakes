package org.frostedflakes.test.proxy.jdk;

public class Original implements Service {
	
	private String state = "init";
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return this.state;
	}
}