package javaFxControllers;

import dbOperations.DbServices;
import entities.Service;
import entities.ServiceJoinedTable;
import entities.ServiceReviewWithJoin;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;

public class ServiceDetails {

    private int serviceIdIdentifier;
    private ServiceJoinedTable serviceJoinedTable;

    public void setService(Service serviceIdIdentifier) {
        this.serviceIdIdentifier = serviceIdIdentifier.getId();
        new Thread(this::populateTable).start();
    }

    @FXML
    private Text serviceName;

    @FXML
    private TableColumn<ServiceReviewWithJoin, String> serviceNameTable;

    @FXML
    private ImageView serviceImage;

    @FXML
    private TableView<ServiceReviewWithJoin> serviceReviewsTable;

    @FXML
    private TableColumn<ServiceReviewWithJoin, Integer> serviceId;

    @FXML
    private TableColumn<ServiceReviewWithJoin, String> serviceReview;

    @FXML
    private TableColumn<ServiceReviewWithJoin, Integer> reviewId;

    @FXML
    public void initialize()
    {
        serviceNameTable.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        serviceReview.setCellValueFactory(new PropertyValueFactory<>("serviceReview"));
        reviewId.setCellValueFactory(new PropertyValueFactory<>("reviewId"));
    }

    private void populateTable() {
        try {

            serviceJoinedTable = DbServices.getInstance().getServiceJoinedTable(serviceIdIdentifier);

            Platform.runLater(() -> {

                serviceName.setText(serviceJoinedTable.getServiceName());

                if(serviceJoinedTable.getServiceImage() != null)
                    serviceImage.setImage(new Image(
                            new ByteArrayInputStream(serviceJoinedTable.getServiceImage())));

                serviceReviewsTable.setItems(FXCollections.observableList(serviceJoinedTable.getServiceReviewWithJoinList()));

            });

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
