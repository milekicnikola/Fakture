package gui.dialogs;

import gui.model.MagacinTableModel;
import gui.panels.MagacinPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogMagacin extends StandardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogMagacin(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Magacin");
		setIconImage(new ImageIcon("Images/magacin.png").getImage());

		tableModel = new MagacinTableModel(new String[] { "Šifra", "Naziv",
				"Adresa", "Šef", "Telefon" }, 0);

		panel = new MagacinPanel();

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
										"Da li ste sigurni da želite da obrišete ovaj magacin?",
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
			((MagacinPanel) panel).getTxtSifra().setEditable(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
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
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
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
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
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
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
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
}
