/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.platform.commons.jshellscriptengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link JShellScriptEngine}.
 *
 * @since 1.4
 */
class JShellScriptEngineTests {

	@Test
	void simple() throws Exception {
		JShellScriptEngine engine = new JShellScriptEngine();

		assertTrue((boolean) engine.eval("0 == 0"));
		assertFalse((boolean) engine.eval("0 == 1"));
		assertEquals(3, engine.eval("1 + 2"));
	}

}
