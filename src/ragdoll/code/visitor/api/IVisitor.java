package ragdoll.code.visitor.api;

import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;

public interface IVisitor {
	void visit(IClass c);
	void visit(IField f);
	void visit(IMethod m);
	void visit(IClassDeclaration cd);
}
