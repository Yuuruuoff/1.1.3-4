package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.LoggerClass;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getInstance().getSource();

    private Statement statement = null;

    private final Logger logger = new LoggerClass().getLogger();

    public UserDaoJDBCImpl() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        try {
            statement.execute("create table Users (id bigint GENERATED ALWAYS AS IDENTITY, name varchar, lastname varchar, age smallint)");
            logger.info("Table created : Users");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating table", e);
        }
    }

    public void dropUsersTable() {
        try {
            statement.execute("drop table Users");
            logger.info("Users table dropped");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error droping table", e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            String addUser = "insert into Users (id, name, lastname, age) values (default, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(addUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            logger.info("User saved : " + name + " " + lastName + " " + age);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving user", e);
        }
    }

    public void removeUserById(long id) {
        try {
            String removeUser = "delete from Users where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(removeUser);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            logger.info("User removed : " + id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error removing user", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
        logger.info("Users table cleaned");
    }
}
