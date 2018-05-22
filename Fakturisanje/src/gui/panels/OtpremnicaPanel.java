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
	private JLabel lblKorisnik;
	private JLabel lblSifraM;
	private JLabel lblMagacin;
	private JLabel lblDatum;

	private JTextField txtSifra;
	private JTextField txtKorisnik;
	private JTextField txtSifraM;
	private JTextField txtMagacin;

	private JButton btnMagacin;

	private JDateChooser txtDatum;

	private Dimension datePreferedSize;

	public OtpremnicaPanel() {

		lblSifra = new JLabel("Šifra otpremnice");
		lblKorisnik = new JLabel("Korisnik");
		lblSifraM = new JLabel("Šifra magacina");
		lblMagacin = new JLabel("Magacin");
		lblDatum = new JLabel("Datum otpremanja");

		txtSifra = new JTextField(20);
		txtKorisnik = new JTextField(20);
		txtSifraM = new JTextField(10);
		txtMagacin = new JTextField(20);

		datePreferedSize = new Dimension(100, 20);

		txtDatum = new JDateChooser();
		txtDatum.setDateFormatString("yyyy-MM-dd");
		txtDatum.setPreferredSize(datePreferedSize);

		btnMagacin = new JButton("Izaberi magacin");
		btnMagacin.setEnabled(false);

		add(lblSifra);
		add(txtSifra);

		add(lblKorisnik);
		add(txtKorisnik);

		add(lblSifraM);
		add(txtSifraM);

		add(lblMagacin);
		add(txtMagacin);
		add(btnMagacin, "wrap");

		add(lblDatum);
		add(txtDatum);

		add(btnConfirm);
		add(btnCancel);

	}

	public JLabel getLblSifra() {
		return lblSifra;
	}

	public void setLblSifra(JLabel lblSifra) {
		this.lblSifra = lblSifra;
	}

	public JLabel getLblKorisnik() {
		return lblKorisnik;
	}

	public void setLblKorisnik(JLabel lblKorisnik) {
		this.lblKorisnik = lblKorisnik;
	}

	public JLabel getLblMagacin() {
		return lblMagacin;
	}

	public void setLblMagacin(JLabel lblMagacin) {
		this.lblMagacin = lblMagacin;
	}

	public JLabel getLblDatum() {
		return lblDatum;
	}

	public void setLblDatum(JLabel lblDatum) {
		this.lblDatum = lblDatum;
	}

	public JTextField getTxtSifra() {
		return txtSifra;
	}

	public void setTxtSifra(JTextField txtSifra) {
		this.txtSifra = txtSifra;
	}

	public JTextField getTxtKorisnik() {
		return txtKorisnik;
	}

	public void setTxtKorisnik(JTextField txtKorisnik) {
		this.txtKorisnik = txtKorisnik;
	}

	public JTextField getTxtMagacin() {
		return txtMagacin;
	}

	public void setTxtMagacin(JTextField txtMagacin) {
		this.txtMagacin = txtMagacin;
	}

	public JButton getBtnMagacin() {
		return btnMagacin;
	}

	public void setBtnMagacin(JButton btnMagacin) {
		this.btnMagacin = btnMagacin;
	}

	public JDateChooser getTxtDatum() {
		return txtDatum;
	}

	public void setTxtDatum(JDateChooser txtDatum) {
		this.txtDatum = txtDatum;
	}

	public JLabel getLblSifraM() {
		return lblSifraM;
	}

	public void setLblSifraM(JLabel lblSifraM) {
		this.lblSifraM = lblSifraM;
	}

	public JTextField getTxtSifraM() {
		return txtSifraM;
	}

	public void setTxtSifraM(JTextField txtSifraM) {
		this.txtSifraM = txtSifraM;
	}

}
