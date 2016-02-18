package ragdoll.app.phase;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ragdoll.code.uml.pattern.Pattern;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.framework.IPhase;

public class TranslateUserSelectedPatternsPhase implements IPhase {

	private PatternInfo patternInfo;
	
	public TranslateUserSelectedPatternsPhase() {
		this.patternInfo = PatternInfo.getInstance();
	}
	
	@Override
	public void execute() throws Exception {
		Set<String> involvedClasses = new HashSet<>();
		Map<String, List<String>> selectedClasses = patternInfo.getSelectedClasses();
		Map<String, List<Pattern>> patternMap = patternInfo.getPatterMap();
		
		for (String selectedPatternName: selectedClasses.keySet()) {
			List<String> selectedClassNames = selectedClasses.get(selectedPatternName);
			for (String selectedClassName : selectedClassNames) {
				if (patternMap.containsKey(selectedPatternName)) {
					List<Pattern> patterns = patternMap.get(selectedPatternName);
					for (Pattern pattern : patterns) {
						if (pattern.getPatternName().equals(selectedClassName)) {
							// The pattern is selected by user.
							Map<String, String> roleMap = pattern.getRoleMap();
							for (String className : roleMap.keySet()) {
								involvedClasses.add(className);
							}
						}
					}
				}
			}
		}
		
		patternInfo.setInvolvedClasses(involvedClasses);
	}

}
