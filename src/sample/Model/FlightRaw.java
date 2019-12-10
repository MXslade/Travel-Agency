package sample.Model;

import java.io.Serializable;

public class FlightRaw implements Serializable {

    private Long id;
    private Long from_city;
    private Long to_city;
    private Long duration;

    public FlightRaw(Long id, Long from_city, Long to_city, Long duration) {
        this.id = id;
        this.from_city = from_city;
        this.to_city = to_city;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFrom_city() {
        return from_city;
    }

    public void setFrom_city(Long from_city) {
        this.from_city = from_city;
    }

    public Long getTo_city() {
        return to_city;
    }

    public void setTo_city(Long to_city) {
        this.to_city = to_city;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
