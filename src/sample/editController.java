// Haseeb Balal (hhb10) and Muffadal Hussain (mmh240)
package sample;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

public class editController {
    @FXML public TextField yname;
    @FXML
    TextField sname;
    @FXML
    TextField alname;
    @FXML
    TextField arname;
    Alert year = new Alert(Alert.AlertType.NONE);
    ObservableList<String> obsList;
    Stage mainStage;
    ArrayList<songClass> arraylist;
    int index;

    public void start(Stage mainStage, int index, ArrayList<songClass> arraylist, ObservableList<String> obsList) {
        this.mainStage = mainStage;
        this.index = index;
        this.arraylist = arraylist;
        this.obsList = obsList;

        yname.setText(arraylist.get(index).year);
        sname.setText(arraylist.get(index).name);
        alname.setText(arraylist.get(index).album);
        arname.setText(arraylist.get(index).artist);

    }

    public void saveData(javafx.event.ActionEvent event) throws Exception
    {

        sname.setText((sname.getText()).trim());
        yname.setText((yname.getText()).trim());
        alname.setText((alname.getText()).trim());
        arname.setText((arname.getText()).trim());

        boolean SongTrue = true;

        for (int i = 0; i < yname.getText().length(); i++) {
            if (!Character.isDigit(yname.getText().charAt((i)))) {
                SongTrue = false;
            }
        }
        if (SongTrue == false || yname.getText().length() != 4 && yname.getText().length() != 0) {
            year.setAlertType(Alert.AlertType.ERROR);
            year.setContentText("Year must be length of 4 and must be numbers");
            year.show();
            return;
        }
        Alert confirmEdit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmEdit.setTitle("Confirmation Dialog");
        //confirmEdit.setHeaderText("Look, a Confirmation Dialog");
        confirmEdit.setContentText("Are you ok with this?");

        Optional<ButtonType> result = confirmEdit.showAndWait();
        songClass temp2 = new songClass(sname.getText(), arname.getText(), alname.getText(), yname.getText());

        if (result.get() == ButtonType.OK) {
            if(sname.getText().isEmpty() || arname.getText().isEmpty())
            {
                Alert empty = new Alert(Alert.AlertType.INFORMATION);
                empty.setContentText("The song and artist names can not be empty!");
                empty.show();
                return;
            }


            if (arraylist.size() == 0) {
                obsList.add(temp2.toString2());
                arraylist.add(temp2);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("sample.fxml"));
                AnchorPane root = (AnchorPane) loader.load();


                Controller listController = loader.getController();
                listController.EditResult(obsList, mainStage, arraylist, 0);

                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
                return;
            }
            for (int i =0; i< arraylist.size();i++)
            {
                if (i != index && arraylist.get(i).name.toUpperCase().equals(temp2.name.toUpperCase()) && arraylist.get(i).artist.toUpperCase().equals(temp2.artist.toUpperCase()))
                {
                    Alert duplicate = new Alert(Alert.AlertType.INFORMATION);
                    duplicate.setContentText("The song and artist names already exist!");
                    duplicate.show();
                    return;
                }
            }
            obsList.remove(index);
            arraylist.remove(index);
            for (int j = 0; j < arraylist.size(); j++) {
                if (temp2.name.toUpperCase().compareTo(arraylist.get(j).name.toUpperCase()) < 0) {

                    obsList.add(j, temp2.toString2());
                    arraylist.add(j, temp2);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("sample.fxml"));
                    AnchorPane root = (AnchorPane) loader.load();


                    Controller listController = loader.getController();
                    listController.EditResult(obsList, mainStage, arraylist, j);

                    Scene scene = new Scene(root);
                    mainStage.setScene(scene);
                    mainStage.show();
                    return;
                } else if (temp2.name.toUpperCase().compareTo(arraylist.get(j).name.toUpperCase()) == 0) {
                    if (temp2.artist.toUpperCase().compareTo(arraylist.get(j).artist.toUpperCase()) < 0) {
                        obsList.add(j, temp2.toString2());
                        arraylist.add(j, temp2);
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("sample.fxml"));
                        AnchorPane root = (AnchorPane) loader.load();


                        Controller listController = loader.getController();
                        listController.EditResult(obsList, mainStage, arraylist, j);

                        Scene scene = new Scene(root);
                        mainStage.setScene(scene);
                        mainStage.show();
                        return;
                    }
                }
            }
            obsList.add(temp2.toString2());
            arraylist.add(temp2);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("sample.fxml"));
            AnchorPane root = (AnchorPane) loader.load();


            Controller listController = loader.getController();
            listController.EditResult(obsList, mainStage, arraylist, arraylist.size()-1);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
            return;
        }
    }
    public void cancel(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        AnchorPane root = (AnchorPane) loader.load();


        Controller listController = loader.getController();
        listController.EditResult(obsList, mainStage, arraylist, index);

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
        return;


    }

}
