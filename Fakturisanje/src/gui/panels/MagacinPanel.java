package gui.panels;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MagacinPanel extends StandardPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblSifra;
	private JLabel lblNaziv;
	private JLabel lblAdresa;
	private JLabel lblSef;
	private JLabel lblTelefon;

	private JTextField txtSifra;
	private JTextField txtNaziv;
	private JTextField txtAdresa;
	private JTextField txtSef;
	private JTextField txtTelefon;

	public MagacinPanel() {

		lblSifra = new JLabel("Šifra magacina");
		lblNaziv = new JLabel("Naziv magacina");
		lblAdresa = new JLabel("Adresa magacina");
		lblSef = new JLabel("Šef magacina");
		lblTelefon = new JLabel("Telefon magacina");

		txtSifra = new JTextField(5);
		txtNaziv = new JTextField(30);
		txtAdresa = new JTextField(30);
		txtSef = new JTextField(30);
		txtTelefon = new JTextField(20);

		add(lblSifra);
		add(txtSifra);

		add(lblNaziv);
		add(txtNaziv);

		add(lblAdresa);
		add(txtAdresa, "wrap");

		add(lblSef);
		add(txtSef);

		add(lblTelefon);
		add(txtTelefon);

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

	public JLabel getLblAdresa() {
		return lblAdresa;
	}

	public void setLblAdresa(JLabel lblAdresa) {
		this.lblAdresa = lblAdresa;
	}

	public JLabel getLblSef() {
		return lblSef;
	}

	public void setLblSef(JLabel lblSef) {
		this.lblSef = lblSef;
	}

	public JLabel getLblTelefon() {
		return lblTelefon;
	}

	public void setLblTelefon(JLabel lblTelefon) {
		this.lblTelefon = lblTelefon;
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

	public JTextField getTxtAdresa() {
		return txtAdresa;
	}

	public void setTxtAdresa(JTextField txtAdresa) {
		this.txtAdresa = txtAdresa;
	}

	public JTextField getTxtSef() {
		return txtSef;
	}

	public void setTxtSef(JTextField txtSef) {
		this.txtSef = txtSef;
	}

	public JTextField getTxtTelefon() {
		return txtTelefon;
	}

	public void setTxtTelefon(JTextField txtTelefon) {
		this.txtTelefon = txtTelefon;
	}

}
