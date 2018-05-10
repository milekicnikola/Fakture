package gui.panels;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public abstract class StandardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JButton btnConfirm;
	public JButton btnCancel;
	public boolean show;

	public StandardPanel() {

		show = true;
		setLayout(new MigLayout("fillx", "[left]rel[grow]", "[]10[]"));
		btnConfirm = new JButton();
		btnCancel = new JButton();

		btnConfirm.setIcon(new ImageIcon("Images/commit.png"));
		btnConfirm.setToolTipText("Potvrdi");

		btnCancel.setIcon(new ImageIcon("Images/cancel.png"));
		btnCancel.setToolTipText("Odustani");

		btnConfirm.setEnabled(false);
		btnCancel.setEnabled(false);

	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public JButton getBtnConfirm() {
		return btnConfirm;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

}
