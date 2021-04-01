package javaFxControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import controllers.InvoiceController;
import entities.InvoiceData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Invoice {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<InvoiceData> invoiceTable;

    @FXML
    private TableColumn<InvoiceData, String> invoideId;

    @FXML
    private TableColumn<InvoiceData, String> customerName;

    @FXML
    private TableColumn<InvoiceData, String> date;

    @FXML
    private Button viewButton;

    ObservableList<InvoiceData> observableList;

    @FXML
    void initialize() {

        getItems();

        invoideId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("cutomerName"));


        invoiceTable.setItems(observableList);

        viewButton.setOnAction((e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().
                    getResource("FXML/InvoiceDetail.fxml"));

            Stage stage = (Stage) borderPane.getScene().getWindow();

            Scene scene = null;

            try {
                scene = new Scene(loader.load(), 1173, 721);

                InvoiceDetail invoiceDetail = loader.getController();
                invoiceDetail.setInvoice(invoiceTable.getSelectionModel().getSelectedItem());

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            stage.setScene(scene);
        }));
    }

    public ObservableList<InvoiceData> getItems() {
        observableList = FXCollections.observableArrayList();
        List<InvoiceData> temp = InvoiceController.getInvoiceList();
        List<String> invId = new ArrayList<>();
        for (InvoiceData i : temp) {
            String id = i.getInvoiceId();
            if (!invId.contains(id)) {
                invId.add(id);
                InvoiceData invoiceData = new InvoiceData(i.getCutomerName());
                invoiceData.setInvoiceId(id);
                invoiceData.setDate(i.getDate());
                System.out.println(invoiceData);
                observableList.add(invoiceData);
            }

        }
        return observableList;
    }
}
