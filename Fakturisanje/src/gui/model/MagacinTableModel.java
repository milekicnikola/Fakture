package gui.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.SortUtils;
import databaseConnection.DBConnection;

public class MagacinTableModel extends StandardTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MagacinTableModel(Object[] colName, int rowCount) {
		super(colName, rowCount);
		basicQuery = "SELECT sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina FROM magacin";
		orderBy = " ORDER BY sifra_magacina";
	}

	// Dodate konstante za potrebe izvestavanja korisnika o greskama
	// Dodata metoda za proveru i zakljucavanje tekuceg reda
	@Override
	public void checkRow(int index) throws SQLException {

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection.getConnection()
				.prepareStatement(basicQuery + " where sifra_magacina =?");

		String sifra = (String) getValueAt(index, 0);
		selectStmt.setString(1, sifra);

		ResultSet rset = selectStmt.executeQuery();

		String sifra_magacina = "", naziv = "", adresa = "", sef = "", telefon = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_magacina = rset.getString("SIFRA_MAGACINA").trim();
			naziv = rset.getString("NAZIV_MAGACINA").trim();
			adresa = rset.getString("ADRESA_MAGACINA").trim();
			sef = rset.getString("SEF_MAGACINA");
			telefon = rset.getString("TELEFON_MAGACINA");			
			postoji = true;
		}
		if (!postoji) {
			removeRow(index);
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else if ((SortUtils.getLatCyrCollator().compare(sifra_magacina,
				((String) getValueAt(index, 0)).trim()) != 0)
				|| (SortUtils.getLatCyrCollator().compare(naziv,
						(String) getValueAt(index, 1)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(adresa,
						(String) getValueAt(index, 2)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(sef,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(telefon,
						(String) getValueAt(index, 4)) != 0)) {
			
			setValueAt(sifra_magacina, index, 0);
			setValueAt(naziv, index, 1);
			setValueAt(adresa, index, 2);
			setValueAt(sef, index, 3);
			setValueAt(telefon, index, 4);						
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
		whereStmt = " WHERE sifra_magacina LIKE '%" + params[0] + "%' AND "
				+ "naziv_magacina LIKE '%" + params[1] + "%' AND "
				+ "adresa_magacina LIKE '%" + params[2] + "%' AND "
				+ "sef_magacina LIKE '%" + params[3] + "%' AND " + "telefon_magacina LIKE '%"
				+ params[4] + "%'";
		fillData(basicQuery + whereStmt + orderBy);		

	}

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_magacina = rset.getString("SIFRA_MAGACINA");
			String naziv = rset.getString("NAZIV_MAGACINA");
			String adresa = rset.getString("ADRESA_MAGACINA");
			String sef = rset.getString("SEF_MAGACINA");
			String telefon = rset.getString("TELEFON_MAGACINA");			

			addRow(new String[] { sifra_magacina, naziv, adresa, sef, telefon });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	@Override
	public void deleteRow(int index) throws SQLException {

		checkRow(index);

		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"DELETE FROM magacin WHERE sifra_magacina = ?");
		String sifra = (String) getValueAt(index, 0);
		stmt.setString(1, sifra);
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
						"INSERT INTO magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) VALUES (?, ?, ?, ?, ?)");
		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[3]);
		stmt.setString(5, params[4]);		

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

		String sifra_magacina = (String) getValueAt(index, 0);

		PreparedStatement stmt = DBConnection
				.getConnection()
				.prepareStatement(
						"UPDATE magacin SET naziv_magacina = ?, adresa_magacina = ?, sef_magacina = ?, telefon_magacina = ? WHERE sifra_magacina = ?");

		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[3]);		
		stmt.setString(5, sifra_magacina);
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		setValueAt(params[0], index, 1);
		setValueAt(params[1], index, 2);
		setValueAt(params[2], index, 3);
		setValueAt(params[3], index, 4);		
		fireTableDataChanged();
	}

}
