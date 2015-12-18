package ragdoll.code.visitor.impl;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.ClassDeclarationVisitor;
import ragdoll.asm.ClassFieldVisitor;
import ragdoll.asm.ClassMethodVisitor;
import ragdoll.code.api.IClass;
import ragdoll.code.impl.Klass;

public class GVOutputStreamTest {

	private GVOutputStream gvOS;
	private final IClass newClass;
	private StringBuffer sb;
	
	public GVOutputStreamTest() throws IOException {
		gvOS = null;
		String className = "ragdoll.asm.sample.SampleClass";
		newClass = new Klass(className);
		ClassReader reader = new ClassReader(className);

		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, newClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, newClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, newClass);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
	}
	
	private final void appendBufferLine(String s) {
		sb.append(s + "\n");
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
		appendBufferLine("fontsize = 8");
		appendBufferLine("node [");
		appendBufferLine("fontname = \"Times New Roman\"");
		appendBufferLine("fontsize = 8");
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
		// TODO
	}
	
	@Test
	public void testPostVisitIClass() {
		// TODO
	}
	
	@Test
	public void testVisitIClassDeclaration() {
		// TODO
	}
	
	@Test
	public void testVisitIField() {
		// TODO
	}
	
	@Test
	public void testVisitIMethod() {
		// TODO
	}
	
	@Test
	public void testVisitString() {
		// TODO
	}
}
