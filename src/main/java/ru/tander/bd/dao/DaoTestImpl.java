package ru.tander.bd.dao;

import java.sql.*;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

public class DaoTestImpl  implements DaoTest{
    private Connection connection;

    public DaoTestImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void addNumbers(Integer n) {
        final String INSERT_QUERY = "insert into test values (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
            for (int i=1; i<=n;i++){
                preparedStatement.setInt(1,i);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNumbersBatch(Integer n) {
        final String INSERT_QUERY = "insert into test values (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
            for (int i=1; i<=n;i++){
                preparedStatement.setInt(1,i);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void clearTable(){
        final String CLEAR_QUERY = "delete from test";
        try(Statement statement = connection.createStatement()){
            statement.execute(CLEAR_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int[] selectAll(){
        final String SELECT_ALL_QUERY = "select * from test";
        try(Statement statement = connection.createStatement(TYPE_SCROLL_INSENSITIVE,CONCUR_READ_ONLY)){
            ResultSet result = statement.executeQuery(SELECT_ALL_QUERY);
            int countRows = getCountRows(result);
            if (countRows>0){
                int[] records = new int[countRows+1];
                int numRow = 0;
                while (result.next()){
                    numRow = numRow + 1;
                    records[numRow] = result.getInt(1);
                }
                return records;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[0];
    }

    private int getCountRows(ResultSet result) throws SQLException {
        if (result != null) {
            try {
                result.last();
                return result.getRow();
            }finally {
                result.beforeFirst();
            }
        }
        return 0;
    }

}
