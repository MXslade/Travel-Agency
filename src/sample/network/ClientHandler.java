package sample.network;

import sample.Model.City;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            }
            if (request.getRequestCode() == RequestCode.SHOW_ONE_WAY_FLIGHT) {

            } else if (request.getRequestCode() == RequestCode.SHOW_BACK_AND_FORTH_FLIGHT) {

            } else if (request.getRequestCode() == RequestCode.SHOW_MULTIPLE_FLIGHT) {

            } else if (request.getRequestCode() == RequestCode.SHOW_ALL_CITIES) {
                Response response = new Response();
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
                    response.setCities(cities);
                    response.setResponseCode(ResponseCode.ALL_CITIES_SUCCESSFUL);
                    objectOutputStream.writeObject(response);
                } catch (SQLException | IOException e) {
                    response.setResponseCode(ResponseCode.ALL_CITIES_FAILURE);
                    e.printStackTrace();
                }
            }
        }
    }

}
