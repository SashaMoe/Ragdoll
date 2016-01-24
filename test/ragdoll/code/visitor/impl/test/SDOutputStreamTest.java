package ragdoll.code.visitor.impl.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.sd.GraphMethodVisitor;
import ragdoll.code.sd.api.Node;
import ragdoll.code.sd.impl.INode;
import ragdoll.code.visitor.impl.SDOutputStream;
import ragdoll.util.Utilities;

public class SDOutputStreamTest {
	private SDOutputStream sdOS;
	private StringBuffer sb;
	private INode startMethod;
	private List<String> classes;
	
	public SDOutputStreamTest() throws Exception {
		String fullyQualifiedMethodName = "ragdoll.asm.sd.test.sample.ClassA.methodA(int)";
		int maxDepth = 5;
		String className = Utilities.getClassNameFromFullyQualifiedMethodSignature(fullyQualifiedMethodName);
		String methodName = Utilities.getMethodNameFromFullyQualifiedMethodSignature(fullyQualifiedMethodName);
		List<String> paramTypes = Utilities.getParamTypesFromFullyQualifiedMethodSignature(fullyQualifiedMethodName);

		ClassReader reader;
		classes = new ArrayList<>();
		startMethod = new Node(className);
		startMethod.setMethodName(methodName);
		startMethod.setParamTypes(paramTypes);
		startMethod.setDepth(0);
		Queue<INode> methodQueue = new LinkedList<INode>();
		methodQueue.add(startMethod);

		while (!methodQueue.isEmpty()) {
			INode currentMethod = methodQueue.poll();
			if (!classes.contains(currentMethod.getClassName())) {
				classes.add(currentMethod.getClassName());
			}
			if (currentMethod.getDepth() < maxDepth) {
				reader = new ClassReader(currentMethod.getClassName());
				ClassVisitor graphMethodVisitor = new GraphMethodVisitor(Opcodes.ASM5, currentMethod);
				reader.accept(graphMethodVisitor, ClassReader.EXPAND_FRAMES);
				for (INode n : currentMethod.getAdjacencyList()) {
					n.setDepth(currentMethod.getDepth() + 1);
					methodQueue.add(n);
				}
			}
		}
	}
	
	private final void appendBufferLine(String s) {
		sb.append(s + "\n");
	}
	
	@Before
	public void setUpSdOS() {
		sdOS = new SDOutputStream();
		sb = new StringBuffer();
	}
	
	@Test
	public void testVisitINode(){
		startMethod.accept(sdOS);
		
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classa:void=ragdoll\\.asm\\.sd\\.test\\.sample\\.classb.<init>()");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classa:java\\.lang\\.Integer=ragdoll\\.asm\\.sd\\.test\\.sample\\.classb.methodB(i0 : int, i1 : int)");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classb:void=ragdoll\\.asm\\.sd\\.test\\.sample\\.classc.methodC()");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classc:void=ragdoll\\.asm\\.sd\\.test\\.sample\\.classc.methodD(j0 : java\\.lang\\.String)");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classc:void=ragdoll\\.asm\\.sd\\.test\\.sample\\.classb.methodE()");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classb:java\\.lang\\.Integer=java\\.lang\\.integer.valueOf(i0 : int)");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classc:void=java\\.util\\.arraylist.<init>()");
		appendBufferLine("java\\.util\\.arraylist:void=java\\.util\\.abstractlist.<init>()");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classc:void=java\\.util\\.collections.shuffle(j0 : java\\.util\\.List)");
		appendBufferLine("java\\.util\\.collections:void=java\\.util\\.random.<init>()");
		appendBufferLine("java\\.util\\.collections:void=java\\.util\\.collections.shuffle(j0 : java\\.util\\.List, j1 : java\\.util\\.Random)");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classb:java\\.lang\\.Integer=java\\.lang\\.integer.valueOf(i0 : int)");
		appendBufferLine("java\\.lang\\.integer:void=java\\.lang\\.integer.<init>(i0 : int)");
		appendBufferLine("java\\.lang\\.integer:void=java\\.lang\\.number.<init>()");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classa:int=java\\.lang\\.integer.intValue()");
		
		assertEquals(sb.toString(), sdOS.toString());
	}
	
	@Test
	public void testVisitClasses(){
		sdOS.visit(classes);
		
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classa:ragdoll.asm.sd.test.sample.ClassA[a]");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classb:ragdoll.asm.sd.test.sample.ClassB[a]");
		appendBufferLine("java\\.lang\\.integer:java.lang.Integer[a]");
		appendBufferLine("ragdoll\\.asm\\.sd\\.test\\.sample\\.classc:ragdoll.asm.sd.test.sample.ClassC[a]");
		appendBufferLine("java\\.util\\.arraylist:java.util.ArrayList[a]");
		appendBufferLine("java\\.util\\.collections:java.util.Collections[a]");
		appendBufferLine("java\\.lang\\.number:java.lang.Number[a]");
		appendBufferLine("java\\.util\\.abstractlist:java.util.AbstractList[a]");
		appendBufferLine("java\\.util\\.random:java.util.Random[a]");
		
		assertEquals(sb.toString(), sdOS.toString());
	}

}
