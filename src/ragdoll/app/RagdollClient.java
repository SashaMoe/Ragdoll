package ragdoll.app;

import java.util.ArrayList;
import java.util.List;

import ragdoll.app.phase.AdapterPatternDetectionPhase;
import ragdoll.app.phase.CompositePatternDetectionPhase;
import ragdoll.app.phase.DecoratorPatternDetectionPhase;
import ragdoll.app.phase.GVOutputPhase;
import ragdoll.app.phase.GenerateDotImagePhase;
import ragdoll.app.phase.GenerateSDImagePhase;
import ragdoll.app.phase.LoadAndVisitASMPhase;
import ragdoll.app.phase.SDAnalyzePhase;
import ragdoll.app.phase.SDOutputPhase;
import ragdoll.app.phase.SingletonPatternDetectionPhase;
import ragdoll.app.phase.TranslateUserSelectedPatternsPhase;
import ragdoll.app.phase.WutPhase;
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
		IPhase generateSDImagePhase = new GenerateSDImagePhase();
		IPhase generateDotImagePhase = new GenerateDotImagePhase();
		IPhase translateUserSelectedPatternsPhase = new TranslateUserSelectedPatternsPhase();
		IPhase wutPhase = new WutPhase();

		properties.loadProperties("resources/config.properties");
		framework.addPhase("SDAnalyze", sdAnalyzePhase);
		framework.addPhase("SDOutput", sdOutputPhase);
		framework.addPhase("LoadAndVisitASM", loadAndVisitASMPhase);
		framework.addPhase("AdapterDetector", adapterPatternDetectionPhase);
		framework.addPhase("CompositeDetector", compositePatternDetectionPhase);
		framework.addPhase("DecoratorDetector", decoratorPatternDetectionPhase);
		framework.addPhase("SingletonDetector", singletonPatternDetectionPhase);
		framework.addPhase("GVOutput", gvOutputPhase);
		framework.addPhase("GenerateDotImage", generateDotImagePhase);
		framework.addPhase("GenerateSDImage", generateSDImagePhase);
		framework.addPhase("TranslateUserSelectedPatterns", translateUserSelectedPatternsPhase);
		framework.addPhase("WutPhase", wutPhase);

		String[] phases = properties.getProperty("Phases").split(",");
		List<String> phaseOrder = new ArrayList<>();
		for (String phase : phases) {
			phaseOrder.add(phase.trim());
		}
		framework.setPhaseExecutionList(phaseOrder);

		framework.executePhases();
	}
}
