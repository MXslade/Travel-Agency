package sample.client_side.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Model.City;
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
    private ComboBox fromCityComboBox;
    @FXML
    private ComboBox toCityComboBox;

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
}
