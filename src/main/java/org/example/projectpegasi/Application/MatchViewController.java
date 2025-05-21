package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.projectpegasi.DomainModels.Match;

public class MatchViewController
{
    @FXML
    private TableView <Match> matchTable;

    @FXML
    private TableColumn<Match, String> jobTitleColumnMatch, companyColumnMatch, requestSwapColumnMatch, declineMatchColumnMatch;

    @FXML
    private Button goToMatchesButton, outgoingRequestButton, incomingRequestButton, backToProfileButton;

    @FXML
    public void initialize(){
        //Create an accept button for each match entry in our request swap column
        requestSwapColumnMatch.setCellFactory(col -> new TableCell<>(){
            private final Button requestSwapButton = new Button("✔");
            {
                requestSwapButton.setOnAction(event -> {Match match = getTableView().getItems().get(getIndex());
                });
            }
        });
    }

}
