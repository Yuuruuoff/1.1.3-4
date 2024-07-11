package jm.task.core.jdbc.util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private final String url = "jdbc:postgresql://localhost:5438/";
    private final String user = "postgres";
    private final String password = "123qwe";

    public Util() {

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT VERSION()")) {
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Util.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}