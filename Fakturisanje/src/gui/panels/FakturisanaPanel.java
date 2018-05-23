package gui.panels;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class FakturisanaPanel extends StandardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblSifraF;
	private JLabel lblSifraR;
	private JLabel lblNazivR;
	private JLabel lblSifraP;
	private JLabel lblDatum;	
	private JLabel lblKomada;
	private JLabel lblOpis;
	private JLabel lblOtpremljeno;

	private JTextField txtSifraF;
	private JTextField txtSifraR;
	private JTextField txtNazivR;
	private JTextField txtSifraP;	
	private JTextField txtKomada;
	private JTextField txtOpis;
	private JTextField txtOtpremljeno;

	private JButton btnRoba;

	private JDateChooser txtDatum;

	private Dimension datePreferedSize;

	// private JLabel lblPrazno;

	public FakturisanaPanel() {

		lblSifraF = new JLabel("Šifra fakture");
		lblSifraR = new JLabel("Šifra robe");
		lblNazivR = new JLabel("Naziv robe");
		lblSifraP = new JLabel("Šifra porudzbine");
		lblDatum = new JLabel("Datum isporuke");		
		lblKomada = new JLabel("Fakturisano komada");
		lblOpis = new JLabel("Opis");
		lblOtpremljeno = new JLabel("Otpremljeno");
		// lblPrazno = new JLabel("     ");

		txtSifraF = new JTextField(20);
		txtSifraR = new JTextField(20);
		txtNazivR = new JTextField(20);
		txtSifraP = new JTextField(20);		
		txtKomada = new JTextField(10);
		txtOpis = new JTextField(20);
		txtOtpremljeno = new JTextField(3);

		datePreferedSize = new Dimension(100, 20);

		txtDatum = new JDateChooser();
		txtDatum.setDateFormatString("yyyy-MM-dd");
		txtDatum.setPreferredSize(datePreferedSize);

		btnRoba = new JButton("Dodaj robu u fakturu");
		btnRoba.setEnabled(false);

		add(lblSifraF);
		add(txtSifraF);
		
		add(lblSifraR);
		add(txtSifraR);

		add(lblNazivR);
		add(txtNazivR);

		add(lblSifraP);
		add(txtSifraP, "wrap");

		add(lblDatum);
		add(txtDatum);		

		add(lblKomada);
		add(txtKomada);

		add(lblOpis);
		add(txtOpis, "wrap");

		add(lblOtpremljeno);
		add(txtOtpremljeno);

		// add(lblPrazno);

		add(btnRoba);

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

	public JLabel getLblSifraP() {
		return lblSifraP;
	}

	public void setLblSifraP(JLabel lblSifraP) {
		this.lblSifraP = lblSifraP;
	}

	public JLabel getLblDatum() {
		return lblDatum;
	}

	public void setLblDatum(JLabel lblDatum) {
		this.lblDatum = lblDatum;
	}

	public JLabel getLblSifraF() {
		return lblSifraF;
	}

	public void setLblSifraF(JLabel lblSifraF) {
		this.lblSifraF = lblSifraF;
	}

	public JLabel getLblKomada() {
		return lblKomada;
	}

	public void setLblKomada(JLabel lblKomada) {
		this.lblKomada = lblKomada;
	}

	public JLabel getLblOpis() {
		return lblOpis;
	}

	public void setLblOpis(JLabel lblOpis) {
		this.lblOpis = lblOpis;
	}

	public JLabel getLblOtpremljeno() {
		return lblOtpremljeno;
	}

	public void setLblOtpremljeno(JLabel lblOtpremljeno) {
		this.lblOtpremljeno = lblOtpremljeno;
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

	public JTextField getTxtSifraP() {
		return txtSifraP;
	}

	public void setTxtSifraP(JTextField txtSifraP) {
		this.txtSifraP = txtSifraP;
	}

	public JTextField getTxtSifraF() {
		return txtSifraF;
	}

	public void setTxtSifraF(JTextField txtSifraF) {
		this.txtSifraF = txtSifraF;
	}

	public JTextField getTxtKomada() {
		return txtKomada;
	}

	public void setTxtKomada(JTextField txtKomada) {
		this.txtKomada = txtKomada;
	}

	public JTextField getTxtOpis() {
		return txtOpis;
	}

	public void setTxtOpis(JTextField txtOpis) {
		this.txtOpis = txtOpis;
	}

	public JTextField getTxtOtpremljeno() {
		return txtOtpremljeno;
	}

	public void setTxtOtpremljeno(JTextField txtOtpremljeno) {
		this.txtOtpremljeno = txtOtpremljeno;
	}

	public JButton getBtnRoba() {
		return btnRoba;
	}

	public void setBtnRoba(JButton btnRoba) {
		this.btnRoba = btnRoba;
	}

	public JDateChooser getTxtDatum() {
		return txtDatum;
	}

	public void setTxtDatum(JDateChooser txtDatum) {
		this.txtDatum = txtDatum;
	}

}
