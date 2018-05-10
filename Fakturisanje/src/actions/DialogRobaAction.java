package actions;

import gui.MainFrame;
import gui.dialogs.DialogRoba;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
		putValue(SMALL_ICON, new ImageIcon("Images/banka.png"));
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
