package sample.client_side.Controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainController {

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
            findOneWatTickets();
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
        TextField fromCityTextField = new TextField();
        fromCityTextField.setPromptText("Откуда");
        fromCityTextField.setStyle(styleForOptionElements);
        TextField toCityTextField = new TextField();
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
        TextField fromCityTextField = new TextField();
        fromCityTextField.setPromptText("Откуда");
        fromCityTextField.setStyle(styleForOptionElements);
        TextField toCityTextField = new TextField();
        toCityTextField.setPromptText("Куда");
        toCityTextField.setStyle(styleForOptionElements);
        DatePicker fromDatePicker = new DatePicker();
        fromDatePicker.setStyle(styleForOptionElements);
        oneWayHBox.getChildren().addAll(fromCityTextField, toCityTextField, fromDatePicker);
        oneWayHBox.setVisible(false);
        oneWayHBox.setStyle(styleForOptionPanes);
        optionsStackPane.getChildren().add(oneWayHBox);
    }

    private void addTicketForSeveralTickets() {
        HBox hBox = new HBox();
        TextField fromCityTextField = new TextField();
        fromCityTextField.setPromptText("Откуда");
        fromCityTextField.setStyle(styleForOptionElements);
        TextField toCityTextField = new TextField();
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

    private void findBackAndForthTickets() {

    }

    private void findOneWatTickets() {

    }

    private void findSeveralTickets() {

    }
}
