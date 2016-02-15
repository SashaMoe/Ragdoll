package ragdoll.code.uml.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternInfo {
	private Map<String, List<Pattern>> patternMap;
	private List<IFormatConsumer> consumers;

	public PatternInfo() {
		this.patternMap = new HashMap<>();
		this.consumers = new ArrayList<>();
	}

	public Map<String, List<Pattern>> getPatterMap() {
		return patternMap;
	}

	public void registerFormatConsumer(IFormatConsumer consumer) {
		this.consumers.add(consumer);
	}

//	public void detectAllPatterns() {
//		for (String patternType : patternDetectors.keySet()) {
//			APatternDetector patternDetector = patternDetectors.get(patternType);
//			patternMap.put(patternType, patternDetector.getPatterns());
//		}
//		for (IFormatConsumer consumer : consumers) {
//			consumer.parse(patternMap);
//		}
//	}
	
	public void storePatternInfo(String patternType, List<Pattern> patterns){
		patternMap.put(patternType, patterns);
	}

}
