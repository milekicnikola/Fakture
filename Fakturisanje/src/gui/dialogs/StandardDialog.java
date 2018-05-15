package gui.dialogs;

import gui.MainFrame;
import gui.model.StandardTableModel;
import gui.panels.StandardPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

public abstract class StandardDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum State {
		POGLED, DODAVANJE, AZURIRANJE, PRETRAGA
	};

	public JSplitPane splitPane;
	public DialogStatusBar statusBar;
	public DialogToolBar toolbar;
	public JFrame parent;
	public JTable table;
	public State state;
	public StandardTableModel tableModel;
	public StandardPanel panel;
	public String whereSQL = "";
	public Boolean isZoom = false;
	public String zoom1 = "";
	public String zoom2 = "";

	public StandardDialog(JFrame parent) {
		super(parent, true);
		this.parent = parent;
		state = State.POGLED;
		setSize((int) parent.getSize().getWidth() * 3 / 4, (int) parent
				.getSize().getHeight() * 3 / 4);
		setLocationRelativeTo(parent);
		setLayout(new MigLayout());
	}

	public void initGUI() {

		table = new JTable();

		table.setModel(tableModel);

		if (whereSQL.equals("")) {

			try {
				tableModel.open();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				tableModel.openWhere(whereSQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.enableInputMethods(false);

		toolbar = new DialogToolBar();
		add(toolbar, "dock north");

		JScrollPane scrollPane = new JScrollPane(table);

		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, panel);
		splitPane.setResizeWeight(1.0);

		add(splitPane, "dock center");

		statusBar = new DialogStatusBar();

		add(statusBar, "dock south");

		statusBar.getStatusRow().setText("0/0");
		statusBar.getStatusDialog().setText(
				MainFrame.getInstance().getKorisnik());
		updateStateAndTextFields(State.POGLED);
		updateStausBarRow(0, table.getRowCount());
		updateStateAndTextFields(State.POGLED);
	}

	public void initStandardActions() {
		toolbar.getBtnView().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view();

			}
		});

		toolbar.getBtnLast().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goLast();
			}
		});
		toolbar.getBtnFirst().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goFirst();

			}
		});

		toolbar.getBtnNext().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goNext();

			}
		});

		toolbar.getBtnPrevious().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goPrev();

			}
		});

		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						allDisable();
						if (isZoom) {
							btnEnable();
							state = State.POGLED;
						}
						statusBar.getStatusState().setText("POGLED");
						if (e.getValueIsAdjusting())
							return;
						int rowCount = table.getModel().getRowCount();
						int currentRow = table.getSelectedRow();
						updateStausBarRow(currentRow + 1, rowCount);
						if (currentRow < 0) {
							toolbar.getBtnDelete().setEnabled(false);
							toolbar.getBtnUpdate().setEnabled(false);
						} else {
							toolbar.getBtnDelete().setEnabled(true);
							toolbar.getBtnUpdate().setEnabled(true);
						}
						sync();
					}
				});

		toolbar.getBtnAdd().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateStateAndTextFields(State.DODAVANJE);

			}
		});

		toolbar.getBtnSearch().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateStateAndTextFields(State.PRETRAGA);

			}
		});

		panel.getBtnConfirm().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if ((isZoom) && (state == State.POGLED)) {
					if (table.getSelectedRow() >= 0) {
						int i = table.getSelectedRow();
						String code = (String) table.getValueAt(i, 0);
						String name = (String) table.getValueAt(i, 1);
						zoom1 = code;
						zoom2 = name;
						dispose();
					}

				} else {
					if (state == State.DODAVANJE) {						
						addRow();						
					}
					if (state == State.AZURIRANJE) {						
						updateRow();						
					}
					if (state == State.PRETRAGA) {
						search();
					}
				}

			}
		});

		toolbar.getBtnRefresh().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tableModel.open();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (isZoom)
					allDisable();
			}
		});
		
		toolbar.getBtnDelete().setEnabled(false);
		toolbar.getBtnUpdate().setEnabled(false);
		

	}

	public void goFirst() {
		int rowCount = table.getModel().getRowCount();
		if (rowCount > 0)
			table.setRowSelectionInterval(0, 0);
	}

	public void goLast() {
		int rowCount = table.getModel().getRowCount();
		if (rowCount > 0)
			table.setRowSelectionInterval(rowCount - 1, rowCount - 1);
	}

	public void goNext() {
		int rowCount = table.getModel().getRowCount();
		if (rowCount > 0) {
			int currentRow = table.getSelectedRow();
			if (currentRow < (rowCount - 1)) {
				table.setRowSelectionInterval(currentRow + 1, currentRow + 1);
			} else {
				table.setRowSelectionInterval(0, 0);
			}
		}
	}

	public void goPrev() {
		int rowCount = table.getModel().getRowCount();
		if (rowCount > 0) {
			int currentRow = table.getSelectedRow();
			if (currentRow > 0) {
				table.setRowSelectionInterval(currentRow - 1, currentRow - 1);
			} else {
				table.setRowSelectionInterval(rowCount - 1, rowCount - 1);
			}
		}
	}

	public void updateStausBarRow(int currentRow, int rowCount) {
		statusBar.getStatusRow().setText(currentRow + "/" + rowCount);

	}

	public void view() {
		if (panel.isShow()) {
			getSplitPane().remove(panel);
			panel.setShow(false);
		} else {
			getSplitPane().add(panel);
			panel.setShow(true);
		}
	}

	public void removeRow() {
		toolbar.getBtnRefresh().doClick();
		int index = table.getSelectedRow();
		if (index == -1)
			return;

		int newIndex = index;

		if (index == table.getRowCount() - 1)
			newIndex--;
		try {
			StandardTableModel s = (StandardTableModel) table.getModel();
			s.deleteRow(index);
			if (table.getRowCount() > 0)
				table.setRowSelectionInterval(newIndex, newIndex);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void btnEnable() {
		panel.getBtnConfirm().setEnabled(true);
		panel.getBtnCancel().setEnabled(true);
	}

	public String getZoom1() {
		return zoom1;
	}

	public String getZoom2() {
		return zoom2;
	}

	abstract void initActions();

	abstract void sync();

	abstract void updateStateAndTextFields(State state);

	abstract void search();

	abstract void allDisable();

	public void addRow() {
	}

	public void updateRow() {
	}
}
