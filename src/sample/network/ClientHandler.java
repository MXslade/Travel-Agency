package sample.network;

import com.mysql.cj.xdevapi.SqlDataResult;
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

                List<FlightFull> flights = getOneWayFlight(request.getFromCity(), request.getToCity(),
                        request.getYear(), request.getMonth(), request.getDay());
                Response response = new Response();

                if (flights == null) {
                    response.setResponseCode(ResponseCode.ONE_WAY_FLIGHT_FAILURE);
                } else {
                    response.setFlightsFull(flights);
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

            } else if (request.getRequestCode() == RequestCode.ADD_CITY) {

                City city = addCity(request.getCityName(), request.getCountry(), request.getLatitude(), request.getLongitude());
                Response response = new Response();

                if (city == null) {
                    response.setResponseCode(ResponseCode.ADD_CITY_FAILURE);
                } else {
                    response.setCity(city);
                    response.setResponseCode(ResponseCode.ADD_CITY_SUCCESSFUL);
                    addRawFlightsFor(city);
                }

                try {
                    objectOutputStream.writeObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (request.getRequestCode() == RequestCode.ADD_FLIGHT_FULL) {

                FlightFull flightFull = addFlightFull(request.getCompany(), request.getFromCity(), request.getToCity(),
                        request.getPrice(), request.getYear(), request.getMonth(), request.getDay(),
                        request.getHour(), request.getMinute(), request.getNumberOfPassengers());
                Response response = new Response();

                if (flightFull == null) {
                    response.setResponseCode(ResponseCode.ADD_FLIGHT_FULL_FAILURE);
                } else {
                    response.setFlightFull(flightFull);
                    response.setResponseCode(ResponseCode.ADD_FLIGHT_FULL_SUCCESSFUL);
                }

                try {
                    objectOutputStream.writeObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (request.getRequestCode() == RequestCode.GET_FLIGHT_RAW) {

                FlightRaw flightRaw = getFlightRaw(request.getFlightRawId());
                Response response = new Response();

                if (flightRaw == null) {
                    response.setResponseCode(ResponseCode.GET_FLIGHT_RAW_FAILURE);
                } else {
                    response.setFlightRaw(flightRaw);
                    response.setResponseCode(ResponseCode.GET_FLIGHT_RAW_SUCCESSFUL);
                }

                try {
                    objectOutputStream.writeObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (request.getRequestCode() == RequestCode.BUY_TICKET) {

                boolean result = buyTicket(request.getUserId(), request.getFlightFull());
                Response response = new Response();

                if (!result) {
                    response.setResponseCode(ResponseCode.BUY_TICKET_FAILURE);
                } else {
                    response.setResponseCode(ResponseCode.BUY_TICKET_SUCCESSFUL);
                }

                try {
                    objectOutputStream.writeObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (request.getRequestCode() == RequestCode.SHOW_BOUGHT_FLIGHTS) {

                List<FlightFull> flights = getBoughtFlights(request.getUserId());
                Response response = new Response();

                if (flights == null) {
                    response.setResponseCode(ResponseCode.SHOW_BOUGHT_FLIGHTS_FAILURE);
                } else {
                    response.setFlightsFull(flights);
                    response.setResponseCode(ResponseCode.SHOW_BOUGHT_FLIGHTS_SUCCESSFUL);
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

    private List<FlightFull> getOneWayFlight(City fromCity, City toCity, Integer year, Integer month, Integer day) {
        List<FlightFull> flights = new ArrayList<>();
        FlightRaw flightRaw = null;
        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM flgihts_raw WHERE from_city = ? AND to_city = ?");
            ps.setLong(1, fromCity.getId());
            ps.setLong(2, toCity.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                Long duration = rs.getLong("duration");
                flightRaw = new FlightRaw(id, fromCity.getId(), toCity.getId(), duration);
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (flightRaw == null) {
            return null;
        }

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM flights_full WHERE flight_id = ? " +
                    "AND year = ? AND month = ? AND day = ?");
            ps.setLong(1, flightRaw.getId());
            ps.setInt(2, year);
            ps.setInt(3, month);
            ps.setInt(4, day);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String companyName = rs.getString("company_name");
                Long price = rs.getLong("price");
                Integer hour = rs.getInt("hour");
                Integer minute = rs.getInt("minute");
                Integer numberOfPassengers = rs.getInt("number_of_passengers");
                flights.add(new FlightFull(id, companyName, flightRaw.getId(), price, year, month, day, hour, minute, numberOfPassengers));
            }
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

    private City addCity(String name, String country, double latitude, double longitude) {

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM city WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                PreparedStatement ps2 = connection.prepareStatement("INSERT INTO city (id, name, country, longitude, latitude) VALUES(NULL, ?, ?, ?, ?)");
                ps2.setString(1, name);
                ps2.setString(2, country);
                ps2.setDouble(3, longitude);
                ps2.setDouble(4, latitude);
                ps2.executeUpdate();
                ps2.close();
            } else {
                ps.close();
                return null;
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getCity(name);
    }

    private City getCity(String name) {
        City city = null;

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM city WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("id");
                String country = rs.getString("country");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                city = new City(id, name, country, latitude, longitude);
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return city;
    }

    private void addRawFlightsFor(City city) {
        List<City> cities = getAllCitiesFromDb();
        List<FlightRaw> flightsRaw = new ArrayList<>();
        if (cities != null) {
            for (City c : cities) {
                if (!c.getId().equals(city.getId())) {
                    long minutesOfFlight = getMinutesOfFlight(city, c);
                    flightsRaw.add(new FlightRaw(null, city.getId(), c.getId(), minutesOfFlight));
                    flightsRaw.add(new FlightRaw(null, c.getId(), city.getId(), minutesOfFlight));
                }
            }
        }
        for (FlightRaw f : flightsRaw) {
            try {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO flgihts_raw (id, from_city, to_city, duration) VALUES (NULL, ?, ?, ?)");
                ps.setLong(1, f.getFrom_city());
                ps.setLong(2, f.getTo_city());
                ps.setLong(3, f.getDuration());
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private FlightFull addFlightFull(String company, City fromCity, City toCity, Long price, Integer year,
                                     Integer month, Integer day, Integer hour, Integer minute,
                                     Integer numberOfPassengers) {

        Long flight_id = null;

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM flgihts_raw WHERE from_city = ? AND to_city = ?");
            ps.setLong(1, fromCity.getId());
            ps.setLong(2, toCity.getId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                flight_id = rs.getLong("id");
            } else {
                return null;
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO flights_full (id, company_name, flight_id, " +
                    "price, year, month, day, hour, minute, number_of_passengers) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, company);
            ps.setLong(2, flight_id);
            ps.setLong(3, price);
            ps.setInt(4, year);
            ps.setInt(5, month);
            ps.setInt(6, day);
            ps.setInt(7, hour);
            ps.setInt(8, minute);
            ps.setInt(9, numberOfPassengers);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getFlightFull(company, flight_id, price, year, month, day, hour, minute, numberOfPassengers);

    }

    private FlightFull getFlightFull(String company, Long flight_id, Long price, Integer year,
                                     Integer month, Integer day, Integer hour, Integer minute,
                                     Integer numberOfPassengers) {

        FlightFull flightFull = null;

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM flights_full WHERE company_name = ? " +
                    "AND flight_id = ? AND price = ? AND year = ? AND month = ? AND day = ? AND hour = ? AND minute = ? " +
                    "AND number_of_passengers = ?");
            ps.setString(1, company);
            ps.setLong(2, flight_id);
            ps.setLong(3, price);
            ps.setInt(4, year);
            ps.setInt(5, month);
            ps.setInt(6, day);
            ps.setInt(7, hour);
            ps.setInt(8, minute);
            ps.setInt(9, numberOfPassengers);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("id");
                flightFull = new FlightFull(id, company, flight_id, price, year, month, day, hour, minute, numberOfPassengers);
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flightFull;
    }

    private FlightRaw getFlightRaw(Long id) {

        FlightRaw flightRaw = null;

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM flgihts_raw WHERE id = ? ");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long fromCity = rs.getLong("from_city");
                Long toCity = rs.getLong("to_city");
                Long duration = rs.getLong("duration");
                flightRaw = new FlightRaw(id, fromCity, toCity, duration);
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flightRaw;

    }

    private boolean buyTicket(long userId, FlightFull flightFull) {

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO user_flight (user_id, flights_full, price) VALUES(?, ?, ?)");
            ps.setLong(1, userId);
            ps.setLong(2, flightFull.getId());
            ps.setLong(3, flightFull.getPrice());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE flights_full SET number_of_passengers = ? WHERE id = ?");
            ps.setInt(1, flightFull.getNumberOfPassengers() - 1);
            ps.setLong(2, flightFull.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }

    private List<FlightFull> getBoughtFlights(Long userId) {
        List<FlightFull> flights = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM user_flight WHERE user_id = ?");
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ids.add(rs.getLong("flights_full"));
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            for (Long id : ids) {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM flights_full WHERE id = ?");
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Long fid = rs.getLong("id");
                    String company = rs.getString("company_name");
                    Long flight_id =rs.getLong("flight_id");
                    Long price = rs.getLong("price");
                    Integer year = rs.getInt("year");
                    Integer month = rs.getInt("month");
                    Integer day = rs.getInt("day");
                    Integer hour = rs.getInt("hour");
                    Integer minute = rs.getInt("minute");
                    Integer number_of_passengers = rs.getInt("number_of_passengers");
                    flights.add(new FlightFull(fid, company, flight_id, price, year, month, day, hour, minute, number_of_passengers));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return flights;
    }

    private long getMinutesOfFlight(City city1, City city2) {
        double R = 6371e3;

        double latInRadians1 = Math.toRadians(city1.getLatitude());
        double latInRadians2 = Math.toRadians(city2.getLatitude());
        double diffInLat = Math.toRadians(city2.getLatitude() - city1.getLatitude());
        double diffInLong = Math.toRadians(city2.getLongitude() - city1.getLongitude());

        double a = Math.sin(diffInLat / 2) * Math.sin(diffInLat / 2) +
                Math.cos(latInRadians1) * Math.cos(latInRadians2) *
                        Math.sin(diffInLong / 2) * Math.sin(diffInLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double d = R * c;
        if (d < 10) {
            d *= 1e5;
        } else {
            d /= 1e3;
        }
        double hoursOfFlight = d / 900;
        return Math.round(hoursOfFlight * 60);
    }

}
