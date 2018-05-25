package gui.dialogs;

import gui.MainFrame;
import gui.model.PorudzbinaTableModel;
import gui.panels.PorudzbinaPanel;

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

public class DialogPorudzbina extends StandardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogPorudzbina(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Porudzbina");
		setIconImage(new ImageIcon("Images/porudzbina.png").getImage());

		tableModel = new PorudzbinaTableModel(new String[] { "Šifra porudzbine", "Šifra magacina",
				"Naziv magacina", "Korisnik", "PIB kupca", "Naziv kupca", "Datum" }, 0);

		panel = new PorudzbinaPanel();

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
										"Da li ste sigurni da želite da obrišete ovu porudzbinu?",
										"Brisanje sloga",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE);
						if (dialogResult == JOptionPane.YES_OPTION) {
							removeRow();
						}
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"Nijedna porudzbina nije selektovana.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE,
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
								"Nijedna porudzbina nije selektovana.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE,
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
			((PorudzbinaPanel) panel).getBtnMagacin().addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							DialogMagacin dialog = new DialogMagacin(MainFrame
									.getInstance(), true);
							dialog.setVisible(true);
							try {
								if (!dialog.getZoom1().equals(""))
									((PorudzbinaPanel) panel).getTxtSifraM()
											.setText(dialog.getZoom1());
								if (!dialog.getZoom2().equals(""))
									((PorudzbinaPanel) panel).getTxtNazivM()
											.setText(dialog.getZoom2());
							} catch (NullPointerException n) {
							}
						}
					});
			((PorudzbinaPanel) panel).getBtnKupac().addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							DialogKupci dialog = new DialogKupci(MainFrame
									.getInstance(), true);
							dialog.setVisible(true);
							try {
								if (!dialog.getZoom1().equals(""))
									((PorudzbinaPanel) panel).getTxtSifraK()
											.setText(dialog.getZoom1());
								if (!dialog.getZoom2().equals(""))
									((PorudzbinaPanel) panel).getTxtNazivK()
											.setText(dialog.getZoom2());								
							} catch (NullPointerException n) {
							}
						}
					});
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
		String sifraM = (String) tableModel.getValueAt(index, 1);
		String nazivM = (String) tableModel.getValueAt(index, 2);
		String ime = (String) tableModel.getValueAt(index, 3);
		String sifraK = (String) tableModel.getValueAt(index, 4);
		String nazivK = (String) tableModel.getValueAt(index, 5);
		String datum = (String) tableModel.getValueAt(index, 6);

		((PorudzbinaPanel) panel).getTxtSifra().setText(sifra);
		((PorudzbinaPanel) panel).getTxtSifraM().setText(sifraM);
		((PorudzbinaPanel) panel).getTxtNazivM().setText(nazivM);
		((PorudzbinaPanel) panel).getTxtKorisnik().setText(ime);
		((PorudzbinaPanel) panel).getTxtSifraK().setText(sifraK);
		((PorudzbinaPanel) panel).getTxtNazivK().setText(nazivK);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;		
		try {
			date = sdf.parse(datum);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		((PorudzbinaPanel) panel).getTxtDatum().setDate(date);

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
			((PorudzbinaPanel) panel).getTxtSifra().setEditable(false);	
			toolbar.getBtnDetaljno().setEnabled(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			
			if (state == State.PRETRAGA)
				((PorudzbinaPanel) panel).getTxtKorisnik().setEditable(true);
			else
				((PorudzbinaPanel) panel).getTxtKorisnik().setEditable(false);
			
			toolbar.getBtnDetaljno().setEnabled(false);
			((PorudzbinaPanel) panel).getTxtSifra().requestFocus();						
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		String sifra = ((PorudzbinaPanel) panel).getTxtSifra().getText().trim();
		String sifraM = ((PorudzbinaPanel) panel).getTxtSifraM().getText().trim();
		String nazivM = ((PorudzbinaPanel) panel).getTxtNazivM().getText().trim();
		String ime = MainFrame.getInstance().getKorisnik();
		String sifraK = ((PorudzbinaPanel) panel).getTxtSifraK().getText().trim();		
		String nazivK = ((PorudzbinaPanel) panel).getTxtNazivK().getText().trim();
		Date datum1 = ((PorudzbinaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}		

		String[] params = { sifra, sifraM, nazivM, ime, sifraK, nazivK, datum };

		try {
			PorudzbinaTableModel ctm = (PorudzbinaTableModel) table.getModel();
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

		String sifraM = ((PorudzbinaPanel) panel).getTxtSifraM().getText().trim();
		String nazivM = ((PorudzbinaPanel) panel).getTxtNazivM().getText().trim();
		String ime = MainFrame.getInstance().getKorisnik();
		String sifraK = ((PorudzbinaPanel) panel).getTxtSifraK().getText().trim();
		String nazivK = ((PorudzbinaPanel) panel).getTxtNazivK().getText().trim();
		Date datum1 = ((PorudzbinaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}			

		String[] params = { sifraM, nazivM, ime, sifraK, nazivK, datum };
		int index = table.getSelectedRow();
		try {
			PorudzbinaTableModel ctm = (PorudzbinaTableModel) table.getModel();
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
		String sifra = ((PorudzbinaPanel) panel).getTxtSifra().getText().trim();
		String sifraM = ((PorudzbinaPanel) panel).getTxtSifraM().getText().trim();		
		String korisnik = ((PorudzbinaPanel) panel).getTxtKorisnik().getText().trim();
		String sifraK = ((PorudzbinaPanel) panel).getTxtSifraK().getText().trim();
		Date datum1 = ((PorudzbinaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}		

		String[] params = { sifra, sifraM, korisnik, sifraK, datum };

		try {
			PorudzbinaTableModel ctm = (PorudzbinaTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((PorudzbinaPanel) panel).getBtnConfirm().setEnabled(false);
		((PorudzbinaPanel) panel).getBtnCancel().setEnabled(false);
		((PorudzbinaPanel) panel).getTxtSifra().setEditable(false);
		((PorudzbinaPanel) panel).getTxtSifraM().setEditable(false);
		((PorudzbinaPanel) panel).getTxtNazivM().setEditable(false);
		((PorudzbinaPanel) panel).getTxtSifraK().setEditable(false);
		((PorudzbinaPanel) panel).getTxtNazivK().setEditable(false);
		((PorudzbinaPanel) panel).getTxtKorisnik().setEditable(false);
		((PorudzbinaPanel) panel).getTxtDatum().setEnabled(false);
		((PorudzbinaPanel) panel).getBtnMagacin().setEnabled(false);
		((PorudzbinaPanel) panel).getBtnKupac().setEnabled(false);

	}

	public void allEnable() {
		((PorudzbinaPanel) panel).getBtnConfirm().setEnabled(true);
		((PorudzbinaPanel) panel).getBtnCancel().setEnabled(true);
		((PorudzbinaPanel) panel).getTxtSifra().setEditable(true);
		((PorudzbinaPanel) panel).getTxtDatum().setEnabled(true);
		
	}

	public void clearAll() {
		((PorudzbinaPanel) panel).getTxtSifra().setText("");
		((PorudzbinaPanel) panel).getTxtSifraM().setText("");
		((PorudzbinaPanel) panel).getTxtNazivM().setText("");
		((PorudzbinaPanel) panel).getTxtSifraK().setText("");
		((PorudzbinaPanel) panel).getTxtNazivK().setText("");
		((PorudzbinaPanel) panel).getTxtKorisnik().setText("");	
		((PorudzbinaPanel) panel).getTxtDatum().setCalendar(null);
	}

	public void btnEnable() {
		((PorudzbinaPanel) panel).getBtnConfirm().setEnabled(true);
		((PorudzbinaPanel) panel).getBtnCancel().setEnabled(true);
		((PorudzbinaPanel) panel).getBtnMagacin().setEnabled(true);
		((PorudzbinaPanel) panel).getBtnKupac().setEnabled(true);
	}
	
	public void addDetaljno() {
		
		JButton btnDetaljno = new JButton("Detalji porudzbine");
		btnDetaljno.setEnabled(false);
		toolbar.dodajDetaljno(btnDetaljno);
		
		
		toolbar.getBtnDetaljno().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					String where = ((PorudzbinaPanel) panel).getTxtSifra().getText().trim();					
					DialogNarucena dialog = new DialogNarucena(MainFrame
							.getInstance(), false, where);
					dialog.setVisible(true);						
				} else {
					JOptionPane.showConfirmDialog(getParent(),
							"Nijedna porudzbina nije selektovana.", "Upozorenje",
							JOptionPane.PLAIN_MESSAGE,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
}
