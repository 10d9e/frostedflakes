package org.frostedflakes.test.primitives;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Determines the storage format for a cached type.
 */
public enum Sort {

	/**
	 * Creates a cache where cached types are wrapped by {@link WeakReference}s.
	 */
	WEAK {
		@Override
		protected Reference<Class<?>> wrap(Class<?> type) {
			return new WeakReference<Class<?>>(type);
		}
	},

	/**
	 * Creates a cache where cached types are wrapped by {@link SoftReference}s.
	 */
	SOFT {
		@Override
		protected Reference<Class<?>> wrap(Class<?> type) {
			return new SoftReference<Class<?>>(type);
		}
	};

	/**
	 * Wraps a type as a {@link Reference}.
	 *
	 * @param type The type to wrap.
	 * @return The reference that represents the type.
	 */
	protected abstract Reference<Class<?>> wrap(Class<?> type);
}