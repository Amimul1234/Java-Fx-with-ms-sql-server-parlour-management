package javaFxControllers;

import entities.Service;

public class ServiceDetails {

    private int serviceId;

    public void setService(Service selectedItem) {
        this.serviceId = selectedItem.getId();
    }
}
