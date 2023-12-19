package hust.project.base.utils.sql_hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class HikariClient {
    public HikariDataSource dataSource;
    public HikariClient(String jdbcUrl, String user, String password, String database) {
        Properties prop = new Properties();
        prop.put("jdbcUrl", jdbcUrl);
        prop.put("dataSource.user", user);
        prop.put("dataSource.password", password);
        prop.put("dataSource.database", database);
        HikariConfig config = new HikariConfig(prop);
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        return connection;
    }

    public void releaseConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
