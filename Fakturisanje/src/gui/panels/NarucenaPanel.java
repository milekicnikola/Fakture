package gui.panels;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class NarucenaPanel extends StandardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lblSifraP;
	private JLabel lblSifraR;
	private JLabel lblNazivR;	
	private JLabel lblNaruceno;
	private JLabel lblPoslato;
	private JLabel lblOstalo;
	private JLabel lblDatum;
	private JLabel lblKo;
	private JLabel lblNapomena;

	private JTextField txtSifraP;
	private JTextField txtSifraR;
	private JTextField txtNazivR;		
	private JTextField txtNaruceno;
	private JTextField txtPoslato;
	private JTextField txtOstalo; 
	private JTextField txtKo;
	private JTextField txtNapomena;
	
	private JButton btnRoba;	

	private JDateChooser txtDatum;

	private Dimension datePreferedSize;
	
	private JLabel lblPrazno;

	public NarucenaPanel() {

		lblSifraP = new JLabel("Šifra porudzbine");
		lblSifraR = new JLabel("Šifra robe");
		lblNazivR = new JLabel("Naziv robe");				
		lblNaruceno = new JLabel("Naručeno komada");
		lblPoslato = new JLabel("Poslato komada");
		lblOstalo = new JLabel("Ostalo komada");		
		lblDatum = new JLabel("Datum isporuke");
		lblKo = new JLabel("Ko radi");
		lblNapomena = new JLabel("Napomena");
		lblPrazno = new JLabel("     ");

		txtSifraP = new JTextField(15);
		txtSifraR = new JTextField(15);
		txtNazivR = new JTextField(20);				
		txtNaruceno = new JTextField(10);
		txtPoslato = new JTextField(10);
		txtOstalo = new JTextField(10);	
		txtKo = new JTextField(20);
		txtNapomena = new JTextField(20);
		
		datePreferedSize = new Dimension(100, 20);

		txtDatum = new JDateChooser();
		txtDatum.setDateFormatString("yyyy-MM-dd");
		txtDatum.setPreferredSize(datePreferedSize);
		
		btnRoba = new JButton("Dodaj robu");
		btnRoba.setEnabled(false);

		add(lblSifraP);
		add(txtSifraP);
		
		add(lblSifraR);
		add(txtSifraR);
		
		add(lblNazivR);
		add(txtNazivR);
		
		add(btnRoba, "wrap");
		
		add(lblDatum);
		add(txtDatum);

		add(lblNaruceno);
		add(txtNaruceno);

		add(lblPoslato);
		add(txtPoslato);

		add(lblOstalo);
		add(txtOstalo, "wrap");		
		
		add(lblKo);
		add(txtKo);
		
		add(lblNapomena);
		add(txtNapomena);
		
		add(lblPrazno);			
		
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

	public JLabel getLblNaruceno() {
		return lblNaruceno;
	}

	public void setLblNaruceno(JLabel lblNaruceno) {
		this.lblNaruceno = lblNaruceno;
	}

	public JLabel getLblPoslato() {
		return lblPoslato;
	}

	public void setLblPoslato(JLabel lblPoslato) {
		this.lblPoslato = lblPoslato;
	}

	public JLabel getLblOstalo() {
		return lblOstalo;
	}

	public void setLblOstalo(JLabel lblOstalo) {
		this.lblOstalo = lblOstalo;
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

	public JTextField getTxtNaruceno() {
		return txtNaruceno;
	}

	public void setTxtNaruceno(JTextField txtNaruceno) {
		this.txtNaruceno = txtNaruceno;
	}

	public JTextField getTxtPoslato() {
		return txtPoslato;
	}

	public void setTxtPoslato(JTextField txtPoslato) {
		this.txtPoslato = txtPoslato;
	}

	public JTextField getTxtOstalo() {
		return txtOstalo;
	}

	public void setTxtOstalo(JTextField txtOstalo) {
		this.txtOstalo = txtOstalo;
	}
	
	public JButton getBtnRoba() {
		return btnRoba;
	}

	public void setBtnRoba(JButton btnRoba) {
		this.btnRoba = btnRoba;
	}
	
	public JLabel getLblDatum() {
		return lblDatum;
	}

	public void setLblDatum(JLabel lblDatum) {
		this.lblDatum = lblDatum;
	}

	public JDateChooser getTxtDatum() {
		return txtDatum;
	}

	public void setTxtDatum(JDateChooser txtDatum) {
		this.txtDatum = txtDatum;
	}
	
	public JLabel getLblKo() {
		return lblKo;
	}

	public void setLblKo(JLabel lblKo) {
		this.lblKo = lblKo;
	}

	public JTextField getTxtKo() {
		return txtKo;
	}

	public void setTxtKo(JTextField txtKo) {
		this.txtKo = txtKo;
	}

	public JLabel getLblNapomena() {
		return lblNapomena;
	}

	public void setLblNapomena(JLabel lblNapomena) {
		this.lblNapomena = lblNapomena;
	}

	public JTextField getTxtNapomena() {
		return txtNapomena;
	}

	public void setTxtNapomena(JTextField txtNapomena) {
		this.txtNapomena = txtNapomena;
	}
}
