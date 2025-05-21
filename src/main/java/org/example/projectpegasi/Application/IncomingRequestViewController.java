package org.example.projectpegasi.Application;

import javafx.event.ActionEvent;

import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.HelloApplication;

public class IncomingRequestViewController
{

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
