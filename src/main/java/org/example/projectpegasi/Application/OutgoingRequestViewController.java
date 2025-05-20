package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.projectpegasi.DomainModels.SwapRequest;

public class OutgoingRequestViewController
{
    //Nedenstående er forslag til hvad der kan anvendes i view
    @FXML
    private Label titleLabel;

    @FXML
    private TableView<SwapRequest> requestTable;

    @FXML
    private TableColumn<SwapRequest, String> jobTitleColumn;

    @FXML
    private TableColumn<SwapRequest, String> companyColumn;

    @FXML
    private TableColumn<SwapRequest, String> deleteColumn;

    @FXML
    private Button deleteButton;

    @FXML
    public void initialize()
    {
        //Funktionalitet tilføjes her
    }
}
