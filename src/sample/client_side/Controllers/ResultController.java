package sample.client_side.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import sample.client_side.CustomNodes.FlightPane;
import sample.client_side.Main;
import sample.client_side.ServerConnector;

public class ResultController {

    private ServerConnector serverConnector;

    @FXML
    private VBox mainVBox;

//    @FXML
//    private ListView resultListView;
    private FlightPane flightPane;

    @FXML
    private void initialize() {
        serverConnector = Main.serverConnector;
//        resultListView.setItems(serverConnector.getLastFlights());
        flightPane = new FlightPane(serverConnector.getLastFlights().get(0));
        mainVBox.getChildren().add(flightPane);
    }
}
