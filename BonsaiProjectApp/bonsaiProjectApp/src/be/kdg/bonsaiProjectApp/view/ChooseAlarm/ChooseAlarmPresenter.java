package be.kdg.bonsaiProjectApp.view.ChooseAlarm;

import be.kdg.bonsaiProjectApp.model.MainModel;
import be.kdg.bonsaiProjectApp.view.MenuView.MenuPresenter;
import be.kdg.bonsaiProjectApp.view.MenuView.MenuView;
import be.kdg.bonsaiProjectApp.view.UISettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

import java.net.MalformedURLException;

public class ChooseAlarmPresenter {
    private MainModel model;
    private ChooseAlarmView view;
    private UISettings uiSettings;

    public ChooseAlarmPresenter(MainModel model, ChooseAlarmView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        view.getBackButton().setOnAction(actionEvent -> {
            MenuView msView = new MenuView(uiSettings);
            MenuPresenter msPresenter = new MenuPresenter(model, msView, uiSettings);
            view.getScene().setRoot(msView);
            try {
                msView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
            } catch (MalformedURLException ex) {
                // // do nothing, if toURL-conversion fails, program can continue
            }
            msView.getScene().getWindow().sizeToScene();
            msView.getScene().getWindow().setHeight(524);
            msView.getScene().getWindow().setWidth(870);
            msPresenter.windowsHandler();
        });

        view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String playerName = view.getNameField().getText().trim();
                if (playerName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Name field is empty");
                    alert.setContentText("Please enter your name");
                    alert.showAndWait();
                    return;
                } else {
                }
                updateView();
            }
        });
    }

    private void updateView(){
    }
}

