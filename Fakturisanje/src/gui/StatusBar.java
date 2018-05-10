package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String imeKorisnika = "";
    public String prezimeKorisnika = "";
	private StatusPane statusBarName;
	private StatusPane statusBarLanguage;
	private StatusPane statusBarDate;

	public StatusBar() {

		setLayout(new GridLayout(1, 3, 10, 10));
		setBackground(Color.lightGray);
		setBorder(BorderFactory.createLineBorder(Color.darkGray));

		statusBarName = new StatusPane("General Solutions");
		statusBarLanguage = new StatusPane("Fakturisanje");
		statusBarDate = new StatusPane(setFormattedDate());

		add(statusBarName);
		add(statusBarLanguage);
		add(statusBarDate);
	}

	class StatusPane extends JLabel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public StatusPane(String text) {
			
			super(text);
			setFont(new Font("Arial", Font.BOLD, 15));
			setHorizontalAlignment(CENTER);
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			setPreferredSize(new Dimension(200, 25));
		}
	}

	public void initComponents() {
		statusBarName.setText("");
		statusBarDate.setText(setFormattedDate());
	}
	
	public String setFormattedDate() {
		DateFormat df = DateFormat.getDateInstance();
		String datum = df.format(new Date());
		return datum;
	}

	public StatusPane getStatusBarName() {
		return statusBarName;
	}

	public StatusPane getStatusBarLanguage() {
		return statusBarLanguage;
	}

	public StatusPane getStatusBarDate() {
		return statusBarDate;
	}

	public String getImeKorisnika() {
		return imeKorisnika;
	}

	public void setImeKorisnika(String imeKorisnika) {
		this.imeKorisnika = imeKorisnika;
	}

	public String getPrezimeKorisnika() {
		return prezimeKorisnika;
	}

	public void setPrezimeKorisnika(String prezimeKorisnika) {
		this.prezimeKorisnika = prezimeKorisnika;
	}
    
	

}
