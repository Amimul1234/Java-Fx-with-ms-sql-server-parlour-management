package javaFxControllers;

import dbOperations.CustomerOperations;
import entities.Customer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerDetails {

    private final List<Customer> customerList = new ArrayList<>();
    private File file;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ChoiceBox<String> customerNameChoiceBox;

    @FXML
    private ImageView imageView;

    @FXML
    private TextArea remarks;

    @FXML
    public void initialize()
    {
        new Thread(() -> {
            Platform.runLater(() -> {
                customerList.addAll(new CustomerOperations().getCustomer());
                for(Customer customer : customerList)
                {
                    customerNameChoiceBox.getItems().add(customer.getName());
                }

                if(customerList.size() > 0)
                    customerNameChoiceBox.setValue(customerList.get(0).getName());
            });
        }).start();
    }

    @FXML
    void addCustomerDetails() {
        if(file == null)
        {
            showAlert("Error!", "Customer image can not be null");
        }
        else if(remarks.getText().isEmpty())
        {
            showAlert("ERROR!", "Remarks can not be null");
        }
        else
        {
            String name = customerNameChoiceBox.getSelectionModel().getSelectedItem();
            Integer customerId = null;

            for(Customer customer : customerList)
            {
                if(customer.getName().equals(name))
                {
                    customerId = customer.getCustomerId();
                    break;
                }
            }

            Integer finalCustomerId = customerId;

            new Thread(() -> {
                CustomerOperations customerOperations = new CustomerOperations();
                try
                {
                    customerOperations.saveCustomerDetails(file, finalCustomerId, remarks.getText());

                    Platform.runLater(() -> {
                        remarks.setText(null);
                        imageView.setImage(null);

                        try {
                            Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().
                                    getClassLoader().getResource("Fxml/CustomerList.fxml")));
                            borderPane.setCenter(view);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });

                }catch (Exception e)
                {
                    Platform.runLater(() -> {
                        showAlert("Error!", "Can not save customer details");
                        System.out.println(e.getMessage());
                    });
                }
            }).start();

        }
    }


    @FXML
    void imageSelected() {
        //getting only images
        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg");

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(imageFilter);
        chooser.setTitle("Select Image");

        file = chooser.showOpenDialog(imageView.getScene().getWindow());

        if(file != null)
        {
            Image image = new Image(file.toURI().toString());
            imageView.setFitWidth(400);
            imageView.setFitHeight(240);
            imageView.setImage(image);
            imageView.setSmooth(true);
            imageView.setCache(true);
            imageView.setPreserveRatio(true);
        }
    }

    private static void showAlert(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
