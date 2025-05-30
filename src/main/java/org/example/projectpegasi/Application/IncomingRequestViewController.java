package org.example.projectpegasi.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.BusinessService.SwapRequestManager;
import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.List;

public class IncomingRequestViewController
{
    @FXML
    private TableView<Match> incomingRequestTable;

    @FXML
    private TableColumn<Match, String> jobTitleColumnMatch, companyColumnMatch;

    @FXML
    private Button goToMatchesButton, outgoingRequestButton, incomingRequestButton, backToProfileButton;

    // Her skal laves en initialize metode. SwapRequestManagers metoder til accept request og decline request
    // skal bruges her. Hardcode kolonner og indsæt enten "✔" eller "✘" alt afhængig af hvilken knap der trykkes
    // Brug metoden fra MatchViewController og omdøb navne.

    @FXML
    public void initialize() {
        SwapRequestManager srManager = new SwapRequestManager();

        // Creates a column with a "✔" button that allows the user to accept a request.
        // When clicked, it creates a jobswap using the match's ID.
        TableColumn<Match, Void> acceptRequestColumnMatch = new TableColumn<>("Accept Request");
        acceptRequestColumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button acceptRequestButton = new Button("✔");

            {
                acceptRequestButton.setOnAction(event -> {
                    Match match = getTableView().getItems().get(getIndex());
                    try {
                        srManager.acceptSwapRequest(match.getMatchID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                setGraphic(acceptRequestButton); //Show button in cell
            }
        });
        incomingRequestTable.getColumns().add(acceptRequestColumnMatch); // Add the column to table

        // Creates a column with a "✘" button that allows the user to decline a request.
        // When clicked, the match is removed from the UI, but remains in the database and
        // changes it's state to "denied".
        TableColumn<Match, Void> declineRequestcolumnMatch = new TableColumn<>("Decline Request");
        declineRequestcolumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button declineRequestButton = new Button("✘");

            {
                declineRequestButton.setOnAction(event -> {
                    Match match = getTableView().getItems().get(getIndex());
                    try {
                        srManager.declineMatchAndRequest(match.getMatchID());
                        incomingRequestTable.getItems().remove(match);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                setGraphic(declineRequestButton); //Show button in cell
            }
        });
        incomingRequestTable.getColumns().add(declineRequestcolumnMatch); // Add the column to table

        // Load all incoming requests for the current user
        int LoginProfileID = MainViewController.getCurrentProfileID();
        DataAccessObject dao = new DataAccessObject();
        List<Match> incomingRequests = dao.getMatchesForProfile(LoginProfileID);

        //Testkode for at tjekke om knapper duer, skal fjernes når view incoming requests virker
        ObservableList<Match> testMatches = FXCollections.observableArrayList();

        Match testMatch = new Match();
        testMatch.setMatchID(123);
        testMatch.setProfileAID(2);
        testMatch.setProfileBID(3);
        testMatch.setStateID(1);
        testMatches.add(testMatch);
        incomingRequestTable.setItems(testMatches);
    }

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
