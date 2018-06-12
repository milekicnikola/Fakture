package actions;

import gui.MainFrame;
import gui.dialogs.DialogKurs;
import util.ResourceLoader;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class DialogKursAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogKursAction() {
		putValue(NAME, "Kurs");
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/kurs.png"));			
		} catch (Exception e) {			

		}				
		
		putValue(SMALL_ICON, new ImageIcon(image));
		putValue(MNEMONIC_KEY, KeyEvent.VK_V);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Kurs");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogKurs kurs = new DialogKurs(MainFrame.getInstance(), false);
		kurs.setVisible(true);

	}

}
