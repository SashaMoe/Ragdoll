package ragdoll.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	public static void main(String[] args) throws IOException {

		List<Class<?>> classes = ClassFinder.find(args[0]);		
		List<String> classNames = new ArrayList<>();
		for (Class<?> c : classes) {
			classNames.add(c.getName());
		}
		GVOutputStream gvOS = new GVOutputStream();
		List<IClass> iClasses = new ArrayList<IClass>();

		for (String className : classNames) {
			if (className.contains(".test.")) {
				continue;
			}
			IClass newClass = new Klass(className);
			ClassReader reader = new ClassReader(className);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, newClass);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, newClass);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, newClass);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			iClasses.add(newClass);
		}

		gvOS.initBuffer();
		for (IClass c : iClasses) {
			c.accept(gvOS);
		}
		gvOS.endBuffer();
		System.out.println(gvOS.toString());
	}
}
