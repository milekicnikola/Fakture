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
		basicQuery = "SELECT sifra_otpremnice, otpremnica.sifra_magacina as sifraMagacina, naziv_magacina, otpremnica.sifra_fakture as sifraFakture, datum_fakture, transport_fakture, poslata_otpremnica FROM otpremnica JOIN magacin ON otpremnica.sifra_magacina = magacin.sifra_magacina JOIN faktura ON otpremnica.sifra_fakture = faktura.sifra_fakture";
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

		String sifra_otpremnice = "", sifraM = "", nazivM = "", sifraF = "", datum = "", transport = "", poslata = "";
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			sifra_otpremnice = rset.getString("SIFRA_OTPREMNICE").trim();			
			sifraM = rset.getString("sifraMagacina");
			nazivM = rset.getString("NAZIV_MAGACINA");
			sifraF = rset.getString("sifraFakture");
			datum = rset.getString("DATUM_FAKTURE");
			transport = rset.getString("TRANSPORT_FAKTURE");
			poslata = rset.getString("POSLATA_OTPREMNICA");
			postoji = true;
		}
		if (!postoji) {
			removeRow(index);
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else if ((SortUtils.getLatCyrCollator().compare(sifra_otpremnice,
				((String) getValueAt(index, 0)).trim()) != 0)
				|| (SortUtils.getLatCyrCollator().compare(sifraM,
						(String) getValueAt(index, 1)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(nazivM,
						(String) getValueAt(index, 2)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(sifraF,
						(String) getValueAt(index, 3)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(datum,
						(String) getValueAt(index, 4)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(transport,
						(String) getValueAt(index, 5)) != 0)
				|| (SortUtils.getLatCyrCollator().compare(poslata,
						(String) getValueAt(index, 6)) != 0)) {

			setValueAt(sifra_otpremnice, index, 0);			
			setValueAt(sifraM, index, 1);
			setValueAt(nazivM, index, 2);
			setValueAt(sifraF, index, 3);
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
				+ "otpremnica.sifra_magacina LIKE '%" + params[1] + "%' AND "
				+ "otpremnica.sifra_fakture LIKE '%" + params[2] + "%' AND "				
				+ "poslata_otpremnica LIKE '%" + params[3] + "%'";
		fillData(basicQuery + whereStmt + orderBy);

	}

	@Override
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra_otpremnice = rset.getString("SIFRA_OTPREMNICE");			
			String sifraM = rset.getString("sifraMagacina");
			String nazivM = rset.getString("NAZIV_MAGACINA");
			String sifraF = rset.getString("sifraFakture");
			String datum = rset.getString("DATUM_FAKTURE");
			String transport = rset.getString("TRANSPORT_FAKTURE");
			String poslata = rset.getString("POSLATA_OTPREMNICA");

			addRow(new String[] { sifra_otpremnice, sifraM, nazivM,
					sifraF, datum, transport, poslata });
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
						"INSERT INTO otpremnica (sifra_otpremnice, otpremnica.sifra_magacina, otpremnica.sifra_fakture, poslata_otpremnica) VALUES (?, ?, ?, ?)");
		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);
		stmt.setString(3, params[3]);
		stmt.setString(4, params[6]);		

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
						"UPDATE otpremnica SET sifra_magacina = ?, sifra_fakture = ? WHERE sifra_otpremnice = ?");

		stmt.setString(1, params[0]);
		stmt.setString(2, params[1]);		
		stmt.setString(3, sifra_otpremnice);
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		setValueAt(params[0], index, 1);
		setValueAt(params[1], index, 3);		
		fireTableDataChanged();
	}

}
