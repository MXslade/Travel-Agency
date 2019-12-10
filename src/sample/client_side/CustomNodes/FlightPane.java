package sample.client_side.CustomNodes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import sample.Model.FlightFull;
import sample.Model.FlightRaw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FlightPane extends HBox {

    private FlightFull flightFull;
    private FlightRaw flightRaw;

    private Label companyLabel;

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
    private SimpleDateFormat sdfHelper;

    public FlightPane(FlightFull flightFull, FlightRaw flightRaw) {
        super();

        this.flightFull = flightFull;
        this.flightRaw = flightRaw;

        companyLabel = new Label(flightFull.getCompany());

        sdfForTime = new SimpleDateFormat("HH:mm");
        sdfForDate = new SimpleDateFormat("d MMM, E");
        sdfHelper = new SimpleDateFormat("yyyy/MM/dd");

        String startDateStr = flightFull.getYear() + "/" + flightFull.getMonth() / 10 + "" + flightFull.getMonth() % 10 +
                "/" + flightFull.getDay() / 10 + "" + flightFull.getDay() % 10;

        startTimeLabel = new Label(flightFull.getHour() + ":" + flightFull.getMinute());
        try {
            startDateLabel = new Label(sdfForDate.format(sdfHelper.parse(startDateStr)));
        } catch (ParseException e) {
            e.printStackTrace();
            startDateLabel = new Label("error");
        }
        startTimeVBox = new VBox(startDateLabel, startTimeLabel);

        long endTime = flightFull.getHour() * 60 + flightFull.getMinute() + flightRaw.getDuration();
        long hours = endTime / 60;
        long minutes = endTime % 60;
        int year = flightFull.getYear();
        int month = flightFull.getMonth();
        int day = flightFull.getDay();

        String endDateStr = year + "/" + month / 10 + "" + month % 10 + "/" + day / 10 + "" + day % 10;
        try {
            Date date = sdfHelper.parse(endDateStr);
            if (hours > 23) {
                hours -= 24;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                date.setTime(calendar.getTimeInMillis());
            }
            endDateLabel = new Label(sdfForDate.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            endDateLabel = new Label("error");
        }
        endTimeLabel = new Label(hours + ":" + minutes);

        endTimeVBox = new VBox(endDateLabel, endTimeLabel);

        durationLabel = new Label(flightRaw.getDuration() / 60 + ":" + flightRaw.getDuration() % 60);
        durationLine = new Line(0, 15, 200, 15);
        durationVBox = new VBox(durationLabel, durationLine);

        priceLabel = new Label(flightFull.getPrice() + "₸");
        chooseButton = new Button("Выбрать");
        priceVBox = new VBox(priceLabel, chooseButton);

        this.getChildren().addAll(companyLabel, startTimeVBox, durationVBox, endTimeVBox, priceVBox);

        customizeNodes();
    }

    private void customizeNodes() {
        String timeStyle = "-fx-text-fill: black; -fx-font-size: 22; -fx-font-name: Arial;";
        String priceStyle = "-fx-text-fill: black; -fx-font-size: 22; -fx-font-name: Arial;";
        String timeVBoxStyle = "-fx-background-color: white; -fx-spacing: 8; -fx-alignment: center;";

        startTimeLabel.setStyle(timeStyle);
        endTimeLabel.setStyle(timeStyle);
        companyLabel.setStyle(timeStyle);
        companyLabel.setMinWidth(200);
        companyLabel.setMaxWidth(200);

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

    public Button getChooseButton() {
        return chooseButton;
    }
}
