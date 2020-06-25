package org.frostedflakes.test.object;

class B {

	private static final String MUST_BE_A_REPOSITORY = String.format("Given type must be assignable to %s!", B.class);
	
	public int i;

	public int i2;

	public B(int i, int i2) {
		super();
		this.i = i;
		this.i2 = i2;
		System.out.println(MUST_BE_A_REPOSITORY);
	}
}