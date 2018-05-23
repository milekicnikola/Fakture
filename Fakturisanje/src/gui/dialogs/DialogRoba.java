package gui.dialogs;

import gui.model.RobaTableModel;
import gui.panels.RobaPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
				"Interna šifra", "Naziv", "Interni naziv", "Jedinica mere", "Komada u setu",
				"Prevod", "Težina", "Cena u ronima" }, 0);

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
		String interni = (String) tableModel.getValueAt(index, 3);
		String jedinica = (String) tableModel.getValueAt(index, 4);
		String komada = (String) tableModel.getValueAt(index, 5);
		String prevod = (String) tableModel.getValueAt(index, 6);
		String tezina = (String) tableModel.getValueAt(index, 7);
		String roni = (String) tableModel.getValueAt(index, 8);

		((RobaPanel) panel).getTxtSifra().setText(sifra);
		((RobaPanel) panel).getTxtInterna().setText(interna);
		((RobaPanel) panel).getTxtNaziv().setText(naziv);
		((RobaPanel) panel).getTxtInterni().setText(interni);

		if (jedinica.equals("komad"))
			((RobaPanel) panel).getCmbJedinicaMere().setSelectedIndex(0);
		else if (jedinica.equals("set"))
			((RobaPanel) panel).getCmbJedinicaMere().setSelectedIndex(1);
		else if (jedinica.equals("metar"))
			((RobaPanel) panel).getCmbJedinicaMere().setSelectedIndex(2);

		((RobaPanel) panel).getTxtKomada().setText(komada);

		if (prevod.equals("el. constructi metalice bucati"))
			((RobaPanel) panel).getCmbPrevod().setSelectedIndex(0);
		else if (prevod.equals("el. constructi metalice in metri"))
			((RobaPanel) panel).getCmbPrevod().setSelectedIndex(1);
		else if (prevod.equals("el. constructi metalice seturi"))
			((RobaPanel) panel).getCmbPrevod().setSelectedIndex(2);
		else if (prevod.equals("cuti carton"))
			((RobaPanel) panel).getCmbPrevod().setSelectedIndex(3);
		else if (prevod.equals("separator carton"))
			((RobaPanel) panel).getCmbPrevod().setSelectedIndex(4);
		else if (prevod.equals("coltar carton"))
			((RobaPanel) panel).getCmbPrevod().setSelectedIndex(5);

		((RobaPanel) panel).getTxtTezina().setText(tezina);
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
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			
			if (state == State.PRETRAGA) {
				((RobaPanel) panel).getCmbJedinicaMere().setEditable(false);
				((RobaPanel) panel).getCmbJedinicaMere().setEnabled(false);
				((RobaPanel) panel).getCmbPrevod().setEditable(false);
				((RobaPanel) panel).getCmbPrevod().setEnabled(false);
			}
				
			((RobaPanel) panel).getTxtSifra().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		/*
		 * double trenutniKurs = 1;
		 * 
		 * try { trenutniKurs = trenutniKurs(); } catch (SQLException ex) {
		 * JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
		 * JOptionPane.ERROR_MESSAGE); } catch (NumberFormatException ex1) {
		 * trenutniKurs = 1; }
		 */

		String sifra = ((RobaPanel) panel).getTxtSifra().getText().trim();
		String interna = ((RobaPanel) panel).getTxtInterna().getText().trim();
		String naziv = ((RobaPanel) panel).getTxtNaziv().getText().trim();
		String interni = ((RobaPanel) panel).getTxtInterni().getText().trim();
		String jedinica = ((RobaPanel) panel).getCmbJedinicaMere()
				.getSelectedItem().toString();
		String komada = ((RobaPanel) panel).getTxtKomada().getText().trim();
		String naziv_prevoda = ((RobaPanel) panel).getCmbPrevod()
				.getSelectedItem().toString();
		String tezina = ((RobaPanel) panel).getTxtTezina().getText().trim();
		String roni = ((RobaPanel) panel).getTxtRoni().getText().trim();

		String mera = "1";

		if (jedinica.equals("set"))
			mera = "2";
		else if (jedinica.equals("metar"))
			mera = "3";
		else
			mera = "1";

		String prevod = "1";

		if (naziv_prevoda.equals("el. constructi metalice in metri"))
			prevod = "2";
		else if (naziv_prevoda.equals("el. constructi metalice seturi"))
			prevod = "3";
		else if (naziv_prevoda.equals("cuti carton"))
			prevod = "4";
		else if (naziv_prevoda.equals("separator carton"))
			prevod = "5";
		else if (naziv_prevoda.equals("coltar carton"))
			prevod = "6";
		else
			prevod = "1";

		/*
		 * String roni = "0"; double roniD;
		 * 
		 * if (evri != null && !evri.equals("")) { roniD =
		 * Double.parseDouble(evri) * trenutniKurs; roni =
		 * String.valueOf(roniD); }
		 */

		String[] params = { sifra, mera, prevod, interna, naziv, interni, jedinica,
				komada, naziv_prevoda, tezina, roni };

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

		/*
		 * double trenutniKurs = 1;
		 * 
		 * try { trenutniKurs = trenutniKurs(); } catch (SQLException ex) {
		 * JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
		 * JOptionPane.ERROR_MESSAGE); } catch (NumberFormatException ex1) {
		 * trenutniKurs = 1; }
		 */

		int i = table.getSelectedRow();
		if (i == -1)
			return;
		String interna = ((RobaPanel) panel).getTxtInterna().getText().trim();
		String naziv = ((RobaPanel) panel).getTxtNaziv().getText().trim();
		String interni = ((RobaPanel) panel).getTxtInterni().getText().trim();
		String jedinica = ((RobaPanel) panel).getCmbJedinicaMere()
				.getSelectedItem().toString();
		String komada = ((RobaPanel) panel).getTxtKomada().getText().trim();
		String naziv_prevoda = ((RobaPanel) panel).getCmbPrevod()
				.getSelectedItem().toString();
		String tezina = ((RobaPanel) panel).getTxtTezina().getText().trim();
		String roni = ((RobaPanel) panel).getTxtRoni().getText().trim();

		String mera = "1";

		if (jedinica.equals("set"))
			mera = "2";
		else if (jedinica.equals("metar"))
			mera = "3";
		else
			mera = "1";

		String prevod = "1";

		if (naziv_prevoda.equals("el. constructi metalice in metri"))
			prevod = "2";
		else if (naziv_prevoda.equals("el. constructi metalice seturi"))
			prevod = "3";
		else if (naziv_prevoda.equals("cuti carton"))
			prevod = "4";
		else if (naziv_prevoda.equals("separator carton"))
			prevod = "5";
		else if (naziv_prevoda.equals("coltar carton"))
			prevod = "6";
		else
			prevod = "1";

		/*
		 * String roni = "0"; double roniD;
		 * 
		 * if (evri != null && !evri.equals("")) { roniD =
		 * Double.parseDouble(evri) * trenutniKurs; roni =
		 * String.valueOf(roniD); }
		 */

		String[] params = { mera, prevod, interna, naziv, interni, jedinica, komada,
				naziv_prevoda, tezina, roni };
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
		String interni = ((RobaPanel) panel).getTxtInterni().getText().trim();
		String komada = ((RobaPanel) panel).getTxtKomada().getText().trim();
		String tezina = ((RobaPanel) panel).getTxtTezina().getText().trim();
		String roni = ((RobaPanel) panel).getTxtRoni().getText().trim();

		String[] params = { sifra, interna, naziv, interni, komada, tezina, roni };

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
		((RobaPanel) panel).getTxtInterni().setEditable(false);		
		((RobaPanel) panel).getCmbJedinicaMere().setEnabled(false);		
		((RobaPanel) panel).getCmbPrevod().setEnabled(false);
		((RobaPanel) panel).getTxtKomada().setEditable(false);
		((RobaPanel) panel).getTxtTezina().setEditable(false);		
		((RobaPanel) panel).getTxtRoni().setEditable(false);

	}

	public void allEnable() {
		((RobaPanel) panel).getBtnConfirm().setEnabled(true);
		((RobaPanel) panel).getBtnCancel().setEnabled(true);
		((RobaPanel) panel).getTxtSifra().setEditable(true);
		((RobaPanel) panel).getTxtInterna().setEditable(true);
		((RobaPanel) panel).getTxtNaziv().setEditable(true);
		((RobaPanel) panel).getTxtInterni().setEditable(true);		
		((RobaPanel) panel).getCmbJedinicaMere().setEnabled(true);		
		((RobaPanel) panel).getCmbPrevod().setEnabled(true);
		((RobaPanel) panel).getTxtKomada().setEditable(true);
		((RobaPanel) panel).getTxtTezina().setEditable(true);		
		((RobaPanel) panel).getTxtRoni().setEditable(true);
	}

	public void clearAll() {
		((RobaPanel) panel).getTxtSifra().setText("");
		((RobaPanel) panel).getTxtInterna().setText("");
		((RobaPanel) panel).getTxtNaziv().setText("");
		((RobaPanel) panel).getTxtInterni().setText("");
		((RobaPanel) panel).getTxtKomada().setText("");
		((RobaPanel) panel).getTxtTezina().setText("");		
		((RobaPanel) panel).getTxtRoni().setText("");
	}

	public void btnEnable() {
		((RobaPanel) panel).getBtnConfirm().setEnabled(true);
		((RobaPanel) panel).getBtnCancel().setEnabled(true);
	}

	/*
	 * public double trenutniKurs() throws SQLException, NumberFormatException {
	 * 
	 * DBConnection.getConnection().setTransactionIsolation(
	 * Connection.TRANSACTION_REPEATABLE_READ); PreparedStatement selectStmt =
	 * DBConnection .getConnection() .prepareStatement(
	 * "SELECT ron_evro FROM kurs WHERE kurs.datum_kursa = (SELECT max(datum_kursa) FROM kurs)"
	 * );
	 * 
	 * ResultSet rset = selectStmt.executeQuery();
	 * 
	 * String kurs = "";
	 * 
	 * while (rset.next()) { kurs = rset.getString("RON_EVRO").trim(); }
	 * 
	 * rset.close(); selectStmt.close();
	 * DBConnection.getConnection().setTransactionIsolation(
	 * Connection.TRANSACTION_READ_COMMITTED);
	 * 
	 * return Double.parseDouble(kurs);
	 * 
	 * }
	 */

}
