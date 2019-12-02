package sample.network;

import java.sql.Connection;

public class DBFiller extends Thread {

    private Connection connection;

    public DBFiller(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        double time = 0;
        while (true) {
            //TODO create algorithm to randomly create flights and push them to the db and figure out how much time delay I need to add new flight

        }
    }
}
