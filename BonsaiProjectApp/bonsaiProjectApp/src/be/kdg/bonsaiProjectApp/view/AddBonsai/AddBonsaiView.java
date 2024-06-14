package be.kdg.bonsaiProjectApp.view.AddBonsai;

import be.kdg.bonsaiProjectApp.view.UISettings;
import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.Pane;


public class AddBonsaiView extends BorderPane {

    private UISettings uiSettings;
    private Button backButton;
    private TextField nameField; // TextField for name input
    private Label nameLabel ;
    private TextField locationField; // TextField for name input
    private Label locationLabel ;
    private Button saveButton;


    public AddBonsaiView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        backButton = new Button("Back");
        this.nameField = new TextField();
        this.nameLabel = new Label("Enter name:");
        this.saveButton = new Button("Save");
        this.locationField = new TextField();
        this.locationLabel = new Label("Enter location");

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

        locationLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: black;");
        locationLabel.setLayoutX(50);
        locationLabel.setLayoutY(150);

        locationField.setStyle("-fx-font-size: 20px;");
        locationField.setLayoutX(50);
        locationField.setLayoutY(200);
        locationField.setPrefWidth(300);

        saveButton.setStyle("-fx-background-color: #B03608; -fx-text-fill: black; -fx-font-size: 22px;");
        saveButton.setLayoutX(50);
        saveButton.setLayoutY(250);

        layout.getChildren().addAll(nameLabel, nameField,locationLabel,locationField, saveButton, backButton);

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
    TextField getLocationField() {return locationField;}
}
