package ragdoll.app.phase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.sd.GraphMethodVisitor;
import ragdoll.code.sd.api.INode;
import ragdoll.code.sd.impl.Node;
import ragdoll.code.sd.impl.SDInfo;
import ragdoll.framework.IPhase;
import ragdoll.framework.RagdollProperties;
import ragdoll.util.Utilities;

public class SDAnalyzePhase implements IPhase {

	@Override
	public void execute() throws Exception {
		SDInfo sdInfo = SDInfo.getInstance();
		RagdollProperties properties = RagdollProperties.getInstance();
		int maxDepth = Integer.valueOf(properties.getProperty("SDEdit-Max-Depth", "3"));
		String fullyQualifiedMethodName = properties.getProperty("Input");

		String className = Utilities.getClassNameFromFullyQualifiedMethodSignature(fullyQualifiedMethodName);
		String methodName = Utilities.getMethodNameFromFullyQualifiedMethodSignature(fullyQualifiedMethodName);
		List<String> paramTypes = Utilities.getParamTypesFromFullyQualifiedMethodSignature(fullyQualifiedMethodName);

		ClassReader reader;
		List<String> classes = new ArrayList<>();
		INode startMethod = new Node(className);
		startMethod.setMethodName(methodName);
		startMethod.setParamTypes(paramTypes);
		startMethod.setDepth(0);
		Queue<INode> methodQueue = new LinkedList<INode>();
		methodQueue.add(startMethod);

		while (!methodQueue.isEmpty()) {
			INode currentMethod = methodQueue.poll();
			if (!classes.contains(currentMethod.getClassName())) {
				classes.add(currentMethod.getClassName());
			}
			if (currentMethod.getDepth() < maxDepth) {
				reader = new ClassReader(currentMethod.getClassName());
				ClassVisitor graphMethodVisitor = new GraphMethodVisitor(Opcodes.ASM5, currentMethod);
				reader.accept(graphMethodVisitor, ClassReader.EXPAND_FRAMES);
				for (INode n : currentMethod.getAdjacencyList()) {
					n.setDepth(currentMethod.getDepth() + 1);
					methodQueue.add(n);
				}
			}
		}
		sdInfo.setClasses(classes);
		sdInfo.setStartMethod(startMethod);
	}

}
