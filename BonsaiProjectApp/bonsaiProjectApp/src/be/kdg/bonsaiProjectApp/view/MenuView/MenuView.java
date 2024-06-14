package be.kdg.bonsaiProjectApp.view.MenuView;

import be.kdg.bonsaiProjectApp.view.UISettings;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MenuView extends BorderPane {

    private VBox vBox;
    private Label mainMenuLabel;
    private Button checkTreesButton;
    private Button addBonsaiButton;
    private Button removeBonsaiButton;
    private Button editAlarmLevelButton;

    private Button editMeasurmentFrequencyButton;
    private Button chooseAlarmButton;
    private MenuItem exitMI;
    private MenuItem saveMI;
    private MenuItem loadMI;
    private MenuItem settingsMI;
    private MenuItem aboutMI;
    private MenuItem infoMI;
    private UISettings uiSettings;

    public MenuView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {

        this.exitMI = new MenuItem("Exit");
        this.saveMI = new MenuItem("Save");
        this.loadMI = new MenuItem("Load");
        this.settingsMI = new MenuItem("Settings");
        this.aboutMI = new MenuItem("About");
        this.infoMI = new MenuItem("Info");
        this.mainMenuLabel = new Label("My Bonsai Garden");
        this.checkTreesButton = new Button("Check my Bonsais");
        this.addBonsaiButton = new Button("Add Bonsai");
        this.removeBonsaiButton = new Button("Remove Bonsai");
        this.editAlarmLevelButton = new Button("Edit Alarm Level");
        this.editMeasurmentFrequencyButton = new Button("Edit Frequency");
        this.chooseAlarmButton = new Button("Choose Alarm");
        this.vBox = new VBox();
    }

    private void layoutNodes() {
        Menu menuFile = new Menu("File",null,loadMI, saveMI, new SeparatorMenuItem(), settingsMI, new SeparatorMenuItem(),exitMI);
        Menu menuHelp = new Menu("Help",null, aboutMI, infoMI);
        MenuBar menuBar = new MenuBar(menuFile,menuHelp);
        setTop(menuBar);
        menuBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);");


        checkTreesButton.setPrefWidth(300); // Adjust this value as needed
        addBonsaiButton.setPrefWidth(300); // Adjust this value as needed
        removeBonsaiButton.setPrefWidth(300); // Adjust this value as needed
        editAlarmLevelButton.setPrefWidth(300); // Adjust this value as needed
        editMeasurmentFrequencyButton.setPrefWidth(300); // Adjust this value as needed
        chooseAlarmButton.setPrefWidth(300); // Adjust this value as needed

        // Add nodes to the vbox
        vBox.getChildren().addAll(mainMenuLabel, checkTreesButton, addBonsaiButton, removeBonsaiButton, editAlarmLevelButton, editMeasurmentFrequencyButton, chooseAlarmButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        this.setCenter(vBox);

        mainMenuLabel.setStyle("-fx-text-fill: white; -fx-font-size: 50px;");
        checkTreesButton.setStyle("-fx-background-color: #B03608; -fx-text-fill: white; -fx-font-size: 22px;");
        addBonsaiButton.setStyle("-fx-background-color: #B03608; -fx-text-fill: white; -fx-font-size: 22px;");
        removeBonsaiButton.setStyle("-fx-background-color: #B03608; -fx-text-fill: white; -fx-font-size: 22px;");
        editAlarmLevelButton.setStyle("-fx-background-color: #B03608; -fx-text-fill: white; -fx-font-size: 22px;");
        editMeasurmentFrequencyButton.setStyle("-fx-background-color: #B03608; -fx-text-fill: white; -fx-font-size: 22px;");
        chooseAlarmButton.setStyle("-fx-background-color: #B03608; -fx-text-fill: white; -fx-font-size: 22px;");


        this.setStyle("-fx-background-repeat: no-repeat;-fx-background-image: url('/images/menu.png');");
    }

    MenuItem getExitItem() {return exitMI;}

    MenuItem getSaveItem() {return saveMI;}

    MenuItem getLoadItem() {return loadMI;}

    MenuItem getSettingsItem() {return settingsMI;}

    MenuItem getAboutItem() {return aboutMI;}

    MenuItem getInfoItem() {return infoMI;}
    Button getCheckTreesButton() {
        return checkTreesButton;
    }

    Button getAddBonsaiButton() {
        return addBonsaiButton;
    }

    Button getRemoveBonsaiButton() {
        return removeBonsaiButton;
    }


    Button getEditAlarmLevelButton() {
        return editAlarmLevelButton;
    }
    Button getChooseAlarmButton() {
        return chooseAlarmButton;
    }

    Button getEditMeasurmentFrequencyButton() {
        return editMeasurmentFrequencyButton;
    }

}
