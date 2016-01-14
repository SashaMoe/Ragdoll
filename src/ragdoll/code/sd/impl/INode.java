package ragdoll.code.sd.impl;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Type;

public interface INode {
	public void addAdjacentNode(INode node);

	public String getClassName();

	public ArrayList<INode> getAdjacencyList();

	public void setParamTypes(List<String> paramTypes);

	public String getMethodName();

	public void setMethodName(String methodName);

	public List<String> getParamTypes();

	public int getDepth();

	public void setDepth(int depth);
	
	public String getReturnType();
	
	public void setReturnType(String type);
}
