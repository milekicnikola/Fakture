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
import gui.MainFrame;
import gui.model.NarucenaTableModel;
import gui.panels.NarucenaPanel;
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

public class DialogNarucena extends StandardDialog {

	private String porudzbina = "";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogNarucena(JFrame parent, Boolean zoom, String where) {
		super(parent);
		setTitle("Naručena roba");
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/porudzbina.png"));			
		} catch (Exception e) {			

		}
		setIconImage(image);

		String whereStm = "";

		if (zoom) {
			isZoom = true;
		}

		if (!isZoom) {

			porudzbina = where;
			whereStm = " WHERE narucena_roba.sifra_porudzbine = '" + where
					+ "'";

		} else {
			if (where.equals("faktura"))
				whereStm = " WHERE narucena_roba.komada_ostalo > 0";
			else
				whereStm = "";
		}

		tableModel = new NarucenaTableModel(
				new String[] { "Šifra porudzbine", "Šifra robe", "Naziv robe",
						"Datum isporuke", "Komada naručeno", "Komada poslato",
						"Komada ostalo", "Ko radi" }, 0, whereStm);

		panel = new NarucenaPanel();

		initGUI();
		initStandardActions();
		initActions();

		if (!isZoom)
			addIzvestaj();

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
			 * DialogNarucena(MainFrame .getInstance(), true, ((NarucenaPanel)
			 * panel).getTxtSifra().getText().trim()); dialog.setVisible(true);
			 * } else { JOptionPane.showConfirmDialog(getParent(),
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

			((NarucenaPanel) panel).getBtnRoba().addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							DialogRoba dialog = new DialogRoba(MainFrame
									.getInstance(), true);
							dialog.setVisible(true);
							try {
								if (!dialog.getZoom1().equals(""))
									((NarucenaPanel) panel).getTxtSifraR()
											.setText(dialog.getZoom1());
								if (!dialog.getZoom3().equals(""))
									((NarucenaPanel) panel).getTxtNazivR()
											.setText(dialog.getZoom3());
							} catch (NullPointerException n) {
							}
						}
					});
			((NarucenaPanel) panel).getTxtSifraP().setText(porudzbina);
		} else {
			toolbar.getBtnAdd().setEnabled(false);
			toolbar.getBtnDelete().setEnabled(false);
			toolbar.getBtnUpdate().setEnabled(false);
			//toolbar.getBtnSearch().setEnabled(false);

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

			String sifraP = (String) tableModel.getValueAt(index, 0);
			String sifraR = (String) tableModel.getValueAt(index, 1);
			String nazivR = (String) tableModel.getValueAt(index, 2);
			String datum = (String) tableModel.getValueAt(index, 3);
			String naruceno = (String) tableModel.getValueAt(index, 4);
			String poslato = (String) tableModel.getValueAt(index, 5);
			String ostalo = (String) tableModel.getValueAt(index, 6);
			String ko = (String) tableModel.getValueAt(index, 7);

			((NarucenaPanel) panel).getTxtSifraP().setText(sifraP);
			((NarucenaPanel) panel).getTxtSifraR().setText(sifraR);
			((NarucenaPanel) panel).getTxtNazivR().setText(nazivR);
			((NarucenaPanel) panel).getTxtNaruceno().setText(naruceno);
			((NarucenaPanel) panel).getTxtPoslato().setText(poslato);
			((NarucenaPanel) panel).getTxtOstalo().setText(ostalo);
			((NarucenaPanel) panel).getTxtKo().setText(ko);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(datum);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			((NarucenaPanel) panel).getTxtDatum().setDate(date);
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
			((NarucenaPanel) panel).getBtnRoba().setEnabled(false);
			((NarucenaPanel) panel).getTxtDatum().setEnabled(false);
			((NarucenaPanel) panel).getTxtNaruceno().setEditable(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			
			if ((state == State.PRETRAGA) && isZoom) {
				((NarucenaPanel) panel).getTxtSifraP().setEditable(true);
				((NarucenaPanel) panel).getTxtSifraR().setEditable(true);
				((NarucenaPanel) panel).getTxtNazivR().setEditable(true);
			}
				
				
			((NarucenaPanel) panel).getTxtNaruceno().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		String sifraP = ((NarucenaPanel) panel).getTxtSifraP().getText().trim();
		String sifraR = ((NarucenaPanel) panel).getTxtSifraR().getText().trim();
		String korisnik = MainFrame.getInstance().getKorisnik();
		String nazivR = ((NarucenaPanel) panel).getTxtNazivR().getText().trim();
		String naruceno = ((NarucenaPanel) panel).getTxtNaruceno().getText()
				.trim();
		String poslato = "0";
		String ostalo = naruceno;
		String ko = ((NarucenaPanel) panel).getTxtKo().getText().trim();
		Date datum1 = ((NarucenaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}

		String[] params = { sifraP, sifraR, korisnik, nazivR, datum, naruceno,
				poslato, ostalo, ko };

		try {
			NarucenaTableModel ctm = (NarucenaTableModel) table.getModel();
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

		// String naruceno = ((NarucenaPanel)
		// panel).getTxtNaruceno().getText().trim();
		// String poslato = ((NarucenaPanel)
		// panel).getTxtPoslato().getText().trim();
		// String ostalo = ((NarucenaPanel)
		// panel).getTxtOstalo().getText().trim();
		String ko = ((NarucenaPanel) panel).getTxtKo().getText().trim();
		/*
		 * Date datum1 = ((NarucenaPanel) panel).getTxtDatum().getDate(); String
		 * datum = ""; if (datum1 != null) { datum = new
		 * SimpleDateFormat("yyyy-MM-dd").format(datum1); }
		 */

		String[] params = { ko };
		int index = table.getSelectedRow();
		try {
			NarucenaTableModel ctm = (NarucenaTableModel) table.getModel();
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

		String sifraP = ((NarucenaPanel) panel).getTxtSifraP().getText().trim();
		String sifraR = ((NarucenaPanel) panel).getTxtSifraR().getText().trim();
		String nazivR = ((NarucenaPanel) panel).getTxtNazivR().getText().trim();
		String naruceno = ((NarucenaPanel) panel).getTxtNaruceno().getText()
				.trim();
		String poslato = ((NarucenaPanel) panel).getTxtPoslato().getText()
				.trim();
		String ostalo = ((NarucenaPanel) panel).getTxtOstalo().getText().trim();
		String ko = ((NarucenaPanel) panel).getTxtKo().getText().trim();
		Date datum1 = ((NarucenaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}

		String[] params = { sifraP, sifraR, nazivR, naruceno, poslato, ostalo, datum,
				ko };

		try {
			NarucenaTableModel ctm = (NarucenaTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((NarucenaPanel) panel).getBtnConfirm().setEnabled(false);
		((NarucenaPanel) panel).getBtnCancel().setEnabled(false);
		((NarucenaPanel) panel).getBtnRoba().setEnabled(false);
		((NarucenaPanel) panel).getTxtSifraP().setEditable(false);
		((NarucenaPanel) panel).getTxtSifraR().setEditable(false);
		((NarucenaPanel) panel).getTxtNazivR().setEditable(false);
		((NarucenaPanel) panel).getTxtNaruceno().setEditable(false);
		((NarucenaPanel) panel).getTxtPoslato().setEditable(false);
		((NarucenaPanel) panel).getTxtOstalo().setEditable(false);
		((NarucenaPanel) panel).getTxtDatum().setEnabled(false);
		((NarucenaPanel) panel).getTxtKo().setEditable(false);
	}

	public void allEnable() {
		((NarucenaPanel) panel).getBtnConfirm().setEnabled(true);
		((NarucenaPanel) panel).getBtnCancel().setEnabled(true);
		((NarucenaPanel) panel).getTxtNaruceno().setEditable(true);
		// ((NarucenaPanel) panel).getTxtPoslato().setEditable(true);
		// ((NarucenaPanel) panel).getTxtOstalo().setEditable(true);
		((NarucenaPanel) panel).getTxtDatum().setEnabled(true);
		((NarucenaPanel) panel).getTxtKo().setEditable(true);

	}

	public void clearAll() {
		((NarucenaPanel) panel).getTxtSifraR().setText("");
		((NarucenaPanel) panel).getTxtNazivR().setText("");
		((NarucenaPanel) panel).getTxtNaruceno().setText("");
		((NarucenaPanel) panel).getTxtPoslato().setText("");
		((NarucenaPanel) panel).getTxtOstalo().setText("");
		((NarucenaPanel) panel).getTxtKo().setText("");
		((NarucenaPanel) panel).getTxtDatum().setCalendar(null);
	}

	public void btnEnable() {
		((NarucenaPanel) panel).getBtnConfirm().setEnabled(true);
		((NarucenaPanel) panel).getBtnCancel().setEnabled(true);
		if (!isZoom)
			((NarucenaPanel) panel).getBtnRoba().setEnabled(true);
	}

	public void addIzvestaj() {

		JButton btnIzvestaj = new JButton("Napravi izveštaj");
		btnIzvestaj.setEnabled(true);
		toolbar.dodajIzvestaj(btnIzvestaj);

		toolbar.getBtnIzvestaj().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					NarucenaTableModel ctm = (NarucenaTableModel) table.getModel();
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
			reportSrcFile = ResourceLoader.load("Reports/porudzbina.jrxml");
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

		parameters.put("sifraPorudzbine", porudzbina);

		JasperPrint print = JasperFillManager.fillReport(jasperReport,
				parameters, conn);

		// Make sure the output directory exists.
		ResourceBundle bundle = PropertyResourceBundle
				.getBundle("util/Report");
		String path = bundle.getString("path") + "/Porudzbine";
		File outDir = new File(path);
		outDir.mkdirs();

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				path + "/Porudzbina " + " - " + timeStamp + ".pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JOptionPane
				.showConfirmDialog(
						getParent(),
						"Izveštaj o porudzbini je uspešno kreiran i nalazi se u folderu " + path + ".",
						"Izveštaj", JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);

	}

}
