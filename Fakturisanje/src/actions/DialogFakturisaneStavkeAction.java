package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import gui.MainFrame;
import gui.dialogs.DialogFakturisaneStavke;

public class DialogFakturisaneStavkeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogFakturisaneStavkeAction() {
		putValue(NAME, "Fakturisane stavke");
		putValue(SMALL_ICON, new ImageIcon("Images/fakturisaneStavke.png"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_M);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Fakturisane stavke");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogFakturisaneStavke fs = new DialogFakturisaneStavke(
				MainFrame.getInstance(), true);
		fs.setVisible(true);

	}

}

