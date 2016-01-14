package ragdoll.code.sd.api;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Type;

import ragdoll.code.sd.impl.INode;

public class Node implements INode {
	private String className;
	private String methodName;
	private List<String> paramTypes;
	private ArrayList<INode> adjacencyList;
	private int depth;
	private String returnType;

	public Node(String className) {
		this.className = className;
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

	public String getReturnType(){
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
}

