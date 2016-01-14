package ragdoll.code.impl.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ragdoll.code.uml.impl.Method;

public class MethodTest {
	private final Method method;
	private final String accessLevel;
	private final List<String> exceptions;
	private final String methodName;
	private final List<String> paramTypes;
	private final String returnType;
	
	public MethodTest() {
		accessLevel = "private";
		exceptions = new ArrayList<>();
		exceptions.add("e1");
		exceptions.add("e2");
		methodName = "method";
		paramTypes = new ArrayList<>();
		paramTypes.add("t1");
		paramTypes.add("t2");
		returnType = "t3";
		method = new Method(methodName, accessLevel, returnType, paramTypes, exceptions);
	}
	
	@Test
	public void testGetAccessLevel() {
		assertEquals(this.accessLevel, this.method.getAccessLevel());
	}

	@Test
	public void testGetExceptions() {
		List<String> exceptions = this.method.getExceptions();
		for (int i = 0; i < exceptions.size(); i++) {
			assertEquals(this.exceptions.get(i), exceptions.get(i));
		}
	}
	
	@Test
	public void testMethodName() {
		assertEquals(this.methodName, this.method.getMethodName());
	}
	
	@Test
	public void testParamTypes() {
		List<String> paramTypes = this.method.getParamTypes();
		for (int i = 0; i < paramTypes.size(); i++) {
			assertEquals(this.paramTypes.get(i), paramTypes.get(i));
		}
	}
	
	@Test
	public void testReturnType() {
		assertEquals(this.returnType, this.method.getReturnType());
	}
}
