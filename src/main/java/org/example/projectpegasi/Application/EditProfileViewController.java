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



    public String OnSafePasswordButtonClick() throws SQLException, ClassNotFoundException
    {
        int UserID = 1;

        String OldPasswordWritten = OldPasswordEdit.getText().toString();

        //Check if it is correct password through database

    DataAccessObject DAOPassword = new DataAccessObject();
    String OldPassword = DAOPassword.getPassword(UserID);
    if (OldPassword.equals(OldPasswordWritten))
    {
        String NewPassword = NewPasswordEdit.getText().toString();
        String RepeatNewPassword = RepeatNewPasswordEdit.getText().toString();
        //Check of these to are the same
        if (NewPassword.equals(RepeatNewPassword))
        {
            //Replace/UPDATE the Password on the User in database
            String NewPasswordToUpdate = NewPasswordEdit.getText().toString();
            DataAccessObject DAONewPassword = new DataAccessObject();
            DAONewPassword.changePassword(NewPasswordToUpdate,UserID);
            System.out.println("Change password to" + NewPasswordToUpdate);

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

    public void onSaveButtonClickEdit()
    {

    }

    public void onCancelButtonClickEdit()
    {
        HelloApplication.changeScene(ControllerNames.ProfileView);
    }

}
