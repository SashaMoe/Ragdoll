package ragdoll.code.uml.pattern;

import java.util.ArrayList;
import java.util.List;

public abstract class APatternDetector {
	protected IClassInfo classInfo;
	protected List<Pattern> patterns;
	
	protected APatternDetector(IClassInfo classInfo) {
		this.classInfo = classInfo;
		this.patterns = new ArrayList<>();
	}
	protected void detectPattern() {}
	protected void addPattern(Pattern pattern) {
		patterns.add(pattern);
	}
	protected List<Pattern> getPatterns() {
		return patterns;
	}
	
}
