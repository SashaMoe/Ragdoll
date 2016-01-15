package ragdoll.asm.uml.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.uml.ClassDeclarationVisitor;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.uml.impl.Klass;

public class ClassDeclarationVisitorTest {

	private final String className;
	private final IClass c;
	private final ClassReader reader;
	private final ClassVisitor declVisitor;

	public ClassDeclarationVisitorTest() throws IOException {
		className = "ragdoll.asm.uml.test.sample.SampleClass";
		reader = new ClassReader(className);
		Map<String, IClass> iClasses = new HashMap<>();
		c = new Klass(className, iClasses);
		iClasses.put(className, c);
		declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c);
	}

	@Test
	public void testVisit() {
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		IClassDeclaration classDeclaration = c.getDeclaration();
		List<String> nameOfInterfaces = classDeclaration.getNameOfInterfaces();

		assertEquals("ragdoll/asm/uml/test/sample/SampleClass", classDeclaration.getClassName());
		assertEquals("ragdoll/asm/uml/test/sample/ParentClass", classDeclaration.getNameOfSuperClass());
		assertEquals("ragdoll/asm/uml/test/sample/SampleInterface", nameOfInterfaces.get(0));
		assertEquals(true, classDeclaration.isAbstract());
		assertEquals(false, classDeclaration.isInterface());
	}
}
