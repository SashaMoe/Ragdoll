package ragdoll.code.uml.pattern;

public class NodeAttrinute {
	private String bgColor;
	private String borderColor;
	private String patternName;
	
	public NodeAttrinute(){
		this.bgColor = "white";
		this.borderColor = "black";
		this.patternName = "";
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

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}
	
	
	
}
