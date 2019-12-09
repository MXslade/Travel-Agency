package sample.client_side;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Model.User;

import java.io.IOException;

public class Main extends Application {

    public static ServerConnector serverConnector;

    public static User currentUser = null;

    private static Stage stage;

    private static Scene mainScene;
    private static Scene resultScene;
    private static Scene accountScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        Parent mainParent = FXMLLoader.load(getClass().getResource("fxmls/main.fxml"));

        mainScene = new Scene(mainParent);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(windowEvent -> serverConnector.close());
    }

    public static void showMainScene() {
        stage.setScene(mainScene);
    }

    public static void showResultScene() {
        try {
            Parent resultParent = FXMLLoader.load(Main.class.getResource("fxmls/result.fxml"));
            resultScene = new Scene(resultParent);
            stage.setScene(resultScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAccountScene() {
        if (currentUser == null) {
            try {
                Parent logRegParent = FXMLLoader.load(Main.class.getResource("fxmls/log_reg.fxml"));
                accountScene = new Scene(logRegParent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (currentUser.getRole().equals("admin")) {
            try {
                Parent adminAccountParent = FXMLLoader.load(Main.class.getResource("fxmls/admin_account.fxml"));
                accountScene = new Scene(adminAccountParent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (currentUser.getRole().equals("user")) {
            try {
                Parent userAccountParent = FXMLLoader.load(Main.class.getResource("fxmls/user_account.fxml"));
                accountScene = new Scene(userAccountParent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (accountScene != null) {
            stage.setScene(accountScene);
        }
    }

    public static void main(String[] args) {
        serverConnector = new ServerConnector();
        launch(args);
    }


}
