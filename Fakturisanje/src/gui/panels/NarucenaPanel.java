package gui.panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NarucenaPanel extends StandardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lblSifraR;
	private JLabel lblNazivR;
	private JLabel lblSifraP;
	private JLabel lblNaruceno;
	private JLabel lblPoslato;
	private JLabel lblOstalo;	

	private JTextField txtSifraR;
	private JTextField txtNazivR;
	private JTextField txtSifraP;	
	private JTextField txtNaruceno;
	private JTextField txtPoslato;
	private JTextField txtOstalo; 
	
	private JButton btnRoba;	

	public NarucenaPanel() {

		lblSifraR = new JLabel("Šifra robe");
		lblNazivR = new JLabel("Naziv robe");
		lblSifraP = new JLabel("Šifra porudzbine");		
		lblNaruceno = new JLabel("Naručeno komada");
		lblPoslato = new JLabel("Poslato komada");
		lblOstalo = new JLabel("Ostalo komada");		

		txtSifraR = new JTextField(20);
		txtNazivR = new JTextField(20);
		txtSifraP = new JTextField(20);		
		txtNaruceno = new JTextField(10);
		txtPoslato = new JTextField(10);
		txtOstalo = new JTextField(10);		
		
		btnRoba = new JButton("Dodaj robu u porudzbinu");
		btnRoba.setEnabled(false);

		add(lblSifraR);
		add(txtSifraR);
		
		add(lblNazivR);
		add(txtNazivR);

		add(lblSifraP);
		add(txtSifraP, "wrap");		

		add(lblNaruceno);
		add(txtNaruceno);

		add(lblPoslato);
		add(txtPoslato);

		add(lblOstalo);
		add(txtOstalo);
		
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

}
