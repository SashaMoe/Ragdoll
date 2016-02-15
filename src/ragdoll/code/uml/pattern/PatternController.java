package ragdoll.code.uml.pattern;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternController {
	private Map<String, Class<? extends APatternDetector>> patternDetectorClasses;
	private Map<String, APatternDetector> patternDetectors;
	private Map<String, List<Pattern>> patternMap;
	private List<IFormatConsumer> consumers;

	public PatternController() {
		this.patternDetectors = new HashMap<>();
		this.patternDetectorClasses = new HashMap<>();
		this.patternMap = new HashMap<>();
		this.consumers = new ArrayList<>();
	}

	public Map<String, List<Pattern>> getPatterMap() {
		return patternMap;
	}

	public void registerPatternDetector(String patternType, APatternDetector pattern) {
		this.patternDetectors.put(patternType, pattern);
	}

	public void registerFormatConsumer(IFormatConsumer consumer) {
		this.consumers.add(consumer);
	}

	public void detectAllPatterns() {
		for (String patternType : patternDetectorClasses.keySet()) {
			Class<? extends APatternDetector> patternDetector = patternDetectorClasses.get(patternType);
			Constructor<?> ctor = patternDetector.getConstructors()[0];
			try {
				APatternDetector tempDetector = (APatternDetector) ctor.newInstance(this);
				patternDetectors.put(patternType, tempDetector);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (String patternType : patternDetectors.keySet()) {
			APatternDetector patternDetector = patternDetectors.get(patternType);
			patternMap.put(patternType, patternDetector.getPatterns());
		}
		for (IFormatConsumer consumer : consumers) {
			consumer.parse(patternMap);
		}
	}

}
