package sample.network;

import sample.Model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FillCitiesInDb {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("text.txt"));
        List<City>  cities = new ArrayList<>();
        while (scanner.hasNextLine()) {
            StringBuffer line = new StringBuffer(scanner.nextLine());
            cities.add(new City(null, getCityName(line), getCountry(line), getLongitude(line), getLatitude(line)));
        }
        System.out.println(cities);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency?useUnicode=true&serverTimezone=UTC", "root", "");
            for (City c : cities) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT into city (id, name, country, longitude, latitude) VALUES(NULL, ?, ?, ?, ?)");
                preparedStatement.setString(1, c.getName());
                preparedStatement.setString(2, c.getCountryName());
                preparedStatement.setDouble(3, c.getLongitude());
                preparedStatement.setDouble(4, c.getLatitude());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getCityName(StringBuffer line) {
        String res = line.substring(0, line.indexOf(","));
        line.delete(0, line.indexOf(",") + 2);
        return res;
    }

    private static String getCountry(StringBuffer line) {
        int index = 0;
        for (int i = 0; i < line.length(); ++i) {
            if (line.charAt(i) >= '0' && line.charAt(i) <= '9') {
                index = i;
                break;
            }
        }
        String res = line.substring(0, index);
        line.delete(0, index);
        return res;
    }

    private static double getLongitude(StringBuffer line) {
        int longitude1 = 0;
        int i = 0;
        for (; i < line.length(); ++i) {
            if (line.charAt(i) >= '0' && line.charAt(i) <= '9') {
                longitude1 = longitude1 * 10 + line.charAt(i) - '0';
            } else {
                break;
            }
        }
        int longitude2 = 0;
        ++i;
        for (; i < line.length(); ++i) {
            if (line.charAt(i) >= '0' && line.charAt(i) <= '9') {
                longitude2 = longitude2 * 10 + line.charAt(i) - '0';
            } else {
                break;
            }
        }
        ++i;
        String dir = String.valueOf(line.charAt(i));
        i += 2;
        line.delete(0, i);
        return ( longitude1 + (double) longitude2 / 60 ) * (dir.equals("N") ? 1 : -1);
    }

    private static double getLatitude(StringBuffer line) {
        int lat1 = 0;
        int i = 0;
        for (; i < line.length(); ++i) {
            if (line.charAt(i) >= '0' && line.charAt(i) <= '9') {
                lat1 = lat1 * 10 + line.charAt(i) - '0';
            } else {
                break;
            }
        }
        int lat2 = 0;
        ++i;
        for (; i < line.length(); ++i) {
            if (line.charAt(i) >= '0' && line.charAt(i) <= '9') {
                lat2 = lat2 * 10 + line.charAt(i) - '0';
            } else {
                break;
            }
        }
        ++i;
        String dir = String.valueOf(line.charAt(i));
        i += 2;
        line.delete(0, i);
        return ( lat1 + (double) lat2 / 60 ) * (dir.equals("E") ? 1 : -1);
    }

}
