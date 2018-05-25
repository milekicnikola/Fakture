package gui.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.SortUtils;
import databaseConnection.DBConnection;

public class OtpremnicaTableModel extends StandardTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OtpremnicaTableModel(Object[] colName, int rowCount) {
		super(colName, rowCount);
		basicQuery = "SELECT sifra_otpremnice, otpremnica.korisnicko_ime as korisnickoIme, otpremnica.sifra_magacina as magacin, naziv_magacina, datum_otpremnice, transport, poslata_otpremnica FROM otpremnica JOIN korisnik ON otpremnica.korisnicko_ime = korisnik.korisnicko_ime JOIN magacin ON otpremnica.sifra_magacina = magacin.sifra_magacina";
		orderBy = " ORDER BY sifra_otpremnice";
	}

	// Dodate konstante za potrebe izvestavanja korisnika o greskama
	// Dodata metoda za proveru i zakljucavanje tekuceg reda
	@Override
	public void checkRow(int index) throws SQLException {

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection.getConnection()
				.prepareStatement(basicQuery + " where sifra_otpremnice = ?");

		String sifra = (String) getValueAt(index, 0);
		selectStmt.setString(1, sifra);

		ResultSet rset = selectStmt.executeQuery();

		String sifra_otpremnice = "", korisnik = "", sifraM = "", magacin = "", datum = "", transport = "", poslata = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_otpremnice = rset.getString("SIFRA_OTPREMNICE").trim();
			korisnik = rset.getString("korisnickoIme").trim();
			sifraM = rset.getString("magacin");
			magacin = rset.getString("NAZIV_MAGACINA");
			datum = rset.getString("DATUM_OTPREMNICE");
			transport = rset.getString("TRANSPORT");
			poslata = rset.getString("POSLATA_OTPREMNICA");
			postoji = true;
		}
		if (!postoji) {
			removeRow(index);
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else if ((SortUtils.getLatCyrCollator().compare(sifra_otpremnice,
				((String) getValueAt(index, 0)).trim()) != 0)
				|| (SortUtils.getLatCyrCollator().compare(korisnik,
						(String) getValueAt(index, 1)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(sifraM,
						(String) getValueAt(index, 2)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(magacin,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(datum,
						(String) getValueAt(index, 4)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(transport,
						(String) getValueAt(index, 5)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(poslata,
						(String) getValueAt(index, 6)) != 0)) {

			setValueAt(sifra_otpremnice, index, 0);
			setValueAt(korisnik, index, 1);
			setValueAt(sifraM, index, 2);
			setValueAt(magacin, index, 3);
			setValueAt(datum, index, 4);
			setValueAt(transport, index, 5);
			setValueAt(poslata, index, 6);
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
		whereStmt = " WHERE sifra_otpremnice LIKE '%" + params[0] + "%' AND "
				+ "otpremnica.korisnicko_ime LIKE '%" + params[1] + "%' AND "
				+ "otpremnica.sifra_magacina LIKE '%" + params[2] + "%' AND "
				+ "datum_otpremnice LIKE '%" + params[3] + "%' AND "
				+ "transport LIKE '%" + params[4] + "%' AND "
				+ "poslata LIKE '%" + params[5] + "%'";
		fillData(basicQuery + whereStmt + orderBy);

	}

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_otpremnice = rset.getString("SIFRA_OTPREMNICE");
			String korisnik = rset.getString("korisnickoIme");
			String sifraM = rset.getString("magacin");
			String magacin = rset.getString("NAZIV_MAGACINA");
			String datum = rset.getString("DATUM_OTPREMNICE");
			String transport = rset.getString("TRANSPORT");
			String poslata = rset.getString("POSLATA_OTPREMNICA");

			addRow(new String[] { sifra_otpremnice, korisnik, sifraM, magacin,
					datum, transport, poslata });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	@Override
	public void deleteRow(int index) throws SQLException {

		checkRow(index);

		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"DELETE FROM otpremnica WHERE sifra_otpremnice = ? ");
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
						"INSERT INTO otpremnica (sifra_otpremnice, otpremnica.korisnicko_ime, otpremnica.sifra_magacina, datum_otpremnice, transport, poslata_otpremnica) VALUES (?, ?, ?, ?, ?, ?)");
		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[2]);
		stmt.setString(4, params[4]);
		stmt.setString(5, params[5]);
		stmt.setString(6, params[6]);

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

		String sifra_otpremnice = (String) getValueAt(index, 0);

		PreparedStatement stmt = DBConnection
				.getConnection()
				.prepareStatement(
						"UPDATE otpremnica SET korisnicko_ime = ?, sifra_magacina = ?, datum_otpremnice = ?, transport = ? WHERE sifra_otpremnice = ?");

		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[3]);
		stmt.setString(4, params[4]);
		stmt.setString(5, sifra_otpremnice);
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
