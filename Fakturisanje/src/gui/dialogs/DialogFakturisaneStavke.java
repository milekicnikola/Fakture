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
import gui.model.FakturisaneStavkeTableModel;
import gui.panels.FakturisaneStavkePanel;
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

public class DialogFakturisaneStavke extends StandardDialog {
	
	private JButton btnSve;
	private JButton btnOlgica;
	private JButton btnMilos;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogFakturisaneStavke(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Fakturisane stavke");
		setIconImage(new ImageIcon("Images/fakturisaneStavke.png").getImage());

		tableModel = new FakturisaneStavkeTableModel(new String[] { "Šifra robe",
				"Naziv robe", "Interni naziv robe", "Porudzbina", "Faktura",
				"Datum isporuke", "Komada naručeno", "Komada fakturisano", "Korisnik" }, 0);

		if (zoom)
			isZoom = true;

		panel = new FakturisaneStavkePanel();

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
					tableModel.fillData("SELECT fakturisana_roba.sifra_robe as sifraRobe, naziv_robe, interni_naziv, fakturisana_roba.sifra_porudzbine as sifraPorudzbine, fakturisana_roba.sifra_fakture as sifraFakture, fakturisana_roba.datum_isporuke as datumIsporuke, komada_naruceno, komada_fakturisano, narucena_roba.korisnicko_ime as korisnickoIme FROM fakturisana_roba JOIN faktura ON fakturisana_roba.sifra_fakture = faktura.sifra_fakture JOIN narucena_roba ON fakturisana_roba.sifra_robe = narucena_roba.sifra_robe AND fakturisana_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND fakturisana_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON fakturisana_roba.sifra_robe = roba.sifra_robe JOIN korisnik ON narucena_roba.korisnicko_ime = korisnik.korisnicko_ime WHERE fakturisana_roba.status = 'fakturisana'");
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		});
		
		btnOlgica.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				try {
					tableModel.fillData("SELECT fakturisana_roba.sifra_robe as sifraRobe, naziv_robe, interni_naziv, fakturisana_roba.sifra_porudzbine as sifraPorudzbine, fakturisana_roba.sifra_fakture as sifraFakture, fakturisana_roba.datum_isporuke as datumIsporuke, komada_naruceno, komada_fakturisano, narucena_roba.korisnicko_ime as korisnickoIme FROM fakturisana_roba JOIN faktura ON fakturisana_roba.sifra_fakture = faktura.sifra_fakture JOIN narucena_roba ON fakturisana_roba.sifra_robe = narucena_roba.sifra_robe AND fakturisana_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND fakturisana_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON fakturisana_roba.sifra_robe = roba.sifra_robe JOIN korisnik ON narucena_roba.korisnicko_ime = korisnik.korisnicko_ime WHERE fakturisana_roba.status = 'fakturisana' AND narucena_roba.korisnicko_ime = 'olgica'");
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		});
		
		btnMilos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				try {
					tableModel.fillData("SELECT fakturisana_roba.sifra_robe as sifraRobe, naziv_robe, interni_naziv, fakturisana_roba.sifra_porudzbine as sifraPorudzbine, fakturisana_roba.sifra_fakture as sifraFakture, fakturisana_roba.datum_isporuke as datumIsporuke, komada_naruceno, komada_fakturisano, narucena_roba.korisnicko_ime as korisnickoIme FROM fakturisana_roba JOIN faktura ON fakturisana_roba.sifra_fakture = faktura.sifra_fakture JOIN narucena_roba ON fakturisana_roba.sifra_robe = narucena_roba.sifra_robe AND fakturisana_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND fakturisana_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON fakturisana_roba.sifra_robe = roba.sifra_robe JOIN korisnik ON narucena_roba.korisnicko_ime = korisnik.korisnicko_ime WHERE fakturisana_roba.status = 'fakturisana' AND narucena_roba.korisnicko_ime = 'milos'");
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
			String datum = (String) tableModel.getValueAt(index, 5);			
			String naruceno = (String) tableModel.getValueAt(index, 6);
			String fakturisano = (String) tableModel.getValueAt(index, 7);
			String korisnik = (String) tableModel.getValueAt(index, 8);

			((FakturisaneStavkePanel) panel).getTxtSifraP().setText(sifraP);
			((FakturisaneStavkePanel) panel).getTxtSifraR().setText(sifraR);
			((FakturisaneStavkePanel) panel).getTxtNazivR().setText(nazivR);
			((FakturisaneStavkePanel) panel).getTxtInterni().setText(interni);
			((FakturisaneStavkePanel) panel).getTxtNaruceno().setText(naruceno);
			((FakturisaneStavkePanel) panel).getTxtFakturisano().setText(fakturisano);			
			((FakturisaneStavkePanel) panel).getTxtKorisnik().setText(korisnik);
			((FakturisaneStavkePanel) panel).getTxtFaktura().setText(faktura);


			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(datum);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			((FakturisaneStavkePanel) panel).getTxtDatum().setDate(date);

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
			((FakturisaneStavkePanel) panel).getTxtDatum().setEnabled(false);
			((FakturisaneStavkePanel) panel).getTxtNaruceno().setEditable(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			((FakturisaneStavkePanel) panel).getTxtNaruceno().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		/*
		 * String sifraP = ((FakturisaneStavkePanel)
		 * panel).getTxtSifraP().getText().trim(); String sifraR =
		 * ((FakturisaneStavkePanel) panel).getTxtSifraR().getText().trim(); String
		 * nazivR = ((FakturisaneStavkePanel)
		 * panel).getTxtNazivR().getText().trim(); String naruceno =
		 * ((FakturisaneStavkePanel) panel).getTxtNaruceno().getText() .trim();
		 * String poslato = "0"; String ostalo = naruceno; String ko =
		 * ((FakturisaneStavkePanel) panel).getTxtKo().getText().trim(); Date
		 * datum1 = ((FakturisaneStavkePanel) panel).getTxtDatum().getDate();
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
		 * // String naruceno = ((FakturisaneStavkePanel) //
		 * panel).getTxtNaruceno().getText().trim(); // String poslato =
		 * ((FakturisaneStavkePanel) // panel).getTxtPoslato().getText().trim(); //
		 * String ostalo = ((FakturisaneStavkePanel) //
		 * panel).getTxtOstalo().getText().trim(); String ko =
		 * ((FakturisaneStavkePanel) panel).getTxtKo().getText().trim();
		 * 
		 * Date datum1 = ((FakturisaneStavkePanel) panel).getTxtDatum().getDate();
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

		String sifraP = ((FakturisaneStavkePanel) panel).getTxtSifraP().getText()
				.trim();
		String sifraR = ((FakturisaneStavkePanel) panel).getTxtSifraR().getText()
				.trim();		
		String sifraF = ((FakturisaneStavkePanel) panel).getTxtFaktura().getText()
				.trim();		
		String korisnik = ((FakturisaneStavkePanel) panel).getTxtKorisnik()
				.getText().trim();
		Date datum1 = ((FakturisaneStavkePanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}

		String[] params = { sifraR, sifraP, sifraF, datum, korisnik };

		try {
			FakturisaneStavkeTableModel ctm = (FakturisaneStavkeTableModel) table
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
		((FakturisaneStavkePanel) panel).getBtnConfirm().setEnabled(false);
		((FakturisaneStavkePanel) panel).getBtnCancel().setEnabled(false);
		((FakturisaneStavkePanel) panel).getTxtSifraP().setEditable(false);
		((FakturisaneStavkePanel) panel).getTxtSifraR().setEditable(false);
		((FakturisaneStavkePanel) panel).getTxtNazivR().setEditable(false);
		((FakturisaneStavkePanel) panel).getTxtNaruceno().setEditable(false);
		((FakturisaneStavkePanel) panel).getTxtFakturisano().setEditable(false);		
		((FakturisaneStavkePanel) panel).getTxtDatum().setEnabled(false);		
		((FakturisaneStavkePanel) panel).getTxtInterni().setEditable(false);
		((FakturisaneStavkePanel) panel).getTxtKorisnik().setEditable(false);
		((FakturisaneStavkePanel) panel).getTxtFaktura().setEditable(false);
	}

	public void allEnable() {

		((FakturisaneStavkePanel) panel).getBtnConfirm().setEnabled(true);
		((FakturisaneStavkePanel) panel).getBtnCancel().setEnabled(true);		
		((FakturisaneStavkePanel) panel).getTxtDatum().setEnabled(true);
		((FakturisaneStavkePanel) panel).getTxtSifraR().setEditable(true);
		((FakturisaneStavkePanel) panel).getTxtSifraP().setEditable(true);
		((FakturisaneStavkePanel) panel).getTxtKorisnik().setEditable(true);
		((FakturisaneStavkePanel) panel).getTxtFaktura().setEditable(true);		

	}

	public void clearAll() {
		((FakturisaneStavkePanel) panel).getTxtSifraR().setText("");
		((FakturisaneStavkePanel) panel).getTxtSifraP().setText("");
		((FakturisaneStavkePanel) panel).getTxtNazivR().setText("");
		((FakturisaneStavkePanel) panel).getTxtInterni().setText("");
		((FakturisaneStavkePanel) panel).getTxtNaruceno().setText("");
		((FakturisaneStavkePanel) panel).getTxtFakturisano().setText("");
		((FakturisaneStavkePanel) panel).getTxtKorisnik().setText("");
		((FakturisaneStavkePanel) panel).getTxtFaktura().setText("");		
		((FakturisaneStavkePanel) panel).getTxtDatum().setCalendar(null);
	}

	public void btnEnable() {
		/*
		 * ((FakturisaneStavkePanel) panel).getBtnConfirm().setEnabled(true);
		 * ((FakturisaneStavkePanel) panel).getBtnCancel().setEnabled(true); if
		 * (!isZoom) ((FakturisaneStavkePanel)
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
					napraviIzvestaj();
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
