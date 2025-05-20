package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    /**
     * Method tied to CreateProfileView.fxml's SaveButton
     */
    @FXML
    public void onSaveButtonClick()
    {

    }

    /**
     * Method tied to CreateProfileView.fxml's CancelButton
     */
    @FXML
    public void onCancelButtonClick()
    {

    }
}
