package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.DomainModels.User;
import org.example.projectpegasi.JobSwapApplication;
import org.example.projectpegasi.Persistence.DAO;
import org.example.projectpegasi.Persistence.DataAccessObject;

/**
 * Controller for the Main View (login screen).
 * Handles user login, redirection to profile creation, and manages the current session state
 * by storing the logged-in user's ID and profile ID as static fields.
 */
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
     * Redirects the user to the Create Profile View.
     * Triggered by clicking the "Create Profile" button on the login screen.
     */
    public void onCreateProfileButtonClick()
    {
        JobSwapApplication.changeScene(ControllerNames.CreateProfileView);
    }

    /**
     * Gets the current user's ID (logged-in user).
     * @return the user ID of the current session
     */
    public static int getCurrentUserID()
    {
        return currentUserID;
    }

    /**
     * Gets the current user's profile ID.
     * @return the profile ID of the current session
     */
    public static int getCurrentProfileID() {
        return currentProfileID;
    }

    /**
     * Handles login attempt when "Login" button is clicked.
     * Validates input and shows error if credentials are wrong, otherwise navigates to Profile View.
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
            JobSwapApplication.changeScene(ControllerNames.ProfileView);
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
        if(!usernameField.getText().isBlank() || !passwordField.getText().isBlank())
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
