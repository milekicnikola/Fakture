package gui.dialogs;

import gui.MainFrame;
import gui.model.FakturisanaTableModel;
import gui.panels.FakturisanaPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import databaseConnection.DBConnection;

public class DialogFakturisana extends StandardDialog {

	private String faktura = "";
	private String preuzetDatum = "";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogFakturisana(JFrame parent, Boolean zoom, String where, String poslata) {
		super(parent);
		setTitle("Fakturisana roba");
		setIconImage(new ImageIcon("Images/faktura.png").getImage());

		faktura = where;

		String whereStm = " WHERE fakturisana_roba.sifra_fakture = '" + where
				+ "'";

		tableModel = new FakturisanaTableModel(
				new String[] { "Šifra fakture", "Šifra robe", "Naziv robe",
						"Šifra porudzbine", "Datum isporuke",
						"Komada fakturisano", "Opis", "Status", }, 0,
				whereStm);

		panel = new FakturisanaPanel();

		if (zoom)
			isZoom = true;

		initGUI();
		initStandardActions();
		initActions();
		
		if (poslata.equals("ne"))
			addPosalji();		

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
										"Da li ste sigurni da želite da obrišete ovu stavku?",
										"Brisanje sloga",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE);
						if (dialogResult == JOptionPane.YES_OPTION) {
							removeRow();
						}
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"Nijedna stavka nije selektovana.",
								"Upozorenje", JOptionPane.PLAIN_MESSAGE,
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
								"Nijedna stavka nije selektovana.",
								"Upozorenje", JOptionPane.PLAIN_MESSAGE,
								JOptionPane.WARNING_MESSAGE);
					}

				}
			});

			/*
			 * toolbar.getBtnDetaljno().addActionListener(new ActionListener() {
			 * 
			 * @Override public void actionPerformed(ActionEvent arg0) { if
			 * (table.getSelectedRow() >= 0) { DialogNarucena dialog = new
			 * DialogNarucena(MainFrame .getInstance(), true,
			 * ((FakturisanaPanel) panel).getTxtSifra().getText().trim());
			 * dialog.setVisible(true); } else {
			 * JOptionPane.showConfirmDialog(getParent(),
			 * "Nijedna Narucena nije selektovana.", "Upozorenje",
			 * JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE); } } });
			 */

			panel.getBtnCancel().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (state == State.DODAVANJE)
						clearAll();

					updateStateAndTextFields(State.POGLED);

				}
			});
			((FakturisanaPanel) panel).getBtnRoba().addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							DialogNarucena dialog = new DialogNarucena(
									MainFrame.getInstance(), true, "");
							dialog.setVisible(true);
							try {
								if (!dialog.getZoom1().equals(""))
									((FakturisanaPanel) panel).getTxtSifraP()
											.setText(dialog.getZoom1());
								if (!dialog.getZoom2().equals(""))
									((FakturisanaPanel) panel).getTxtSifraR()
											.setText(dialog.getZoom2());
								if (!dialog.getZoom3().equals(""))
									((FakturisanaPanel) panel).getTxtNazivR()
											.setText(dialog.getZoom3());
								if (!dialog.getZoom4().equals("")) {
									preuzetDatum = dialog.getZoom4();
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd");
									Date date = null;
									try {
										date = sdf.parse(preuzetDatum);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									((FakturisanaPanel) panel).getTxtDatum()
											.setDate(date);
								}
							} catch (NullPointerException n) {
							}
						}
					});
			((FakturisanaPanel) panel).getTxtSifraF().setText(faktura);
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
			// toolbar.getBtnDetaljno().setEnabled(false);
			return;
		}
		// toolbar.getBtnDetaljno().setEnabled(true);

		String sifraF = (String) tableModel.getValueAt(index, 0);
		String sifraR = (String) tableModel.getValueAt(index, 1);
		String nazivR = (String) tableModel.getValueAt(index, 2);
		String sifraP = (String) tableModel.getValueAt(index, 3);
		String datum = (String) tableModel.getValueAt(index, 4);
		String komada = (String) tableModel.getValueAt(index, 5);
		String opis = (String) tableModel.getValueAt(index, 6);
		String status = (String) tableModel.getValueAt(index, 7);

		((FakturisanaPanel) panel).getTxtSifraF().setText(sifraF);
		((FakturisanaPanel) panel).getTxtSifraP().setText(sifraP);
		((FakturisanaPanel) panel).getTxtSifraR().setText(sifraR);
		((FakturisanaPanel) panel).getTxtNazivR().setText(nazivR);
		((FakturisanaPanel) panel).getTxtKomada().setText(komada);
		((FakturisanaPanel) panel).getTxtOpis().setText(opis);
		((FakturisanaPanel) panel).getTxtStatus().setText(status);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(datum);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		((FakturisanaPanel) panel).getTxtDatum().setDate(date);		
		 

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
			((FakturisanaPanel) panel).getBtnRoba().setEnabled(false);
			((FakturisanaPanel) panel).getTxtDatum().setEnabled(false);
			((FakturisanaPanel) panel).getTxtSifraP().setEnabled(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			if (state == State.PRETRAGA) {
				((FakturisanaPanel) panel).getTxtStatus()
						.setEditable(true);
				((FakturisanaPanel) panel).getTxtSifraP().setEditable(true);
			} else {
				((FakturisanaPanel) panel).getTxtStatus().setEditable(
						false);
				((FakturisanaPanel) panel).getTxtSifraP().setEditable(false);
			}
		}
		((FakturisanaPanel) panel).getTxtKomada().requestFocus();
		statusBar.getStatusState().setText(state.toString());		
		
		this.state = state;
	}

	@Override
	public void addRow() {

		String sifraP = ((FakturisanaPanel) panel).getTxtSifraP().getText()
				.trim();
		String sifraR = ((FakturisanaPanel) panel).getTxtSifraR().getText()
				.trim();
		String nazivR = ((FakturisanaPanel) panel).getTxtNazivR().getText()
				.trim();
		String sifraF = ((FakturisanaPanel) panel).getTxtSifraF().getText()
				.trim();
		String komada = ((FakturisanaPanel) panel).getTxtKomada().getText()
				.trim();
		String opis = ((FakturisanaPanel) panel).getTxtOpis().getText().trim();

		String[] params = { sifraF, sifraR, nazivR, sifraP, preuzetDatum,
				komada, opis, "narucena" };

		try {
			FakturisanaTableModel ctm = (FakturisanaTableModel) table
					.getModel();
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
		int i = table.getSelectedRow();
		if (i == -1)
			return;

		String komada = ((FakturisanaPanel) panel).getTxtKomada().getText()
				.trim();
		String opis = ((FakturisanaPanel) panel).getTxtOpis().getText().trim();
		String status = ((FakturisanaPanel) panel).getTxtStatus()
				.getText().trim();
		/*
		 * Date datum1 = ((FakturisanaPanel) panel).getTxtDatum().getDate();
		 * String datum = ""; if (datum1 != null) { datum = new
		 * SimpleDateFormat("yyyy-MM-dd").format(datum1); }
		 */

		String[] params = { komada, opis, status };
		int index = table.getSelectedRow();
		try {
			FakturisanaTableModel ctm = (FakturisanaTableModel) table
					.getModel();
			ctm.updateRow(index, params);
			updateStateAndTextFields(State.AZURIRANJE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška1",
					JOptionPane.ERROR_MESSAGE);
		}
		table.setRowSelectionInterval(index, index);
	}

	@Override
	public void search() {

		String sifraF = ((FakturisanaPanel) panel).getTxtSifraF().getText()
				.trim();
		String sifraP = ((FakturisanaPanel) panel).getTxtSifraP().getText()
				.trim();
		String sifraR = ((FakturisanaPanel) panel).getTxtSifraR().getText()
				.trim();
		String komada = ((FakturisanaPanel) panel).getTxtKomada().getText()
				.trim();
		String opis = ((FakturisanaPanel) panel).getTxtOpis().getText().trim();
		String status = ((FakturisanaPanel) panel).getTxtStatus()
				.getText().trim();

		String[] params = { sifraR, sifraP, preuzetDatum, sifraF, komada, opis,
				status };

		try {
			FakturisanaTableModel ctm = (FakturisanaTableModel) table
					.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((FakturisanaPanel) panel).getBtnConfirm().setEnabled(false);
		((FakturisanaPanel) panel).getBtnCancel().setEnabled(false);
		((FakturisanaPanel) panel).getBtnRoba().setEnabled(false);
		((FakturisanaPanel) panel).getTxtSifraP().setEditable(false);
		((FakturisanaPanel) panel).getTxtSifraR().setEditable(false);
		((FakturisanaPanel) panel).getTxtNazivR().setEditable(false);
		((FakturisanaPanel) panel).getTxtSifraF().setEditable(false);
		((FakturisanaPanel) panel).getTxtKomada().setEditable(false);
		((FakturisanaPanel) panel).getTxtOpis().setEditable(false);
		((FakturisanaPanel) panel).getTxtDatum().setEnabled(false);
		((FakturisanaPanel) panel).getTxtStatus().setEditable(false);
	}

	public void allEnable() {
		((FakturisanaPanel) panel).getBtnConfirm().setEnabled(true);
		((FakturisanaPanel) panel).getBtnCancel().setEnabled(true);
		((FakturisanaPanel) panel).getTxtKomada().setEditable(true);
		((FakturisanaPanel) panel).getTxtOpis().setEditable(true);

	}

	public void clearAll() {
		((FakturisanaPanel) panel).getTxtSifraR().setText("");
		((FakturisanaPanel) panel).getTxtNazivR().setText("");
		((FakturisanaPanel) panel).getTxtKomada().setText("");
		((FakturisanaPanel) panel).getTxtStatus().setText("");
		((FakturisanaPanel) panel).getTxtOpis().setText("");
		((FakturisanaPanel) panel).getTxtSifraP().setText("");
		((FakturisanaPanel) panel).getTxtDatum().setCalendar(null);
	}

	public void btnEnable() {
		((FakturisanaPanel) panel).getBtnConfirm().setEnabled(true);
		((FakturisanaPanel) panel).getBtnCancel().setEnabled(true);
		((FakturisanaPanel) panel).getBtnRoba().setEnabled(true);
	}

	public void addPosalji() {	

		JButton btnPosalji = new JButton("Pošalji fakturu");
		btnPosalji.setEnabled(true);
		toolbar.dodajPosalji(btnPosalji);

		toolbar.getBtnPosalji().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				srediPodatke();
				toolbar.getBtnPosalji().setEnabled(false);
				toolbar.getBtnRefresh().doClick();				
			}
		});

	}

	public void srediPodatke() {

		String upit = "SELECT fakturisana_roba.sifra_robe as sifraRobe, fakturisana_roba.sifra_porudzbine as sifraPorudzbine, fakturisana_roba.datum_isporuke as datumIsporuke, fakturisana_roba.sifra_fakture as sifraFakture, komada_fakturisano, komada_ostalo FROM fakturisana_roba JOIN narucena_roba ON fakturisana_roba.sifra_robe = narucena_roba.sifra_robe AND fakturisana_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND fakturisana_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN faktura ON fakturisana_roba.sifra_fakture = faktura.sifra_fakture WHERE fakturisana_roba.sifra_fakture = '"
				+ faktura + "'";

		try {

			Statement stmt = DBConnection.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(upit);
			
			while (rset.next()) {
				String sifra_robe = rset.getString("sifraRobe");
				String sifra_porudzbine = rset.getString("sifraPorudzbine");
				String datum = rset.getString("datumIsporuke");
				String faktura = rset.getString("sifraFakture");
				String fakturisano = rset.getString("KOMADA_FAKTURISANO");				
				String ostalo = rset.getString("KOMADA_OSTALO");

				int komada_ostalo = Integer.parseInt(ostalo);
				int komada_fakturisano = Integer.parseInt(fakturisano);

				int komadaOstalo = komada_ostalo - komada_fakturisano;

				String ko = Integer.toString(komadaOstalo);

				PreparedStatement stmt1 = DBConnection
						.getConnection()
						.prepareStatement(
								"UPDATE narucena_roba SET komada_poslato = ?, komada_ostalo = ? WHERE sifra_robe = ? and sifra_porudzbine = ? and datum_isporuke = ?");

				stmt1.setString(1, fakturisano);
				stmt1.setString(2, ko);
				stmt1.setString(3, sifra_robe);
				stmt1.setString(4, sifra_porudzbine);
				stmt1.setString(5, datum);
				stmt1.executeUpdate();
				stmt1.close();

				PreparedStatement stmt2 = DBConnection
						.getConnection()
						.prepareStatement(
								"UPDATE fakturisana_roba SET status = ? WHERE sifra_robe = ? and sifra_porudzbine = ? and datum_isporuke = ? and sifra_fakture = ?");

				stmt2.setString(1, "fakturisana");
				stmt2.setString(2, sifra_robe);
				stmt2.setString(3, sifra_porudzbine);
				stmt2.setString(4, datum);
				stmt2.setString(5, faktura);
				stmt2.executeUpdate();
				stmt2.close();

				/*
				 * addRow(new String[] { faktura, sifra_robe, naziv_robe,
				 * sifra_porudzbine, datum, komada, opis, otpremljena });
				 */
			}

			rset.close();
			stmt.close();

			PreparedStatement stmt3 = DBConnection
					.getConnection()
					.prepareStatement(
							"UPDATE faktura SET poslata_faktura = ? WHERE sifra_fakture = ?");

			stmt3.setString(1, "da");
			stmt3.setString(2, faktura);
			stmt3.executeUpdate();
			stmt3.close();

			DBConnection.getConnection().commit();
			// fireTableDataChanged();

			/*
			 * } else { JOptionPane.showConfirmDialog(getParent(),
			 * "Nijedna faktura nije selektovana.", "Upozorenje",
			 * JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE); }
			 */
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}	

}
