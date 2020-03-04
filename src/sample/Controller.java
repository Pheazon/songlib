// Haseeb Balal (hhb10) and Muffadal Hussain (mmh240)
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.time.Year;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Controller {

    @FXML
    ListView<String> listview1;

    @FXML
    Label songShow, artistShow, albumShow, yearShow;

    @FXML
    TextField SongTextField, ArtistTextField, AlbumTextField, YearTextField;
    String currentSong = "";
    ArrayList<songClass> arraylist;
    Alert delete = new Alert(AlertType.NONE);
    Alert addAlert = new Alert(AlertType.NONE);
    Alert editAlert = new Alert(AlertType.NONE);
    Alert year = new Alert(AlertType.NONE);
    Stage mainStage;


    private ObservableList<String> obsList;

    public void start(Stage mainStage) {

        this.mainStage = mainStage;
        arraylist = new ArrayList<songClass>();

        obsList = FXCollections.observableArrayList();

        //JSONObject obj = new JSONObject();


        File yourFile = new File("data.json");

        if (yourFile.exists()) {
            try {
                JSONParser parser = new JSONParser();
                BufferedReader reader = new BufferedReader(new FileReader("data.json"));
                Object jsonObj = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) jsonObj;

                JSONArray song = (JSONArray) jsonObject.get("songName");
                JSONArray artist = (JSONArray) jsonObject.get("artistName");
                JSONArray album = (JSONArray) jsonObject.get("albumName");
                JSONArray year = (JSONArray) jsonObject.get("year");

                Iterator<String> a = song.iterator();
                Iterator<String> b = artist.iterator();
                Iterator<String> c = album.iterator();
                Iterator<String> d = year.iterator();

                String line;
                while (a.hasNext()) {
                    arraylist.add(new songClass(a.next(), b.next(), c.next(), d.next()));


                }
                reader.close();
            } catch (Exception e) {
                System.err.format("Exception occurred trying to read '%s'.", "data.json");
                e.printStackTrace();

            }
        }
        for (int i = 0; i < arraylist.size(); i++) {
            obsList.add(arraylist.get(i).toString2());

        }
        listview1.setItems(obsList);
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                JSONObject list2 = new JSONObject();
                PrintWriter pw = null;
                JSONArray songName = new JSONArray();
                JSONArray artistName = new JSONArray();
                JSONArray albumName = new JSONArray();
                JSONArray yearName = new JSONArray();

                for (int i = 0; i < arraylist.size(); i++) {
                    songName.add(arraylist.get(i).name);
                    artistName.add(arraylist.get(i).artist);
                    albumName.add(arraylist.get(i).album);
                    yearName.add(arraylist.get(i).year);
                }

                list2.put("songName", songName);
                list2.put("artistName", artistName);
                list2.put("albumName", albumName);
                list2.put("year", yearName);

                try {
                    FileWriter file = new FileWriter("data.json");
                    file.write(list2.toJSONString());
                    file.flush();
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        );

        if ((arraylist.size() != 0)) {
            listview1.setItems(obsList);
            listview1.getSelectionModel().select(0);
            songShow.setText(arraylist.get(0).name);
            artistShow.setText(arraylist.get(0).artist);
            albumShow.setText(arraylist.get(0).album);
            yearShow.setText(arraylist.get(0).year);
        }
        listview1
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(e -> showSongDetails());
    }

    private void showSongDetails() {

        int index = listview1.getSelectionModel().getSelectedIndex();
        if (index != -1 && arraylist.size() > index) {
            songShow.setText(arraylist.get(index).name);
            artistShow.setText(arraylist.get(index).artist);
            albumShow.setText(arraylist.get(index).album);
            yearShow.setText(arraylist.get(index).year);

        }
    }

    public void Delete(ActionEvent e) throws IOException {
        int index = listview1.getSelectionModel().getSelectedIndex();
        if (listview1.getSelectionModel().isEmpty()) {
            delete.setAlertType(AlertType.ERROR);
            delete.setContentText("error nothing selected");
            delete.show();
            return;
        }
        Alert confirmDelete = new Alert(AlertType.INFORMATION);
        confirmDelete.setTitle("Confirmation Dialog");
        //confirmDelete.setHeaderText("Look, a Confirmation Dialog");
        confirmDelete.setContentText("Are you ok with this?");

        Optional<ButtonType> result = confirmDelete.showAndWait();
        if (result.get() == ButtonType.OK) {
            obsList.remove(index);
            arraylist.remove(index);
            songShow.setText("");
            artistShow.setText("");
            albumShow.setText("");
            yearShow.setText("");
            showSongDetails();
        }

        listview1.setItems(obsList);
        listview1.getSelectionModel().select(index);
    }

    public void Add(ActionEvent e) throws IOException {

        SongTextField.setText((SongTextField.getText()).trim());
        ArtistTextField.setText((ArtistTextField.getText()).trim());
        AlbumTextField.setText((AlbumTextField.getText()).trim());
        YearTextField.setText((YearTextField.getText()).trim());


        boolean SongTrue = true;
        int index = listview1.getItems().size();
        if (SongTextField.getText().isEmpty() || ArtistTextField.getText().isEmpty()) {
            addAlert.setAlertType(AlertType.ERROR);
            addAlert.setContentText("Song and Artist must not be empty");
            addAlert.show();
            return;
        }


        for (int i = 0; i < YearTextField.getText().length(); i++) {
            if (!Character.isDigit(YearTextField.getText().charAt((i)))) {
                SongTrue = false;
            }
        }

        if (SongTrue == false || YearTextField.getText().length() != 4 && YearTextField.getText().length() != 0) {
            year.setAlertType(AlertType.ERROR);
            year.setContentText("Year must be length of 4 and must be numbers");
            year.show();
            return;

        }
        Alert confirmAdd = new Alert(AlertType.INFORMATION);
        confirmAdd.setTitle("Confirmation Dialog");
        //confirmAdd.setHeaderText("Look, a Confirmation Dialog");
        confirmAdd.setContentText("Are you ok with this?");

        Optional<ButtonType> result = confirmAdd.showAndWait();
        songClass temp = new songClass(SongTextField.getText(), ArtistTextField.getText(), AlbumTextField.getText(), YearTextField.getText());
        if (result.get() == ButtonType.OK) {
            if (arraylist.size() == 0) {
                listview1.getSelectionModel().select(0);
                obsList.add(0, temp.toString2());
                arraylist.add(0, temp);
                listview1.setItems(obsList);
                listview1.getSelectionModel().select(0);

                SongTextField.clear();
                ArtistTextField.clear();
                AlbumTextField.clear();
                YearTextField.clear();
                return;
            }
            for (int j = 0; j < arraylist.size(); j++) {
                if (temp.name.toUpperCase().compareTo(arraylist.get(j).name.toUpperCase()) < 0) {
                    obsList.add(j, temp.toString2());
                    arraylist.add(j, temp);
                    listview1.setItems(obsList);
                    listview1.getSelectionModel().select(0);
                    SongTextField.clear();
                    ArtistTextField.clear();
                    AlbumTextField.clear();
                    YearTextField.clear();
                    return;
                } else if (temp.name.toUpperCase().compareTo(arraylist.get(j).name.toUpperCase()) == 0) {
                    if (temp.artist.toUpperCase().compareTo(arraylist.get(j).artist.toUpperCase()) < 0) {
                        obsList.add(j, temp.toString2());
                        arraylist.add(j, temp);
                        listview1.setItems(obsList);
                        listview1.getSelectionModel().select(0);
                        SongTextField.clear();
                        ArtistTextField.clear();
                        AlbumTextField.clear();
                        YearTextField.clear();
                        return;
                    }
                    else if (temp.artist.toUpperCase().compareTo(arraylist.get(j).artist.toUpperCase()) == 0)
                    {
                        Alert duplicate = new Alert(AlertType.INFORMATION);
                        duplicate.setContentText("The song and artist names already exist!");
                        duplicate.show();
                        return;
                    }
                }
            }
            obsList.add(temp.toString2());
            arraylist.add(temp);
            listview1.setItems(obsList);
            listview1.getSelectionModel().select(0);
            SongTextField.clear();
            ArtistTextField.clear();
            AlbumTextField.clear();
            YearTextField.clear();
            return;
        }
        return;
    }
    public void Edit(ActionEvent e) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("editMenu.fxml"));
        AnchorPane root = (AnchorPane) loader.load();


        editController listController = loader.getController();
        listController.start(mainStage, listview1.getSelectionModel().getSelectedIndex(), arraylist, obsList);

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
    public void EditResult(ObservableList<String> obsList, Stage mainStage, ArrayList<songClass> arraylist, int index)
    {
            listview1.setItems(obsList);
            this.obsList = obsList;
            this.mainStage = mainStage;
            this.arraylist = arraylist;
        listview1.getSelectionModel().select(index);
        songShow.setText(arraylist.get(index).name);
        artistShow.setText(arraylist.get(index).artist);
        albumShow.setText(arraylist.get(index).album);
        yearShow.setText(arraylist.get(index).year);
        listview1
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(e -> showSongDetails());


    }
}
