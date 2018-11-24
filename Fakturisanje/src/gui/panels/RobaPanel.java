package gui.panels;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RobaPanel extends StandardPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblSifra;
	private JLabel lblInterna;
	private JLabel lblNaziv;
	private JLabel lblInterni;
	private JLabel lblJedinicaMere;
	private JLabel lblKomada;
	private JLabel lblTezina;
	private JLabel lblPrevod;
	private JLabel lblRoni;

	private JTextField txtSifra;
	private JTextField txtInterna;
	private JTextField txtNaziv;
	private JTextField txtInterni;
	private JComboBox<String> cmbJedinicaMere;
	private JTextField txtKomada;
	private JTextField txtTezina;
	private JComboBox<String> cmbPrevod;
	private JTextField txtRoni;

	public RobaPanel() {

		lblSifra = new JLabel("Šifra robe");
		lblInterna = new JLabel("Interna šifra robe");
		lblNaziv = new JLabel("Naziv robe");
		lblInterni = new JLabel("Interni naziv");
		lblJedinicaMere = new JLabel("Jedinica mere robe");
		lblKomada = new JLabel("Komada u setu");
		lblPrevod = new JLabel("Prevod");
		lblTezina = new JLabel("Težina robe");
		lblRoni = new JLabel("Cena robe u ronima");

		txtSifra = new JTextField(20);
		txtInterna = new JTextField(20);
		txtNaziv = new JTextField(20);
		txtInterni = new JTextField(20);
		cmbJedinicaMere = new JComboBox<String>();
		cmbJedinicaMere.addItem("komad");
		cmbJedinicaMere.addItem("set");
		cmbJedinicaMere.addItem("metar");
		cmbJedinicaMere.setEditable(false);
		txtKomada = new JTextField(4);
		txtKomada.setText("1");
		txtKomada.setEditable(false);
		txtTezina = new JTextField(10);
		txtRoni = new JTextField(10);
		cmbPrevod = new JComboBox<String>();
		cmbPrevod.addItem("el. constructi metalice bucati");
		cmbPrevod.addItem("el. constructi metalice in metri");
		cmbPrevod.addItem("el. constructi metalice seturi");
		cmbPrevod.addItem("cuti carton");
		cmbPrevod.addItem("separator carton");
		cmbPrevod.addItem("coltar carton");
		cmbPrevod.addItem("pakovanje");
		cmbPrevod.setEditable(false);		

		add(lblSifra);
		add(txtSifra);

		add(lblInterna);
		add(txtInterna);

		add(lblNaziv);
		add(txtNaziv);

		add(lblInterni);
		add(txtInterni, "wrap");

		add(lblJedinicaMere);
		add(cmbJedinicaMere);

		add(lblKomada);
		add(txtKomada);

		add(lblPrevod);
		add(cmbPrevod);

		add(lblTezina);
		add(txtTezina, "wrap");

		add(lblRoni);
		add(txtRoni);

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

	public JTextField getTxtNaziv() {
		return txtNaziv;
	}

	public void setTxtNaziv(JTextField txtNaziv) {
		this.txtNaziv = txtNaziv;
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

	public JTextField getTxtRoni() {
		return txtRoni;
	}

	public void setTxtRoni(JTextField txtRoni) {
		this.txtRoni = txtRoni;
	}

	public JComboBox<String> getCmbJedinicaMere() {
		return cmbJedinicaMere;
	}

	public void setCmbJedinicaMere(JComboBox<String> cmbJedinicaMere) {
		this.cmbJedinicaMere = cmbJedinicaMere;
	}

	public JLabel getLblPrevod() {
		return lblPrevod;
	}

	public void setLblPrevod(JLabel lblPrevod) {
		this.lblPrevod = lblPrevod;
	}

	public JComboBox<String> getCmbPrevod() {
		return cmbPrevod;
	}

	public void setCmbPrevod(JComboBox<String> cmbPrevod) {
		this.cmbPrevod = cmbPrevod;
	}

	public JLabel getLblInterni() {
		return lblInterni;
	}

	public void setLblInterni(JLabel lblInterni) {
		this.lblInterni = lblInterni;
	}

	public JTextField getTxtInterni() {
		return txtInterni;
	}

	public void setTxtInterni(JTextField txtInterni) {
		this.txtInterni = txtInterni;
	}

}
