package entities;

import java.sql.Date;

public class AssignS {
    private String serviceName;
    private String servicePrice;
    private String status;

    public AssignS(String serviceName, String servicePrice) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.status = "not added";
//
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssignS{" +
                "serviceName='" + serviceName + '\'' +
                ", servicePrice='" + servicePrice + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
