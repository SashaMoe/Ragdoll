package ragdoll.code.visitor.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.uml.api.IField;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.visitor.api.IVisitor;
import ragdoll.util.Utilities;

public class GVOutputStream implements IVisitor {

	private StringBuffer sb;

	public GVOutputStream() {
		this.sb = new StringBuffer();
	}

	public void initBuffer() {
		appendBufferLine("digraph G {");
		appendBufferLine("rankdir=BT;");
		appendBufferLine("fontname = \"Times New Roman\"");
		appendBufferLine("fontsize = 12");

		appendBufferLine("node [");
		appendBufferLine("fontname = \"Times New Roman\"");
		appendBufferLine("fontsize = 12");
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
		// this.sb.append("label = <{");
		this.sb.append("label = \"{");
	}

	public void postVisit(IClass c) {
		appendBufferLine("}\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");

		IClassDeclaration cd = c.getDeclaration();
		for (String interfaceName : cd.getNameOfInterfaces()) {
			appendBufferLine('"' + Utilities.packagifyClassName(cd.getClassName()) + '"' + " -> " + '"'
					+ Utilities.packagifyClassName(interfaceName) + '"');
		}

		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");

		if (!Utilities.packagifyClassName(cd.getNameOfSuperClass()).equals("java.lang.Object")) {
			appendBufferLine('"' + Utilities.packagifyClassName(cd.getClassName()) + '"' + " -> " + '"'
					+ Utilities.packagifyClassName(cd.getNameOfSuperClass()) + '"');
		}

		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("]");
		Set<String> useList = c.getUseSet();
		for (String usedClass : useList) {
			if (!Utilities.packagifyClassName(usedClass).startsWith("java.")) {
				appendBufferLine('"' + Utilities.packagifyClassName(cd.getClassName()) + '"' + " -> " + '"'
						+ Utilities.packagifyClassName(usedClass) + '"');
			}
		}

		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("]");
		for (String type : c.getAssociationType()) {
			appendBufferLine('"' + Utilities.packagifyClassName(cd.getClassName()) + '"' + " -> " + '"' + type + '"');
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
		this.sb.append(accessModifier + " " + f.getFieldName() + " : " + f.getType() + "\\l");
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
			String pTypeName = Utilities.getLastPartOfType(pType);
			this.sb.append(String.valueOf(pTypeName.toLowerCase().charAt(0)) + pCount + ": ");
			this.sb.append(pTypeName);
			pCount++;
		}
		String rTypeName = Utilities.getLastPartOfType(m.getReturnType());
		this.sb.append("): " + rTypeName + "\\l");
	}

	public void visit(IClassDeclaration cd) {
		if (cd.isInterface()) {
			sb.append("«interface»\\n");

		} else if (cd.isAbstract()) {
			sb.append("«abstract»\\n");
		}
		sb.append(Utilities.packagifyClassName(cd.getClassName()));
		if (!cd.isInterface()) {
			sb.append("|");
		}
	}

	@Override
	public String toString() {
		return this.sb.toString();
	}
}
