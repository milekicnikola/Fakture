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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import databaseConnection.DBConnection;
import gui.model.OtpremljenaTableModel;
import gui.panels.OtpremljenaPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import util.ResourceLoader;

public class DialogOtpremljena extends StandardDialog {

	private String otpremnica = "";
	private String faktura = "";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogOtpremljena(JFrame parent, Boolean zoom, String where, String magacin1, String sifraFakture, String poslata) {
		super(parent);
		setTitle("Otrpemljena roba");
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/otpremnica.png"));			
		} catch (Exception e) {			

		}
		setIconImage(image);

		otpremnica = where;
		
		faktura = sifraFakture;

		String whereStm = "";

		whereStm = " WHERE otpremljena_roba.sifra_otpremnice = '" + otpremnica
				+ "' and otpremljena_roba.status_robe != 'narucena'";

		tableModel = new OtpremljenaTableModel(
				new String[] { "Šifra fakture", "Šifra robe", "Naziv robe", "Šifra porudzbine", "Datum isporuke",
						"Komada naručeno", "Komada fakturisano", "Opis", "Status", "Otpremnica" },
				0, whereStm);

		panel = new OtpremljenaPanel();

		/*
		 * if (zoom) isZoom = true;
		 */

		initGUI();
		initStandardActions();
		initActions();

		if (poslata.equals("ne"))
			addPosalji();

		addIzvestaj();

		toolbar.remove(9);
		toolbar.remove(9);
		toolbar.remove(9);
		toolbar.remove(1);

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
			 * DialogNarucena(MainFrame .getInstance(), true, ((OtpremljenaPanel)
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
			/*
			 * ((OtpremljenaPanel) panel).getBtnRoba().addActionListener( new
			 * ActionListener() {
			 * 
			 * @Override public void actionPerformed(ActionEvent arg0) { // TODO
			 * Auto-generated method stub DialogNarucena dialog = new DialogNarucena(
			 * MainFrame.getInstance(), true, "faktura"); dialog.setVisible(true); try { if
			 * (!dialog.getZoom1().equals("")) ((OtpremljenaPanel) panel).getTxtSifraP()
			 * .setText(dialog.getZoom1()); if (!dialog.getZoom2().equals(""))
			 * ((OtpremljenaPanel) panel).getTxtSifraR() .setText(dialog.getZoom2()); if
			 * (!dialog.getZoom3().equals("")) ((OtpremljenaPanel) panel).getTxtNazivR()
			 * .setText(dialog.getZoom3()); if (!dialog.getZoom4().equals("")) {
			 * preuzetDatum = dialog.getZoom4(); SimpleDateFormat sdf = new
			 * SimpleDateFormat( "yyyy-MM-dd"); Date date = null; try { date =
			 * sdf.parse(preuzetDatum); } catch (ParseException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); }
			 * 
			 * ((OtpremljenaPanel) panel).getTxtDatum() .setDate(date); } } catch
			 * (NullPointerException n) { } } });
			 */
			((OtpremljenaPanel) panel).getTxtOtpremnica().setText(otpremnica);
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
			String sifraP = (String) tableModel.getValueAt(index, 3);
			String datum = (String) tableModel.getValueAt(index, 4);
			String naruceno = (String) tableModel.getValueAt(index, 5);
			String komada = (String) tableModel.getValueAt(index, 6);
			String opis = (String) tableModel.getValueAt(index, 7);
			String status = (String) tableModel.getValueAt(index, 8);
			String otpremnica = (String) tableModel.getValueAt(index, 9);

			((OtpremljenaPanel) panel).getTxtSifraF().setText(sifraF);
			((OtpremljenaPanel) panel).getTxtSifraP().setText(sifraP);
			((OtpremljenaPanel) panel).getTxtSifraR().setText(sifraR);
			((OtpremljenaPanel) panel).getTxtNazivR().setText(nazivR);
			((OtpremljenaPanel) panel).getTxtNaruceno().setText(naruceno);
			((OtpremljenaPanel) panel).getTxtKomada().setText(komada);
			((OtpremljenaPanel) panel).getTaOpis().setText(opis);
			((OtpremljenaPanel) panel).getTxtStatus().setText(status);
			((OtpremljenaPanel) panel).getTxtOtpremnica().setText(otpremnica);
			((OtpremljenaPanel) panel).getTxtDatum().setText(datum);

		}

	}

	@Override
	public void updateStateAndTextFields(State state) {
		if (this.state == State.PRETRAGA && state != State.PRETRAGA) {
			try {
				tableModel.open();
				allDisable();
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
			// ((OtpremljenaPanel) panel).getBtnRoba().setEnabled(false);
			((OtpremljenaPanel) panel).getTxtDatum().setEditable(false);
			((OtpremljenaPanel) panel).getTxtSifraP().setEditable(false);
			((OtpremljenaPanel) panel).getTxtNaruceno().setEditable(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			if (state == State.PRETRAGA) {
				((OtpremljenaPanel) panel).getTxtStatus().setEditable(true);
				((OtpremljenaPanel) panel).getTxtSifraP().setEditable(true);
				((OtpremljenaPanel) panel).getTxtNaruceno().setEditable(true);
			} else {
				((OtpremljenaPanel) panel).getTxtStatus().setEditable(false);
				((OtpremljenaPanel) panel).getTxtSifraP().setEditable(false);
				((OtpremljenaPanel) panel).getTxtNaruceno().setEditable(false);
			}
		}
		((OtpremljenaPanel) panel).getTxtKomada().requestFocus();
		statusBar.getStatusState().setText(state.toString());

		this.state = state;
	}

	@Override
	public void addRow() {

		/*
		 * String sifraP = ((OtpremljenaPanel) panel).getTxtSifraP().getText() .trim();
		 * String sifraR = ((OtpremljenaPanel) panel).getTxtSifraR().getText() .trim();
		 * String nazivR = ((OtpremljenaPanel) panel).getTxtNazivR().getText() .trim();
		 * String sifraF = ((OtpremljenaPanel) panel).getTxtSifraF().getText() .trim();
		 * String komada = ((OtpremljenaPanel) panel).getTxtKomada().getText() .trim();
		 * String opis = ((OtpremljenaPanel) panel).getTaOpis().getText().trim();
		 * 
		 * String[] params = { sifraF, sifraR, nazivR, sifraP, preuzetDatum, komada,
		 * opis, "narucena" };
		 * 
		 * try { FakturisanaTableModel ctm = (FakturisanaTableModel) table .getModel();
		 * int index = ctm.insertRow(params); table.setRowSelectionInterval(index,
		 * index); updateStateAndTextFields(State.DODAVANJE); } catch (SQLException ex)
		 * { JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
		 * JOptionPane.ERROR_MESSAGE); }
		 */

	}

	@Override
	public void updateRow() {
		/*
		 * int i = table.getSelectedRow(); if (i == -1) return;
		 * 
		 * String komada = ((OtpremljenaPanel) panel).getTxtKomada().getText() .trim();
		 * String opis = ((OtpremljenaPanel) panel).getTaOpis().getText().trim(); String
		 * status = ((OtpremljenaPanel) panel).getTxtStatus() .getText().trim();
		 * 
		 * Date datum1 = ((OtpremljenaPanel) panel).getTxtDatum().getDate(); String
		 * datum = ""; if (datum1 != null) { datum = new
		 * SimpleDateFormat("yyyy-MM-dd").format(datum1); }
		 * 
		 * 
		 * String[] params = { komada, opis, status }; int index =
		 * table.getSelectedRow(); try { FakturisanaTableModel ctm =
		 * (FakturisanaTableModel) table .getModel(); ctm.updateRow(index, params);
		 * updateStateAndTextFields(State.AZURIRANJE); } catch (SQLException ex) {
		 * JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška1",
		 * JOptionPane.ERROR_MESSAGE); } table.setRowSelectionInterval(index, index);
		 */
	}

	@Override
	public void search() {

		/*
		 * String sifraF = ((OtpremljenaPanel) panel).getTxtSifraF().getText() .trim();
		 * String sifraP = ((OtpremljenaPanel) panel).getTxtSifraP().getText() .trim();
		 * String sifraR = ((OtpremljenaPanel) panel).getTxtSifraR().getText() .trim();
		 * String komada = ((OtpremljenaPanel) panel).getTxtKomada().getText() .trim();
		 * String opis = ((OtpremljenaPanel) panel).getTaOpis().getText().trim(); String
		 * status = ((OtpremljenaPanel) panel).getTxtStatus() .getText().trim();
		 * 
		 * String[] params = { sifraR, sifraP, preuzetDatum, sifraF, komada, opis,
		 * status };
		 * 
		 * try { FakturisanaTableModel ctm = (FakturisanaTableModel) table .getModel();
		 * ctm.search(params); updateStateAndTextFields(State.PRETRAGA); } catch
		 * (SQLException ex) { JOptionPane.showMessageDialog(this, ex.getMessage(),
		 * "Greška", JOptionPane.ERROR_MESSAGE); }
		 */
	}

	@Override
	public void allDisable() {
		((OtpremljenaPanel) panel).getBtnConfirm().setEnabled(false);
		((OtpremljenaPanel) panel).getBtnCancel().setEnabled(false);
		((OtpremljenaPanel) panel).getTxtSifraP().setEditable(false);
		((OtpremljenaPanel) panel).getTxtSifraR().setEditable(false);
		((OtpremljenaPanel) panel).getTxtNazivR().setEditable(false);
		((OtpremljenaPanel) panel).getTxtSifraF().setEditable(false);
		((OtpremljenaPanel) panel).getTxtKomada().setEditable(false);
		((OtpremljenaPanel) panel).getTaOpis().setEditable(false);
		((OtpremljenaPanel) panel).getTaOpis().setEnabled(false);
		((OtpremljenaPanel) panel).getTxtDatum().setEditable(false);
		((OtpremljenaPanel) panel).getTxtStatus().setEditable(false);
		((OtpremljenaPanel) panel).getTxtOtpremnica().setEditable(false);
		((OtpremljenaPanel) panel).getTxtNaruceno().setEditable(false);
	}

	public void allEnable() {
		/*
		 * ((OtpremljenaPanel) panel).getBtnConfirm().setEnabled(true);
		 * ((OtpremljenaPanel) panel).getBtnCancel().setEnabled(true);
		 * ((OtpremljenaPanel) panel).getTxtKomada().setEditable(true);
		 * ((OtpremljenaPanel) panel).getTaOpis().setEditable(true); ((OtpremljenaPanel)
		 * panel).getTaOpis().setEnabled(true);
		 */

	}

	public void clearAll() {
		/*
		 * ((OtpremljenaPanel) panel).getTxtSifraR().setText(""); ((OtpremljenaPanel)
		 * panel).getTxtNazivR().setText(""); ((OtpremljenaPanel)
		 * panel).getTxtKomada().setText(""); ((OtpremljenaPanel)
		 * panel).getTxtStatus().setText(""); ((OtpremljenaPanel)
		 * panel).getTaOpis().setText(""); ((OtpremljenaPanel)
		 * panel).getTxtSifraP().setText(""); ((OtpremljenaPanel)
		 * panel).getTxtDatum().setCalendar(null);
		 */
	}

	public void btnEnable() {
		((OtpremljenaPanel) panel).getBtnConfirm().setEnabled(true);
		((OtpremljenaPanel) panel).getBtnCancel().setEnabled(true);
	}

	public void addPosalji() {

		JButton btnPosalji = new JButton("Otpremi robu");
		btnPosalji.setEnabled(true);
		toolbar.dodajPosalji(btnPosalji);

		toolbar.getBtnPosalji().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				OtpremljenaTableModel ctm = (OtpremljenaTableModel) table.getModel();
				if (ctm.getRowCount() > 0) {

					int dialogResult = JOptionPane.showConfirmDialog(getParent(),
							"Da li ste sigurni da želite da pošaljete ovu otpremnicu?", "Slanje otpremnice",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (dialogResult == JOptionPane.YES_OPTION) {
						srediPodatke();
						toolbar.getBtnPosalji().setEnabled(false);
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

		String upit = "SELECT otpremljena_roba.sifra_otpremnice as sifraOtpremnice, otpremljena_roba.sifra_robe as sifraRobe, naziv_robe, otpremljena_roba.sifra_porudzbine as sifraPorudzbine, otpremljena_roba.datum_isporuke as datumIsporuke, otpremljena_roba.sifra_fakture as sifraFakture, komada_naruceno, komada_fakturisano, opis, status FROM otpremljena_roba JOIN fakturisana_roba ON otpremljena_roba.sifra_robe = fakturisana_roba.sifra_robe AND otpremljena_roba.sifra_porudzbine = fakturisana_roba.sifra_porudzbine AND otpremljena_roba.datum_isporuke = fakturisana_roba.datum_isporuke JOIN narucena_roba ON otpremljena_roba.sifra_robe = narucena_roba.sifra_robe AND otpremljena_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND otpremljena_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN porudzbina ON otpremljena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON otpremljena_roba.sifra_robe = roba.sifra_robe WHERE otpremljena_roba.sifra_otpremnice = '"
				+ otpremnica + "' and otpremljena_roba.status_robe != 'narucena'";
		try {

			Statement stmt = DBConnection.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(upit);

			while (rset.next()) {
				String sifra_robe = rset.getString("sifraRobe");
				String sifra_porudzbine = rset.getString("sifraPorudzbine");
				String datum = rset.getString("datumIsporuke");
				String faktura = rset.getString("sifraFakture");

				PreparedStatement stmt1 = DBConnection.getConnection().prepareStatement(
						"UPDATE otpremljena_roba SET status_robe = ? WHERE sifra_robe = ? and sifra_porudzbine = ? and datum_isporuke = ? and sifra_fakture = ?");

				stmt1.setString(1, "otpremljena");
				stmt1.setString(2, sifra_robe);
				stmt1.setString(3, sifra_porudzbine);
				stmt1.setString(4, datum);
				stmt1.setString(5, faktura);
				stmt1.executeUpdate();
				stmt1.close();

				PreparedStatement stmt2 = DBConnection.getConnection().prepareStatement(
						"UPDATE fakturisana_roba SET status = ? WHERE sifra_robe = ? and sifra_porudzbine = ? and datum_isporuke = ? and sifra_fakture = ?");

				stmt2.setString(1, "otpremljena");
				stmt2.setString(2, sifra_robe);
				stmt2.setString(3, sifra_porudzbine);
				stmt2.setString(4, datum);
				stmt2.setString(5, faktura);
				stmt2.executeUpdate();
				stmt2.close();

			}

			rset.close();
			stmt.close();

			PreparedStatement stmt2 = DBConnection.getConnection()
					.prepareStatement("UPDATE otpremnica SET poslata_otpremnica = ? WHERE sifra_otpremnice = ?");

			stmt2.setString(1, "da");
			stmt2.setString(2, otpremnica);
			stmt2.executeUpdate();
			stmt2.close();

			DBConnection.getConnection().commit();

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void addIzvestaj() {

		JButton btnIzvestaj = new JButton("Napravi izveštaj");
		JButton btnIzvestajC = new JButton("Napravi izveštaj za C");
		btnIzvestaj.setEnabled(true);
		btnIzvestajC.setEnabled(true);

		toolbar.dodajIzvestaj(btnIzvestaj);
		toolbar.dodajProsireniIzvestaj(btnIzvestajC);

		toolbar.getBtnIzvestaj().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					OtpremljenaTableModel ctm = (OtpremljenaTableModel) table.getModel();
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

		toolbar.getBtnProsireniIzvestaj().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					OtpremljenaTableModel ctm = (OtpremljenaTableModel) table.getModel();
					if (ctm.getRowCount() > 0) {
						napraviIzvestaj2();
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
			reportSrcFile = ResourceLoader.load("Reports/otpremnica.jrxml");
		} catch (Exception e) {			
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		// First, compile jrxml file.
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

		Connection conn = DBConnection.getConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("sifraOtpremnice", otpremnica);

		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

		// Make sure the output directory exists.
		ResourceBundle bundle = PropertyResourceBundle
				.getBundle("util/Report");
		String path = bundle.getString("path") + "/" + "Izvoz " + faktura;
		File outDir = new File(path);
		outDir.mkdirs();

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				path + "/Otpremnica " + otpremnica + " - " + timeStamp + ".pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JOptionPane.showConfirmDialog(getParent(),
				"Izveštaj o otpremnici je uspešno kreiran i nalazi se u folderu " + path + ".", "Izveštaj",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);

	}

	public void napraviIzvestaj2() throws JRException, ClassNotFoundException, SQLException {

		InputStream reportSrcFile = null;
		try {
			reportSrcFile = ResourceLoader.load("Reports/otpremnicaCprofil.jrxml");
		} catch (Exception e) {			
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		// First, compile jrxml file.
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

		Connection conn = DBConnection.getConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("sifraOtpremnice", otpremnica);

		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

		// Make sure the output directory exists.
		ResourceBundle bundle = PropertyResourceBundle
				.getBundle("util/Report");
		String path = bundle.getString("path") + "/" + "Izvoz " + faktura;
		File outDir = new File(path);
		outDir.mkdirs();

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				path + "/Otpremnica C-profil " + otpremnica + " - " + timeStamp + ".pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JOptionPane.showConfirmDialog(getParent(),
				"Izveštaj o otpremnici C-profila je uspešno kreiran i nalazi se u folderu " + path + ".",
				"Izveštaj", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);

	}

}
