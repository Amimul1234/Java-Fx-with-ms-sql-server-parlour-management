package javaFxControllers;

import java.io.IOException;
import java.util.List;
import controllers.InvoiceController;
import dbOperations.DbServices;
import entities.AssignS;
import entities.Customer;
import entities.InvoiceData;
import entities.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AssignService {

    InvoiceData invoiceData;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<AssignS> assignTable;

    @FXML
    private TableColumn<AssignS, String> serviceName;

    @FXML
    private TableColumn<AssignS, String> servicePrice;

    @FXML
    private TableColumn<AssignS, String> status;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button doneButton;

    ObservableList<AssignS> observableList;

    public void setCustomer(Customer customer) {
        this.invoiceData = new InvoiceData(customer.getName());
        System.out.println(customer);
    }

    @FXML
    void initialize() {

        getItems();

        serviceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        servicePrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        assignTable.setItems(observableList);

        addButton.setOnAction(e -> {
            int index = assignTable.getSelectionModel().getSelectedIndex();
            observableList.get(index).setStatus("added");

            if (!invoiceData.getAssignSList().contains(assignTable.getSelectionModel().getSelectedItem()))
                this.invoiceData.getAssignSList().add(assignTable.getSelectionModel().getSelectedItem());

            assignTable.refresh();
        });

        removeButton.setOnAction(e -> {
            int index = assignTable.getSelectionModel().getSelectedIndex();
            observableList.get(index).setStatus("not added");
            this.invoiceData.getAssignSList().remove(assignTable.getSelectionModel().getSelectedItem());
            assignTable.refresh();
        });

        doneButton.setOnAction(e -> {

            System.out.println(invoiceData);
            InvoiceController.insertInvoice(this.invoiceData);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Dashboard.fxml"));

            Stage stage = (Stage) pane.getScene().getWindow();
            Scene scene = null;
            try {
                scene = new Scene(loader.load(), 1173, 721);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            stage.setScene(scene);
        });
    }

    private void getItems() {
        observableList = FXCollections.observableArrayList();
        List<Service> serviceList = DbServices.getInstance().getAllServicesRecords();
        for (Service s : serviceList) {
            AssignS temp = new AssignS(s.getServiceName(), String.valueOf(s.getServicePrice()));
            observableList.add(temp);
        }
    }
}
