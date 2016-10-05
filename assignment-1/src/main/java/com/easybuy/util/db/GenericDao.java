package com.easybuy.util.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The generic DAO (Data Access Object) that can facilitate model DAOs.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Sep 28, 2016
 *
 */
public class GenericDao {

    /**
     * log writer for this class
     */
    protected final Logger log = LoggerFactory.getLogger(GenericDao.class);

    /**
     * the underlying commons-dbutils' query runner that does all the work
     */
    final private QueryRunner runner = new QueryRunner();


    /**
     * Call a single prepared or call-able insert sql statement and return auto-generated ID. The connection used during
     * call is retrieved from {@link PooledDataSource}.
     *
     * @param clazz
     *            the bean's class
     *
     * @param sql
     *            a prepared or call-able sql statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return auto-generated ID in the table
     * @throws SQLException
     */
    public <T> int insert(Class<T> clazz, String sql, Object... params) throws SQLException {
        Connection conn = PooledDataSource.getConnection();
        try {
            int newId = runner.insert(conn, sql, new ResultSetHandler<Integer>() {

                /**
                 * Anonymous handle method that retrieve the auto-generated Id
                 */
                @Override
                public Integer handle(ResultSet rs) throws SQLException {
                    if (rs.next()) {
                        int ret = rs.getInt(1);
                        rs.close();
                        return ret;
                    }
                    return 0;
                }
            }, params);
            conn.commit();
            return newId;
        } catch (SQLException e) {
            conn.rollback();
            log.error(e.getMessage());
            throw new SQLException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }


    /**
     * Call a single prepared insert/update/delete sql statement or call-able procedure/function to database. The
     * connection used during call is retrieved from {@link PooledDataSource}.
     *
     * @param sql
     *            a prepared or call-able sql statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return row count affected
     * @throws Exception
     */
    public int exec(String sql, Object... params) throws SQLException {
        Connection conn = PooledDataSource.getConnection();
        try {
            int ret = 0;
            ret = runner.update(conn, sql, params);
            conn.commit();
            return ret;
        } catch (SQLException e) {
            conn.rollback();
            log.error(e.getMessage());
            throw new SQLException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }


    /**
     * Call a single prepared insert/update/delete sql statement or procedure/function to database.
     *
     * @param conn
     *            the specified database connection to use for the query execution
     * @param sql
     *            a prepared or call-able sql statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return row count affected
     * @throws SQLException
     */
    public int exec(Connection conn, String sql, Object... params) throws SQLException {
        if (null != conn) {
            try {
                return runner.update(conn, sql, params);
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new SQLException(e);
            }
        }
        return -1;
    }


    /**
     * Call multiple prepared insert/update/delete sql statements or call-able procedures/functions to database. The
     * connection used during call is retrieved from {@link PooledDataSource}.
     *
     * @param sql
     *            a prepared or call-able sql statement template for multiple iteration of parameters array
     * @param params
     *            an array of variable parameters for the prepared sql statement template
     * @return row count affected
     * @throws SQLException
     */
    public int execBatch(String sql, Object[]... params) throws Exception {
        Connection conn = PooledDataSource.getConnection();
        try {
            int[] cs = runner.batch(conn, sql, params);
            int ret = 0;
            for (int c : cs) {
                ret += c;
            }
            conn.commit();
            return ret;
        } catch (SQLException e) {
            conn.rollback();
            log.error(e.getMessage());
            throw new SQLException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }


    /**
     * Call multiple prepared insert/update/delete sql statements or call-able procedures/functions to database.
     *
     * @param conn
     *            the specified database connection to use for the query execution
     * @param sql
     *            a prepared or call-able sql statement template for multiple iteration of parameters array
     * @param params
     *            an array of variable parameters for the prepared sql statement template
     * @return row count affected
     * @throws SQLException
     */
    public int execBatch(Connection conn, String sql, Object[]... params)
            throws SQLException {
        if (null != conn) {
            try {
                int[] cs = runner.batch(conn, sql, params);
                int ret = 0;
                for (int c : cs) {
                    ret += c;
                }
                return ret;
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new SQLException(e);
            }
        }
        return -1;
    }


    /**
     * Call multiple insert/update/delete plain sql statements to database. The connection used during call is retrieved
     * from {@link PooledDataSource}.
     *
     * @param sqls
     *            list of normal sql statements
     * @return row count affected
     * @throws SQLException
     */
    public int execBatch(List<String> sqls) throws Exception {

        Connection conn = null;
        try {
            conn = PooledDataSource.getConnection();
            conn.setAutoCommit(false);
            int ret = 0;
            if (null != sqls) {
                final int sqlCnt = sqls.size();
                for (int i = 0; i < sqlCnt; i++) {
                    ret += runner.update(conn, sqls.get(i));
                }
            }
            conn.commit();
            return ret;
        } catch (SQLException e) {
            conn.rollback();
            log.error(e.getMessage());
            throw new SQLException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }

    }


    /**
     * Query a bean object of specified type from a record of query result. The connection used during call is retrieved
     * from {@link PooledDataSource}.
     *
     * @param <T>
     *            type of bean
     * @param clazz
     *            the class of type T
     * @param sql
     *            a prepared "select ... from" statement, while ... are field names matching all the member names
     *            defined in class T
     * @param params
     *            variable parameters for the prepared sql statement
     * @return an object of the type T as first record in resultset, or null if no records found
     * @throws SQLException
     */
    public <T> T queryObj(Class<T> clazz, String sql, Object... params)
            throws Exception {
        Connection conn = null;
        try {
            conn = PooledDataSource.getConnection();
            return runner.query(conn, sql, new BeanHandler<T>(clazz),
                    params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }


    /**
     * Query a bean object of specified type from a record of query result.
     *
     * @param <T>
     *            type of bean
     * @param conn
     *            the specified database connection to use for the query execution
     * @param clazz
     *            the class of type T
     * @param sql
     *            a prepared "select ... from" statement, while ... are field names matching all the member names
     *            defined in class T
     * @param params
     *            variable parameters for the prepared sql statement
     * @return an object of the type T as first record in resultset, or null if no records found
     * @throws SQLException
     */
    public <T> T queryObj(Connection conn, Class<T> clazz, String sql,
            Object... params) throws SQLException {
        try {
            return runner.query(conn, sql, new BeanHandler<T>(clazz),
                    params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        }
    }


    /**
     * Query a list of bean objects of specified type from records of query result. The connection used during call is
     * retrieved from {@link PooledDataSource}.
     *
     * @param <T>
     *            type of bean
     * @param clazz
     *            the class of type T
     * @param sql
     *            a prepared "select ... from" statement, while ... are field names matching all the member names
     *            defined in class T
     * @param params
     *            variable parameters for the prepared sql statement
     * @return a list of objects of the type T, if no records found, the list is empty
     * @throws SQLException
     */
    public <T> List<T> queryObjs(Class<T> clazz, String sql, Object... params)
            throws Exception {
        Connection conn = null;
        try {
            conn = PooledDataSource.getConnection();
            return runner.query(conn, sql, new BeanListHandler<T>(clazz),
                    params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }


    /**
     * Query a list of bean objects of specified type from records of query result.
     *
     * @param <T>
     *            type of bean
     * @param conn
     *            the specified database connection to use for the query execution
     * @param clazz
     *            the class of type T
     * @param sql
     *            a prepared "select ... from" statement, while ... are field names matching all the member names
     *            defined in class T
     * @param params
     *            variable parameters for the prepared sql statement
     * @return a list of objects of the type T, if no records found, the list is empty
     * @throws SQLException
     */
    public <T> List<T> queryObjs(Connection conn, Class<T> clazz, String sql,
            Object... params) throws SQLException {
        try {
            return runner.query(conn, sql, new BeanListHandler<T>(clazz),
                    params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        }
    }

    /**
     * The default customized query handler that works with commons-dbuitls' QueryRunner to render query result set as
     * an object of {@link SimpleSqlResultSet}.
     */
    private final ResultSetHandler<SimpleSqlResultSet> handler = new ResultSetHandler<SimpleSqlResultSet>() {

        @Override
        public SimpleSqlResultSet handle(ResultSet rs) throws SQLException {
            ResultSetMetaData meta = rs.getMetaData();
            final int cols = meta.getColumnCount();
            String[] colNames = new String[cols];
            for (int i = 0; i < cols; i++) {
                colNames[i] = meta.getColumnName(i + 1);
            }
            List<Object[]> result = new ArrayList<Object[]>();
            Object[] arow = null;
            while (rs.next()) {
                arow = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    arow[i] = rs.getObject(i + 1);
                }
                result.add(arow);
            }
            rs.close();
            return new SimpleSqlResultSet(colNames, result);
        }
    };


    /**
     * Query a prepared or call-able sql statement and return a SimpleSqlResultSet.
     *
     * @param conn
     *            the specified database connection to use for the query execution
     *
     * @param sql
     *            a prepared or call-able sql statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return a custom wrapped result set
     * @throws SQLException
     */
    public SimpleSqlResultSet query(Connection conn, String sql, Object... params)
            throws SQLException {

        try {
            return runner.query(conn, sql, handler, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        }
    }


    /**
     * Query a prepared or call-able sql statement and return a SimpleSqlResultSet. The connection used during call is
     * retrieved from {@link PooledDataSource}.
     *
     * @param sql
     *            a prepared or call-able sql statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return a custom wrapped result set
     * @throws SQLException
     */
    public SimpleSqlResultSet query(String sql, Object... params) throws Exception {

        Connection conn = null;
        try {
            conn = PooledDataSource.getConnection();
            return runner.query(conn, sql, handler, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }


    /**
     * Get a list of objects of specified simple type from first column in records of query result. The connection used
     * during call is retrieved from {@link PooledDataSource}.
     *
     * @param <T>
     *            simple type (Integer/Long/Double/Number/String/Timestamp...)
     * @param clazz
     *            the class of type T
     * @param sql
     *            a prepared "select field from table" statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return a list of objects of the simple type T as first column from query result, if no records found, the list
     *         is empty
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> query1stCol(Class<T> clazz, String sql, Object... params)
            throws Exception {
        List<T> ret = new ArrayList<T>();
        SimpleSqlResultSet rs = null;
        Connection conn = null;
        try {
            conn = PooledDataSource.getConnection();
            rs = query(conn, sql, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        if (null != rs) {
            final int rowCnt = rs.getRowCount();
            for (int i = 0; i < rowCnt; i++) {
                ret.add((T) rs.getCell(i, 0));
            }
        }
        return ret;
    }


    /**
     * Get a list of objects of specified simple type from first column in records of query result.
     *
     * @param <T>
     *            simple type (Integer/Long/Double/Number/String/Timestamp...)
     * @param conn
     *            the specified database connection to use for the query execution
     *
     * @param clazz
     *            the class of type T
     * @param sql
     *            a prepared "select field from table" statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return a list of objects of the simple type T as first column from query result, if no records found, the list
     *         is empty
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> query1stCol(Connection conn, Class<T> clazz, String sql,
            Object... params) throws SQLException {
        List<T> ret = new ArrayList<T>();
        SimpleSqlResultSet rs = null;
        try {
            rs = query(conn, sql, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        }
        if (null != rs) {
            final int rowCnt = rs.getRowCount();
            for (int i = 0; i < rowCnt; i++) {
                ret.add((T) rs.getCell(i, 0));
            }
        }
        return ret;
    }


    @SuppressWarnings("unchecked")
    public <V> Map<Long, V> query1st2ColsAsMap(Class<V> valClazz, String sql,
            Object... params) throws Exception {
        Map<Long, V> ret = new LinkedHashMap<Long, V>();
        SimpleSqlResultSet rs = null;
        try {
            rs = query(sql, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        }
        if (null != rs) {
            final int rowCnt = rs.getRowCount();
            for (int i = 0; i < rowCnt; i++) {
                ret.put(((BigDecimal) rs.getCell(i, 0)).longValue(),
                        (V) rs.getCell(i, 1));
            }
        }
        return ret;
    }


    @SuppressWarnings("unchecked")
    public <V> Map<Long, V> query1st2ColsAsMap(Connection conn,
            Class<V> valClazz, String sql, Object... params)
            throws SQLException {
        Map<Long, V> ret = new LinkedHashMap<Long, V>();
        SimpleSqlResultSet rs = null;
        try {
            rs = query(conn, sql, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        }
        if (null != rs) {
            final int rowCnt = rs.getRowCount();
            for (int i = 0; i < rowCnt; i++) {
                ret.put(((BigDecimal) rs.getCell(i, 0)).longValue(),
                        (V) rs.getCell(i, 1));
            }
        }
        return ret;
    }


    @SuppressWarnings("unchecked")
    public <V> Map<Integer, V> query1st2ColsAsMapInt(Class<V> valClazz,
            String sql, Object... params) throws Exception {
        Map<Integer, V> ret = new LinkedHashMap<Integer, V>();
        SimpleSqlResultSet rs = null;
        try {
            rs = query(sql, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        }
        if (null != rs) {
            final int rowCnt = rs.getRowCount();
            for (int i = 0; i < rowCnt; i++) {
                ret.put(((BigDecimal) rs.getCell(i, 0)).intValue(),
                        (V) rs.getCell(i, 1));
            }
        }
        return ret;
    }


    @SuppressWarnings("unchecked")
    public <V> Map<Integer, V> query1st2ColsAsMapInt(Connection conn,
            Class<V> valClazz, String sql, Object... params)
            throws SQLException {
        Map<Integer, V> ret = new LinkedHashMap<Integer, V>();
        SimpleSqlResultSet rs = null;
        try {
            rs = query(conn, sql, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        }
        if (null != rs) {
            final int rowCnt = rs.getRowCount();
            for (int i = 0; i < rowCnt; i++) {
                ret.put(((BigDecimal) rs.getCell(i, 0)).intValue(),
                        (V) rs.getCell(i, 1));
            }
        }
        return ret;
    }


    /**
     * Get a map of specified simple types as <Key,Value> from first and second column in records of query result. The
     * connection used during call is retrieved from {@link PooledDataSource}.
     *
     * @param <K>
     *            simple types as key (Integer/Long/Double/Number/String...)
     * @param <V>
     *            simple types as value (Integer/Long/Double/Number/String...)
     * @param kClazz
     *            the class of type K
     * @param valClazz
     *            the class of type V
     * @param sql
     *            a prepared "select ... from" statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return a map of <K,V> entries of the type K as first column as key and type V as secound column as V from query
     *         result, if no records found, the map is empty
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> query1st2ColsAsMap(Class<K> kClazz,
            Class<V> valClazz, String sql, Object... params)
            throws Exception {
        Map<K, V> ret = new LinkedHashMap<K, V>();
        SimpleSqlResultSet rs = null;
        Connection conn = null;
        try {
            conn = PooledDataSource.getConnection();
            rs = query(conn, sql, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        if (null != rs) {
            final int rowCnt = rs.getRowCount();
            for (int i = 0; i < rowCnt; i++) {
                ret.put((K) rs.getCell(i, 0), (V) rs.getCell(i, 1));
            }
        }
        return ret;
    }


    /**
     * Get a map of specified simple types as <Key,Value> from first and second column in records of query result.
     *
     * @param <K>
     *            simple types as key (Integer/Long/Double/Number/String...)
     * @param <V>
     *            simple types as value (Integer/Long/Double/Number/String...)
     * @param conn
     *            the specified database connection to use for the query execution
     *
     * @param kClazz
     *            the class of type K
     * @param valClazz
     *            the class of type V
     * @param sql
     *            a prepared "select ... from" statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return a map of <K,V> entries of the type K as first column as key and type V as secound column as V from query
     *         result, if no records found, the map is empty
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> query1st2ColsAsMap(Connection conn,
            Class<K> kClazz, Class<V> valClazz, String sql, Object... params)
            throws SQLException {
        Map<K, V> ret = new LinkedHashMap<K, V>();
        SimpleSqlResultSet rs = null;
        try {
            rs = query(conn, sql, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        }
        if (null != rs) {
            final int rowCnt = rs.getRowCount();
            for (int i = 0; i < rowCnt; i++) {
                ret.put((K) rs.getCell(i, 0), (V) rs.getCell(i, 1));
            }
        }
        return ret;
    }


    /**
     * Get an object from first cell (1st column 1st row) in query result. The connection used during call is retrieved
     * from {@link PooledDataSource}.
     *
     * @param sql
     *            a prepared "select ... from" statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return the value of first cell from query result as an object(could be casted to String/Number/Timestamp...), if
     *         no records found, this object is null
     * @throws SQLException
     */
    public Object query1stCell(String sql, Object... params)
            throws Exception {
        SimpleSqlResultSet rs = null;
        Connection conn = null;
        try {
            conn = PooledDataSource.getConnection();
            rs = query(conn, sql, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        if (null != rs && rs.getRowCount() > 0) {
            return rs.getCell(0, 0);
        }
        return null;
    }


    /**
     * Get an object from first cell (1st column 1st row) in query result.
     *
     * @param conn
     *            the specified database connection to use for the query execution
     * @param sql
     *            a prepared "select ... from" statement
     * @param params
     *            variable parameters for the prepared sql statement
     * @return the value of first cell from query result as an object(could be casted to String/Number/Timestamp...), if
     *         no records found, this object is null
     * @throws SQLException
     */
    public Object query1stCell(Connection conn, String sql, Object... params)
            throws SQLException {
        SimpleSqlResultSet rs = null;
        try {
            rs = query(conn, sql, params);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e);
        }
        if (null != rs && rs.getRowCount() > 0) {
            return rs.getCell(0, 0);
        }
        return null;
    }
}
