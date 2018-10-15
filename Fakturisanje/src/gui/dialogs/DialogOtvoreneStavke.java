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
import gui.model.OtvoreneStavkeTableModel;
import gui.panels.OtvoreneStavkePanel;
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

public class DialogOtvoreneStavke extends StandardDialog {	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String SifraR = "";
	private String NazivR = "";
	private String Interni = "";
	private String SifraP = "";
	private String Datum = "";
	private String Korisnik = "";
	private String KoRadi = "";
	private String DatumOd = "";	
	private String DatumDo = "";

	public DialogOtvoreneStavke(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Otvorene stavke");
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/otvoreneStavke.png"));			
		} catch (Exception e) {			

		}
		setIconImage(image);

		tableModel = new OtvoreneStavkeTableModel(
				new String[] { "Šifra robe", "Naziv robe", "Interni naziv robe", "Šifra porudzbine", "Datum isporuke",
						"Korisnik", "Komada naručeno", "Komada poslato", "Komada ostalo", "Ko radi" },
				0);

		if (zoom)
			isZoom = true;

		panel = new OtvoreneStavkePanel();

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
			String datum = (String) tableModel.getValueAt(index, 4);
			String korisnik = (String) tableModel.getValueAt(index, 5);
			String naruceno = (String) tableModel.getValueAt(index, 6);
			String poslato = (String) tableModel.getValueAt(index, 7);
			String ostalo = (String) tableModel.getValueAt(index, 8);
			String ko = (String) tableModel.getValueAt(index, 9);

			((OtvoreneStavkePanel) panel).getTxtSifraP().setText(sifraP);
			((OtvoreneStavkePanel) panel).getTxtSifraR().setText(sifraR);
			((OtvoreneStavkePanel) panel).getTxtNazivR().setText(nazivR);
			((OtvoreneStavkePanel) panel).getTxtInterni().setText(interni);
			((OtvoreneStavkePanel) panel).getTxtNaruceno().setText(naruceno);
			((OtvoreneStavkePanel) panel).getTxtPoslato().setText(poslato);
			((OtvoreneStavkePanel) panel).getTxtOstalo().setText(ostalo);
			((OtvoreneStavkePanel) panel).getTxtKo().setText(ko);
			((OtvoreneStavkePanel) panel).getTxtKorisnik().setText(korisnik);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(datum);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			((OtvoreneStavkePanel) panel).getTxtDatum().setDate(date);

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
			((OtvoreneStavkePanel) panel).getTxtDatum().setEnabled(false);
			((OtvoreneStavkePanel) panel).getTxtNaruceno().setEditable(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} 
		else {
			//clearAll();
			btnEnable();
			allEnable();
			((OtvoreneStavkePanel) panel).getTxtNaruceno().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		/*
		 * String sifraP = ((OtvoreneStavkePanel)
		 * panel).getTxtSifraP().getText().trim(); String sifraR =
		 * ((OtvoreneStavkePanel) panel).getTxtSifraR().getText().trim(); String nazivR
		 * = ((OtvoreneStavkePanel) panel).getTxtNazivR().getText().trim(); String
		 * naruceno = ((OtvoreneStavkePanel) panel).getTxtNaruceno().getText() .trim();
		 * String poslato = "0"; String ostalo = naruceno; String ko =
		 * ((OtvoreneStavkePanel) panel).getTxtKo().getText().trim(); Date datum1 =
		 * ((OtvoreneStavkePanel) panel).getTxtDatum().getDate(); String datum = ""; if
		 * (datum1 != null) { datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		 * }
		 * 
		 * String[] params = { sifraP, sifraR, nazivR, datum, naruceno, poslato, ostalo,
		 * ko };
		 * 
		 * try { NarucenaTableModel ctm = (NarucenaTableModel) table.getModel(); int
		 * index = ctm.insertRow(params); table.setRowSelectionInterval(index, index);
		 * updateStateAndTextFields(State.DODAVANJE); } catch (SQLException ex) {
		 * JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
		 * JOptionPane.ERROR_MESSAGE); }
		 */

	}

	@Override
	public void updateRow() {
		/*
		 * int i = table.getSelectedRow(); if (i == -1) return;
		 * 
		 * // String naruceno = ((OtvoreneStavkePanel) //
		 * panel).getTxtNaruceno().getText().trim(); // String poslato =
		 * ((OtvoreneStavkePanel) // panel).getTxtPoslato().getText().trim(); // String
		 * ostalo = ((OtvoreneStavkePanel) // panel).getTxtOstalo().getText().trim();
		 * String ko = ((OtvoreneStavkePanel) panel).getTxtKo().getText().trim();
		 * 
		 * Date datum1 = ((OtvoreneStavkePanel) panel).getTxtDatum().getDate(); String
		 * datum = ""; if (datum1 != null) { datum = new
		 * SimpleDateFormat("yyyy-MM-dd").format(datum1); }
		 * 
		 * 
		 * String[] params = { ko }; int index = table.getSelectedRow(); try {
		 * NarucenaTableModel ctm = (NarucenaTableModel) table.getModel();
		 * ctm.updateRow(index, params); updateStateAndTextFields(State.AZURIRANJE); }
		 * catch (SQLException ex) { JOptionPane.showMessageDialog(this,
		 * ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE); }
		 * table.setRowSelectionInterval(index, index);
		 */
	}

	@Override
	public void search() {

		String sifraP = ((OtvoreneStavkePanel) panel).getTxtSifraP().getText().trim();
		String sifraR = ((OtvoreneStavkePanel) panel).getTxtSifraR().getText().trim();
		String nazivR = ((OtvoreneStavkePanel) panel).getTxtNazivR().getText().trim();
		String interni = ((OtvoreneStavkePanel) panel).getTxtInterni().getText().trim();
		String ko = ((OtvoreneStavkePanel) panel).getTxtKo().getText().trim();
		String korisnik = ((OtvoreneStavkePanel) panel).getTxtKorisnik().getText().trim();
		Date datum1 = ((OtvoreneStavkePanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}
		
		Date od = ((OtvoreneStavkePanel) panel).getTxtOd().getDate();
		String datumOd = "";
		if (od != null) {
			datumOd = new SimpleDateFormat("yyyy-MM-dd").format(od);
		}
		
		Date datDo = ((OtvoreneStavkePanel) panel).getTxtDo().getDate();
		String datumDo = "";
		if (datDo != null) {
			datumDo = new SimpleDateFormat("yyyy-MM-dd").format(datDo);
		}
		
		String datumPretrage = "";
		
		String izvestajOd = "";
		String izvestajDo = "";
		
		if (datumOd.equals("") && datumDo.equals("")) {
			datumPretrage = "";
		} else if (datumOd.equals("") || datumDo.equals("")) {
			JOptionPane.showMessageDialog(this, "Moraju biti uneta oba datuma za pretragu ili nijedan.", "Greška", JOptionPane.ERROR_MESSAGE);				
		} else if (!datumOd.equals("") && !datumDo.equals("")) {			
			if (!od.before(datDo)) {
				JOptionPane.showMessageDialog(this, "Datum početka pretrage mora biti pre datuma kraja pretrage.", "Greška", JOptionPane.ERROR_MESSAGE);
			} else {				
				datumPretrage = " AND datum_isporuke BETWEEN '" + datumOd + "' AND '" + datumDo + "'";
				izvestajOd = datumOd;
				izvestajDo = datumDo;
			}
		}		

		String[] params = { sifraR, nazivR, interni, sifraP, datum, ko, korisnik, datumPretrage };

		try {
			OtvoreneStavkeTableModel ctm = (OtvoreneStavkeTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
			SifraR = sifraR;
			NazivR = nazivR;
			Interni = interni;
			SifraP = sifraP;			
			Datum = datum;
			KoRadi = ko;
			Korisnik = korisnik;
			DatumOd = izvestajOd;
			DatumDo = izvestajDo;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((OtvoreneStavkePanel) panel).getBtnConfirm().setEnabled(false);
		((OtvoreneStavkePanel) panel).getBtnCancel().setEnabled(false);
		((OtvoreneStavkePanel) panel).getTxtSifraP().setEditable(false);
		((OtvoreneStavkePanel) panel).getTxtSifraR().setEditable(false);
		((OtvoreneStavkePanel) panel).getTxtNazivR().setEditable(false);
		((OtvoreneStavkePanel) panel).getTxtNaruceno().setEditable(false);
		((OtvoreneStavkePanel) panel).getTxtPoslato().setEditable(false);
		((OtvoreneStavkePanel) panel).getTxtOstalo().setEditable(false);
		((OtvoreneStavkePanel) panel).getTxtDatum().setEnabled(false);
		((OtvoreneStavkePanel) panel).getTxtKo().setEditable(false);
		((OtvoreneStavkePanel) panel).getTxtInterni().setEditable(false);
		((OtvoreneStavkePanel) panel).getTxtKorisnik().setEditable(false);
		((OtvoreneStavkePanel) panel).getTxtOd().setEnabled(false);
		((OtvoreneStavkePanel) panel).getTxtDo().setEnabled(false);
	}

	public void allEnable() {

		((OtvoreneStavkePanel) panel).getBtnConfirm().setEnabled(true);
		((OtvoreneStavkePanel) panel).getBtnCancel().setEnabled(true);
		((OtvoreneStavkePanel) panel).getTxtDatum().setEnabled(true);
		((OtvoreneStavkePanel) panel).getTxtKo().setEditable(true);
		((OtvoreneStavkePanel) panel).getTxtSifraR().setEditable(true);
		((OtvoreneStavkePanel) panel).getTxtNazivR().setEditable(true);
		((OtvoreneStavkePanel) panel).getTxtInterni().setEditable(true);
		((OtvoreneStavkePanel) panel).getTxtSifraP().setEditable(true);
		((OtvoreneStavkePanel) panel).getTxtKorisnik().setEditable(true);
		((OtvoreneStavkePanel) panel).getTxtOd().setEnabled(true);
		((OtvoreneStavkePanel) panel).getTxtDo().setEnabled(true);

	}

	public void clearAll() {
		((OtvoreneStavkePanel) panel).getTxtSifraR().setText("");
		((OtvoreneStavkePanel) panel).getTxtSifraP().setText("");
		((OtvoreneStavkePanel) panel).getTxtNazivR().setText("");
		((OtvoreneStavkePanel) panel).getTxtNaruceno().setText("");
		((OtvoreneStavkePanel) panel).getTxtPoslato().setText("");
		((OtvoreneStavkePanel) panel).getTxtOstalo().setText("");
		((OtvoreneStavkePanel) panel).getTxtKo().setText("");
		((OtvoreneStavkePanel) panel).getTxtKorisnik().setText("");
		((OtvoreneStavkePanel) panel).getTxtInterni().setText("");
		((OtvoreneStavkePanel) panel).getTxtDatum().setCalendar(null);
		((OtvoreneStavkePanel) panel).getTxtOd().setCalendar(null);
		((OtvoreneStavkePanel) panel).getTxtDo().setCalendar(null);
		SifraR = "";
		NazivR = "";
		Interni = "";
		SifraP = "";			
		Datum = "";
		KoRadi = "";
		Korisnik = "";
		DatumOd = "";
		DatumDo = "";
	}

	public void btnEnable() {		
	}

	public void addIzvestaj() {

		JButton btnIzvestaj = new JButton("Napravi izveštaj");
		btnIzvestaj.setEnabled(true);
		toolbar.dodajIzvestaj(btnIzvestaj);

		toolbar.getBtnIzvestaj().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					OtvoreneStavkeTableModel ctm = (OtvoreneStavkeTableModel) table.getModel();
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

	public void napraviIzvestaj() throws JRException, ClassNotFoundException, SQLException {

		InputStream reportSrcFile = null;
		try {
			reportSrcFile = ResourceLoader.load("Reports/otvoreneStavke.jrxml");
		} catch (Exception e) {			
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		// First, compile jrxml file.
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

		Connection conn = DBConnection.getConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		String sifraR = "%" + SifraR + "%";
		String nazivR = "%" + NazivR + "%";
		String interni = "%" + Interni + "%";
		String sifraP = "%" + SifraP + "%";
		String datum = "%" + Datum + "%";
		String korisnik = "%" + Korisnik + "%";
		String koRadi = "%" + KoRadi + "%";
		String datumOd = "";
		String datumDo = "";
		
		if (DatumOd.equals("")) {
			datumOd = "2000-01-01";			
		} else {
			datumOd = DatumOd;
		}
		
		if (DatumDo.equals("")) {
			datumDo = "3000-01-01";			
		} else {
			datumDo = DatumDo;
		}

		parameters.put("sifraR", sifraR);
		parameters.put("nazivR", nazivR);
		parameters.put("interni", interni);
		parameters.put("sifraP", sifraP);
		parameters.put("datum", datum);
		parameters.put("korisnik", korisnik);
		parameters.put("koRadi", koRadi);
		parameters.put("datumOd", datumOd);
		parameters.put("datumDo", datumDo);

		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

		// Make sure the output directory exists.
		ResourceBundle bundle = PropertyResourceBundle
				.getBundle("util/Report");
		String path = bundle.getString("path") + "/Otvorene stavke";
		File outDir = new File(path);
		outDir.mkdirs();

		// PDF Exportor.
		JRPdfExporter exporterP = new JRPdfExporter();
		JRXlsxExporter exporterE = new JRXlsxExporter();
		JRDocxExporter exporterD = new JRDocxExporter();		

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporterP.setExporterInput(exporterInput);
		exporterD.setExporterInput(exporterInput);
		exporterE.setExporterInput(exporterInput);

		String naziv = "";

		if (Korisnik.toLowerCase().equals("olgica") || Korisnik.toLowerCase().contains("g") || Korisnik.toLowerCase().contains("ol") || Korisnik.toLowerCase().contains("olg")) {
			naziv = "Olgica";			
		}			
		else if (Korisnik.toLowerCase().equals("milos") || Korisnik.toLowerCase().contains("m")) {
			naziv = "Milos";
		}
		else {
			naziv = "Sve";
		}

		// ExporterOutput
		OutputStreamExporterOutput exporterOutputP = new SimpleOutputStreamExporterOutput(
				path + "/Otvorene stavke " + naziv + " - " + timeStamp + ".pdf");
		OutputStreamExporterOutput exporterOutputD = new SimpleOutputStreamExporterOutput(
				path + "/Otvorene stavke " + naziv + " - " + timeStamp + ".docx");
		OutputStreamExporterOutput exporterOutputE = new SimpleOutputStreamExporterOutput(
				path + "/Otvorene stavke " + naziv + " - " + timeStamp + ".xlsx");
		// Output
		exporterP.setExporterOutput(exporterOutputP);
		exporterE.setExporterOutput(exporterOutputE);
		exporterD.setExporterOutput(exporterOutputD);

		//
		SimplePdfExporterConfiguration configurationP = new SimplePdfExporterConfiguration();
		SimpleXlsxExporterConfiguration configurationE = new SimpleXlsxExporterConfiguration();
		SimpleDocxExporterConfiguration configurationD = new SimpleDocxExporterConfiguration();
		exporterP.setConfiguration(configurationP);
		exporterD.setConfiguration(configurationD);
		exporterE.setConfiguration(configurationE);
		exporterP.exportReport();
		exporterD.exportReport();
		exporterE.exportReport();

		JOptionPane.showConfirmDialog(getParent(),
				"Izveštaj o otvorenim stavkama je uspešno kreiran i nalazi se u folderu " + path + ".", "Izveštaj",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);

	}

}
