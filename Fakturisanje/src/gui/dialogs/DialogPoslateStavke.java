package gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import databaseConnection.DBConnection;
import gui.model.PoslateStavkeTableModel;
import gui.panels.PoslateStavkePanel;
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

public class DialogPoslateStavke extends StandardDialog {
	
	private JButton btnSve;
	private JButton btnOlgica;
	private JButton btnMilos;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogPoslateStavke(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Poslate stavke");
		setIconImage(new ImageIcon("Images/poslateStavke.png").getImage());

		tableModel = new PoslateStavkeTableModel(new String[] { "Šifra robe",
				"Naziv robe", "Interni naziv robe", "Porudzbina", "Faktura",
				"Otpremnica", "Datum isporuke", "Korisnik", "Komada naručeno",
				"Komada poslato", "Komada ostalo", "Ko radi" }, 0);

		if (zoom)
			isZoom = true;

		panel = new PoslateStavkePanel();

		initGUI();
		initStandardActions();
		initActions();

		if (isZoom)
			addIzvestaj();
		
		toolbar.remove(9);
		toolbar.remove(9);		
		toolbar.remove(9);

		toolbar.getBtnAdd().setEnabled(false);
		toolbar.getBtnUpdate().setEnabled(false);
		toolbar.getBtnDelete().setEnabled(false);

	}

	@Override
	public void initActions() {
		
		btnSve = new JButton("Sve stavke");
		btnOlgica = new JButton("Olgicine stavke");
		btnMilos = new JButton("Miloseve stavke");

		btnSve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				try {
					tableModel.fillData("SELECT otpremljena_roba.sifra_robe as sifraRobe, naziv_robe, interni_naziv, otpremljena_roba.sifra_otpremnice as sifraOtpremnice, otpremljena_roba.sifra_porudzbine as sifraPorudzbine, otpremljena_roba.sifra_fakture as sifraFakture, otpremljena_roba.datum_isporuke as datumIsporuke, komada_naruceno, komada_fakturisano, komada_ostalo, ko_radi, narucena_roba.korisnicko_ime as korisnickoIme FROM otpremljena_roba JOIN otpremnica ON otpremljena_roba.sifra_otpremnice = otpremnica.sifra_otpremnice JOIN fakturisana_roba ON otpremljena_roba.sifra_robe = fakturisana_roba.sifra_robe AND otpremljena_roba.sifra_porudzbine = fakturisana_roba.sifra_porudzbine AND otpremljena_roba.datum_isporuke = fakturisana_roba.datum_isporuke AND otpremljena_roba.sifra_fakture = fakturisana_roba.sifra_fakture JOIN narucena_roba ON otpremljena_roba.sifra_robe = narucena_roba.sifra_robe AND otpremljena_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND otpremljena_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON otpremljena_roba.sifra_robe = roba.sifra_robe JOIN korisnik ON narucena_roba.korisnicko_ime = korisnik.korisnicko_ime WHERE otpremljena_roba.status_robe = 'otpremljena'");
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		});
		
		btnOlgica.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				try {
					tableModel.fillData("SELECT otpremljena_roba.sifra_robe as sifraRobe, naziv_robe, interni_naziv, otpremljena_roba.sifra_otpremnice as sifraOtpremnice, otpremljena_roba.sifra_porudzbine as sifraPorudzbine, otpremljena_roba.sifra_fakture as sifraFakture, otpremljena_roba.datum_isporuke as datumIsporuke, komada_naruceno, komada_fakturisano, komada_ostalo, ko_radi, narucena_roba.korisnicko_ime as korisnickoIme FROM otpremljena_roba JOIN otpremnica ON otpremljena_roba.sifra_otpremnice = otpremnica.sifra_otpremnice JOIN fakturisana_roba ON otpremljena_roba.sifra_robe = fakturisana_roba.sifra_robe AND otpremljena_roba.sifra_porudzbine = fakturisana_roba.sifra_porudzbine AND otpremljena_roba.datum_isporuke = fakturisana_roba.datum_isporuke AND otpremljena_roba.sifra_fakture = fakturisana_roba.sifra_fakture JOIN narucena_roba ON otpremljena_roba.sifra_robe = narucena_roba.sifra_robe AND otpremljena_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND otpremljena_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON otpremljena_roba.sifra_robe = roba.sifra_robe JOIN korisnik ON narucena_roba.korisnicko_ime = korisnik.korisnicko_ime WHERE otpremljena_roba.status_robe = 'otpremljena' AND narucena_roba.korisnicko_ime = 'olgica'");
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		});
		
		btnMilos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				try {
					tableModel.fillData("SELECT otpremljena_roba.sifra_robe as sifraRobe, naziv_robe, interni_naziv, otpremljena_roba.sifra_otpremnice as sifraOtpremnice, otpremljena_roba.sifra_porudzbine as sifraPorudzbine, otpremljena_roba.sifra_fakture as sifraFakture, otpremljena_roba.datum_isporuke as datumIsporuke, komada_naruceno, komada_fakturisano, komada_ostalo, ko_radi, narucena_roba.korisnicko_ime as korisnickoIme FROM otpremljena_roba JOIN otpremnica ON otpremljena_roba.sifra_otpremnice = otpremnica.sifra_otpremnice JOIN fakturisana_roba ON otpremljena_roba.sifra_robe = fakturisana_roba.sifra_robe AND otpremljena_roba.sifra_porudzbine = fakturisana_roba.sifra_porudzbine AND otpremljena_roba.datum_isporuke = fakturisana_roba.datum_isporuke AND otpremljena_roba.sifra_fakture = fakturisana_roba.sifra_fakture JOIN narucena_roba ON otpremljena_roba.sifra_robe = narucena_roba.sifra_robe AND otpremljena_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND otpremljena_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON otpremljena_roba.sifra_robe = roba.sifra_robe JOIN korisnik ON narucena_roba.korisnicko_ime = korisnik.korisnicko_ime WHERE otpremljena_roba.status_robe = 'otpremljena' AND narucena_roba.korisnicko_ime = 'milos'");
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		});
		
		toolbar.add(btnSve);
		toolbar.addSeparator();
		toolbar.add(btnOlgica);
		toolbar.addSeparator();
		toolbar.add(btnMilos);
		toolbar.addSeparator();

	}

	@Override
	public void sync() {
		int index = table.getSelectedRow();
		if (index < 0) {
			clearAll();
			return;
		}

		if (index <= (table.getModel().getRowCount() - 1)) {

			String sifraR = (String) tableModel.getValueAt(index, 0);
			String nazivR = (String) tableModel.getValueAt(index, 1);
			String interni = (String) tableModel.getValueAt(index, 2);
			String sifraP = (String) tableModel.getValueAt(index, 3);
			String faktura = (String) tableModel.getValueAt(index, 4);
			String otpremnica = (String) tableModel.getValueAt(index, 5);
			String datum = (String) tableModel.getValueAt(index, 6);
			String korisnik = (String) tableModel.getValueAt(index, 7);
			String naruceno = (String) tableModel.getValueAt(index, 8);
			String poslato = (String) tableModel.getValueAt(index, 9);
			String ostalo = (String) tableModel.getValueAt(index, 10);
			String ko = (String) tableModel.getValueAt(index, 11);

			((PoslateStavkePanel) panel).getTxtSifraP().setText(sifraP);
			((PoslateStavkePanel) panel).getTxtSifraR().setText(sifraR);
			((PoslateStavkePanel) panel).getTxtNazivR().setText(nazivR);
			((PoslateStavkePanel) panel).getTxtInterni().setText(interni);
			((PoslateStavkePanel) panel).getTxtNaruceno().setText(naruceno);
			((PoslateStavkePanel) panel).getTxtPoslato().setText(poslato);
			((PoslateStavkePanel) panel).getTxtOstalo().setText(ostalo);
			((PoslateStavkePanel) panel).getTxtKo().setText(ko);
			((PoslateStavkePanel) panel).getTxtKorisnik().setText(korisnik);
			((PoslateStavkePanel) panel).getTxtFaktura().setText(faktura);
			((PoslateStavkePanel) panel).getTxtOtpremnica().setText(otpremnica);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(datum);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			((PoslateStavkePanel) panel).getTxtDatum().setDate(date);

		}

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
			// btnEnable();
			// allEnable();
			((PoslateStavkePanel) panel).getTxtDatum().setEnabled(false);
			((PoslateStavkePanel) panel).getTxtNaruceno().setEditable(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			((PoslateStavkePanel) panel).getTxtNaruceno().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		/*
		 * String sifraP = ((PoslateStavkePanel)
		 * panel).getTxtSifraP().getText().trim(); String sifraR =
		 * ((PoslateStavkePanel) panel).getTxtSifraR().getText().trim(); String
		 * nazivR = ((PoslateStavkePanel)
		 * panel).getTxtNazivR().getText().trim(); String naruceno =
		 * ((PoslateStavkePanel) panel).getTxtNaruceno().getText() .trim();
		 * String poslato = "0"; String ostalo = naruceno; String ko =
		 * ((PoslateStavkePanel) panel).getTxtKo().getText().trim(); Date
		 * datum1 = ((PoslateStavkePanel) panel).getTxtDatum().getDate();
		 * String datum = ""; if (datum1 != null) { datum = new
		 * SimpleDateFormat("yyyy-MM-dd").format(datum1); }
		 * 
		 * String[] params = { sifraP, sifraR, nazivR, datum, naruceno, poslato,
		 * ostalo, ko };
		 * 
		 * try { NarucenaTableModel ctm = (NarucenaTableModel) table.getModel();
		 * int index = ctm.insertRow(params);
		 * table.setRowSelectionInterval(index, index);
		 * updateStateAndTextFields(State.DODAVANJE); } catch (SQLException ex)
		 * { JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
		 * JOptionPane.ERROR_MESSAGE); }
		 */

	}

	@Override
	public void updateRow() {
		/*
		 * int i = table.getSelectedRow(); if (i == -1) return;
		 * 
		 * // String naruceno = ((PoslateStavkePanel) //
		 * panel).getTxtNaruceno().getText().trim(); // String poslato =
		 * ((PoslateStavkePanel) // panel).getTxtPoslato().getText().trim(); //
		 * String ostalo = ((PoslateStavkePanel) //
		 * panel).getTxtOstalo().getText().trim(); String ko =
		 * ((PoslateStavkePanel) panel).getTxtKo().getText().trim();
		 * 
		 * Date datum1 = ((PoslateStavkePanel) panel).getTxtDatum().getDate();
		 * String datum = ""; if (datum1 != null) { datum = new
		 * SimpleDateFormat("yyyy-MM-dd").format(datum1); }
		 * 
		 * 
		 * String[] params = { ko }; int index = table.getSelectedRow(); try {
		 * NarucenaTableModel ctm = (NarucenaTableModel) table.getModel();
		 * ctm.updateRow(index, params);
		 * updateStateAndTextFields(State.AZURIRANJE); } catch (SQLException ex)
		 * { JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
		 * JOptionPane.ERROR_MESSAGE); } table.setRowSelectionInterval(index,
		 * index);
		 */
	}

	@Override
	public void search() {

		String sifraP = ((PoslateStavkePanel) panel).getTxtSifraP().getText()
				.trim();
		String sifraR = ((PoslateStavkePanel) panel).getTxtSifraR().getText()
				.trim();		
		String sifraF = ((PoslateStavkePanel) panel).getTxtFaktura().getText()
				.trim();
		String sifraO = ((PoslateStavkePanel) panel).getTxtOtpremnica().getText()
				.trim();
		String ko = ((PoslateStavkePanel) panel).getTxtKo().getText().trim();
		String korisnik = ((PoslateStavkePanel) panel).getTxtKorisnik()
				.getText().trim();
		Date datum1 = ((PoslateStavkePanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}

		String[] params = { sifraR, sifraP, sifraF, sifraO, datum,
				korisnik, ko };

		try {
			PoslateStavkeTableModel ctm = (PoslateStavkeTableModel) table
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
		((PoslateStavkePanel) panel).getBtnConfirm().setEnabled(false);
		((PoslateStavkePanel) panel).getBtnCancel().setEnabled(false);
		((PoslateStavkePanel) panel).getTxtSifraP().setEditable(false);
		((PoslateStavkePanel) panel).getTxtSifraR().setEditable(false);
		((PoslateStavkePanel) panel).getTxtNazivR().setEditable(false);
		((PoslateStavkePanel) panel).getTxtNaruceno().setEditable(false);
		((PoslateStavkePanel) panel).getTxtPoslato().setEditable(false);
		((PoslateStavkePanel) panel).getTxtOstalo().setEditable(false);
		((PoslateStavkePanel) panel).getTxtDatum().setEnabled(false);
		((PoslateStavkePanel) panel).getTxtKo().setEditable(false);
		((PoslateStavkePanel) panel).getTxtInterni().setEditable(false);
		((PoslateStavkePanel) panel).getTxtKorisnik().setEditable(false);
		((PoslateStavkePanel) panel).getTxtFaktura().setEditable(false);
		((PoslateStavkePanel) panel).getTxtOtpremnica().setEditable(false);
	}

	public void allEnable() {

		((PoslateStavkePanel) panel).getBtnConfirm().setEnabled(true);
		((PoslateStavkePanel) panel).getBtnCancel().setEnabled(true);		
		((PoslateStavkePanel) panel).getTxtDatum().setEnabled(true);
		((PoslateStavkePanel) panel).getTxtKo().setEditable(true);
		((PoslateStavkePanel) panel).getTxtSifraR().setEditable(true);
		((PoslateStavkePanel) panel).getTxtSifraP().setEditable(true);
		((PoslateStavkePanel) panel).getTxtKorisnik().setEditable(true);
		((PoslateStavkePanel) panel).getTxtFaktura().setEditable(true);
		((PoslateStavkePanel) panel).getTxtOtpremnica().setEditable(true);

	}

	public void clearAll() {
		((PoslateStavkePanel) panel).getTxtSifraR().setText("");
		((PoslateStavkePanel) panel).getTxtSifraP().setText("");
		((PoslateStavkePanel) panel).getTxtNazivR().setText("");
		((PoslateStavkePanel) panel).getTxtInterni().setText("");
		((PoslateStavkePanel) panel).getTxtNaruceno().setText("");
		((PoslateStavkePanel) panel).getTxtPoslato().setText("");
		((PoslateStavkePanel) panel).getTxtOstalo().setText("");
		((PoslateStavkePanel) panel).getTxtKo().setText("");
		((PoslateStavkePanel) panel).getTxtKorisnik().setText("");
		((PoslateStavkePanel) panel).getTxtFaktura().setText("");
		((PoslateStavkePanel) panel).getTxtOtpremnica().setText("");
		((PoslateStavkePanel) panel).getTxtDatum().setCalendar(null);
	}

	public void btnEnable() {
		/*
		 * ((PoslateStavkePanel) panel).getBtnConfirm().setEnabled(true);
		 * ((PoslateStavkePanel) panel).getBtnCancel().setEnabled(true); if
		 * (!isZoom) ((PoslateStavkePanel)
		 * panel).getBtnRoba().setEnabled(true);
		 */
	}

	public void addIzvestaj() {

		JButton btnIzvestaj = new JButton("Napravi izveštaj");
		btnIzvestaj.setEnabled(true);
		toolbar.dodajIzvestaj(btnIzvestaj);

		toolbar.getBtnIzvestaj().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					PoslateStavkeTableModel ctm = (PoslateStavkeTableModel) table.getModel();
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

	}

	public void napraviIzvestaj() throws JRException, ClassNotFoundException,
			SQLException {

		String reportSrcFile = "Reports/poslatestavke.jrxml";

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(Calendar.getInstance().getTime());

		// First, compile jrxml file.
		JasperReport jasperReport = JasperCompileManager
				.compileReport(reportSrcFile);

		Connection conn = DBConnection.getConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();

		JasperPrint print = JasperFillManager.fillReport(jasperReport,
				parameters, conn);

		// Make sure the output directory exists.
		// File outDir = new File("C:/jasperoutput");
		// outDir.mkdirs();

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				"GeneratedReports/Poslate stavke " + " - " + timeStamp + ".pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JOptionPane
				.showConfirmDialog(
						getParent(),
						"Izveštaj o poslatim stavkama je uspešno kreiran i nalazi se u folderu GeneratedReports.",
						"Izveštaj", JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);

	}

}
