package ragdoll.framework;

public class FrameworkStatus {
	private String currentPhaseName;
	private int currentPhaseNumber;
	private int totalPhaseNumber;
	
	public FrameworkStatus(String currentPhaseName, int currentPhaseNumber, int totalPhaseNumber) {
		this.currentPhaseName = currentPhaseName;
		this.currentPhaseNumber = currentPhaseNumber;
		this.totalPhaseNumber = totalPhaseNumber;
	}

	public String getCurrentPhaseName() {
		return currentPhaseName;
	}

	public int getCurrentPhaseNumber() {
		return currentPhaseNumber;
	}

	public int getTotalPhaseNumber() {
		return totalPhaseNumber;
	}

}
