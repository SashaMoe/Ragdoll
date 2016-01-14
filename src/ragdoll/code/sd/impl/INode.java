package ragdoll.code.sd.impl;

import java.util.ArrayList;

public interface INode {
	public void addAdjacentNode(INode node);

	public String getClassName();

	public ArrayList<INode> getAdjacencyList();
}
