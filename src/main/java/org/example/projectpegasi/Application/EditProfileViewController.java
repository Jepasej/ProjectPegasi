package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.sql.SQLException;

import static org.example.projectpegasi.Application.MainViewController.getCurrentUserID;


/**
 * Controller for the Edit Profile view.
 * Handles password changes and profile updates through DAO calls.
 */
public class EditProfileViewController
{
    @FXML
    private TextField OldPasswordEdit, NewPasswordEdit, RepeatNewPasswordEdit, fullNameFieldEdit;
    @FXML
    private TextField jobTitleFieldEdit, homeAddressFieldEdit;
    @FXML
    private Button SaveNewPassword, saveButtonEdit, cancelButtonEdit;
    @FXML
    private ComboBox<String> jobTitleComboBox, companyComboBoxEdit, minSalaryComboBoxEdit, distancePrefComboBoxEdit;
    @FXML
    private Label WrongPasswordLabel;


    /**
     *  Verifies that the old password entered by the user matches the one stored in the database.
     *  If correct, it checks whether the new password and repeat password match.
     *  If both validations pass, the user's password is updated in the database.
     *
     *  @return the old password entered by the user
     *  @throws SQLException           if a database access error occurs
     *  @throws ClassNotFoundException if the SQL driver class is not found
     */
    public String OnSafePasswordButtonClick() throws SQLException, ClassNotFoundException
    {
        int UserID = getCurrentUserID();

        String OldPasswordWritten = OldPasswordEdit.getText().toString();

        //Check if it is correct password through database

    DataAccessObject DAOPassword = new DataAccessObject();
    String OldPassword = DAOPassword.getPassword(UserID);
    if (OldPassword.equals(OldPasswordWritten))
    {
        String NewPassword = NewPasswordEdit.getText();
        String RepeatNewPassword = RepeatNewPasswordEdit.getText();
        //Check of these to are the same
        if (NewPassword.equals(RepeatNewPassword))
        {
            //Replace/UPDATE the Password on the User in database
            String NewPasswordToUpdate = NewPasswordEdit.getText();
            DataAccessObject DAONewPassword = new DataAccessObject();
            DAONewPassword.changePassword(NewPasswordToUpdate,UserID);
            NewPasswordEdit.clear();
            RepeatNewPasswordEdit.clear();
            OldPasswordEdit.clear();
            //System.out.println("Change password to" + NewPasswordToUpdate);
        }
        else
        {
            WrongPasswordLabel.setText("Password doesn't match");
        }
    }
    else
    {
        WrongPasswordLabel.setText("Wrong Password");
    }
        return OldPasswordWritten;
    }

    /**
     * Saves updated profile information for the currently logged-in user.
     * If the mandatory field (full name) is missing, the operation is aborted and an error label is shown.
     *
     * @throws SQLException           if a database access error occurs
     * @throws ClassNotFoundException if the SQL driver class is not found
     */
    public void onSaveButtonClickEdit() throws SQLException, ClassNotFoundException
    {
        int UserID = getCurrentUserID();
        String newFullName = fullNameFieldEdit.getText(); //Mandatory
        String jobTitle = jobTitleFieldEdit.getText();
        String homeAddress = homeAddressFieldEdit.getText();
        String company = companyComboBoxEdit.getSelectionModel().getSelectedItem();
        String minSalary = minSalaryComboBoxEdit.getSelectionModel().getSelectedItem();
        String distancePref = distancePrefComboBoxEdit.getSelectionModel().getSelectedItem();

        if(newFullName.isEmpty())
        {
            WrongPasswordLabel.setText("Name is Mandatory, please state your name");
        }
        else
        {
            DataAccessObject EditProfile = new DataAccessObject();
            EditProfile.SafeEditProfileData(UserID,newFullName,jobTitle,homeAddress,company,minSalary,distancePref);
        }


    }

    /**
     * Cancels the edit operation and navigates the user back to the Profile View.
     */
    public void onCancelButtonClickEdit()
    {
        HelloApplication.changeScene(ControllerNames.ProfileView);
    }

}
