package gui.panels;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OtpremljenaPanel extends StandardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblOtpremnica;
	private JLabel lblSifraF;
	private JLabel lblSifraR;
	private JLabel lblNazivR;
	private JLabel lblSifraP;
	private JLabel lblDatum;
	private JLabel lblNaruceno;
	private JLabel lblKomada;
	private JLabel lblOpis;
	private JLabel lblStatus;

	private JTextField txtOtpremnica;
	private JTextField txtSifraF;
	private JTextField txtSifraR;
	private JTextField txtNazivR;
	private JTextField txtSifraP;
	private JTextField txtNaruceno;
	private JTextField txtKomada;
	private JTextArea taOpis;
	private JTextField txtStatus;
	private JTextField txtDatum;

	public OtpremljenaPanel() {		

		lblOtpremnica = new JLabel("Šifra otpremnice");
		lblSifraF = new JLabel("Šifra fakture");
		lblSifraR = new JLabel("Šifra robe");
		lblNazivR = new JLabel("Naziv robe");
		lblSifraP = new JLabel("Šifra porudzbine");
		lblDatum = new JLabel("Datum isporuke");
		lblNaruceno = new JLabel("Naručeno komada");
		lblKomada = new JLabel("Fakturisano komada");
		lblOpis = new JLabel("Opis");
		lblStatus = new JLabel("Status");		

		txtOtpremnica = new JTextField(20);
		txtSifraF = new JTextField(20);
		txtSifraR = new JTextField(20);
		txtNazivR = new JTextField(20);
		txtSifraP = new JTextField(20);
		txtNaruceno = new JTextField(10);
		txtKomada = new JTextField(10);
		txtDatum = new JTextField(10);
		taOpis = new JTextArea(4, 20);
		JScrollPane scrollPane = new JScrollPane(taOpis);		
		txtStatus = new JTextField(15);
					
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
		
		add(lblNaruceno);
		add(txtNaruceno);

		add(lblKomada);
		add(txtKomada);		

		add(lblStatus);
		add(txtStatus, "wrap");
		
		add(lblOpis);
		add(scrollPane, "height 80::, span 2 2");	
		
		add(lblOtpremnica);
		add(txtOtpremnica);

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

	public JLabel getLblStatus() {
		return lblStatus;
	}

	public void setLblStatus(JLabel lblStatus) {
		this.lblStatus = lblStatus;
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

	public JTextArea getTaOpis() {
		return taOpis;
	}

	public void setTaOpis(JTextArea taOpis) {
		this.taOpis = taOpis;
	}

	public JTextField getTxtStatus() {
		return txtStatus;
	}

	public void setTxtStatus(JTextField txtStatus) {
		this.txtStatus = txtStatus;
	}	
	
	public JLabel getLblOtpremnica() {
		return lblOtpremnica;
	}

	public void setLblOtpremnica(JLabel lblOtpremnica) {
		this.lblOtpremnica = lblOtpremnica;
	}

	public JTextField getTxtOtpremnica() {
		return txtOtpremnica;
	}

	public void setTxtOtpremnica(JTextField txtOtpremnica) {
		this.txtOtpremnica = txtOtpremnica;
	}

	public JTextField getTxtDatum() {
		return txtDatum;
	}

	public void setTxtDatum(JTextField txtDatum) {
		this.txtDatum = txtDatum;
	}
	
	public JLabel getLblNaruceno() {
		return lblNaruceno;
	}

	public void setLblNaruceno(JLabel lblNaruceno) {
		this.lblNaruceno = lblNaruceno;
	}

	public JTextField getTxtNaruceno() {
		return txtNaruceno;
	}

	public void setTxtNaruceno(JTextField txtNaruceno) {
		this.txtNaruceno = txtNaruceno;
	}

}
