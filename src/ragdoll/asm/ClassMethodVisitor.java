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
import ragdoll.util.Utilities;

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
		IMethod method = new Method(name, level, returnType, sTypes, exceptionList);
		this.c.addMethod(method);

		MethodVisitor oriMv = new MethodVisitor(Opcodes.ASM5) {
		};
		MethodVisitor instMv = new MethodVisitor(Opcodes.ASM5, oriMv) {
			@Override
			public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
				if (name.equals("<init>")) {
					ClassMethodVisitor.this.c.addUse(Utilities.packagifyClassName(owner));
				}
			}

			@Override
			public void visitFieldInsn(int opcode, String owner, String name, String desc) {
				if (Opcodes.PUTFIELD != opcode) {
					return;
				}
				ClassMethodVisitor.this.c.addAssociationField(name);
				
			}
		};
		return instMv;
	}

}
