package gui.panels;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class OtpremnicaPanel extends StandardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblSifra;
	private JLabel lblSifraM;
	private JLabel lblNazivM;
	private JLabel lblSifraF;
	private JLabel lblDatum;
	private JLabel lblTransport;
	private JLabel lblPoslata;

	private JTextField txtSifra;
	private JTextField txtSifraM;
	private JTextField txtNazivM;
	private JTextField txtSifraF;
	private JTextField txtTransport;
	private JTextField txtPoslata;

	private JButton btnMagacin;
	private JButton btnFaktura;

	private JDateChooser txtDatum;

	private Dimension datePreferedSize;

	public OtpremnicaPanel() {

		lblSifra = new JLabel("Šifra otpremnice");
		lblSifraM = new JLabel("Šifra magacina");
		lblNazivM = new JLabel("Naziv magacina");
		lblSifraF = new JLabel("Šifra fakture");
		lblDatum = new JLabel("Datum fakture");
		lblTransport = new JLabel("Transport");
		lblPoslata = new JLabel("Poslata");

		txtSifra = new JTextField(20);
		txtSifraM = new JTextField(10);
		txtNazivM = new JTextField(20);
		txtSifraF = new JTextField(10);
		txtTransport = new JTextField(20);
		txtPoslata = new JTextField(3);

		datePreferedSize = new Dimension(100, 20);

		txtDatum = new JDateChooser();
		txtDatum.setDateFormatString("yyyy-MM-dd");
		txtDatum.setPreferredSize(datePreferedSize);

		btnMagacin = new JButton("Izaberi magacin");
		btnMagacin.setEnabled(false);

		btnFaktura = new JButton("Izaberi fakturu");
		btnFaktura.setEnabled(false);

		add(lblSifra);
		add(txtSifra);

		add(lblSifraM);
		add(txtSifraM);

		add(lblNazivM);
		add(txtNazivM);
		add(btnMagacin, "wrap");

		add(lblSifraF);
		add(txtSifraF);

		add(lblDatum);
		add(txtDatum);

		add(lblTransport);
		add(txtTransport);
		add(btnFaktura, "wrap");

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

	public JLabel getLblSifraM() {
		return lblSifraM;
	}

	public void setLblSifraM(JLabel lblSifraM) {
		this.lblSifraM = lblSifraM;
	}

	public JLabel getLblNazivM() {
		return lblNazivM;
	}

	public void setLblNazivM(JLabel lblNazivM) {
		this.lblNazivM = lblNazivM;
	}

	public JLabel getLblSifraF() {
		return lblSifraF;
	}

	public void setLblSifraF(JLabel lblSifraF) {
		this.lblSifraF = lblSifraF;
	}

	public JLabel getLblDatum() {
		return lblDatum;
	}

	public void setLblDatum(JLabel lblDatum) {
		this.lblDatum = lblDatum;
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

	public JTextField getTxtSifraM() {
		return txtSifraM;
	}

	public void setTxtSifraM(JTextField txtSifraM) {
		this.txtSifraM = txtSifraM;
	}

	public JTextField getTxtNazivM() {
		return txtNazivM;
	}

	public void setTxtNazivM(JTextField txtNazivM) {
		this.txtNazivM = txtNazivM;
	}

	public JTextField getTxtSifraF() {
		return txtSifraF;
	}

	public void setTxtSifraF(JTextField txtSifraF) {
		this.txtSifraF = txtSifraF;
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

	public JButton getBtnMagacin() {
		return btnMagacin;
	}

	public void setBtnMagacin(JButton btnMagacin) {
		this.btnMagacin = btnMagacin;
	}

	public JButton getBtnFaktura() {
		return btnFaktura;
	}

	public void setBtnFaktura(JButton btnFaktura) {
		this.btnFaktura = btnFaktura;
	}

	public JDateChooser getTxtDatum() {
		return txtDatum;
	}

	public void setTxtDatum(JDateChooser txtDatum) {
		this.txtDatum = txtDatum;
	}

}
