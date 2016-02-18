package ragdoll.app.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;

import ragdoll.code.uml.pattern.Pattern;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.common.observer.Observer;
import ragdoll.framework.FrameworkStatus;
import ragdoll.framework.RagdollFramework;
import ragdoll.framework.RagdollProperties;
import ragdoll.util.Utilities;

import javax.swing.ScrollPaneConstants;

public class PersianFrame extends JFrame {

	private JPanel contentPane;

	private List<String> phaseOrder;

	JScrollPane treeScrollPane;
	JScrollPane imageScrollPane;
	JPanel treeScrollPanel;
	HashMap<String, List<JCheckBox>> checkboxes;

	RagdollFrame ragdollFrame;

	private static final int RANGE = 20;
	private static final int CHECKBOX_WIDTH = 220;
	private static final int CHECKBOX_HEIGHT = 20;

	/**
	 * Create the frame.
	 */
	public PersianFrame() {
		phaseOrder = new ArrayList<>();
		phaseOrder.add("TranslateUserSelectedPatterns");
		phaseOrder.add("GVOutput");
		phaseOrder.add("GenerateDotImage");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 950);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JMenuBar menuBar = new JMenuBar();
		// panel.add(menuBar);

		this.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem reloadMenuItem = new JMenuItem("Reload...");
		fileMenu.add(reloadMenuItem);
		reloadMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PersianFrame.this.setVisible(false);
				PersianFrame.this.init();
				ragdollFrame.init();
				ragdollFrame.setVisible(true);
			}
		});

		JMenuItem exportMenuItem = new JMenuItem("Export...");
		fileMenu.add(exportMenuItem);
		exportMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(PersianFrame.this.contentPane);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					RagdollProperties properties = RagdollProperties.getInstance();
					String filePath = fc.getSelectedFile().getAbsolutePath();
					filePath = filePath.endsWith("." + properties.getProperty("Output-Image-Type")) ? filePath
							: (filePath + "." + properties.getProperty("Output-Image-Type"));
					
					String outputImagePath = properties.getProperty("Output-Directory")
							+ properties.getProperty("Output-Image-Name") + "."
							+ properties.getProperty("Output-Image-Type");
					try {
						Files.copy(new File(outputImagePath).toPath(), new File(filePath).toPath());
					} catch (IOException e1) {
						Utilities.printVerbose("Can't export the file.");
					}
				} else {
					Utilities.printVerbose("Open command cancelled by user.");
				}
			}
		});

		JMenuItem exitMenuItem = new JMenuItem("Exit~");
		fileMenu.add(exitMenuItem);
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

		JMenuItem helpMenuItem = new JMenuItem("Help");
		helpMenu.add(helpMenuItem);

		JMenuItem usMenuItem = new JMenuItem("About Ragdoll...");
		helpMenu.add(usMenuItem);
		contentPane.setLayout(null);

		treeScrollPanel = new JPanel();

		treeScrollPane = new JScrollPane(treeScrollPanel);
		treeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		treeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		treeScrollPane.setBounds(0, 0, 250, 900);
		contentPane.add(treeScrollPane, treeScrollPanel);

		treeScrollPanel.setPreferredSize(new Dimension(250, 900));
		treeScrollPanel.setAutoscrolls(true);
		treeScrollPanel.setLayout(null);

		imageScrollPane = new JScrollPane();
		imageScrollPane.setBounds(251, 0, 1025, 900);
		contentPane.add(imageScrollPane);

	}

	public void setRagdollFrame(RagdollFrame ragdollFrame) {
		this.ragdollFrame = ragdollFrame;
	}

	public void init() {
		treeScrollPanel.removeAll();
		treeScrollPanel.repaint();
		imageScrollPane.setViewportView(null);
		imageScrollPane.repaint();
	}

	public void addElement() {
		if (RagdollProperties.getInstance().getProperty("Mode").equals("SD")) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ImagePanel imagePanel = new ImagePanel();
			imageScrollPane.setViewportView(imagePanel);
			return;
		}

		PatternInfo patternInfo = PatternInfo.getInstance();
		HashSet<String> patternSet;
		checkboxes = new HashMap<>();
		int x = 0;
		int y = 10;
		for (String patternType : patternInfo.getPatterMap().keySet()) {
			patternSet = new HashSet<>();
			JCheckBox patternTypeBox = new JCheckBox(patternType + " ");
			patternTypeBox.addActionListener(new checkboxListener());
			patternTypeBox.setBounds(x, y, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
			treeScrollPanel.add(patternTypeBox);
			patternTypeBox.setLocation(x, y);
			checkboxes.put(patternType, new ArrayList<>());
			for (Pattern pattern : patternInfo.getPatterMap().get(patternType)) {
				patternSet.add(pattern.getPatternName());
			}
			x += RANGE;
			y += RANGE;
			for (String patternName : patternSet) {
				JCheckBox patternNameBox = new JCheckBox(patternName);
				patternNameBox.addActionListener(new checkboxListener());
				treeScrollPanel.add(patternNameBox);
				patternNameBox.setLocation(x, y);
				patternNameBox.setBounds(x, y, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
				checkboxes.get(patternType).add(patternNameBox);
				y += RANGE;
			}
			x = 0;
		}
		treeScrollPanel.setPreferredSize(new Dimension(250, y));
		
		this.repaint();
	}

	public class checkboxListener implements ActionListener, Observer {

		@Override
		public void actionPerformed(ActionEvent e) {
			RagdollFramework framework = RagdollFramework.getInstance();

			JCheckBox thisBox = (JCheckBox) e.getSource();
			if (thisBox.getText().endsWith(" ")) {
				String patternType = thisBox.getText().trim();
				for (JCheckBox box : checkboxes.get(patternType)) {
					box.setSelected(thisBox.isSelected());
				}
			}

			Map<String, List<String>> selectedMap = new HashMap<>();
			for (String patternType : checkboxes.keySet()) {
				selectedMap.put(patternType, new ArrayList<>());
				for (JCheckBox box : checkboxes.get(patternType)) {
					if (box.isSelected()) {
						selectedMap.get(patternType).add(box.getText());
					}
				}
			}

			PatternInfo patternInfo = PatternInfo.getInstance();
			patternInfo.setSelectedClasses(selectedMap);

			framework.clearObservers();
			framework.registerObserver(this);
			framework.setPhaseExecutionList(phaseOrder);
			framework.executePhases();
		}

		@Override
		public synchronized void update(Object o) {
			if (!(o instanceof FrameworkStatus)) {
				return;
			}

			FrameworkStatus status = (FrameworkStatus) o;

			if (status.getCurrentPhaseNumber() == status.getTotalPhaseNumber() + 1) {
				// Done.
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ImagePanel imagePanel = new ImagePanel();
				imageScrollPane.setViewportView(imagePanel);
			}
		}
	}

	public class ImagePanel extends JPanel {
		private BufferedImage image;

		public ImagePanel() {
			RagdollProperties properties = RagdollProperties.getInstance();
			String outputImage = properties.getProperty("Output-Directory")
					+ properties.getProperty("Output-Image-Name") + "." + properties.getProperty("Output-Image-Type");
			try {
				image = ImageIO.read(new File(outputImage));
				this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
		}

	}
}
