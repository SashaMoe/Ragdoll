package ragdoll.code.sd.api;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Type;

import ragdoll.code.sd.impl.INode;
import ragdoll.code.visitor.api.ISDVisitor;
import ragdoll.code.visitor.api.IUMLVisitor;
import ragdoll.code.visitor.api.IVisitor;
import ragdoll.util.Utilities;

public class Node implements INode {
	private String className;
	private String methodName;
	private List<String> paramTypes;
	private ArrayList<INode> adjacencyList;
	private int depth;
	private String returnType;
	private INode callerNode;

	public INode getCallerNode() {
		return callerNode;
	}

	public void setCallerNode(INode callerNode) {
		this.callerNode = callerNode;
	}

	public Node(String className) {
		this.className = Utilities.packagifyClassName(className);
		this.adjacencyList = new ArrayList<>();
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setParamTypes(List<String> paramTypes) {
		this.paramTypes = paramTypes;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setReturnType(String type) {
		this.returnType = type;
	}

	public String getReturnType() {
		return this.returnType;
	}

	public List<String> getParamTypes() {
		return paramTypes;
	}

	public void addAdjacentNode(INode node) {
		this.adjacencyList.add(node);
	}

	public String getClassName() {
		return className;
	}

	public ArrayList<INode> getAdjacencyList() {
		return adjacencyList;
	}

	@Override
	public void accept(IVisitor v) {
		for (INode calleeNode : this.adjacencyList) {
			((ISDVisitor) v).visit(calleeNode);
			calleeNode.accept(v);
		}
	}
}
