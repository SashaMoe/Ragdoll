package ragdoll.code.uml.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class NodeAttrinute {
	private String bgColor;
	private String borderColor;
	private HashSet<String> patternNames;
	private HashMap<String, String> associationArrowTextMap;
	
	public NodeAttrinute(){
		this.bgColor = "white";
		this.borderColor = "black";
		this.patternNames = new HashSet<>();
		this.associationArrowTextMap = new HashMap<>();
	}
	
	public HashMap<String, String> getAssociationArrowText() {
		return associationArrowTextMap;
	}

	public void addAssociationArrowText(String targetClassName, String associationArrowText) {
		this.associationArrowTextMap.put(targetClassName, associationArrowText);
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public void addPatternName(String patternName) {
		this.patternNames.add(patternName);
	}

	public Set<String> getPatternNames() {
		return patternNames;
	}
	
}
