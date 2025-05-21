package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.DomainModels.User;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DAO;
import org.example.projectpegasi.Persistence.DataAccessObject;

public class CreateProfileViewController
{
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField jobTitleField;
    @FXML
    private ComboBox<String> jobFunctionComboBox;
    @FXML
    private ComboBox<String> companyComboBox;
    @FXML
    private TextField homeAddressField;
    @FXML
    private ComboBox<String> minSalaryComboBox;
    @FXML
    private ComboBox<String> distancePrefComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private boolean isUnique = false;

    /**
     * Method tied to CreateProfileView.fxml's SaveButton
     */
    @FXML
    public void onSaveButtonClick()
    {
        User user = new User(usernameField.getText(), passwordField.getText());

        isUnique = checkUniqueness(user.getUserName());
        if (isUnique)
        {
            checkMandatoryFields();
        }

        HelloApplication.changeScene(ControllerNames.ProfileView);
    }

    private void checkMandatoryFields()
    {
    }

    private boolean checkUniqueness(String name)
    {
        DAO dao = new DataAccessObject();
        dao.checkUsernameIsUnique(name);
        return false;
    }

    /**
     * Method tied to CreateProfileView.fxml's CancelButton
     */
    @FXML
    public void onCancelButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.MainView);
    }
}
