package gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
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
import gui.model.MagacinTableModel;
import gui.panels.MagacinPanel;
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

public class DialogMagacin extends StandardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogMagacin(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Magacin");
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/magacin.png"));			
		} catch (Exception e) {			

		}
		setIconImage(image);

		tableModel = new MagacinTableModel(new String[] { "�ifra", "Naziv",
				"Adresa", "�ef", "Telefon" }, 0);

		panel = new MagacinPanel();

		if (zoom)
			isZoom = true;

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
										"Da li ste sigurni da �elite da obri�ete ovaj magacin?",
										"Brisanje sloga",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE);
						if (dialogResult == JOptionPane.YES_OPTION) {
							removeRow();
						}
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"Nijedan magacin nije selektovan.",
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
								"Nijedan magacin nije selektovan.",
								"Upozorenje", JOptionPane.PLAIN_MESSAGE,
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
			return;
		}

		if (index <= (table.getModel().getRowCount() - 1)) {

			String sifra = (String) tableModel.getValueAt(index, 0);
			String naziv = (String) tableModel.getValueAt(index, 1);
			String adresa = (String) tableModel.getValueAt(index, 2);
			String sef = (String) tableModel.getValueAt(index, 3);
			String telefon = (String) tableModel.getValueAt(index, 4);

			((MagacinPanel) panel).getTxtSifra().setText(sifra);
			((MagacinPanel) panel).getTxtNaziv().setText(naziv);
			((MagacinPanel) panel).getTxtAdresa().setText(adresa);
			((MagacinPanel) panel).getTxtSef().setText(sef);
			((MagacinPanel) panel).getTxtTelefon().setText(telefon);
		
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
			((MagacinPanel) panel).getTxtSifra().setEditable(false);
			statusBar.getStatusState().setText("A�URIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			((MagacinPanel) panel).getTxtSifra().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		String sifra = ((MagacinPanel) panel).getTxtSifra().getText().trim();
		String naziv = ((MagacinPanel) panel).getTxtNaziv().getText().trim();
		String adresa = ((MagacinPanel) panel).getTxtAdresa().getText().trim();
		String sef = ((MagacinPanel) panel).getTxtSef().getText().trim();
		String telefon = ((MagacinPanel) panel).getTxtTelefon().getText()
				.trim();

		String[] params = { sifra, naziv, adresa, sef, telefon };

		try {
			MagacinTableModel ctm = (MagacinTableModel) table.getModel();
			int index = ctm.insertRow(params);
			table.setRowSelectionInterval(index, index);
			updateStateAndTextFields(State.DODAVANJE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Gre�ka",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void updateRow() {
		int i = table.getSelectedRow();
		if (i == -1)
			return;

		String naziv = ((MagacinPanel) panel).getTxtNaziv().getText().trim();
		String adresa = ((MagacinPanel) panel).getTxtAdresa().getText().trim();
		String sef = ((MagacinPanel) panel).getTxtSef().getText().trim();
		String telefon = ((MagacinPanel) panel).getTxtTelefon().getText()
				.trim();

		String[] params = { naziv, adresa, sef, telefon };
		int index = table.getSelectedRow();
		try {
			MagacinTableModel ctm = (MagacinTableModel) table.getModel();
			ctm.updateRow(index, params);
			updateStateAndTextFields(State.AZURIRANJE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Gre�ka",
					JOptionPane.ERROR_MESSAGE);
		}
		table.setRowSelectionInterval(index, index);
	}

	@Override
	public void search() {
		String sifra = ((MagacinPanel) panel).getTxtSifra().getText().trim();
		String naziv = ((MagacinPanel) panel).getTxtNaziv().getText().trim();
		String adresa = ((MagacinPanel) panel).getTxtAdresa().getText().trim();
		String sef = ((MagacinPanel) panel).getTxtSef().getText().trim();
		String telefon = ((MagacinPanel) panel).getTxtTelefon().getText()
				.trim();

		String[] params = { sifra, naziv, adresa, sef, telefon };

		try {
			MagacinTableModel ctm = (MagacinTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Gre�ka",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((MagacinPanel) panel).getBtnConfirm().setEnabled(false);
		((MagacinPanel) panel).getBtnCancel().setEnabled(false);
		((MagacinPanel) panel).getTxtSifra().setEditable(false);
		((MagacinPanel) panel).getTxtNaziv().setEditable(false);
		((MagacinPanel) panel).getTxtAdresa().setEditable(false);
		((MagacinPanel) panel).getTxtSef().setEditable(false);
		((MagacinPanel) panel).getTxtTelefon().setEditable(false);

	}

	public void allEnable() {
		((MagacinPanel) panel).getBtnConfirm().setEnabled(true);
		((MagacinPanel) panel).getBtnCancel().setEnabled(true);
		((MagacinPanel) panel).getTxtSifra().setEditable(true);
		((MagacinPanel) panel).getTxtNaziv().setEditable(true);
		((MagacinPanel) panel).getTxtAdresa().setEditable(true);
		((MagacinPanel) panel).getTxtSef().setEditable(true);
		((MagacinPanel) panel).getTxtTelefon().setEditable(true);
	}

	public void clearAll() {
		((MagacinPanel) panel).getTxtSifra().setText("");
		((MagacinPanel) panel).getTxtNaziv().setText("");
		((MagacinPanel) panel).getTxtAdresa().setText("");
		((MagacinPanel) panel).getTxtSef().setText("");
		((MagacinPanel) panel).getTxtTelefon().setText("");
	}

	public void btnEnable() {
		((MagacinPanel) panel).getBtnConfirm().setEnabled(true);
		((MagacinPanel) panel).getBtnCancel().setEnabled(true);
	}

	public void addIzvestaj() {

		JButton btnIzvestaj = new JButton("Napravi izve�taj");
		btnIzvestaj.setEnabled(true);
		toolbar.dodajIzvestaj(btnIzvestaj);

		toolbar.getBtnIzvestaj().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					MagacinTableModel ctm = (MagacinTableModel) table.getModel();
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
			reportSrcFile = ResourceLoader.load("Reports/magacin.jrxml");
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

		JasperPrint print = JasperFillManager.fillReport(jasperReport,
				parameters, conn);

		// Make sure the output directory exists.
		ResourceBundle bundle = PropertyResourceBundle
				.getBundle("util/Report");
		String path = bundle.getString("path");
		File outDir = new File(path);
		outDir.mkdirs();

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				path + "/Magacin" + " - " + timeStamp + ".pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JOptionPane
				.showConfirmDialog(
						getParent(),
						"Izve�taj o magacinima je uspe�no kreiran i nalazi se u folderu " + path + ".",
						"Izve�taj", JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);

	}
}
