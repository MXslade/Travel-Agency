package sample.client_side.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Model.City;
import sample.Model.FlightFull;
import sample.client_side.Main;
import sample.client_side.ServerConnector;

public class AdminAccountController {

    private ServerConnector serverConnector = Main.serverConnector;

    private ObservableList<City> cities;

    @FXML
    private TextField nameAddCityTextField;
    @FXML
    private TextField countryAddCityTextField;
    @FXML
    private TextField longitudeAddCityTextField;
    @FXML
    private TextField latitudeAddCityTextField;

    @FXML
    private ComboBox changeCityComboBox;

    @FXML
    private TextField companyTextField;
    @FXML
    private ComboBox fromCityComboBox;
    @FXML
    private ComboBox toCityComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField numberTextField;

    @FXML
    private void initialize() {
        initCities();
        initComboNodes();
    }

    private void initCities() {
        if (serverConnector.getCitiesFromDb() == null) {
            cities = FXCollections.observableArrayList();
        } else {
            cities = serverConnector.getCities();
        }
    }

    private void initComboNodes() {
        changeCityComboBox.setItems(cities);
        fromCityComboBox.setItems(cities);
        toCityComboBox.setItems(cities);
    }

    @FXML
    private void addCity(MouseEvent mouseEvent) {
        String name = nameAddCityTextField.getText();
        String country = countryAddCityTextField.getText();
        Double longitude = null;
        Double latitude = null;

        try {
            longitude = Double.parseDouble(longitudeAddCityTextField.getText());
            latitude = Double.parseDouble(latitudeAddCityTextField.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (name.isBlank() || country.isBlank() || longitude == null || latitude == null) {
            return;
        }

        City city = serverConnector.addCity(name, country, latitude, longitude);
        if (city == null) {
            //TODO RISE DIALOG WHERE PROBLEM IS DESCRIBED
        } else {
            nameAddCityTextField.setText("");
            countryAddCityTextField.setText("");
            latitudeAddCityTextField.setText("");
            longitudeAddCityTextField.setText("");
        }
    }

    @FXML
    private void addFlight(MouseEvent mouseEvent) {
        String company = companyTextField.getText();
        City fromCity = (City) fromCityComboBox.getValue();
        City toCity = (City) toCityComboBox.getValue();
        Long price = null;
        Integer year = datePicker.getValue().getYear();
        Integer month = datePicker.getValue().getMonthValue();
        Integer day = datePicker.getValue().getDayOfMonth();
        Integer hour = null;
        Integer minute = null;
        Integer number = null;

        try {
            price = Long.parseLong(priceTextField.getText());
            hour = Integer.parseInt(timeTextField.getText().substring(0, 2));
            minute = Integer.parseInt(timeTextField.getText().substring(3));
            number = Integer.parseInt(numberTextField.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (company.isBlank() || fromCity == null || toCity == null || price == null ||
                hour == null || minute == null || number == null) {
            return;
        }

        FlightFull flightFull = serverConnector.addFlightFull(company, fromCity, toCity, price,
                year, month, day, hour, minute, number);
        if (flightFull == null) {
            //TODO RISE DIALOG WHERE PROBLEM IS DESCRIBED
        } else {
            companyTextField.setText("");
            priceTextField.setText("");
            timeTextField.setText("");
            numberTextField.setText("");
        }
    }

    @FXML
    private void exitPerformed(MouseEvent mouseEvent) {
        Main.currentUser = null;
        Main.showMainScene();
    }
}
