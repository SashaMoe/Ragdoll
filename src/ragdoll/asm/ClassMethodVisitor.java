package ragdoll.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import ragdoll.code.api.IMethod;
import ragdoll.code.impl.Method;

public class ClassMethodVisitor extends ClassVisitor {

	public ClassMethodVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		// TODO Auto-generated method stub
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

		String returnType = Type.getReturnType(desc).getClassName();

		Type[] argTypes = Type.getArgumentTypes(desc);
		List<String> sTypes = new ArrayList<>();
		for (Type t : argTypes) {
			sTypes.add(t.getClassName());
		}

		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "public";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "protected";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "private";
		} else {
			level = "default";
		}

		System.out.println("Method " + level + "	" + returnType + "	" + name + "	" + sTypes.toString());
		IMethod method = new Method();

		return toDecorate;
	}

}
