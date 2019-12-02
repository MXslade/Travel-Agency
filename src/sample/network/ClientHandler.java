package sample.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;

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
            if (request.getCode() == Code.SHOW_ONE_WAY_FLIGHT) {

            } else if (request.getCode() == Code.SHOW_BACK_AND_FORTH_FLIGHT) {

            } else if (request.getCode() == Code.SHOW_MULTIPLE_FLIGHT) {

            }
        }
    }

}
