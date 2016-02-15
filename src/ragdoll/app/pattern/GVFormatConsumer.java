package ragdoll.app.pattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ragdoll.code.uml.pattern.IFormatConsumer;
import ragdoll.code.uml.pattern.NodeAttribute;
import ragdoll.code.uml.pattern.Pattern;

public class GVFormatConsumer implements IFormatConsumer {
	private volatile static GVFormatConsumer instance;

	private static final String YIMA_RED = "#A32B2E";

	private Map<String, NodeAttribute> nodeAttributeMap;

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
			if (patternType.toLowerCase().equals("singleton")) {
				for (Pattern pattern : patterns) {
					String className = pattern.getRoleMap().keySet().iterator().next();
					NodeAttribute node = getClassNodeAttribute(className);
					node.setBorderColor("blue");
					node.addPatternName("«singleton»");
				}
			} else if (patternType.toLowerCase().equals("adapter")) {
				setNodeAttribute(patterns, YIMA_RED);
			} else if (patternType.toLowerCase().equals("decorator")) {
				setNodeAttribute(patterns, "green");
			} else if (patternType.toLowerCase().equals("composite")) {
				setNodeAttribute(patterns, "yellow");
			}
		}
	}

	private void setNodeAttribute(List<Pattern> patterns, String patternColor) {
		for (Pattern pattern : patterns) {
			for (String className : pattern.getRoleMap().keySet()) {
				String role = pattern.getRoleMap().get(className);
				NodeAttribute node = getClassNodeAttribute(className);
				node.setBgColor(patternColor);
				node.addPatternName("«" + role + "»");
			}
			try {
				List<String> fromTo = pattern.getRelationMap().keySet().iterator().next();
				String relation = pattern.getRelationMap().get(fromTo);
				NodeAttribute node = getClassNodeAttribute(fromTo.get(0));
				node.addAssociationArrowText(fromTo.get(1), relation);
			} catch (Exception e) {

			}
		}
	}

	public NodeAttribute getClassNodeAttribute(String className) {
		if (!nodeAttributeMap.containsKey(className)) {
			nodeAttributeMap.put(className, new NodeAttribute());
		}
		return nodeAttributeMap.get(className);
	}

}
