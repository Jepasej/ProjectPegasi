package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.DomainModels.Company;
import org.example.projectpegasi.DomainModels.User;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DAO;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.LinkedList;
import java.util.List;

public class CreateProfileViewController
{
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField repeatPasswordField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField jobTitleField;
    @FXML
    private ComboBox<String> jobFunctionComboBox;
    @FXML
    private TextArea jobFunctionArea;
    @FXML
    private ComboBox<String> companyComboBox;
    @FXML
    private TextField companyField;
    @FXML
    private TextField homeAddressField, currentSalaryField;
    @FXML
    private ComboBox<String> minSalaryComboBox;
    @FXML
    private ComboBox<String> distancePrefComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private boolean isUsernameUnique = false;
    private boolean isFormCorrect = false;

    private String errorColour = "-fx-background-color: red;";

    /**
     * Method tied to CreateProfileView.fxml's SaveButton
     */
    @FXML
    public void onSaveButtonClick()
    {

//        Company c = new Company("Novo Nordisk", "Novo Allé 1, 2880 Bagsværd");
//        Profile p = new Profile("ErikErik", "Finanser", "Lillegade 8", 24000, 18000,"20km", c);
//        User u = new User("Erik", "ErikdenRode", p);
//        createUser(u);


        isFormCorrect = checkMandatoryFields();
        isUsernameUnique = checkUniqueness(usernameField.getText());

        if (isFormCorrect && isUsernameUnique)
        {
            User user = setupUser();

            createUser(user);

            HelloApplication.changeScene(ControllerNames.MainView);
        }
    }

    /**
     * Verifies whether fields denoted as Mandatory in UI have been filled out.
     */
    private boolean checkMandatoryFields()
    {
        //Create LinkedList of mandatory inputs.
        List<TextField> mandatoryInput = new LinkedList<>();
        mandatoryInput.add(usernameField);
        mandatoryInput.add(passwordField);
        mandatoryInput.add(repeatPasswordField);
        mandatoryInput.add(fullNameField);
        mandatoryInput.add(jobTitleField);
        mandatoryInput.add(companyField);
        mandatoryInput.add(homeAddressField);
        mandatoryInput.add(currentSalaryField);

        boolean check = true;
        for(TextField t : mandatoryInput)
        {
            if(t.getText().isBlank())
            {
                t.setStyle(errorColour);
                check = false;
            }
        }

        if(jobFunctionArea.getText().isBlank())
        {
            jobFunctionArea.setStyle(errorColour);
            check = false;
        }

        //HER SKAL VAERE VALIDERING AF MIN SALARY og DIST PREF I SAMME STIL SOM OVENSTAAENDE


        return check;
    }

    /**
     * Verifies uniqueness of entered username
     * @param name userinput of requested username.
     * @return true if username does not exist in database. False if it does.
     */
    private boolean checkUniqueness(String name)
    {
        DAO dao = new DataAccessObject();
        dao.checkUsernameIsUnique(name);
        return false;
    }

    private User setupUser()
    {
        User user = new User();

        user.setUserName(usernameField.getText());
        user.setPassword(passwordField.getText());

        Profile profile = setupProfile();

        user.setProfile(profile);

        return user;
    }

    private Profile setupProfile()
    {
        Profile profile = new Profile();

        profile.setFullName(fullNameField.getText());
        profile.setJobTitle(jobTitleField.getText());
        profile.setHomeAddress(homeAddressField.getText());
        profile.setWage(Integer.parseInt(currentSalaryField.getText()));
        profile.setPayPref(Integer.parseInt(minSalaryComboBox.getValue()));
        profile.setDistPref(distancePrefComboBox.getValue());
        profile.setCompany(new Company(companyField.getText()));

        return profile;
    }

    private void createUser(User user)
    {
        DAO dao = new DataAccessObject();

        dao.createUser(user);
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
