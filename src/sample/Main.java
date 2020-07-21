package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {
    private Scene scene;
    public static List<Grupe> grupes = new ArrayList<Grupe>();
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Registracijos sistema");
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            File failas = new File("save.txt");
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
                                String laikinas = new String();
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
        } catch (IOException e){
            e.printStackTrace();
        }
        launch(args);
    }

    @Override
    public void stop(){
        try{
            FileWriter fail = new FileWriter("save.txt");
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
