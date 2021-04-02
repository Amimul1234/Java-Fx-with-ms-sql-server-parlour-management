package dbOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entities.Service;
import entities.ServiceReviewWithJoin;
import entities.TimeSlots;

public class DbServices {

    private static DbServices dbServices = null;
    private Connection connection = null;

    private DbServices() throws SQLException {
        try {

            String connectionUrl = "jdbc:sqlserver://localhost:1433; databaseName=testDatabase;" +
                    "user=rahi;password=Rahi-8000";

            connection = DriverManager.getConnection(connectionUrl);

            new Thread(() -> {
                try {
                    createTablesIfNotExists();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }).start();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static DbServices getInstance()
    {
        if (dbServices == null) {
            try {
                dbServices = new DbServices();
            } catch (SQLException throwable) {
                System.out.println("Can not connect to database, Error is: " + throwable.getMessage());
            }
        }
        return dbServices;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTablesIfNotExists() throws SQLException
    {
        if(connection!=null)
        {
            Statement statement = connection.createStatement();

            //create appointment table if not exists
            {
                String createAdminTable = "CREATE TABLE appointment(appointmentId Integer PRIMARY KEY IDENTITY(1, 1), appointmentNumber varchar(255), " +
                        "name varchar(255), email varchar(255),  phoneNumber varchar(255), appointMakingDate date, appointmentTime varchar(255), selectedService varchar(255)," +
                        "appointmentDate datetime, remark varchar(255), status varchar(255), remarkDate datetime)";

                ResultSet appointmentTable = connection.getMetaData().getTables(null, null,
                        "appointment", null);

                if (appointmentTable.next()) {
                    System.out.println("Appointment table already exists");
                } else {
                    statement.executeUpdate(createAdminTable);
                    System.out.println("Successfully created appointment table");
                }
            }

            //create time slots table if not exists
            {
                String createTimeSlotTable = "CREATE TABLE timeSlots(timeSlots varchar(255))";

                ResultSet timeSlots = connection.getMetaData().getTables(null, null,
                        "timeSlots", null);

                if (timeSlots.next()) {
                    System.out.println("Time slots table already exists");
                } else {
                    statement.executeUpdate(createTimeSlotTable);
                    System.out.println("Successfully created timeslots table");
                }
            }

            //create admin table if not exists
            {
                String createAdminTable = "CREATE TABLE admin(adminId INTEGER PRIMARY KEY Identity(1,1) " +
                        ", name varchar(255), password varchar(255), email varchar(255), contactNumber varchar(255))" ;

                ResultSet adminTable = connection.getMetaData().getTables(null, null,
                        "admin", null);

                if (adminTable.next()) {
                    System.out.println("Admin table already exists");
                } else {
                    statement.executeUpdate(createAdminTable);
                    System.out.println("Successfully created admin table");
                }
            }

            //create customer table if not exists
            {
                String createCustomer =
                        "CREATE TABLE Customer(customerId INTEGER PRIMARY KEY IDENTITY (1, 1), name VARCHAR(255) NOT NULL, " +
                        "email varchar(255) NOT NULL, mobileNumber varchar(20), creationDate varchar(255) NOT NULL)";

                ResultSet customerTable = connection.getMetaData().getTables(null, null,
                        "Customer", null);

                if (customerTable.next()) {
                    System.out.println("Customer table already exists");
                } else {
                    statement.executeUpdate(createCustomer);
                    System.out.println("Successfully created customer table");
                }
            }

            //create customer details table if not exists
            //one to one relation ship with customer table
            {
                String customerDetails =
                        "CREATE TABLE CustomerDetails(detailsId INTEGER Not Null PRIMARY KEY IDENTITY (1, 1), " +
                                "customerRemarks varchar(max) NOT NULL, customerImage varbinary(max), " +
                                "customerId INTEGER references customer(customerId), UNIQUE (customerId))";

                ResultSet customerTable = connection.getMetaData().getTables(null, null,
                        "CustomerDetails", null);

                if (customerTable.next()) {
                    System.out.println("Customer Details table already exists");
                } else {
                    statement.executeUpdate(customerDetails);
                    System.out.println("Successfully created customer details table");
                }
            }

            //create invoice table if not exists
            {
                String createInvoice =
                        "CREATE TABLE Invoice(" +
                                "customername varchar(255) NOT NULL, servicename varchar(255), " +
                                "serviceprice varchar(255), date varchar(255), id varchar(255))";

                ResultSet createInvoiceTable = connection.getMetaData().getTables(null, null,
                        "Invoice", null);

                if (createInvoiceTable.next()) {
                    System.out.println("Invoice table already exists");
                } else {
                    statement.executeUpdate(createInvoice);
                    System.out.println("Successfully created invoice table");
                }
            }

            //create service table if not exists
            {
                String createServiceTable = "CREATE TABLE service(id INTEGER PRIMARY KEY Identity(1,1) , serviceName varchar(255)," +
                        "servicePrice DOUBLE PRECISION)";

                ResultSet serviceTable = connection.getMetaData().getTables(null, null,
                        "service", null);

                if (serviceTable.next()) {

                    System.out.println("Service table already exists");

                    /*
                    String query2 = "CREATE FULLTEXT INDEX ON service(serviceName) KEY INDEX PK__service__3213E83FE106A22C ON " +
                            "fullTextSearchIndex WITH CHANGE_TRACKING AUTO";

                    statement.executeUpdate(query2);
                     */

                } else {
                    statement.executeUpdate(createServiceTable);

                    String query1 = "CREATE FULLTEXT CATALOG fullTextSearchIndex";
                    statement.executeUpdate(query1);

                    System.out.println("Successfully created service table");
                }
            }

            //Service Review Table
            //Creating one to many relationship
            {
                String createServiceReview = "CREATE TABLE serviceReview(reviewId INTEGER PRIMARY KEY Identity(1,1) " +
                        ", name varchar(max), id INTEGER references service(id))" ;

                assert connection != null;
                ResultSet adminTable = connection.getMetaData().getTables(null, null,
                        "serviceReview", null);

                if (adminTable.next()) {
                    System.out.println("Service review table already exists");
                } else {
                    statement.executeUpdate(createServiceReview);
                    System.out.println("Successfully created service review table");
                }
            }

            //service image table
            {
                String createServiceReview = "CREATE TABLE serviceImage(serviceImageId INTEGER PRIMARY KEY Identity(1,1) " +
                        ", image varbinary(max), imageId INTEGER references service(id), UNIQUE (imageId))" ;

                ResultSet serviceImage = connection.getMetaData().getTables(null, null,
                        "serviceImage", null);

                if (serviceImage.next()) {
                    System.out.println("Service image table already exists");
                } else {
                    statement.executeUpdate(createServiceReview);
                    System.out.println("Successfully created service image table");
                }

            }
        }
        else
        {
            System.out.println("Can not connect to database");
        }
    }


    public synchronized String addNewService(Service service) throws SQLException {

        if(connection != null)
        {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO service VALUES (?, ?)");

            preparedStatement.setString(1, service.getServiceName());
            preparedStatement.setDouble(2, service.getServicePrice());

            preparedStatement.executeUpdate();
            return "Successfully added new service";
        }
        else
        {
            return "Can not connect to database";
        }
    }

    public synchronized List<String> getServicesName() {

        if(connection != null)
        {
            try {

                List<String> serviceList = new ArrayList<>();

                Statement statement = connection.createStatement();


                //Select the 5 sorted via price that are not in top 5 services
                String query = "SELECT TOP 5 * FROM service WHERE id NOT IN " +
                        "(SELECT TOP 5 id FROM service ORDER BY id) ORDER BY servicePrice";

                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next())
                {
                    serviceList.add(resultSet.getString("serviceName"));
                }

                return serviceList;

            } catch (Exception e) {
                return null;
            }
        }
        else
        {
            System.out.println("Can not connect to database, please try again");
            return null;
        }
    }

    public synchronized List<Service> getSearchedService(String queryString)
    {
        if(connection != null)
        {
            List<Service> serviceList = new ArrayList<>();

            try
            {
                Statement statement = connection.createStatement();

                String query = "SELECT * FROM service WHERE CONTAINS (serviceName, " + "'\"" + queryString + "*\"')";

                System.out.println(query);

                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next())
                {
                    Service service = new Service();

                    service.setId(resultSet.getInt("id"));
                    service.setServiceName(resultSet.getString("serviceName"));
                    service.setServicePrice(resultSet.getDouble("servicePrice"));

                    serviceList.add(service);
                }

                return serviceList;
            }catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
        else
        {
            System.out.println("Can not connect to database, please try again");
            return null;
        }
    }

    public synchronized List<Service> getAllServicesRecords() {

        if(connection!=null)
        {
            try {

                List<Service> serviceList = new ArrayList<>();

                Statement statement = connection.createStatement();

                String query = "SELECT * FROM service";

                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next())
                {
                    Service service = new Service();

                    service.setId(resultSet.getInt("id"));
                    service.setServiceName(resultSet.getString("serviceName"));
                    service.setServicePrice(resultSet.getDouble("servicePrice"));

                    serviceList.add(service);
                }

                return serviceList;

            } catch (Exception e) {
                return null;
            }
        }
        else
        {
            System.out.println("Can not connect to database, please try again");
            return null;
        }
    }

    public synchronized List<String> getTimeSlots() {

        if(connection!=null)
        {
            try {

                List<String> timeSlotList = new ArrayList<>();

                Statement statement = connection.createStatement();

                String query = "SELECT * FROM timeSlots";

                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next())
                {
                    timeSlotList.add(resultSet.getString("timeSlots"));
                }

                return timeSlotList;

            } catch (Exception e) {
                return null;
            }
        }
        else
        {
            System.out.println("Can not connect to database, please try again");
            return null;
        }
    }

    public synchronized String saveTime(String text) {
        TimeSlots timeSlots = new TimeSlots();
        timeSlots.setTimeSlots(text);

        try {
//            entityManager.getTransaction().begin();
//            entityManager.persist(timeSlots);
//            entityManager.getTransaction().commit();
            return "Successfully added time slot to database";
        } catch (Exception e) {
            return "Can not add time slot to database, please try again";
        }
    }


    public boolean addServiceReview(int serviceId, String text) {
        if(connection != null)
        {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO serviceReview VALUES (?, ?)");
                preparedStatement.setString(1, text);
                preparedStatement.setInt(2, serviceId);

                preparedStatement.executeUpdate();
                return true;

            } catch (SQLException throwable) {
                throwable.printStackTrace();
                return false;
            }
        }
        else
        {
            System.out.println("Can not save review to database");
            return false;
        }
    }

    public List<ServiceReviewWithJoin> getAllReviews() {
        if(connection != null)
        {
            List<ServiceReviewWithJoin> serviceReviewWithJoinList = new ArrayList<>();

            try {
                Statement statement = connection.createStatement();

                //Getting review and service name from a inner join query
                String query = "SELECT * FROM service INNER JOIN serviceReview sR on service.id = sR.id";

                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next())
                {
                    ServiceReviewWithJoin serviceReviewWithJoin = new ServiceReviewWithJoin();

                    serviceReviewWithJoin.setServiceId(resultSet.getInt("id"));
                    serviceReviewWithJoin.setServiceReview(resultSet.getString("name"));
                    serviceReviewWithJoin.setReviewId(resultSet.getInt("reviewId"));
                    serviceReviewWithJoin.setServiceName("serviceName");

                    serviceReviewWithJoinList.add(serviceReviewWithJoin);
                }

                return serviceReviewWithJoinList;

            } catch (SQLException throwable) {
                throwable.printStackTrace();
                return null;
            }
        }
        else
        {
            System.out.println("Can not save review to database");
            return null;
        }
    }

    public synchronized void saveServiceImage(File file, Integer finalCustomerId) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO serviceImage VALUES (?, ?)");

            FileInputStream fileInputStream = new FileInputStream(file.getPath());
            byte[] bytes;
            bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes, 0, bytes.length);
            fileInputStream.close();

            preparedStatement.setBytes(1, bytes);
            preparedStatement.setInt(2, finalCustomerId);

            preparedStatement.executeUpdate();

            System.out.println(preparedStatement);

            System.out.println("Service image inserted successfully!");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
