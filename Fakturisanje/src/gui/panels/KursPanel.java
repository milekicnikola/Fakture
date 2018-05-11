package gui.panels;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class KursPanel extends StandardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblDatum;
	private JLabel lblEvro;
	private JLabel lblRon;

	private JDateChooser txtDatum;
	private JTextField txtKurs;

	private Dimension datePreferedSize;

	public KursPanel() {

		lblDatum = new JLabel("Datum kursa");
		lblEvro = new JLabel("1 euro = ");
		lblRon = new JLabel(" rona");

		txtKurs = new JTextField(8);

		datePreferedSize = new Dimension(100, 20);

		txtDatum = new JDateChooser();
		txtDatum.setDateFormatString("yyyy-MM-dd");
		txtDatum.setPreferredSize(datePreferedSize);

		add(lblDatum);
		add(txtDatum);

		add(lblEvro);
		add(txtKurs);
		add(lblRon);

		add(btnConfirm);
		add(btnCancel);

	}

	public JLabel getLblDatum() {
		return lblDatum;
	}

	public void setLblDatum(JLabel lblDatum) {
		this.lblDatum = lblDatum;
	}

	public JLabel getLblEvro() {
		return lblEvro;
	}

	public void setLblEvro(JLabel lblEvro) {
		this.lblEvro = lblEvro;
	}

	public JLabel getLblRon() {
		return lblRon;
	}

	public void setLblRon(JLabel lblRon) {
		this.lblRon = lblRon;
	}

	public JDateChooser getTxtDatum() {
		return txtDatum;
	}

	public void setTxtDatum(JDateChooser txtDatum) {
		this.txtDatum = txtDatum;
	}

	public JTextField getTxtKurs() {
		return txtKurs;
	}

	public void setTxtKurs(JTextField txtKurs) {
		this.txtKurs = txtKurs;
	}

}
