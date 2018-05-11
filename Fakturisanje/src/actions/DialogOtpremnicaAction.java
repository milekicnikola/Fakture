package actions;

import gui.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class DialogOtpremnicaAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogOtpremnicaAction() {
		putValue(NAME, "Otpremnica");
		putValue(SMALL_ICON, new ImageIcon("Images/otpremnica.png"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Otpremnica");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogOtpreminca otpremnica = new DialogOtpremnica(MainFrame.getInstance(), false);
		otpremnica.setVisible(true);

	}

}
