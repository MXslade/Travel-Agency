package sample.network;

import sample.Model.City;
import sample.Model.Flight;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    private ResponseCode responseCode;
    private Flight flight;
    private List<Flight> flights;
    private List<City> cities;

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
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
}
