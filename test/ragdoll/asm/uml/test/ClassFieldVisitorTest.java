package ragdoll.asm.uml.test;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.uml.ClassFieldVisitor;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IField;
import ragdoll.code.uml.impl.Klass;

public class ClassFieldVisitorTest {
	private final String className;
	private final IClass c;
	private final ClassReader reader;
	private final ClassVisitor fieldVisitor;
	
	public ClassFieldVisitorTest() throws IOException {
		className = "ragdoll.asm.uml.test.sample.SampleClass";
		reader = new ClassReader(className);
		Map<String, IClass> iClasses = new HashMap<>();
		c = new Klass(className, iClasses);
		iClasses.put(className, c);
		fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, c);
	}
	
	@Test
	public void testVisit() {
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		HashMap<String, IField> fieldMap = c.getFieldMap();
		assertEquals(false, fieldMap.containsKey("n0"));
		
		assertEquals(true, fieldMap.containsKey("i1"));
		assertEquals("private", fieldMap.get("i1").getAccessLevel());
		assertEquals("int", fieldMap.get("i1").getType());
		assertEquals(null, fieldMap.get("i1").getSignature());
		
		assertEquals(true, fieldMap.containsKey("o2"));
		assertEquals("public", fieldMap.get("o2").getAccessLevel());
		assertEquals("java.lang.Object", fieldMap.get("o2").getType());
		assertEquals(null, fieldMap.get("o2").getSignature());
		
		assertEquals(true, fieldMap.containsKey("a3"));
		assertEquals("protected", fieldMap.get("a3").getAccessLevel());
		assertEquals("java.util.ArrayList", fieldMap.get("a3").getType());
		assertEquals("Ljava/util/ArrayList<Ljava/lang/String;>;", fieldMap.get("a3").getSignature());
		
		assertEquals(true, fieldMap.containsKey("n4"));
		assertEquals("default", fieldMap.get("n4").getAccessLevel());
		assertEquals("boolean", fieldMap.get("n4").getType());
		assertEquals(null, fieldMap.get("n4").getSignature());
		
	}
}
