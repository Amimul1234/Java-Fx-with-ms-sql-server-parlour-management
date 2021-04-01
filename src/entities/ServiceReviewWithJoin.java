package entities;

public class ServiceReviewWithJoin {
    private int serviceId;
    private String serviceName;
    private String serviceReview;
    private int reviewId;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceReview() {
        return serviceReview;
    }

    public void setServiceReview(String serviceReview) {
        this.serviceReview = serviceReview;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
}
