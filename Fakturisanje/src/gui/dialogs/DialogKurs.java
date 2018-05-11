package gui.dialogs;

import gui.model.KursTableModel;
import gui.panels.KursPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogKurs extends StandardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogKurs(JFrame parent, Boolean zoom) {
		super(parent);
		setTitle("Kurs");
		setIconImage(new ImageIcon("Images/kurs.png").getImage());

		tableModel = new KursTableModel(new String[] { "Datum", "Kurs" }, 0);

		panel = new KursPanel();

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
										"Da li ste sigurni da želite da obrišete ovaj kurs?",
										"Brisanje sloga",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE);
						if (dialogResult == JOptionPane.YES_OPTION) {
							removeRow();
						}
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"Nijedan kurs nije selektovan.", "Upozorenje",
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
								"Nijedan kurs nije selektovan.", "Upozorenje",
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
		String datum = (String) tableModel.getValueAt(index, 0);
		String kurs = (String) tableModel.getValueAt(index, 1);		

		((KursPanel) panel).getTxtKurs().setText(kurs);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;		
		try {
			date = sdf.parse(datum);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		((KursPanel) panel).getTxtDatum().setDate(date);

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
			((KursPanel) panel).getTxtDatum().setEnabled(false);
			statusBar.getStatusState().setText("AŽURIRANJE");
			this.state = state;
		} else {
			clearAll();
			btnEnable();
			allEnable();
			((KursPanel) panel).getTxtDatum().requestFocus();
			statusBar.getStatusState().setText(state.toString());
			this.state = state;
		}

	}

	@Override
	public void addRow() {

		Date datum1 = ((KursPanel) panel).getTxtDatum().getDate();
		String datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);
		String kurs = ((KursPanel) panel).getTxtKurs().getText().trim();		

		String[] params = { datum, kurs };

		try {
			KursTableModel ctm = (KursTableModel) table.getModel();
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

		String kurs = ((KursPanel) panel).getTxtKurs().getText().trim();		

		String[] params = { kurs };
		int index = table.getSelectedRow();
		try {
			KursTableModel ctm = (KursTableModel) table.getModel();
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
		Date datum1 = ((KursPanel) panel).getTxtDatum().getDate();
		String datum = "";
		if (datum1 != null) {
			datum = new SimpleDateFormat("yyyy-MM-dd").format(datum1);

		}
		String kurs = ((KursPanel) panel).getTxtKurs().getText().trim();
		

		String[] params = { datum, kurs };

		try {
			KursTableModel ctm = (KursTableModel) table.getModel();
			ctm.search(params);
			updateStateAndTextFields(State.PRETRAGA);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void allDisable() {
		((KursPanel) panel).getBtnConfirm().setEnabled(false);
		((KursPanel) panel).getBtnCancel().setEnabled(false);
		((KursPanel) panel).getTxtDatum().setEnabled(false);
		((KursPanel) panel).getTxtKurs().setEditable(false);
	}

	public void allEnable() {
		((KursPanel) panel).getBtnConfirm().setEnabled(true);
		((KursPanel) panel).getBtnCancel().setEnabled(true);
		((KursPanel) panel).getTxtDatum().setEnabled(true);
		((KursPanel) panel).getTxtKurs().setEditable(true);		
	}

	public void clearAll() {
		((KursPanel) panel).getTxtDatum().setCalendar(null);
		((KursPanel) panel).getTxtKurs().setText("");		
	}

	public void btnEnable() {
		((KursPanel) panel).getBtnConfirm().setEnabled(true);
		((KursPanel) panel).getBtnCancel().setEnabled(true);
	}
}
