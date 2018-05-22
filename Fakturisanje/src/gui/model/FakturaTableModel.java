package gui.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.SortUtils;
import databaseConnection.DBConnection;

public class FakturaTableModel extends StandardTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FakturaTableModel(Object[] colName, int rowCount) {
		super(colName, rowCount);
		basicQuery = "SELECT sifra_fakture, datum_fakture, faktura.korisnicko_ime as korisnickoIme, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe, poslata_faktura FROM faktura JOIN korisnik ON faktura.korisnicko_ime = korisnik.korisnicko_ime";
		orderBy = " ORDER BY sifra_fakture";
	}

	// Dodate konstante za potrebe izvestavanja korisnika o greskama
	// Dodata metoda za proveru i zakljucavanje tekuceg reda
	@Override
	public void checkRow(int index) throws SQLException {

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection.getConnection()
				.prepareStatement(basicQuery + " where sifra_fakture = ?");

		String sifra = (String) getValueAt(index, 0);
		selectStmt.setString(1, sifra);

		ResultSet rset = selectStmt.executeQuery();

		String sifra_fakture = "", datum = "", ime = "", paritet = "", bruto = "", neto = "", ukupno = "", poslata = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_fakture = rset.getString("SIFRA_FAKTURE").trim();
			datum = rset.getString("DATUM_FAKTURE").trim();
			/*
			 * sifraK = rset.getString("sifraKupca").trim(); nazivK =
			 * rset.getString("NAZIV_KUPCA").trim();
			 */
			ime = rset.getString("korisnickoIme").trim();
			paritet = rset.getString("PARITET_FAKTURE");
			bruto = rset.getString("BRUTO_FAKTURE");
			neto = rset.getString("NETO_FAKTURE");
			ukupno = rset.getString("UKUPNO_KOMADA_ROBE");
			poslata = rset.getString("POSLATA_FAKTURA");
			postoji = true;
		}
		if (!postoji) {
			removeRow(index);
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else if ((SortUtils.getLatCyrCollator().compare(sifra_fakture,
				((String) getValueAt(index, 0)).trim()) != 0)
				|| (SortUtils.getLatCyrCollator().compare(datum,
						(String) getValueAt(index, 1)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(ime,
						(String) getValueAt(index, 2)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(paritet,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(bruto,
						(String) getValueAt(index, 4)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(neto,
						(String) getValueAt(index, 5)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(ukupno,
						(String) getValueAt(index, 6)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(poslata,
						(String) getValueAt(index, 7)) != 0)) {

			setValueAt(sifra_fakture, index, 0);
			setValueAt(datum, index, 1);
			setValueAt(ime, index, 2);
			setValueAt(paritet, index, 3);
			setValueAt(bruto, index, 4);
			setValueAt(neto, index, 5);
			setValueAt(ukupno, index, 6);
			setValueAt(poslata, index, 7);
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
		whereStmt = " WHERE sifra_fakture LIKE '%" + params[0] + "%' AND "
				+ "datum_fakture LIKE '%" + params[1] + "%' AND "
				+ "faktura.korisnicko_ime LIKE '%" + params[2] + "%' AND "
				+ "paritet_fakture LIKE '%" + params[3] + "%' AND "
				+ "bruto_fakture LIKE '%" + params[4] + "%' AND "
				+ "neto_fakture LIKE '%" + params[5] + "%' AND "
				+ "ukupno_komada_robe LIKE '%" + params[6] + "%' AND "
				+ "poslata_faktura LIKE '%" + params[7] + "%'";
		fillData(basicQuery + whereStmt + orderBy);

	}

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_fakture = rset.getString("SIFRA_FAKTURE");
			String datum = rset.getString("DATUM_FAKTURE");
			/*
			 * String sifraK = rset.getString("sifraKupca"); String nazivK =
			 * rset.getString("NAZIV_KUPCA");
			 */
			String ime = rset.getString("korisnickoIme");
			String paritet = rset.getString("PARITET_FAKTURE");
			String bruto = rset.getString("BRUTO_FAKTURE");
			String neto = rset.getString("NETO_FAKTURE");
			String ukupno = rset.getString("UKUPNO_KOMADA_ROBE");
			String poslata = rset.getString("POSLATA_FAKTURA");

			addRow(new String[] { sifra_fakture, datum, ime, paritet, bruto,
					neto, ukupno, poslata });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	@Override
	public void deleteRow(int index) throws SQLException {

		checkRow(index);

		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"DELETE FROM faktura WHERE sifra_fakture = ? ");
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
						"INSERT INTO faktura (sifra_fakture, datum_fakture, faktura.korisnicko_ime, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe, poslata_faktura) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[3]);
		stmt.setString(5, params[4]);
		stmt.setString(6, params[5]);
		stmt.setString(7, params[6]);
		stmt.setString(8, "ne");		

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

		String sifra_fakture = (String) getValueAt(index, 0);

		PreparedStatement stmt = DBConnection
				.getConnection()
				.prepareStatement(
						"UPDATE faktura SET datum_fakture = ?, korisnicko_ime = ?, paritet_fakture = ?, bruto_fakture = ?, neto_fakture = ?, ukupno_komada_robe = ? WHERE sifra_fakture = ?");

		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[3]);
		stmt.setString(5, params[4]);
		stmt.setString(6, params[5]);
		stmt.setString(7, sifra_fakture);
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		setValueAt(params[0], index, 1);
		setValueAt(params[1], index, 2);
		setValueAt(params[2], index, 3);
		setValueAt(params[3], index, 4);
		setValueAt(params[4], index, 5);
		setValueAt(params[5], index, 6);
		fireTableDataChanged();
	}

}
