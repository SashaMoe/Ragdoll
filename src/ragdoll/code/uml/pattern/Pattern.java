package ragdoll.code.uml.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pattern {
	private HashMap<String, String> roleMap;
	private HashMap<List<String>, String> relationMap;
	private String patternName;
	
	public Pattern(String patternName) {
		super();
		this.patternName = patternName;
		this.roleMap = new HashMap<>();
		this.relationMap = new HashMap<>();
	}
	
	public void addRole(String className, String roleName) {
		this.roleMap.put(className, roleName);
	}
	
	public void addRelation(String fromClassName, String toClassName, String relation) {
		List<String> fromToList = new ArrayList<>();
		fromToList.add(fromClassName);
		fromToList.add(toClassName);
		this.relationMap.put(fromToList, relation);
	}

	public HashMap<String, String> getRoleMap() {
		return roleMap;
	}

	public HashMap<List<String>, String> getRelationMap() {
		return relationMap;
	}

	public String getPatternName() {
		return patternName;
	}
	
}
