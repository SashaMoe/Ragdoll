package ragdoll.asm;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.impl.ClassDeclaration;
import ragdoll.code.visitor.api.IVisitor;

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
		boolean isAbstract = (access & Opcodes.ACC_ABSTRACT) != 0;
		boolean isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
		List<String> intfArr = new ArrayList<>(Arrays.asList(interfaces));
		IClassDeclaration classDeclaration = new ClassDeclaration(isAbstract, isInterface, name, superName, intfArr);
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
