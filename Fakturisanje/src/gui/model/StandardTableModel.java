package gui.model;

import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import util.SortUtils;

public abstract class StandardTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String basicQuery = "";
	public String orderBy = "";
	public String whereStmt = "";

	public static final int CUSTOM_ERROR_CODE = 50000;
	public static final String ERROR_RECORD_WAS_CHANGED = "Slog je promenjen od strane drugog korisnika. Molim vas, pogledajte njegovu trenutnu vrednost";
	public static final String ERROR_RECORD_WAS_DELETED = "Slog je obrisan od strane drugog korisnika";

	public StandardTableModel(Object[] colName, int rowCount) {
		super(colName, rowCount);
	}

	public void checkRow(int index) throws SQLException {

	}

	public void search(String[] params) throws SQLException {
	}

	public void fillData(String sql) throws SQLException {

	}

	public void open() throws SQLException {
		fillData(basicQuery + orderBy);
	}

	public void openWhere(String whereSQL) throws SQLException {
		whereStmt = whereSQL;
		fillData(basicQuery + whereStmt + orderBy);

	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void deleteRow(int index) throws SQLException {

	}

	public int sortedInsert(String[] params) {
		int left = 0;
		int right = getRowCount() - 1;
		int mid = (left + right) / 2;
		while (left <= right) {
			mid = (left + right) / 2;
			String aSifra = (String) getValueAt(mid, 0);
			if (SortUtils.getLatCyrCollator().compare(params[0], aSifra) > 0)
				left = mid + 1;
			else if (SortUtils.getLatCyrCollator().compare(params[0], aSifra) < 0)
				right = mid - 1;
			else
				break;
		}
		insertRow(left, params);
		return left;
	}

	public int insertRow(String[] params) throws SQLException {
		return 0;
	}

	public void updateRow(int index, String[] params) throws SQLException {

	}

}
