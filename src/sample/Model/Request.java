package sample.Model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Request implements Serializable {

    private RequestCode requestCode;
    private City fromCity;
    private City toCity;
    private List<City> fromCities;
    private List<City> toCities;
    private Date fromDate;
    private Date toDate;
    private List<Date> formDates;
    private List<Date> toDates;
    private String login;
    private String password;
    private String name;
    private String surname;

    public RequestCode getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(RequestCode requestCode) {
        this.requestCode = requestCode;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List<Date> getFormDates() {
        return formDates;
    }

    public void setFormDates(List<Date> formDates) {
        this.formDates = formDates;
    }

    public List<Date> getToDates() {
        return toDates;
    }

    public void setToDates(List<Date> toDates) {
        this.toDates = toDates;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
