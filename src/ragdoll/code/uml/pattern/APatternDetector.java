package ragdoll.code.uml.pattern;

import java.util.ArrayList;
import java.util.List;

import ragdoll.code.uml.api.IClassInfo;

public abstract class APatternDetector {
	protected IClassInfo classInfo;
	protected List<Pattern> patterns;
	
	protected APatternDetector(IClassInfo classInfo) {
		this.classInfo = classInfo;
		this.patterns = new ArrayList<>();
	}
	public void detectPattern() {}
	protected void addPattern(Pattern pattern) {
		patterns.add(pattern);
	}
	public List<Pattern> getPatterns() {
		return patterns;
	}
	
}
