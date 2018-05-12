package actions;

import gui.MainFrame;
import gui.dialogs.DialogPorudzbina;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
		putValue(SMALL_ICON, new ImageIcon("Images/porudzbina.png"));
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
