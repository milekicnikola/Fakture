package gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import databaseConnection.DBConnection;
import gui.MainFrame;
import gui.model.FakturaTableModel;
import gui.panels.FakturaPanel;
import util.ResourceLoader;

public class DialogFaktura extends StandardDialog {

	private String faktura = "";
	private JButton btnAktivno;
	private JButton btnSve;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogFaktura(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Faktura");
		BufferedImage image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("Images/faktura.png"));			
		} catch (Exception e) {			

		}
		setIconImage(image);

		tableModel = new FakturaTableModel(new String[] { "Šifra fakture",
				"Datum fakture", "Paritet", "Ukupna težina", "Transport",
				"Poslata" }, 0);

		panel = new FakturaPanel();

		if (zoom)
			isZoom = true;

		initGUI();
		initStandardActions();
		initActions();

		if (!isZoom) {			
			addDetaljno();
		}

	}

	@Override
	public void initActions() {
		
		btnSve = new JButton("Sve fakture");
		btnAktivno = new JButton("Aktivne fakture");
		
		btnSve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				toolbar.getBtnRefresh().doClick();			
			}
		});
		
		btnAktivno.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				try {
					tableModel.fillData("SELECT sifra_fakture, datum_fakture, paritet_fakture, ukupna_tezina, transport_fakture, poslata_faktura FROM faktura WHERE poslata_faktura = 'ne'");
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		});
		
		toolbar.add(btnSve);
		toolbar.addSeparator();
		toolbar.add(btnAktivno);
		toolbar.addSeparator();

		if (!isZoom) {

			toolbar.getBtnDelete().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (table.getSelectedRow() >= 0) {
						int dialogResult = JOptionPane
								.showConfirmDialog(
										getParent(),
										"Da li ste sigurni da želite da obrišete ovu fakturu?",
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
			}
			return;
		}

		if (index <= (table.getModel().getRowCount() - 1)) {

			if (!isZoom) {
				toolbar.getBtnDetaljno().setEnabled(true);				

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
			}

			statusBar.getStatusState().setText("AŽURIRANJE");
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
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
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
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
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
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
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
		toolbar.addSeparator();

		toolbar.getBtnDetaljno().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					
					faktura = ((FakturaPanel) panel).getTxtSifra().getText().trim();

					DialogFakturisana dialog = new DialogFakturisana(MainFrame
							.getInstance(), false, ((FakturaPanel) panel)
							.getTxtSifra().getText().trim(),
							((FakturaPanel) panel).getTxtPoslata().getText()
									.trim());
					dialog.setVisible(true);
					
					izracunajTezinu(faktura);

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

	public void izracunajTezinu(String faktura) {
		
		String upit = "SELECT roba.tezina_robe*komada_fakturisano as tezina FROM fakturisana_roba JOIN roba ON fakturisana_roba.sifra_robe = roba.sifra_robe WHERE fakturisana_roba.sifra_fakture = '" + faktura +"'";
		
		double ukupna_tezina = 0;
		double tezinaRobe = 0;
		
		
		try {

			Statement stmt = DBConnection.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(upit);

			while (rset.next()) {
				
				String tezina = rset.getString("tezina");								

				tezinaRobe = Double.parseDouble(tezina);
				
				ukupna_tezina += tezinaRobe;						
				
			}

			rset.close();
			stmt.close();
			
			String tezinaR = Double.toString(ukupna_tezina);

			PreparedStatement stmt2 = DBConnection
					.getConnection()
					.prepareStatement(
							"UPDATE faktura SET ukupna_tezina = ? WHERE sifra_fakture = ?");

			stmt2.setString(1, tezinaR);
			stmt2.setString(2, faktura);
			stmt2.executeUpdate();
			stmt2.close();

			DBConnection.getConnection().commit();
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
