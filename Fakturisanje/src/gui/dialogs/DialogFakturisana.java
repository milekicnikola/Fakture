package gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import databaseConnection.DBConnection;
import gui.MainFrame;
import gui.model.FakturisanaTableModel;
import gui.panels.FakturisanaPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;
import util.ResourceLoader;
import util.Roba;

public class DialogFakturisana extends StandardDialog {

	private String faktura = "";
	private String preuzetDatum = "";
	private String poslata = "";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogFakturisana(JFrame parent, Boolean zoom, String where, String poslata1) {
		super(parent);
		setTitle("Fakturisana roba");

		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/faktura.png"));
		} catch (Exception e) {

		}
		setIconImage(image);

		faktura = where;
		poslata = poslata1;

		String whereStm = " WHERE fakturisana_roba.sifra_fakture = '" + where + "'";

		tableModel = new FakturisanaTableModel(new String[] { "Šifra fakture", "Šifra robe", "Naziv robe",
				"Jedinica mere", "Šifra porudzbine", "Datum isporuke", "Komada naručeno", "Komada fakturisano", "Opis",
				"Komada u metru", "Status", }, 0, whereStm);

		panel = new FakturisanaPanel();

		if (zoom)
			isZoom = true;

		initGUI();
		initStandardActions();
		initActions();

		if (poslata.equals("ne")) {
			addCelaPorudzbina();
			addPosalji();
		} else {
			toolbar.getBtnDelete().setEnabled(false);
			toolbar.getBtnUpdate().setEnabled(false);
			toolbar.getBtnAdd().setEnabled(false);
		}

		if (!isZoom)
			addIzvestaj();

		if (poslata.equals("da"))
			isZoom = true;

	}

	@Override
	public void initActions() {

		if (!isZoom) {

			toolbar.getBtnDelete().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (table.getSelectedRow() >= 0) {
						int dialogResult = JOptionPane.showConfirmDialog(getParent(),
								"Da li ste sigurni da želite da obrišete ovu stavku?", "Brisanje sloga",
								JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						if (dialogResult == JOptionPane.YES_OPTION) {
							removeRow();
						}
					} else {
						JOptionPane.showConfirmDialog(getParent(), "Nijedna stavka nije selektovana.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
					}
				}
			});

			toolbar.getBtnUpdate().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (table.getSelectedRow() >= 0) {
						updateStateAndTextFields(State.AZURIRANJE);
					} else {
						JOptionPane.showConfirmDialog(getParent(), "Nijedna stavka nije selektovana.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
					}

				}
			});

			/*
			 * toolbar.getBtnDetaljno().addActionListener(new ActionListener() {
			 * 
			 * @Override public void actionPerformed(ActionEvent arg0) { if
			 * (table.getSelectedRow() >= 0) { DialogNarucena dialog = new
			 * DialogNarucena(MainFrame .getInstance(), true, ((FakturisanaPanel)
			 * panel).getTxtSifra().getText().trim()); dialog.setVisible(true); } else {
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
			((FakturisanaPanel) panel).getBtnRoba().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					DialogNarucena dialog = new DialogNarucena(MainFrame.getInstance(), true, "faktura");
					dialog.setVisible(true);
					try {
						if (!dialog.getZoom1().equals(""))
							((FakturisanaPanel) panel).getTxtSifraP().setText(dialog.getZoom1());
						if (!dialog.getZoom2().equals(""))
							((FakturisanaPanel) panel).getTxtSifraR().setText(dialog.getZoom2());
						if (!dialog.getZoom3().equals(""))
							((FakturisanaPanel) panel).getTxtNazivR().setText(dialog.getZoom3());
						if (!dialog.getZoom4().equals("")) {
							preuzetDatum = dialog.getZoom4();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date date = null;
							try {
								date = sdf.parse(preuzetDatum);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							((FakturisanaPanel) panel).getTxtDatum().setDate(date);
						}
						if (!dialog.getZoom5().equals(""))
							((FakturisanaPanel) panel).getTxtNaruceno().setText(dialog.getZoom5());

						try {

							String upit = "SELECT naziv_mere FROM jedinica_mere JOIN roba ON jedinica_mere.redni_broj = roba.jedinica_mere WHERE roba.sifra_robe = '"
									+ dialog.getZoom2() + "'";

							DBConnection.getConnection()
									.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
							PreparedStatement stmt = DBConnection.getConnection().prepareStatement(upit);
							ResultSet rset = stmt.executeQuery();

							while (rset.next()) {
								String mera = rset.getString("NAZIV_MERE");
								((FakturisanaPanel) panel).getTxtMera().setText(mera);
							}
							rset.close();
							stmt.close();
							DBConnection.getConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
							DBConnection.getConnection().commit();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						if (((FakturisanaPanel) panel).getTxtMera().getText().equals("metar")) {
							((FakturisanaPanel) panel).getTxtMetri().setEditable(true);
							((FakturisanaPanel) panel).getTxtMetri().setText("1");
						} else {
							((FakturisanaPanel) panel).getTxtMetri().setEditable(false);
							((FakturisanaPanel) panel).getTxtMetri().setText("0");
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

		if (index <= (table.getModel().getRowCount() - 1)) {

			String sifraF = (String) tableModel.getValueAt(index, 0);
			String sifraR = (String) tableModel.getValueAt(index, 1);
			String nazivR = (String) tableModel.getValueAt(index, 2);
			String mera = (String) tableModel.getValueAt(index, 3);
			String sifraP = (String) tableModel.getValueAt(index, 4);
			String datum = (String) tableModel.getValueAt(index, 5);
			String naruceno = (String) tableModel.getValueAt(index, 6);
			String komada = (String) tableModel.getValueAt(index, 7);
			String opis = (String) tableModel.getValueAt(index, 8);
			String metri = (String) tableModel.getValueAt(index, 9);
			String status = (String) tableModel.getValueAt(index, 10);

			((FakturisanaPanel) panel).getTxtSifraF().setText(sifraF);
			((FakturisanaPanel) panel).getTxtSifraP().setText(sifraP);
			((FakturisanaPanel) panel).getTxtSifraR().setText(sifraR);
			((FakturisanaPanel) panel).getTxtNazivR().setText(nazivR);
			((FakturisanaPanel) panel).getTxtMera().setText(mera);
			((FakturisanaPanel) panel).getTxtNaruceno().setText(naruceno);
			((FakturisanaPanel) panel).getTxtKomada().setText(komada);
			((FakturisanaPanel) panel).getTaOpis().setText(opis);
			((FakturisanaPanel) panel).getTxtMetri().setText(metri);
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

	}

	@Override
	public void updateStateAndTextFields(State state) {
		if (this.state == State.PRETRAGA && state != State.PRETRAGA && state != State.POGLED) {
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
				((FakturisanaPanel) panel).getTxtStatus().setEditable(true);
				((FakturisanaPanel) panel).getTxtSifraP().setEditable(true);
				((FakturisanaPanel) panel).getTxtNaruceno().setEditable(true);
				((FakturisanaPanel) panel).getBtnConfirm().setEnabled(true);
				((FakturisanaPanel) panel).getBtnCancel().setEnabled(true);
				((FakturisanaPanel) panel).getBtnRoba().setEnabled(false);
			} else {
				((FakturisanaPanel) panel).getTxtStatus().setEditable(false);
				((FakturisanaPanel) panel).getTxtSifraP().setEditable(false);
				((FakturisanaPanel) panel).getTxtNaruceno().setEditable(false);
			}
		}
		((FakturisanaPanel) panel).getTxtKomada().requestFocus();
		statusBar.getStatusState().setText(state.toString());

		this.state = state;
	}

	@Override
	public void addRow() {

		String sifraP = ((FakturisanaPanel) panel).getTxtSifraP().getText().trim();
		String sifraR = ((FakturisanaPanel) panel).getTxtSifraR().getText().trim();
		String nazivR = ((FakturisanaPanel) panel).getTxtNazivR().getText().trim();
		String mera = ((FakturisanaPanel) panel).getTxtMera().getText().trim();
		String sifraF = ((FakturisanaPanel) panel).getTxtSifraF().getText().trim();
		String naruceno = ((FakturisanaPanel) panel).getTxtNaruceno().getText().trim();
		String komada = ((FakturisanaPanel) panel).getTxtKomada().getText().trim();
		String opis = ((FakturisanaPanel) panel).getTaOpis().getText().trim();
		String metri = ((FakturisanaPanel) panel).getTxtMetri().getText().trim();

		String[] params = { sifraF, sifraR, nazivR, mera, sifraP, preuzetDatum, naruceno, komada, opis, metri,
				"narucena" };

		try {
			FakturisanaTableModel ctm = (FakturisanaTableModel) table.getModel();
			int index = ctm.insertRow(params);
			table.setRowSelectionInterval(index, index);
			updateStateAndTextFields(State.DODAVANJE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void updateRow() {
		int i = table.getSelectedRow();
		if (i == -1)
			return;

		String naruceno = ((FakturisanaPanel) panel).getTxtNaruceno().getText().trim();
		String komada = ((FakturisanaPanel) panel).getTxtKomada().getText().trim();
		String opis = ((FakturisanaPanel) panel).getTaOpis().getText().trim();
		String metri = ((FakturisanaPanel) panel).getTxtMetri().getText().trim();
		String status = ((FakturisanaPanel) panel).getTxtStatus().getText().trim();
		/*
		 * Date datum1 = ((FakturisanaPanel) panel).getTxtDatum().getDate(); String
		 * datum = ""; if (datum1 != null) { datum = new
		 * SimpleDateFormat("yyyy-MM-dd").format(datum1); }
		 */

		String[] params = { naruceno, komada, opis, metri, status };
		int index = table.getSelectedRow();
		try {
			FakturisanaTableModel ctm = (FakturisanaTableModel) table.getModel();
			ctm.updateRow(index, params);
			updateStateAndTextFields(State.AZURIRANJE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška1", JOptionPane.ERROR_MESSAGE);
		}
		table.setRowSelectionInterval(index, index);
	}

	@Override
	public void search() {

		String sifraF = ((FakturisanaPanel) panel).getTxtSifraF().getText().trim();
		String sifraP = ((FakturisanaPanel) panel).getTxtSifraP().getText().trim();
		String sifraR = ((FakturisanaPanel) panel).getTxtSifraR().getText().trim();
		String naruceno = ((FakturisanaPanel) panel).getTxtNaruceno().getText().trim();
		String komada = ((FakturisanaPanel) panel).getTxtKomada().getText().trim();
		String opis = ((FakturisanaPanel) panel).getTaOpis().getText().trim();
		String metri = ((FakturisanaPanel) panel).getTxtMetri().getText().trim();
		String status = ((FakturisanaPanel) panel).getTxtStatus().getText().trim();

		String[] params = { sifraR, sifraP, preuzetDatum, sifraF, naruceno, komada, opis, metri, status };

		try {
			FakturisanaTableModel ctm = (FakturisanaTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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
		((FakturisanaPanel) panel).getTxtMera().setEditable(false);
		((FakturisanaPanel) panel).getTxtSifraF().setEditable(false);
		((FakturisanaPanel) panel).getTxtKomada().setEditable(false);
		((FakturisanaPanel) panel).getTaOpis().setEditable(false);
		// ((FakturisanaPanel) panel).getTaOpis().setEnabled(false);
		((FakturisanaPanel) panel).getTxtDatum().setEnabled(false);
		((FakturisanaPanel) panel).getTxtStatus().setEditable(false);
		((FakturisanaPanel) panel).getTxtMetri().setEditable(false);
		((FakturisanaPanel) panel).getTxtNaruceno().setEditable(false);
	}

	public void allEnable() {
		((FakturisanaPanel) panel).getBtnConfirm().setEnabled(true);
		((FakturisanaPanel) panel).getBtnCancel().setEnabled(true);
		((FakturisanaPanel) panel).getTxtKomada().setEditable(true);
		// ((FakturisanaPanel) panel).getTxtMetri().setEditable(true);
		((FakturisanaPanel) panel).getTaOpis().setEditable(true);
		// ((FakturisanaPanel) panel).getTaOpis().setEnabled(true);

	}

	public void clearAll() {
		((FakturisanaPanel) panel).getTxtSifraR().setText("");
		((FakturisanaPanel) panel).getTxtNazivR().setText("");
		((FakturisanaPanel) panel).getTxtMera().setText("");
		((FakturisanaPanel) panel).getTxtKomada().setText("");
		((FakturisanaPanel) panel).getTxtStatus().setText("");
		((FakturisanaPanel) panel).getTaOpis().setText("");
		((FakturisanaPanel) panel).getTxtSifraP().setText("");
		((FakturisanaPanel) panel).getTxtNaruceno().setText("");
		((FakturisanaPanel) panel).getTxtMetri().setText("");
		((FakturisanaPanel) panel).getTxtDatum().setCalendar(null);
	}

	public void btnEnable() {

		if (poslata.equals("ne")) {
			((FakturisanaPanel) panel).getBtnConfirm().setEnabled(true);
			((FakturisanaPanel) panel).getBtnCancel().setEnabled(true);
			((FakturisanaPanel) panel).getBtnRoba().setEnabled(true);
		}
	}

	public void addPosalji() {

		JButton btnPosalji = new JButton("Pošalji fakturu");
		btnPosalji.setEnabled(true);
		toolbar.dodajPosalji(btnPosalji);

		toolbar.getBtnPosalji().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				FakturisanaTableModel ctm = (FakturisanaTableModel) table.getModel();
				if (ctm.getRowCount() > 0) {

					int dialogResult = JOptionPane.showConfirmDialog(getParent(),
							"Da li ste sigurni da želite da pošaljete ovu fakturu?", "Slanje fakture",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (dialogResult == JOptionPane.YES_OPTION) {
						srediPodatke();
						toolbar.getBtnPosalji().setEnabled(false);
						toolbar.getBtnPorudzbina().setEnabled(false);
						toolbar.getBtnRefresh().doClick();
					}
				} else {
					JOptionPane.showConfirmDialog(getParent(), "Ne postoji nijedna stavka.", "Upozorenje",
							JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
				}

			}
		});

	}

	public void srediPodatke() {

		String upit = "SELECT fakturisana_roba.sifra_robe as sifraRobe, fakturisana_roba.sifra_porudzbine as sifraPorudzbine, fakturisana_roba.datum_isporuke as datumIsporuke, fakturisana_roba.sifra_fakture as sifraFakture, komada_fakturisano, komada_poslato, komada_ostalo FROM fakturisana_roba JOIN narucena_roba ON fakturisana_roba.sifra_robe = narucena_roba.sifra_robe AND fakturisana_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND fakturisana_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN faktura ON fakturisana_roba.sifra_fakture = faktura.sifra_fakture WHERE fakturisana_roba.sifra_fakture = '"
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
				String poslato = rset.getString("KOMADA_POSLATO");

				int komada_ostalo = Integer.parseInt(ostalo);
				int komada_fakturisano = Integer.parseInt(fakturisano);
				int komada_poslato = Integer.parseInt(poslato);

				int komadaOstalo = komada_ostalo - komada_fakturisano;
				int komadaPoslato = komada_poslato + komada_fakturisano;

				String ko = Integer.toString(komadaOstalo);
				String kp = Integer.toString(komadaPoslato);

				PreparedStatement stmt1 = DBConnection.getConnection().prepareStatement(
						"UPDATE narucena_roba SET komada_poslato = ?, komada_ostalo = ? WHERE sifra_robe = ? and sifra_porudzbine = ? and datum_isporuke = ?");

				stmt1.setString(1, kp);
				stmt1.setString(2, ko);
				stmt1.setString(3, sifra_robe);
				stmt1.setString(4, sifra_porudzbine);
				stmt1.setString(5, datum);
				stmt1.executeUpdate();
				stmt1.close();

				PreparedStatement stmt2 = DBConnection.getConnection().prepareStatement(
						"UPDATE fakturisana_roba SET status = ? WHERE sifra_robe = ? and sifra_porudzbine = ? and datum_isporuke = ? and sifra_fakture = ?");

				stmt2.setString(1, "fakturisana");
				stmt2.setString(2, sifra_robe);
				stmt2.setString(3, sifra_porudzbine);
				stmt2.setString(4, datum);
				stmt2.setString(5, faktura);
				stmt2.executeUpdate();
				stmt2.close();

				/*
				 * addRow(new String[] { faktura, sifra_robe, naziv_robe, sifra_porudzbine,
				 * datum, komada, opis, otpremljena });
				 */
			}

			rset.close();
			stmt.close();

			PreparedStatement stmt3 = DBConnection.getConnection()
					.prepareStatement("UPDATE faktura SET poslata_faktura = ? WHERE sifra_fakture = ?");

			stmt3.setString(1, "da");
			stmt3.setString(2, faktura);
			stmt3.executeUpdate();
			stmt3.close();

			DBConnection.getConnection().commit();
			// fireTableDataChanged();

			/*
			 * } else { JOptionPane.showConfirmDialog(getParent(),
			 * "Nijedna faktura nije selektovana.", "Upozorenje", JOptionPane.PLAIN_MESSAGE,
			 * JOptionPane.WARNING_MESSAGE); }
			 */
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void addIzvestaj() {

		JButton btnIzvestaj = new JButton("Izveštaj");
		JButton btnPrevod = new JButton("Prevod");
		JButton btnProsireniIzvestaj = new JButton("Prošireni izveštaj");
		JButton btnIzvestajMeli = new JButton("Izveštaj za Meli");
		btnIzvestaj.setEnabled(true);
		btnProsireniIzvestaj.setEnabled(true);
		btnPrevod.setEnabled(true);
		btnIzvestajMeli.setEnabled(true);
		toolbar.dodajIzvestaj(btnIzvestaj);
		toolbar.dodajPrevod(btnPrevod);
		toolbar.dodajProsireniIzvestaj(btnProsireniIzvestaj);
		toolbar.dodajIzvestajMeli(btnIzvestajMeli);

		toolbar.getBtnIzvestaj().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					FakturisanaTableModel ctm = (FakturisanaTableModel) table.getModel();
					if (ctm.getRowCount() > 0) {
						napraviIzvestaj();
					} else {
						JOptionPane.showConfirmDialog(getParent(), "Ne postoji nijedna stavka.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
					}
				} catch (JRException e) {
					System.out.println("Jasper error");
					e.printStackTrace();
				} catch (ClassNotFoundException e1) {
					System.out.println("Nema klase");
				} catch (SQLException e2) {
					System.out.println("SQL error");
				}

			}

		});

		toolbar.getBtnPrevod().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					FakturisanaTableModel ctm = (FakturisanaTableModel) table.getModel();
					if (ctm.getRowCount() > 0) {
						napraviPrevod();
					} else {
						JOptionPane.showConfirmDialog(getParent(), "Ne postoji nijedna stavka.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
					}
				} catch (JRException e) {
					System.out.println("Jasper error");
					e.printStackTrace();
				} catch (ClassNotFoundException e1) {
					System.out.println("Nema klase");
				} catch (SQLException e2) {
					System.out.println("SQL error");
				}

			}

		});

		toolbar.getBtnProsireniIzvestaj().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					FakturisanaTableModel ctm = (FakturisanaTableModel) table.getModel();
					if (ctm.getRowCount() > 0) {
						napraviProsireniIzvestaj();
					} else {
						JOptionPane.showConfirmDialog(getParent(), "Ne postoji nijedna stavka.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
					}
				} catch (JRException e) {
					System.out.println("Jasper error");
					e.printStackTrace();
				} catch (ClassNotFoundException e1) {
					System.out.println("Nema klase");
				} catch (SQLException e2) {
					System.out.println("SQL error");
				}

			}

		});
		
		toolbar.getBtnIzvestajMeli().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					FakturisanaTableModel ctm = (FakturisanaTableModel) table.getModel();
					if (ctm.getRowCount() > 0) {
						napraviIzvestajMeli();
					} else {
						JOptionPane.showConfirmDialog(getParent(), "Ne postoji nijedna stavka.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
					}
				} catch (JRException e) {
					System.out.println("Jasper error");
					e.printStackTrace();
				} catch (ClassNotFoundException e1) {
					System.out.println("Nema klase");
				} catch (SQLException e2) {
					System.out.println("SQL error");
				}

			}

		});

	}

	public void napraviIzvestaj() throws JRException, ClassNotFoundException, SQLException {

		InputStream reportSrcFile = null;
		try {
			reportSrcFile = ResourceLoader.load("Reports/faktura.jrxml");
		} catch (Exception e) {
		}		

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		// First, compile jrxml file.
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);		

		Connection conn = DBConnection.getConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("sifraFakture", faktura);		

		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

		// Make sure the output directory exists.
		ResourceBundle bundle = PropertyResourceBundle.getBundle("util/Report");
		String path = bundle.getString("path") + "/" + "Izvoz " + faktura;
		File outDir = new File(path);
		outDir.mkdirs();

		// PDF Exportor.
		JRPdfExporter exporterP = new JRPdfExporter();
		JRDocxExporter exporterD = new JRDocxExporter();
		JRXlsxExporter exporterE = new JRXlsxExporter();	

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporterP.setExporterInput(exporterInput);
		exporterD.setExporterInput(exporterInput);
		exporterE.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutputP = new SimpleOutputStreamExporterOutput(
				path + "/Faktura " + faktura + " - " + timeStamp + ".pdf");

		OutputStreamExporterOutput exporterOutputD = new SimpleOutputStreamExporterOutput(
				path + "/Faktura " + faktura + " - " + timeStamp + ".docx");

		OutputStreamExporterOutput exporterOutputE = new SimpleOutputStreamExporterOutput(
				path + "/Faktura " + faktura + " - " + timeStamp + ".xlsx");		
		
		// Output
		exporterP.setExporterOutput(exporterOutputP);
		exporterD.setExporterOutput(exporterOutputD);
		exporterE.setExporterOutput(exporterOutputE);

		//
		SimplePdfExporterConfiguration configurationP = new SimplePdfExporterConfiguration();
		SimpleDocxExporterConfiguration configurationD = new SimpleDocxExporterConfiguration();
		SimpleXlsxExporterConfiguration configurationE = new SimpleXlsxExporterConfiguration();		

		exporterP.setConfiguration(configurationP);
		exporterD.setConfiguration(configurationD);
		exporterE.setConfiguration(configurationE);	

		exporterP.exportReport();
		exporterD.exportReport();
		exporterE.exportReport();

		JOptionPane.showConfirmDialog(getParent(),
				"Izveštaj o fakturi je uspešno kreiran i nalazi se u folderu " + path + ".", "Izveštaj",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);

	}

	public void napraviPrevod() throws JRException, ClassNotFoundException, SQLException {

		InputStream reportSrcFile = null;
		try {
			reportSrcFile = ResourceLoader.load("Reports/prevod.jrxml");
		} catch (Exception e) {
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		// First, compile jrxml file.
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

		Connection conn = DBConnection.getConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("sifraFakture", faktura);

		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

		// Make sure the output directory exists.
		ResourceBundle bundle = PropertyResourceBundle.getBundle("util/Report");
		String path = bundle.getString("path") + "/" + "Izvoz " + faktura;
		File outDir = new File(path);
		outDir.mkdirs();

		JRPdfExporter exporterP = new JRPdfExporter();
		JRDocxExporter exporterD = new JRDocxExporter();
		JRXlsxExporter exporterE = new JRXlsxExporter();

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporterP.setExporterInput(exporterInput);
		exporterD.setExporterInput(exporterInput);
		exporterE.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutputP = new SimpleOutputStreamExporterOutput(
				path + "/Prevod " + faktura + " - " + timeStamp + ".pdf");

		OutputStreamExporterOutput exporterOutputD = new SimpleOutputStreamExporterOutput(
				path + "/Prevod " + faktura + " - " + timeStamp + ".docx");

		OutputStreamExporterOutput exporterOutputE = new SimpleOutputStreamExporterOutput(
				path + "/Prevod " + faktura + " - " + timeStamp + ".xlsx");
		// Output
		exporterP.setExporterOutput(exporterOutputP);
		exporterD.setExporterOutput(exporterOutputD);
		exporterE.setExporterOutput(exporterOutputE);

		//
		SimplePdfExporterConfiguration configurationP = new SimplePdfExporterConfiguration();
		SimpleDocxExporterConfiguration configurationD = new SimpleDocxExporterConfiguration();
		SimpleXlsxExporterConfiguration configurationE = new SimpleXlsxExporterConfiguration();

		exporterP.setConfiguration(configurationP);
		exporterD.setConfiguration(configurationD);
		exporterE.setConfiguration(configurationE);

		exporterP.exportReport();
		exporterD.exportReport();
		exporterE.exportReport();

		JOptionPane.showConfirmDialog(getParent(),
				"Izveštaj o fakturi sa prevodom je uspešno kreiran i nalazi se u folderu " + path + ".", "Izveštaj",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);

	}

	public void napraviProsireniIzvestaj() throws JRException, ClassNotFoundException, SQLException {

		InputStream reportSrcFile = null;
		try {
			reportSrcFile = ResourceLoader.load("Reports/prosirenaFaktura.jrxml");
		} catch (Exception e) {
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		// First, compile jrxml file.
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

		Connection conn = DBConnection.getConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("sifraFakture", faktura);

		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

		// Make sure the output directory exists.
		ResourceBundle bundle = PropertyResourceBundle.getBundle("util/Report");
		String path = bundle.getString("path") + "/" + "Izvoz " + faktura;
		File outDir = new File(path);
		outDir.mkdirs();

		JRPdfExporter exporterP = new JRPdfExporter();		

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporterP.setExporterInput(exporterInput);		

		// ExporterOutput
		OutputStreamExporterOutput exporterOutputP = new SimpleOutputStreamExporterOutput(
				path + "/Prosirena Faktura " + faktura + " - " + timeStamp + ".pdf");

		// Output
		exporterP.setExporterOutput(exporterOutputP);
		
		SimplePdfExporterConfiguration configurationP = new SimplePdfExporterConfiguration();
		
		exporterP.setConfiguration(configurationP);

		exporterP.exportReport();

		JOptionPane.showConfirmDialog(getParent(),
				"Prošireni izveštaj o fakturi je uspešno kreiran i nalazi se u folderu " + path + ".", "Izveštaj",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);

	}
	
	public void napraviIzvestajMeli() throws JRException, ClassNotFoundException, SQLException {

		InputStream reportSrcFile = null;
		try {
			reportSrcFile = ResourceLoader.load("Reports/kurs.jrxml");
		} catch (Exception e) {
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		// First, compile jrxml file.
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

		Connection conn = DBConnection.getConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("sifraFakture", faktura);

		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

		// Make sure the output directory exists.
		ResourceBundle bundle = PropertyResourceBundle.getBundle("util/Report");
		String path = bundle.getString("path") + "/" + "Izvoz " + faktura;
		File outDir = new File(path);
		outDir.mkdirs();

		JRPdfExporter exporterP = new JRPdfExporter();		

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporterP.setExporterInput(exporterInput);		

		// ExporterOutput
		OutputStreamExporterOutput exporterOutputP = new SimpleOutputStreamExporterOutput(
				path + "/Meli Faktura " + faktura + " - " + timeStamp + ".pdf");

		// Output
		exporterP.setExporterOutput(exporterOutputP);
		
		SimplePdfExporterConfiguration configurationP = new SimplePdfExporterConfiguration();
		
		exporterP.setConfiguration(configurationP);

		exporterP.exportReport();

		JOptionPane.showConfirmDialog(getParent(),
				"Izveštaj za Meli o fakturi je uspešno kreiran i nalazi se u folderu " + path + ".", "Izveštaj",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);

	}


	public void addCelaPorudzbina() {

		JButton btnPorudzbina = new JButton("Dodaj celu porudzbinu");
		btnPorudzbina.setEnabled(true);
		toolbar.dodajCeluPorudzbinu(btnPorudzbina);
		toolbar.addSeparator();

		toolbar.getBtnPorudzbina().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String porudzbina = "";

				DialogPorudzbina dialog = new DialogPorudzbina(MainFrame.getInstance(), true);
				dialog.setVisible(true);
				try {
					if (!dialog.getZoom1().equals(""))
						porudzbina = dialog.getZoom1();
				} catch (NullPointerException n) {
				}

				celaPorudzbina(porudzbina);
				toolbar.getBtnRefresh().doClick();
			}
		});
	}

	public void celaPorudzbina(String sifraPorudzbine) {

		String porudzbina = sifraPorudzbine;

		String upitFakturisana = "SELECT fakturisana_roba.sifra_robe as sifraRobe, fakturisana_roba.sifra_porudzbine as sifraPorudzbine, fakturisana_roba.datum_isporuke as datumIsporuke FROM fakturisana_roba WHERE fakturisana_roba.sifra_fakture = '"
				+ faktura + "'";

		String upitPorudzbina = "SELECT narucena_roba.sifra_robe as sifraRobe, narucena_roba.sifra_porudzbine as sifraPorudzbine, narucena_roba.datum_isporuke as datumIsporuke, komada_ostalo FROM narucena_roba WHERE narucena_roba.komada_ostalo > 0 AND narucena_roba.sifra_porudzbine = '"
				+ porudzbina + "'";

		ArrayList<Roba> mapaRobe = new ArrayList<Roba>();
		ArrayList<Roba> novaMapa = new ArrayList<Roba>();
		Roba roba = new Roba("", "", "", "");
		mapaRobe.add(roba);

		try {

			Statement stmt = DBConnection.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(upitFakturisana);

			while (rset.next()) {
				String sifra_robe = rset.getString("sifraRobe");
				String sifra_porudzbine = rset.getString("sifraPorudzbine");
				String datum = rset.getString("datumIsporuke");

				Roba r = new Roba(sifra_robe, sifra_porudzbine, datum, "0");

				mapaRobe.add(r);
			}

			rset.close();
			stmt.close();

			Statement stmt1 = DBConnection.getConnection().createStatement();
			ResultSet rset1 = stmt1.executeQuery(upitPorudzbina);

			while (rset1.next()) {

				String sifra_robe = rset1.getString("sifraRobe");
				String sifra_porudzbine = rset1.getString("sifraPorudzbine");
				String datum = rset1.getString("datumIsporuke");
				String komada = rset1.getString("KOMADA_OSTALO");

				boolean postoji = false;

				for (Roba r : mapaRobe) {

					if (sifra_robe.equals(r.getSifra()) && sifra_porudzbine.equals(r.getPorudzbina())
							&& datum.equals(r.getDatum())) {
						postoji = true;
					}
				}

				if (!postoji) {
					Roba nova = new Roba(sifra_robe, sifra_porudzbine, datum, komada);
					novaMapa.add(nova);
				}
			}

			rset1.close();
			stmt1.close();

			for (Roba r : novaMapa) {

				PreparedStatement stmt3 = DBConnection.getConnection().prepareStatement(
						"INSERT INTO fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, komada_u_metru, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				stmt3.setString(1, r.getSifra());
				stmt3.setString(2, r.getPorudzbina());
				stmt3.setString(3, r.getDatum());
				stmt3.setString(4, faktura);
				stmt3.setString(5, r.getKomada());
				stmt3.setString(6, "");
				stmt3.setString(7, "0");
				stmt3.setString(8, "narucena");

				stmt3.executeUpdate();
				stmt3.close();

			}

			DBConnection.getConnection().commit();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
		}

	}

}
