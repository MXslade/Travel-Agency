package sample.client_side.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.Model.FlightFull;
import sample.client_side.CustomNodes.FlightPane;
import sample.client_side.Main;
import sample.client_side.ServerConnector;

public class UserAccountController {

    @FXML
    private VBox flightsVBox;
    private ServerConnector serverConnector;
    private ObservableList<FlightFull> flights;

    @FXML
    private void initialize() {
        serverConnector = Main.serverConnector;
        initFlights();
    }

    private void initFlights() {
        flights = serverConnector.getBoughtFlights();
        if (flights == null) {
            return;
        }
        for (FlightFull f : flights) {
            FlightPane flightPane = new FlightPane(f, serverConnector.getFlightRaw(f.getFlight_id()));
            flightsVBox.getChildren().add(flightPane);
            flightPane.getChooseButton().setVisible(false);
        }
    }

    @FXML
    private void showMain(MouseEvent mouseEvent) {
        Main.showMainScene();
    }
}
