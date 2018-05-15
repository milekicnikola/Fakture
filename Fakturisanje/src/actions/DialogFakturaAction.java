package actions;

import gui.MainFrame;
import gui.dialogs.DialogFaktura;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class DialogFakturaAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogFakturaAction() {
		putValue(NAME, "Faktura");
		putValue(SMALL_ICON, new ImageIcon("Images/faktura.png"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_F);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Faktura");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogFaktura faktura = new DialogFaktura(MainFrame.getInstance(), false);
		faktura.setVisible(true);

	}

}
