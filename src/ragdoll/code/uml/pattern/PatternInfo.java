package ragdoll.code.uml.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternInfo {
	private volatile static PatternInfo instance;
	
	private Map<String, List<Pattern>> patternMap;
	private Map<String, List<String>> selectedClasses;

	private PatternInfo() {
		init();
	}
	
	public void init() {
		this.patternMap = new HashMap<>();
		this.selectedClasses = new HashMap<>();
	}

	public static PatternInfo getInstance() {
		if (instance == null) {
			synchronized (PatternInfo.class) {
				if (instance == null) {
					instance = new PatternInfo();
				}
			}
		}
		return instance;
	}

	public boolean isPatternSelected(String patternType, Pattern pattern) {
		String patternName = pattern.getPatternName();
		return selectedClasses.containsKey(patternType) &&
			selectedClasses.get(patternType).contains(patternName);
	}
	
	public void addSelectedClasses(String patternType, String selectedClass) {
		if (!this.selectedClasses.containsKey(patternType)) {
			this.selectedClasses.put(patternType, new ArrayList<>());
		}
		this.selectedClasses.get(patternType).add(selectedClass);
	}

	public Map<String, List<String>> getSelectedClasses() {
		return selectedClasses;
	}

	public Map<String, List<Pattern>> getPatterMap() {
		return patternMap;
	}
	
	public void storePatternInfo(String patternType, List<Pattern> patterns){
		patternMap.put(patternType, patterns);
	}

}
