package ragdoll.code.visitor.impl;

import java.util.Set;

import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.uml.api.IField;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.visitor.api.AOutputStream;
import ragdoll.code.visitor.api.IUMLVisitor;
import ragdoll.util.Utilities;

public class GVOutputStream extends AOutputStream implements IUMLVisitor {

	@Override
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

	@Override
	public void endBuffer() {
		appendBufferLine("}");
	}

	public void visit(IClass c) {
		appendBufferLine('"' + c.getName() + '"' + " [");
		appendBufferLine("color=" + (c.getDeclaration().isSingleton() ? "blue" : "black"));
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
		this.sb.append(Utilities.getParamString(m.getParamTypes()));
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
		if (cd.isSingleton()) {
			sb.append("\n«singleton»\\n");
		}
		if (!cd.isInterface()) {
			sb.append("|");
		}
	}

}
