package ragdoll.asm.sd;

import java.util.ArrayList;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import ragdoll.asm.uml.ClassMethodVisitor;
import ragdoll.code.sd.api.Node;
import ragdoll.util.Utilities;

public class GraphMethodVisitor extends ClassVisitor {
	private Node node;
	
	public GraphMethodVisitor(int arg0) {
		super(arg0);
	}

	public GraphMethodVisitor(int arg0, ClassVisitor arg1, Node node) {
		super(arg0, arg1);
		this.node = node;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodVisitor instMv = new MethodVisitor(Opcodes.ASM5, toDecorate) {
			@Override
			public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
				// NOTE: Here, we check if name is <init>, not owner is <init>.
				// Therefore, we are checking the case in the code where `new` keyword is presented.
				// We are NOT only checking the constructor method.
				if (name.equals("<init>")) {
//					.addUse(Utilities.packagifyClassName(owner));
				}
			}
		};
		
		String returnType = Type.getReturnType(desc).getClassName();
		Type[] argTypesArray = Type.getArgumentTypes(desc);
		ArrayList<Type> argTypes = new ArrayList<Type>();
		for(Type type: argTypesArray){
			argTypes.add(type);
		}
		
		System.out.println(argTypes.get(0));
		
//		Edge edge = new Edge(toClass, name, returnType, argTypes);
		
		return null;
	}

}
