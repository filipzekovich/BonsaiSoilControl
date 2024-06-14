package be.kdg.bonsaiProjectApp.view.MenuView;
import be.kdg.bonsaiProjectApp.view.AddBonsai.AddBonsaiPresenter;
import be.kdg.bonsaiProjectApp.view.AddBonsai.AddBonsaiView;
import be.kdg.bonsaiProjectApp.view.CheckTrees.CheckTreesPresenter;
import be.kdg.bonsaiProjectApp.view.CheckTrees.CheckTreesView;
import be.kdg.bonsaiProjectApp.view.ChooseAlarm.ChooseAlarmPresenter;
import be.kdg.bonsaiProjectApp.view.ChooseAlarm.ChooseAlarmView;
import be.kdg.bonsaiProjectApp.view.EditAlarmLevel.EditAlarmLevelPresenter;
import be.kdg.bonsaiProjectApp.view.EditAlarmLevel.EditAlarmLevelView;
import be.kdg.bonsaiProjectApp.view.EditFrequency.EditFrequencyPresenter;
import be.kdg.bonsaiProjectApp.view.EditFrequency.EditFrequencyView;
import be.kdg.bonsaiProjectApp.view.RemoveBonsai.RemoveBonsaiPresenter;
import be.kdg.bonsaiProjectApp.view.RemoveBonsai.RemoveBonsaiView;
import be.kdg.bonsaiProjectApp.view.UISettings;
import be.kdg.bonsaiProjectApp.model.MainModel;
import be.kdg.bonsaiProjectApp.view.aboutscreen.AboutScreenPresenter;
import be.kdg.bonsaiProjectApp.view.aboutscreen.AboutScreenView;
import be.kdg.bonsaiProjectApp.view.settingsscreen.SettingsPresenter;
import be.kdg.bonsaiProjectApp.view.settingsscreen.SettingsView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.List;

public class MenuPresenter {
    private MainModel model;
    private MenuView view;
    private UISettings uiSettings;
    
    public MenuPresenter(MainModel model, MenuView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        updateView();
        EventHandlers();
    }


    private void EventHandlers() {}
    private void updateView() {
        view.getSettingsItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SettingsView settingsView = new SettingsView(uiSettings);
                SettingsPresenter presenter = new SettingsPresenter(model, settingsView, uiSettings);
                Stage settingsStage = new Stage();
                settingsStage.setTitle("Settings");
                settingsStage.initOwner(view.getScene().getWindow());
                settingsStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(settingsView);
                settingsStage.setScene(scene);
                settingsStage.setTitle(uiSettings.getApplicationName() + " - Settings");
                settingsStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                settingsStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
                if (Files.exists(uiSettings.getApplicationIconPath())) {
                    try {
                        settingsStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                } else { // do nothing, if ApplicationIconImage is not available, program can continue
                }
                settingsView.getScene().getWindow().setHeight(7 * uiSettings.getResY() / 10);
                settingsView.getScene().getWindow().setWidth(7 * uiSettings.getResX() / 10);
                if (uiSettings.styleSheetAvailable()) {
                    settingsStage.getScene().getStylesheets().removeAll();
                    try {
                        settingsStage.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
                settingsStage.showAndWait();
                if (uiSettings.styleSheetAvailable()) {
                    view.getScene().getStylesheets().removeAll();
                    try {
                        view.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
            }
        });
        view.getLoadItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Load Data File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Textfiles", "*.txt"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow());
                if ((selectedFile != null) ){ //^ (Files.isReadable(Paths.get(selectedFile.toURI())))) {
                    try {
                        List<String> input = Files.readAllLines(Paths.get(selectedFile.toURI()));
                        // Start implementation read data to be transfered to model
                        for (int i = 0; i < input.size(); i++) {
                            String inputline = input.get(i);
                            String[] elementen = inputline.split(" ");
                        }
                        // End implementation read data to be transfered to model
                    } catch (IOException e) {
                        //
                    }
                } else {
                    Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                    errorWindow.setHeaderText("Problem with the selected input file:");
                    errorWindow.setContentText("File is not readable");
                    errorWindow.showAndWait();
                }
            }
        });
        view.getSaveItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Data File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Textfiles", "*.txt"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showSaveDialog(view.getScene().getWindow());
                if ((selectedFile != null) ^ (Files.isWritable(Paths.get(selectedFile.toURI())))) {
                    try {
                        Files.deleteIfExists(Paths.get(selectedFile.toURI()));
                    } catch (IOException e) {
                        //
                    }
                    try (Formatter output = new Formatter(selectedFile)) {
                        // Start implementation write data coming from the model
                        output.format("%s%n", "Here comes the data!");
                        output.format("%s%n", "First record");
                        output.format("%s%n", "...");
                        output.format("%s%n", "Last record");
                        // End implementation write data coming from the model
                    } catch (IOException e) {
                        //
                    }
                } else {
                    Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                    errorWindow.setHeaderText("Problem with the selected output file:");
                    errorWindow.setContentText("File is not writable");
                    errorWindow.showAndWait();
                }
            }
        });
        view.getExitItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleCloseEvent(event);
            }
        });
        view.getAboutItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AboutScreenView aboutScreenView = new AboutScreenView(uiSettings);
                AboutScreenPresenter aboutScreenPresenter = new AboutScreenPresenter(model, aboutScreenView, uiSettings);
                Stage aboutScreenStage = new Stage();
                aboutScreenStage.initOwner(view.getScene().getWindow());
                aboutScreenStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(aboutScreenView);
                aboutScreenStage.setScene(scene);
                aboutScreenStage.setTitle(uiSettings.getApplicationName() + " - About");
                aboutScreenStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                aboutScreenStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);

                if (Files.exists(uiSettings.getApplicationIconPath())) {
                    try {
                        aboutScreenStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                } else { // do nothing, if ApplicationIconImage is not available, program can continue
                }
                aboutScreenView.getScene().getWindow().setHeight(uiSettings.getResY() / 2);
                aboutScreenView.getScene().getWindow().setWidth(uiSettings.getResX() / 2);
                if (uiSettings.styleSheetAvailable()) {
                    aboutScreenView.getScene().getStylesheets().removeAll();
                    try {
                        aboutScreenView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
                aboutScreenView.getScene().getWindow().setHeight(800);
                aboutScreenView.getScene().getWindow().setWidth(1100);

                aboutScreenStage.showAndWait();
            }});

        view.getCheckTreesButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CheckTreesView checkTreesView = new CheckTreesView(uiSettings);
                CheckTreesPresenter checkTreesPresenter = new CheckTreesPresenter(model, checkTreesView, uiSettings);

                Scene rootScreen = view.getScene();
                rootScreen.setRoot(checkTreesView);
                rootScreen = checkTreesView.getScene();


                // Resize the current scene if necessary
                rootScreen.getWindow().setWidth(870);
                rootScreen.getWindow().setHeight(524);

                // Apply stylesheets if available
                if (uiSettings.styleSheetAvailable()) {
                    rootScreen.getStylesheets().removeAll();
                    try {
                        rootScreen.getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // Handle the exception if necessary
                    }
                }
                model.sendString("3");
                model.receiveString();
                model.sendString("6");


            }
        });

        view.getAddBonsaiButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddBonsaiView addBonsaiView = new AddBonsaiView(uiSettings);
                AddBonsaiPresenter addBonsaiPresenter = new AddBonsaiPresenter(model, addBonsaiView, uiSettings);

                Scene rootScreen = view.getScene();
                rootScreen.setRoot(addBonsaiView);
                rootScreen = addBonsaiView.getScene();


                // Resize the current scene if necessary
                rootScreen.getWindow().setWidth(870);
                rootScreen.getWindow().setHeight(524);

                // Apply stylesheets if available
                if (uiSettings.styleSheetAvailable()) {
                    rootScreen.getStylesheets().removeAll();
                    try {
                        rootScreen.getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // Handle the exception if necessary
                    }
                }
            }
        });

        view.getRemoveBonsaiButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RemoveBonsaiView removeBonsaiView = new RemoveBonsaiView(uiSettings);
                RemoveBonsaiPresenter removeBonsaiPresenter = new RemoveBonsaiPresenter(model, removeBonsaiView, uiSettings);

                Scene rootScreen = view.getScene();
                rootScreen.setRoot(removeBonsaiView);
                rootScreen = removeBonsaiView.getScene();


                // Resize the current scene if necessary
                rootScreen.getWindow().setWidth(870);
                rootScreen.getWindow().setHeight(524);

                // Apply stylesheets if available
                if (uiSettings.styleSheetAvailable()) {
                    rootScreen.getStylesheets().removeAll();
                    try {
                        rootScreen.getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // Handle the exception if necessary
                    }
                }
                model.sendString("2");
            }
        });

        view.getChooseAlarmButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChooseAlarmView  chooseAlarmView = new ChooseAlarmView(uiSettings);
                ChooseAlarmPresenter chooseAlarmPresenter = new ChooseAlarmPresenter(model, chooseAlarmView, uiSettings);

                Scene rootScreen = view.getScene();
                rootScreen.setRoot(chooseAlarmView);
                rootScreen = chooseAlarmView.getScene();


                // Resize the current scene if necessary
                rootScreen.getWindow().setWidth(870);
                rootScreen.getWindow().setHeight(524);

                // Apply stylesheets if available
                if (uiSettings.styleSheetAvailable()) {
                    rootScreen.getStylesheets().removeAll();
                    try {
                        rootScreen.getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // Handle the exception if necessary
                    }
                }
                model.sendString("7");
            }
        });

        view.getEditAlarmLevelButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EditAlarmLevelView editAlarmLevelView = new EditAlarmLevelView(uiSettings);
                EditAlarmLevelPresenter editAlarmLevelPresenter = new EditAlarmLevelPresenter(model, editAlarmLevelView, uiSettings);

                Scene rootScreen = view.getScene();
                rootScreen.setRoot(editAlarmLevelView);
                rootScreen = editAlarmLevelView.getScene();


                // Resize the current scene if necessary
                rootScreen.getWindow().setWidth(870);
                rootScreen.getWindow().setHeight(524);

                // Apply stylesheets if available
                if (uiSettings.styleSheetAvailable()) {
                    rootScreen.getStylesheets().removeAll();
                    try {
                        rootScreen.getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // Handle the exception if necessary
                    }
                }
            }
        });

        view.getEditMeasurmentFrequencyButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EditFrequencyView editFrequencyView = new EditFrequencyView(uiSettings);
                EditFrequencyPresenter editFrequencyPresenter = new EditFrequencyPresenter(model, editFrequencyView, uiSettings);

                Scene rootScreen = view.getScene();
                rootScreen.setRoot(editFrequencyView);
                rootScreen = editFrequencyView.getScene();


                // Resize the current scene if necessary
                rootScreen.getWindow().setWidth(870);
                rootScreen.getWindow().setHeight(524);

                // Apply stylesheets if available
                if (uiSettings.styleSheetAvailable()) {
                    rootScreen.getStylesheets().removeAll();
                    try {
                        rootScreen.getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // Handle the exception if necessary
                    }
                }

            }
        });
    }

    public void windowsHandler() {
        view.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) { handleCloseEvent(event); }});
    }

    private void handleCloseEvent(Event event){
        final Alert stopWindow = new Alert(Alert.AlertType.CONFIRMATION);
        stopWindow.setHeaderText("You're closing the application.");
        stopWindow.setContentText("Are you sure? Unsaved data may be lost.");
        stopWindow.setTitle("WARNING!");
        stopWindow.getButtonTypes().clear();
        ButtonType noButton = new ButtonType("No");
        ButtonType yesButton = new ButtonType("Yes");
        stopWindow.getButtonTypes().addAll(yesButton, noButton);
        stopWindow.showAndWait();
    }
}
