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
    private String cityName;
    private String country;
    private double latitude;
    private double longitude;
    private String company;
    private Long price;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private Integer numberOfPassengers;
    private Long flightRawId;
    private FlightFull flightFull;
    private Long userId;

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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public Long getFlightRawId() {
        return flightRawId;
    }

    public void setFlightRawId(Long flightRawId) {
        this.flightRawId = flightRawId;
    }

    public FlightFull getFlightFull() {
        return flightFull;
    }

    public void setFlightFull(FlightFull flightFull) {
        this.flightFull = flightFull;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
