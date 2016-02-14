package ragdoll.asm.uml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.api.IMethodCall;
import ragdoll.code.uml.impl.Method;
import ragdoll.code.uml.impl.MethodCall;
import ragdoll.util.Utilities;

public class ClassMethodVisitor extends ClassVisitor {

	private IClass c;
	private IMethod currentMethod;

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
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodVisitor instMv = new MethodVisitor(Opcodes.ASM5, toDecorate) {
			@Override
			public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
				// NOTE: Here, we check if name is <init>, not owner is <init>.
				// Therefore, we are checking the case in the code where `new`
				// keyword is presented.
				// We are NOT only checking the constructor method.
				String className = Utilities.packagifyClassName(owner);
				if (name.equals("<init>")) {
					ClassMethodVisitor.this.c.addUse(className);
				}
				IMethodCall callee = new MethodCall(className, name);
				Type[] paramTypes = Type.getArgumentTypes(desc);
				for(Type paramType : paramTypes){
					callee.addParamTypes(paramType.getClassName());
				}
				ClassMethodVisitor.this.currentMethod.addCallees(callee);
			}
		};
		MethodVisitor getInstanceMv = new MethodVisitor(Opcodes.ASM5, instMv) {
			@Override
			public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
				if (Utilities.packagifyClassName(owner).equals(ClassMethodVisitor.this.c.getName())
						&& name.equals("<init>")) {
					ClassMethodVisitor.this.c.setHasLazyGetInstanceMethod(true);
				}
			}
		};
		MethodVisitor initMv = new MethodVisitor(Opcodes.ASM5, instMv) {
			@Override
			public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
				if (Utilities.packagifyClassName(owner).equals(ClassMethodVisitor.this.c.getName())
						&& name.equals("<init>")) {
					ClassMethodVisitor.this.c.setHasEagerInit(true);
				}
			}
		};

		if (name.contains("$")) {
			return toDecorate;
		}

		String returnType = Type.getReturnType(desc).getClassName();

		Type[] argTypes = Type.getArgumentTypes(desc);
		List<String> sTypes = new ArrayList<>();
		for (Type t : argTypes) {
			sTypes.add(t.getClassName());
			this.c.addUse(t.getClassName());
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
		this.currentMethod = new Method(name, level, returnType, sTypes, exceptionList);
		this.c.addMethod(currentMethod);

		if (name.equals("<clinit>")) {
			return initMv;
		}
		if (Utilities.packagifyClassName(returnType).equals(this.c.getName())) {
			this.c.setHasGetInstanceMethod(true);
			return getInstanceMv;
		}

		return instMv;
	}

}
