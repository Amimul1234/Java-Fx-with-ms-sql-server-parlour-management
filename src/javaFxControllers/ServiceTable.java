package javaFxControllers;

import java.util.List;
import dbOperations.DbServices;
import entities.Service;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ServiceTable {
    @FXML
    private TableView<Service> servicesTable;

    @FXML
    private TableColumn<Service, String> serviceId;

    @FXML
    private TableColumn<Service, String> serviceName;

    @FXML
    private TableColumn<Service, String> serviceCharge;

    ObservableList<Service> serviceObservableList;

    @FXML
    void initialize() {

        new Thread(this::getItems).start();


        serviceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        serviceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceCharge.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
    }

    private void getItems() {

        List<Service> allList = DbServices.getInstance().getAllServicesRecords();

        Platform.runLater(() -> {
            this.serviceObservableList = FXCollections.observableArrayList();
            serviceObservableList.addAll(allList);
            servicesTable.setItems(serviceObservableList);
        });
    }
}
