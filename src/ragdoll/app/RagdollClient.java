package ragdoll.app;

import java.util.ArrayList;
import java.util.List;

import ragdoll.app.phase.AdapterPatternDetectionPhase;
import ragdoll.app.phase.CompositePatternDetectionPhase;
import ragdoll.app.phase.DecoratorPatternDetectionPhase;
import ragdoll.app.phase.GVOutputPhase;
import ragdoll.app.phase.LoadAndVisitASMPhase;
import ragdoll.app.phase.SDAnalyzePhase;
import ragdoll.app.phase.SDOutputPhase;
import ragdoll.app.phase.SingletonPatternDetectionPhase;
import ragdoll.framework.IPhase;
import ragdoll.framework.RagdollFramework;
import ragdoll.framework.RagdollProperties;

public class RagdollClient {
	public static void main(String[] args) throws Exception {
		RagdollProperties properties = RagdollProperties.getInstance();
		RagdollFramework framework = new RagdollFramework();

		IPhase sdAnalyzePhase = new SDAnalyzePhase();
		IPhase sdOutputPhase = new SDOutputPhase();
		IPhase loadAndVisitASMPhase = new LoadAndVisitASMPhase();
		IPhase adapterPatternDetectionPhase = new AdapterPatternDetectionPhase();
		IPhase compositePatternDetectionPhase = new CompositePatternDetectionPhase();
		IPhase decoratorPatternDetectionPhase = new DecoratorPatternDetectionPhase();
		IPhase singletonPatternDetectionPhase = new SingletonPatternDetectionPhase();
		IPhase gvOutputPhase = new GVOutputPhase();

		properties.loadProperties("resources/config.properties");
		framework.addPhase("SDAnalyze", sdAnalyzePhase);
		framework.addPhase("SDOutput", sdOutputPhase);
		framework.addPhase("LoadAndVisitASM", loadAndVisitASMPhase);
		framework.addPhase("AdapterPattern", adapterPatternDetectionPhase);
		framework.addPhase("CompositePattern", compositePatternDetectionPhase);
		framework.addPhase("DecoratorPattern", decoratorPatternDetectionPhase);
		framework.addPhase("SingletonPattern", singletonPatternDetectionPhase);
		framework.addPhase("GVOutput", gvOutputPhase);

		String[] phases = properties.getProperty("Phases").split(",");
		List<String> phaseOrder = new ArrayList<>();
		for (String phase : phases) {
			phaseOrder.add(phase.trim());
		}
		
		framework.executePhases();
	}
}
