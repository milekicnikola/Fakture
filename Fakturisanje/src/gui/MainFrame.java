package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import util.ImagePanel;
import actions.ActionManager;
import databaseConnection.DBConnection;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static MainFrame instance = null;	
	
	private String korisnik = "prazan";

	private StatusBar statusBar;
	private ToolBar toolbar;
	private JMenuBar menu;
	private JMenuItem roba;	
	private JMenuItem kupci;	
	private JMenuItem magacin;	
	private JMenuItem kurs;	
	private JMenuItem faktura;	
	private JMenuItem porudzbina;	
	private JMenuItem otpremnica;
	private JMenuItem otvoreneStavke;
	private JMenuItem poslateStavke;
	private JMenuItem fakturisaneStavke;

	private MainFrame() {

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		setLayout(new BorderLayout());
		setTitle("Fakturisanje");		

		setIconImage(new ImageIcon("Images/fakturisanje.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize((int) (screenSize.getWidth() * 3 / 4),
				(int) (screenSize.getHeight() * 3 / 4));

		setLocationRelativeTo(null);

		statusBar = new StatusBar();
		toolbar = new ToolBar();		

		menu = new JMenuBar();
		roba = new JMenuItem("Roba");
		kupci = new JMenuItem("Kupci");
		magacin = new JMenuItem("Magacin");
		kurs = new JMenuItem("Kurs");
		porudzbina = new JMenuItem("Porudzbina");
		faktura = new JMenuItem("Faktura");				
		otpremnica = new JMenuItem("Otpremnica");
		otvoreneStavke = new JMenuItem("Otvorene stavke");
		poslateStavke = new JMenuItem("Poslate stavke");
		fakturisaneStavke = new JMenuItem("Fakturisane stavke");

		roba.addActionListener(ActionManager.getInstance()
				.getDialogRobaAction());
		magacin.addActionListener(ActionManager.getInstance()
				.getDialogMagacinAction());
		kupci.addActionListener(ActionManager.getInstance()
				.getDialogKupciAction());
		kurs.addActionListener(ActionManager.getInstance()
				.getDialogKursAction());
		porudzbina.addActionListener(ActionManager.getInstance()
				.getDialogPorudzbinaAction());		
		faktura.addActionListener(ActionManager.getInstance()
				.getDialogFakturaAction());		
		otpremnica.addActionListener(ActionManager.getInstance()
				.getDialogOtpremnicaAction());
		otvoreneStavke.addActionListener(ActionManager.getInstance()
				.getDialogOtvoreneStavkeAction());
		poslateStavke.addActionListener(ActionManager.getInstance()
				.getDialogPoslateStavkeAction());
		fakturisaneStavke.addActionListener(ActionManager.getInstance()
				.getDialogFakturisaneStavkeAction());
		
		menu.add(roba);
		menu.add(kupci);
		menu.add(magacin);
		menu.add(kurs);
		menu.add(porudzbina);
		menu.add(faktura);
		menu.add(otpremnica);
		menu.add(otvoreneStavke);
		menu.add(poslateStavke);
		menu.add(fakturisaneStavke);
				

		add(toolbar, BorderLayout.NORTH);
		add(statusBar, BorderLayout.SOUTH);
		setJMenuBar(menu);

		ImagePanel panel = new ImagePanel(
				new ImageIcon(("images/gs.png")).getImage());
		add(panel, BorderLayout.CENTER);
		DBConnection.getConnection();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DBConnection.close();
			}
		});		

	}

	public static MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();			
		}

		return instance;
	}	
	
	public void setKorisnik(String setKorisnik) {
		korisnik = setKorisnik;
	}
	
	public String getKorisnik() {
		return this.korisnik;
	}

	
}
