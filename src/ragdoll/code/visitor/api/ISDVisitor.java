package ragdoll.code.visitor.api;

import java.util.List;

import ragdoll.code.sd.api.INode;

public interface ISDVisitor extends IVisitor {
	void visit(List<String> classes);
	void visit(INode node);
}
