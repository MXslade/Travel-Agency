package sample.network;

import javafx.scene.chart.ScatterChart;
import sample.Model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FillRawFlightsInDb {

    public static void main(String[] args) {
        List<City> cities = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency?useUnicode=true&serverTimezone=UTC", "root", "");
            PreparedStatement ps = connection.prepareStatement("Select * from city");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                double longitude = rs.getDouble("longitude");
                double latitude = rs.getDouble("latitude");
                cities.add(new City(id, name, country, latitude, longitude));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < cities.size(); ++i) {
            for (int j = 0; j < cities.size(); ++j) {
                if (i == j) {
                    continue;
                }
                double R = 6371e3;

                double latInRadians1 = Math.toRadians(cities.get(i).getLatitude());
                double latInRadians2 = Math.toRadians(cities.get(j).getLatitude());
                double diffInLat = Math.toRadians(cities.get(j).getLatitude() - cities.get(i).getLatitude());
                double diffInLong = Math.toRadians(cities.get(j).getLongitude() - cities.get(i).getLongitude());

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
                long minutesOfFlight = Math.round(hoursOfFlight * 60);
                try {
                    PreparedStatement ps = connection.prepareStatement("INSERT into flgihts_raw (id, from_city, to_city, duration) VALUES(NULL, ?, ?, ?)");
                    ps.setInt(1, cities.get(i).getId().intValue());
                    ps.setInt(2, cities.get(j).getId().intValue());
                    ps.setInt(3, (int) minutesOfFlight);
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
