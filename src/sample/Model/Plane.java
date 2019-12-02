package sample.Model;

import java.io.Serializable;
import java.util.Date;

public class Plane implements Serializable {

    private Long id;
    private String name;
    private Date date;
    private Integer numberOfPassengers;

    public Plane(Long id, String name, Date date, Integer numberOfPassengers) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.numberOfPassengers = numberOfPassengers;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", numberOfPassengers=" + numberOfPassengers +
                '}';
    }
}
