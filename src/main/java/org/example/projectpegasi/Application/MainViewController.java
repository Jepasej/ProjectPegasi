package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.HelloApplication;

public class MainViewController
{
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button createProfileButton;
    @FXML
    private Button loginButton;


    public void onCreateProfileButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.CreateProfileView);
    }

    @FXML
    public void onLoginButtonClick()
    {
    }
}
