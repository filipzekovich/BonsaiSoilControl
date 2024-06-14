package be.kdg.bonsaiProjectApp;

import be.kdg.bonsaiProjectApp.model.MainModel;
import be.kdg.bonsaiProjectApp.view.MenuView.MenuPresenter;
import be.kdg.bonsaiProjectApp.view.MenuView.MenuView;
import be.kdg.bonsaiProjectApp.view.UISettings;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.nio.file.Files;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainModel model = new MainModel();
        UISettings uiSettings = new UISettings();
        MenuView view = new MenuView(uiSettings);

        Scene scene = new Scene(view);
        if (uiSettings.styleSheetAvailable()){
            try {
                scene.getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
            } catch (MalformedURLException ex) {
                // do nothing, if toURL-conversion fails, program can continue
            }
        }
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Nanum+Brush+Script&display=swap");
        primaryStage.setHeight(524);
        primaryStage.setWidth(870);
        primaryStage.setTitle(uiSettings.getApplicationName());
        if (Files.exists(uiSettings.getApplicationIconPath())) {
            try {
                primaryStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
            }
            catch (MalformedURLException ex) {
                // do nothing, if toURL-conversion fails, program can continue
            }
        } else { // do nothing, if ApplicationIcon is not available, program can continue
        }
        MenuPresenter presenter = new MenuPresenter(model,view,uiSettings);
        presenter.windowsHandler();
        primaryStage.show();

    }
}
