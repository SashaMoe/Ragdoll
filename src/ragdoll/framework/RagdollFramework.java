package ragdoll.framework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ragdoll.util.Utilities;

public class RagdollFramework {
	private Map<String, IPhase> phaseHashMap;
	private List<String> phaseExecutionList;
	
	public RagdollFramework() {
		phaseHashMap = new HashMap<>();
	}

	public void addPhase(String phaseName, IPhase phase) {
		phaseHashMap.put(phaseName, phase);
	}
	
	public void setPhaseExecutionList(List<String> phaseExecutionList) {
		this.phaseExecutionList = phaseExecutionList;
	}
	
	public void executePhases() {
		for (String phaseName : phaseExecutionList) {
			Utilities.printVerbose("Executing " + phaseName + " ...");
			try {
				phaseHashMap.get(phaseName).execute();
			} catch (Exception e) {
				System.out.println("ERROR " + phaseName);
				e.printStackTrace();
			}
		}
	}
}
