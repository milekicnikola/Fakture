package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import util.ImagePanel;
import actions.ActionManager;
import databaseConnection.DBConnection;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static MainFrame instance = null;

	private StatusBar statusBar;
	private ToolBar toolbar;
	private JMenuBar menu;
	private JMenu roba;	
	private JMenu kupci;	
	private JMenu magacin;	
	private JMenu kurs;	
	private JMenu faktura;	
	private JMenu porudzbina;	

	private MainFrame() {

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		setLayout(new BorderLayout());
		setTitle("Fakturisanje");

		setIconImage(new ImageIcon("Images/money.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize((int) (screenSize.getWidth() * 3 / 4),
				(int) (screenSize.getHeight() * 3 / 4));

		setLocationRelativeTo(null);

		statusBar = new StatusBar();
		toolbar = new ToolBar();

		menu = new JMenuBar();
		roba = new JMenu("Roba");
		kupci = new JMenu("Kupci");
		magacin = new JMenu("Magacin");
		kurs = new JMenu("Kurs");
		faktura = new JMenu("Faktura");
		porudzbina = new JMenu("Porudzbina");

		roba.addActionListener(ActionManager.getInstance()
				.getDialogRobaAction());
		/*kupci.addActionListener(ActionManager.getInstance()
				.getDialogMestaAction());
		magacin.addActionListener(ActionManager.getInstance()
				.getDialogBankaAction());
		kurs.addActionListener(ActionManager.getInstance()
				.getDialogLiceAction());
		faktura.addActionListener(ActionManager.getInstance()
				.getDialogPreduzeceAction());
		porudzbina.addActionListener(ActionManager.getInstance()
				.getDialogRacuniAction());*/
		
		menu.add(roba);
		menu.add(kupci);
		menu.add(magacin);
		menu.add(kurs);
		menu.add(faktura);
		menu.add(porudzbina);

		add(toolbar, BorderLayout.NORTH);
		add(statusBar, BorderLayout.SOUTH);
		setJMenuBar(menu);

		ImagePanel panel = new ImagePanel(
				new ImageIcon(("images/bank.jpg")).getImage());
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

}
