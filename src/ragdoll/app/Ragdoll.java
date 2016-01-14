package ragdoll.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.sd.GraphMethodVisitor;
import ragdoll.asm.uml.ClassDeclarationVisitor;
import ragdoll.asm.uml.ClassFieldVisitor;
import ragdoll.asm.uml.ClassMethodVisitor;
import ragdoll.code.sd.api.Node;
import ragdoll.code.sd.impl.INode;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.impl.Klass;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.util.ClassFinder;
import ragdoll.util.Utilities;

public class Ragdoll {
	public static void main(String[] args) throws Exception {
		if (args.length <= 1) {
			throw new Exception("Please specify at least one package name!");
		}
		String diagramType = args[0];
		if (diagramType.toUpperCase().equals("UML")) { // UML
			generateUML(Arrays.copyOfRange(args, 1, args.length));
		} else if (diagramType.toUpperCase().equals("SD")) { // Sequance Diagram
			int maxDepth = args.length < 2 ? 5 : Integer.valueOf(args[2]);
			generateSD(args[1], maxDepth);
		}
	}

	public static void generateSD(String fullyQualifiedMethodName, int maxDepth) throws Exception {
		String className = Utilities.getClassNameFromFullyQualifiedMethodSignature(fullyQualifiedMethodName);
		String methodName = Utilities.getMethodNameFromFullyQualifiedMethodSignature(fullyQualifiedMethodName);
		List<String> paramTypes = Utilities.getParamTypesFromFullyQualifiedMethodSignature(fullyQualifiedMethodName);

		ClassReader reader = new ClassReader(className);
		INode startMethod = new Node(className);
		startMethod.setMethodName(methodName);
		startMethod.setParamTypes(paramTypes);
		startMethod.setDepth(0);
		Queue<INode> methodQueue = new LinkedList<INode>();
		methodQueue.add(startMethod);

		while (!methodQueue.isEmpty()) {
			INode currentMethod = methodQueue.poll();
			if (currentMethod.getDepth() < maxDepth) {
				ClassVisitor graphMethodVisitor = new GraphMethodVisitor(Opcodes.ASM5, currentMethod);
				reader.accept(graphMethodVisitor, ClassReader.EXPAND_FRAMES);
				for (INode n : currentMethod.getAdjacencyList()) {
					n.setDepth(currentMethod.getDepth()+1);
					methodQueue.add(n);
				}
			}
		}
	}

	public static void generateUML(String[] items) throws Exception {
		// Traverse classes
		// List<Class<?>> classes = ClassFinder.find(args[0]);
		List<Class<?>> classes = new ArrayList<>();
		for (int i = 0; i < items.length; i++) {
			try {
				classes.add(Class.forName(items[i]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		List<String> classNames = new ArrayList<>();
		for (Class<?> c : classes) {
			classNames.add(c.getName());
		}
		GVOutputStream gvOS = new GVOutputStream();
		Map<String, IClass> iClasses = new HashMap<>();

		for (String className : classNames) {
			if (className.contains(".test.") || className.endsWith("Test") || className.endsWith("Tests")
					|| className.contains("$")) {
				continue;
			}
			IClass newClass = new Klass(className, iClasses);
			ClassReader reader = new ClassReader(className);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, newClass);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, newClass);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, newClass);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			iClasses.put(className, newClass);
		}

		// Output
		gvOS.initBuffer();
		for (String c : iClasses.keySet()) {
			iClasses.get(c).accept(gvOS);
		}
		gvOS.endBuffer();
		System.out.println(gvOS.toString());
	}
}
