package gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import util.ResourceLoader;

public class DialogPoslateStavke extends StandardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String SifraR = "";
	private String NazivR = "";
	private String Interni = "";
	private String SifraP = "";
	private String SifraF = "";
	private String SifraO = "";
	private String Datum = "";
	private String Korisnik = "";
	private String KoRadi = "";	

	public DialogPoslateStavke(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Poslate stavke");
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/poslateStavke.png"));			
		} catch (Exception e) {			

		}
		setIconImage(image);

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

	}

	@Override
	public void initActions() {
		
		toolbar.getBtnRefresh().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tableModel.open();
					clearAll();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (isZoom)
					allDisable();
			}
		});
		
		toolbar.getBtnSearch().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateStateAndTextFields(State.PRETRAGA);
				clearAll();
			}
		});		
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
		String nazivR = ((PoslateStavkePanel) panel).getTxtNazivR().getText()
				.trim();
		String interni = ((PoslateStavkePanel) panel).getTxtInterni().getText()
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

		String[] params = { sifraR, nazivR, interni, sifraP, sifraF, sifraO, datum,
				korisnik, ko };

		try {
			PoslateStavkeTableModel ctm = (PoslateStavkeTableModel) table
					.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
			
			SifraR = sifraR;
			NazivR = nazivR;
			Interni = interni;
			SifraP = sifraP;
			SifraF = sifraF;
			SifraO = sifraO;
			Datum = datum;
			Korisnik = korisnik;
			KoRadi = ko;
			
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
		((PoslateStavkePanel) panel).getTxtNazivR().setEditable(true);
		((PoslateStavkePanel) panel).getTxtInterni().setEditable(true);

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

		InputStream reportSrcFile = null;
		try {
			reportSrcFile = ResourceLoader.load("Reports/poslateStavke.jrxml");
		} catch (Exception e) {			
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(Calendar.getInstance().getTime());

		// First, compile jrxml file.
		JasperReport jasperReport = JasperCompileManager
				.compileReport(reportSrcFile);

		Connection conn = DBConnection.getConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		String sifraR = "%" + SifraR + "%";
		String nazivR = "%" + NazivR + "%";
		String interni = "%" + Interni + "%";
		String sifraP = "%" + SifraP + "%";
		String sifraF = "%" + SifraF + "%";
		String sifraO = "%" + SifraO + "%";
		String datum = "%" + Datum + "%";
		String korisnik = "%" + Korisnik + "%";
		String koRadi = "%" + KoRadi + "%";

		parameters.put("sifraR", sifraR);
		parameters.put("nazivR", nazivR);
		parameters.put("interni", interni);
		parameters.put("sifraP", sifraP);
		parameters.put("sifraF", sifraF);
		parameters.put("sifraO", sifraO);
		parameters.put("datum", datum);
		parameters.put("korisnik", korisnik);
		parameters.put("koRadi", koRadi);

		JasperPrint print = JasperFillManager.fillReport(jasperReport,
				parameters, conn);

		// Make sure the output directory exists.
		ResourceBundle bundle = PropertyResourceBundle
				.getBundle("util/Report");
		String path = bundle.getString("path") + "/Poslate stavke";
		File outDir = new File(path);
		outDir.mkdirs();

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporter.setExporterInput(exporterInput);
		
		String naziv = "";

		if (Korisnik.toLowerCase().equals("olgica") || Korisnik.toLowerCase().contains("g") || Korisnik.toLowerCase().contains("ol") || Korisnik.toLowerCase().contains("olg")) {
			naziv = "Olgica";			
		}			
		else if (Korisnik.toLowerCase().equals("milos") || Korisnik.toLowerCase().contains("m")) {
			naziv = "Milos";
		}
		else if (Korisnik.toLowerCase().equals("nedeljko") || Korisnik.toLowerCase().contains("ne")) {
			naziv = "Nedeljko";
		}
		else {
			naziv = "Sve";
		}

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				path + "/Poslate stavke " + naziv + " - " + timeStamp + ".pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JOptionPane
				.showConfirmDialog(
						getParent(),
						"Izveštaj o poslatim stavkama je uspešno kreiran i nalazi se u folderu " + path + ".",
						"Izveštaj", JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);

	}

}
