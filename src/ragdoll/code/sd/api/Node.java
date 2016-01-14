package ragdoll.code.sd.api;

import java.util.ArrayList;
import java.util.List;

import ragdoll.code.sd.impl.INode;

public class Node implements INode {
	private String className;
	private String methodName;
	private List<String> paramTypes;
	private ArrayList<INode> adjacencyList;

	public Node(String className) {
		this.className = className;
		this.adjacencyList = new ArrayList<>();
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