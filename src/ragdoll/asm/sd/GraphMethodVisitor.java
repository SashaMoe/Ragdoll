package ragdoll.asm.sd;

import java.util.ArrayList;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import ragdoll.asm.uml.ClassMethodVisitor;
import ragdoll.code.sd.api.Node;
import ragdoll.code.sd.impl.INode;
import ragdoll.util.Utilities;

public class GraphMethodVisitor extends ClassVisitor {
	private INode node;
	
	public GraphMethodVisitor(int arg0, INode currentMethod) {
		super(arg0);
		this.node = currentMethod;
	}

	public GraphMethodVisitor(int arg0, ClassVisitor arg1, INode node) {
		super(arg0, arg1);
		this.node = node;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodVisitor instMv = new MethodVisitor(Opcodes.ASM5, toDecorate) {
			@Override
			public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
				// Get class name, method name, return type and parameter
				String className = owner;
				String methodName = name;
				String returnType = Type.getReturnType(desc).getClassName();
				Type[] argTypesArray = Type.getArgumentTypes(desc);
				ArrayList<String> argTypes = new ArrayList<String>();
				for(Type type: argTypesArray){
					argTypes.add(type.getClassName());
				}
				// Create a new Node
				Node newNode = new Node(className);
				newNode.setMethodName(methodName);
				newNode.setReturnType(returnType);
				newNode.setParamTypes(argTypes);
				
				// Add the node to current method's adjacency list
				node.addAdjacentNode(node);
				
			}
		};
		
		// Get return type and param types
		String returnType = Type.getReturnType(desc).getClassName();
		Type[] argTypesArray = Type.getArgumentTypes(desc);
		ArrayList<String> argTypes = new ArrayList<String>();
		for(Type type: argTypesArray){
			argTypes.add(type.getClassName());
		}

		// Check if it matches the current method
		if(node.getParamTypes().toString().equals(argTypes.toString()) && node.getReturnType().equals(returnType)){
			// If yes, update the return type of current method
			node.setReturnType(returnType);
		}
		
		
		
		
		System.out.println(argTypes.get(0));
		
//		Edge edge = new Edge(toClass, name, returnType, argTypes);
		
		return instMv;
	}

}
