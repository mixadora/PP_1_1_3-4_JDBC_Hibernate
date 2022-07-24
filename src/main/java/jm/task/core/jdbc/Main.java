package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("Mukhammad", "Davronov", (byte) 28);
        userService.saveUser("Elon", "Musk", (byte) 51);
        userService.saveUser("Tom", "Cruise", (byte) 60);
        userService.saveUser("Iron", "Man", (byte) 57);
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
