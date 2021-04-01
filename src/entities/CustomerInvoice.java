package entities;

import java.io.Serializable;
import java.util.UUID;

public class CustomerInvoice implements Serializable {
    private Integer Id;
    private UUID invoiceNumber;
    private String serviceTaken;
    private Double serviceCharge;
    private String invoiceDate;

    private Customer customer;

    public CustomerInvoice() {
    }

    public CustomerInvoice(Integer Id, UUID invoiceNumber, String serviceTaken, Double serviceCharge, String invoiceDate) {
        this.Id = Id;
        this.invoiceNumber = invoiceNumber;
        this.serviceTaken = serviceTaken;
        this.serviceCharge = serviceCharge;
        this.invoiceDate = invoiceDate;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public UUID getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(UUID invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getServiceTaken() {
        return serviceTaken;
    }

    public void setServiceTaken(String serviceTaken) {
        this.serviceTaken = serviceTaken;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getDate() {
        return invoiceDate;
    }

    public void setDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
