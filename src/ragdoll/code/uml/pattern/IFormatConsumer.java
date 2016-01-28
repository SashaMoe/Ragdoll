package ragdoll.code.uml.pattern;

import java.util.List;
import java.util.Map;

public interface IFormatConsumer {
	public void parse(Map<String, List<Pattern>> patternMap);
}
