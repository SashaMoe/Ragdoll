package ragdoll.code.visitor.impl;

import java.util.List;

import ragdoll.code.sd.impl.INode;
import ragdoll.code.visitor.api.AOutputStream;
import ragdoll.code.visitor.api.ISDVisitor;
import ragdoll.util.Utilities;

public class SDOutputStream extends AOutputStream implements ISDVisitor {
	
	public void visit(INode node) {
		String methodName = node.getMethodName();
		
		appendBufferLine(Utilities.getSDInstanceName(node.getCallerNode().getClassName()) + ":"+Utilities.getSDName(node.getReturnType())+"="
				+ Utilities.getSDInstanceName(node.getClassName()) + "." + methodName + "("
				+ Utilities.getSDName(Utilities.getParamString(node.getParamTypes())) + ")");
	}

	public void visit(List<String> classes) {
		for (String c : classes) {
			appendBufferLine(Utilities.getSDInstanceName(c) + ":" + c + "[a]");
		}
	}

}
