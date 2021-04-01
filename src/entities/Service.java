package entities;

import java.io.Serializable;

public class Service implements Serializable {

    private Integer id;
    private String serviceName;
    private Double servicePrice;

    public Service() {
    }

    public Service(Integer id, String serviceName, Double servicePrice) {
        this.id = id;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }
}
