package sample.network;

import sample.Model.Plane;

import java.io.Serializable;

public class Request implements Serializable {

    private String code;
    private Plane plane;
    private Long id;

    public Request(String code) {
        this.code = code;
    }

    public Request(String code, Long id) {
        this.code = code;
        this.id = id;
    }

    public Request(String code, Plane plane) {
        this.code = code;
        this.plane = plane;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
