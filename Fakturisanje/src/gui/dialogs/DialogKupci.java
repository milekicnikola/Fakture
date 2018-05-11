package gui.dialogs;

import gui.model.KupciTableModel;
import gui.panels.KupciPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogKupci extends StandardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogKupci(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Kupci");
		setIconImage(new ImageIcon("Images/kupci.png").getImage());

		tableModel = new KupciTableModel(new String[] { "Šifra", "Naziv",
				"Drugi naziv", "Adresa", "Grad", "Država" }, 0);

		panel = new KupciPanel();

		if (zoom)
			isZoom = true;

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
										"Da li ste sigurni da želite da obrišete ovog kupca?",
										"Brisanje sloga",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE);
						if (dialogResult == JOptionPane.YES_OPTION) {
							removeRow();
						}
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"Nijedan kupac nije selektovan.", "Upozorenje",
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
								"Nijedan kupac nije selektovan.", "Upozorenje",
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
		String sifra = (String) tableModel.getValueAt(index, 0);
		String naziv = (String) tableModel.getValueAt(index, 1);
		String naziv2 = (String) tableModel.getValueAt(index, 2);
		String adresa = (String) tableModel.getValueAt(index, 3);
		String grad = (String) tableModel.getValueAt(index, 4);
		String drzava = (String) tableModel.getValueAt(index, 5);

		((KupciPanel) panel).getTxtSifra().setText(sifra);
		((KupciPanel) panel).getTxtNaziv().setText(naziv);
		((KupciPanel) panel).getTxtNaziv2().setText(naziv2);
		((KupciPanel) panel).getTxtAdresa().setText(adresa);
		((KupciPanel) panel).getTxtGrad().setText(grad);
		((KupciPanel) panel).getTxtDrzava().setText(drzava);

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
			((KupciPanel) panel).getTxtSifra().setEditable(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			((KupciPanel) panel).getTxtSifra().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		String sifra = ((KupciPanel) panel).getTxtSifra().getText().trim();
		String naziv = ((KupciPanel) panel).getTxtNaziv().getText().trim();
		String naziv2 = ((KupciPanel) panel).getTxtNaziv2().getText().trim();
		String adresa = ((KupciPanel) panel).getTxtAdresa().getText().trim();
		String grad = ((KupciPanel) panel).getTxtGrad().getText().trim();
		String drzava = ((KupciPanel) panel).getTxtDrzava().getText().trim();

		String[] params = { sifra, naziv, naziv2, adresa, grad, drzava };

		try {
			KupciTableModel ctm = (KupciTableModel) table.getModel();
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

		String naziv = ((KupciPanel) panel).getTxtNaziv().getText().trim();
		String naziv2 = ((KupciPanel) panel).getTxtNaziv2().getText().trim();
		String adresa = ((KupciPanel) panel).getTxtAdresa().getText().trim();
		String grad = ((KupciPanel) panel).getTxtGrad().getText().trim();
		String drzava = ((KupciPanel) panel).getTxtDrzava().getText().trim();

		String[] params = { naziv, naziv2, adresa, grad, drzava };
		int index = table.getSelectedRow();
		try {
			KupciTableModel ctm = (KupciTableModel) table.getModel();
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
		String sifra = ((KupciPanel) panel).getTxtSifra().getText().trim();
		String naziv = ((KupciPanel) panel).getTxtNaziv().getText().trim();
		String naziv2 = ((KupciPanel) panel).getTxtNaziv2().getText().trim();
		String adresa = ((KupciPanel) panel).getTxtAdresa().getText().trim();
		String grad = ((KupciPanel) panel).getTxtGrad().getText().trim();
		String drzava = ((KupciPanel) panel).getTxtDrzava().getText().trim();

		String[] params = { sifra, naziv, naziv2, adresa, grad, drzava };

		try {
			KupciTableModel ctm = (KupciTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((KupciPanel) panel).getBtnConfirm().setEnabled(false);
		((KupciPanel) panel).getBtnCancel().setEnabled(false);
		((KupciPanel) panel).getTxtSifra().setEditable(false);
		((KupciPanel) panel).getTxtNaziv().setEditable(false);
		((KupciPanel) panel).getTxtNaziv2().setEditable(false);
		((KupciPanel) panel).getTxtAdresa().setEditable(false);
		((KupciPanel) panel).getTxtGrad().setEditable(false);
		((KupciPanel) panel).getTxtDrzava().setEditable(false);

	}

	public void allEnable() {
		((KupciPanel) panel).getBtnConfirm().setEnabled(true);
		((KupciPanel) panel).getBtnCancel().setEnabled(true);
		((KupciPanel) panel).getTxtSifra().setEditable(true);
		((KupciPanel) panel).getTxtNaziv().setEditable(true);
		((KupciPanel) panel).getTxtNaziv2().setEditable(true);
		((KupciPanel) panel).getTxtAdresa().setEditable(true);
		((KupciPanel) panel).getTxtGrad().setEditable(true);
		((KupciPanel) panel).getTxtDrzava().setEditable(true);
	}

	public void clearAll() {
		((KupciPanel) panel).getTxtSifra().setText("");
		((KupciPanel) panel).getTxtNaziv().setText("");
		((KupciPanel) panel).getTxtNaziv2().setText("");
		((KupciPanel) panel).getTxtAdresa().setText("");
		((KupciPanel) panel).getTxtGrad().setText("");
		((KupciPanel) panel).getTxtDrzava().setText("");
	}

	public void btnEnable() {
		((KupciPanel) panel).getBtnConfirm().setEnabled(true);
		((KupciPanel) panel).getBtnCancel().setEnabled(true);
	}
}
