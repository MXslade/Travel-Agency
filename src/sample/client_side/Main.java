package sample.client_side;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent mainParent = FXMLLoader.load(getClass().getResource("fxmls/main.fxml"));
        mainScene = new Scene(mainParent);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
