package gui.dialogs;

import gui.MainFrame;
import gui.model.FakturaTableModel;
import gui.panels.FakturaPanel;

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
import databaseConnection.DBConnection;

public class DialogFaktura extends StandardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogFaktura(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Faktura");
		setIconImage(new ImageIcon("Images/faktura.png").getImage());

		tableModel = new FakturaTableModel(new String[] { "�ifra fakture",
				"Datum fakture", "Paritet", "Ukupna te�ina", "Transport",
				"Poslata" }, 0);

		panel = new FakturaPanel();

		if (zoom)
			isZoom = true;

		initGUI();
		initStandardActions();
		initActions();

		if (!isZoom) {
			addIzvestaj();
			addDetaljno();
		}

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
										"Da li ste sigurni da �elite da obri�ete ovu fakturu?",
										"Brisanje sloga",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE);
						if (dialogResult == JOptionPane.YES_OPTION) {
							removeRow();
						}
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"Nijedna faktura nije selektovana.",
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
								"Nijedna faktura nije selektovana.",
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
			/*
			 * ((FakturaPanel) panel).getBtnKupac().addActionListener( new
			 * ActionListener() {
			 * 
			 * @Override public void actionPerformed(ActionEvent arg0) { // TODO
			 * Auto-generated method stub DialogKupci dialog = new
			 * DialogKupci(MainFrame .getInstance(), true);
			 * dialog.setVisible(true); try { if (!dialog.getZoom1().equals(""))
			 * ((FakturaPanel) panel).getTxtSifraK()
			 * .setText(dialog.getZoom1()); if (!dialog.getZoom2().equals(""))
			 * ((FakturaPanel) panel).getTxtNazivK()
			 * .setText(dialog.getZoom2()); } catch (NullPointerException n) { }
			 * } });
			 */
		} else {
			toolbar.getBtnAdd().setEnabled(false);
			toolbar.getBtnDelete().setEnabled(false);
			toolbar.getBtnUpdate().setEnabled(false);
			if (!isZoom) {
				toolbar.getBtnDetaljno().setEnabled(false);
				toolbar.getBtnIzvestaj().setEnabled(false);
			}

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
			if (!isZoom) {
				toolbar.getBtnDetaljno().setEnabled(false);
				toolbar.getBtnIzvestaj().setEnabled(false);
			}
			return;
		}

		if (index <= (table.getModel().getRowCount() - 1)) {

			if (!isZoom) {

				toolbar.getBtnDetaljno().setEnabled(true);
				toolbar.getBtnIzvestaj().setEnabled(true);

			}

			String sifra = (String) tableModel.getValueAt(index, 0);
			String datum = (String) tableModel.getValueAt(index, 1);
			String paritet = (String) tableModel.getValueAt(index, 2);
			String tezina = (String) tableModel.getValueAt(index, 3);
			String transport = (String) tableModel.getValueAt(index, 4);
			String poslata = (String) tableModel.getValueAt(index, 5);

			((FakturaPanel) panel).getTxtSifra().setText(sifra);
			((FakturaPanel) panel).getTxtParitet().setText(paritet);
			((FakturaPanel) panel).getTxtTezina().setText(tezina);
			((FakturaPanel) panel).getTxtTransport().setText(transport);
			((FakturaPanel) panel).getTxtPoslata().setText(poslata);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(datum);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			((FakturaPanel) panel).getTxtDatum().setDate(date);
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
			btnEnable();
			allEnable();
			((FakturaPanel) panel).getTxtSifra().setEditable(false);

			if (!isZoom) {
				toolbar.getBtnDetaljno().setEnabled(false);
				toolbar.getBtnIzvestaj().setEnabled(false);
			}

			statusBar.getStatusState().setText("A�URIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();

			if (state == State.PRETRAGA) {
				((FakturaPanel) panel).getTxtPoslata().setEditable(true);
				((FakturaPanel) panel).getTxtTezina().setEditable(true);
			} else {
				((FakturaPanel) panel).getTxtPoslata().setEditable(false);
				((FakturaPanel) panel).getTxtTezina().setEditable(false);
			}

			if (!isZoom) {
				toolbar.getBtnDetaljno().setEnabled(false);
				toolbar.getBtnIzvestaj().setEnabled(false);
			}
			
			((FakturaPanel) panel).getTxtSifra().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		String sifra = ((FakturaPanel) panel).getTxtSifra().getText().trim();
		Date datum1 = ((FakturaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}

		String paritet = ((FakturaPanel) panel).getTxtParitet().getText()
				.trim();
		String transport = ((FakturaPanel) panel).getTxtTransport().getText()
				.trim();

		String[] params = { sifra, datum, paritet, "0", transport, "ne" };

		try {
			FakturaTableModel ctm = (FakturaTableModel) table.getModel();
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

		Date datum1 = ((FakturaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}

		String paritet = ((FakturaPanel) panel).getTxtParitet().getText()
				.trim();
		String transport = ((FakturaPanel) panel).getTxtTransport().getText()
				.trim();

		String[] params = { datum, paritet, transport };
		int index = table.getSelectedRow();
		try {
			FakturaTableModel ctm = (FakturaTableModel) table.getModel();
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
		String sifra = ((FakturaPanel) panel).getTxtSifra().getText().trim();
		Date datum1 = ((FakturaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}
		String paritet = ((FakturaPanel) panel).getTxtParitet().getText()
				.trim();
		String tezina = ((FakturaPanel) panel).getTxtTezina().getText().trim();
		String transport = ((FakturaPanel) panel).getTxtTransport().getText()
				.trim();
		String poslata = ((FakturaPanel) panel).getTxtPoslata().getText()
				.trim();

		String[] params = { sifra, datum, paritet, tezina, transport, poslata };

		try {
			FakturaTableModel ctm = (FakturaTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Gre�ka",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((FakturaPanel) panel).getBtnConfirm().setEnabled(false);
		((FakturaPanel) panel).getBtnCancel().setEnabled(false);
		((FakturaPanel) panel).getTxtSifra().setEditable(false);
		((FakturaPanel) panel).getTxtDatum().setEnabled(false);
		((FakturaPanel) panel).getTxtParitet().setEditable(false);
		((FakturaPanel) panel).getTxtTezina().setEditable(false);
		((FakturaPanel) panel).getTxtTransport().setEditable(false);
		((FakturaPanel) panel).getTxtPoslata().setEditable(false);

	}

	public void allEnable() {
		((FakturaPanel) panel).getBtnConfirm().setEnabled(true);
		((FakturaPanel) panel).getBtnCancel().setEnabled(true);
		((FakturaPanel) panel).getTxtSifra().setEditable(true);
		((FakturaPanel) panel).getTxtDatum().setEnabled(true);
		((FakturaPanel) panel).getTxtParitet().setEditable(true);
		((FakturaPanel) panel).getTxtTransport().setEditable(true);
	}

	public void clearAll() {
		((FakturaPanel) panel).getTxtSifra().setText("");
		((FakturaPanel) panel).getTxtDatum().setCalendar(null);
		((FakturaPanel) panel).getTxtParitet().setText("");
		((FakturaPanel) panel).getTxtTezina().setText("");
		((FakturaPanel) panel).getTxtTransport().setText("");
		((FakturaPanel) panel).getTxtPoslata().setText("");
	}

	public void btnEnable() {
		((FakturaPanel) panel).getBtnConfirm().setEnabled(true);
		((FakturaPanel) panel).getBtnCancel().setEnabled(true);
	}

	public void addDetaljno() {

		JButton btnDetaljno = new JButton("Detalji fakture");
		btnDetaljno.setEnabled(false);
		toolbar.dodajDetaljno(btnDetaljno);

		toolbar.getBtnDetaljno().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {

					DialogFakturisana dialog = new DialogFakturisana(MainFrame
							.getInstance(), false, ((FakturaPanel) panel)
							.getTxtSifra().getText().trim(),
							((FakturaPanel) panel).getTxtPoslata().getText()
									.trim());
					dialog.setVisible(true);

					toolbar.getBtnRefresh().doClick();

				} else {
					JOptionPane.showConfirmDialog(getParent(),
							"Nijedna faktura nije selektovana.", "Upozorenje",
							JOptionPane.PLAIN_MESSAGE,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	public void addIzvestaj() {

		JButton btnIzvestaj = new JButton("Pro�ireni izve�taj");
		btnIzvestaj.setEnabled(false);
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

		String reportSrcFile = "Reports/fakturapros.jrxml";

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(Calendar.getInstance().getTime());

		// First, compile jrxml file.
		JasperReport jasperReport = JasperCompileManager
				.compileReport(reportSrcFile);

		Connection conn = DBConnection.getConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();

		String faktura = ((FakturaPanel) panel).getTxtSifra().getText().trim();

		parameters.put("sifraFakture", faktura);

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
				"GeneratedReports/Prosirena Faktura " + faktura + " - "
						+ timeStamp + ".pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JOptionPane
				.showConfirmDialog(
						getParent(),
						"Pro�ireni izve�taj o fakturi je uspe�no kreiran i nalazi se u folderu GeneratedReports.",
						"Izve�taj", JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);

	}
}
