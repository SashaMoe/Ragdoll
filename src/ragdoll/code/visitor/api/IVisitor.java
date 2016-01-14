package ragdoll.code.visitor.api;

import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.uml.api.IField;
import ragdoll.code.uml.api.IMethod;

public interface IVisitor {
	void visit(IClass c);
	void postVisit(IClass c);
	void visit(IField f);
	void visit(String s);
	void visit(IMethod m);
	void visit(IClassDeclaration cd);
}
