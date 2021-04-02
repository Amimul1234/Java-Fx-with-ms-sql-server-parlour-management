package javaFxControllers;

import dbOperations.CustomerOperations;
import dbOperations.DbServices;
import entities.Customer;
import entities.Service;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
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

public class AddServiceDetails {

    private final List<Service> serviceList = new ArrayList<>();
    private File file;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ChoiceBox<String> serviceNameChoiceBox;

    @FXML
    private ImageView serviceImage;

    @FXML
    public void initialize()
    {
        new Thread(() -> Platform.runLater(() -> {

            serviceList.addAll(DbServices.getInstance().getAllServicesRecords());

            for(Service service : serviceList)
            {
                serviceNameChoiceBox.getItems().add(service.getServiceName());
            }

            if(serviceList.size() > 0)
                serviceNameChoiceBox.setValue(serviceList.get(0).getServiceName());

        })).start();
    }

    @FXML
    void addServiceImage() {
        if(file == null)
        {
            showAlert("Error!", "Customer image can not be null");
        }
        else
        {
            String name = serviceNameChoiceBox.getSelectionModel().getSelectedItem();

            Integer serviceId = null;

            for(Service service : serviceList)
            {
                if(service.getServiceName().equals(name))
                {
                    serviceId = service.getId();
                    break;
                }
            }

            Integer serviceId1 = serviceId;

            new Thread(() -> {
                try
                {
                    DbServices.getInstance().saveServiceImage(file, serviceId1);

                    Platform.runLater(() -> {

                        try {
                            Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().
                                    getClassLoader().getResource("Fxml/AdminDashboard.fxml")));
                            borderPane.setCenter(view);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });

                }catch (Exception e)
                {
                    Platform.runLater(() -> {
                        showAlert("Error!", "Can not save service image");
                        System.out.println(e.getMessage());
                    });
                }
            }).start();

        }
    }

    @FXML
    void imageSelected() {

        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg");

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(imageFilter);
        chooser.setTitle("Select Image");

        file = chooser.showOpenDialog(serviceImage.getScene().getWindow());

        if(file != null)
        {
            Image image = new Image(file.toURI().toString());
            serviceImage.setFitWidth(400);
            serviceImage.setFitHeight(240);
            serviceImage.setImage(image);
            serviceImage.setSmooth(true);
            serviceImage.setCache(true);
            serviceImage.setPreserveRatio(true);
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
