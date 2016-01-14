package ragdoll.app;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.uml.ClassDeclarationVisitor;
import ragdoll.asm.uml.ClassFieldVisitor;
import ragdoll.asm.uml.ClassMethodVisitor;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.impl.Klass;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.util.ClassFinder;

public class Ragdoll {
	public static void main(String[] args) throws Exception {
		if (args.length <= 1) {
			throw new Exception("Please specify at least one package name!");
		}
		String diagramType = args[0];
		if (diagramType.toUpperCase().equals("UML")) { // UML
			generateUML(Arrays.copyOfRange(args, 1, args.length));
		} else if (diagramType.toUpperCase().equals("SD")) { // SD is Sweet Dessert
			generateSD(args[1]);
		}
	}
	
	public static void generateSD(String fullyQualifiedMethodName) {
		
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
