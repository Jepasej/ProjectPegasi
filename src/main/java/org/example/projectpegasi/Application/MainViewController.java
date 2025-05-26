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

    private static int currentUserID; // Static variable to store the users ID

    private static int currentProfileID; // Static variable to store the profile ID

    /**
     * Method tied to MainView.fxml's CreateProfileButton.
     * Sends the user to the CreateProfileView
     */
    public void onCreateProfileButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.CreateProfileView);
    }

    public static int getCurrentUserID()
    {
        return currentUserID;
    }

    public static int getCurrentProfileID() {
        return currentProfileID;
    }

    /**
     * Method tied to MainView.fxml's LoginButton
     * Retrieves Username and Password from respective TextFields,
     * validates login and either sends user to ProfileView or
     * notifies user of wrong credentials.
     */
    @FXML
    public void onLoginButtonClick()
    {
        boolean isVerified = false;
        String username = usernameField.getText();
        String password = passwordField.getText();
        isVerified = validateLogin(username, password);

        if(isVerified)
        {
            HelloApplication.changeScene(ControllerNames.ProfileView);
        }
        else
            wrongLogin();
    }

    private void wrongLogin()
    {
        wrongCredentials.setVisible(true);
    }

    /**
     *  Validates the login by checking the username and password against the database.
     *  If login is successful, the method sets the userID and profileID in the login session.
     * @param username the entered user name
     * @param password the entered password
     * @return true if log-in is successful, otherwise false.
     */
    private boolean validateLogin(String username, String password)
    {
        if(!usernameField.getText().isEmpty() || !passwordField.getText().isEmpty())
        {
            User user = new User(usernameField.getText(), passwordField.getText());
            DAO dao = new DataAccessObject();
            boolean isVerified = dao.verifyUser(user);
            if(isVerified)
            {
                currentUserID = dao.getUserID(username);
                currentProfileID = dao.getProfileID(currentUserID); //Finds the profileID based upon the currently logged-in user
            }
            return isVerified;
        }
        return false;
    }
}
