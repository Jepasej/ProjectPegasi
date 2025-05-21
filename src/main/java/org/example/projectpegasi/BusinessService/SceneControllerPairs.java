package org.example.projectpegasi.BusinessService;

import javafx.scene.Scene;

/**
 *  A class that connects a Scene, which is only created when needed,
 *  with the name of the view from our enum. This makes it easier to keep track of
 *  and switch between different views in the program.
 */
public class SceneControllerPairs
{
    private Lazy<Scene> lazyscene;
    private ControllerNames name;

    public Scene getScene()
    {
        return lazyscene.getValue();
    }

    public ControllerNames getName()
    {
        return name;
    }

    /**
     * Method to pair up Scene and ControllerName
     * @param scene
     * @param name
     */
    public SceneControllerPairs(Lazy<Scene> scene, ControllerNames name)
    {
        this.lazyscene = scene;
        this.name = name;
    }
}
