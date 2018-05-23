package gui.dialogs;

import gui.MainFrame;
import gui.model.NarucenaTableModel;
import gui.panels.NarucenaPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogNarucena extends StandardDialog {
	
	private String porudzbina = "";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DialogNarucena(JFrame parent, Boolean zoom, String where) {
		super(parent);
		setTitle("Naručena roba");
		setIconImage(new ImageIcon("Images/porudzbina.png").getImage());
		
		String whereStm = "";
					
		if (zoom)
			isZoom = true;
		
		if (!isZoom) {
		
		porudzbina = where;
		whereStm = " WHERE narucena_roba.sifra_porudzbine = '" + where + "'";
		
		} else {
			whereStm = "";			
		}		
		
		tableModel = new NarucenaTableModel(new String[] { "Šifra porudzbine", "Šifra robe",
				"Naziv robe", "Datum isporuke", "Komada naručeno", "Komada poslato", "Komada ostalo", "Ko radi" }, 0, whereStm);

		panel = new NarucenaPanel();
		
		initGUI();
		initStandardActions();
		initActions();		
		
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
								"Nijedna stavka nije selektovana.", "Upozorenje",
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
								"Nijedna stavka nije selektovana.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE,
								JOptionPane.WARNING_MESSAGE);
					}

				}
			});
			
			/*toolbar.getBtnDetaljno().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (table.getSelectedRow() >= 0) {
						DialogNarucena dialog = new DialogNarucena(MainFrame
								.getInstance(), true, ((NarucenaPanel) panel).getTxtSifra().getText().trim());
						dialog.setVisible(true);						
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"Nijedna Narucena nije selektovana.", "Upozorenje",
								JOptionPane.PLAIN_MESSAGE,
								JOptionPane.WARNING_MESSAGE);
					}
				}
			});*/

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
			//toolbar.getBtnDetaljno().setEnabled(false);
			return;
		}
		//toolbar.getBtnDetaljno().setEnabled(true);
		
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
			((NarucenaPanel) panel).getBtnRoba().setEnabled(false);
			((NarucenaPanel) panel).getTxtDatum().setEnabled(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();				
			((NarucenaPanel) panel).getTxtNaruceno().requestFocus();						
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		String sifraP = ((NarucenaPanel) panel).getTxtSifraP().getText().trim();
		String sifraR = ((NarucenaPanel) panel).getTxtSifraR().getText().trim();
		String nazivR = ((NarucenaPanel) panel).getTxtNazivR().getText().trim();
		String naruceno = ((NarucenaPanel) panel).getTxtNaruceno().getText().trim();
		String poslato = ((NarucenaPanel) panel).getTxtPoslato().getText().trim();		
		String ostalo = ((NarucenaPanel) panel).getTxtOstalo().getText().trim();
		String ko = ((NarucenaPanel) panel).getTxtKo().getText().trim();
		Date datum1 = ((NarucenaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}		
		
		String[] params = { sifraP, sifraR, nazivR, datum, naruceno, poslato, ostalo, ko };

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
				
		String naruceno = ((NarucenaPanel) panel).getTxtNaruceno().getText().trim();
		String poslato = ((NarucenaPanel) panel).getTxtPoslato().getText().trim();
		String ostalo = ((NarucenaPanel) panel).getTxtOstalo().getText().trim();
		String ko = ((NarucenaPanel) panel).getTxtKo().getText().trim();
		/*Date datum1 = ((NarucenaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}*/
		
		String[] params = { naruceno, poslato, ostalo, ko };
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
		String naruceno = ((NarucenaPanel) panel).getTxtNaruceno().getText().trim();
		String poslato = ((NarucenaPanel) panel).getTxtPoslato().getText().trim();
		String ostalo = ((NarucenaPanel) panel).getTxtOstalo().getText().trim();
		String ko = ((NarucenaPanel) panel).getTxtKo().getText().trim();
		Date datum1 = ((NarucenaPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		}
		
		String[] params = { sifraP, sifraR, naruceno, poslato, ostalo, datum, ko };

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
		((NarucenaPanel) panel).getTxtPoslato().setEditable(true);
		((NarucenaPanel) panel).getTxtOstalo().setEditable(true);
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
		((NarucenaPanel) panel).getBtnRoba().setEnabled(true);		
	}

}
