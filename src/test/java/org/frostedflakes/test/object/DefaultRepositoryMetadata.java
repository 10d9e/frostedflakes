package org.frostedflakes.test.object;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class DefaultRepositoryMetadata {

	private static final String MUST_BE_A_REPOSITORY = String.format("Given type must be assignable to %s!",
			Repository.class);

	public final Class<?> idType;
	public final Class<?> domainType;

	/**
	 * Creates a new {@link DefaultRepositoryMetadata} for the given repository interface.
	 *
	 * @param repositoryInterface must not be {@literal null}.
	 */
	public DefaultRepositoryMetadata(Class<?> repositoryInterface) {
		
		if(!repositoryInterface.isAnnotationPresent((Class<? extends Annotation>) Repository.class)) {
			System.err.println(MUST_BE_A_REPOSITORY);
		}

		List<String> list = new ArrayList<>();
		list.add("java.lang.String");
		list.add("java.lang.Integer");
		
		this.domainType = resolveTypeParameter(list, 0,
				() -> String.format("Could not resolve domain type of %s!", repositoryInterface));
		this.idType = resolveTypeParameter(list, 1,
				() -> String.format("Could not resolve id type of %s!", repositoryInterface));
	}

	private static Class<?> resolveTypeParameter(List<String> arguments, int index,
			Supplier<String> exceptionMessage) {

		if (arguments.size() <= index || arguments.get(index) == null) {
			throw new IllegalArgumentException(exceptionMessage.get());
		}

		return Test1.class;
	}
}