package org.example.projectpegasi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.projectpegasi.BusinessService.Lazy;
import org.example.projectpegasi.BusinessService.SceneControllerPairs;
import org.example.projectpegasi.BusinessService.ControllerNames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class HelloApplication extends Application
{
    private static Collection<SceneControllerPairs> scenes = new ArrayList<>();
    private static Stage stageHolder = null;

    @Override
    public void start(Stage stage) throws IOException
    {
        stageHolder = stage;

        //Loads our MainView.fxml as our first Scene
        FXMLLoader MainViewLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent MainViewPane = MainViewLoader.load();
        Scene overViewScene = new Scene(MainViewPane, 1000, 800);

        //Adds our scenes to the UI through lazy initialization
        scenes.add(new SceneControllerPairs(
                new Lazy<>(() -> buildScene("CreateProfileView.fxml")),
                ControllerNames.CreateProfileView
        ));

        scenes.add(new SceneControllerPairs(
                new Lazy<>(() -> buildScene("EditProfileView.fxml")),
                ControllerNames.EditProfileView
        ));

        scenes.add(new SceneControllerPairs(
                new Lazy<>(() -> buildScene("HRView.fxml")),
                ControllerNames.HRView
        ));

        scenes.add(new SceneControllerPairs(
                new Lazy<>(() -> buildScene("IncomingRequestView.fxml")),
                ControllerNames.IncomingRequestView
        ));

        scenes.add(new SceneControllerPairs(
                new Lazy<>(() -> buildScene("MainView.fxml")),
                ControllerNames.MainView
        ));

        scenes.add(new SceneControllerPairs(
                new Lazy<>(() -> buildScene("MatchView.fxml")),
                ControllerNames.MatchView
        ));

        scenes.add(new SceneControllerPairs(
                new Lazy<>(() -> buildScene("OutgoingRequestView.fxml")),
                ControllerNames.OutgoingRequestView
        ));

        scenes.add(new SceneControllerPairs(
                new Lazy<>(() -> buildScene("ProfileView.fxml")),
                ControllerNames.ProfileView
        ));


        stage.setTitle("JobSwap");
        stage.setScene(overViewScene);
        stage.show();


    }

    /**
     * Changes between different Scenes
     *
     * @param sceneName What scene is chosen from our enum list
     */
    public static void changeScene(ControllerNames sceneName)
    {
        stageHolder.setTitle(sceneName.toString());

        for (SceneControllerPairs sceneCtrlNamePair : scenes)
        {
            if (sceneCtrlNamePair.getName().equals(sceneName))
            {
                stageHolder.setScene(sceneCtrlNamePair.getScene());
            }
        }
    }

    /**
     * Loads and builds scenes from fxml-files.
     *
     * @param resource the name of the FXML file.
     * @return the constructed Scene from the FXML file or null in non found.
     */
    private Scene buildScene(String resource)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent root = loader.load();
            return new Scene(root, 1000, 800);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args)
    {
        launch();
    }
}