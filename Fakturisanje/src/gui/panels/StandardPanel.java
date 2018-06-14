package gui.panels;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import util.ResourceLoader;

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

		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/commit.png"));			
		} catch (Exception e) {			

		}		
		
		BufferedImage image1 = null;
		try {
			image1 = ImageIO.read(ResourceLoader.load("Images/cancel.png"));			
		} catch (Exception e) {			

		}
		
		btnConfirm.setIcon(new ImageIcon(image));
		btnConfirm.setToolTipText("Potvrdi");

		btnCancel.setIcon(new ImageIcon(image1));
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
