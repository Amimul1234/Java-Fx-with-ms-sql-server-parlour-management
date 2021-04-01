package javaFxControllers;

import java.io.IOException;
import java.util.List;
import dbOperations.CustomerOperations;
import entities.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CustomerList {

    private final CustomerOperations customerOperations = new CustomerOperations();

    @FXML
    public BorderPane borderPane;

    @FXML
    private TableView<Customer> tableView;

    @FXML
    private TableColumn<Customer, String> name;

    @FXML
    private TableColumn<Customer, String> email;

    @FXML
    private TableColumn<Customer, String> mobile;

    @FXML
    private TableColumn<Customer, String> creationDate;

    @FXML
    private Button assignService;

    @FXML
    private AnchorPane pane;

    ObservableList<Customer> allCustomer;

    @FXML
    void initialize() {

        getItems();

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        tableView.setItems(allCustomer);



        tableView.setOnMousePressed(event ->
        {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
            {
                Node node = ((Node) event.getTarget()).getParent();

                TableRow row;

                if (node instanceof TableRow) {
                    row = (TableRow) node;
                } else {
                    row = (TableRow) node.getParent();
                }

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/customerDetails.fxml"));

                    Scene scene = new Scene(fxmlLoader.load(), 1173, 721);
                    Stage stage = new Stage();
                    stage.setTitle("Customer Details");
                    stage.getIcons().add(new Image("HomeImages/parlourLogo.png"));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(row.getItem());
            }
        });



        assignService.setOnAction(e -> {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/AssignService.fxml"));

            Stage stage = (Stage) pane.getScene().getWindow();
            Scene scene = null;

            try {

                scene = new Scene(loader.load(), 1173, 721);

                int index = tableView.getSelectionModel().getSelectedIndex();
                AssignService assignService = loader.getController();
                assignService.setCustomer(tableView.getSelectionModel().getSelectedItem());

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            stage.setScene(scene);
        });
    }




    private void getItems() {

        this.allCustomer = FXCollections.observableArrayList();

        List<Customer> cars = customerOperations.getCustomer();

        allCustomer.addAll(cars);

    }
}
