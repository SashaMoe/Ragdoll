package ragdoll.asm;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.code.api.IClass;
import ragdoll.code.api.IField;
import ragdoll.code.impl.Klass;

public class ClassFieldVisitorTest {
	private final String className;
	private final IClass c;
	private final ClassReader reader;
	private final ClassVisitor fieldVisitor;
	
	public ClassFieldVisitorTest() throws IOException {
		className = "ragdoll.asm.sample.SampleClass";
		reader = new ClassReader(className);
		c = new Klass(className);
		fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, c);
	}
	
	@Test
	public void testVisit() {
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		HashMap<String, IField> fieldMap = c.getFieldMap();
		assertEquals(true, fieldMap.containsKey("i1"));
		assertEquals("private", fieldMap.get("i1").getAccessLevel());
		assertEquals("int", fieldMap.get("i1").getType());
		
		assertEquals(true, fieldMap.containsKey("o2"));
		assertEquals("public", fieldMap.get("o2").getAccessLevel());
		assertEquals("java.lang.Object", fieldMap.get("o2").getType());
	}
}
