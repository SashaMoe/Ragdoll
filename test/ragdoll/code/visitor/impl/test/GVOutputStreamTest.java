package ragdoll.code.visitor.impl.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.ClassDeclarationVisitor;
import ragdoll.asm.ClassFieldVisitor;
import ragdoll.asm.ClassMethodVisitor;
import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;
import ragdoll.code.impl.Klass;
import ragdoll.code.visitor.impl.GVOutputStream;

public class GVOutputStreamTest {

	private GVOutputStream gvOS;
	private final IClass newClass;
	private StringBuffer sb;

	public GVOutputStreamTest() throws IOException {
		gvOS = null;
		String className = "ragdoll.asm.test.sample.SampleClass";
		Map<String, IClass> iClasses = new HashMap<>();
		newClass = new Klass(className, iClasses);
		iClasses.put(className, newClass);
		ClassReader reader = new ClassReader(className);

		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, newClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, newClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, newClass);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
	}

	private final void appendBufferLine(String s) {
		sb.append(s + "\n");
	}

	private final void appendBuffer(String s) {
		sb.append(s);
	}

	@Before
	public void setUpGvOS() {
		gvOS = new GVOutputStream();
		sb = new StringBuffer();
	}

	@Test
	public void testInitBuffer() {
		gvOS.initBuffer();

		appendBufferLine("digraph G {");
		appendBufferLine("fontname = \"Times New Roman\"");
		appendBufferLine("fontsize = 12");
		appendBufferLine("node [");
		appendBufferLine("fontname = \"Times New Roman\"");
		appendBufferLine("fontsize = 12");
		appendBufferLine("shape = \"record\"");
		appendBufferLine("]");

		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testEndBuffer() {
		gvOS.endBuffer();
		appendBufferLine("}");
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testVisitIClass() {
		gvOS.visit(newClass);
		appendBufferLine("\"ragdoll.asm.test.sample.SampleClass\" [");
		appendBuffer("label = <{");
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testPostVisitIClass() {
		gvOS.postVisit(newClass);

		appendBufferLine("}>");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("\"ragdoll.asm.test.sample.SampleClass\" -> \"ragdoll.asm.test.sample.SampleInterface\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("\"ragdoll.asm.test.sample.SampleClass\" -> \"ragdoll.asm.test.sample.ParentClass\"");
		
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testVisitIClassDeclaration() {
		IClassDeclaration classDeclaration = newClass.getDeclaration();
		classDeclaration.accept(gvOS);
		appendBuffer("«abstract»<br/>ragdoll.asm.test.sample.SampleClass|");
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testVisitIField() {
		HashMap<String, IField> fieldMap = newClass.getFieldMap();
		for (String fieldName : fieldMap.keySet()) {
			IField f = fieldMap.get(fieldName);
			f.accept(gvOS);
		}
		
		appendBuffer("+ o2 : java.lang.Object<br/>- i1 : int<br/>");
		
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testVisitIMethod() {
		List<IMethod> methodList = newClass.getMethodList();
		for (IMethod m : methodList){
			m.accept(gvOS);
		}
		
		appendBuffer("+ sampleMethod(i0: into1: Object): void<br/>");
		
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testVisitString() {
		gvOS.visit("|");
		appendBuffer("|");
		assertEquals(sb.toString(), gvOS.toString());
	}
}
