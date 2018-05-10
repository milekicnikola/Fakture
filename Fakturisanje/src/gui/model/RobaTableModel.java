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
		basicQuery = "SELECT sifra_robe, interna_sifra_robe, roba.sifra_magacina as sifraMagacina, naziv_robe, jedinica_mere_robe, komada_u_setu, tezina_robe, kvalitet_robe, cena_evri, cena_roni FROM roba JOIN magacin on roba.sifra_magacina = magacin.sifra_magacina";
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

		String sifra_robe = "", interna_sifra = "", sifra_magacina = "", naziv = "", jedinica_mere = "", komada_u_setu = "", tezina = "", kvalitet = "", evri = "", roni = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_robe = rset.getString("SIFRA_ROBE").trim();
			interna_sifra = rset.getString("INTERNA_SIFRA_ROBE").trim();
			sifra_magacina = rset.getString("sifraMagacina").trim();
			naziv = rset.getString("NAZIV_ROBE");
			jedinica_mere = rset.getString("JEDINICA_MERE_ROBE");
			komada_u_setu = rset.getString("KOMADA_U_SETU");
			tezina = rset.getString("TEZINA_ROBE");
			kvalitet = rset.getString("KVALITET_ROBE");
			evri = rset.getString("CENA_EVRI");
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
				|| (SortUtils.getLatCyrCollator().compare(sifra_magacina,
						(String) getValueAt(index, 2)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(naziv,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(jedinica_mere,
						(String) getValueAt(index, 4)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(komada_u_setu,
						(String) getValueAt(index, 5)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(tezina,
						(String) getValueAt(index, 6)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(kvalitet,
						(String) getValueAt(index, 7)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(evri,
						(String) getValueAt(index, 8)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(roni,
						(String) getValueAt(index, 9)) != 0)) {
			
			setValueAt(sifra_robe, index, 0);
			setValueAt(interna_sifra, index, 1);
			setValueAt(sifra_magacina, index, 2);
			setValueAt(naziv, index, 3);
			setValueAt(jedinica_mere, index, 4);
			setValueAt(komada_u_setu, index, 5);
			setValueAt(tezina, index, 6);
			setValueAt(kvalitet, index, 7);
			setValueAt(evri, index, 8);
			setValueAt(roni, index, 9);			
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
				+ "roba.sifra_magacina LIKE '%" + params[2] + "%' AND "
				+ "naziv_robe LIKE '%" + params[3] + "%' AND " + "jedinica_mere_robe LIKE '%"
				+ params[4] + "%' AND " + "komada_u_setu LIKE '%" + params[5]
				+ "%' AND " + "tezina_robe LIKE '%" + params[6] +  "%' AND "
				+ "kvalitet_robe LIKE '%" + params[7] + "%' AND "
				+ "cena_evri LIKE '%" + params[8] + "%' AND "
				+ "cena_roni LIKE '%" + params[9] + "%'";
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
			String sifra_magacina = rset.getString("sifraMagacina");
			String naziv = rset.getString("NAZIV_ROBE");
			String jedinica_mere = rset.getString("JEDINICA_MERE_ROBE");
			String komada_u_setu = rset.getString("KOMADA_U_SETU");
			String tezina = rset.getString("TEZINA_ROBE");
			String kvalitet = rset.getString("KVALITET_ROBE");
			String evri = rset.getString("CENA_EVRI");
			String roni = rset.getString("CENA_RONI");

			addRow(new String[] { sifra_robe, interna_sifra, sifra_magacina, naziv, jedinica_mere, 
					komada_u_setu, tezina, kvalitet, evri, roni });
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
						"INSERT INTO roba (sifra_robe, interna_sifra_robe, roba.sifra_magacina, naziv_robe, jedinica_mere_robe, komada_u_setu, tezina_robe, kvalitet_robe, cena_evri, cena_roni) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[3]);
		stmt.setString(5, params[4]);
		stmt.setString(6, params[5]);
		stmt.setString(7, params[6]);
		stmt.setString(8, params[7]);
		stmt.setString(9, params[8]);
		stmt.setString(10, params[9]);

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

		String sifra_robe = (String) getValueAt(index, 0);

		PreparedStatement stmt = DBConnection
				.getConnection()
				.prepareStatement(
						"UPDATE roba SET interna_sifra_robe = ?, roba.sifra_magacina = ?, naziv_robe = ?, jedinica_mere_robe = ?, komada_u_setu = ?, tezina_robe = ?, kvalitet_robe = ?, cena_evri = ?, cena_roni = ? WHERE sifra_robe = ?");

		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[3]);
		stmt.setString(5, params[4]);
		stmt.setString(6, params[5]);
		stmt.setString(7, params[6]);
		stmt.setString(8, params[7]);
		stmt.setString(9, params[8]);
		stmt.setString(10, sifra_robe);
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		setValueAt(params[0], index, 1);
		setValueAt(params[1], index, 2);
		setValueAt(params[2], index, 3);
		setValueAt(params[3], index, 4);
		setValueAt(params[4], index, 5);
		setValueAt(params[5], index, 6);
		setValueAt(params[6], index, 7);
		setValueAt(params[7], index, 8);
		setValueAt(params[8], index, 9);
		fireTableDataChanged();
	}

}
