package actions;

import gui.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class DialogKursAction extends AbstractAction {
	
	public DialogKursAction() {
		putValue(NAME, "Kurs");
		putValue(SMALL_ICON, new ImageIcon("Images/kurs.png"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_V);
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "Kurs");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DialogKurs kurs= new DialogKurs(MainFrame.getInstance(), false);
		kurs.setVisible(true);

	}

}
