package sample.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerApp {

    private Connection connection = null;
    private ServerSocket serverSocket = null;

    public ServerApp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency?useUnicode=true&serverTimezone=UTC", "root", "");
            serverSocket = new ServerSocket(8080);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try{
                System.out.println("Waiting for a client...");

                Socket socket = serverSocket.accept();
                System.out.println("client connected " + socket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(socket, connection);
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ServerApp serverApp = new ServerApp();
        serverApp.start();
    }

}
