package gui.dialogs;

import gui.MainFrame;
import gui.model.RobaTableModel;
import gui.panels.RobaPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import databaseConnection.DBConnection;

public class DialogRoba extends StandardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogRoba(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Roba");
		setIconImage(new ImageIcon("Images/roba.png").getImage());

		tableModel = new RobaTableModel(new String[] { "�ifra",
				"Interna �ifra", "�ifra magacina", "Naziv magacina", "Naziv",
				"Jedinica mere", "Komada u setu", "Te�ina", "Kvalitet",
				"Cena u evrima", "Cena u ronima" }, 0);

		panel = new RobaPanel();

		if (zoom)
			isZoom = true;

		initGUI();
		initStandardActions();
		initActions();

	}

	@Override
	public void initActions() {

		if (!isZoom) {

			toolbar.getBtnDelete().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (table.getSelectedRow() >= 0) {
						int dialogResult = JOptionPane
								.showConfirmDialog(
										getParent(),
										"Da li ste sigurni da �elite da obri�ete ovu robu?",
										"Brisanje sloga",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE);
						if (dialogResult == JOptionPane.YES_OPTION) {
							removeRow();
						}
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"Nijedna roba nije selektovana.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE,
								JOptionPane.WARNING_MESSAGE);
					}
				}
			});

			toolbar.getBtnUpdate().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (table.getSelectedRow() >= 0) {
						updateStateAndTextFields(State.AZURIRANJE);
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"Nijedna roba nije selektovana.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE,
								JOptionPane.WARNING_MESSAGE);
					}

				}
			});

			panel.getBtnCancel().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (state == State.DODAVANJE)
						clearAll();

					updateStateAndTextFields(State.POGLED);

				}
			});

			((RobaPanel) panel).getBtnMagacin().addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							DialogMagacin dialog = new DialogMagacin(MainFrame
									.getInstance(), true);
							dialog.setVisible(true);
							try {
								if (!dialog.getZoom1().equals(""))
									((RobaPanel) panel).getTxtSifraM().setText(
											dialog.getZoom1());
								if (!dialog.getZoom2().equals(""))
									((RobaPanel) panel).getTxtNazivM().setText(
											dialog.getZoom2());
							} catch (NullPointerException n) {
							}
						}
					});
		} else {
			toolbar.getBtnAdd().setEnabled(false);
			toolbar.getBtnDelete().setEnabled(false);
			toolbar.getBtnUpdate().setEnabled(false);

			panel.getBtnCancel().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
	}

	@Override
	public void sync() {
		int index = table.getSelectedRow();
		if (index < 0) {
			clearAll();
			toolbar.getBtnDelete().setEnabled(false);
			return;
		}
		String sifra = (String) tableModel.getValueAt(index, 0);
		String interna = (String) tableModel.getValueAt(index, 1);
		String sifraM = (String) tableModel.getValueAt(index, 2);
		String nazivM = (String) tableModel.getValueAt(index, 3);
		String naziv = (String) tableModel.getValueAt(index, 4);
		String jedinica = (String) tableModel.getValueAt(index, 5);
		String komada = (String) tableModel.getValueAt(index, 6);
		String tezina = (String) tableModel.getValueAt(index, 7);
		String kvalitet = (String) tableModel.getValueAt(index, 8);
		String evri = (String) tableModel.getValueAt(index, 9);
		String roni = (String) tableModel.getValueAt(index, 10);

		((RobaPanel) panel).getTxtSifra().setText(sifra);
		((RobaPanel) panel).getTxtInterna().setText(interna);
		((RobaPanel) panel).getTxtSifraM().setText(sifraM);
		((RobaPanel) panel).getTxtNazivM().setText(nazivM);
		((RobaPanel) panel).getTxtNaziv().setText(naziv);
		((RobaPanel) panel).getTxtJedinicaMere().setText(jedinica);
		((RobaPanel) panel).getTxtKomada().setText(komada);
		((RobaPanel) panel).getTxtTezina().setText(tezina);
		((RobaPanel) panel).getTxtKvalitet().setText(kvalitet);
		((RobaPanel) panel).getTxtEvri().setText(evri);
		((RobaPanel) panel).getTxtRoni().setText(roni);

	}

	@Override
	public void updateStateAndTextFields(State state) {
		if (this.state == State.PRETRAGA && state != State.PRETRAGA) {
			try {
				tableModel.open();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (state == State.POGLED) {
			allDisable();
			statusBar.getStatusState().setText("POGLED");
			this.state = State.POGLED;
		} else if (state == State.AZURIRANJE) {
			btnEnable();
			allEnable();
			((RobaPanel) panel).getTxtSifra().setEditable(false);
			((RobaPanel) panel).getTxtRoni().setEditable(false);
			statusBar.getStatusState().setText("A�URIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			((RobaPanel) panel).getTxtSifra().requestFocus();
			((RobaPanel) panel).getTxtRoni().setEditable(false);
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		double trenutniKurs = 1;

		try {
			trenutniKurs = trenutniKurs();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Gre�ka",
					JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException ex1) {
			trenutniKurs = 1;
		}

		String sifra = ((RobaPanel) panel).getTxtSifra().getText().trim();
		String interna = ((RobaPanel) panel).getTxtInterna().getText().trim();
		String sifraM = ((RobaPanel) panel).getTxtSifraM().getText().trim();
		String nazivM = ((RobaPanel) panel).getTxtNazivM().getText().trim();
		String naziv = ((RobaPanel) panel).getTxtNaziv().getText().trim();
		String jedinica = ((RobaPanel) panel).getTxtJedinicaMere().getText()
				.trim();
		String komada = ((RobaPanel) panel).getTxtKomada().getText().trim();
		String tezina = ((RobaPanel) panel).getTxtTezina().getText().trim();
		String kvalitet = ((RobaPanel) panel).getTxtKvalitet().getText().trim();
		String evri = ((RobaPanel) panel).getTxtEvri().getText().trim();

		String roni = "0";
		double roniD;

		if (evri != null && !evri.equals("")) {
			roniD = Double.parseDouble(evri) * trenutniKurs;
			roni = String.valueOf(roniD);
		}

		String[] params = { sifra, interna, sifraM, nazivM, naziv, jedinica,
				komada, tezina, kvalitet, evri, roni };

		try {
			RobaTableModel ctm = (RobaTableModel) table.getModel();
			int index = ctm.insertRow(params);
			table.setRowSelectionInterval(index, index);
			updateStateAndTextFields(State.DODAVANJE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Gre�ka",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void updateRow() {

		double trenutniKurs = 1;

		try {
			trenutniKurs = trenutniKurs();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Gre�ka",
					JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException ex1) {
			trenutniKurs = 1;
		}

		int i = table.getSelectedRow();
		if (i == -1)
			return;
		String interna = ((RobaPanel) panel).getTxtInterna().getText().trim();
		String sifraM = ((RobaPanel) panel).getTxtSifraM().getText().trim();
		String nazivM = ((RobaPanel) panel).getTxtNazivM().getText().trim();
		String naziv = ((RobaPanel) panel).getTxtNaziv().getText().trim();
		String jedinica = ((RobaPanel) panel).getTxtJedinicaMere().getText()
				.trim();
		String komada = ((RobaPanel) panel).getTxtKomada().getText().trim();
		String tezina = ((RobaPanel) panel).getTxtTezina().getText().trim();
		String kvalitet = ((RobaPanel) panel).getTxtKvalitet().getText().trim();
		String evri = ((RobaPanel) panel).getTxtEvri().getText().trim();
		String roni = "0";
		double roniD;

		if (evri != null && !evri.equals("")) {
			roniD = Double.parseDouble(evri) * trenutniKurs;
			roni = String.valueOf(roniD);
		}

		String[] params = { interna, sifraM, nazivM, naziv, jedinica, komada,
				tezina, kvalitet, evri, roni };
		int index = table.getSelectedRow();
		try {
			RobaTableModel ctm = (RobaTableModel) table.getModel();
			ctm.updateRow(index, params);
			updateStateAndTextFields(State.AZURIRANJE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Gre�ka",
					JOptionPane.ERROR_MESSAGE);
		}
		table.setRowSelectionInterval(index, index);
	}

	@Override
	public void search() {
		String sifra = ((RobaPanel) panel).getTxtSifra().getText().trim();
		String interna = ((RobaPanel) panel).getTxtInterna().getText().trim();
		String sifraM = ((RobaPanel) panel).getTxtSifraM().getText().trim();
		String naziv = ((RobaPanel) panel).getTxtNaziv().getText().trim();
		String jedinica = ((RobaPanel) panel).getTxtJedinicaMere().getText()
				.trim();
		String komada = ((RobaPanel) panel).getTxtKomada().getText().trim();
		String tezina = ((RobaPanel) panel).getTxtTezina().getText().trim();
		String kvalitet = ((RobaPanel) panel).getTxtKvalitet().getText().trim();
		String evri = ((RobaPanel) panel).getTxtEvri().getText().trim();
		String roni = ((RobaPanel) panel).getTxtRoni().getText().trim();

		String[] params = { sifra, interna, sifraM, naziv, jedinica, komada,
				tezina, kvalitet, evri, roni };

		try {
			RobaTableModel ctm = (RobaTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Gre�ka",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((RobaPanel) panel).getBtnConfirm().setEnabled(false);
		((RobaPanel) panel).getBtnCancel().setEnabled(false);
		((RobaPanel) panel).getBtnMagacin().setEnabled(false);
		((RobaPanel) panel).getTxtSifra().setEditable(false);
		((RobaPanel) panel).getTxtInterna().setEditable(false);
		((RobaPanel) panel).getTxtSifraM().setEditable(false);
		((RobaPanel) panel).getTxtNazivM().setEditable(false);
		((RobaPanel) panel).getTxtNaziv().setEditable(false);
		((RobaPanel) panel).getTxtJedinicaMere().setEditable(false);
		((RobaPanel) panel).getTxtKomada().setEditable(false);
		((RobaPanel) panel).getTxtTezina().setEditable(false);
		((RobaPanel) panel).getTxtKvalitet().setEditable(false);
		((RobaPanel) panel).getTxtEvri().setEditable(false);
		((RobaPanel) panel).getTxtRoni().setEditable(false);

	}

	public void allEnable() {
		((RobaPanel) panel).getBtnConfirm().setEnabled(true);
		((RobaPanel) panel).getBtnCancel().setEnabled(true);
		((RobaPanel) panel).getBtnMagacin().setEnabled(true);
		((RobaPanel) panel).getTxtSifra().setEditable(true);
		((RobaPanel) panel).getTxtInterna().setEditable(true);
		((RobaPanel) panel).getTxtNaziv().setEditable(true);
		((RobaPanel) panel).getTxtJedinicaMere().setEditable(true);
		((RobaPanel) panel).getTxtKomada().setEditable(true);
		((RobaPanel) panel).getTxtTezina().setEditable(true);
		((RobaPanel) panel).getTxtKvalitet().setEditable(true);
		((RobaPanel) panel).getTxtEvri().setEditable(true);
		((RobaPanel) panel).getTxtRoni().setEditable(true);
	}

	public void clearAll() {
		((RobaPanel) panel).getTxtSifra().setText("");
		((RobaPanel) panel).getTxtInterna().setText("");
		((RobaPanel) panel).getTxtSifraM().setText("");
		((RobaPanel) panel).getTxtNazivM().setText("");
		((RobaPanel) panel).getTxtNaziv().setText("");
		((RobaPanel) panel).getTxtJedinicaMere().setText("");
		((RobaPanel) panel).getTxtKomada().setText("");
		((RobaPanel) panel).getTxtTezina().setText("");
		((RobaPanel) panel).getTxtKvalitet().setText("");
		((RobaPanel) panel).getTxtEvri().setText("");
		((RobaPanel) panel).getTxtRoni().setText("");
	}

	public void btnEnable() {
		((RobaPanel) panel).getBtnConfirm().setEnabled(true);
		((RobaPanel) panel).getBtnCancel().setEnabled(true);
		((RobaPanel) panel).getBtnMagacin().setEnabled(true);
	}

	public double trenutniKurs() throws SQLException, NumberFormatException {

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection
				.getConnection()
				.prepareStatement(
						"SELECT ron_evro FROM kurs WHERE kurs.datum_kursa = (SELECT max(datum_kursa) FROM kurs)");

		ResultSet rset = selectStmt.executeQuery();

		String kurs = "";

		while (rset.next()) {
			kurs = rset.getString("RON_EVRO").trim();
		}

		rset.close();
		selectStmt.close();
		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_READ_COMMITTED);

		return Double.parseDouble(kurs);

	}

}
