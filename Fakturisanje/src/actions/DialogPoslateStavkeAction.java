package actions;

import gui.MainFrame;
import gui.dialogs.DialogPoslateStavke;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class DialogPoslateStavkeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogPoslateStavkeAction() {
		putValue(NAME, "Poslate stavke");
		putValue(SMALL_ICON, new ImageIcon("Images/poslateStavke.png"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_L);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Poslate stavke");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogPoslateStavke ps = new DialogPoslateStavke(
				MainFrame.getInstance(), true);
		ps.setVisible(true);

	}

}
