package org.frostedflakes.test.object;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.frostedflakes.test.collections.TestUtilities;
import org.junit.Test;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisHelper;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

public class ObjectConstructionTests {

	@Test
	public void testObjectConstruction() {

		IFrostedFlakes<A> FLAKES = new FrostedFlakes<>();
		// Creating an empty Map
		List<String> list1 = new ArrayList<String>();

		// Mapping string values to int keys
		list1.add("Geeks");
		list1.add("4");
		list1.add("Geeks");
		list1.add("Welcomes");
		list1.add("You");
		
		A a = new A(list1);

		// Checking the equality
		A other = FLAKES.deepCopy(a);

		// then
		TestUtilities.testCollectionEquality(list1, a.list);
		
		assertEquals(a.b.i, other.b.i);
		assertEquals(a.b.i2, other.b.i2);
		
		assertEquals(a.SPEC_DEFINED_EXTRACTORS, other.SPEC_DEFINED_EXTRACTORS);
		TestUtilities.testCollectionEquality(a.SPEC_DEFINED_EXTRACTORS, other.SPEC_DEFINED_EXTRACTORS);
		
	    assertFalse(Objects.deepEquals(a, other));

	}
	
	
	@Test
	public void objenesisTest() throws IOException {
		
		IFrostedFlakes<DefaultRepositoryMetadata> FLAKES = new FrostedFlakes<>();

		DefaultRepositoryMetadata repoMeta = new DefaultRepositoryMetadata(Test1.class);
		
		
		FLAKES.save(repoMeta, "r.bin");

		DefaultRepositoryMetadata repoMeta2 = FLAKES.open("r.bin");
		
		// Checking the equality
		//DefaultRepositoryMetadata repoMeta2 = FLAKES.deepCopy(repoMeta);
		
		System.out.println(repoMeta2);
	}
	
	@Test
	public void objenesisRestoreTest() throws IOException {
		IFrostedFlakes<DefaultRepositoryMetadata> FLAKES = new FrostedFlakes<>();
		DefaultRepositoryMetadata repoMeta2 = FLAKES.open("r.bin");
		
		// Checking the equality
		//DefaultRepositoryMetadata repoMeta2 = FLAKES.deepCopy(repoMeta);
		
		System.out.println(repoMeta2);
	}

}