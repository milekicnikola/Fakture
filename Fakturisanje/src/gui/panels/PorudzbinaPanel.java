package gui.panels;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class PorudzbinaPanel extends StandardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblSifra;
	private JLabel lblSifraM;
	private JLabel lblNazivM;
	private JLabel lblKorisnik;
	private JLabel lblSifraK;
	private JLabel lblNazivK;
	private JLabel lblDatum;

	private JTextField txtSifra;
	private JTextField txtSifraM;
	private JTextField txtNazivM;
	private JTextField txtKorisnik;
	private JTextField txtSifraK;
	private JTextField txtNazivK;

	private JButton btnMagacin;
	private JButton btnKupac;

	private JDateChooser txtDatum;

	private Dimension datePreferedSize;

	public PorudzbinaPanel() {

		lblSifra = new JLabel("Šifra porudzbine");
		lblSifraM = new JLabel("Šifra magacina");
		lblNazivM = new JLabel("Naziv magacina");
		lblKorisnik = new JLabel("Korisnik");
		lblSifraK = new JLabel("Šifra kupca");
		lblNazivK = new JLabel("Naziv kupca");
		lblDatum = new JLabel("Datum porudzbine");

		txtSifra = new JTextField(20);
		txtSifraM = new JTextField(5);
		txtNazivM = new JTextField(20);
		txtKorisnik = new JTextField(20);
		txtSifraK = new JTextField(3);
		txtNazivK = new JTextField(20);

		datePreferedSize = new Dimension(100, 20);

		txtDatum = new JDateChooser();
		txtDatum.setDateFormatString("yyyy-MM-dd");
		txtDatum.setPreferredSize(datePreferedSize);

		btnMagacin = new JButton("Izaberi magacin");
		btnKupac = new JButton("Izaberi kupca");
		btnMagacin.setEnabled(false);
		btnKupac.setEnabled(false);

		add(lblSifra);
		add(txtSifra);

		add(lblSifraM);
		add(txtSifraM);

		add(lblNazivM);
		add(txtNazivM);
		add(btnMagacin, "wrap");

		add(lblKorisnik);
		add(txtKorisnik);

		add(lblSifraK);
		add(txtSifraK);

		add(lblNazivK);
		add(txtNazivK);
		add(btnKupac, "wrap");

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

	public JLabel getLblKorisnik() {
		return lblKorisnik;
	}

	public void setLblKorisnik(JLabel lblKorisnik) {
		this.lblKorisnik = lblKorisnik;
	}

	public JLabel getLblSifraK() {
		return lblSifraK;
	}

	public void setLblSifraK(JLabel lblSifraK) {
		this.lblSifraK = lblSifraK;
	}

	public JLabel getLblNazivK() {
		return lblNazivK;
	}

	public void setLblNazivK(JLabel lblNazivK) {
		this.lblNazivK = lblNazivK;
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

	public JTextField getTxtKorisnik() {
		return txtKorisnik;
	}

	public void setTxtKorisnik(JTextField txtKorisnik) {
		this.txtKorisnik = txtKorisnik;
	}

	public JTextField getTxtSifraK() {
		return txtSifraK;
	}

	public void setTxtSifraK(JTextField txtSifraK) {
		this.txtSifraK = txtSifraK;
	}

	public JTextField getTxtNazivK() {
		return txtNazivK;
	}

	public void setTxtNazivK(JTextField txtNazivK) {
		this.txtNazivK = txtNazivK;
	}

	public JDateChooser getTxtDatum() {
		return txtDatum;
	}

	public void setTxtDatum(JDateChooser txtDatum) {
		this.txtDatum = txtDatum;
	}

	public JButton getBtnMagacin() {
		return btnMagacin;
	}

	public void setBtnMagacin(JButton btnMagacin) {
		this.btnMagacin = btnMagacin;
	}

	public JButton getBtnKupac() {
		return btnKupac;
	}

	public void setBtnKupac(JButton btnKupac) {
		this.btnKupac = btnKupac;
	}

}
