package Application;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreateProfileViewController {

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
}
