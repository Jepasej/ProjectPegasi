package org.example.projectpegasi.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.DomainModels.Company;
import org.example.projectpegasi.DomainModels.JobFunction;
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

    List<Company> companies;

    private boolean isUsernameUnique = false;
    private boolean isFormCorrect = false;

    private String errorColour = "-fx-background-color: red;";

    @FXML
    public void initialize()
    {
        populateMinSalaryBox();
        populateDistanceBox();
    }

    private void loadCompanyBox()
    {
        DAO dao = new DataAccessObject();
        companies = dao.readAll(new Company());

        for(Company c : companies)
        {
            companyComboBox.getItems().add(c.getName());
        }
    }

    private void loadJobFunctionBox()
    {
        DAO dao = new DataAccessObject();
        List<JobFunction> jobFunctFromDB = dao.readAll(new JobFunction());

        for(JobFunction jf : jobFunctFromDB)
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

        String selected = jobFunctionComboBox.getValue();
        // Check if the selection is valid
        if (selected != null && !selected.isBlank())
        {
            String currentText = jobFunctionArea.getText();

            // Avoid adding duplicates
            if (!currentText.contains(selected))
            {
                if (!currentText.isBlank())
                {
                    jobFunctionArea.appendText("\n" + selected);
                } else {
                    jobFunctionArea.setText(selected);
                }
            }
        }
    }

    private void populateDistanceBox()
    {
        for(int i = 0; i < 20; i++)
        {
            int dist = i*5;
            distancePrefComboBox.getItems().add(dist + " km");
        }
    }

    private void populateMinSalaryBox()
    {
        for(int i = 0; i < 50; i++)
        {
            int sal = i*1000;
            minSalaryComboBox.getItems().add(String.valueOf(sal));
        }
    }

    @FXML
    public void onCompanySelected()
    {
        loadCompanyBox();

        for(Company c : companies)
        {
            if(c.getName().equals(companyComboBox.getValue()))
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
            User user = setupUser();

            createUser(user);

            clearFields();

            HelloApplication.changeScene(ControllerNames.MainView);
        }
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

        if(!passwordField.getText().equals(repeatPasswordField.getText()))
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
        jobFunctionArea.clear();
        companyComboBox.getItems().clear();
        jobFunctionComboBox.getItems().clear();
        minSalaryComboBox.getItems().clear();
        distancePrefComboBox.getItems().clear();
    }

    /**
     * Verifies uniqueness of entered username
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
        profile.setJobFunction(jobFunctionArea.getText());

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
        clearFields();
        HelloApplication.changeScene(ControllerNames.MainView);
    }
}
