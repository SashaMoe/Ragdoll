package ragdoll.code.uml.pattern;

import java.util.ArrayList;
import java.util.HashMap;

public class NodeAttrinute {
	private String bgColor;
	private String borderColor;
	private ArrayList<String> patternNames;
	private HashMap<String, String> associationArrowTextMap;
	
	public NodeAttrinute(){
		this.bgColor = "white";
		this.borderColor = "black";
		this.patternNames = new ArrayList<>();
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

	public ArrayList<String> addPatternName() {
		return this.patternNames;
	}

	public void addPatternName(String patternName) {
		this.patternNames.add(patternName);
	}

	public ArrayList<String> getPatternNames() {
		return patternNames;
	}
	
}
