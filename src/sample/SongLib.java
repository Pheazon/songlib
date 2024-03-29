// Haseeb Balal (hhb10) and Muffadal Hussain (mmh240)
package sample;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Controller;

public class SongLib extends Application {
    @Override
    public void start(Stage primaryStage)
            throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(
                getClass().getResource("sample.fxml"));
        AnchorPane root = (AnchorPane) loader.<AnchorPane>load();


        Controller listController = loader.getController();
        listController.start(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}