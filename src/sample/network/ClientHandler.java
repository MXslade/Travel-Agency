package sample.network;

import sample.Model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {

    private Socket socket;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;
    private Connection connection;

    public ClientHandler(Socket socket, Connection connection) {
        this.socket = socket;
        this.connection = connection;

        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            Request request = null;

            try {
                request = (Request) objectInputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
                continue;
            }

            if (request.getRequestCode() == RequestCode.SHOW_ONE_WAY_FLIGHT) {

                List<Flight> flights = getOneWayFlight(request.getFromCity(), request.getToCity(), request.getFromDate());
                Response response = new Response();

                if (flights == null) {
                    response.setResponseCode(ResponseCode.ONE_WAY_FLIGHT_FAILURE);
                } else {
                    response.setFlights(flights);
                    response.setResponseCode(ResponseCode.ONE_WAY_FLIGHT_SUCCESSFUL);
                }

                try {
                    objectOutputStream.writeObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (request.getRequestCode() == RequestCode.SHOW_BACK_AND_FORTH_FLIGHT) {

            } else if (request.getRequestCode() == RequestCode.SHOW_MULTIPLE_FLIGHT) {

            } else if (request.getRequestCode() == RequestCode.SHOW_ALL_CITIES) {

                List<City> cities = getAllCitiesFromDb();
                Response response = new Response();

                if (cities == null) {
                    response.setResponseCode(ResponseCode.ALL_CITIES_FAILURE);
                } else {
                    response.setCities(cities);
                    response.setResponseCode(ResponseCode.ALL_CITIES_SUCCESSFUL);
                }

                try {
                    objectOutputStream.writeObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (request.getRequestCode() == RequestCode.LOGIN) {

                User user = getUser(request.getLogin(), request.getPassword());
                Response response = new Response();

                if (user == null) {
                    response.setResponseCode(ResponseCode.LOGIN_FAILURE);
                } else {
                    response.setUser(user);
                    response.setResponseCode(ResponseCode.LOGIN_SUCCESSFUL);
                }

                try {
                    objectOutputStream.writeObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (request.getRequestCode() == RequestCode.REGISTRATION) {

                User user = registerUser(request.getLogin(), request.getPassword(), request.getName(), request.getSurname());
                Response response = new Response();

                if (user == null) {
                    response.setResponseCode(ResponseCode.REGISTRATION_FAILURE);
                } else {
                    response.setUser(user);
                    response.setResponseCode(ResponseCode.REGISTRATION_SUCCESSFUL);
                }

                try {
                    objectOutputStream.writeObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (request.getRequestCode() == RequestCode.EXIT) {
                try {
                    objectOutputStream.close();
                    objectInputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private List<City> getAllCitiesFromDb() {
        List<City> cities = new ArrayList<>();

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * from city");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                double longitude = rs.getDouble("longitude");
                double latitude = rs.getDouble("latitude");
                cities.add(new City(id, name, country, latitude, longitude));
            }

            ps.close();

        } catch (SQLException e) {
            cities = null;
        }
        return cities;
    }

    private List<Flight> getOneWayFlight(City fromCity, City toCity, Date fromDate) {
        List<Flight> flights = new ArrayList<>();
        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM flight WHERE from_city = ? AND to_city = ? AND start_date_time = ?");
            ps.setLong(1, fromCity.getId());
            ps.setLong(2, toCity.getId());
            ps.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(fromDate));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                Time duration = rs.getTime("duration");
                duration.setTime(duration.getTime() - 6 * 3600 * 1000);
                Date startDateTime = rs.getDate("start_date_time");
                Date endDateTime = rs.getDate("end_date_time");
                flights.add(new Flight(id, fromCity, toCity, duration, startDateTime, endDateTime));
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            flights = null;
        }

        return flights;
    }

    private User getUser(String login, String password) {
        User user = null;

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String role =rs.getString("role");
                user = new User(id, name, surname, login, password, role);
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User registerUser(String login, String password, String name, String surname) {

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                PreparedStatement ps2 = connection.prepareStatement("INSERT INTO users (id, name, surname, login, password, role) VALUES(NULL, ?, ?, ?, ?, ?)");
                ps2.setString(1, name);
                ps2.setString(2, surname);
                ps2.setString(3, login);
                ps2.setString(4, password);
                ps2.setString(5, "user");
                ps2.executeUpdate();
                ps2.close();
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getUser(login, password);
    }

}
