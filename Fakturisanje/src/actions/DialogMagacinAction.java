package actions;

import gui.MainFrame;
import gui.dialogs.DialogMagacin;
import util.ResourceLoader;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class DialogMagacinAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogMagacinAction() {
		putValue(NAME, "Magacin");
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/magacin.png"));			
		} catch (Exception e) {			

		}				
		
		putValue(SMALL_ICON, new ImageIcon(image));
		putValue(MNEMONIC_KEY, KeyEvent.VK_M);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Magacin");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogMagacin magacin = new DialogMagacin(MainFrame.getInstance(), false);
		magacin.setVisible(true);

	}

}
