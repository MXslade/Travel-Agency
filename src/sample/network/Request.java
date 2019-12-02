package sample.network;

import sample.Model.City;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {

    private Code code;
    private City fromCity;
    private City toCity;
    private List<City> fromCities;
    private List<City> toCities;

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public City getFromCity() {
        return fromCity;
    }

    public void setFromCity(City fromCity) {
        this.fromCity = fromCity;
    }

    public City getToCity() {
        return toCity;
    }

    public void setToCity(City toCity) {
        this.toCity = toCity;
    }

    public List<City> getFromCities() {
        return fromCities;
    }

    public void setFromCities(List<City> fromCities) {
        this.fromCities = fromCities;
    }

    public List<City> getToCities() {
        return toCities;
    }

    public void setToCities(List<City> toCities) {
        this.toCities = toCities;
    }
}
