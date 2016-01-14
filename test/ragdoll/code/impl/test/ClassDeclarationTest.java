package ragdoll.code.impl.test;

import static org.junit.Assert.*;
import org.junit.Test;

import ragdoll.code.uml.impl.ClassDeclaration;

import java.util.ArrayList;
import java.util.List;

public class ClassDeclarationTest {
	private final ClassDeclaration classDeclaration;
	private final boolean isAbstract;
	private final boolean isInterface;
	private final String className;
	private final String nameOfSuperClass;
	private final List<String> nameOfInterfaces;
	
	public ClassDeclarationTest() {
		isAbstract = true;
		isInterface = false;
		className = "ragdoll.class";
		nameOfInterfaces = new ArrayList<>();
		nameOfInterfaces.add("i1");
		nameOfInterfaces.add("i2");
		nameOfSuperClass = "java.lang.Object";
		classDeclaration = new ClassDeclaration(isAbstract, isInterface, className, nameOfSuperClass, nameOfInterfaces);
	}
	
	@Test
	public void testIsAbstract() {
		assertEquals(this.isAbstract, this.classDeclaration.isAbstract());
	}

	@Test
	public void testIsInterface() {
		assertEquals(this.isInterface, this.classDeclaration.isInterface());
	}

	@Test
	public void testGetClassName() {
		assertEquals(this.className, this.classDeclaration.getClassName());
	}

	@Test
	public void testGetNameOfSuperClass() {
		assertEquals(this.nameOfSuperClass, this.classDeclaration.getNameOfSuperClass());
	}

	@Test
	public void testGetNameOfInterfaces() {
		List<String> interfaces = this.classDeclaration.getNameOfInterfaces();
		for (int i = 0; i < nameOfInterfaces.size(); i++) {
			assertEquals(nameOfInterfaces.get(i), interfaces.get(i));
		}
	}
}
