package be.kdg.bonsaiProjectApp.view.CheckTrees;

import be.kdg.bonsaiProjectApp.view.UISettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CheckTreesView extends BorderPane {

    private UISettings uiSettings;
    private Button backButton;
    private TextField nameField; // TextField for name input
    private Label nameLabel ;
    private Button saveButton;


    public CheckTreesView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        backButton = new Button("Back");
        this.nameField = new TextField();
        this.nameLabel = new Label("Enter your name:");
        this.saveButton = new Button("Save");

    }

    private void layoutNodes() {
        setPadding(new Insets(uiSettings.getInsetsMargin() * 2));
        setPrefWidth(uiSettings.getLowestRes() / 2);
        setPrefHeight(uiSettings.getLowestRes() / 2);
        backButton.setStyle("-fx-background-color: #B03608; -fx-text-fill: white; -fx-font-size: 22px;");

        HBox centerBox = new HBox(20);
        centerBox.getChildren().addAll(backButton);
        centerBox.setAlignment(Pos.BOTTOM_RIGHT);

        setBottom(centerBox);

        this.setStyle("-fx-background-repeat: no-repeat; -fx-background-image: url('/images/menu.png')");


    }

    Button getBackButton() {
        return backButton;
    }
    Button getSaveButton() {return saveButton;}
    TextField getNameField() {
        return nameField;
    }
}
