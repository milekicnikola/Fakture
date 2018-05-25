package gui.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.SortUtils;
import databaseConnection.DBConnection;

public class OtpremljenaTableModel extends StandardTableModel {

	public String basicQuery1;
	private String otpremnica = "";
	String poslata = "";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OtpremljenaTableModel(Object[] colName, int rowCount, String where,
			String otpremnica1, String poslata1, String bQ) {
		super(colName, rowCount);
		
		poslata = poslata1;

		if (poslata.equals("ne")) {

			basicQuery = "SELECT fakturisana_roba.sifra_robe as sifraRobe, naziv_robe, fakturisana_roba.sifra_porudzbine as sifraPorudzbine, fakturisana_roba.datum_isporuke as datumIsporuke, fakturisana_roba.sifra_fakture as sifraFakture, komada_fakturisano, opis, status FROM fakturisana_roba JOIN narucena_roba ON fakturisana_roba.sifra_robe = narucena_roba.sifra_robe AND fakturisana_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND fakturisana_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON narucena_roba.sifra_robe = roba.sifra_robe"
					+ where;
			basicQuery1 = "SELECT fakturisana_roba.sifra_robe as sifraRobe, naziv_robe, fakturisana_roba.sifra_porudzbine as sifraPorudzbine, fakturisana_roba.datum_isporuke as datumIsporuke, fakturisana_roba.sifra_fakture as sifraFakture, komada_fakturisano, opis, status FROM fakturisana_roba JOIN narucena_roba ON fakturisana_roba.sifra_robe = narucena_roba.sifra_robe AND fakturisana_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND fakturisana_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON narucena_roba.sifra_robe = roba.sifra_robe";
			orderBy = " ORDER BY fakturisana_roba.sifra_fakture";
		} else {
			basicQuery = where;
			basicQuery1 = bQ;
		}

		otpremnica = otpremnica1;
	}

	// Dodate konstante za potrebe izvestavanja korisnika o greskama
	// Dodata metoda za proveru i zakljucavanje tekuceg reda
	@Override
	public void checkRow(int index) throws SQLException {
		
		
		String where = "";
		
		if (poslata.equals("ne"))
			where = " where fakturisana_roba.sifra_robe = ? and fakturisana_roba.sifra_porudzbine = ? and fakturisana_roba.datum_isporuke = ? and fakturisana_roba.sifra_fakture = ?";
		else 
			where = " where otpremljena_roba.sifra_robe = ? and otpremljena_roba.sifra_porudzbine = ? and otpremeljena_roba.datum_isporuke = ? and otpremljena_roba.sifra_fakture = ?";

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection
				.getConnection()
				.prepareStatement(
						basicQuery1
								+ where);

		String f = (String) getValueAt(index, 0);
		String r = (String) getValueAt(index, 1);
		String p = (String) getValueAt(index, 3);
		String d = (String) getValueAt(index, 4);

		selectStmt.setString(1, r);
		selectStmt.setString(2, p);
		selectStmt.setString(3, d);
		selectStmt.setString(4, f);

		ResultSet rset = selectStmt.executeQuery();

		String sifra_robe = "", naziv_robe = "", sifra_porudzbine = "", datum = "", faktura = "", komada = "", opis = "", status = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_robe = rset.getString("sifraRobe").trim();
			naziv_robe = rset.getString("NAZIV_ROBE").trim();
			sifra_porudzbine = rset.getString("sifraPorudzbine").trim();
			datum = rset.getString("datumIsporuke");
			faktura = rset.getString("sifraFakture").trim();
			komada = rset.getString("KOMADA_FAKTURISANO");
			opis = rset.getString("OPIS");
			status = rset.getString("STATUS");
			postoji = true;
		}
		if (!postoji) {
			removeRow(index);
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else if ((SortUtils.getLatCyrCollator().compare(faktura,
				(String) getValueAt(index, 0)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(sifra_robe,
						((String) getValueAt(index, 1)).trim()) != 0)
				|| (SortUtils.getLatCyrCollator().compare(naziv_robe,
						(String) getValueAt(index, 2)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(sifra_porudzbine,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(datum,
						(String) getValueAt(index, 4)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(komada,
						(String) getValueAt(index, 5)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(opis,
						(String) getValueAt(index, 6)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(status,
						(String) getValueAt(index, 7)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(otpremnica,
						(String) getValueAt(index, 8)) != 0)) {

			setValueAt(faktura, index, 0);
			setValueAt(sifra_robe, index, 1);
			setValueAt(naziv_robe, index, 2);
			setValueAt(sifra_porudzbine, index, 3);
			setValueAt(datum, index, 4);
			setValueAt(komada, index, 5);
			setValueAt(opis, index, 6);
			setValueAt(status, index, 7);
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

/*	@Override
	public void search(String[] params) throws SQLException {
		whereStmt = " WHERE fakturisana_roba.sifra_robe LIKE '%" + params[0]
				+ "%' AND " + "fakturisana_roba.sifra_porudzbine LIKE '%"
				+ params[1] + "%' AND "
				+ "fakturisana_roba.datum_isporuke LIKE '%" + params[2]
				+ "%' AND " + "fakturisana_roba.sifra_fakture LIKE '%"
				+ params[3] + "%' AND " + "komada_fakturisano LIKE '%"
				+ params[4] + "%' AND " + "opis LIKE '%" + params[5]
				+ "%' AND " + "status LIKE '%" + params[6] + "%'";
		fillData(basicQuery1 + whereStmt + orderBy);

	}*/

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_robe = rset.getString("sifraRobe");
			String naziv_robe = rset.getString("NAZIV_ROBE");
			String sifra_porudzbine = rset.getString("sifraPorudzbine");
			String datum = rset.getString("datumIsporuke");
			String faktura = rset.getString("sifraFakture");
			String komada = rset.getString("KOMADA_FAKTURISANO");
			String opis = rset.getString("OPIS");
			String status = rset.getString("STATUS");

			addRow(new String[] { faktura, sifra_robe, naziv_robe,
					sifra_porudzbine, datum, komada, opis, status, otpremnica });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	/*
	 * @Override public void deleteRow(int index) throws SQLException {
	 * 
	 * checkRow(index);
	 * 
	 * PreparedStatement stmt = DBConnection .getConnection() .prepareStatement(
	 * "DELETE FROM fakturisana_roba WHERE sifra_robe = ? and sifra_porudzbine = ? and datum_isporuke = ? and sifra_fakture = ?"
	 * );
	 * 
	 * String faktura = (String) getValueAt(index, 0); String roba = (String)
	 * getValueAt(index, 1); String porudzbina = (String) getValueAt(index, 3);
	 * String datum = (String) getValueAt(index, 4);
	 * 
	 * stmt.setString(1, roba); stmt.setString(2, porudzbina); stmt.setString(3,
	 * datum); stmt.setString(4, faktura); // Brisanje iz baze int rowsAffected
	 * = stmt.executeUpdate(); stmt.close();
	 * DBConnection.getConnection().commit(); if (rowsAffected > 0) { // i
	 * brisanje iz TableModel-a removeRow(index); fireTableDataChanged(); } }
	 */

	/*
	 * @Override public int insertRow(String[] params) throws SQLException { int
	 * retVal = 0; PreparedStatement stmt = DBConnection .getConnection()
	 * .prepareStatement(
	 * "INSERT INTO fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, status) VALUES (?, ?, ?, ?, ?, ?, ?)"
	 * ); stmt.setString(1, params[1]); stmt.setString(2, params[3]);
	 * stmt.setString(3, params[4]); stmt.setString(4, params[0]);
	 * stmt.setString(5, params[5]); stmt.setString(6, params[6]);
	 * stmt.setString(7, params[7]);
	 * 
	 * int rowsAffected = stmt.executeUpdate(); stmt.close(); // Unos sloga u
	 * bazu DBConnection.getConnection().commit(); if (rowsAffected > 0) { // i
	 * unos u TableModel retVal = sortedInsert(params);
	 * fireTableRowsInserted(retVal, retVal);
	 * 
	 * } return retVal; }
	 */

	/*
	 * @Override public void updateRow(int index, String[] params) throws
	 * SQLException {
	 * 
	 * checkRow(index);
	 * 
	 * String faktura = (String) getValueAt(index, 0); String sifra_robe =
	 * (String) getValueAt(index, 1); String sifra_porudzbine = (String)
	 * getValueAt(index, 3); String datum = (String) getValueAt(index, 4);
	 * 
	 * PreparedStatement stmt = DBConnection .getConnection() .prepareStatement(
	 * "UPDATE fakturisana_roba SET komada_fakturisano = ?, opis = ? WHERE sifra_robe = ? and sifra_porudzbine = ? and datum_isporuke = ? and sifra_fakture = ?"
	 * );
	 * 
	 * stmt.setString(1, params[0]); stmt.setString(2, params[1]);
	 * stmt.setString(3, sifra_robe); stmt.setString(4, sifra_porudzbine);
	 * stmt.setString(5, datum); stmt.setString(6, faktura);
	 * stmt.executeUpdate(); stmt.close();
	 * DBConnection.getConnection().commit(); setValueAt(params[0], index, 5);
	 * setValueAt(params[1], index, 6); setValueAt(params[2], index, 7);
	 * fireTableDataChanged(); }
	 */

}
