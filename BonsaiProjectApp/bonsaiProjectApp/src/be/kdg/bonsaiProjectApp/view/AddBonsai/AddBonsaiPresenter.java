package be.kdg.bonsaiProjectApp.view.AddBonsai;

import be.kdg.bonsaiProjectApp.model.MainModel;
import be.kdg.bonsaiProjectApp.view.CheckTrees.CheckTreesView;
import be.kdg.bonsaiProjectApp.view.MenuView.MenuPresenter;
import be.kdg.bonsaiProjectApp.view.MenuView.MenuView;
import be.kdg.bonsaiProjectApp.view.UISettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

import java.net.MalformedURLException;

public class AddBonsaiPresenter {
    private MainModel model;
    private AddBonsaiView view;
    private UISettings uiSettings;

    public AddBonsaiPresenter(MainModel model, AddBonsaiView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        addEventHandlers();
        updateView();
    }
    private void addEventHandlers(){
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
                String nameField = view.getNameField().getText().trim();
                String locationField = view.getLocationField().getText().trim();
                if(nameField.isEmpty() || locationField.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Name field or Location field is empty");
                    alert.setContentText("Please enter your name or location");
                    alert.showAndWait();
                    return;
                }
                else {
                    String name = view.getNameField().getText();
                    String location = view.getLocationField().getText();

                    if (model.countBonsais() < 3){
                        model.sendString("1");
                        model.addBonsai(name,location);
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("The maximum number of Bonsais is entered");
                        alert.setContentText("Please remove one Bonsai before adding another one");
                        alert.showAndWait();
                        return;
                    }
                }
                updateView();
            }
        });
    }

    private void updateView(){
    }
}
