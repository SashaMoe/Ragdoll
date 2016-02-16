package ragdoll.app.phase;

import java.util.ArrayList;
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
		List<String> ls = new ArrayList<>();
		ls.add("java.io.FilterOutputStream");
		ls.add("ragdoll.asm.uml.test.sample.decorator.EncryptionOutputStream");
		patternInfo.setSelectedClasses(ls);
		
		
		Map<String, IClass> iClasses = new HashMap<>();
		List<String> selectedClasses = patternInfo.getSelectedClasses();
		Map<String, List<Pattern>> patternMap = patternInfo.getPatterMap();
		for (String patternName : patternMap.keySet()) {
			List<Pattern> patterns = patternMap.get(patternName);
			for (Pattern pattern : patterns) {
				String patternInstanceName = pattern.getPatternName();
				if (selectedClasses.contains(patternInstanceName)) {
					// The pattern is selected by user.
					Map<String, String> roleMap = pattern.getRoleMap();
					for (String className : roleMap.keySet()) {
						putClassToClasses(iClasses, className);
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
