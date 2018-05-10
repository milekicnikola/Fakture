package gui.dialogs;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class DialogToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnView;
	JButton btnUnos;
	JButton btnFirst;
	JButton btnNext;
	JButton btnPrevious;
	JButton btnLast;	

	JButton btnAdd;
	JButton btnUpdate;
	JButton btnDelete;
	JButton btnSearch;

	JButton btnRefresh;	
	JButton btnNextForm;

	JButton btnHelp;

	public DialogToolBar() {
		super();

		btnView = new JButton();
		btnView.setIcon(new ImageIcon("Images/view.png"));
		btnView.setToolTipText("Pogled");
		btnView.setMnemonic(KeyEvent.VK_P);
		add(btnView);

		btnSearch = new JButton();
		btnSearch.setToolTipText("Pretraga");
		btnSearch.setIcon(new ImageIcon("Images/search.png"));
		btnSearch.setMnemonic(KeyEvent.VK_F9);
		add(btnSearch);

		btnRefresh = new JButton();
		btnRefresh.setToolTipText("Osvezi");
		btnRefresh.setIcon(new ImageIcon("Images/refresh.png"));
		btnRefresh.setMnemonic(KeyEvent.VK_F10);
		add(btnRefresh);
		
		addSeparator();

		btnFirst = new JButton();
		btnFirst.setToolTipText("Prvi slog");
		btnFirst.setIcon(new ImageIcon("Images/first.png"));
		btnFirst.setMnemonic(KeyEvent.VK_F2);
		add(btnFirst);

		btnPrevious = new JButton();
		btnPrevious.setToolTipText("Predhodni slog");
		btnPrevious.setIcon(new ImageIcon("Images/previous.png"));
		btnPrevious.setMnemonic(KeyEvent.VK_F4);
		add(btnPrevious);

		btnNext = new JButton();
		btnNext.setToolTipText("Sledeci slog");
		btnNext.setIcon(new ImageIcon("Images/next.png"));
		btnNext.setMnemonic(KeyEvent.VK_F3);
		add(btnNext);

		btnLast = new JButton();
		btnLast.setToolTipText("Poslednji slog");
		btnLast.setIcon(new ImageIcon("Images/last.png"));
		btnLast.setMnemonic(KeyEvent.VK_F5);
		add(btnLast);

		addSeparator();

		btnAdd = new JButton();
		btnAdd.setToolTipText("Dodaj novi slog");
		btnAdd.setIcon(new ImageIcon("Images/add.png"));
		btnAdd.setMnemonic(KeyEvent.VK_F6);
		add(btnAdd);

		btnDelete = new JButton();
		btnDelete.setToolTipText("Obrisi slog");
		btnDelete.setIcon(new ImageIcon("Images/delete.png"));
		btnDelete.setMnemonic(KeyEvent.VK_F8);
		add(btnDelete);

		btnUpdate = new JButton();
		btnUpdate.setToolTipText("Izmeni slog");
		btnUpdate.setIcon(new ImageIcon("Images/edit.png"));
		btnUpdate.setMnemonic(KeyEvent.VK_F7);
		add(btnUpdate);
		
	}

	public JButton getBtnView() {
		return btnView;
	}

	public JButton getBtnFirst() {
		return btnFirst;
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public JButton getBtnPrevious() {
		return btnPrevious;
	}

	public JButton getBtnLast() {
		return btnLast;
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	public JButton getBtnUpdate() {
		return btnUpdate;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnSearch() {
		return btnSearch;
	}

	public JButton getBtnRefresh() {
		return btnRefresh;
	}	

	public JButton getBtnNextForm() {
		return btnNextForm;
	}

	public JButton getBtnHelp() {
		return btnHelp;
	}

	public JButton getBtnUnos() {
		return btnUnos;
	}

	public void setBtnUnos(JButton btnUnos) {
		this.btnUnos = btnUnos;
	}
	
}
