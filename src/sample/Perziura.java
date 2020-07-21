package sample;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Perziura {
    @FXML private AnchorPane anchor;
    @FXML private ChoiceBox cGroup;
    @FXML private TableView<Studentas> table, tv;
    @FXML private TableColumn<Studentas, String> vardas, pavarde;
    @FXML private DatePicker nuo, iki;

    private List<TableColumn<Studentas, String>> lank = new ArrayList<TableColumn<Studentas, String>>();
    private int diff = -1;

    public void meniu(ActionEvent event) throws IOException {
        ((Stage)anchor.getScene().getWindow()).setScene(new Scene(FXMLLoader.load(getClass().getResource("sample.fxml"))));
    }
    public void initialize(){
        vardas.setCellValueFactory(new PropertyValueFactory<Studentas, String>("vardas"));
        pavarde.setCellValueFactory(new PropertyValueFactory<Studentas, String>("pavarde"));

        for(Grupe g: Main.grupes){
            cGroup.getItems().add(g.getPavadinimas());
        }

        cGroup.valueProperty().addListener((observable, oldValue, newValue) -> {
           tableUpdate();
        });

        nuo.valueProperty().addListener((observable, oldValue, newValue) -> {
           if(iki.getValue()!=null){
               diff=(int)ChronoUnit.DAYS.between(nuo.getValue(),iki.getValue());
               if(cGroup.getSelectionModel().getSelectedItem()!=null)
               tableUpdate();
           }
        });
        iki.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (nuo.getValue() != null){
                diff = (int) ChronoUnit.DAYS.between(nuo.getValue(), iki.getValue());
                if(cGroup.getSelectionModel().getSelectedItem()!=null)
                tableUpdate();
            }
        });
    }


    public void tableUpdate(){
        for(Studentas s: Main.grupes.get(cGroup.getSelectionModel().getSelectedIndex()).getStud()){
            s.resetLaikinas();
        }

        Studentas.resetNum();
        table.getItems().clear();
        table.getItems().addAll(Main.grupes.get(cGroup.getSelectionModel().getSelectedIndex()).getStud());
        table.getColumns().remove(2,table.getColumns().size());
        lank.removeAll(lank);
        Studentas.sendDate(nuo.getValue());
        Studentas.setDif(diff);

        for(int i = 0; i <= diff; i++){
            lank.add(new TableColumn<Studentas, String>());
            lank.get(i).setText(ChronoUnit.DAYS.addTo(nuo.getValue(),i) + "");

            lank.get(i).setCellValueFactory(new PropertyValueFactory<Studentas, String>("kazkas"));
                table.getColumns().add(lank.get(i));

        }

        for(int i = 2; i < table.getColumns().size(); i++){
            boolean no = false;
            for(int j = 0; j < Main.grupes.get(cGroup.getSelectionModel().getSelectedIndex()).getGroupSize(); j++){
                System.out.print(table.getColumns().get(7).getCellData(j).toString());
               // if(table.getColumns().get(2).getCellData(3).toString().length() > 1){
               //     no = true;
               // }
            }
            System.out.println(" ");
            //if(!no){
             //   table.getItems().remove(i);
             //   i--;
             //   diff--;
          //  }
        }
    }

    public void savepdf() throws IOException {
        if(cGroup.getSelectionModel().getSelectedItem() != null && nuo.getValue() != null && iki.getValue() != null && diff >= 0)
        new makePdf(nuo.getValue(), iki.getValue(), Main.grupes.get(cGroup.getSelectionModel().getSelectedIndex()), anchor);
    }

}
