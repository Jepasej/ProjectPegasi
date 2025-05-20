package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ProfileViewController
{
    @FXML
    private Button exitProfileViewButt, showMoreMatchesButt, showMoreRequestButt;

    @FXML
    private Label profileNameLbl, jobTitleLbl, jobFunctionLbl, companyNameLbl, homeAddressLbl, wageLbl, payPrefLbl, distPrefLbl;

    @FXML
    private ListView<String> recentMatchesLV;

    @FXML
    private ListView<String> recentRequestLV;

    @FXML
    protected void exitProfileButtOnAction()
    {
        System.out.println("Exiting Profile View");

    }
}
