package gui.panels;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class KupciPanel extends StandardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblSifra;
	private JLabel lblNaziv;
	private JLabel lblNaziv2;
	private JLabel lblAdresa;
	private JLabel lblGrad;
	private JLabel lblDrzava;

	private JTextField txtSifra;
	private JTextField txtNaziv;
	private JTextField txtNaziv2;
	private JTextField txtAdresa;
	private JTextField txtGrad;
	private JTextField txtDrzava;

	public KupciPanel() {

		lblSifra = new JLabel("Šifra kupca");
		lblNaziv = new JLabel("Naziv kupca");
		lblNaziv2 = new JLabel("Drugi naziv kupca");
		lblAdresa = new JLabel("Adresa kupca");
		lblGrad = new JLabel("Grad kupca");
		lblDrzava = new JLabel("Država kupca");

		txtSifra = new JTextField(3);
		txtNaziv = new JTextField(30);
		txtNaziv2 = new JTextField(30);
		txtAdresa = new JTextField(30);
		txtGrad = new JTextField(20);
		txtDrzava = new JTextField(20);

		add(lblSifra);
		add(txtSifra);

		add(lblNaziv);
		add(txtNaziv);

		add(lblNaziv2);
		add(txtNaziv2, "wrap");

		add(lblAdresa);
		add(txtAdresa);

		add(lblGrad);
		add(txtGrad);

		add(lblDrzava);
		add(txtDrzava);

		add(btnConfirm);
		add(btnCancel);

	}

	public JLabel getLblSifra() {
		return lblSifra;
	}

	public void setLblSifra(JLabel lblSifra) {
		this.lblSifra = lblSifra;
	}

	public JLabel getLblNaziv() {
		return lblNaziv;
	}

	public void setLblNaziv(JLabel lblNaziv) {
		this.lblNaziv = lblNaziv;
	}

	public JLabel getLblNaziv2() {
		return lblNaziv2;
	}

	public void setLblNaziv2(JLabel lblNaziv2) {
		this.lblNaziv2 = lblNaziv2;
	}

	public JLabel getLblAdresa() {
		return lblAdresa;
	}

	public void setLblAdresa(JLabel lblAdresa) {
		this.lblAdresa = lblAdresa;
	}

	public JLabel getLblGrad() {
		return lblGrad;
	}

	public void setLblGrad(JLabel lblGrad) {
		this.lblGrad = lblGrad;
	}

	public JLabel getLblDrzava() {
		return lblDrzava;
	}

	public void setLblDrzava(JLabel lblDrzava) {
		this.lblDrzava = lblDrzava;
	}

	public JTextField getTxtSifra() {
		return txtSifra;
	}

	public void setTxtSifra(JTextField txtSifra) {
		this.txtSifra = txtSifra;
	}

	public JTextField getTxtNaziv() {
		return txtNaziv;
	}

	public void setTxtNaziv(JTextField txtNaziv) {
		this.txtNaziv = txtNaziv;
	}

	public JTextField getTxtNaziv2() {
		return txtNaziv2;
	}

	public void setTxtNaziv2(JTextField txtNaziv2) {
		this.txtNaziv2 = txtNaziv2;
	}

	public JTextField getTxtAdresa() {
		return txtAdresa;
	}

	public void setTxtAdresa(JTextField txtAdresa) {
		this.txtAdresa = txtAdresa;
	}

	public JTextField getTxtGrad() {
		return txtGrad;
	}

	public void setTxtGrad(JTextField txtGrad) {
		this.txtGrad = txtGrad;
	}

	public JTextField getTxtDrzava() {
		return txtDrzava;
	}

	public void setTxtDrzava(JTextField txtDrzava) {
		this.txtDrzava = txtDrzava;
	}

}
