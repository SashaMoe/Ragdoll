package ragdoll.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ragdoll.common.observer.Observer;
import ragdoll.common.observer.Subject;
import ragdoll.util.Utilities;

public class RagdollFramework implements Subject {
	
	private volatile static RagdollFramework instance;
	
	public static RagdollFramework getInstance() {
		if (instance == null) {
			synchronized (RagdollFramework.class) {
				if (instance == null) {
					instance = new RagdollFramework();
				}
			}
		}
		return instance;
	}
	
	private RagdollFramework() {
		init();
	}
	
	private Map<String, IPhase> phaseHashMap;
	private List<String> phaseExecutionList;
	private List<Observer> observers;

	public void init() {
		phaseHashMap = new HashMap<>();
		phaseExecutionList = new ArrayList<>();
		observers = new ArrayList<>();
	}

	public void addPhase(String phaseName, IPhase phase) {
		phaseHashMap.put(phaseName, phase);
	}

	public void setPhaseExecutionList(List<String> phaseExecutionList) {
		this.phaseExecutionList = phaseExecutionList;
	}

	public void executePhases() {
		executePhases(0);
	}

	public void executePhases(int interval) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				executePhasesHelper(interval);
			}
		}, 0);
	}

	public void executePhasesHelper(int interval) {
		int currentPhaseNumber = 1;
		int totalPhaseNumber = phaseExecutionList.size();
		for (String phaseName : phaseExecutionList) {
			Utilities.printVerbose("Executing " + phaseName + " ...");
			notifyObservers(new FrameworkStatus(phaseName, currentPhaseNumber, totalPhaseNumber));
			try {
				phaseHashMap.get(phaseName).execute();
			} catch (Exception e) {
				System.out.println("ERROR " + phaseName);
				e.printStackTrace();
			}
			currentPhaseNumber++;
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyObservers(new FrameworkStatus("Done", currentPhaseNumber, totalPhaseNumber));
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	
	@Override
	public void clearObservers() {
		observers = new ArrayList<>();
	}

	@Override
	public synchronized void notifyObservers(Object o) {
		for (Observer observer : observers) {
			observer.update(o);
		}
	}
}
