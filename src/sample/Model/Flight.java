package sample.Model;

import java.sql.Time;
import java.sql.Date;

public class Flight {

    private Long id;
    private City fromCity;
    private City toCity;
    private Time duration;
    private Date start_date_time;
    private Date end_date_time;

    public Flight(Long id, City fromCity, City toCity, Time duration, Date start_date_time, Date end_date_time) {
        this.id = id;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.duration = duration;
        this.start_date_time = start_date_time;
        this.end_date_time = end_date_time;
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

    public Date getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(Date start_date_time) {
        this.start_date_time = start_date_time;
    }

    public Date getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(Date end_date_time) {
        this.end_date_time = end_date_time;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", fromCity=" + fromCity +
                ", toCity=" + toCity +
                ", duration=" + duration +
                ", start_date_time=" + start_date_time +
                ", end_date_time=" + end_date_time +
                '}';
    }
}
