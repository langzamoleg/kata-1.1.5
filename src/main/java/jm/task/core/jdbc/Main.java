package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

import static jm.task.core.jdbc.util.Util.connection;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserService service = new UserServiceImpl();

        service.createUsersTable();

        User user1 = new User("Konstantin", "Voronin", (byte) 40);
        service.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        System.out.println("User " + user1.getName() + " added");

        User user2 = new User("Vera", "Voronina", (byte) 35);
        service.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        System.out.println("User " + user2.getName() + " added");

        User user3 = new User("Leonid", "Voronin", (byte) 45);
        service.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        System.out.println("User " + user3.getName() + " added");

        User user4 = new User("Galina", "Voronina", (byte) 75);
        service.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        System.out.println("User " + user4.getName() + " added");

        List<User> out = service.getAllUsers();
        for (int i = 0; i < out.size(); i++) {
            User user_out = out.get(i);
            System.out.println(user_out.getName() + user_out.getLastName() + user_out.getAge());
        }

        service.cleanUsersTable();
        service.dropUsersTable();

        connection.close();
    }
}