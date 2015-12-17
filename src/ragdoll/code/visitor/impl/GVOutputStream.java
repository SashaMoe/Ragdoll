package ragdoll.code.visitor.impl;

import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;
import ragdoll.code.visitor.api.IVisitor;

public class GVOutputStream implements IVisitor {

	private StringBuffer buffer;

	public GVOutputStream() {
		this.buffer = new StringBuffer();
	}

	public void initBuffer() {
		appendBufferLine("digraph G {");
		appendBufferLine("fontname = \"Times New Roman\"");
		appendBufferLine("fontsize = 8");

		appendBufferLine("node [");
		appendBufferLine("fontname = \"Times New Roman\"");
		appendBufferLine("fontsize = 8");
		appendBufferLine("shape = \"record\"");
		appendBufferLine("]");
	}

	public void endBuffer() {
		appendBufferLine("}");
	}

	private void appendBufferLine(String s) {
		this.buffer.append(s + "\n");
	}

	public void visit(IClass c) {
		appendBufferLine(c.getName()+" [");
		this.buffer.append("label = \"{" + c.getName() + "|");
	}
	
	public void postVisit(IClass c) {
		appendBufferLine("}\"");
		appendBufferLine("]");
	}

	public void visit(IField f) {
		// TODO Auto-generated method stub

	}

	public void visit(IMethod m) {
		// TODO Auto-generated method stub

	}

	public void visit(IClassDeclaration cd) {
		// TODO Auto-generated method stub

	}
}
