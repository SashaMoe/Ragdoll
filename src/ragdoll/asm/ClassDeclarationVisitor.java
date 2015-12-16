package ragdoll.asm;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {

	public ClassDeclarationVisitor(int arg0) {
		super(arg0);
	}

	public ClassDeclarationVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
