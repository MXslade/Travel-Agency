package sample.client_side.Controllers;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.Model.FlightFull;
import sample.client_side.CustomNodes.FlightPane;
import sample.client_side.Main;
import sample.client_side.ServerConnector;

public class ResultController {

    private ServerConnector serverConnector;
    private ObservableList<FlightFull> flights;

    @FXML
    private VBox mainVBox;
    @FXML
    private ScrollPane flightsScrollPane;
    @FXML
    private VBox flightsVBox;

    private Alert alert;


    @FXML
    private void initialize() {
        serverConnector = Main.serverConnector;
        initAlerts();
        initFlights();
    }

    private void initAlerts() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations");
        alert.setHeaderText("You have bout that ticket");
    }

    private void initFlights() {
        flights = serverConnector.getLastFlights();
        for (FlightFull f : flights) {
            FlightPane flightPane = new FlightPane(f, serverConnector.getFlightRaw(f.getFlight_id()));
            flightsVBox.getChildren().add(flightPane);
            flightPane.getChooseButton().setOnMouseClicked(mouseEvent -> {
                boolean val = serverConnector.buyTicket(f);
                if (val) {
                    alert.showAndWait();
                }
            });
        }
    }

    @FXML
    private void showMain(MouseEvent mouseEvent) {
        Main.showMainScene();
    }

    @FXML
    private void showAccount(MouseEvent mouseEvent) {
        Main.showAccountScene();
    }
}
