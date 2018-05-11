package gui.dialogs;

import gui.MainFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import databaseConnection.DBConnection;

public class LoginDialog extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUserName;
	private JPasswordField txtPassword;	

	public LoginDialog(){
		
		getContentPane().setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		
		setSize(264, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(new ImageIcon("Images/fakturisanje.png").getImage());
		getContentPane().setLayout(null);
		setTitle("Fakturisanje");
		JLabel lblUsername = new JLabel("Korisničko ime : ");
		lblUsername.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblUsername.setBounds(10, 28, 89, 14);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Lozinka : ");
		lblPassword.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblPassword.setBounds(10, 59, 89, 14);
		getContentPane().add(lblPassword);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(101, 25, 140, 20);
		getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setFont(new Font("Tahoma", Font.ITALIC, 12));
		btnLogIn.setBounds(84, 88, 89, 23);
		getContentPane().add(btnLogIn);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(101, 57, 140, 20);
		getContentPane().add(txtPassword);
		
		btnLogIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				login(txtUserName.getText(), new String(txtPassword.getPassword()));
			}

			
		});
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	private void login(String user, String password) {
		
		/*MainFrame mf = MainFrame.getInstance();
		this.dispose();
		mf.setVisible(true);*/
		
		if(user.equals("")){
			
			JOptionPane.showMessageDialog(this, "Polje za korisničko ime je prazno", "Upozorenje", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if(password.equals("")){
			
			JOptionPane.showMessageDialog(this, "Polje za lozinku je prazno", "Upozorenje", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		
		//ovo je nebezbedno :) pa cak i sa PreparedStatement-om
		//String query = "selsect * from KORISNIK where KORISNICKOIME = " +"'"+ user +"'"+ " AND LOZINKA = " +"'"+ password +"'";
		
		try {
			DBConnection.open();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			// iz bezbedonosnih razloga koristimo PreparedStatement
			// mogli smo i rucno da pravimo filter protiv SQL Inj, al to smara
			// koriscenjem PreparedStatement-a stringovi budu isparsirani od strane JDBC driver-a
			String selectStatement = "SELECT * FROM korisnik WHERE korisnicko_ime = ? and Lozinka = ?";
			Connection con = DBConnection.getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, user);
			prepStmt.setString(2, password);
			ResultSet rs = prepStmt.executeQuery();
			
			if(rs.next()){ // postoji korisnik u bazi
				MainFrame mf = MainFrame.getInstance();
				this.dispose();
				mf.setVisible(true);
				mf.setKorisnik(user);				
			} else {
				JOptionPane.showMessageDialog(this, "Neispravno korisničko ime ili lozinka!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
