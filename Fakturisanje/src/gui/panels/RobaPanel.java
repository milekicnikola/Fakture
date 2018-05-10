package gui.panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RobaPanel extends StandardPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel lblSifra;
	private JLabel lblInterna;
	private JLabel lblMagacin;
	private JLabel lblNaziv;
	private JLabel lblJedinicaMere;
	private JLabel lblKomada;
	private JLabel lblTezina;
	private JLabel lblKvalitet;
	private JLabel lblEvri;
	private JLabel lblRoni;
		
	private JTextField txtSifra;
	private JTextField txtInterna;
	private JTextField txtMagacin;
	private JTextField txtNaziv;
	private JTextField txtJedinicaMere;
	private JTextField txtKomada;
	private JTextField txtTezina;
	private JTextField txtKvalitet;
	private JTextField txtEvri;
	private JTextField txtRoni;	

	private JButton btnMagacin;
	
	public RobaPanel() {

		lblSifra = new JLabel("Sifra robe");
		lblInterna = new JLabel("Interna sifra robe");
		lblMagacin = new JLabel("Magacin");
		lblNaziv = new JLabel("Naziv robe");
		lblJedinicaMere = new JLabel("Jedinica mere robe");
		lblKomada = new JLabel("Komada u setu");
		lblTezina = new JLabel("Tezina robe");
		lblKvalitet = new JLabel("Kvalitet robe");
		lblEvri = new JLabel("Cena robe u evrima");
		lblRoni = new JLabel("Cena robe u ronima");		
		
		txtSifra = new JTextField(20);
		txtInterna = new JTextField(20);
		txtMagacin = new JTextField(30);
		txtNaziv = new JTextField(30);
		txtJedinicaMere = new JTextField(10);
		txtKomada = new JTextField(4);
		txtTezina = new JTextField(10);
		txtKvalitet = new JTextField(20);
		txtEvri = new JTextField(10);
		txtRoni = new JTextField(10);
		//lblPrazno = new JLabel("     ");
		
		txtMagacin.setEditable(false);
		btnMagacin = new JButton("Izaberi magacin");
		btnMagacin.setEnabled(false);

		add(lblSifra);
		add(txtSifra);
		
		add(lblInterna);
		add(txtInterna);
		
		add(lblMagacin);
		add(txtMagacin);
		add(btnMagacin);
		
		add(lblNaziv);
		add(txtNaziv, "wrap");
		
		add(lblJedinicaMere);
		add(txtJedinicaMere);
		
		add(lblKomada);
		add(txtKomada);
		
		add(lblTezina);
		add(txtTezina);
		
		add(lblKvalitet);
		add(txtKvalitet, "wrap");
		
		add(lblEvri);
		add(txtEvri);
		
		add(lblRoni);
		add(txtRoni);		
		//add(lblPrazno);		
		add(btnConfirm);		
		add(btnCancel);

	}
	
	public JLabel getLblSifra() {
		return lblSifra;
	}

	public void setLblSifra(JLabel lblSifra) {
		this.lblSifra = lblSifra;
	}

	public JLabel getLblInterna() {
		return lblInterna;
	}

	public void setLblInterna(JLabel lblInterna) {
		this.lblInterna = lblInterna;
	}

	public JLabel getLblMagacin() {
		return lblMagacin;
	}

	public void setLblMagacin(JLabel lblMagacin) {
		this.lblMagacin = lblMagacin;
	}

	public JLabel getLblNaziv() {
		return lblNaziv;
	}

	public void setLblNaziv(JLabel lblNaziv) {
		this.lblNaziv = lblNaziv;
	}

	public JLabel getLblJedinicaMere() {
		return lblJedinicaMere;
	}

	public void setLblJedinicaMere(JLabel lblJedinicaMere) {
		this.lblJedinicaMere = lblJedinicaMere;
	}

	public JLabel getLblKomada() {
		return lblKomada;
	}

	public void setLblKomada(JLabel lblKomada) {
		this.lblKomada = lblKomada;
	}

	public JLabel getLblTezina() {
		return lblTezina;
	}

	public void setLblTezina(JLabel lblTezina) {
		this.lblTezina = lblTezina;
	}

	public JLabel getLblKvalitet() {
		return lblKvalitet;
	}

	public void setLblKvalitet(JLabel lblKvalitet) {
		this.lblKvalitet = lblKvalitet;
	}

	public JLabel getLblEvri() {
		return lblEvri;
	}

	public void setLblEvri(JLabel lblEvri) {
		this.lblEvri = lblEvri;
	}

	public JLabel getLblRoni() {
		return lblRoni;
	}

	public void setLblRoni(JLabel lblRoni) {
		this.lblRoni = lblRoni;
	}

	public JTextField getTxtSifra() {
		return txtSifra;
	}

	public void setTxtSifra(JTextField txtSifra) {
		this.txtSifra = txtSifra;
	}

	public JTextField getTxtInterna() {
		return txtInterna;
	}

	public void setTxtInterna(JTextField txtInterna) {
		this.txtInterna = txtInterna;
	}

	public JTextField getTxtMagacin() {
		return txtMagacin;
	}

	public void setTxtMagacin(JTextField txtMagacin) {
		this.txtMagacin = txtMagacin;
	}

	public JTextField getTxtNaziv() {
		return txtNaziv;
	}

	public void setTxtNaziv(JTextField txtNaziv) {
		this.txtNaziv = txtNaziv;
	}

	public JTextField getTxtJedinicaMere() {
		return txtJedinicaMere;
	}

	public void setTxtJedinicaMere(JTextField txtJedinicaMere) {
		this.txtJedinicaMere = txtJedinicaMere;
	}

	public JTextField getTxtKomada() {
		return txtKomada;
	}

	public void setTxtKomada(JTextField txtKomada) {
		this.txtKomada = txtKomada;
	}

	public JTextField getTxtTezina() {
		return txtTezina;
	}

	public void setTxtTezina(JTextField txtTezina) {
		this.txtTezina = txtTezina;
	}

	public JTextField getTxtKvalitet() {
		return txtKvalitet;
	}

	public void setTxtKvalitet(JTextField txtKvalitet) {
		this.txtKvalitet = txtKvalitet;
	}

	public JTextField getTxtEvri() {
		return txtEvri;
	}

	public void setTxtEvri(JTextField txtEvri) {
		this.txtEvri = txtEvri;
	}

	public JTextField getTxtRoni() {
		return txtRoni;
	}

	public void setTxtRoni(JTextField txtRoni) {
		this.txtRoni = txtRoni;
	}
	
	public JButton getBtnMagacin() {
		return btnMagacin;
	}

	public void setBtnMagacin(JButton btnMagacin) {
		this.btnMagacin = btnMagacin;
	}

}
