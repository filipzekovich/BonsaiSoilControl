package be.kdg.bonsaiProjectApp.view.aboutscreen;

import be.kdg.bonsaiProjectApp.model.MainModel;
import be.kdg.bonsaiProjectApp.view.UISettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AboutScreenPresenter {

    private MainModel model;
    private AboutScreenView view;
    private UISettings uiSettings;

    public AboutScreenPresenter(MainModel model, AboutScreenView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        view.getBtnOk().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getScene().getWindow().hide();
            }
        });
    }
}
