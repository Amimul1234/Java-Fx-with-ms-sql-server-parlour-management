package javaFxControllers;

import dbOperations.CustomerOperations;
import entities.Customer;
import entities.CustomerFullDetails;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.ByteArrayInputStream;

public class CustomerAllDetails {

    private CustomerFullDetails customerFullDetails;

    private Customer customer;

    @FXML
    private ImageView customerImage;

    @FXML
    private Text customerName;

    @FXML
    private Text csutomerEmail;

    @FXML
    private Text customerMobileNumber;

    @FXML
    private TextArea customerRemarks;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        getItems();
    }

    private void getItems() {

        new Thread(() -> {

            CustomerOperations customerOperations = new CustomerOperations();

            customerFullDetails = customerOperations.getCustomerFullDetails(customer.getCustomerId());

            Platform.runLater(() -> {
                customerRemarks.setText(customerFullDetails.getCustomerRemark());
                customerImage.setImage(new Image(new ByteArrayInputStream(customerFullDetails.getCustomerImage())));
                customerMobileNumber.setText(customerFullDetails.getMobileNumber());
                csutomerEmail.setText(customerFullDetails.getEmailAddress());
                customerName.setText(customerFullDetails.getCustomerName());
            });

        }).start();

    }



}
