package javaFxControllers;

import dbOperations.DbServices;
import entities.ServiceReviewWithJoin;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class ServiceReviews {

    private ObservableList<ServiceReviewWithJoin> observableList;

    @FXML
    private TableView<ServiceReviewWithJoin> serviceReviewsTable;

    @FXML
    private TableColumn<ServiceReviewWithJoin, Integer> serviceId;

    @FXML
    private TableColumn<ServiceReviewWithJoin, String> serviceName;

    @FXML
    private TableColumn<ServiceReviewWithJoin, String> serviceReview;

    @FXML
    private TableColumn<ServiceReviewWithJoin, Integer> reviewId;

    @FXML
    public void initialize()
    {
        initializeColumns();

        new Thread(this::getItems).start();
    }

    private void initializeColumns() {
        serviceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        serviceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceReview.setCellValueFactory(new PropertyValueFactory<>("serviceReview"));
        reviewId.setCellValueFactory(new PropertyValueFactory<>("reviewId"));
    }

    private void getItems() {

        observableList = FXCollections.observableArrayList();

        List<ServiceReviewWithJoin> serviceReviewWithJoins = DbServices.getInstance().
                getAllReviews();

        if(serviceReviewWithJoins != null)
        {
            Platform.runLater(() -> {
                observableList.addAll(serviceReviewWithJoins);
                serviceReviewsTable.setItems(observableList);
                serviceReviewsTable.refresh();
            });
        }
        else
        {
            Platform.runLater(ServiceReviews::showAlert);
        }
    }

    private static void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed to fetch services");
        alert.setHeaderText(null);
        alert.setContentText("Failed to get services review, please try again");
        alert.showAndWait();
    }

}
