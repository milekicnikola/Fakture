package gui.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.SortUtils;
import databaseConnection.DBConnection;

public class RobaTableModel extends StandardTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RobaTableModel(Object[] colName, int rowCount) {
		super(colName, rowCount);
		basicQuery = "SELECT sifra_robe, roba.jedinica_mere as jedinicaMere, roba.prevod as prevod, interna_sifra_robe, naziv_robe, naziv_mere, komada_u_setu, naziv_prevoda, tezina_robe, cena_roni FROM roba JOIN jedinica_mere ON roba.jedinica_mere = jedinica_mere.redni_broj JOIN prevod ON prevod = prevod.redni_broj";
		orderBy = " ORDER BY sifra_robe";
	}

	// Dodate konstante za potrebe izvestavanja korisnika o greskama
	// Dodata metoda za proveru i zakljucavanje tekuceg reda
	@Override
	public void checkRow(int index) throws SQLException {

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection.getConnection()
				.prepareStatement(basicQuery + " where sifra_robe =?");

		String sifra = (String) getValueAt(index, 0);
		selectStmt.setString(1, sifra);

		ResultSet rset = selectStmt.executeQuery();

		String sifra_robe = "", interna_sifra = "", naziv = "", naziv_mere = "", komada_u_setu = "", prevod = "", tezina = "", roni = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_robe = rset.getString("SIFRA_ROBE").trim();
			interna_sifra = rset.getString("INTERNA_SIFRA_ROBE").trim();
			naziv = rset.getString("NAZIV_ROBE");
			naziv_mere = rset.getString("NAZIV_MERE");
			komada_u_setu = rset.getString("KOMADA_U_SETU");
			prevod = rset.getString("NAZIV_PREVODA");
			tezina = rset.getString("TEZINA_ROBE");
			roni = rset.getString("CENA_RONI");
			postoji = true;
		}
		if (!postoji) {
			removeRow(index);
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else if ((SortUtils.getLatCyrCollator().compare(sifra_robe,
				((String) getValueAt(index, 0)).trim()) != 0)
				|| (SortUtils.getLatCyrCollator().compare(interna_sifra,
						(String) getValueAt(index, 1)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(naziv,
						(String) getValueAt(index, 2)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(naziv_mere,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(komada_u_setu,
						(String) getValueAt(index, 4)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(prevod,
						(String) getValueAt(index, 5)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(tezina,
						(String) getValueAt(index, 6)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(roni,
						(String) getValueAt(index, 7)) != 0)) {

			setValueAt(sifra_robe, index, 0);
			setValueAt(interna_sifra, index, 1);
			setValueAt(naziv, index, 2);
			setValueAt(naziv_mere, index, 3);
			setValueAt(komada_u_setu, index, 4);
			setValueAt(prevod, index, 5);			
			setValueAt(tezina, index, 6);
			setValueAt(roni, index, 7);
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
		whereStmt = " WHERE sifra_robe LIKE '%" + params[0] + "%' AND "
				+ "interna_sifra_robe LIKE '%" + params[1] + "%' AND "
				+ "naziv_robe LIKE '%" + params[2] + "%' AND "
				+ "komada_u_setu LIKE '%" + params[3] + "%' AND "
				+ "tezina_robe LIKE '%" + params[4] + "%' AND "				
				+ "cena_roni LIKE '%" + params[5] + "%'";
		fillData(basicQuery + whereStmt + orderBy);

	}

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_robe = rset.getString("SIFRA_ROBE");
			String interna_sifra = rset.getString("INTERNA_SIFRA_ROBE");
			String naziv = rset.getString("NAZIV_ROBE");
			String jedinica_mere = rset.getString("NAZIV_MERE");
			String komada_u_setu = rset.getString("KOMADA_U_SETU");
			String prevod = rset.getString("NAZIV_PREVODA");
			String tezina = rset.getString("TEZINA_ROBE");			
			String roni = rset.getString("CENA_RONI");

			addRow(new String[] { sifra_robe, interna_sifra, naziv,
					jedinica_mere, komada_u_setu, prevod, tezina, roni });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	@Override
	public void deleteRow(int index) throws SQLException {

		checkRow(index);

		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"DELETE FROM roba WHERE sifra_robe = ?");
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
						"INSERT INTO roba (sifra_robe, roba.jedinica_mere, roba.prevod, interna_sifra_robe, naziv_robe, komada_u_setu, tezina_robe, cena_roni) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[3]);
		stmt.setString(5, params[4]);
		stmt.setString(6, params[6]);
		stmt.setString(7, params[8]);
		stmt.setString(8, params[9]);		

		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		// Unos sloga u bazu
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i unos u TableModel
			String[] newParams = { params[0], params[3], params[4],
					params[5], params[6], params[7], params[8], params[9] };
			retVal = sortedInsert(newParams);
			fireTableRowsInserted(retVal, retVal);

		}
		return retVal;
	}

	@Override
	public void updateRow(int index, String[] params) throws SQLException {
		checkRow(index);

		String sifra_robe = (String) getValueAt(index, 0);

		PreparedStatement stmt = DBConnection
				.getConnection()
				.prepareStatement(
						"UPDATE roba SET roba.jedinica_mere = ?, roba.prevod = ?, interna_sifra_robe = ?, naziv_robe = ?, komada_u_setu = ?, tezina_robe = ?, cena_roni = ? WHERE sifra_robe = ?");

		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[3]);
		stmt.setString(5, params[5]);		
		stmt.setString(6, params[7]);
		stmt.setString(7, params[8]);
		stmt.setString(8, sifra_robe);
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		setValueAt(params[2], index, 1);
		setValueAt(params[3], index, 2);
		setValueAt(params[4], index, 3);
		setValueAt(params[5], index, 4);
		setValueAt(params[6], index, 5);
		setValueAt(params[7], index, 6);
		setValueAt(params[8], index, 7);		
		fireTableDataChanged();
	}

}
