package actions;

import gui.MainFrame;
import gui.dialogs.DialogKupci;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class DialogKupciAction extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogKupciAction() {
		putValue(NAME, "Kupci");
		putValue(SMALL_ICON, new ImageIcon("Images/kupci.png"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_K);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Kupci");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogKupci kupci = new DialogKupci(MainFrame.getInstance(), false);
		kupci.setVisible(true);

	}

}
