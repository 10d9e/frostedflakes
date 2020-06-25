package org.frostedflakes.test.random;

class CycleObject {
	class A {
		public B b;
	}

	class B {
		public A a;
	}
	
	A a = new A();
	B b = new B();
	
	public CycleObject() {
		a = new A();
		b = new B();
		a.b = b;
		b.a = a;
	}

	public static void main(String... args) {
		CycleObject test = new CycleObject();
		
		

	}

}