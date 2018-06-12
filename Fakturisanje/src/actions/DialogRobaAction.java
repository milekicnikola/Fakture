package actions;

import gui.MainFrame;
import gui.dialogs.DialogRoba;
import util.ResourceLoader;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class DialogRobaAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogRobaAction() {
		putValue(NAME, "Roba");
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/roba.png"));			
		} catch (Exception e) {			

		}				
		
		putValue(SMALL_ICON, new ImageIcon(image));
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Roba");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogRoba roba = new DialogRoba(MainFrame.getInstance(), false);
		roba.setVisible(true);

	}

}
