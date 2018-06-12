package actions;

import gui.MainFrame;
import gui.dialogs.DialogOtpremnica;
import util.ResourceLoader;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
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
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/otpremnica.png"));			
		} catch (Exception e) {			

		}				
		
		putValue(SMALL_ICON, new ImageIcon(image));
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Otpremnica");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogOtpremnica otpremnica = new DialogOtpremnica(MainFrame.getInstance(), false);
		otpremnica.setVisible(true);

	}

}
