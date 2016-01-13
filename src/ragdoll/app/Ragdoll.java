package ragdoll.app;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.ClassDeclarationVisitor;
import ragdoll.asm.ClassFieldVisitor;
import ragdoll.asm.ClassMethodVisitor;
import ragdoll.code.api.IClass;
import ragdoll.code.impl.Klass;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.util.ClassFinder;

public class Ragdoll {
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			throw new Exception("Please specify at least one package name!");
		}

		// Traverse classes
		// List<Class<?>> classes = ClassFinder.find(args[0]);
		List<Class<?>> classes = new ArrayList<>();
		for (int i = 0; i < args.length; i++) {
			try {
				classes.add(Class.forName(args[i]));
			} catch (Exception e) {
//				e.printStackTrace();
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
