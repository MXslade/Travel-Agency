package sample.client_side;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerConnector {

    private Socket socket = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;

    private ObservableList<FlightFull> lastFlights;
    private ObservableList<City> cities;

    public ServerConnector() {
        lastFlights = FXCollections.observableArrayList();
        cities = FXCollections.observableArrayList();
        try {
            socket = new Socket("127.0.0.1", 8080);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<FlightFull> getLastFlights() {
        return lastFlights;
    }

    public void setLastFlights(ObservableList<FlightFull> lastFlights) {
        this.lastFlights = lastFlights;
    }

    public void setCities(ObservableList<City> cities) {
        this.cities = cities;
    }

    public ObservableList<City> getCities() {
        return cities;
    }

    public List<City> getCitiesFromDb() {
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
        cities.clear();
        cities.setAll(response.getCities());
        return response.getCities();
    }

    public List<FlightFull> getOneWayFlights(City fromCity, City toCity, Integer year, Integer month, Integer day) {

        Request request = new Request();
        request.setRequestCode(RequestCode.SHOW_ONE_WAY_FLIGHT);
        request.setFromCity(fromCity);
        request.setToCity(toCity);
        request.setYear(year);
        request.setMonth(month);
        request.setDay(day);
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
        lastFlights.setAll(response.getFlightsFull());
        return response.getFlightsFull();

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

    public City addCity(String name, String country, double latitude, double longitude) {
        Request request = new Request();
        request.setRequestCode(RequestCode.ADD_CITY);
        request.setCityName(name);
        request.setCountry(country);
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        Response response;
        try {
            oos.writeObject(request);
            response = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        if (response.getResponseCode() == ResponseCode.ADD_CITY_FAILURE) {
            return null;
        }
        cities.add(response.getCity());
        return response.getCity();
    }

    public FlightFull addFlightFull(String company, City fromCity, City toCity, Long price,
            Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer numberOfPassengers) {

        Request request = new Request();
        request.setRequestCode(RequestCode.ADD_FLIGHT_FULL);
        request.setCompany(company);
        request.setFromCity(fromCity);
        request.setToCity(toCity);
        request.setPrice(price);
        request.setYear(year);
        request.setMonth(month);
        request.setDay(day);
        request.setHour(hour);
        request.setMinute(minute);
        request.setNumberOfPassengers(numberOfPassengers);
        Response response;

        try {
            oos.writeObject(request);
            response = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        if (response.getResponseCode() == ResponseCode.ADD_FLIGHT_FULL_FAILURE) {
            return null;
        }

        return response.getFlightFull();
    }

    public FlightRaw getFlightRaw(Long id) {
        Request request = new Request();
        request.setRequestCode(RequestCode.GET_FLIGHT_RAW);
        request.setFlightRawId(id);
        Response response;

        try {
            oos.writeObject(request);
            response = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        if (response.getResponseCode() == ResponseCode.GET_FLIGHT_RAW_FAILURE) {
            return null;
        }

        return response.getFlightRaw();
    }

    public boolean buyTicket(FlightFull flightFull) {
        if (Main.currentUser == null) {
            return false;
        }
        Request request = new Request();
        request.setRequestCode(RequestCode.BUY_TICKET);
        request.setFlightFull(flightFull);
        request.setUserId(Main.currentUser.getId());
        Response response;

        try {
            oos.writeObject(request);
            response = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return response.getResponseCode() != ResponseCode.BUY_TICKET_FAILURE;
    }

    public ObservableList<FlightFull> getBoughtFlights() {
        if (Main.currentUser == null) {
            return null;
        }
        Request request = new Request();
        request.setRequestCode(RequestCode.SHOW_BOUGHT_FLIGHTS);
        request.setUserId(Main.currentUser.getId());
        Response response;

        try {
            oos.writeObject(request);
            response = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        if (response.getResponseCode() == ResponseCode.SHOW_BOUGHT_FLIGHTS_FAILURE) {
            return null;
        }

        lastFlights.clear();
        lastFlights.setAll(response.getFlightsFull());

        return lastFlights;
    }
}
