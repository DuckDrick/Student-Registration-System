package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Lankomumas {

    @FXML
    private TableView tv;
    @FXML
    private ChoiceBox cGroup;
    @FXML private TableColumn<Studentas, String> vardas, pavarde;
    @FXML private TableColumn<Studentas, CheckBox> check;
    @FXML private AnchorPane anchor;
    @FXML private DatePicker data;
    private ListView lv;
    private CheckBoxListCell<String> zz = new CheckBoxListCell<String>();
    private CheckBoxListCell<String> bb = new CheckBoxListCell<String>();
    private CheckBox s = new CheckBox();
    public void meniu(ActionEvent event) throws IOException {
        for(Grupe g: Main.grupes){
            for(Studentas s: g.getStud()){
                s.setStateCheckBox(false);
            }
        }

        ((Stage)anchor.getScene().getWindow()).setScene(new Scene(FXMLLoader.load(getClass().getResource("sample.fxml"))));
    }
    public void initialize(){
        vardas.setCellValueFactory(new PropertyValueFactory<Studentas, String>("vardas"));
        pavarde.setCellValueFactory(new PropertyValueFactory<Studentas, String>("pavarde"));
        check.setCellValueFactory(new PropertyValueFactory<Studentas, CheckBox>("checkBox"));

        data.setValue(LocalDate.now());

        data.valueProperty().addListener((observableValue, localDate, t1) -> {
            if(cGroup.getSelectionModel().getSelectedItem()!=null)
            tableUpdate();
        });
        for(Grupe g: Main.grupes){
            cGroup.getItems().add(g.getPavadinimas());
        }
        cGroup.valueProperty().addListener((observable, oldValue, newValue) -> {
            tableUpdate();

        });
    }
    public void saveDates(){
        if(cGroup.getSelectionModel().getSelectedItem() != null)
        if(data.getValue()!=null)
            for(Studentas s: Main.grupes.get(cGroup.getSelectionModel().getSelectedIndex()).getStud()){
                if(s.getCheckBox().isSelected()) {
                    s.addDate(data.getValue());
                }else{
                    s.addDateb(data.getValue());
                }
            }


    }
    public void clear(){
        if(cGroup.getSelectionModel().getSelectedItem() != null)
            if(data.getValue()!=null)
                for(Studentas s: Main.grupes.get(cGroup.getSelectionModel().getSelectedIndex()).getStud()){
                    if(s.getCheckBox().isSelected()) {
                        s.remove(data.getValue());
                    }else{
                        s.remove(data.getValue());
                    }
                }
        tableUpdate();
    }
    public void tableUpdate(){
        for(Grupe g: Main.grupes){
            for(Studentas s: g.getStud()){
                s.setStateCheckBox(false);
            }
        }
        for(Studentas s: Main.grupes.get(cGroup.getSelectionModel().getSelectedIndex()).getStud()){
            int laikinas;
            for(LocalDate d: s.getLankomumas()){
                laikinas = data.getValue().compareTo(d);
                if(laikinas == 0){
                    s.setStateCheckBox(true);
                }else if(laikinas > 1){
                    break;
                }
            }
        }
        tv.setItems(Main.grupes.get(cGroup.getSelectionModel().getSelectedIndex()).getStud());
    }
}
