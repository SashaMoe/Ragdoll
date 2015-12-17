package ragdoll.asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import ragdoll.code.api.IClass;
import ragdoll.code.api.IMethod;
import ragdoll.code.impl.Method;

public class ClassMethodVisitor extends ClassVisitor {

	private IClass c;

	public ClassMethodVisitor(int arg0, IClass c) {
		super(arg0);
		this.c = c;
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, IClass c) {
		super(arg0, arg1);
		this.c = c;
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

		List<String> exceptionList = exceptions == null ? new ArrayList<>()
				: new ArrayList<>(Arrays.asList(exceptions));
		System.out.println("Method " + level + "	" + returnType + "	" + name + "	" + sTypes.toString());
		IMethod method = new Method(name, level, returnType, sTypes, exceptionList);
		this.c.addMethod(method);
		return toDecorate;
	}

}
