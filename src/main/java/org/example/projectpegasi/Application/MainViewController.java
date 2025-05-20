package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.DomainModels.User;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DAO;
import org.example.projectpegasi.Persistence.DataAccessObject;

public class MainViewController
{
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label wrongCredentials;

    private String[] credentials = new String[2];

    public void onCreateProfileButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.CreateProfileView);
    }

    @FXML
    public void onLoginButtonClick()
    {
        boolean isVerified = false;
        credentials[0] = usernameField.getText();
        credentials[1] = passwordField.getText();
        isVerified = validateLogin();

        if(isVerified)
            HelloApplication.changeScene(ControllerNames.ProfileView);
        else
            wrongLogin();
    }

    private void wrongLogin()
    {
        wrongCredentials.setVisible(true);
    }

    private boolean validateLogin()
    {
        User user = new User(usernameField.getText(), passwordField.getText());

        DAO dao = new DataAccessObject();

        return dao.verifyUser(user);
    }
}
