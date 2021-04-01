package controllers;

import dbOperations.DbServices;
import java.sql.*;


public class LoginController {

    public static boolean isValid(String usrname, String password) {

        try {
            Connection connection = DbServices.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM admin WHERE name = ?");

            preparedStatement.setString(1, usrname);

            ResultSet rs = preparedStatement.executeQuery();

            String passwordAdmin = null;

            while(rs.next())
            {
                passwordAdmin = rs.getString("password");
            }

            assert passwordAdmin != null;
            return passwordAdmin.equals(password.trim());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}