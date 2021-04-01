package javaFxControllers;

import java.io.IOException;
import java.util.List;
import controllers.InvoiceController;
import dbOperations.CustomerOperations;
import entities.Customer;
import entities.InvoiceData;
import entities.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InvoiceDetail {

    private InvoiceData invoice;

    private final CustomerOperations customerOperations = new CustomerOperations();

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label number;

    @FXML
    private Label date;

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, String> serviceName;

    @FXML
    private TableColumn<Service, String> serviceCost;

    @FXML
    private Label totalCost;

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane pane;

    @FXML
    private Text nameOfCustomer;

    @FXML
    private Text invoiceDate;

    @FXML
    private Text phoneNumber;

    @FXML
    private Text emailId;


    ObservableList<Service> observableList;

    public InvoiceDetail() {
        observableList = FXCollections.observableArrayList();
    }

    public void setInvoice(InvoiceData invoice) {

        this.invoice = invoice;
        System.out.println(this.invoice);

        getItems();


        serviceTable.setItems(observableList);

        List<Customer> customers = customerOperations.getCustomer();

        System.out.println(this.invoice);

        for (Customer c : customers) {
            if (c.getName().equals(invoice.getCutomerName())) {
                nameOfCustomer.setText(invoice.getCutomerName());
                emailId.setText(c.getEmail());
                phoneNumber.setText(c.getMobileNumber());
                invoiceDate.setText(invoice.getDate());
                break;
            }
        }

    }

    @FXML
    void initialize() {

        serviceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceCost.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));

        serviceTable.setItems(observableList);

        backButton.setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Dashboard.fxml"));

            Stage stage = (Stage) pane.getScene().getWindow();
            Scene scene = null;
            try {
                scene = new Scene((Parent) loader.load(), 1173, 721);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            stage.setScene(scene);
        });
    }

    public void getItems() {
        observableList = FXCollections.observableArrayList();
        List<Service> services = InvoiceController.getServices(this.invoice.getInvoiceId());
        double tCost = 0;
        for (Service i : services) {
            tCost += i.getServicePrice();
            observableList.add(i);
        }
        totalCost.setText(tCost + "");
    }
}