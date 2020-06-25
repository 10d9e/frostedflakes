package org.frostedflakes.test.proxy.jdk;
import java.lang.reflect.Proxy;

public class ServiceWithDynamicProxy {
	
	private Class<?> proxyClass;

	private Service service;
	
	// second reference
	private Service service2;
	
	

	public ServiceWithDynamicProxy() {
		Original original = new Original();
		Handler handler = new Handler(original);
		service = (Service) Proxy.newProxyInstance(Service.class.getClassLoader(), new Class[] { Service.class },
				handler);
		
		service2 = service;
		
		proxyClass = service.getClass();
	}

	public void setState(String state) {
		service.setState(state);
	}

	public String getState() {
		return service.getState();
	}

}
