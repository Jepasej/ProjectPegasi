package org.example.projectpegasi.Application;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.HelloApplication;

public class IncomingRequestViewController
{
    @FXML
    private TableView incomingRequestTable;

    @FXML
    private TableColumn<Match, String> jobTitleColumnMatch, companyColumnMatch;

    @FXML
    private Button goToMatchesButton, outgoingRequestButton, incomingRequestButton, backToProfileButton;

    // Her skal laves en initialize metode. SwapRequestManagers metoder til accept request og decline request
    // skal bruges her. Hardcode kolonner og indsæt enten "✔" eller "✘" alt afhængig af hvilken knap der trykkes
    // Brug metoden fra MatchViewController og omdøb navne.

    public void onMatchesButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.MatchView);
    }

    public void onOutgoingRequestsButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.OutgoingRequestView);
    }

    public void onBackToProfileButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.ProfileView);
    }
}
