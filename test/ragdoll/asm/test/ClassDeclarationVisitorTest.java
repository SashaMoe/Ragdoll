package ragdoll.asm.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.ClassDeclarationVisitor;
import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.impl.Klass;

public class ClassDeclarationVisitorTest {

	private final String className;
	private final IClass c;
	private final ClassReader reader;
	private final ClassVisitor declVisitor;

	public ClassDeclarationVisitorTest() throws IOException {
		className = "ragdoll.asm.test.sample.SampleClass";
		reader = new ClassReader(className);
		c = new Klass(className);
		declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c);
	}

	@Test
	public void testVisit() {
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		IClassDeclaration classDeclaration = c.getDeclaration();
		List<String> nameOfInterfaces = classDeclaration.getNameOfInterfaces();

		assertEquals("ragdoll/asm/test/sample/SampleClass", classDeclaration.getClassName());
		assertEquals("ragdoll/asm/test/sample/ParentClass", classDeclaration.getNameOfSuperClass());
		assertEquals("ragdoll/asm/test/sample/SampleInterface", nameOfInterfaces.get(0));
		assertEquals(true, classDeclaration.isAbstract());
		assertEquals(false, classDeclaration.isInterface());
	}
}
