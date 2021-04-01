package controllers;

import dbOperations.DbServices;
import entities.Service;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AddServiceController {

    public static boolean addService(Service service) {
        try {
            Connection connection = DbServices.getInstance().getConnection();
            Statement statement = connection.createStatement();
            String query = "INSERT INTO Service VALUES ( '" + service.getId() + "','" + service.getServiceName() + "','" + service.getServicePrice() + "');";
            statement.executeUpdate(query);
            System.out.println(query);
            System.out.println("Inserted successfully!");
            return true;
        } catch (SQLException e) {
            System.out.println("Error in Adding Service is " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        List<Service> services = DbServices.getInstance().getAllServicesRecords();
        for (Service s : services)
            System.out.println(s);
    }
}
