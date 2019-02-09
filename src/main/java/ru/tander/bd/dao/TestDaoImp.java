package ru.tander.bd.dao;


import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Arrays;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

public class TestDaoImp implements TestDao {
    private static final Logger LOGGER = Logger.getLogger(TestDaoImp.class);
    private static final String TABLE_NAME = "test";
    private static final int COUNT_QUERY_ONE_BATCH = 10;
    private Connection connection;

    public TestDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int addNumbers(int n) {
        final String INSERT_QUERY = "insert into " + TABLE_NAME + " values (?)";
        int countAddRecord = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            connection.setAutoCommit(false);
            for (int i = 1; i <= n; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.addBatch();
                if (i % COUNT_QUERY_ONE_BATCH == 0 || i == n) {
                    countAddRecord = countAddRecord + Arrays.stream(preparedStatement.executeBatch()).sum();
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Error INSERT_QUERY. " + e);
            countAddRecord = 0;
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                LOGGER.error(e1);
            }
        }
        return countAddRecord;
    }

    @Override
    public int clearTable() {
        final String CLEAR_QUERY = "delete from " + TABLE_NAME;
        int countClearRecord = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_QUERY)) {
            countClearRecord = preparedStatement.executeUpdate();
            LOGGER.info("Table '" + TABLE_NAME + "' cleared: " + countClearRecord + " records.");
        } catch (SQLException e) {
            LOGGER.error("Error CLEAR_QUERY. " + e);
        }
        return countClearRecord;
    }

    @Override
    public String[] selectAll() {
        String[] records = null;
        final String SELECT_ALL_QUERY = "select * from " + TABLE_NAME;
        try (Statement statement = connection.createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY);
             ResultSet result = statement.executeQuery(SELECT_ALL_QUERY)) {
            int countRows = getCountRows(result);
            if (countRows > 0) {
                records = new String[countRows];
                int numRow = 0;
                while (result.next()) {
                    records[numRow] = result.getString(1);
                    numRow++;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error SELECT_ALL_QUERY. ", e);
        }
        return records;
    }

    private int getCountRows(ResultSet result) throws SQLException {
        if (result != null) {
            try {
                result.last();
                return result.getRow();
            } finally {
                result.beforeFirst();
            }
        }
        return 0;
    }

}
