package gui.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import databaseConnection.DBConnection;

public class PoslateStavkeTableModel extends StandardTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PoslateStavkeTableModel(Object[] colName, int rowCount) {
		super(colName, rowCount);
		basicQuery = "SELECT otpremljena_roba.sifra_robe as sifraRobe, naziv_robe, interni_naziv, otpremljena_roba.sifra_otpremnice as sifraOtpremnice, otpremljena_roba.sifra_porudzbine as sifraPorudzbine, otpremljena_roba.sifra_fakture as sifraFakture, otpremljena_roba.datum_isporuke as datumIsporuke, komada_naruceno, komada_fakturisano, komada_ostalo, ko_radi, narucena_roba.korisnicko_ime as korisnickoIme FROM otpremljena_roba JOIN otpremnica ON otpremljena_roba.sifra_otpremnice = otpremnica.sifra_otpremnice JOIN fakturisana_roba ON otpremljena_roba.sifra_robe = fakturisana_roba.sifra_robe AND otpremljena_roba.sifra_porudzbine = fakturisana_roba.sifra_porudzbine AND otpremljena_roba.datum_isporuke = fakturisana_roba.datum_isporuke AND otpremljena_roba.sifra_fakture = fakturisana_roba.sifra_fakture JOIN narucena_roba ON otpremljena_roba.sifra_robe = narucena_roba.sifra_robe AND otpremljena_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND otpremljena_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN porudzbina ON narucena_roba.sifra_porudzbine = porudzbina.sifra_porudzbine JOIN roba ON otpremljena_roba.sifra_robe = roba.sifra_robe JOIN korisnik ON narucena_roba.korisnicko_ime = korisnik.korisnicko_ime WHERE otpremljena_roba.status_robe = 'otpremljena'";
		orderBy = " ORDER BY ko_radi, otpremljena_roba.sifra_robe";
	}

	// Dodate konstante za potrebe izvestavanja korisnika o greskama
	// Dodata metoda za proveru i zakljucavanje tekuceg reda
	/*
	 * @Override public void checkRow(int index) throws SQLException {
	 * 
	 * DBConnection.getConnection().setTransactionIsolation(
	 * Connection.TRANSACTION_REPEATABLE_READ); PreparedStatement selectStmt =
	 * DBConnection .getConnection() .prepareStatement( basicQuery1 +
	 * " where narucena_roba.sifra_porudzbine = ? and narucena_roba.sifra_robe = ? and datum_isporuke = ?"
	 * );
	 * 
	 * String sifraR = (String) getValueAt(index, 0); String sifraP = (String)
	 * getValueAt(index, 3); String datum = (String) getValueAt(index, 4);
	 * 
	 * selectStmt.setString(1, sifraR); selectStmt.setString(2, sifraP);
	 * selectStmt.setString(3, datum);
	 * 
	 * ResultSet rset = selectStmt.executeQuery();
	 * 
	 * String sifra_porudzbine = "", sifra_robe = "", nazivR = "", interni = "",
	 * naruceno = "", poslato = "", ostalo = "", datum_isporuke = "", ko = "",
	 * korisnik = ""; Boolean postoji = false; String errorMsg = ""; while
	 * (rset.next()) { sifra_robe = rset.getString("sifraRobe").trim(); nazivR =
	 * rset.getString("NAZIV_ROBE").trim(); interni =
	 * rset.getString("INTERNI_NAZIV").trim(); sifra_porudzbine =
	 * rset.getString("sifraPorudzbine").trim(); datum_isporuke =
	 * rset.getString("DATUM_ISPORUKE"); naruceno =
	 * rset.getString("KOMADA_NARUCENO").trim(); poslato =
	 * rset.getString("KOMADA_POSLATO"); ostalo =
	 * rset.getString("KOMADA_OSTALO"); ko = rset.getString("KO_RADI"); korisnik
	 * = rset.getString("korisnickoIme"); postoji = true; } if (!postoji) {
	 * removeRow(index); fireTableDataChanged(); errorMsg =
	 * ERROR_RECORD_WAS_DELETED; } else if
	 * ((SortUtils.getLatCyrCollator().compare(sifra_robe, ((String)
	 * getValueAt(index, 0)).trim()) != 0) ||
	 * (SortUtils.getLatCyrCollator().compare(nazivR, (String) getValueAt(index,
	 * 1)) != 0) || (SortUtils.getLatCyrCollator().compare(interni, (String)
	 * getValueAt(index, 2)) != 0) ||
	 * (SortUtils.getLatCyrCollator().compare(sifra_porudzbine, (String)
	 * getValueAt(index, 3)) != 0) ||
	 * (SortUtils.getLatCyrCollator().compare(datum, (String) getValueAt(index,
	 * 4)) != 0) || (SortUtils.getLatCyrCollator().compare(korisnik, (String)
	 * getValueAt(index, 5)) != 0) ||
	 * (SortUtils.getLatCyrCollator().compare(naruceno, (String)
	 * getValueAt(index, 6)) != 0) ||
	 * (SortUtils.getLatCyrCollator().compare(poslato, (String)
	 * getValueAt(index, 7)) != 0) ||
	 * (SortUtils.getLatCyrCollator().compare(ostalo, (String) getValueAt(index,
	 * 8)) != 0) || (SortUtils.getLatCyrCollator().compare(ko, (String)
	 * getValueAt(index, 9)) != 0)) {
	 * 
	 * setValueAt(sifra_robe, index, 0); setValueAt(nazivR, index, 1);
	 * setValueAt(interni, index, 2); setValueAt(sifra_porudzbine, index, 3);
	 * setValueAt(datum_isporuke, index, 4); setValueAt(korisnik, index, 5);
	 * setValueAt(naruceno, index, 6); setValueAt(poslato, index, 7);
	 * setValueAt(ostalo, index, 8); setValueAt(ko, index, 9);
	 * fireTableDataChanged(); errorMsg = ERROR_RECORD_WAS_CHANGED; }
	 * rset.close(); selectStmt.close();
	 * DBConnection.getConnection().setTransactionIsolation(
	 * Connection.TRANSACTION_READ_COMMITTED); if (errorMsg != "") {
	 * DBConnection.getConnection().commit(); throw new SQLException(errorMsg,
	 * "", CUSTOM_ERROR_CODE); } }
	 */

	/*
	 * @Override public void search(String[] params) throws SQLException {
	 * whereStmt = " WHERE narucena_roba.sifra_robe LIKE '%" + params[0] +
	 * "%' AND " + "narucena_roba.sifra_porudzbine LIKE '%" + params[1] +
	 * "%' AND " + "komada_naruceno LIKE '%" + params[2] + "%' AND " +
	 * "komada_poslato LIKE '%" + params[3] + "%' AND " +
	 * "komada_ostalo LIKE '%" + params[4] + "%' AND " +
	 * "datum_isporuke LIKE '%" + params[5] + "%' AND " +
	 * "narucena_roba.korisnicko_ime LIKE '%" + params[6] + "%' AND " +
	 * "ko_radi LIKE '%" + params[7] + "%' AND komada_poslato > 0";
	 * fillData(basicQuery1 + whereStmt + orderBy);
	 * 
	 * }
	 */

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_robe = rset.getString("sifraRobe");
			String nazivR = rset.getString("NAZIV_ROBE");
			String interni = rset.getString("INTERNI_NAZIV");
			String sifra_porudzbine = rset.getString("sifraPorudzbine");
			String datum = rset.getString("datumIsporuke");
			String naruceno = rset.getString("KOMADA_NARUCENO");
			String fakturisano = rset.getString("KOMADA_FAKTURISANO");
			String ostalo = rset.getString("KOMADA_OSTALO");
			String ko = rset.getString("KO_RADI");
			String korisnik = rset.getString("korisnickoIme");
			String otpremnica = rset.getString("sifraOtpremnice");
			String faktura = rset.getString("sifraFakture");

			addRow(new String[] { sifra_robe, nazivR, interni,
					sifra_porudzbine, faktura, otpremnica, datum, korisnik, naruceno, fakturisano,
					ostalo, ko });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	@Override
	public void deleteRow(int index) throws SQLException {

		/*
		 * checkRow(index);
		 * 
		 * PreparedStatement stmt = DBConnection .getConnection()
		 * .prepareStatement(
		 * "DELETE FROM narucena_roba WHERE sifra_porudzbine = ? and sifra_robe = ? and datum_isporuke = ?"
		 * ); String sifraP = (String) getValueAt(index, 0); String sifraR =
		 * (String) getValueAt(index, 1); String datum = (String)
		 * getValueAt(index, 3);
		 * 
		 * stmt.setString(1, sifraP); stmt.setString(2, sifraR);
		 * stmt.setString(3, datum); // Brisanje iz baze int rowsAffected =
		 * stmt.executeUpdate(); stmt.close();
		 * DBConnection.getConnection().commit(); if (rowsAffected > 0) { // i
		 * brisanje iz TableModel-a removeRow(index); fireTableDataChanged(); }
		 */
	}

	/*
	 * @Override public int insertRow(String[] params) throws SQLException { int
	 * retVal = 0; PreparedStatement stmt = DBConnection .getConnection()
	 * .prepareStatement(
	 * "INSERT INTO narucena_roba (sifra_porudzbine, sifra_robe, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) VALUES (?, ?, ?, ?, ?, ?, ?)"
	 * ); stmt.setString(1, params[0]); stmt.setString(2, params[1]);
	 * stmt.setString(3, params[4]); stmt.setString(4, params[5]);
	 * stmt.setString(5, params[6]); stmt.setString(6, params[3]);
	 * stmt.setString(7, params[7]);
	 * 
	 * int rowsAffected = stmt.executeUpdate(); stmt.close(); // Unos sloga u
	 * bazu DBConnection.getConnection().commit(); if (rowsAffected > 0) { // i
	 * unos u TableModel retVal = sortedInsert(params);
	 * fireTableRowsInserted(retVal, retVal);
	 * 
	 * } return retVal; }
	 */

	@Override
	public void updateRow(int index, String[] params) throws SQLException {

		/*
		 * checkRow(index);
		 * 
		 * String sifra_porudzbine = (String) getValueAt(index, 0); String
		 * sifra_robe = (String) getValueAt(index, 1); String datum = (String)
		 * getValueAt(index, 3);
		 * 
		 * PreparedStatement stmt = DBConnection .getConnection()
		 * .prepareStatement(
		 * "UPDATE narucena_roba SET ko_radi = ? WHERE sifra_porudzbine = ? and sifra_robe = ? and datum_isporuke = ?"
		 * );
		 * 
		 * stmt.setString(1, params[0]); stmt.setString(2, sifra_porudzbine);
		 * stmt.setString(3, sifra_robe); stmt.setString(4, datum);
		 * stmt.executeUpdate(); stmt.close();
		 * DBConnection.getConnection().commit(); setValueAt(params[0], index,
		 * 7); fireTableDataChanged();
		 */
	}

}
