package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Mukhammad", "Davronov", (byte) 28);
        service.saveUser("Elon", "Musk", (byte) 51);
        service.saveUser("Tom", "Cruise", (byte) 60);
        service.saveUser("Iron", "Man", (byte) 57);

        List<User> list = service.getAllUsers();
        System.out.println(list);
        service.removeUserById(1);
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}


















