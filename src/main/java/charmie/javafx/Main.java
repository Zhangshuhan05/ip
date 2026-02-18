package charmie.javafx;

import java.io.IOException;

import charmie.Charmie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Charmie charmie = new Charmie("./data/charmie.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Charmie");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setCharmie(charmie);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
