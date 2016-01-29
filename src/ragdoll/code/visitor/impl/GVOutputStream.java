package ragdoll.code.visitor.impl;

import java.util.Set;

import ragdoll.app.pattern.GVFormatConsumer;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.uml.api.IField;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.pattern.NodeAttribute;
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
		GVFormatConsumer consumer = GVFormatConsumer.getInstance();
		NodeAttribute nodeAttrinute = consumer.getClassNodeAttribute(c.getName());
		
		appendBufferLine('"' + c.getName() + '"' + " [");
		appendBufferLine("color=" + nodeAttrinute.getBorderColor());
		appendBufferLine("fillcolor=\"" + nodeAttrinute.getBgColor() + "\"");
		appendBufferLine("style=filled");
		this.sb.append("label = \"{");
	}

	public void postVisit(IClass c) {
		GVFormatConsumer consumer = GVFormatConsumer.getInstance();
		NodeAttribute nodeAttrinute = consumer.getClassNodeAttribute(c.getName());
		
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

		for (String type : c.getAssociationType()) {
			appendBufferLine("edge [");
			appendBufferLine("style = \"solid\"");
			appendBufferLine("arrowhead = \"vee\"");
			if (nodeAttrinute.getAssociationArrowText().containsKey(type)) {
				appendBufferLine("label = \"" + nodeAttrinute.getAssociationArrowText().get(type) + "\"");
			} else {
				appendBufferLine("label = \" \"");
			}
			appendBufferLine("]");
			appendBufferLine('"' + Utilities.packagifyClassName(cd.getClassName()) + '"' + " -> " + '"' + type + '"');
		}
		for (String targetClass : nodeAttrinute.getAssociationArrowText().keySet()) {
			if (!c.getAssociationType().contains(targetClass)) {
				appendBufferLine("edge [");
				appendBufferLine("style = \"solid\"");
				appendBufferLine("arrowhead = \"vee\"");
				appendBufferLine("label = \"" + nodeAttrinute.getAssociationArrowText().get(targetClass) + "\"");
				appendBufferLine("]");
				appendBufferLine('"' + Utilities.packagifyClassName(cd.getClassName()) + '"' + " -> " + '"' + targetClass + '"');
			}
		}
		appendBufferLine("edge [label=\" \"]");
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
		GVFormatConsumer consumer = GVFormatConsumer.getInstance();
		NodeAttribute nodeAttrinute = consumer.getClassNodeAttribute(Utilities.packagifyClassName(cd.getClassName()));
		
		if (cd.isInterface()) {
			this.sb.append("«interface»\\n");

		} else if (cd.isAbstract()) {
			this.sb.append("«abstract»\\n");
		}
		sb.append(Utilities.packagifyClassName(cd.getClassName()));
		
		if (!nodeAttrinute.getPatternNames().isEmpty()) {
			for (String patternName : nodeAttrinute.getPatternNames()) {
				this.sb.append("\n" + patternName + "\\n");
			}
		}
		
		if (!cd.isInterface()) {
			sb.append("|");
		}
	}

}
