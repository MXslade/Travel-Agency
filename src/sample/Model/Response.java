package sample.Model;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    private ResponseCode responseCode;
    private List<Flight> flights;
    private List<City> cities;
    private User user;
    private City city;
    private FlightFull flightFull;
    private List<FlightFull> flightsFull;
    private FlightRaw flightRaw;

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public FlightFull getFlightFull() {
        return flightFull;
    }

    public void setFlightFull(FlightFull flightFull) {
        this.flightFull = flightFull;
    }

    public List<FlightFull> getFlightsFull() {
        return flightsFull;
    }

    public void setFlightsFull(List<FlightFull> flightsFull) {
        this.flightsFull = flightsFull;
    }

    public FlightRaw getFlightRaw() {
        return flightRaw;
    }

    public void setFlightRaw(FlightRaw flightRaw) {
        this.flightRaw = flightRaw;
    }
}
