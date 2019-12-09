package sample.client_side;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ServerConnector {

    private Socket socket = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;

    private ObservableList<Flight> lastFlights;

    public ServerConnector() {
        lastFlights = FXCollections.observableArrayList();
        try {
            socket = new Socket("127.0.0.1", 8080);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Flight> getLastFlights() {
        return lastFlights;
    }

    public void setLastFlights(ObservableList<Flight> lastFlights) {
        this.lastFlights = lastFlights;
    }

    public List<City> getCities() {
        Request request = new Request();
        Response response;
        request.setRequestCode(RequestCode.SHOW_ALL_CITIES);
        try {
            oos.writeObject(request);
            response = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        if (response.getResponseCode() == ResponseCode.ALL_CITIES_FAILURE) {
            return null;
        }
        return response.getCities();
    }

    public List<Flight> getOneWayFlights(City fromCity, City toCity, String fromDate) {
        Request request = new Request();
        request.setFromCity(fromCity);
        request.setToCity(toCity);
        try {
            Date date = new Date(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate).getTime());
            request.setFromDate(date);
        } catch (ParseException e) {
            request.setFromDate(null);
            e.printStackTrace();
        }
        request.setRequestCode(RequestCode.SHOW_ONE_WAY_FLIGHT);
        Response response;
        try {
            oos.writeObject(request);
            response = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        if (response.getResponseCode() == ResponseCode.ONE_WAY_FLIGHT_FAILURE) {
            return null;
        }
        lastFlights.clear();
        lastFlights.setAll(response.getFlights());
        return response.getFlights();
    }

    public void close() {
        Request request = new Request();
        request.setRequestCode(RequestCode.EXIT);
        try {
            oos.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User login(String login, String password) {
        Request request = new Request();
        request.setRequestCode(RequestCode.LOGIN);
        request.setLogin(login);
        request.setPassword(password);
        Response response;
        try {
            oos.writeObject(request);
            response = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        if (response.getResponseCode() == ResponseCode.LOGIN_FAILURE) {
            return null;
        }
        return response.getUser();
    }

    public User register(String login, String password, String name, String surname) {
        Request request = new Request();
        request.setRequestCode(RequestCode.REGISTRATION);
        request.setLogin(login);
        request.setPassword(password);
        request.setName(name);
        request.setSurname(surname);
        Response response;
        try {
            oos.writeObject(request);
            response = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        if (response.getResponseCode() == ResponseCode.REGISTRATION_FAILURE) {
            return null;
        }
        return response.getUser();
    }
}
