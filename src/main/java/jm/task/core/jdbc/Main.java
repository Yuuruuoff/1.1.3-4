package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();

        userService.createUsersTable();

        userService.saveUser("Yuri", "Klabukov", (byte) 24);
        userService.saveUser("Kolya", "Smirnov", (byte) 10);
        userService.saveUser("German", "Zhukov", (byte) 11);
        userService.saveUser("Vasya", "Atletov", (byte) 12);

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getLastName() + " " + user.getAge());
        }

        userService.cleanUsersTable();

       // userService.dropUsersTable();

    }
}
