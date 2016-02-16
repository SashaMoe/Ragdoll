package ragdoll.code.uml.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternInfo {
	private volatile static PatternInfo instance;
	
	private Map<String, List<Pattern>> patternMap;
	private List<String> selectedClasses;

	private PatternInfo() {
		this.patternMap = new HashMap<>();
		this.selectedClasses = new ArrayList<>();
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
	
	public void setSelectedClasses(List<String> selectedClasses) {
		this.selectedClasses = selectedClasses;
	}

	public List<String> getSelectedClasses() {
		return selectedClasses;
	}

	public Map<String, List<Pattern>> getPatterMap() {
		return patternMap;
	}
	
	public void storePatternInfo(String patternType, List<Pattern> patterns){
		patternMap.put(patternType, patterns);
	}

}
