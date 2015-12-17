package ragdoll.code.visitor.impl;

import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;
import ragdoll.code.visitor.api.IVisitor;

public class GVOutputStream implements IVisitor {

	private StringBuffer sb;

	public GVOutputStream() {
		this.sb = new StringBuffer();
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
		this.sb.append(s + "\n");
	}

	public void visit(IClass c) {
		appendBufferLine('"' + c.getName() + '"' + " [");
		this.sb.append("label = <{");
	}

	public void postVisit(IClass c) {
		appendBufferLine("}>");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");

		IClassDeclaration cd = c.getDeclaration();
		for (String interfaceName : cd.getNameOfInterfaces()) {
			appendBufferLine('"' + packagifyClassName(cd.getClassName()) + '"' + " -> " + '"'
					+ packagifyClassName(interfaceName) + '"');
		}

		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");

		if (!packagifyClassName(cd.getNameOfSuperClass()).equals("java.lang.Object")) {
			appendBufferLine('"' + packagifyClassName(cd.getClassName()) + '"' + " -> " + '"'
					+ packagifyClassName(cd.getNameOfSuperClass()) + '"');
		}

	}

	public void visit(IField f) {
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
		this.sb.append(accessModifier + " " + f.getFieldName() + " : " + f.getType() + "<br/>");
	}

	public void visit(String s) {
		this.sb.append(s);
	}

	public void visit(IMethod m) {
		if (m.getMethodName().contains("<")) {
			return;
		}
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
		this.sb.append(accessModifier + " " + m.getMethodName());
		this.sb.append("(");
		int pCount = 0;
		for (String pType : m.getParamTypes()) {
			String pTypeName = getLastPartOfType(pType);
			this.sb.append(String.valueOf(pTypeName.toLowerCase().charAt(0)) + pCount + ": ");
			this.sb.append(pTypeName);
			pCount++;
		}
		String rTypeName = getLastPartOfType(m.getReturnType());
		this.sb.append("): " + rTypeName + "<br/>");
	}

	public void visit(IClassDeclaration cd) {
		if (cd.isInterface()) {
			sb.append("«interface»<br/>");

		} else if (cd.isAbstract()) {
			sb.append("«abstract»<br/>");
		}
		sb.append(packagifyClassName(cd.getClassName()));
		if (!cd.isInterface()) {
			sb.append("|");
		}
	}

	private String getLastPartOfType(String type) {
		String[] typeParts = type.split("\\.");
		return typeParts[typeParts.length - 1];
	}

	private String packagifyClassName(String className) {
		return className.replaceAll("[/]", ".");
	}

	@Override
	public String toString() {
		return this.sb.toString();
	}
}
