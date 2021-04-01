package controllers;

import dbOperations.DbServices;
import entities.InvoiceData;
import entities.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceController {

    public static void insertInvoice(InvoiceData invoiceData) {

        if (invoiceData.getAssignSList().isEmpty())
            return;

        try {
            
            String date = invoiceData.getDate();

            Connection connection = DbServices.getInstance().getConnection();
            Statement statement = connection.createStatement();

            for (int i = 0; i < invoiceData.getAssignSList().size(); i++) {

                String query = "INSERT INTO Invoice VALUES ( '" + invoiceData.getCutomerName() + "','"
                        + invoiceData.getAssignSList().get(i).getServiceName() + "','" +
                        invoiceData.getAssignSList().get(i).getServicePrice() + "','" + date + "','" + invoiceData.getInvoiceId()
                        + "');";

                statement.executeUpdate(query);
                System.out.println(query);
                System.out.println("Inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error in Invoice insertion is " + e.getMessage());
        }
    }

    public static List<Service> getServices(String id) {

        List<Service> services = new ArrayList<>();

        try {
            Connection connection = DbServices.getInstance().getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM Invoice where id= '" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Service service = new Service();
                service.setServiceName(rs.getString("servicename"));
                service.setServicePrice(rs.getDouble("serviceprice"));
                services.add(service);
                System.out.println(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return services;
    }

    public static List<InvoiceData> getInvoiceList() {
        List<InvoiceData> users = new ArrayList<>();
        try {
            Connection connection = DbServices.getInstance().getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM Invoice";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                InvoiceData user = new InvoiceData();
                user.setCutomerName(rs.getString("customername"));
                user.setInvoiceId(rs.getString("id"));
                user.setDate(rs.getString("date"));
                if (!users.contains(user))
                    users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}

