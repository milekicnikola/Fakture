package actions;

import gui.MainFrame;
import gui.dialogs.DialogOtvoreneStavke;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class DialogOtvoreneStavkeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogOtvoreneStavkeAction() {
		putValue(NAME, "Otvorene stavke");
		putValue(SMALL_ICON, new ImageIcon("Images/otvoreneStavke.png"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Otvorene stavke");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogOtvoreneStavke os = new DialogOtvoreneStavke(
				MainFrame.getInstance(), true);
		os.setVisible(true);

	}

}
