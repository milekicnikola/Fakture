package gui.dialogs;

import gui.MainFrame;
import gui.model.FakturaTableModel;
import gui.panels.FakturaPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogFaktura extends StandardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogFaktura(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Faktura");
		setIconImage(new ImageIcon("Images/faktura.png").getImage());

		tableModel = new FakturaTableModel(new String[] { "Šifra fakture",
				"Datum fakture", "Korisnik",
				"Paritet", "Bruto", "Neto", "Ukupno komada robe" }, 0);

		panel = new FakturaPanel();

		if (zoom)
			isZoom = true;

		initGUI();
		initStandardActions();
		initActions();

		addDetaljno();

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
			/*((FakturaPanel) panel).getBtnKupac().addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							DialogKupci dialog = new DialogKupci(MainFrame
									.getInstance(), true);
							dialog.setVisible(true);
							try {
								if (!dialog.getZoom1().equals(""))
									((FakturaPanel) panel).getTxtSifraK()
											.setText(dialog.getZoom1());
								if (!dialog.getZoom2().equals(""))
									((FakturaPanel) panel).getTxtNazivK()
											.setText(dialog.getZoom2());
							} catch (NullPointerException n) {
							}
						}
					});*/
		} else {
			toolbar.getBtnAdd().setEnabled(false);
			toolbar.getBtnDelete().setEnabled(false);
			toolbar.getBtnUpdate().setEnabled(false);
			toolbar.getBtnDetaljno().setEnabled(false);

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
			toolbar.getBtnDetaljno().setEnabled(false);
			return;
		}
		toolbar.getBtnDetaljno().setEnabled(true);

		String sifra = (String) tableModel.getValueAt(index, 0);
		String datum = (String) tableModel.getValueAt(index, 1);
		/*String sifraK = (String) tableModel.getValueAt(index, 2);
		String nazivK = (String) tableModel.getValueAt(index, 3);*/
		String ime = (String) tableModel.getValueAt(index, 2);
		String paritet = (String) tableModel.getValueAt(index, 3);
		String bruto = (String) tableModel.getValueAt(index, 4);
		String neto = (String) tableModel.getValueAt(index, 5);
		String ukupno = (String) tableModel.getValueAt(index, 6);		

		((FakturaPanel) panel).getTxtSifra().setText(sifra);
		/*((FakturaPanel) panel).getTxtSifraK().setText(sifraK);
		((FakturaPanel) panel).getTxtNazivK().setText(nazivK);*/
		((FakturaPanel) panel).getTxtKorisnik().setText(ime);
		((FakturaPanel) panel).getTxtParitet().setText(paritet);
		((FakturaPanel) panel).getTxtBruto().setText(bruto);
		((FakturaPanel) panel).getTxtNeto().setText(neto);
		((FakturaPanel) panel).getTxtUkupno().setText(ukupno);
		
		

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
			toolbar.getBtnDetaljno().setEnabled(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			toolbar.getBtnDetaljno().setEnabled(false);
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
		/*String sifraK = ((FakturaPanel) panel).getTxtSifraK().getText()
				.trim();
		String nazivK = ((FakturaPanel) panel).getTxtNazivK().getText()
				.trim();*/
		String ime = MainFrame.getInstance().getKorisnik();
		String paritet = ((FakturaPanel) panel).getTxtParitet().getText()
				.trim();
		String bruto = ((FakturaPanel) panel).getTxtBruto().getText()
				.trim();
		String neto = ((FakturaPanel) panel).getTxtNeto().getText()
				.trim();
		String ukupno = ((FakturaPanel) panel).getTxtUkupno().getText()
				.trim();		

		String[] params = { sifra, datum, ime, paritet, bruto, neto, ukupno };

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
		
		/*String sifraK = ((FakturaPanel) panel).getTxtSifraK().getText()
				.trim();
		String nazivK = ((FakturaPanel) panel).getTxtNazivK().getText()
				.trim();*/
		String ime = MainFrame.getInstance().getKorisnik();
		String paritet = ((FakturaPanel) panel).getTxtParitet().getText()
				.trim();
		String bruto = ((FakturaPanel) panel).getTxtBruto().getText()
				.trim();
		String neto = ((FakturaPanel) panel).getTxtNeto().getText()
				.trim();
		String ukupno = ((FakturaPanel) panel).getTxtUkupno().getText()
				.trim();		

		String[] params = { datum, ime, paritet, bruto, neto, ukupno };
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
		/*String sifraK = ((FakturaPanel) panel).getTxtSifraK().getText()
				.trim();*/
		String paritet = ((FakturaPanel) panel).getTxtParitet().getText()
				.trim();
		String bruto = ((FakturaPanel) panel).getTxtBruto().getText()
				.trim();
		String neto = ((FakturaPanel) panel).getTxtNeto().getText()
				.trim();
		String ukupno = ((FakturaPanel) panel).getTxtUkupno().getText()
				.trim();		
		
		String[] params = { sifra, datum, paritet, bruto, neto, ukupno };

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
		/*((FakturaPanel) panel).getTxtSifraK().setEditable(false);
		((FakturaPanel) panel).getTxtNazivK().setEditable(false);*/
		((FakturaPanel) panel).getTxtKorisnik().setEditable(false);
		((FakturaPanel) panel).getTxtParitet().setEditable(false);
		((FakturaPanel) panel).getTxtBruto().setEditable(false);		
		((FakturaPanel) panel).getTxtNeto().setEditable(false);
		((FakturaPanel) panel).getTxtUkupno().setEditable(false);		
		//((FakturaPanel) panel).getBtnKupac().setEnabled(false);
	}

	public void allEnable() {
		((FakturaPanel) panel).getBtnConfirm().setEnabled(true);
		((FakturaPanel) panel).getBtnCancel().setEnabled(true);
		((FakturaPanel) panel).getTxtSifra().setEditable(true);
		((FakturaPanel) panel).getTxtDatum().setEnabled(true);
		((FakturaPanel) panel).getTxtParitet().setEditable(true);
		((FakturaPanel) panel).getTxtBruto().setEditable(true);
		((FakturaPanel) panel).getTxtNeto().setEditable(true);
		((FakturaPanel) panel).getTxtUkupno().setEditable(true);
	}

	public void clearAll() {
		((FakturaPanel) panel).getTxtSifra().setText("");
		((FakturaPanel) panel).getTxtDatum().setCalendar(null);
		//((FakturaPanel) panel).getTxtSifraK().setText("");
		//((FakturaPanel) panel).getTxtNazivK().setText("");
		((FakturaPanel) panel).getTxtKorisnik().setText("");
		((FakturaPanel) panel).getTxtParitet().setText("");
		((FakturaPanel) panel).getTxtBruto().setText("");
		((FakturaPanel) panel).getTxtNeto().setText("");
		((FakturaPanel) panel).getTxtUkupno().setText("");		
	}

	public void btnEnable() {
		((FakturaPanel) panel).getBtnConfirm().setEnabled(true);
		((FakturaPanel) panel).getBtnCancel().setEnabled(true);		
		//((FakturaPanel) panel).getBtnKupac().setEnabled(true);
	}

	public void addDetaljno() {

		JButton btnDetaljno = new JButton("Detalji fakture");
		btnDetaljno.setEnabled(false);
		toolbar.dodajDetaljno(btnDetaljno);

		toolbar.getBtnDetaljno().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					/*DialogNarucena dialog = new DialogNarucena(MainFrame
							.getInstance(), false, ((FakturaPanel) panel)
							.getTxtSifra().getText().trim());
					dialog.setVisible(true);*/
				} else {
					JOptionPane.showConfirmDialog(getParent(),
							"Nijedna faktura nije selektovana.",
							"Upozorenje", JOptionPane.PLAIN_MESSAGE,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
}
