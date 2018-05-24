package gui.dialogs;

import gui.MainFrame;
import gui.model.OtpremnicaTableModel;
import gui.panels.OtpremnicaPanel;

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

public class DialogOtpremnica extends StandardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogOtpremnica(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Otpremnica");
		setIconImage(new ImageIcon("Images/otpremnica.png").getImage());

		tableModel = new OtpremnicaTableModel(new String[] { "Šifra otpremnice",
				"Korisnik", "Šifra magacina", "Magacin", "Datum", "Transport" }, 0);

		panel = new OtpremnicaPanel();

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
										"Da li ste sigurni da želite da obrišete ovu otpremnicu?",
										"Brisanje sloga",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE);
						if (dialogResult == JOptionPane.YES_OPTION) {
							removeRow();
						}
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"Nijedna otpremnica nije selektovana.",
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
								"Nijedna otpremnica nije selektovana.",
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
			
			((OtpremnicaPanel) panel).getBtnMagacin().addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							DialogMagacin dialog = new DialogMagacin(MainFrame
									.getInstance(), true);
							dialog.setVisible(true);
							try {
								if (!dialog.getZoom1().equals(""))
									((OtpremnicaPanel) panel).getTxtSifraM()
											.setText(dialog.getZoom1());
								if (!dialog.getZoom2().equals(""))
									((OtpremnicaPanel) panel).getTxtMagacin()
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
		String korisnik = (String) tableModel.getValueAt(index, 1);
		String sifraM = (String) tableModel.getValueAt(index, 2);
		String magacin = (String) tableModel.getValueAt(index, 3);
		String datum = (String) tableModel.getValueAt(index, 4);		
		String transport = (String) tableModel.getValueAt(index, 5);

		((OtpremnicaPanel) panel).getTxtSifra().setText(sifra);		
		((OtpremnicaPanel) panel).getTxtKorisnik().setText(korisnik);
		((OtpremnicaPanel) panel).getTxtSifraM().setText(sifraM);
		((OtpremnicaPanel) panel).getTxtMagacin().setText(magacin);				
		((OtpremnicaPanel) panel).getTxtTransport().setText(transport);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(datum);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		((OtpremnicaPanel) panel).getTxtDatum().setDate(date);

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
			((OtpremnicaPanel) panel).getTxtSifra().setEditable(false);			
			toolbar.getBtnDetaljno().setEnabled(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();	
			
			if (state == State.PRETRAGA)
				((OtpremnicaPanel) panel).getTxtKorisnik().setEditable(true);
			else 
				((OtpremnicaPanel) panel).getTxtKorisnik().setEditable(false);
			
			toolbar.getBtnDetaljno().setEnabled(false);
			((OtpremnicaPanel) panel).getTxtSifra().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		String sifra = ((OtpremnicaPanel) panel).getTxtSifra().getText().trim();
		String korisnik = MainFrame.getInstance().getKorisnik();
		String sifraM = ((OtpremnicaPanel) panel).getTxtSifraM().getText().trim();
		String magacin = ((OtpremnicaPanel) panel).getTxtMagacin().getText().trim();
		String transport = ((OtpremnicaPanel) panel).getTxtTransport().getText().trim();
		Date datum1 = ((OtpremnicaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}						

		String[] params = { sifra, korisnik, sifraM, magacin, datum, transport };

		try {
			OtpremnicaTableModel ctm = (OtpremnicaTableModel) table.getModel();
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
			
		String korisnik = MainFrame.getInstance().getKorisnik();		
		String sifraM = ((OtpremnicaPanel) panel).getTxtSifraM().getText()
				.trim();
		String magacin = ((OtpremnicaPanel) panel).getTxtMagacin().getText()
				.trim();
		String transport = ((OtpremnicaPanel) panel).getTxtTransport().getText()
				.trim();
		
		Date datum1 = ((OtpremnicaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}		

		String[] params = { korisnik, sifraM, magacin, datum, transport };
		int index = table.getSelectedRow();
		try {
			OtpremnicaTableModel ctm = (OtpremnicaTableModel) table.getModel();
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
		String sifra = ((OtpremnicaPanel) panel).getTxtSifra().getText().trim();
		String sifraM = ((OtpremnicaPanel) panel).getTxtSifraM().getText().trim();
		String korisnik = ((OtpremnicaPanel) panel).getTxtKorisnik().getText().trim();
		String transport = ((OtpremnicaPanel) panel).getTxtTransport().getText().trim();
		Date datum1 = ((OtpremnicaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}		
		
		String[] params = { sifra, korisnik, sifraM, datum, transport };

		try {
			OtpremnicaTableModel ctm = (OtpremnicaTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((OtpremnicaPanel) panel).getBtnConfirm().setEnabled(false);
		((OtpremnicaPanel) panel).getBtnCancel().setEnabled(false);
		((OtpremnicaPanel) panel).getTxtSifra().setEditable(false);
		((OtpremnicaPanel) panel).getTxtDatum().setEnabled(false);		
		((OtpremnicaPanel) panel).getTxtKorisnik().setEditable(false);
		((OtpremnicaPanel) panel).getTxtSifraM().setEditable(false);
		((OtpremnicaPanel) panel).getTxtMagacin().setEditable(false);
		((OtpremnicaPanel) panel).getTxtTransport().setEditable(false);
		((OtpremnicaPanel) panel).getBtnMagacin().setEnabled(false);
		
	}

	public void allEnable() {
		((OtpremnicaPanel) panel).getBtnConfirm().setEnabled(true);
		((OtpremnicaPanel) panel).getBtnCancel().setEnabled(true);
		((OtpremnicaPanel) panel).getTxtSifra().setEditable(true);
		((OtpremnicaPanel) panel).getTxtTransport().setEditable(true);
		((OtpremnicaPanel) panel).getTxtDatum().setEnabled(true);	
	}

	public void clearAll() {
		((OtpremnicaPanel) panel).getTxtSifra().setText("");
		((OtpremnicaPanel) panel).getTxtDatum().setCalendar(null);
		((OtpremnicaPanel) panel).getTxtKorisnik().setText("");
		((OtpremnicaPanel) panel).getTxtSifraM().setText("");
		((OtpremnicaPanel) panel).getTxtMagacin().setText("");		
		((OtpremnicaPanel) panel).getTxtTransport().setText("");
	}

	public void btnEnable() {
		((OtpremnicaPanel) panel).getBtnConfirm().setEnabled(true);
		((OtpremnicaPanel) panel).getBtnCancel().setEnabled(true);		
		((OtpremnicaPanel) panel).getBtnMagacin().setEnabled(true);
	}

	public void addDetaljno() {

		JButton btnDetaljno = new JButton("Detalji otpremnice");
		btnDetaljno.setEnabled(false);
		toolbar.dodajDetaljno(btnDetaljno);

		toolbar.getBtnDetaljno().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					/*DialogNarucena dialog = new DialogNarucena(MainFrame
							.getInstance(), false, ((OtpremnicaPanel) panel)
							.getTxtSifra().getText().trim());
					dialog.setVisible(true);*/
				} else {
					JOptionPane.showConfirmDialog(getParent(),
							"Nijedna otpremnica nije selektovana.",
							"Upozorenje", JOptionPane.PLAIN_MESSAGE,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
}
