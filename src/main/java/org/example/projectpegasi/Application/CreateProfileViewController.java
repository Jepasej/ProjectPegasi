package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.BusinessService.MatchManager;
import org.example.projectpegasi.DomainModels.Company;
import org.example.projectpegasi.DomainModels.JobFunction;
import org.example.projectpegasi.DomainModels.User;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DAO;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Controller class for handling profile creation in the JavaFX UI.
 * This class is connected to CreateProfileView.fxml and manages user input, validation,
 * confirmation display, and persistence of new user and profile data.
 * It interacts with the DAO layer for database operations and triggers the MatchManager for match updates.
 */
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
    private ComboBox<String> companyComboBox;
    @FXML
    private TextField companyField;
    @FXML
    private TextField homeAddressField, currentSalaryField, jobFunctionField;
    @FXML
    private ComboBox<String> minSalaryComboBox;
    @FXML
    private ComboBox<String> distancePrefComboBox;
    @FXML
    private Label usernameLabel, passwordLabel, fullNameLabel, jobTitleLabel, jobFunctionLabel, companyLabel, homeAddressLabel, minSalLabel, curSalLabel, maxDistLabel;
    @FXML
    private VBox confirmationVBox, formVBox;

    private List<Company> companies;
    private List<JobFunction> jobFunctions;

    private boolean isUsernameUnique = false;
    private boolean isFormCorrect = false;

    private String errorColour = "-fx-background-color: red;";

    /**
     * Initializes combo boxes for minimum salary and distance preference.
     * Automatically called when the view is loaded.
     */
    @FXML
    public void initialize()
    {
        populateMinSalaryBox();
        populateDistanceBox();
    }

    @FXML
    private void onConfirmButtonClick()
    {
        User user = setupUser();

        createUser(user);

        clearFields();

        MatchManager matchManager = new MatchManager();
        matchManager.findAllMatches();

        HelloApplication.changeScene(ControllerNames.MainView);
    }

    @FXML
    private void onBackButtonClick()
    {
        changeVisibility();
    }

    private void changeVisibility()
    {
        if(formVBox.isVisible())
        {
            formVBox.setVisible(false);
            confirmationVBox.setVisible(true);
        }
        else
        {
            confirmationVBox.setVisible(false);
            formVBox.setVisible(true);
        }
    }

    private void loadCompanyBox()
    {
        DAO dao = new DataAccessObject();
        companies = dao.readAll(new Company());

        for (Company c : companies)
        {
            companyComboBox.getItems().add(c.getName());
        }
    }

    private void loadJobFunctionBox()
    {
        DAO dao = new DataAccessObject();
        jobFunctions = dao.readAll(new JobFunction());

        for (JobFunction jf : jobFunctions)
        {
            jobFunctionComboBox.getItems().add(jf.getJobFunction());
        }

    }

    /**
     * Adds the selected job function from the ComboBox to the TextArea.
     * Ensures the same job function is not added multiple times.
     */
    @FXML
    private void onJobFunctionSelected()
    {
        loadJobFunctionBox();

        for (JobFunction jf : jobFunctions)
        {
            if (jf.getJobFunction().equals(jobFunctionComboBox.getValue()))
            {
                jobFunctionField.setText(jf.getJobFunction());
            }
        }
    }

    private void populateDistanceBox()
    {
        for (int i = 0; i < 20; i++)
        {
            int dist = i * 5;
            distancePrefComboBox.getItems().add(dist + " km");
        }
    }

    private void populateMinSalaryBox()
    {
        for (int i = 0; i < 50; i++)
        {
            int sal = i * 1000;
            minSalaryComboBox.getItems().add(String.valueOf(sal));
        }
    }

    /**
     * Populates the company field based on the selected company from the combo box.
     * Also loads company address for preview.
     */
    @FXML
    public void onCompanySelected()
    {
        loadCompanyBox();

        for (Company c : companies)
        {
            if (c.getName().equals(companyComboBox.getValue()))
            {
                companyField.setText(c.getName() + ", " + c.getAddress());
            }
        }
    }

    /**
     * Method tied to CreateProfileView.fxml's SaveButton
     * Creates a User and a Profile from user input.
     */
    @FXML
    public void onSaveButtonClick()
    {
        isFormCorrect = checkFields();
        isUsernameUnique = checkUniqueness(usernameField.getText());

        if (isFormCorrect && isUsernameUnique)
        {
            updateConfirmationLabels();
            changeVisibility();
        }
    }

    private void updateConfirmationLabels()
    {
        String password = "";
        for(int i = 0; i<passwordField.getText().length(); i++)
        {
            password += "*";
        }

        usernameLabel.setText(usernameField.getText());
        passwordLabel.setText(password);
        fullNameLabel.setText(fullNameField.getText());
        jobTitleLabel.setText(jobTitleField.getText());
        jobFunctionLabel.setText(jobFunctionField.getText());
        companyLabel.setText(companyField.getText());
        homeAddressLabel.setText(homeAddressField.getText());
        minSalLabel.setText(minSalaryComboBox.getValue());
        curSalLabel.setText(currentSalaryField.getText());
        maxDistLabel.setText(distancePrefComboBox.getValue());
    }

    /**
     * Verifies whether fields denoted as Mandatory in UI have been filled out.
     */
    private boolean checkFields()
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
        mandatoryInput.add(jobFunctionField);

        boolean check = true;

        for (TextField t : mandatoryInput)
        {
            if (t.getText().isBlank())
            {
                t.setStyle(errorColour);
                check = false;
            }
        }

        if (!passwordField.getText().equals(repeatPasswordField.getText()))
        {
            passwordField.setStyle(errorColour);
            passwordField.clear();
            repeatPasswordField.setStyle(errorColour);
            repeatPasswordField.clear();
        }

        return check;
    }

    private void clearFields()
    {
        usernameField.clear();
        passwordField.clear();
        repeatPasswordField.clear();
        fullNameField.clear();
        jobTitleField.clear();
        companyField.clear();
        homeAddressField.clear();
        currentSalaryField.clear();
        jobFunctionField.clear();
        companyComboBox.getItems().clear();
        jobFunctionComboBox.getItems().clear();
    }

    /**
     * Verifies uniqueness of entered username
     *
     * @param name userinput of requested username.
     * @return true if username does not exist in database. False if it does.
     */
    private boolean checkUniqueness(String name)
    {
        DAO dao = new DataAccessObject();

        return dao.checkUsernameIsUnique(name);
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
        profile.setCompany(new Company(companyComboBox.getValue()));
        profile.setJobFunction(jobFunctionField.getText());

        return profile;
    }

    private void createUser(User user)
    {
        DAO dao = new DataAccessObject();

        dao.createUser(user);
    }

    /**
     * Method tied to CreateProfileView.fxml's CancelButton
     * Triggered when the cancel button is clicked.
     *  * Clears all input fields and navigates back to the main view.
     */
    @FXML
    public void onCancelButtonClick()
    {
        clearFields();
        HelloApplication.changeScene(ControllerNames.MainView);
    }
}
