package org.example.projectpegasi.Application;

import javafx.event.ActionEvent;

import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.HelloApplication;

public class IncomingRequestViewController
{


    public void onOutgoingRequestsButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.OutgoingRequestView);
    }
    public void onMatchesButtonClick(ActionEvent actionEvent) {
    }

    public void onOutgoingRequestsButtonClick(ActionEvent actionEvent) {
    }

    public void onIncomingRequestsButtonClick(ActionEvent actionEvent) {
    }
}
