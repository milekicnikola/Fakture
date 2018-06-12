package gui.dialogs;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import util.ResourceLoader;

public class DialogToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton btnView;	
	JButton btnFirst;
	JButton btnNext;
	JButton btnPrevious;
	JButton btnLast;

	JButton btnAdd;
	JButton btnUpdate;
	JButton btnDelete;
	JButton btnSearch;
	JButton btnRefresh;

	JButton btnDetaljno;
	JButton btnPosalji;

	JButton btnIzvestaj;
	JButton btnPrevod;
	JButton btnProsireniIzvestaj;
	JButton btnPorudzbina;

	public DialogToolBar() {
		super();

		BufferedImage view = null, search = null, refresh = null, first = null, previous = null, next = null,
				last = null, add = null, delete = null, edit = null;

		btnView = new JButton();
		try {
			view = ImageIO.read(ResourceLoader.load("Images/view.png"));
		} catch (Exception e) {
		}
		btnView.setIcon(new ImageIcon(view));
		btnView.setToolTipText("Pogled");
		btnView.setMnemonic(KeyEvent.VK_P);
		add(btnView);

		btnSearch = new JButton();
		btnSearch.setToolTipText("Pretraga");
		try {
			search = ImageIO.read(ResourceLoader.load("Images/search.png"));
		} catch (Exception e) {
		}
		btnSearch.setIcon(new ImageIcon(search));
		btnSearch.setMnemonic(KeyEvent.VK_F9);
		add(btnSearch);

		btnRefresh = new JButton();
		btnRefresh.setToolTipText("Osveži");
		try {
			refresh = ImageIO.read(ResourceLoader.load("Images/refresh.png"));
		} catch (Exception e) {			
		}
		btnRefresh.setIcon(new ImageIcon(refresh));
		btnRefresh.setMnemonic(KeyEvent.VK_F10);
		add(btnRefresh);

		addSeparator();

		btnFirst = new JButton();
		btnFirst.setToolTipText("Prvi slog");
		try {
			first = ImageIO.read(ResourceLoader.load("Images/first.png"));
		} catch (Exception e) {			
		}
		btnFirst.setIcon(new ImageIcon(first));
		btnFirst.setMnemonic(KeyEvent.VK_F2);
		add(btnFirst);

		btnPrevious = new JButton();
		btnPrevious.setToolTipText("Predhodni slog");
		try {
			previous = ImageIO.read(ResourceLoader.load("Images/previous.png"));
		} catch (Exception e) {			
		}
		btnPrevious.setIcon(new ImageIcon(previous));
		btnPrevious.setMnemonic(KeyEvent.VK_F4);
		add(btnPrevious);

		btnNext = new JButton();
		btnNext.setToolTipText("Sledeći slog");
		try {
			next = ImageIO.read(ResourceLoader.load("Images/next.png"));
		} catch (Exception e) {			
		}
		btnNext.setIcon(new ImageIcon(next));
		btnNext.setMnemonic(KeyEvent.VK_F3);
		add(btnNext);

		btnLast = new JButton();
		btnLast.setToolTipText("Poslednji slog");
		try {
			last = ImageIO.read(ResourceLoader.load("Images/last.png"));
		} catch (Exception e) {			
		}
		btnLast.setIcon(new ImageIcon(last));
		btnLast.setMnemonic(KeyEvent.VK_F5);
		add(btnLast);

		addSeparator();

		btnAdd = new JButton();
		btnAdd.setToolTipText("Dodaj novi slog");
		try {
			add = ImageIO.read(ResourceLoader.load("Images/add.png"));
		} catch (Exception e) {			
		}
		btnAdd.setIcon(new ImageIcon(add));
		btnAdd.setMnemonic(KeyEvent.VK_F6);
		add(btnAdd);

		btnDelete = new JButton();
		btnDelete.setToolTipText("Obriši slog");
		try {
			delete = ImageIO.read(ResourceLoader.load("Images/delete.png"));
		} catch (Exception e) {			
		}
		btnDelete.setIcon(new ImageIcon(delete));
		btnDelete.setMnemonic(KeyEvent.VK_F8);
		add(btnDelete);

		btnUpdate = new JButton();
		btnUpdate.setToolTipText("Izmeni slog");
		try {
			edit = ImageIO.read(ResourceLoader.load("Images/edit.png"));
		} catch (Exception e) {			
		}
		btnUpdate.setIcon(new ImageIcon(edit));
		btnUpdate.setMnemonic(KeyEvent.VK_F7);
		add(btnUpdate);

		addSeparator();
		addSeparator();

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
	
	public JButton getBtnDetaljno() {
		return btnDetaljno;
	}

	public void setBtnDetaljno(JButton btnDetaljno) {
		this.btnDetaljno = btnDetaljno;
	}

	public void dodajDetaljno(JButton detaljno) {
		this.btnDetaljno = detaljno;
		this.add(btnDetaljno);
	}

	public JButton getBtnPosalji() {
		return btnPosalji;
	}

	public void setBtnPosalji(JButton btnPosalji) {
		this.btnPosalji = btnPosalji;
	}

	public void dodajPosalji(JButton posalji) {
		this.btnPosalji = posalji;
		this.add(btnPosalji);
	}

	public JButton getBtnIzvestaj() {
		return btnIzvestaj;
	}

	public void setBtnIzvestaj(JButton btnIzvestaj) {
		this.btnIzvestaj = btnIzvestaj;
	}

	public JButton getBtnPrevod() {
		return btnPrevod;
	}

	public void setBtnPrevod(JButton btnPrevod) {
		this.btnPrevod = btnPrevod;
	}

	public JButton getBtnProsireniIzvestaj() {
		return btnProsireniIzvestaj;
	}

	public void setBtnProsireniIzvestaj(JButton btnProsireniIzvestaj) {
		this.btnProsireniIzvestaj = btnProsireniIzvestaj;
	}

	public void dodajIzvestaj(JButton izvestaj) {
		this.btnIzvestaj = izvestaj;
		this.addSeparator();
		this.add(btnIzvestaj);
	}

	public void dodajPrevod(JButton prevod) {
		this.btnPrevod = prevod;
		this.addSeparator();
		this.add(btnPrevod);
	}

	public void dodajProsireniIzvestaj(JButton prosireniIzvestaj) {
		this.btnProsireniIzvestaj = prosireniIzvestaj;
		this.addSeparator();
		this.add(btnProsireniIzvestaj);
	}

	public void dodajCeluPorudzbinu(JButton porudzbina) {
		this.btnPorudzbina = porudzbina;
		this.addSeparator();
		this.add(btnPorudzbina);

	}

	public JButton getBtnPorudzbina() {
		return btnPorudzbina;
	}

	public void setBtnPorudzbina(JButton btnPorudzbina) {
		this.btnPorudzbina = btnPorudzbina;
	}

}
