package gui.panels;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class FakturaPanel extends StandardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblSifra;
	private JLabel lblDatum;
	private JLabel lblParitet;
	private JLabel lblTezina;
	private JLabel lblTransport;
	private JLabel lblPoslata;

	private JTextField txtSifra;
	private JTextField txtParitet;
	private JTextField txtTezina;
	private JTextField txtTransport;
	private JTextField txtPoslata;

	private JDateChooser txtDatum;

	private Dimension datePreferedSize;

	public FakturaPanel() {

		lblSifra = new JLabel("Šifra fakture");
		lblDatum = new JLabel("Datum fakture");
		lblParitet = new JLabel("Paritet");
		lblTezina = new JLabel("Ukupna tezina");
		lblTransport = new JLabel("Transport");
		lblPoslata = new JLabel("Poslata");

		txtSifra = new JTextField(20);
		txtParitet = new JTextField(20);
		txtTezina = new JTextField(10);
		txtTransport = new JTextField(20);
		txtPoslata = new JTextField(3);

		datePreferedSize = new Dimension(100, 20);

		txtDatum = new JDateChooser();
		txtDatum.setDateFormatString("yyyy-MM-dd");
		txtDatum.setPreferredSize(datePreferedSize);

		add(lblSifra);
		add(txtSifra);

		add(lblDatum);
		add(txtDatum);

		add(lblParitet);
		add(txtParitet, "wrap");

		add(lblTezina);
		add(txtTezina);

		add(lblTransport);
		add(txtTransport);

		add(lblPoslata);
		add(txtPoslata);

		add(btnConfirm);
		add(btnCancel);

	}

	public JLabel getLblSifra() {
		return lblSifra;
	}

	public void setLblSifra(JLabel lblSifra) {
		this.lblSifra = lblSifra;
	}

	public JLabel getLblDatum() {
		return lblDatum;
	}

	public void setLblDatum(JLabel lblDatum) {
		this.lblDatum = lblDatum;
	}

	public JLabel getLblParitet() {
		return lblParitet;
	}

	public void setLblParitet(JLabel lblParitet) {
		this.lblParitet = lblParitet;
	}

	public JLabel getLblTezina() {
		return lblTezina;
	}

	public void setLblTezina(JLabel lblTezina) {
		this.lblTezina = lblTezina;
	}

	public JLabel getLblTransport() {
		return lblTransport;
	}

	public void setLblTransport(JLabel lblTransport) {
		this.lblTransport = lblTransport;
	}

	public JLabel getLblPoslata() {
		return lblPoslata;
	}

	public void setLblPoslata(JLabel lblPoslata) {
		this.lblPoslata = lblPoslata;
	}

	public JTextField getTxtSifra() {
		return txtSifra;
	}

	public void setTxtSifra(JTextField txtSifra) {
		this.txtSifra = txtSifra;
	}

	public JTextField getTxtParitet() {
		return txtParitet;
	}

	public void setTxtParitet(JTextField txtParitet) {
		this.txtParitet = txtParitet;
	}

	public JTextField getTxtTezina() {
		return txtTezina;
	}

	public void setTxtTezina(JTextField txtTezina) {
		this.txtTezina = txtTezina;
	}

	public JTextField getTxtTransport() {
		return txtTransport;
	}

	public void setTxtTransport(JTextField txtTransport) {
		this.txtTransport = txtTransport;
	}

	public JTextField getTxtPoslata() {
		return txtPoslata;
	}

	public void setTxtPoslata(JTextField txtPoslata) {
		this.txtPoslata = txtPoslata;
	}

	public JDateChooser getTxtDatum() {
		return txtDatum;
	}

	public void setTxtDatum(JDateChooser txtDatum) {
		this.txtDatum = txtDatum;
	}

}
