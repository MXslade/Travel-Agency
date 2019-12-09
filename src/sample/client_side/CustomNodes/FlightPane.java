package sample.client_side.CustomNodes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import sample.Model.Flight;

import java.text.SimpleDateFormat;

public class FlightPane extends HBox {

    private Flight flight;

    private VBox startTimeVBox;
    private Label startTimeLabel;
    private Label startDateLabel;

    private VBox endTimeVBox;
    private Label endTimeLabel;
    private Label endDateLabel;

    private VBox durationVBox;
    private Label durationLabel;
    private Line durationLine;

    private VBox priceVBox;
    private Label priceLabel;
    private Button chooseButton;

    private SimpleDateFormat sdfForTime;
    private SimpleDateFormat sdfForDate;

    public FlightPane(Flight flight) {
        super();

        this.flight = flight;

        sdfForTime = new SimpleDateFormat("HH:mm");
        sdfForDate = new SimpleDateFormat("d MMM, E");

        startTimeLabel = new Label(sdfForTime.format(flight.getStartDateTime()));
        startDateLabel = new Label(sdfForDate.format(flight.getStartDateTime()));
        startTimeVBox = new VBox(startDateLabel, startTimeLabel);

        endTimeLabel = new Label(sdfForTime.format(flight.getEndDateTime()));
        endDateLabel = new Label(sdfForDate.format(flight.getEndDateTime()));
        endTimeVBox = new VBox(endDateLabel, endTimeLabel);

        durationLabel = new Label(sdfForTime.format(flight.getDuration()));
        durationLine = new Line(0, 15, 200, 15);
        durationVBox = new VBox(durationLabel, durationLine);

        priceLabel = new Label("Money");
        chooseButton = new Button("Выбрать");
        priceVBox = new VBox(priceLabel, chooseButton);

        this.getChildren().addAll(startTimeVBox, durationVBox, endTimeVBox, priceVBox);

        customizeNodes();
        addEventHandlers();
    }

    private void customizeNodes() {
        String timeStyle = "-fx-text-fill: black; -fx-font-size: 22; -fx-font-name: Arial;";
        String priceStyle = "-fx-text-fill: black; -fx-font-size: 22; -fx-font-name: Arial;";
        String timeVBoxStyle = "-fx-background-color: white; -fx-spacing: 8; -fx-alignment: center;";

        startTimeLabel.setStyle(timeStyle);
        endTimeLabel.setStyle(timeStyle);

        priceLabel.setStyle(priceStyle);
        chooseButton.setStyle("-fx-cursor: hand; -fx-font-size: 16;" + "-fx-text-fill: white;" +
                "-fx-font-name: Arial;" + "-fx-font-weight: bold;-fx-background-color: green; " +
                "-fx-padding: 8 32 8 32; -fx-background-radius: 5em;");

        startTimeVBox.setStyle(timeVBoxStyle);
        endTimeVBox.setStyle(timeVBoxStyle);
        priceVBox.setStyle("-fx-background-color: #f5f5f5; -fx-spacing: 8; -fx-padding: 16, 8, 16, 8; -fx-alignment: center;");
        durationVBox.setAlignment(Pos.CENTER);

        this.setStyle("-fx-padding: 8, 16, 8, 16; -fx-spacing: 16; -fx-background-color: white; -fx-alignment: center;");
    }

    private void addEventHandlers() {

    }
}
