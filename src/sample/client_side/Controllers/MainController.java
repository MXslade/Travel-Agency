package sample.client_side.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import sample.Model.City;
import sample.network.Request;
import sample.network.RequestCode;
import sample.network.Response;
import sample.network.ResponseCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;

public class MainController {

    private Socket socket = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;

    private Request request = null;
    private Response response = null;

    private ObservableList<City> cities = FXCollections.observableArrayList();

    private Alert errorOccurredAlert;

    @FXML
    private Label backAndForthLabel;
    @FXML
    private Label oneWayLabel;
    @FXML
    private Label severalTicketsLabel;
    @FXML
    private StackPane optionsStackPane;

    private HBox backAndForthHBox;
    private HBox oneWayHBox;
    private VBox severalTicketsVBox;

    private Border border;
    private Border emptyBorder;

    private String styleForOptionElements = "-fx-font-name: Arial;" + "-fx-font-size: 16";
    private String styleForOptionPanes = "-fx-spacing: 16;" + "-fx-alignment: top-center;";

    @FXML
    private void initialize() {
        initOptions();
        initBorder();
        initNetworkErrorAlert();
        initNetworkConnection();
        showBackAndForthOption();
    }

    @FXML
    private void optionLabelClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == backAndForthLabel) {
            showBackAndForthOption();
        } else if (mouseEvent.getSource() == severalTicketsLabel) {
            showSeveralTicketsOption();
        } else if (mouseEvent.getSource() == oneWayLabel) {
            showOneWayOption();
        }
    }

    @FXML
    private void findTickets() {
        if (backAndForthHBox.isVisible()) {
            findBackAndForthTickets();
        } else if (oneWayHBox.isVisible()) {
            findOneWayTickets();
        } else if (severalTicketsVBox.isVisible()) {
            findSeveralTickets();
        }
    }



    private void initOptions() {
        initBackAndForthOption();
        initSeveralTicketsOption();
        initOneWayTicketOption();
    }

    private void initBorder() {
        border = new Border(new BorderStroke(
                Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY,
                new BorderWidths(2),
                Insets.EMPTY));
        emptyBorder = new Border(new BorderStroke(null, null, null, null));
    }

    private void initNetworkErrorAlert() {
        errorOccurredAlert = new Alert(Alert.AlertType.ERROR);
        errorOccurredAlert.setTitle("Network Error");
        errorOccurredAlert.setHeaderText("Network failure occurred while running");
        errorOccurredAlert.setContentText("Check you internet connection. Then Rerun program again");
    }

    private void initNetworkConnection() {
        try {
            socket = new Socket("127.0.0.1", 8080);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            getCities();
        } catch (IOException e) {
            errorOccurredAlert.showAndWait();
        }
    }

    private void showBackAndForthOption() {
        backAndForthHBox.setVisible(true);
        oneWayHBox.setVisible(false);
        severalTicketsVBox.setVisible(false);
        backAndForthLabel.setBorder(border);
        oneWayLabel.setBorder(emptyBorder);
        severalTicketsLabel.setBorder(emptyBorder);
    }

    private void showSeveralTicketsOption() {
        severalTicketsVBox.setVisible(true);
        oneWayHBox.setVisible(false);
        backAndForthHBox.setVisible(false);
        severalTicketsLabel.setBorder(border);
        oneWayLabel.setBorder(emptyBorder);
        backAndForthLabel.setBorder(emptyBorder);
    }

    private void showOneWayOption() {
        oneWayHBox.setVisible(true);
        severalTicketsVBox.setVisible(false);
        backAndForthHBox.setVisible(false);
        oneWayLabel.setBorder(border);
        severalTicketsLabel.setBorder(emptyBorder);
        backAndForthLabel.setBorder(emptyBorder);
    }

    private void initBackAndForthOption() {
        backAndForthHBox = new HBox();
        ComboBox<City> fromCityTextField = new ComboBox<>();
        fromCityTextField.setItems(cities);
        fromCityTextField.setPromptText("Откуда");
        fromCityTextField.setStyle(styleForOptionElements);
        ComboBox<City> toCityTextField = new ComboBox<>();
        toCityTextField.setItems(cities);
        toCityTextField.setPromptText("Куда");
        toCityTextField.setStyle(styleForOptionElements);
        DatePicker fromDatePicker = new DatePicker();
        fromDatePicker.setStyle(styleForOptionElements);
        DatePicker toDatePicker = new DatePicker();
        toDatePicker.setStyle(styleForOptionElements);
        backAndForthHBox.getChildren().addAll(
                fromCityTextField, toCityTextField, fromDatePicker, toDatePicker
        );
        backAndForthHBox.setVisible(false);
        backAndForthHBox.setStyle(styleForOptionPanes);
        optionsStackPane.getChildren().add(backAndForthHBox);
    }

    private void initSeveralTicketsOption() {
        severalTicketsVBox = new VBox();
        severalTicketsVBox.setVisible(false);
        Button addTicketButton = new Button("+ Добавить перелет");
        addTicketButton.setStyle(styleForOptionElements);
        addTicketButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addTicketForSeveralTickets();
            }
        });
        severalTicketsVBox.getChildren().add(addTicketButton);
        severalTicketsVBox.setSpacing(16);
        optionsStackPane.getChildren().add(severalTicketsVBox);
        addTicketForSeveralTickets();
    }

    private void initOneWayTicketOption() {
        oneWayHBox = new HBox();
        ComboBox<City> fromCityTextField = new ComboBox<>();
        fromCityTextField.setItems(cities);
        fromCityTextField.setPromptText("Откуда");
        fromCityTextField.setStyle(styleForOptionElements);
        fromCityTextField.setId("fromCity");
        ComboBox<City> toCityTextField = new ComboBox<>();
        toCityTextField.setItems(cities);
        toCityTextField.setPromptText("Куда");
        toCityTextField.setStyle(styleForOptionElements);
        toCityTextField.setId("toCity");
        DatePicker fromDatePicker = new DatePicker();
        fromDatePicker.setStyle(styleForOptionElements);
        fromDatePicker.setId("fromDate");
        oneWayHBox.getChildren().addAll(fromCityTextField, toCityTextField, fromDatePicker);
        oneWayHBox.setVisible(false);
        oneWayHBox.setStyle(styleForOptionPanes);
        optionsStackPane.getChildren().add(oneWayHBox);
    }

    private void addTicketForSeveralTickets() {
        HBox hBox = new HBox();
        ComboBox<City> fromCityTextField = new ComboBox<>();
        fromCityTextField.setItems(cities);
        fromCityTextField.setPromptText("Откуда");
        fromCityTextField.setStyle(styleForOptionElements);
        ComboBox<City> toCityTextField = new ComboBox<>();
        toCityTextField.setItems(cities);
        toCityTextField.setPromptText("Куда");
        toCityTextField.setStyle(styleForOptionElements);
        DatePicker fromDatePicker = new DatePicker();
        fromCityTextField.setStyle(styleForOptionElements);
        hBox.getChildren().addAll(fromCityTextField, toCityTextField, fromDatePicker);
        hBox.setStyle(styleForOptionPanes);
        severalTicketsVBox.getChildren().add(hBox);
        Button button = (Button) severalTicketsVBox.getChildren().filtered(node -> node instanceof Button).get(0);
        severalTicketsVBox.getChildren().remove(button);
        severalTicketsVBox.getChildren().add(button);
    }

    private void getCities() {
        request = new Request();
        request.setRequestCode(RequestCode.SHOW_ALL_CITIES);
        try {
            oos.writeObject(request);
            response = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            errorOccurredAlert.showAndWait();
        }
        if (response.getResponseCode() == ResponseCode.ALL_CITIES_FAILURE) {
            errorOccurredAlert.showAndWait();
        } else {
            cities.setAll(response.getCities());
        }
    }

    private void findBackAndForthTickets() {

    }

    private void findOneWayTickets() {
        City fromCity = null, toCity = null;
        String fromDate = null;
        for (Node node : oneWayHBox.getChildren()) {
            if (node.getId().equals("fromCity")) {
                fromCity = ((ComboBox<City>) node).getValue();
            } else if (node.getId().equals("toCity")) {
                toCity = ((ComboBox<City>) node).getValue();
            } else if (node.getId().equals("fromDate")) {
                fromDate = ((DatePicker) node).getEditor().getText();
            }
        }
        if (fromCity == null || toCity == null || fromDate == null) {
            return;
        }
        System.out.println(fromCity + " " + toCity + " " + fromDate);
    }

    private void findSeveralTickets() {

    }
}
