package gui.panels;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class FakturisaneStavkePanel extends StandardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblSifraR;
	private JLabel lblNazivR;
	private JLabel lblInterni;
	private JLabel lblSifraP;
	private JLabel lblNaruceno;
	private JLabel lblFakturisano;
	private JLabel lblDatum;
	private JLabel lblFaktura;
	private JLabel lblKorisnik;

	private JTextField txtSifraR;
	private JTextField txtNazivR;
	private JTextField txtInterni;
	private JTextField txtSifraP;
	private JTextField txtNaruceno;
	private JTextField txtFakturisano;
	private JTextField txtFaktura;
	private JTextField txtKorisnik;

	private JDateChooser txtDatum;

	private Dimension datePreferedSize;

	public FakturisaneStavkePanel() {

		lblSifraP = new JLabel("Porudzbina");
		lblSifraR = new JLabel("Šifra robe");
		lblNazivR = new JLabel("Naziv robe");
		lblInterni = new JLabel("Interni naziv robe");
		lblNaruceno = new JLabel("Naručeno komada");
		lblFakturisano = new JLabel("Fakturisano komada");
		lblDatum = new JLabel("Datum isporuke");
		lblFaktura = new JLabel("Faktura");
		lblKorisnik = new JLabel("Korisnik");

		txtSifraP = new JTextField(15);
		txtSifraR = new JTextField(15);
		txtNazivR = new JTextField(20);
		txtInterni = new JTextField(20);
		txtNaruceno = new JTextField(10);
		txtFakturisano = new JTextField(10);
		txtFaktura = new JTextField(20);
		txtKorisnik = new JTextField(20);

		datePreferedSize = new Dimension(100, 20);

		txtDatum = new JDateChooser();
		txtDatum.setDateFormatString("yyyy-MM-dd");
		txtDatum.setPreferredSize(datePreferedSize);

		add(lblSifraR);
		add(txtSifraR);

		add(lblNazivR);
		add(txtNazivR);

		add(lblInterni);
		add(txtInterni, "wrap");

		add(lblSifraP);
		add(txtSifraP);

		add(lblFaktura);
		add(txtFaktura);

		add(lblDatum);
		add(txtDatum, "wrap");

		add(lblNaruceno);
		add(txtNaruceno);

		add(lblFakturisano);
		add(txtFakturisano);

		add(lblKorisnik);
		add(txtKorisnik);

		add(btnConfirm);
		add(btnCancel);

	}

	public JLabel getLblSifraR() {
		return lblSifraR;
	}

	public void setLblSifraR(JLabel lblSifraR) {
		this.lblSifraR = lblSifraR;
	}

	public JLabel getLblNazivR() {
		return lblNazivR;
	}

	public void setLblNazivR(JLabel lblNazivR) {
		this.lblNazivR = lblNazivR;
	}

	public JLabel getLblInterni() {
		return lblInterni;
	}

	public void setLblInterni(JLabel lblInterni) {
		this.lblInterni = lblInterni;
	}

	public JLabel getLblSifraP() {
		return lblSifraP;
	}

	public void setLblSifraP(JLabel lblSifraP) {
		this.lblSifraP = lblSifraP;
	}

	public JLabel getLblNaruceno() {
		return lblNaruceno;
	}

	public void setLblNaruceno(JLabel lblNaruceno) {
		this.lblNaruceno = lblNaruceno;
	}

	public JLabel getLblFakturisano() {
		return lblFakturisano;
	}

	public void setLblFakturisano(JLabel lblFakturisano) {
		this.lblFakturisano = lblFakturisano;
	}

	public JLabel getLblDatum() {
		return lblDatum;
	}

	public void setLblDatum(JLabel lblDatum) {
		this.lblDatum = lblDatum;
	}

	public JLabel getLblFaktura() {
		return lblFaktura;
	}

	public void setLblFaktura(JLabel lblFaktura) {
		this.lblFaktura = lblFaktura;
	}

	public JTextField getTxtSifraR() {
		return txtSifraR;
	}

	public void setTxtSifraR(JTextField txtSifraR) {
		this.txtSifraR = txtSifraR;
	}

	public JTextField getTxtNazivR() {
		return txtNazivR;
	}

	public void setTxtNazivR(JTextField txtNazivR) {
		this.txtNazivR = txtNazivR;
	}

	public JTextField getTxtInterni() {
		return txtInterni;
	}

	public void setTxtInterni(JTextField txtInterni) {
		this.txtInterni = txtInterni;
	}

	public JTextField getTxtSifraP() {
		return txtSifraP;
	}

	public void setTxtSifraP(JTextField txtSifraP) {
		this.txtSifraP = txtSifraP;
	}

	public JTextField getTxtNaruceno() {
		return txtNaruceno;
	}

	public void setTxtNaruceno(JTextField txtNaruceno) {
		this.txtNaruceno = txtNaruceno;
	}

	public JTextField getTxtFakturisano() {
		return txtFakturisano;
	}

	public void setTxtFakturisano(JTextField txtFakturisano) {
		this.txtFakturisano = txtFakturisano;
	}

	public JTextField getTxtFaktura() {
		return txtFaktura;
	}

	public void setTxtFaktura(JTextField txtFaktura) {
		this.txtFaktura = txtFaktura;
	}

	public JDateChooser getTxtDatum() {
		return txtDatum;
	}

	public void setTxtDatum(JDateChooser txtDatum) {
		this.txtDatum = txtDatum;
	}

	public JLabel getLblKorisnik() {
		return lblKorisnik;
	}

	public void setLblKorisnik(JLabel lblKorisnik) {
		this.lblKorisnik = lblKorisnik;
	}

	public JTextField getTxtKorisnik() {
		return txtKorisnik;
	}

	public void setTxtKorisnik(JTextField txtKorisnik) {
		this.txtKorisnik = txtKorisnik;
	}

}
