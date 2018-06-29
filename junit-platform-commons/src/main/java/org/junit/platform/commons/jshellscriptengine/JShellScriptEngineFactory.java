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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.SimpleBindings;

public class JShellScriptEngineFactory implements ScriptEngineFactory {
	private final Bindings globalBindings = new SimpleBindings();

	@Override
	public String getEngineName() {
		return "JShell ScriptEngine";
	}

	@Override
	public String getEngineVersion() {
		return "1.0";
	}

	@Override
	public List<String> getExtensions() {
		return Collections.singletonList("java");
	}

	@Override
	public String getLanguageName() {
		return "Java";
	}

	@Override
	public String getLanguageVersion() {
		return "9";
	}

	@Override
	public String getMethodCallSyntax(String object, String method, String... args) {
		StringBuilder s = new StringBuilder();
		s.append(object).append(".").append(method);
		s.append("(");
		for (int i = 0; i < args.length; i++) {
			s.append(args[i]);
			if (i < args.length - 1) {
				s.append(",");
			}
		}
		s.append(");");
		return s.toString();
	}

	@Override
	public List<String> getMimeTypes() {
		return Collections.singletonList("text/x-java-source");
	}

	@Override
	public List<String> getNames() {
		return Arrays.asList("Java", "JShell", "jshell", "java");
	}

	@Override
	public String getOutputStatement(String toDisplay) {
		return "System.out.println(" + toDisplay + ");";
	}

	@Override
	public Object getParameter(String key) {
		switch (key) {
			case ScriptEngine.ENGINE:
				return getEngineName();
			case ScriptEngine.ENGINE_VERSION:
				return getEngineVersion();
			case ScriptEngine.LANGUAGE:
				return getLanguageName();
			case ScriptEngine.LANGUAGE_VERSION:
				return getLanguageVersion();
			case ScriptEngine.NAME:
				return getNames().get(0);
			default:
				return null;
		}
	}

	@Override
	public String getProgram(String... statements) {
		StringBuilder s = new StringBuilder();
		for (String statement : statements) {
			s.append(statement).append(",").append("\n");
		}
		return s.toString();
	}

	@Override
	public ScriptEngine getScriptEngine() {
		return new JShellScriptEngine(globalBindings);
	}
}
