package com.easybuy.util.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom SQL result set that simplifies data (size/row/cell) and column name retrieval.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Sep 23, 2016
 *
 */
public class SimpleSqlResultSet {

    /**
     * a collection of rows of data
     */
    protected List<Object[]> tableData = new ArrayList<Object[]>();

    /**
     * column names of query result set
     */
    protected String[] colNames = new String[] {};


    /**
     * Construct from given column names and list of rows of data
     *
     * @param colNames
     *            an array of column names
     * @param tabledata
     *            a table of data as List of arrays of objects
     * @see CpdAccess#query(java.sql.Connection, String, Object...)
     */
    public SimpleSqlResultSet(String[] colNames, List<Object[]> tabledata) {
        this.colNames = colNames;
        this.tableData = tabledata;
    }


    /**
     *
     * @return the column names
     */
    public String[] getColNames() {
        return colNames;
    }


    /**
     *
     * @return a table of all data as a list of arrays of objects
     */
    public List<Object[]> getTableData() {
        return tableData;
    }


    /**
     *
     * @param row
     *            row number, 0 based
     * @return an array of objects as a row of data in the result set
     */
    public Object[] getRowAt(int row) {
        return this.tableData.get(row);
    }


    /**
     * Get data in cell located at specified row and column number.
     *
     * @param row
     *            row number, 0 based
     * @param col
     *            column number, 0 based
     * @return an Object as a cell at specified row and column
     */
    public Object getCell(int row, int col) {
        return (getRowAt(row)[col]);
    }


    /**
     * Get data in cell located at specified row by its column name.
     *
     * @param row
     *            row number, 0 based
     * @param colName
     *            unique column name, not case-sensitive
     * @return an Object as a cell at specified row number and column name
     */
    public Object getCell(int row, String colName) throws SQLException {
        final int colCnt = colNames.length;
        for (int i = 0; i < colCnt; i++) {
            if (colNames[i].equalsIgnoreCase(colName)) {
                return getCell(row, i);
            }
        }
        throw new SQLException("ERROR : You are trying to get data from a field which does not exist: "
                + colName);
    }


    /**
     *
     * @return total row count of the result set
     */
    public int getRowCount() {
        return this.tableData.size();
    }


    /**
     *
     * @return total column count of the result set
     */
    public int getColCount() {
        return this.colNames.length;
    }
}
