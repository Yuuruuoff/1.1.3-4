package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final String URL = "jdbc:postgresql://localhost:5438/";

    private static final String USER = "postgres";

    private static final String PASSWORD = "123qwe";

    private static Util instance;

    private final Connection source;

    private static SessionFactory sourceSessionFactory;

    private Util(Connection source) {
        this.source = source;
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util(
                    Util.connect()
            );
        }
        return instance;
    }

    public Connection getSource() {
        return source;
    }

    public SessionFactory getSourceSessionFactory() {
        return sourceSessionFactory;
    }

    private static Connection connect() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "org.postgresql.Driver");
            properties.put(Environment.URL, "jdbc:postgresql://localhost:5438/");
            properties.put(Environment.USER, "postgres");
            properties.put(Environment.PASS, "123qwe");
            sourceSessionFactory = new Configuration().setProperties(properties).addAnnotatedClass(User.class).buildSessionFactory();

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Util.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return connection;
    }
}