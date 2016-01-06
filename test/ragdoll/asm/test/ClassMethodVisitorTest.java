package ragdoll.asm.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.ClassMethodVisitor;
import ragdoll.code.api.IClass;
import ragdoll.code.api.IMethod;
import ragdoll.code.impl.Klass;

public class ClassMethodVisitorTest {
	private final String className;
	private final IClass c;
	private final ClassReader reader;
	private final ClassVisitor methodVisitor;

	public ClassMethodVisitorTest() throws IOException {
		className = "ragdoll.asm.test.sample.SampleClass";
		reader = new ClassReader(className);
		Map<String, IClass> iClasses = new HashMap<>();
		c = new Klass(className, iClasses);
		iClasses.put(className, c);
		methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, c);
	}

	@Test
	public void testVisit() {
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		List<IMethod> methodList = c.getMethodList();
		List<String> paramTypes = methodList.get(1).getParamTypes();
		List<String> exceptionList = methodList.get(1).getExceptions();
		
		assertEquals("public", methodList.get(1).getAccessLevel());
		assertEquals("void", methodList.get(1).getReturnType());
		assertEquals("sampleMethod", methodList.get(1).getMethodName());
		
		assertEquals("int", paramTypes.get(0));
		assertEquals("java.lang.Object", paramTypes.get(1));
		assertEquals("java/lang/Exception", exceptionList.get(0));
	}
}
