package ragdoll.framework;

import java.util.LinkedHashMap;
import java.util.Map;

import ragdoll.util.Utilities;

public class RagdollFramework {
	private static final int MAP_INIT_CAPACITY = 16;
	private static final float MAP_LOAD_FACTOR = 0.75f;
	
	private Map<String, IPhase> phaseLinkedHashMap;
	
	public RagdollFramework() {
		initPhases();
	}
	
	public void initPhases() {
		phaseLinkedHashMap = new LinkedHashMap<>(MAP_INIT_CAPACITY, MAP_LOAD_FACTOR, false);
	}
	
	public void addPhase(String phaseName, IPhase phase) {
		phaseLinkedHashMap.put(phaseName, phase);
	}
	
	public void executePhases() {
		for (String phaseName : phaseLinkedHashMap.keySet()) {
			Utilities.printVerbose("Executing " + phaseName + " ...");
			phaseLinkedHashMap.get(phaseName).execute();
		}
	}
}
