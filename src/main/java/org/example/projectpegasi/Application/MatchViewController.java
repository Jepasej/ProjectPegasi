package org.example.projectpegasi.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.BusinessService.LoginCredentialsSession;
import org.example.projectpegasi.BusinessService.SwapRequestManager;
import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.List;

public class MatchViewController
{
    @FXML
    private TableView <Match> matchTable;

    @FXML
    private TableColumn<Match, String> jobTitleColumnMatch, companyColumnMatch;

    @FXML
    private Button goToMatchesButton, outgoingRequestButton, incomingRequestButton, backToProfileButton;

    @FXML
    public void initialize() throws Exception {
        SwapRequestManager srManager = new SwapRequestManager();

        // Creates a column with a "✔" button that allows the user to accept a match.
        // When clicked, it creates a swap request using the match's ID.
        TableColumn<Match, Void> requestSwapColumnMatch = new TableColumn<>("Request swap");
        requestSwapColumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button requestSwapButton = new Button("✔");
                    {
                        requestSwapButton.setOnAction(event -> {
                            Match match = getTableView().getItems().get(getIndex());
                            try {
                                srManager.createSwapRequest(match.getMatchID());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        setGraphic(requestSwapButton); //Show button in cell
                    }
        });
        matchTable.getColumns().add(requestSwapColumnMatch); // Add the column to table

        // Creates a column with a "✘" button that allows the user to decline a match.
        // When clicked, the match is removed from the UI, but remains in the database and
        // changes it's state to "denied".
        TableColumn<Match, Void> declineMatchcolumnMatch = new TableColumn<>("Decline match");
        declineMatchcolumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button declineMatchButton = new Button("✘");
            {
                declineMatchButton.setOnAction(event -> {
                    Match match = getTableView().getItems().get(getIndex());
                    try {
                        srManager.declineMatchAndRequest(match.getMatchID());
                        matchTable.getItems().remove(match);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                setGraphic(declineMatchButton); //Show button in cell
            }
        });
        matchTable.getColumns().add(declineMatchcolumnMatch); // Add the column to table

        //Loads all matches for th elooged-in user
        //Finds the other profiles that matches with logged-in user and prepares to show it in UI
        int LoginProfileID = LoginCredentialsSession.getProfileID();
        DataAccessObject dao = new DataAccessObject();
        List<Match> matches = dao.getMatchesForProfile(LoginProfileID);
        for (Match match : matches) {
            int profileID;

            if(match.getProfileAID() == LoginProfileID) {
                profileID = match.getProfileBID();
            }
            else {
                profileID = match.getProfileAID();
            }
        }
    }




    public void onOutgoingRequestButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.OutgoingRequestView);
    }

    public void onIncomingRequestButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.IncomingRequestView);
    }

    public void onBackToProfileButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.ProfileView);
    }
}
