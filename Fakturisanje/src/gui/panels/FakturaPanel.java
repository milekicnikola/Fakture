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
	/*private JLabel lblSifraK;
	private JLabel lblNazivK;*/
	private JLabel lblKorisnik;
	private JLabel lblParitet;
	private JLabel lblBruto;
	private JLabel lblNeto;
	private JLabel lblUkupno;	

	private JTextField txtSifra;
	/*private JTextField txtSifraK;
	private JTextField txtNazivK;*/
	private JTextField txtKorisnik;
	private JTextField txtParitet;
	private JTextField txtBruto;
	private JTextField txtNeto;
	private JTextField txtUkupno;
	
	//private JButton btnKupac;

	private JDateChooser txtDatum;

	private Dimension datePreferedSize;
	
	public FakturaPanel() {

		lblSifra = new JLabel("Šifra fakture");
		lblDatum = new JLabel("Datum fakture");
		/*lblSifraK = new JLabel("PIB kupca");
		lblNazivK = new JLabel("Naziv kupca");*/
		lblKorisnik = new JLabel("Korisnik");
		lblParitet = new JLabel("Paritet");
		lblBruto = new JLabel("Bruto");
		lblNeto = new JLabel("Neto");
		lblUkupno = new JLabel("Ukupno komada robe");
		

		txtSifra = new JTextField(20);
		/*txtSifraK = new JTextField(10);
		txtNazivK = new JTextField(20);*/
		txtKorisnik = new JTextField(20);
		txtParitet = new JTextField(20);
		txtBruto = new JTextField(10);
		txtNeto = new JTextField(10);
		txtUkupno = new JTextField(10);		

		datePreferedSize = new Dimension(100, 20);

		txtDatum = new JDateChooser();
		txtDatum.setDateFormatString("yyyy-MM-dd");
		txtDatum.setPreferredSize(datePreferedSize);
		
		/*btnKupac = new JButton("Izaberi kupca");		
		btnKupac.setEnabled(false);*/

		add(lblSifra);
		add(txtSifra);
		
		add(lblDatum);
		add(txtDatum);
		
		/*add(lblSifraK);
		add(txtSifraK);

		add(lblNazivK);
		add(txtNazivK);
		add(btnKupac, "wrap");*/

		
		add(lblKorisnik);
		add(txtKorisnik, "wrap");
		
		add(lblParitet);
		add(txtParitet);
		
		add(lblBruto);
		add(txtBruto);
		
		add(lblNeto);
		add(txtNeto);
		
		add(lblUkupno);
		add(txtUkupno);		

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

	/*public JLabel getLblSifraK() {
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
	}*/

	public JLabel getLblKorisnik() {
		return lblKorisnik;
	}

	public void setLblKorisnik(JLabel lblKorisnik) {
		this.lblKorisnik = lblKorisnik;
	}

	public JLabel getLblParitet() {
		return lblParitet;
	}

	public void setLblParitet(JLabel lblParitet) {
		this.lblParitet = lblParitet;
	}

	public JLabel getLblBruto() {
		return lblBruto;
	}

	public void setLblBruto(JLabel lblBruto) {
		this.lblBruto = lblBruto;
	}

	public JLabel getLblNeto() {
		return lblNeto;
	}

	public void setLblNeto(JLabel lblNeto) {
		this.lblNeto = lblNeto;
	}

	public JLabel getLblUkupno() {
		return lblUkupno;
	}

	public void setLblUkupno(JLabel lblUkupno) {
		this.lblUkupno = lblUkupno;
	}

	public JTextField getTxtSifra() {
		return txtSifra;
	}

	public void setTxtSifra(JTextField txtSifra) {
		this.txtSifra = txtSifra;
	}

	/*public JTextField getTxtSifraK() {
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
	}*/

	public JTextField getTxtKorisnik() {
		return txtKorisnik;
	}

	public void setTxtKorisnik(JTextField txtKorisnik) {
		this.txtKorisnik = txtKorisnik;
	}

	public JTextField getTxtParitet() {
		return txtParitet;
	}

	public void setTxtParitet(JTextField txtParitet) {
		this.txtParitet = txtParitet;
	}

	public JTextField getTxtBruto() {
		return txtBruto;
	}

	public void setTxtBruto(JTextField txtBruto) {
		this.txtBruto = txtBruto;
	}

	public JTextField getTxtNeto() {
		return txtNeto;
	}

	public void setTxtNeto(JTextField txtNeto) {
		this.txtNeto = txtNeto;
	}

	public JTextField getTxtUkupno() {
		return txtUkupno;
	}

	public void setTxtUkupno(JTextField txtUkupno) {
		this.txtUkupno = txtUkupno;
	}

	/*public JButton getBtnKupac() {
		return btnKupac;
	}

	public void setBtnKupac(JButton btnKupac) {
		this.btnKupac = btnKupac;
	}*/

	public JDateChooser getTxtDatum() {
		return txtDatum;
	}

	public void setTxtDatum(JDateChooser txtDatum) {
		this.txtDatum = txtDatum;
	}
	
	

}
