package gui.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.SortUtils;
import databaseConnection.DBConnection;

public class FakturisanaTableModel extends StandardTableModel {

	public String basicQuery1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FakturisanaTableModel(Object[] colName, int rowCount, String where) {
		super(colName, rowCount);
		basicQuery = "SELECT fakturisana_roba.sifra_robe as sifraRobe, naziv_robe, naziv_mere, fakturisana_roba.sifra_porudzbine as sifraPorudzbine, fakturisana_roba.datum_isporuke as datumIsporuke, fakturisana_roba.sifra_fakture as sifraFakture, komada_naruceno, komada_fakturisano, opis, komada_u_metru, status FROM fakturisana_roba JOIN narucena_roba ON fakturisana_roba.sifra_robe = narucena_roba.sifra_robe AND fakturisana_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND fakturisana_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN faktura ON fakturisana_roba.sifra_fakture = faktura.sifra_fakture JOIN roba ON narucena_roba.sifra_robe = roba.sifra_robe JOIN jedinica_mere ON roba.jedinica_mere = jedinica_mere.redni_broj"
				+ where;
		basicQuery1 = "SELECT fakturisana_roba.sifra_robe as sifraRobe, naziv_robe, naziv_mere, fakturisana_roba.sifra_porudzbine as sifraPorudzbine, fakturisana_roba.datum_isporuke as datumIsporuke, fakturisana_roba.sifra_fakture as sifraFakture, komada_naruceno, komada_fakturisano, opis, komada_u_metru, status FROM fakturisana_roba JOIN narucena_roba ON fakturisana_roba.sifra_robe = narucena_roba.sifra_robe AND fakturisana_roba.sifra_porudzbine = narucena_roba.sifra_porudzbine AND fakturisana_roba.datum_isporuke = narucena_roba.datum_isporuke JOIN faktura ON fakturisana_roba.sifra_fakture = faktura.sifra_fakture JOIN roba ON narucena_roba.sifra_robe = roba.sifra_robe JOIN jedinica_mere ON roba.jedinica_mere = jedinica_mere.redni_broj";
		orderBy = " ORDER BY fakturisana_roba.sifra_fakture";
	}

	// Dodate konstante za potrebe izvestavanja korisnika o greskama
	// Dodata metoda za proveru i zakljucavanje tekuceg reda
	@Override
	public void checkRow(int index) throws SQLException {

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection
				.getConnection()
				.prepareStatement(
						basicQuery1
								+ " where fakturisana_roba.sifra_robe = ? and fakturisana_roba.sifra_porudzbine = ? and fakturisana_roba.datum_isporuke = ? and fakturisana_roba.sifra_fakture = ? and fakturisana_roba.status != 'otpremljena'");

		String f = (String) getValueAt(index, 0);
		String r = (String) getValueAt(index, 1);
		String p = (String) getValueAt(index, 4);
		String d = (String) getValueAt(index, 5);

		selectStmt.setString(1, r);
		selectStmt.setString(2, p);
		selectStmt.setString(3, d);
		selectStmt.setString(4, f);

		ResultSet rset = selectStmt.executeQuery();

		String sifra_robe = "", naziv_robe = "", naziv_mere = "", sifra_porudzbine = "", datum = "", faktura = "", naruceno = "", komada = "", opis = "", komada_u_metru = "", status = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_robe = rset.getString("sifraRobe").trim();
			naziv_robe = rset.getString("NAZIV_ROBE").trim();
			naziv_mere = rset.getString("NAZIV_MERE").trim();
			sifra_porudzbine = rset.getString("sifraPorudzbine").trim();
			datum = rset.getString("datumIsporuke");
			faktura = rset.getString("sifraFakture").trim();
			naruceno = rset.getString("KOMADA_NARUCENO");
			komada = rset.getString("KOMADA_FAKTURISANO");
			opis = rset.getString("OPIS");
			komada_u_metru = rset.getString("KOMADA_U_METRU");
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
				|| (SortUtils.getLatCyrCollator().compare(naziv_mere,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(sifra_porudzbine,
						(String) getValueAt(index, 4)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(datum,
						(String) getValueAt(index, 5)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(naruceno,
						(String) getValueAt(index, 6)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(komada,
						(String) getValueAt(index, 7)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(opis,
						(String) getValueAt(index, 8)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(komada_u_metru,
						(String) getValueAt(index, 9)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(status,
						(String) getValueAt(index, 10)) != 0)) {

			setValueAt(faktura, index, 0);
			setValueAt(sifra_robe, index, 1);
			setValueAt(naziv_robe, index, 2);
			setValueAt(naziv_mere, index, 3);
			setValueAt(sifra_porudzbine, index, 4);
			setValueAt(datum, index, 5);
			setValueAt(naruceno, index, 6);
			setValueAt(komada, index, 7);
			setValueAt(opis, index, 8);
			setValueAt(komada_u_metru, index, 9);
			setValueAt(status, index, 10);
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
		whereStmt = " WHERE fakturisana_roba.sifra_robe LIKE '%" + params[0]
				+ "%' AND " + "fakturisana_roba.sifra_porudzbine LIKE '%"
				+ params[1] + "%' AND "
				+ "fakturisana_roba.datum_isporuke LIKE '%" + params[2]
				+ "%' AND " + "fakturisana_roba.sifra_fakture LIKE '%"
				+ params[3] + "%' AND " + "komada_naruceno LIKE '%" + params[4]
				+ "%' AND " + "komada_fakturisano LIKE '%" + params[5]
				+ "%' AND " + "opis LIKE '%" + params[6] + "%' AND "
				+ "komada_u_metru LIKE '%" + params[7] + "%' AND "
				+ "status LIKE '%" + params[8] + "%'";
		fillData(basicQuery1 + whereStmt + orderBy);

	}

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_robe = rset.getString("sifraRobe");
			String naziv_robe = rset.getString("NAZIV_ROBE");
			String naziv_mere = rset.getString("NAZIV_MERE");
			String sifra_porudzbine = rset.getString("sifraPorudzbine");
			String datum = rset.getString("datumIsporuke");
			String faktura = rset.getString("sifraFakture");
			String naruceno = rset.getString("KOMADA_NARUCENO");
			String komada = rset.getString("KOMADA_FAKTURISANO");
			String opis = rset.getString("OPIS");
			String komada_u_metru = rset.getString("KOMADA_U_METRU");
			String status = rset.getString("STATUS");

			addRow(new String[] { faktura, sifra_robe, naziv_robe, naziv_mere,
					sifra_porudzbine, datum, naruceno, komada, opis, komada_u_metru, status });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	@Override
	public void deleteRow(int index) throws SQLException {	
		
		checkRow(index);		

		PreparedStatement stmt = DBConnection
				.getConnection()
				.prepareStatement(
						"DELETE FROM fakturisana_roba WHERE sifra_robe = ? and sifra_porudzbine = ? and datum_isporuke = ? and sifra_fakture = ?");

		String faktura = (String) getValueAt(index, 0);
		String roba = (String) getValueAt(index, 1);
		String porudzbina = (String) getValueAt(index, 4);
		String datum = (String) getValueAt(index, 5);

		stmt.setString(1, roba);
		stmt.setString(2, porudzbina);
		stmt.setString(3, datum);
		stmt.setString(4, faktura);
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
						"INSERT INTO fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, komada_u_metru, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		stmt.setString(1, params[1]);
		stmt.setString(2, params[4]);
		stmt.setString(3, params[5]);
		stmt.setString(4, params[0]);
		stmt.setString(5, params[7]);
		stmt.setString(6, params[8]);
		stmt.setString(7, params[9]);
		stmt.setString(8, params[10]);

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

		String faktura = (String) getValueAt(index, 0);
		String sifra_robe = (String) getValueAt(index, 1);
		String sifra_porudzbine = (String) getValueAt(index, 4);
		String datum = (String) getValueAt(index, 5);

		PreparedStatement stmt = DBConnection
				.getConnection()
				.prepareStatement(
						"UPDATE fakturisana_roba SET komada_fakturisano = ?, opis = ?, komada_u_metru = ? WHERE sifra_robe = ? and sifra_porudzbine = ? and datum_isporuke = ? and sifra_fakture = ?");

		stmt.setString(1, params[1]);
		stmt.setString(2, params[2]);
		stmt.setString(3, params[3]);
		stmt.setString(4, sifra_robe);
		stmt.setString(5, sifra_porudzbine);
		stmt.setString(6, datum);
		stmt.setString(7, faktura);
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		setValueAt(params[0], index, 6);
		setValueAt(params[1], index, 7);
		setValueAt(params[2], index, 8);
		setValueAt(params[3], index, 9);
		setValueAt(params[4], index, 10);
		fireTableDataChanged();
	}

}