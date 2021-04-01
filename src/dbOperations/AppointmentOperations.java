package dbOperations;

import entities.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOperations {

    DbServices dbServices = DbServices.getInstance();
    Connection connection = dbServices.getConnection();

    public synchronized String addAnAppointments(Appointment appointment) {

        if(connection!=null)
        {
            try {

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO appointment " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                preparedStatement.setString(1, String.valueOf(appointment.getAppointmentNumber()));
                preparedStatement.setString(2, appointment.getName());
                preparedStatement.setString(3, appointment.getEmail());
                preparedStatement.setString(4, appointment.getPhoneNumber());
                preparedStatement.setDate(5, appointment.getAppointMakingDate());
                preparedStatement.setString(6, appointment.getAppointmentTime());
                preparedStatement.setString(7, appointment.getSelectedService());
                preparedStatement.setDate(8, appointment.getAppointmentDate());
                preparedStatement.setString(9, appointment.getRemark());
                preparedStatement.setString(10, appointment.getStatus());
                preparedStatement.setDate(11, appointment.getRemarkDate());

                if(preparedStatement.executeUpdate()>0)
                {
                    return "Successfully made an appointment. Appointment number is: " + appointment.getAppointmentNumber();
                }
                else
                {
                    return "Can not make an appointment, please try again";
                }

            } catch (Exception e) {
                System.out.println("Error occurred. Error is:- "+e.getMessage());
                return "Can not make an appointment, please try again";
            }
        }
        else
        {
            System.out.println("Can not connect to database, please try again");
            return "Can not make an appointment, please try again";
        }
    }


    public synchronized void deleteAppointment(String appointmentNumber) throws SQLException {
        if(connection!=null)
        {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM appointment WHERE appointmentNumber = ?");
            preparedStatement.setString(1, appointmentNumber);

            System.out.println(preparedStatement);

            try {
                preparedStatement.executeUpdate();
                System.out.println("Appointment deleted successfully!");
            } catch (SQLException e) {
                System.out.println("Error while deleting appointment, Error is: " + e.getMessage());
            }
        }
        else
        {
            System.out.println("Can not connect to database, please try again");
        }
    }

    public List<Appointment> getAllAppointments() {

        List<Appointment> appointments = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM appointment";

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                Appointment appointment = new Appointment();

                appointment.setAppointmentId(rs.getInt("appointmentId"));
                appointment.setAppointmentNumber(rs.getString("appointmentNumber"));
                appointment.setName(rs.getString("name"));
                appointment.setEmail(rs.getString("email"));
                appointment.setPhoneNumber(rs.getString("phoneNumber"));
                appointment.setAppointMakingDate(rs.getDate("appointMakingDate"));
                appointment.setAppointmentTime(rs.getString("appointmentTime"));
                appointment.setSelectedService(rs.getString("selectedService"));
                appointment.setAppointmentDate(rs.getDate("appointmentDate"));
                appointment.setRemark(rs.getString("remark"));
                appointment.setStatus(rs.getString("status"));

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public synchronized void updateStatus(Integer appointmentId) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE appointment set status = 'accepted' WHERE appointmentId = ?");

            preparedStatement.setInt(1, appointmentId);

            preparedStatement.executeUpdate();

        }catch (Exception e)
        {
            System.out.println("Error occurred while updating appointment data");
            e.printStackTrace();
        }
    }

}
