package com.pluralsight.DealershipAPI.dataHandlers.abstractDAO;

import java.sql.*;
import java.util.List;

import javax.sql.DataSource;

public abstract class DataManager {
    protected DataSource dataSource;

    public DataManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> int executeUpdate(String query, List<T> arguments) {
        Connection connection;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//TODO  Same thing, extract this method somehow
        try (PreparedStatement insertStmt = connection.prepareStatement(query)) {
            for (int i = 0; i < arguments.size(); i++) {
                T argument = arguments.get(i);
                if (argument instanceof Integer) {
                    insertStmt.setInt(i + 1, (Integer) argument);
                } else if (argument instanceof Double) {
                    insertStmt.setDouble(i + 1, (Double) argument);
                } else if (argument instanceof String) {
                    insertStmt.setString(i + 1, (String) argument);
                } else {
                    throw new IllegalArgumentException("Unsupported argument type");
                }
            }

            return insertStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

}
