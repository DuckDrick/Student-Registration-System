package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Add {
    @FXML
    private AnchorPane anchor;
    @FXML private ToggleGroup SG;
    @FXML private Pane grupe;
    @FXML private Pane studenta;
    @FXML private TextField vardas, pavarde, nGrupe;
    @FXML private ChoiceBox cGrupe;
    @FXML private ListView sSarasas, gSarasas;
    @FXML private Button addS;




    public void meniu(ActionEvent event) throws IOException {


        ((Stage)anchor.getScene().getWindow()).setScene(new Scene(FXMLLoader.load(getClass().getResource("sample.fxml"))));
    }




    public void addStud(ActionEvent event){

        int groupSelect=cGrupe.getItems().indexOf(cGrupe.valueProperty().getValue());
        if(groupSelect!=-1){
           Main.grupes.get(groupSelect).addStudent(vardas.getText(), pavarde.getText());
            Main.grupes.get(groupSelect).setGroupSize(Main.grupes.get(groupSelect).getGroupSize()+1);
            sSarasas.getItems().addAll(vardas.getText() + " " + pavarde.getText());

        }

    }

    public void remStud(ActionEvent event){
        int groupSelect=cGrupe.getItems().indexOf(cGrupe.valueProperty().getValue());
        int selected=sSarasas.getSelectionModel().getSelectedIndex();
        if(selected!=-1 && groupSelect!=-1){
            Main.grupes.get(groupSelect).remStudent(selected);
            sSarasas.getItems().remove(selected);
        }
    }

    public void edit(ActionEvent event){
        int groupSelect=cGrupe.getItems().indexOf(cGrupe.valueProperty().getValue());
        int selected=sSarasas.getSelectionModel().getSelectedIndex();
        if(selected!=-1 && groupSelect!=-1 && vardas.getText().length() != 0 && pavarde.getText().length()!=0){
            Main.grupes.get(groupSelect).getStud().get(selected).changeName(vardas.getText(), pavarde.getText());
            sSarasas.getItems().remove(selected);
            sSarasas.getItems().add(selected, vardas.getText() + " " + pavarde.getText());
        }
    }


    public void addGrp(ActionEvent event){
             for(Grupe g: Main.grupes){
                if(g.getPavadinimas().equals(nGrupe.getText())){
                    return;
                 }
            }

        Main.grupes.add(new Grupe(nGrupe.getText()));
        cGrupe.getItems().add(nGrupe.getText());
        gSarasas.getItems().add(nGrupe.getText());
    }

    public void remGrp(ActionEvent event){
        int selected=gSarasas.getSelectionModel().getSelectedIndex();
        if(selected!=-1){
            gSarasas.getItems().remove(selected);
            Main.grupes.remove(selected);
            cGrupe.getItems().remove(selected);
        }

    }

    public void save(ActionEvent event){

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"));
        fc.setInitialFileName("Sarasas");
        File selectedFile = fc.showSaveDialog((Stage)anchor.getScene().getWindow());
        if(selectedFile != null) {

            if(selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.')+1).equals("xlsx")){
                XSSFWorkbook workbook = new XSSFWorkbook();

                XSSFSheet sheet = workbook.createSheet(selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf('.')));
                int rNum = 0;
                for(Grupe g: Main.grupes){
                    int cNum = 0;
                    Row row = sheet.createRow(rNum++);
                    Cell cell = row.createCell(cNum++);
                    cell.setCellValue(g.getPavadinimas());
                    for(Studentas s: g.getStud()){
                        cNum = 1;
                        row = sheet.createRow(rNum++);
                        cell = row.createCell(cNum++);
                        cell.setCellValue(s.getVardas());
                        cell = row.createCell(cNum++);
                        cell.setCellValue(s.getPavarde());
                        for(LocalDate d: s.getLankomumas()){
                            cell = row.createCell(cNum++);
                            cell.setCellValue(d.toString());
                        }
                        row = sheet.createRow(rNum++);
                        cNum = 3;
                        for(LocalDate d: s.getLankomumasb()){
                            cell = row.createCell(cNum++);
                            cell.setCellValue(d.toString());
                        }
                    }
                }
                try{
                    FileOutputStream oS = new FileOutputStream(selectedFile.getAbsolutePath());
                    workbook.write(oS);
                    workbook.close();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else {
                try{
                    FileWriter fail = new FileWriter(selectedFile.getAbsolutePath());
                    for(Grupe g: Main.grupes){
                        fail.write(g.getPavadinimas() + "\n");
                        for(Studentas s:g.getStud()) {
                            fail.write(";" + s.getVardas() + ";" + s.getPavarde());
                            for(LocalDate d: s.getLankomumas()){
                                fail.write(";" + d);
                            }
                            fail.write("\n;;");
                            for(LocalDate d: s.getLankomumasb()){
                                fail.write(";" + d);
                            }
                            fail.write("\n");
                        }
                    }
                    fail.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }





    public void load(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog((Stage)anchor.getScene().getWindow());
        if(selectedFile!=null) {
            if (selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.') + 1).equals("xlsx")) {

                try{
                    FileInputStream excel = new FileInputStream(new File(selectedFile.getAbsolutePath()));
                    Workbook workbook = new XSSFWorkbook(excel);
                    Sheet sheet = workbook.getSheetAt(0);
                    Iterator<Row> iterator = sheet.iterator();

                    Row currentRow = iterator.next();
                    Iterator<Cell> cellIterator = currentRow.iterator();
                    String nowGroup = new String();
                    nowGroup = cellIterator.next().getStringCellValue();

                    while(iterator.hasNext()){

                        boolean exists = false;
                        for (Grupe g : Main.grupes) {
                            if(g.getPavadinimas().equals(nowGroup)){
                                exists = true;
                                break;
                            }
                        }
                        if(!exists){
                            System.out.println(nowGroup);
                            Main.grupes.add(new Grupe(nowGroup));
                            gSarasas.getItems().add(nowGroup);
                            cGrupe.getItems().add(nowGroup);
                        }

                        exists = false;
                        for (Grupe g : Main.grupes) {
                            if (g.getPavadinimas().equals(nowGroup)) {

                                exists = true;
                                while(iterator.hasNext()){
                                    currentRow = iterator.next();
                                    cellIterator = currentRow.iterator();

                                    if(currentRow.getCell(0) == null || currentRow.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK){
                                        String va = cellIterator.next().getStringCellValue();
                                        String pa = cellIterator.next().getStringCellValue();
                                        g.addStudent(va, pa);

                                            if(cGrupe.getSelectionModel().getSelectedIndex() != -1 && cGrupe.getItems().get(cGrupe.getSelectionModel().getSelectedIndex()).equals(nowGroup)){
                                                sSarasas.getItems().addAll(va + " " + pa);
                                                System.out.println(va);
                                            }
                                        while(cellIterator.hasNext()){
                                            LocalDate d = LocalDate.parse(cellIterator.next().getStringCellValue());
                                            g.getStud().get(g.getGroupSize()).addDate(d);

                                        }
                                        currentRow = iterator.next();
                                        if(currentRow.getCell(3) != null && currentRow.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK){
                                            System.out.println(currentRow.getCell(3).getStringCellValue());
                                            cellIterator = currentRow.iterator();
                                            while(cellIterator.hasNext()){
                                                LocalDate d = LocalDate.parse(cellIterator.next().getStringCellValue());
                                                g.getStud().get(g.getGroupSize()).addDateb(d);

                                            }
                                        }

                                        g.setGroupSize(g.getGroupSize() + 1);
                                    }else{
                                        String laikinas = cellIterator.next().getStringCellValue();

                                        nowGroup = laikinas;
                                        break;
                                    }
                                }
                            }
                            if(exists==true){
                                break;
                            }
                        }
                    }
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else if(selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.')+1).equals("csv")){
                    String laikinas = new String();
                try {
                    File failas = new File(selectedFile.getAbsolutePath());
                    if (failas.exists()) {
                        Scanner reader = new Scanner(failas);
                        String nowGroup = new String();
                        while (reader.hasNextLine()) {
                            String data = reader.nextLine();
                            if (data.charAt(0) == ';') {
                                for (Grupe g : Main.grupes) {
                                    if (g.getPavadinimas().equals(nowGroup)) {
                                        g.addStudent(data.substring(1).replaceAll(";[A-Za-z0-9-]*", ""), data.substring(1).replaceFirst("[A-Za-z]*;", "").replaceAll(";[0-9-]*", ""));

                                        data = data.substring(1).replaceAll("[A-Za-z]*[A-Za-z](;){0,2}", "");
                                        laikinas = new String();
                                        System.out.println(data);
                                        while (data.length() > 10) {
                                            laikinas = data.substring(0, 10);
                                            g.getStud().get(g.getGroupSize()).addDate(LocalDate.parse(laikinas));
                                            data = data.substring(11);
                                        }
                                        if (data.length() > 0)
                                            g.getStud().get(g.getGroupSize()).addDate(LocalDate.parse(data));

                                        data = reader.nextLine();

                                        if(data.length()>3) {
                                            data = data.substring(3);
                                            System.out.println(data);
                                            while (data.length() > 10) {
                                                laikinas = data.substring(0, 10);
                                                g.getStud().get(g.getGroupSize()).addDateb(LocalDate.parse(laikinas));
                                                data = data.substring(11);
                                            }
                                            if (data.length() > 0)
                                                g.getStud().get(g.getGroupSize()).addDateb(LocalDate.parse(data));

                                        }
                                        g.setGroupSize(g.getGroupSize() + 1);
                                    }
                                }
                            } else {
                                nowGroup = data;
                                boolean exist = false;
                                for (Grupe g : Main.grupes) {
                                    if (g.getPavadinimas().equals(data)) {
                                        exist = true;
                                    }
                                }
                                if (!exist) {
                                    Main.grupes.add(new Grupe(data));
                                }
                            }
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initialize(){

        formatText(vardas);
        formatText(pavarde);

        for(Grupe g: Main.grupes){
            cGrupe.getItems().add(g.getPavadinimas());
            gSarasas.getItems().add(g.getPavadinimas());
        }




        cGrupe.valueProperty().addListener((observable, oldValue, newValue) -> {
            sSarasas.getItems().clear();
            for(Studentas s: Main.grupes.get(cGrupe.getItems().indexOf(newValue)).getStud()){
                sSarasas.getItems().add(s.getVardas() + " " + s.getPavarde());
            }

        });




        SG.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                if(((ToggleButton)newValue).getText().equals("Grupe")){
                    studenta.setVisible(false);
                    grupe.setVisible(true);
                }else{
                    studenta.setVisible(true);
                    grupe.setVisible(false);
                }
            }else{
                    oldValue.setSelected(true);
            }
        });
    }


    private void formatText(TextField tF){
        tF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()!=0)
            if(!newValue.matches("[A-Za-z]{0,30}")){
                tF.setText(oldValue);
            }
        });
    }
}
