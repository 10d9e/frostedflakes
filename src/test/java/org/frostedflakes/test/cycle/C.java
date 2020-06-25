package org.frostedflakes.test.cycle;
class C {
		A a;
		B b;
		public C() {
			a = new A();
			b = new B();
			a.b = b;
			b.a = a;
		}
	}