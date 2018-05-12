package gui.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.SortUtils;
import databaseConnection.DBConnection;

public class NarucenaTableModel extends StandardTableModel {
	
	private String basicQuery1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NarucenaTableModel(Object[] colName, int rowCount, String where) {
		super(colName, rowCount);
		basicQuery = "SELECT narucena_roba.sifra_porudzbine as sifraPorudzbine, narucena_roba.sifra_robe as sifraRobe, naziv_robe, komada_naruceno, komada_poslato, komada_ostalo FROM narucena_roba JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON narucena_roba.sifra_robe = roba.sifra_robe" + where;
		basicQuery1 = "SELECT narucena_roba.sifra_porudzbine as sifraPorudzbine, narucena_roba.sifra_robe as sifraRobe, naziv_robe, komada_naruceno, komada_poslato, komada_ostalo FROM narucena_roba JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON narucena_roba.sifra_robe = roba.sifra_robe"; 
		orderBy = " ORDER BY narucena_roba.sifra_porudzbine";
	}

	// Dodate konstante za potrebe izvestavanja korisnika o greskama
	// Dodata metoda za proveru i zakljucavanje tekuceg reda
	@Override
	public void checkRow(int index) throws SQLException {

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection.getConnection()
				.prepareStatement(basicQuery1 + " where narucena_roba.sifra_porudzbine = ? and narucena_roba.sifra_robe");

		String sifraP = (String) getValueAt(index, 0);
		String sifraR = (String) getValueAt(index, 1);
		selectStmt.setString(1, sifraP);
		selectStmt.setString(2, sifraR);

		ResultSet rset = selectStmt.executeQuery();

		String sifra_porudzbine = "", sifra_robe = "", nazivR = "", naruceno = "", poslato = "", ostalo = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_porudzbine = rset.getString("sifraPorudzbine").trim();
			sifra_robe = rset.getString("sifraRobe").trim();
			nazivR = rset.getString("NAZIV_ROBE").trim();
			naruceno = rset.getString("KOMADA_NARUCENO").trim();
			poslato = rset.getString("KOMADA_POSLATO");
			ostalo = rset.getString("KOMADA_OSTALO");			
			postoji = true;
		}
		if (!postoji) {
			removeRow(index);
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else if ((SortUtils.getLatCyrCollator().compare(sifra_porudzbine,
				((String) getValueAt(index, 0)).trim()) != 0)
				|| (SortUtils.getLatCyrCollator().compare(sifra_robe,
						(String) getValueAt(index, 1)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(nazivR,
						(String) getValueAt(index, 2)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(naruceno,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(poslato,
						(String) getValueAt(index, 4)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(ostalo,
						(String) getValueAt(index, 5)) != 0)) {

			setValueAt(sifra_porudzbine, index, 0);
			setValueAt(sifra_robe, index, 1);
			setValueAt(nazivR, index, 2);
			setValueAt(naruceno, index, 3);
			setValueAt(poslato, index, 4);
			setValueAt(ostalo, index, 5);			
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_CHANGED;
		}
		rset.close();
		selectStmt.close();
		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_READ_COMMITTED);
		if (errorMsg != "") {
			DBConnection.getConnection().commit();
			throw new SQLException(errorMsg, "", CUSTOM_ERROR_CODE);
		}
	}

	@Override
	public void search(String[] params) throws SQLException {
		whereStmt = " WHERE narucena_roba.sifra_porudzbine LIKE '%" + params[0] + "%' AND "
				+ "narucena_roba.sifra_robe LIKE '%" + params[1] + "%' AND "				
				+ "komada_naruceno LIKE '%" + params[2] + "%' AND "
				+ "komada_poslato LIKE '%" + params[3] + "%' AND "
				+ "komada_ostalo LIKE '%" + params[3] + "%'";
		fillData(basicQuery1 + whereStmt + orderBy);

	}

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();		
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_porudzbine = rset.getString("sifraPorudzbine");
			String sifra_robe = rset.getString("sifraRobe");
			String nazivR = rset.getString("NAZIV_ROBE");
			String naruceno = rset.getString("KOMADA_NARUCENO");
			String poslato = rset.getString("KOMADA_POSLATO");
			String ostalo = rset.getString("KOMADA_OSTALO");			

			addRow(new String[] { sifra_porudzbine, sifra_robe, nazivR, naruceno,
					poslato, ostalo });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	@Override
	public void deleteRow(int index) throws SQLException {

		checkRow(index);

		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"DELETE FROM porudzbina WHERE sifra_porudzbine = ? and sifra_robe = ?");
		String sifraP = (String) getValueAt(index, 0);
		String sifraR = (String) getValueAt(index, 1);
		stmt.setString(1, sifraP);
		stmt.setString(2, sifraR);
		// Brisanje iz baze
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i brisanje iz TableModel-a
			removeRow(index);
			fireTableDataChanged();
		}
	}

	@Override
	public int insertRow(String[] params) throws SQLException {
		int retVal = 0;
		PreparedStatement stmt = DBConnection
				.getConnection()
				.prepareStatement(
						"INSERT INTO narucena_roba (sifra_porudzbine, sifra_robe, komada_naruceno, komada_poslato, komada_ostalo) VALUES (?, ?, ?, ?, ?, ?)");
		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[3]);
		stmt.setString(4, params[4]);
		stmt.setString(5, params[5]);		

		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		// Unos sloga u bazu
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i unos u TableModel
			retVal = sortedInsert(params);
			fireTableRowsInserted(retVal, retVal);

		}
		return retVal;
	}

	@Override
	public void updateRow(int index, String[] params) throws SQLException {
		checkRow(index);

		String sifra_porudzbine = (String) getValueAt(index, 0);
		String sifra_robe = (String) getValueAt(index, 1);

		PreparedStatement stmt = DBConnection
				.getConnection()
				.prepareStatement(
						"UPDATE porudzbina SET komada_naruceno = ?, komada_poslato = ?, komada_ostalo = ? WHERE sifra_porudzbine = ? and sifra_robe = ?");

		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);				
		stmt.setString(4, sifra_porudzbine);
		stmt.setString(5, sifra_robe);
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		setValueAt(params[0], index, 1);
		setValueAt(params[1], index, 2);
		setValueAt(params[2], index, 3);		
		fireTableDataChanged();
	}

}
