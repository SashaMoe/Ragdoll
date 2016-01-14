package ragdoll.asm.uml;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.uml.impl.ClassDeclaration;

public class ClassDeclarationVisitor extends ClassVisitor {

	private IClass c;
	
	public ClassDeclarationVisitor(int arg0, IClass c) {
		super(arg0);
		this.c = c;
	}

	public ClassDeclarationVisitor(int arg0, ClassVisitor arg1, IClass c) {
		super(arg0, arg1);
		this.c = c;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		boolean isAbstract = (access & Opcodes.ACC_ABSTRACT) != 0;
		boolean isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
		List<String> intfArr = new ArrayList<>(Arrays.asList(interfaces));
		IClassDeclaration classDeclaration = new ClassDeclaration(isAbstract, isInterface, name, superName, intfArr);
		this.c.setDeclaration(classDeclaration);
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
