package dbOperations;

import entities.Customer;
import entities.CustomerFullDetails;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
                customer.setCustomerId(rs.getInt("customerId"));
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

    public synchronized void saveCustomerDetails(File file, Integer customerId, String remarks)
    {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO CustomerDetails VALUES (?, ?, ?)");

            FileInputStream fileInputStream = new FileInputStream(file.getPath());
            byte[] bytes;
            bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes, 0, bytes.length);
            fileInputStream.close();

            preparedStatement.setString(1, remarks);
            preparedStatement.setBytes(2, bytes);
            preparedStatement.setInt(3, customerId);

            preparedStatement.executeUpdate();

            System.out.println(preparedStatement);

            System.out.println("Customer details inserted successfully!");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    public CustomerFullDetails getCustomerFullDetails(Integer customerId) {
        try {

            Statement statement = connection.createStatement();

            //Getting review and service name from a inner join query based o specific customer id
            String query = "SELECT * FROM Customer INNER JOIN CustomerDetails sR on sR.customerId = Customer.customerId" +
                    " WHERE sR.customerId = " + customerId;

            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next())
            {
                CustomerFullDetails customerFullDetails = new CustomerFullDetails();

                customerFullDetails.setCustomerName(resultSet.getString("name"));
                customerFullDetails.setEmailAddress(resultSet.getString("email"));
                customerFullDetails.setCustomerRemark(resultSet.getString("customerRemarks"));
                customerFullDetails.setCustomerImage(resultSet.getBytes("customerImage"));
                customerFullDetails.setMobileNumber(resultSet.getString("mobileNumber"));

                return customerFullDetails;
            }

            return null;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
