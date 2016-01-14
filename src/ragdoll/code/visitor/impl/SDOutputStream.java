package ragdoll.code.visitor.impl;

import java.util.List;

import ragdoll.code.sd.impl.INode;
import ragdoll.code.visitor.api.AOutputStream;
import ragdoll.code.visitor.api.ISDVisitor;
import ragdoll.util.Utilities;

public class SDOutputStream extends AOutputStream implements ISDVisitor {
	private INode startMethod;

	public SDOutputStream(INode startMethod) {
		this.startMethod = startMethod;
	}

	public void visit(INode node) {
		appendBufferLine(Utilities.getSDInstanceName(node.getCallerNode().getClassName()) + ":"
				+ Utilities.getSDInstanceName(node.getClassName()) + "." + node.getMethodName() + "("
				+ Utilities.getParamString(node.getParamTypes()) + ")");
	}

	public void visit(List<String> classes) {
		for (String c : classes) {
			if (c.equals(this.startMethod.getClassName()) || this.startMethod.getParamTypes().contains(c)) {
				appendBufferLine(Utilities.getSDInstanceName(c) + ":" + c + "[a]");
			} else {
				appendBufferLine("/" + Utilities.getSDInstanceName(c) + ":" + c + "[a]");
			}
		}
	}

}
