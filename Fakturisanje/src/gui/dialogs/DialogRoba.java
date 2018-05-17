package gui.dialogs;

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

		tableModel = new RobaTableModel(new String[] { "Šifra",
				"Interna šifra", "Naziv", "Jedinica mere", "Komada u setu",
				"Težina", "Kvalitet", "Cena u evrima", "Cena u ronima" }, 0);

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
										"Da li ste sigurni da želite da obrišete ovu robu?",
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
		String naziv = (String) tableModel.getValueAt(index, 2);
		String jedinica = (String) tableModel.getValueAt(index, 3);
		String komada = (String) tableModel.getValueAt(index, 4);
		String tezina = (String) tableModel.getValueAt(index, 5);
		String kvalitet = (String) tableModel.getValueAt(index, 6);
		String evri = (String) tableModel.getValueAt(index, 7);
		String roni = (String) tableModel.getValueAt(index, 8);

		((RobaPanel) panel).getTxtSifra().setText(sifra);
		((RobaPanel) panel).getTxtInterna().setText(interna);
		((RobaPanel) panel).getTxtNaziv().setText(naziv);		

		if (jedinica.equals("komad"))
			((RobaPanel) panel).getCmbJedinicaMere().setSelectedIndex(0);
		else if (jedinica.equals("set"))
			((RobaPanel) panel).getCmbJedinicaMere().setSelectedIndex(1);
		else if (jedinica.equals("metar"))
			((RobaPanel) panel).getCmbJedinicaMere().setSelectedIndex(2);
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
			statusBar.getStatusState().setText("AŽURIRANJE");
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
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException ex1) {
			trenutniKurs = 1;
		}

		String sifra = ((RobaPanel) panel).getTxtSifra().getText().trim();
		String interna = ((RobaPanel) panel).getTxtInterna().getText().trim();
		String naziv = ((RobaPanel) panel).getTxtNaziv().getText().trim();
		String jedinica = ((RobaPanel) panel).getCmbJedinicaMere()
				.getSelectedItem().toString();
		String komada = ((RobaPanel) panel).getTxtKomada().getText().trim();
		String tezina = ((RobaPanel) panel).getTxtTezina().getText().trim();
		String kvalitet = ((RobaPanel) panel).getTxtKvalitet().getText().trim();
		String evri = ((RobaPanel) panel).getTxtEvri().getText().trim();

		String mera = "1";

		if (jedinica.equals("set"))
			mera = "2";
		else if (jedinica.equals("metar"))
			mera = "3";
		else
			mera = "1";

		String roni = "0";
		double roniD;

		if (evri != null && !evri.equals("")) {
			roniD = Double.parseDouble(evri) * trenutniKurs;
			roni = String.valueOf(roniD);
		}

		String[] params = { sifra, mera, interna, naziv, jedinica, komada, tezina,
				kvalitet, evri, roni };

		try {
			RobaTableModel ctm = (RobaTableModel) table.getModel();
			int index = ctm.insertRow(params);
			table.setRowSelectionInterval(index, index);
			updateStateAndTextFields(State.DODAVANJE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void updateRow() {

		double trenutniKurs = 1;

		try {
			trenutniKurs = trenutniKurs();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException ex1) {
			trenutniKurs = 1;
		}

		int i = table.getSelectedRow();
		if (i == -1)
			return;
		String interna = ((RobaPanel) panel).getTxtInterna().getText().trim();
		String naziv = ((RobaPanel) panel).getTxtNaziv().getText().trim();
		String jedinica = ((RobaPanel) panel).getCmbJedinicaMere()
				.getSelectedItem().toString();
		String komada = ((RobaPanel) panel).getTxtKomada().getText().trim();
		String tezina = ((RobaPanel) panel).getTxtTezina().getText().trim();
		String kvalitet = ((RobaPanel) panel).getTxtKvalitet().getText().trim();
		String evri = ((RobaPanel) panel).getTxtEvri().getText().trim();

		String mera = "1";

		if (jedinica.equals("set"))
			mera = "2";
		else if (jedinica.equals("metar"))
			mera = "3";
		else
			mera = "1";

		String roni = "0";
		double roniD;

		if (evri != null && !evri.equals("")) {
			roniD = Double.parseDouble(evri) * trenutniKurs;
			roni = String.valueOf(roniD);
		}

		String[] params = { mera, interna, naziv, jedinica, komada, tezina, kvalitet,
				evri, roni };
		int index = table.getSelectedRow();
		try {
			RobaTableModel ctm = (RobaTableModel) table.getModel();
			ctm.updateRow(index, params);
			updateStateAndTextFields(State.AZURIRANJE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
		table.setRowSelectionInterval(index, index);
	}

	@Override
	public void search() {
		String sifra = ((RobaPanel) panel).getTxtSifra().getText().trim();
		String interna = ((RobaPanel) panel).getTxtInterna().getText().trim();
		String naziv = ((RobaPanel) panel).getTxtNaziv().getText().trim();		
		String komada = ((RobaPanel) panel).getTxtKomada().getText().trim();
		String tezina = ((RobaPanel) panel).getTxtTezina().getText().trim();
		String kvalitet = ((RobaPanel) panel).getTxtKvalitet().getText().trim();
		String evri = ((RobaPanel) panel).getTxtEvri().getText().trim();
		String roni = ((RobaPanel) panel).getTxtRoni().getText().trim();		

		String[] params = { sifra, interna, naziv, komada, tezina,
				kvalitet, evri, roni };

		try {
			RobaTableModel ctm = (RobaTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((RobaPanel) panel).getBtnConfirm().setEnabled(false);
		((RobaPanel) panel).getBtnCancel().setEnabled(false);
		((RobaPanel) panel).getTxtSifra().setEditable(false);
		((RobaPanel) panel).getTxtInterna().setEditable(false);
		((RobaPanel) panel).getTxtNaziv().setEditable(false);
		((RobaPanel) panel).getCmbJedinicaMere().setEditable(false);
		((RobaPanel) panel).getCmbJedinicaMere().setEnabled(false);
		((RobaPanel) panel).getTxtKomada().setEditable(false);
		((RobaPanel) panel).getTxtTezina().setEditable(false);
		((RobaPanel) panel).getTxtKvalitet().setEditable(false);
		((RobaPanel) panel).getTxtEvri().setEditable(false);
		((RobaPanel) panel).getTxtRoni().setEditable(false);

	}

	public void allEnable() {
		((RobaPanel) panel).getBtnConfirm().setEnabled(true);
		((RobaPanel) panel).getBtnCancel().setEnabled(true);
		((RobaPanel) panel).getTxtSifra().setEditable(true);
		((RobaPanel) panel).getTxtInterna().setEditable(true);
		((RobaPanel) panel).getTxtNaziv().setEditable(true);
		((RobaPanel) panel).getCmbJedinicaMere().setEditable(true);
		((RobaPanel) panel).getCmbJedinicaMere().setEnabled(true);
		((RobaPanel) panel).getTxtKomada().setEditable(true);
		((RobaPanel) panel).getTxtTezina().setEditable(true);
		((RobaPanel) panel).getTxtKvalitet().setEditable(true);
		((RobaPanel) panel).getTxtEvri().setEditable(true);
		((RobaPanel) panel).getTxtRoni().setEditable(true);
	}

	public void clearAll() {
		((RobaPanel) panel).getTxtSifra().setText("");
		((RobaPanel) panel).getTxtInterna().setText("");
		((RobaPanel) panel).getTxtNaziv().setText("");		
		((RobaPanel) panel).getTxtKomada().setText("");
		((RobaPanel) panel).getTxtTezina().setText("");
		((RobaPanel) panel).getTxtKvalitet().setText("");
		((RobaPanel) panel).getTxtEvri().setText("");
		((RobaPanel) panel).getTxtRoni().setText("");
	}

	public void btnEnable() {
		((RobaPanel) panel).getBtnConfirm().setEnabled(true);
		((RobaPanel) panel).getBtnCancel().setEnabled(true);
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
