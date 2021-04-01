package javaFxControllers;

import dbOperations.AppointmentOperations;
import dbOperations.DbServices;
import entities.Appointment;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeAppointmentController {

    private final DbServices dbServices = DbServices.getInstance();
    private final AppointmentOperations appointmentOperations = new AppointmentOperations();

    @FXML
    private BorderPane homeBorderPane;

    @FXML
    private TextField appointmentMakerName;

    @FXML
    private TextField appointmentMakerEmail;

    @FXML
    private ChoiceBox<String> selectedService;

    @FXML
    private Text selectServices;

    @FXML
    private DatePicker appointmentDate;

    @FXML
    private TextField appointmentMakerPhone;

    @FXML
    private ChoiceBox<String> appointmentTimeChoiceBox;

    @FXML
    private Text selectAppointmentTimeText;

    @FXML
    public void initialize() {

        new Thread(() -> {

            final List<String> servicesNamesList = dbServices.getServicesName();

            if (!servicesNamesList.isEmpty()) {
                Platform.runLater(() -> {
                    selectedService.getItems().addAll(servicesNamesList);
                    selectedService.setValue(servicesNamesList.get(0));
                    selectServices.setVisible(false);
                });
            } else {
                Platform.runLater(() ->
                        showAlert(Alert.AlertType.ERROR, "Can not get services",
                                "Can not get services,please try again"));
            }
        }
        ).start();


        new Thread(() -> {

            final List<String> timeslots = dbServices.getTimeSlots();

            System.out.println(timeslots);

            if (!timeslots.isEmpty()) {
                Platform.runLater(() -> {
                    appointmentTimeChoiceBox.getItems().addAll(timeslots);
                    appointmentTimeChoiceBox.setValue(timeslots.get(0));
                    selectAppointmentTimeText.setVisible(false);
                });
            } else {
                Platform.runLater(() ->
                        showAlert(Alert.AlertType.ERROR, "Can not get time slots",
                                "Can not get time slots,please try again"));
            }
        }).start();
    }

    @FXML
    void aboutUs() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/about_us.fxml")));
        Stage primaryStage = (Stage) homeBorderPane.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void admin() throws IOException
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/AdminSigninPage.fxml")));
        Stage primaryStage = (Stage) homeBorderPane.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void contactUs()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxmls/AdminTimeSlots.fxml"));

        Stage stage = (Stage) selectServices.getScene().getWindow();
        Scene scene = null;

        try {
            scene = new Scene(loader.load(), 1173, 721);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
    }

    @FXML
    void homeScreen()
    {

    }

    @FXML
    void services() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/Services.fxml")));
        Stage primaryStage = (Stage) homeBorderPane.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void makeAnAppointment()
    {
        validateAppointmentInput();
    }

    private void validateAppointmentInput() {

        if (appointmentMakerName.getText().isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, "Appointment maker name can not be empty", "Please enter your name");
        }
        else if (appointmentDate.getValue() == null)
        {
            showAlert(Alert.AlertType.ERROR, "Appointment can not be empty", "Please enter appointment date");
        }
        else if (appointmentMakerName.getText().isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, "Appoint maker phone number can not be empty", "Please enter your mobile number");
        }
        else if (appointmentDate.getValue().isBefore(LocalDate.now()))
        {
            showAlert(Alert.AlertType.ERROR, "Date selection error", "Appointment selection date can not be before current date");
        }
        else if (appointmentMakerName.getText().length() < 11) {
            showAlert(Alert.AlertType.ERROR, "Phone Number invalid", "Please enter a valid phone number");
        }
        else {
            new Thread(this::saveToDatabase).start();
        }
    }

    private void saveToDatabase() {

        Appointment appointment = new Appointment();

        appointment.setAppointmentNumber(String.valueOf(UUID.randomUUID()));
        appointment.setName(appointmentMakerName.getText());
        appointment.setEmail(appointmentMakerEmail.getText());
        appointment.setPhoneNumber(appointmentMakerPhone.getText());
        appointment.setAppointMakingDate(java.sql.Date.valueOf(LocalDate.now()));
        appointment.setAppointmentTime(appointmentTimeChoiceBox.getSelectionModel().getSelectedItem());
        appointment.setSelectedService(selectedService.getSelectionModel().getSelectedItem());
        appointment.setAppointmentDate(java.sql.Date.valueOf(LocalDate.now()));

        final String response = appointmentOperations.addAnAppointments(appointment);

        if (response.equals("Can not make an appointment, please try again")) {
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error making an appointment", response));
        } else {
            Platform.runLater(() -> {

                showAlert(Alert.AlertType.CONFIRMATION, "Successful", response);

                appointmentMakerName.setText(null);
                appointmentMakerEmail.setText(null);
                appointmentMakerPhone.setText(null);
                appointmentDate.setValue(null);

            });
        }
    }

    @FXML
    void serviceReview() throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().
                getResource("Fxml/serviceReview.fxml")));

        Stage primaryStage = (Stage) homeBorderPane.getScene().getWindow();

        primaryStage.getScene().setRoot(root);
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}