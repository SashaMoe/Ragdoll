package ragdoll.app.gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.GridLayout;

public class RagdollFrame extends JFrame {

	private JPanel contentPane;

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
		
		JButton analyzeButton = new JButton("Analyze");
		analyzeButton.setBounds(253, 69, 117, 29);
		panel_2.add(analyzeButton);
		
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

}
