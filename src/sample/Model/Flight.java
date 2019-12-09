package sample.Model;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;

public class Flight implements Serializable {

    private Long id;
    private City fromCity;
    private City toCity;
    private Time duration;
    private Date startDateTime;
    private Date endDateTime;

    public Flight(Long id, City fromCity, City toCity, Time duration, Date startDateTime, Date endDateTime) {
        this.id = id;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.duration = duration;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", fromCity=" + fromCity +
                ", toCity=" + toCity +
                ", duration=" + duration +
                ", start_date_time=" + startDateTime +
                ", end_date_time=" + endDateTime +
                '}';
    }
}
