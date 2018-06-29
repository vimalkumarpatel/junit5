/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.platform.commons.util;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;

public abstract class Try<V> {

	public static <V> Try<V> call(Callable<V> action) {
		try {
			return success(action.call());
		}
		catch (Exception e) {
			return failure(e);
		}
	}

	public static <V> Try<V> success(V value) {
		return new Success<>(value);
	}

	public static <V> Try<V> failure(Exception cause) {
		return new Failure<>(Preconditions.notNull(cause, "cause must not be null"));
	}

	public abstract <U> Try<U> andThen(Transformer<V, U> action);

	public abstract Try<V> orElse(Callable<V> action);

	public abstract V get();

	public abstract V getOrThrow(Function<Exception, Exception> exceptionCreator);

	public abstract Optional<V> toOptional();

	@FunctionalInterface
	public interface Transformer<S, T> {
		T apply(S input) throws Exception;
	}

	private static class Success<V> extends Try<V> {

		private final V value;

		Success(V value) {
			this.value = value;
		}

		@Override
		public <U> Try<U> andThen(Transformer<V, U> action) {
			return call(() -> action.apply(value));
		}

		@Override
		public Try<V> orElse(Callable<V> action) {
			return this;
		}

		@Override
		public V get() {
			return value;
		}

		@Override
		public V getOrThrow(Function<Exception, Exception> exceptionCreator) {
			return value;
		}

		@Override
		public Optional<V> toOptional() {
			return Optional.ofNullable(value);
		}
	}

	private static class Failure<V> extends Try<V> {

		private final Exception cause;

		Failure(Exception cause) {
			this.cause = cause;
		}

		@Override
		public <U> Try<U> andThen(Transformer<V, U> action) {
			return new Failure<>(cause);
		}

		@Override
		public Try<V> orElse(Callable<V> action) {
			return call(action);
		}

		@Override
		public V get() {
			throw ExceptionUtils.throwAsUncheckedException(cause);
		}

		@Override
		public V getOrThrow(Function<Exception, Exception> exceptionCreator) {
			throw ExceptionUtils.throwAsUncheckedException(exceptionCreator.apply(cause));
		}

		@Override
		public Optional<V> toOptional() {
			return Optional.empty();
		}
	}

}
