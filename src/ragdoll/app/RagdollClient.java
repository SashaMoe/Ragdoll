package ragdoll.app;

import ragdoll.app.gui.PersianFrame;
import ragdoll.app.gui.RagdollFrame;

public class RagdollClient {
	public static void main(String[] args) throws Exception {
		// EventQueue.invokeLater(new Runnable() {
		// public void run() {
		// try {
		// RagdollFrame frame = new RagdollFrame();
		// frame.setVisible(true);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });

		try {
			RagdollFrame ragdollFrame = new RagdollFrame();
			PersianFrame persianFrame = new PersianFrame();
			ragdollFrame.setPersianFrame(persianFrame);
			persianFrame.setRagdollFrame(ragdollFrame);
			ragdollFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
