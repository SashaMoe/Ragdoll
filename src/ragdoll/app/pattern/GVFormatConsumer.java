package ragdoll.app.pattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.pattern.IFormatConsumer;
import ragdoll.code.uml.pattern.NodeAttribute;
import ragdoll.code.uml.pattern.Pattern;
import ragdoll.code.uml.pattern.PatternInfo;

public class GVFormatConsumer implements IFormatConsumer {
	private volatile static GVFormatConsumer instance;

	private static final String YIMA_RED = "#A32B2E";

	private Map<String, NodeAttribute> nodeAttributeMap;

	private GVFormatConsumer() {
		init();
	}
	
	public void init() {
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
					if (!PatternInfo.getInstance().isPatternSelected(patternType, pattern)) {
						continue;
					}
					String className = pattern.getRoleMap().keySet().iterator().next();
					NodeAttribute node = getClassNodeAttribute(className);
					node.setBorderColor("blue");
					node.addPatternName("«singleton»");
				}
			} else if (patternType.toLowerCase().equals("adapter")) {
				setNodeAttribute(patternType, patterns, YIMA_RED);
			} else if (patternType.toLowerCase().equals("decorator")) {
				setNodeAttribute(patternType, patterns, "green");
			} else if (patternType.toLowerCase().equals("composite")) {
				setNodeAttribute(patternType, patterns, "yellow");
			}
		}
	}

	private void setNodeAttribute(String patternType, List<Pattern> patterns, String patternColor) {
		for (Pattern pattern : patterns) {
			if (!PatternInfo.getInstance().isPatternSelected(patternType, pattern)) {
				continue;
			}
			for (String className : pattern.getRoleMap().keySet()) {
				String role = pattern.getRoleMap().get(className);
				NodeAttribute node = getClassNodeAttribute(className);
				node.setBgColor(patternColor);
				node.addPatternName("«" + role + "»");
			}
			try {
				List<String> fromTo = pattern.getRelationMap().keySet().iterator().next();
				String relation = pattern.getRelationMap().get(fromTo);
				String fromClass = fromTo.get(0);
				String toClass = fromTo.get(1);
				if (ClassInfo.getInstance().containsClass(toClass)) {
					NodeAttribute node = getClassNodeAttribute(fromClass);
					node.addAssociationArrowText(toClass, relation);
				}
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
