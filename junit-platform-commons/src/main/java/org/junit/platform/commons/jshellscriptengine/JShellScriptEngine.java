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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

public class JShellScriptEngine implements ScriptEngine {

	private ScriptContext context = new SimpleScriptContext();

	public JShellScriptEngine() {
		this(null);
	}

	public JShellScriptEngine(Bindings globalBindings) {

	}

	@Override
	public Bindings createBindings() {
		return new SimpleBindings();
	}

	@Override
	public Object eval(String script) throws ScriptException {
		return eval(script, context.getBindings(ScriptContext.ENGINE_SCOPE));
	}

	@Override
	public Object eval(Reader reader) throws ScriptException {
		return eval(readScript(reader));
	}

	@Override
	public Object eval(String script, ScriptContext context) throws ScriptException {
		return eval(script, context, context.getBindings(ScriptContext.ENGINE_SCOPE));
	}

	@Override
	public Object eval(Reader reader, ScriptContext context) throws ScriptException {
		return eval(readScript(reader), context);
	}

	@Override
	public Object eval(String script, Bindings bindings) throws ScriptException {
		return eval(script, context, bindings);
	}

	@Override
	public Object eval(Reader reader, Bindings bindings) throws ScriptException {
		return eval(readScript(reader), bindings);
	}

	private Object eval(String script, ScriptContext context, Bindings bindings) throws ScriptException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the string to declare a type of value's type.
	 */
	public static String getDeclaredType(Object value) {
		return "java.lang.Object";
	}

	/**
	 * Called from script to retrieve values from the bindings.
	 */
	public static Object getBindingValue(String name) {
		if (name.equals("__")) {
			name = "_";
		}
		return name;
	}

	@Override
	public Object get(String key) {
		return getBindings(ScriptContext.ENGINE_SCOPE).get(key);
	}

	@Override
	public Bindings getBindings(int scope) {
		return context.getBindings(scope);
	}

	@Override
	public ScriptContext getContext() {
		return context;
	}

	@Override
	public ScriptEngineFactory getFactory() {
		return new JShellScriptEngineFactory();
	}

	@Override
	public void put(String key, Object value) {
		getBindings(ScriptContext.ENGINE_SCOPE).put(key, value);
	}

	@Override
	public void setBindings(Bindings bindings, int scope) {
		context.setBindings(bindings, scope);
	}

	@Override
	public void setContext(ScriptContext context) {
		this.context = context;
	}

	/**
	 * Returns the whole contents of reader.
	 */
	private static String readScript(Reader reader) throws ScriptException {
		try {
			StringBuilder s = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				s.append(line);
				s.append("\n");
			}
			return s.toString();
		}
		catch (IOException e) {
			throw new ScriptException(e);
		}
	}
}
