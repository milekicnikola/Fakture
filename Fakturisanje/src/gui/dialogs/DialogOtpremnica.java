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

	private String preuzetDatum = "";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogOtpremnica(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Otpremnica");
		setIconImage(new ImageIcon("Images/otpremnica.png").getImage());

		tableModel = new OtpremnicaTableModel(new String[] {
				"Šifra otpremnice", "Šifra magacina", "Naziv magacina",
				"Šifra fakture", "Datum", "Transport", "Poslata" }, 0);

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
									((OtpremnicaPanel) panel).getTxtNazivM()
											.setText(dialog.getZoom2());
							} catch (NullPointerException n) {
							}
						}
					});

			((OtpremnicaPanel) panel).getBtnFaktura().addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							DialogFaktura dialog = new DialogFaktura(MainFrame
									.getInstance(), true);
							dialog.setVisible(true);
							try {
								if (!dialog.getZoom1().equals(""))
									((OtpremnicaPanel) panel).getTxtSifraF()
											.setText(dialog.getZoom1());
								if (!dialog.getZoom5().equals(""))
									((OtpremnicaPanel) panel).getTxtTransport()
											.setText(dialog.getZoom5());
								if (!dialog.getZoom2().equals("")) {
									preuzetDatum = dialog.getZoom2();
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd");
									Date date = null;
									try {
										date = sdf.parse(preuzetDatum);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									((OtpremnicaPanel) panel).getTxtDatum()
											.setDate(date);
								}
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

		if (index <= (table.getModel().getRowCount() - 1)) {

			toolbar.getBtnDetaljno().setEnabled(true);

			String sifra = (String) tableModel.getValueAt(index, 0);			
			String sifraM = (String) tableModel.getValueAt(index, 1);
			String nazivM = (String) tableModel.getValueAt(index, 2);
			String sifraF = (String) tableModel.getValueAt(index, 3);
			String datum = (String) tableModel.getValueAt(index, 4);
			String transport = (String) tableModel.getValueAt(index, 5);
			String poslata = (String) tableModel.getValueAt(index, 6);

			((OtpremnicaPanel) panel).getTxtSifra().setText(sifra);			
			((OtpremnicaPanel) panel).getTxtSifraM().setText(sifraM);
			((OtpremnicaPanel) panel).getTxtNazivM().setText(nazivM);
			((OtpremnicaPanel) panel).getTxtSifraF().setText(sifraF);
			((OtpremnicaPanel) panel).getTxtTransport().setText(transport);
			((OtpremnicaPanel) panel).getTxtPoslata().setText(poslata);

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

			if (state == State.PRETRAGA) {				
				((OtpremnicaPanel) panel).getTxtPoslata().setEditable(true);
			} else {				
				((OtpremnicaPanel) panel).getTxtPoslata().setEditable(false);
			}

			toolbar.getBtnDetaljno().setEnabled(false);
			((OtpremnicaPanel) panel).getTxtSifra().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		String sifra = ((OtpremnicaPanel) panel).getTxtSifra().getText().trim();		
		String sifraM = ((OtpremnicaPanel) panel).getTxtSifraM().getText()
				.trim();
		String nazivM = ((OtpremnicaPanel) panel).getTxtNazivM().getText()
				.trim();
		String sifraF = ((OtpremnicaPanel) panel).getTxtSifraF().getText()
				.trim();
		String transport = ((OtpremnicaPanel) panel).getTxtTransport()
				.getText().trim();
		Date datum1 = ((OtpremnicaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}

		String[] params = { sifra, sifraM, nazivM, sifraF, datum, transport,
				"ne" };

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

		String sifraM = ((OtpremnicaPanel) panel).getTxtSifraM().getText()
				.trim();		
		String sifraF = ((OtpremnicaPanel) panel).getTxtSifraF().getText()
				.trim();		

		String[] params = { sifraM, sifraF };
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
		String sifraM = ((OtpremnicaPanel) panel).getTxtSifraM().getText()
				.trim();
		String sifraF = ((OtpremnicaPanel) panel).getTxtSifraF().getText()
				.trim();		
		String poslata = ((OtpremnicaPanel) panel).getTxtPoslata().getText()
				.trim();		

		String[] params = { sifra, sifraM, sifraF, poslata };

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
		((OtpremnicaPanel) panel).getTxtSifraM().setEditable(false);
		((OtpremnicaPanel) panel).getTxtNazivM().setEditable(false);
		((OtpremnicaPanel) panel).getTxtSifraF().setEditable(false);
		((OtpremnicaPanel) panel).getTxtTransport().setEditable(false);
		((OtpremnicaPanel) panel).getTxtPoslata().setEditable(false);
		((OtpremnicaPanel) panel).getBtnMagacin().setEnabled(false);
		((OtpremnicaPanel) panel).getBtnFaktura().setEnabled(false);

	}

	public void allEnable() {
		((OtpremnicaPanel) panel).getBtnConfirm().setEnabled(true);
		((OtpremnicaPanel) panel).getBtnCancel().setEnabled(true);
		((OtpremnicaPanel) panel).getTxtSifra().setEditable(true);		
	}

	public void clearAll() {
		((OtpremnicaPanel) panel).getTxtSifra().setText("");
		((OtpremnicaPanel) panel).getTxtDatum().setCalendar(null);		
		((OtpremnicaPanel) panel).getTxtSifraM().setText("");
		((OtpremnicaPanel) panel).getTxtNazivM().setText("");
		((OtpremnicaPanel) panel).getTxtSifraF().setText("");
		((OtpremnicaPanel) panel).getTxtTransport().setText("");
		((OtpremnicaPanel) panel).getTxtPoslata().setText("");
	}

	public void btnEnable() {
		((OtpremnicaPanel) panel).getBtnConfirm().setEnabled(true);
		((OtpremnicaPanel) panel).getBtnCancel().setEnabled(true);
		((OtpremnicaPanel) panel).getBtnMagacin().setEnabled(true);
		((OtpremnicaPanel) panel).getBtnFaktura().setEnabled(true);
	}

	public void addDetaljno() {

		JButton btnDetaljno = new JButton("Detalji otpremnice");
		btnDetaljno.setEnabled(false);
		toolbar.dodajDetaljno(btnDetaljno);

		toolbar.getBtnDetaljno().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					DialogOtpremljena dialog = new DialogOtpremljena(MainFrame
							.getInstance(), true, ((OtpremnicaPanel) panel)
							.getTxtSifra().getText().trim(),
							((OtpremnicaPanel) panel).getTxtSifraM().getText()
									.trim(), ((OtpremnicaPanel) panel)
									.getTxtPoslata().getText().trim());
					dialog.setVisible(true);

					toolbar.getBtnRefresh().doClick();

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
