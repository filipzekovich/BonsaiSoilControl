package be.kdg.bonsaiProjectApp.view.settingsscreen;

import be.kdg.bonsaiProjectApp.model.MainModel;
import be.kdg.bonsaiProjectApp.view.UISettings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Paths;

public class SettingsPresenter {

    private MainModel model;
    private SettingsView view;
    private UISettings uiSettings;

    public SettingsPresenter(MainModel model, SettingsView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        updateView();
        addEventHandlers();
    }

    private void updateView() {
    }

    private void addEventHandlers() {
        view.getExitItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {handleCloseEvent(event);}});
        view.getCssButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Style Sheet Files", "*.css"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                fileChooser.setInitialDirectory(new File("resources" + UISettings.FILE_SEPARATOR + "stylesheets"));
                File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow());
                if (selectedFile != null) {
                    URI uri = selectedFile.toURI();
                    uiSettings.setStyleSheetPath(Paths.get(uri));
                }
                if (uiSettings.styleSheetAvailable()){
                    view.getScene().getStylesheets().removeAll();
                    try{
                        view.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
            }
        });
        view.getOkButton().setOnMouseClicked(new EventHandler<MouseEvent>()  {
            @Override
            public void handle(MouseEvent event) { handleCloseEvent(event);
            }
        });
    }

    public void windowsHandler() {
        view.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {handleCloseEvent(event);}});
    }

    private void handleCloseEvent(Event event){
        view.getScene().getWindow().hide();
    }
}
