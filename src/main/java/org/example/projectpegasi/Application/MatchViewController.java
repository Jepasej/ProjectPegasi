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
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.MatchDetails;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchViewController {
    @FXML
    private TableView<MatchDetails> matchTable;

    @FXML
    private TableColumn<MatchDetails, String> jobTitleColumnMatch, companyColumnMatch;

    @FXML
    private Button goToMatchesButton, outgoingRequestButton, incomingRequestButton, backToProfileButton;

    /**
     * Initializes the MatchView.
     * Loads all matches for the logged-in user and populates the TableView.
     * Adds buttons for accepting or declining matches with proper event handling.
     */
    @FXML
    public void initialize() throws Exception {
        System.out.println("LoginProfileID: " + LoginCredentialsSession.getProfileID());

        SwapRequestManager srManager = new SwapRequestManager();

        // Creates a column with a "✔" button that allows the user to accept a match.
        // When clicked, it creates a swap request using the match's ID.
        TableColumn<MatchDetails, Void> requestSwapColumnMatch = new TableColumn<>("Request swap");
        requestSwapColumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button requestSwapButton = new Button("✔");

            {
                requestSwapButton.setOnAction(event -> {
                    MatchDetails details = getTableView().getItems().get(getIndex());
                    try {
                        srManager.createSwapRequest(details.getMatchID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                setGraphic(requestSwapButton); //Show button in cell
            }
        });
        //matchTable.getColumns().add(requestSwapColumnMatch); // Add the column to table

        // Creates a column with a "✘" button that allows the user to decline a match.
        // When clicked, the match is removed from the UI, but remains in the database and
        // changes it's state to "denied".
        TableColumn<MatchDetails, Void> declineMatchcolumnMatch = new TableColumn<>("Decline match");
        declineMatchcolumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button declineMatchButton = new Button("✘");

            {
                declineMatchButton.setOnAction(event -> {
                    MatchDetails details = getTableView().getItems().get(getIndex());
                    try {
                        srManager.declineMatchAndRequest(details.getMatchID());
                        matchTable.getItems().remove(details);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                setGraphic(declineMatchButton); //Show button in cell
            }
        });
        //matchTable.getColumns().add(declineMatchcolumnMatch); // Add the column to table

        //Loads all matches for the looged-in user
        //Finds the other profiles that matches with logged-in user and prepares to show it in UI
        int LoginProfileID = LoginCredentialsSession.getProfileID();
        DataAccessObject dao = new DataAccessObject();
        List<Match> matches = dao.getMatchesForProfile(LoginProfileID);

        // Filter out matches where state is not 1 (match-State = 1 in database)
        matches = matches.stream()
                .filter(match -> match.getStateID() == 1)
                .collect(Collectors.toList());
        List<MatchDetails> matchDetails = new ArrayList<>();

        for (Match match : matches) {
            int othersProfileID= (match.getProfileAID() == LoginProfileID) ? match.getProfileBID() : match.getProfileAID();
            //Get profileinformation
            Profile profile = dao.getAttributesForMatchView(othersProfileID);
            //
            MatchDetails details = new MatchDetails(profile.getJobTitle(), profile.getCompany().getName(), match.getMatchID());
            System.out.println("Jobtitle: " + profile.getJobTitle() + ", Company: " + profile.getCompany().getName());
            System.out.println("Total matches added: " + matchDetails.size());

            matchDetails.add(details);
        }
        ObservableList<MatchDetails> observableList = FXCollections.observableArrayList(matchDetails);

        jobTitleColumnMatch.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        companyColumnMatch.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        matchTable.setItems(observableList);

        matchTable.getColumns().removeIf(col ->
                col.getText().equals("Request swap") || col.getText().equals("Decline match"));

        if (!observableList.isEmpty()) {
            matchTable.getColumns().addAll(requestSwapColumnMatch, declineMatchcolumnMatch);
        }
    }


        public void onOutgoingRequestButtonClick ()
        {
            HelloApplication.changeScene(ControllerNames.OutgoingRequestView);
        }

        public void onIncomingRequestButtonClick ()
        {
            HelloApplication.changeScene(ControllerNames.IncomingRequestView);
        }

        public void onBackToProfileButtonClick ()
        {
            HelloApplication.changeScene(ControllerNames.ProfileView);
        }
    }
