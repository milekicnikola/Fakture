package actions;

import gui.MainFrame;
import gui.dialogs.DialogMagacin;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
		putValue(SMALL_ICON, new ImageIcon("Images/magacin.png"));
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
