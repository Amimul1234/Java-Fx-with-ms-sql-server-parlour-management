package entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

public class Appointment implements Serializable {

    private Integer appointmentId;
    private UUID appointmentNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private Date appointMakingDate;
    private String appointmentTime;
    private String selectedService;
    private Date appointmentDate;
    private String remark;
    private String status;
    private Date remarkDate;


    public Appointment() {
    }

    public Appointment(Integer appointmentId, UUID appointmentNumber, String name,
                       String email, String phoneNumber, Date appointMakingDate,
                       String appointmentTime, String selectedService, Date appointmentDate,
                       String remark, String status, Date remarkDate)
    {
        this.appointmentId = appointmentId;
        this.appointmentNumber = appointmentNumber;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.appointMakingDate = appointMakingDate;
        this.appointmentTime = appointmentTime;
        this.selectedService = selectedService;
        this.appointmentDate = appointmentDate;
        this.remark = remark;
        this.status = status;
        this.remarkDate = remarkDate;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public UUID getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = UUID.fromString(appointmentNumber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getAppointMakingDate() {
        return appointMakingDate;
    }

    public void setAppointMakingDate(Date appointMakingDate) {
        this.appointMakingDate = appointMakingDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(String selectedService) {
        this.selectedService = selectedService;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRemarkDate() {
        return remarkDate;
    }

    public void setRemarkDate(Date remarkDate) {
        this.remarkDate = remarkDate;
    }
}