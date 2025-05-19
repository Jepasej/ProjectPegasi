package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


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
        try
        {
            //Load CreateProfileView.fxml
            Parent root = FXMLLoader.load(getClass().getResource("CreateProfileView.fxml"));

            //Get "nuværende" stage
            Stage stage = (Stage) createProfileButton.getScene().getWindow();
            //Change scene
            stage.setScene(new Scene(root));
            stage.setTitle("Create Profile");
            stage.show();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLoginButtonClick()
    {
    }
}

