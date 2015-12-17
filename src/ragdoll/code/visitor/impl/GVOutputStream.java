package ragdoll.code.visitor.impl;

import java.util.HashMap;
import java.util.List;

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
		appendBufferLine(c.getName() + " [");
		this.buffer.append("label = \"{" + c.getName() + "|");
	}

	public void postVisit(IClass c) {
		appendBufferLine("}\"");
		appendBufferLine("]");
	}

	public void visit(HashMap<String, IField> fs) {
		for (String fieldName : fs.keySet()) {
			IField f = fs.get(fieldName);
			String accessLevel = f.getAccessLevel();
			char accessModifier = '\0';
			if (accessLevel.equals("public")) {
				accessModifier = '+';
			} else if (accessLevel.equals("private")) {
				accessModifier = '-';
			} else if (accessLevel.equals("protected")) {
				accessModifier = '#';
			} else if (accessLevel.equals("default")) {
				accessModifier = '~';
			}
			this.buffer.append(accessModifier + " " + f.getFieldName() + " : " + f.getType() + "\\l");
		}
		this.buffer.append("|");
	}

	public void visit(List<IMethod> ms) {
		for (IMethod m : ms) {
			String accessLevel = m.getAccessLevel();
			char accessModifier = '\0';
			if (accessLevel.equals("public")) {
				accessModifier = '+';
			} else if (accessLevel.equals("private")) {
				accessModifier = '-';
			} else if (accessLevel.equals("protected")) {
				accessModifier = '#';
			} else if (accessLevel.equals("default")) {
				accessModifier = '~';
			}
			this.buffer.append(accessModifier + " " + m.getMethodName());
			this.buffer.append("(");
			int pCount = 0;
			for (String pType : m.getParamTypes()) {
				String pTypeName = getLastPartOfType(pType);
				this.buffer.append(pTypeName.toLowerCase().charAt(0) + pCount + ": ");
				this.buffer.append(pTypeName);
				pCount++;
			}
			String rTypeName = getLastPartOfType(m.getReturnType());
			this.buffer.append("): " + rTypeName + "\\l");
		}
	}
	
	private String getLastPartOfType(String type) {
		String[] typeParts = type.split(".");
		return typeParts[typeParts.length - 1];
	}

	public void visit(IClassDeclaration cd) {
		// TODO Auto-generated method stub

	}
}
