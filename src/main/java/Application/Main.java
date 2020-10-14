package Application;

import Controller.Controller;
import View.TranslateView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    @Override
    public void start(Stage window) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TranslateView.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.setTitle("Translator");
            //window.setMaximized(true);
            window.show();

            Controller c = new Controller()
                    .setTranslateView((TranslateView) loader.getController())
                    .initView();



        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}