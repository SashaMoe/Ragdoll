package ragdoll.util.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import ragdoll.util.Utilities;

public class UtilitiesTest {
	private static String signature;
	
	public UtilitiesTest() {
		signature = "ragdoll.test.TestClass.testMethod(int, boolean, java.util.ArrayList)";
	}
	
	@Test
	public void testGetClassNameFromFullyQualifiedMethodSignature() {
		assertEquals("ragdoll.test.TestClass", Utilities.getClassNameFromFullyQualifiedMethodSignature(signature));
	}
	
	@Test
	public void testGetMethodNameFromFullyQualifiedMethodSignature() {
		assertEquals("testMethod", Utilities.getMethodNameFromFullyQualifiedMethodSignature(signature));
	}
	
	@Test
	public void testGetMethodTypesFromFullyQualifiedMethodSignature() {
		List<String> types = Utilities.getMethodTypesFromFullyQualifiedMethodSignature(signature);
		assertEquals("int", types.get(0));
		assertEquals("boolean", types.get(1));
		assertEquals("java.util.ArrayList", types.get(2));
	}
}
