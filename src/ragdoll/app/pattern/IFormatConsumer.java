package ragdoll.app.pattern;

import java.util.List;
import java.util.Map;

import ragdoll.code.uml.pattern.Pattern;

public interface IFormatConsumer {
	public void parse(Map<String, List<Pattern>> patternMap);
}
