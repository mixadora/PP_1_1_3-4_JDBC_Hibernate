package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection conn;

    static {
        try {
            conn = Util.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback ();
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback ();
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (PreparedStatement pstm = conn.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")) {
            conn.setAutoCommit(false);
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback ();
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement pstm = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            conn.setAutoCommit(false);
            pstm.setLong(1, id);
            pstm.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback ();
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM users")) {
            conn.setAutoCommit(false);
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback ();
        } finally {
            conn.setAutoCommit(true);
        }

        return users;
    }

    public void cleanUsersTable() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE TABLE users");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback ();
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
