package org.frostedflakes.test.random;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Snapshot {

	private Entrance entrance;

	private long threadId;

	private Object target;

	private String methodName;

	private String[] parametersTypes;

	private Throwable error;

	private Object[] arguments;

	List<Map<String, Object>> frames;

	public Snapshot() {
	}

	public Snapshot(long threadId, Entrance entrance, Object target, Method m, Object[] arguments, Throwable error,
			List<Map<String, Object>> frames) {
		super();
		this.threadId = threadId;
		this.entrance = entrance;
		this.target = target;
		this.methodName = m.getName();
		this.error = error;
		this.arguments = arguments;
		this.frames = frames;
		addParameterTypes(m);
	}

	public Snapshot(long threadId, Entrance entrance, Object target, Method m, Object[] arguments, Throwable error) {
		super();
		this.threadId = threadId;
		this.entrance = entrance;
		this.target = target;
		this.methodName = m.getName();
		this.error = error;
		this.arguments = arguments;
		addParameterTypes(m);
	}
	
	public Snapshot(long threadId, Entrance entrance, Object target, Method m, Object[] arguments) {
		super();
		this.threadId = threadId;
		this.entrance = entrance;
		this.target = target;
		this.methodName = m.getName();
		this.arguments = arguments;
		addParameterTypes(m);
	}

	private void addParameterTypes(Method m) {
		this.parametersTypes = new String[m.getParameterTypes().length];
		int i = 0;
		for (Class<?> p : m.getParameterTypes()) {
			this.parametersTypes[i] = p.getCanonicalName();
			i++;
		}
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Method getMethod() {
		try {
			return this.target.getClass().getMethod(methodName, getParameterTypes());
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String method) {
		this.methodName = method;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public Entrance getEntrance() {
		return entrance;
	}

	public void setEntrance(Entrance entrance) {
		this.entrance = entrance;
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public List<Map<String, Object>> getFrames() {
		return frames;
	}

	public void setFrames(List<Map<String, Object>> frames) {
		this.frames = frames;
	}

	public Class<?>[] getParameterTypes() {
		Class<?>[] ps = new Class<?>[this.parametersTypes.length];
		int i = 0;
		for (String p : this.parametersTypes) {
			try {
				ps[i] = Class.forName(p);
				i++;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ps;
	}

	public void setParameters(Class<?>[] parameters) {
		this.parametersTypes = new String[parameters.length];
		int i = 0;
		for (Class<?> p : parameters) {
			this.parametersTypes[i] = p.getCanonicalName();
			i++;
		}
	}

}
