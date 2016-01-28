package ragdoll.app.pattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ragdoll.code.uml.pattern.IFormatConsumer;
import ragdoll.code.uml.pattern.NodeAttrinute;
import ragdoll.code.uml.pattern.Pattern;

public class GVFormatConsumer implements IFormatConsumer {
	private volatile static GVFormatConsumer instance;

	private Map<String, NodeAttrinute> nodeAttributeMap;
	
	private GVFormatConsumer() {
		this.nodeAttributeMap = new HashMap<>();
	}

	public static GVFormatConsumer getInstance() {
		if (instance == null) {
			synchronized (GVFormatConsumer.class) {
				if (instance == null) {
					instance = new GVFormatConsumer();
				}
			}
		}
		return instance;
	}
	
	public void parse(Map<String, List<Pattern>> patternMap) {
		for (String patternType : patternMap.keySet()) {
			List<Pattern> patterns = patternMap.get(patternType);
			if (patternType.equals("Singleton")) {
				for (Pattern pattern : patterns) {
					String className = pattern.getRoleMap().keySet().iterator().next();
					NodeAttrinute node = getClassNodeAttribute(className);
					node.setBorderColor("blue");
					node.setPatternName("«singleton»");
				}
				
			}
		}
	}
	
	public NodeAttrinute getClassNodeAttribute(String className) {
		if (!nodeAttributeMap.containsKey(className)) {
			nodeAttributeMap.put(className, new NodeAttrinute());
		}
		return nodeAttributeMap.get(className);
	}

}
