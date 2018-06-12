package actions;

import gui.MainFrame;
import gui.dialogs.DialogPorudzbina;
import util.ResourceLoader;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class DialogPorudzbinaAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogPorudzbinaAction() {
		putValue(NAME, "Porudzbina");
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/porudzbina.png"));			
		} catch (Exception e) {			

		}				
		
		putValue(SMALL_ICON, new ImageIcon(image));
		putValue(MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Porudzbina");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogPorudzbina porudzbina = new DialogPorudzbina(MainFrame.getInstance(), false);
		porudzbina.setVisible(true);

	}

}
