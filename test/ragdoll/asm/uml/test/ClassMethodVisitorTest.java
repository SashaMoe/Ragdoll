package ragdoll.asm.uml.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.uml.ClassFieldVisitor;
import ragdoll.asm.uml.ClassMethodVisitor;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.impl.Klass;

public class ClassMethodVisitorTest {
	private final String className;
	private final IClass c;
	private final ClassReader reader;
	private final ClassVisitor methodVisitor;
	String packageName;

	public ClassMethodVisitorTest() throws IOException {
		packageName = "ragdoll.asm.uml.test.sample.";
		className = packageName + "SampleClass";
		reader = new ClassReader(className);
		Map<String, IClass> iClasses = new HashMap<>();
		c = new Klass(className, iClasses);
		IClass stubClass = new Klass("", iClasses);
		iClasses.put(className, c);
		iClasses.put(packageName + "UsedClass", stubClass);
		iClasses.put(packageName + "UsedClass2", stubClass);
		iClasses.put(packageName + "ParentClass", stubClass);
		iClasses.put(packageName + "SampleInterface", stubClass);
		methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, c);
	}

	@Test
	public void testVisit() {
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		List<IMethod> methodList = c.getMethodList();
		List<String> paramTypes;
		List<String> exceptionList;

		paramTypes = methodList.get(1).getParamTypes();
		exceptionList = methodList.get(1).getExceptions();

		assertEquals("public", methodList.get(1).getAccessLevel());
		assertEquals("void", methodList.get(1).getReturnType());
		assertEquals("sampleMethod", methodList.get(1).getMethodName());
		assertEquals("int", paramTypes.get(0));
		assertEquals("java.lang.Object", paramTypes.get(1));
		assertEquals("java/lang/Exception", exceptionList.get(0));

		assertEquals("private", methodList.get(2).getAccessLevel());
		assertEquals("ragdoll.asm.uml.test.sample.SampleInterface", methodList.get(2).getReturnType());
		assertEquals("sampleMethod2", methodList.get(2).getMethodName());

		assertEquals("protected", methodList.get(3).getAccessLevel());
		assertEquals("int", methodList.get(3).getReturnType());
		assertEquals("sampleMethod3", methodList.get(3).getMethodName());

		assertEquals("default", methodList.get(4).getAccessLevel());
		assertEquals("boolean", methodList.get(4).getReturnType());
		assertEquals("sampleMethod4", methodList.get(4).getMethodName());
	}

	@Test
	public void testUseArrow() {
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		c.filterUseSet();
		Set<String> useSet = c.getUseSet();

		assertTrue(useSet.contains(packageName + "UsedClass"));
		assertTrue(useSet.contains(packageName + "UsedClass2"));
		assertTrue(useSet.contains(packageName + "ParentClass"));
		assertEquals(3, useSet.size());
	}

	@Test
	public void testAssociationArrow() {
		ClassFieldVisitor fieldVistor = new ClassFieldVisitor(Opcodes.ASM5, c);
		reader.accept(fieldVistor, ClassReader.EXPAND_FRAMES);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		c.filterAssocSet();
		Set<String> associationTypeSet = c.getAssociationType();

		assertTrue(associationTypeSet.contains(packageName + "SampleClass"));
		assertTrue(associationTypeSet.contains(packageName + "ParentClass"));
		assertEquals(2, associationTypeSet.size());
	}
}
