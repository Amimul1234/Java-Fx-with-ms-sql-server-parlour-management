package entities;

import java.util.ArrayList;
import java.util.List;

public class ServiceJoinedTable {

    private String serviceName;
    private byte[] serviceImage;
    private List<ServiceReviewWithJoin> serviceReviewWithJoinList = new ArrayList<>();

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public byte[] getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(byte[] serviceImage) {
        this.serviceImage = serviceImage;
    }

    public List<ServiceReviewWithJoin> getServiceReviewWithJoinList() {
        return serviceReviewWithJoinList;
    }

    public void setServiceReviewWithJoinList(List<ServiceReviewWithJoin> serviceReviewWithJoinList) {
        this.serviceReviewWithJoinList = serviceReviewWithJoinList;
    }

    public void addServiceReview(ServiceReviewWithJoin serviceReviewWithJoin)
    {
        serviceReviewWithJoinList.add(serviceReviewWithJoin);
    }
}
