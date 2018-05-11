package gui.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.SortUtils;
import databaseConnection.DBConnection;

public class KupciTableModel extends StandardTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KupciTableModel(Object[] colName, int rowCount) {
		super(colName, rowCount);
		basicQuery = "SELECT sifra_kupca, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca FROM kupci";
		orderBy = " ORDER BY sifra_kupca";
	}

	// Dodate konstante za potrebe izvestavanja korisnika o greskama
	// Dodata metoda za proveru i zakljucavanje tekuceg reda
	@Override
	public void checkRow(int index) throws SQLException {

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection.getConnection()
				.prepareStatement(basicQuery + " where sifra_kupca = ?");

		String sifra = (String) getValueAt(index, 0);
		selectStmt.setString(1, sifra);

		ResultSet rset = selectStmt.executeQuery();

		String sifra_kupca = "", naziv = "", naziv2 = "", adresa = "", grad = "", drzava = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_kupca = rset.getString("SIFRA_KUPCA").trim();
			naziv = rset.getString("NAZIV_KUPCA").trim();
			naziv2 = rset.getString("NAZIV_KUPCA2").trim();
			adresa = rset.getString("ADRESA_KUPCA").trim();
			grad = rset.getString("GRAD_KUPCA");
			drzava = rset.getString("DRZAVA_KUPCA");			
			postoji = true;
		}
		if (!postoji) {
			removeRow(index);
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else if ((SortUtils.getLatCyrCollator().compare(sifra_kupca,
				((String) getValueAt(index, 0)).trim()) != 0)
				|| (SortUtils.getLatCyrCollator().compare(naziv,
						(String) getValueAt(index, 1)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(naziv2,
						(String) getValueAt(index, 2)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(adresa,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(grad,
						(String) getValueAt(index, 4)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(drzava,
						(String) getValueAt(index, 5)) !=0)) {
			
			setValueAt(sifra_kupca, index, 0);
			setValueAt(naziv, index, 1);
			setValueAt(naziv2, index, 2);
			setValueAt(adresa, index, 3);
			setValueAt(grad, index, 4);						
			setValueAt(drzava, index, 5);
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
		whereStmt = " WHERE sifra_kupca LIKE '%" + params[0] + "%' AND "
				+ "naziv_kupca LIKE '%" + params[1] + "%' AND "
				+ "naziv_kupca2 LIKE '%" + params[2] + "%' AND "
				+ "adresa_kupca LIKE '%" + params[3] + "%' AND "
				+ "grad_kupca LIKE '%" + params[4] + "%' AND "
				+ "drzava_kupca LIKE '%" + params[5] + "%'";
		fillData(basicQuery + whereStmt + orderBy);		

	}

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_kupca = rset.getString("SIFRA_KUPCA");
			String naziv = rset.getString("NAZIV_KUPCA");
			String naziv2 = rset.getString("NAZIV_KUPCA2");
			String adresa = rset.getString("ADRESA_KUPCA");
			String grad = rset.getString("GRAD_KUPCA");
			String drzava = rset.getString("DRZAVA_KUPCA");			

			addRow(new String[] { sifra_kupca, naziv, naziv2, adresa, grad, drzava });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	@Override
	public void deleteRow(int index) throws SQLException {

		checkRow(index);

		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"DELETE FROM kupci WHERE sifra_kupca = ?");
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
						"INSERT INTO kupci (sifra_kupca, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) VALUES (?, ?, ?, ?, ?, ?)");
		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[3]);
		stmt.setString(5, params[4]);		
		stmt.setString(6, params[5]);

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
						"UPDATE kupci SET naziv_kupca = ?, naziv_kupca2 = ?, adresa_kupca = ?, grad_kupca = ?, drzava_kupca = ? WHERE sifra_kupca = ?");

		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[3]);
		stmt.setString(5, params[4]);
		stmt.setString(6, sifra_magacina);
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		setValueAt(params[0], index, 1);
		setValueAt(params[1], index, 2);
		setValueAt(params[2], index, 3);
		setValueAt(params[3], index, 4);
		setValueAt(params[4], index, 5);
		fireTableDataChanged();
	}

}
