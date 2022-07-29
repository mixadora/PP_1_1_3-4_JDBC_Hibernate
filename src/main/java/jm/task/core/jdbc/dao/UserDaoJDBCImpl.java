package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String CREATE_TABLE = "CREATE TABLE if not exists users " +
                "(id BIGINT not NULL AUTO_INCREMENT, " +
                " name VARCHAR(70), " +
                " lastName VARCHAR(70), " +
                " age TINYINT, " +
                " PRIMARY KEY ( id ))";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE);
            System.out.println("Table users create successful or already exists!");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Table users is delete!");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String INSERT = "INSERT INTO users VALUES(id,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User: " + name + "/" + lastName + "/" + age + " added to db!");
            connection.commit();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException {
        String DELETE_BY_ID = "DELETE FROM users WHERE id= ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.executeUpdate();
            System.out.println("User with id : " + id + " deleted from db!");
            connection.commit();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"));

                user.setId(rs.getLong("id"));
                userList.add(user);
                System.out.println(user);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE from users");
            System.out.println("Table users cleared!");
            connection.commit();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
