package sample.Model;

import java.io.Serializable;

public class City implements Serializable {

    private Long id;
    private String name;
    private String countryName;
    private double longitude;
    private double latitude;

    public City(Long id, String name, String countryName, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.countryName = countryName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return name;
    }
}
