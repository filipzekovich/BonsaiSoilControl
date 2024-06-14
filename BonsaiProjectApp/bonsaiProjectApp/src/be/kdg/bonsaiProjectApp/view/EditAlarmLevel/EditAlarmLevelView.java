package be.kdg.bonsaiProjectApp.view.EditAlarmLevel;

import be.kdg.bonsaiProjectApp.view.UISettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EditAlarmLevelView extends BorderPane {

    private UISettings uiSettings;
    private Button backButton;
    private TextField nameField; // TextField for name input
    private Label nameLabel ;
    private Button saveButton;


    public EditAlarmLevelView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
    }
    private void initialiseNodes() {
        backButton = new Button("Back");
        this.nameField = new TextField();
        this.nameLabel = new Label("Enter new Alarm Level:");
        this.saveButton = new Button("Save");

    }
    private void layoutNodes() {
        setPadding(new Insets(uiSettings.getInsetsMargin() * 2));
        setPrefWidth(uiSettings.getLowestRes() / 2);
        setPrefHeight(uiSettings.getLowestRes() / 2);
        Pane layout = new Pane();

        backButton.setStyle("-fx-background-color: #B03608; -fx-text-fill: white; -fx-font-size: 22px;");
        backButton.setLayoutX(250);
        backButton.setLayoutY(350);

        nameLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: black;");
        nameLabel.setLayoutX(50);
        nameLabel.setLayoutY(50);

        nameField.setStyle("-fx-font-size: 20px;");
        nameField.setLayoutX(50);
        nameField.setLayoutY(100);
        nameField.setPrefWidth(300);

        saveButton.setStyle("-fx-background-color: #B03608; -fx-text-fill: black; -fx-font-size: 22px;");
        saveButton.setLayoutX(50);
        saveButton.setLayoutY(150);

        layout.getChildren().addAll(nameLabel, nameField, saveButton, backButton);

        this.setCenter(layout);

        this.setStyle("-fx-background-repeat: no-repeat; -fx-background-image: url('/images/restOfScreens.png')");


    }
    Button getBackButton() {
        return backButton;
    }
    Button getSaveButton() {return saveButton;}
    TextField getNameField() {
        return nameField;
    }
}
