package jm.task.core.jdbc.util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final String url = "jdbc:postgresql://localhost:5438/";

    private static final String user = "postgres";

    private static final String password = "123qwe";

    private static Util instance;

    private final Connection source;

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


    private static Connection connect() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT VERSION()");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Util.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return connection;
    }
}