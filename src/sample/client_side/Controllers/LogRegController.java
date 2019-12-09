package sample.client_side.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Model.User;
import sample.client_side.Main;
import sample.client_side.ServerConnector;

public class LogRegController {

    private ServerConnector serverConnector;

    @FXML
    private TextField loginLoginTextField;
    @FXML
    private PasswordField passwordLoginPasswordField;
    @FXML
    private TextField nameRegTextField;
    @FXML
    private TextField surnameRegTextField;
    @FXML
    private TextField loginRegTextField;
    @FXML
    private PasswordField passwordRegPasswordField;

    @FXML
    private void initialize() {
        serverConnector = Main.serverConnector;
    }

    @FXML
    private void showMain(MouseEvent mouseEvent) {
        Main.showMainScene();
    }

    @FXML
    private void enterPressed(MouseEvent mouseEvent) {
        String login = loginLoginTextField.getText();
        String password = passwordLoginPasswordField.getText();
        User user = serverConnector.login(login, password);
        if (user != null) {
            Main.currentUser = user;
            Main.showAccountScene();
        } else {
            //TODO error message
        }
    }

    @FXML
    private void regPressed(MouseEvent mouseEvent) {
        String login = loginRegTextField.getText();
        String password = passwordRegPasswordField.getText();
        String name = nameRegTextField.getText();
        String surname = surnameRegTextField.getText();
        if (login.isBlank() || password.isBlank() || name.isBlank() || surname.isBlank()) {
            return;
        }
        User user = serverConnector.register(login, password, name, surname);
        if (user != null) {
            Main.currentUser = user;
            Main.showAccountScene();
        } else {
            //TODO error message
        }
    }
}
