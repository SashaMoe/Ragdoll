package ragdoll.app.gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RagdollFrame extends JFrame {

	private JPanel contentPane;
	private JFileChooser fc;
	private RagdollProperties ragdollProperties = RagdollProperties.getInstance();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RagdollFrame frame = new RagdollFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RagdollFrame() {
		fc = new JFileChooser();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 5, 440, 116);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton loadButton = new JButton("Load Config");
		loadButton.setBounds(94, 69, 117, 29);
		panel_2.add(loadButton);
		loadButton.addActionListener(new LoadConfig());
		
		JButton analyzeButton = new JButton("Analyze");
		analyzeButton.setBounds(253, 69, 117, 29);
		panel_2.add(analyzeButton);
		analyzeButton.addActionListener(new AnalyzeListener());
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 120, 440, 46);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel progressLabel = new JLabel("New label");
		progressLabel.setBounds(189, 5, 61, 16);
		progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(progressLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 166, 440, 106);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(89, 5, 280, 20);
		panel_1.add(progressBar);
	}
	
	public class LoadConfig implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			 int returnVal = fc.showOpenDialog(RagdollFrame.this.contentPane);
			 
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                try {
						ragdollProperties.loadProperties(file.getAbsolutePath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            } else {
	            	System.out.println("Open command cancelled by user.");
	            }
		}
	}
	
	public class AnalyzeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
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
}
