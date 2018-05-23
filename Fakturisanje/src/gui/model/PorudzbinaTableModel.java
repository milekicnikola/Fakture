package gui.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.SortUtils;
import databaseConnection.DBConnection;

public class PorudzbinaTableModel extends StandardTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PorudzbinaTableModel(Object[] colName, int rowCount) {
		super(colName, rowCount);
		basicQuery = "SELECT sifra_porudzbine, porudzbina.sifra_magacina as sifraMagacina, naziv_magacina, porudzbina.korisnicko_ime as korisnickoIme, porudzbina.pib_kupca as sifraKupca, naziv_kupca, datum_porudzbine FROM porudzbina JOIN magacin ON porudzbina.sifra_magacina = magacin.sifra_magacina JOIN korisnik ON porudzbina.korisnicko_ime = korisnik.korisnicko_ime JOIN kupci ON porudzbina.pib_kupca = kupci.pib";
		orderBy = " ORDER BY sifra_porudzbine";
	}

	// Dodate konstante za potrebe izvestavanja korisnika o greskama
	// Dodata metoda za proveru i zakljucavanje tekuceg reda
	@Override
	public void checkRow(int index) throws SQLException {

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection.getConnection()
				.prepareStatement(basicQuery + " where sifra_porudzbine = ?");

		String sifra = (String) getValueAt(index, 0);
		selectStmt.setString(1, sifra);

		ResultSet rset = selectStmt.executeQuery();

		String sifra_porudzbine = "", sifraM = "", nazivM = "", ime = "", sifraK = "", nazivK = "", datum = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_porudzbine = rset.getString("SIFRA_PORUDZBINE").trim();
			sifraM = rset.getString("sifraMagacina").trim();
			nazivM = rset.getString("NAZIV_MAGACINA").trim();
			ime = rset.getString("korisnickoIme").trim();
			sifraK = rset.getString("sifraKupca");
			nazivK = rset.getString("NAZIV_KUPCA");
			datum = rset.getString("DATUM_PORUDZBINE");
			postoji = true;
		}
		if (!postoji) {
			removeRow(index);
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else if ((SortUtils.getLatCyrCollator().compare(sifra_porudzbine,
				((String) getValueAt(index, 0)).trim()) != 0)
				|| (SortUtils.getLatCyrCollator().compare(sifraM,
						(String) getValueAt(index, 1)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(nazivM,
						(String) getValueAt(index, 2)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(ime,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(sifraK,
						(String) getValueAt(index, 4)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(nazivK,
						(String) getValueAt(index, 5)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(datum,
						(String) getValueAt(index, 6)) != 0)) {

			setValueAt(sifra_porudzbine, index, 0);
			setValueAt(sifraM, index, 1);
			setValueAt(nazivM, index, 2);
			setValueAt(ime, index, 3);
			setValueAt(sifraK, index, 4);
			setValueAt(nazivK, index, 5);
			setValueAt(datum, index, 6);
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
		whereStmt = " WHERE sifra_porudzbine LIKE '%" + params[0] + "%' AND "
				+ "porudzbina.sifra_magacina LIKE '%" + params[1] + "%' AND "				
				+ "porudzbina.korisnicko_ime LIKE '%" + params[2] + "%' AND "
				+ "porudzbina.pib_kupca LIKE '%" + params[3] + "%' AND "
				+ "datum_porudzbine LIKE '%" + params[4] + "%'";
		fillData(basicQuery + whereStmt + orderBy);

	}

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_porudzbine = rset.getString("SIFRA_PORUDZBINE");
			String sifraM = rset.getString("sifraMagacina");
			String nazivM = rset.getString("NAZIV_MAGACINA");
			String ime = rset.getString("korisnickoIme");
			String sifraK = rset.getString("sifraKupca");
			String nazivK = rset.getString("NAZIV_KUPCA");
			String datum = rset.getString("DATUM_PORUDZBINE");

			addRow(new String[] { sifra_porudzbine, sifraM, nazivM, ime, sifraK,
					nazivK, datum });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	@Override
	public void deleteRow(int index) throws SQLException {

		checkRow(index);

		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"DELETE FROM porudzbina WHERE sifra_porudzbine = ? ");
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
						"INSERT INTO porudzbina (sifra_porudzbine, porudzbina.sifra_magacina, porudzbina.korisnicko_ime, porudzbina.pib_kupca, datum_porudzbine) VALUES (?, ?, ?, ?, ?)");
		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[3]);
		stmt.setString(4, params[4]);
		stmt.setString(5, params[6]);		

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

		PreparedStatement stmt = DBConnection
				.getConnection()
				.prepareStatement(
						"UPDATE porudzbina SET sifra_magacina = ?, korisnicko_ime = ?, pib_kupca = ?, datum_porudzbine = ? WHERE sifra_porudzbine = ?");

		stmt.setString(1, params[0]);
		stmt.setString(2, params[2]);
		stmt.setString(3, params[3]);
		stmt.setString(4, params[5]);		
		stmt.setString(5, sifra_porudzbine);
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