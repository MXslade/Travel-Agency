package sample.network;

import sample.Model.City;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DBFiller {

    public static void main(String[] args) {
        List<City> cities = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency?useUnicode=true&serverTimezone=UTC", "root", "");
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
            ps.close();
            Random random = new Random();
            for (int i = 0; i < cities.size(); ++i) {
                for (int j = 0; j < cities.size(); ++j) {
                    //TODO calculate the distance
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
                    int fullHours = (int) hoursOfFlight;
                    int minutes = (int) ((hoursOfFlight - fullHours) * 60);
                    List<Integer> chosenDays = new ArrayList<>();
                    Date currentDate = new Date();
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                    while (chosenDays.size() < 10) {
                        int day = Math.abs(random.nextInt() % 1000);
                        if (chosenDays.contains(day)) {
                            break;
                        }
                        chosenDays.add(day);
                        calendar.setTime(currentDate);
                        calendar.add(Calendar.DATE, day);
                        calendar.add(Calendar.HOUR, Math.abs(random.nextInt() % 24));
                        calendar.add(Calendar.MINUTE, Math.abs(random.nextInt() % 60));
                        java.sql.Date starDateTime = new java.sql.Date(calendar.getTimeInMillis());
                        calendar.add(Calendar.HOUR, fullHours);
                        calendar.add(Calendar.MINUTE, minutes);
                        java.sql.Date endDateTime = new java.sql.Date(calendar.getTimeInMillis());
                        Time duration = new Time(endDateTime.getTime() - starDateTime.getTime());
                        ps = connection.prepareStatement("INSERT into flight (id, from_city, to_city, duration, start_date_time, end_date_time) VALUES (NULL, ?, ?, ?, ?, ?)");
                        ps.setInt(1, cities.get(i).getId().intValue());
                        ps.setInt(2, cities.get(j).getId().intValue());
                        ps.setTime(3, duration);
                        ps.setDate(4, starDateTime);
                        ps.setDate(5, endDateTime);
                        ps.executeUpdate();
                        ps.close();
                    }

                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
