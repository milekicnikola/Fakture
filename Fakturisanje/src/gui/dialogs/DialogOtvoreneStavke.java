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

	private JButton btnSve;
	private JButton btnOlgica;
	private JButton btnMilos;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

		btnSve = new JButton("Sve stavke");
		btnOlgica = new JButton("Olgicine stavke");
		btnMilos = new JButton("Miloseve stavke");

		btnSve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				toolbar.getBtnRefresh().doClick();
			}
		});

		btnOlgica.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					tableModel.fillData(
							"SELECT narucena_roba.sifra_robe as sifraRobe, naziv_robe, interni_naziv, narucena_roba.sifra_porudzbine as sifraPorudzbine, datum_isporuke, komada_naruceno, komada_poslato, komada_ostalo, ko_radi, narucena_roba.korisnicko_ime as korisnickoIme FROM narucena_roba JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON narucena_roba.sifra_robe = roba.sifra_robe JOIN korisnik ON narucena_roba.korisnicko_ime = korisnik.korisnicko_ime WHERE komada_ostalo > 0 AND narucena_roba.korisnicko_ime = 'olgica'");
					((OtvoreneStavkeTableModel) tableModel).izvestaj = "olgica";
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		btnMilos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					tableModel.fillData(
							"SELECT narucena_roba.sifra_robe as sifraRobe, naziv_robe, interni_naziv, narucena_roba.sifra_porudzbine as sifraPorudzbine, datum_isporuke, komada_naruceno, komada_poslato, komada_ostalo, ko_radi, narucena_roba.korisnicko_ime as korisnickoIme FROM narucena_roba JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON narucena_roba.sifra_robe = roba.sifra_robe JOIN korisnik ON narucena_roba.korisnicko_ime = korisnik.korisnicko_ime WHERE komada_ostalo > 0 AND narucena_roba.korisnicko_ime = 'milos'");
					((OtvoreneStavkeTableModel) tableModel).izvestaj = "milos";
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
		} else {
			clearAll();
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

		String[] params = { sifraR, nazivR, interni, sifraP, datum, ko, korisnik };

		try {
			OtvoreneStavkeTableModel ctm = (OtvoreneStavkeTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
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

		String izvestaj = ((OtvoreneStavkeTableModel) tableModel).izvestaj;
		
		String ko = "%";
		if (((OtvoreneStavkePanel) panel).getTxtKo().isEditable()) 
			ko += ((OtvoreneStavkePanel) panel).getTxtKo().getText().trim();		
		ko += "%";		

		parameters.put("korisnik", izvestaj);
		parameters.put("koRadi", ko);

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

		if (izvestaj.equals("olgica"))
			naziv = "Olgica";
		if (izvestaj.equals("milos"))
			naziv = "Milos";
		if (izvestaj.equals("%"))
			naziv = "Sve";

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
