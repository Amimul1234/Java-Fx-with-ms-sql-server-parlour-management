package dbOperations;

import entities.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerOperations {

    DbServices dbServices = DbServices.getInstance();
    Connection connection = dbServices.getConnection();

    public boolean signup(String username, String email, String mobileNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Customer VALUES (?, ?, ?, ?)");

            Date date = new Date(System.currentTimeMillis());

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, mobileNumber);
            preparedStatement.setString(4, date.toString());

            preparedStatement.executeUpdate();

            System.out.println(preparedStatement);

            System.out.println("Customer record inserted successfully!");
            return true;
        } catch (SQLException e) {
            System.out.println("Error in Customer SignUp is " + e.getMessage());
            return false;
        }
    }

    public synchronized List<Customer> getCustomer() {

        List<Customer> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM Customer";

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCreationDate(rs.getString("creationDate"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setMobileNumber(rs.getString("mobileNumber"));
                users.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
