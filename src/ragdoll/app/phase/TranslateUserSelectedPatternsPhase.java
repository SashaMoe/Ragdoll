package ragdoll.app.phase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.pattern.Pattern;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.framework.IPhase;

public class TranslateUserSelectedPatternsPhase implements IPhase {

	private PatternInfo patternInfo;
	private IClassInfo classInfo;
	
	public TranslateUserSelectedPatternsPhase() {
		this.patternInfo = PatternInfo.getInstance();
		this.classInfo = ClassInfo.getInstance();
	}
	
	@Override
	public void execute() throws Exception {
		// TODO: This is just for tests! Remove them after GUI is finished!
		patternInfo.addSelectedClasses("decorator", "java.io.FilterOutputStream");
//		patternInfo.addSelectedClasses("decorator", "ragdoll.asm.uml.test.sample.decorator.DecryptionInputStream");
		patternInfo.addSelectedClasses("singleton", "ragdoll.asm.uml.test.sample.decorator.DecryptionInputStream");
		
		Map<String, IClass> iClasses = new HashMap<>();
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
								putClassToClasses(iClasses, className);
							}
						}
					}
				}
			}
		}
		classInfo.setClasses(iClasses);
	}
	
	private void putClassToClasses(Map<String, IClass> iClasses, String className) {
		iClasses.put(className, classInfo.getClassByName(className));
	}

}
