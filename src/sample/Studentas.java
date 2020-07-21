package sample;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Studentas {

    private static LocalDate data;
    private static int num;
    private static int dif;
    private int id;
    private String vardas;
    private String pavarde;
    private Grupe gr;
    private List<LocalDate> lankomumas;
    private List<LocalDate> lankomumasb;
    private CheckBox checkBox;
    private int laikinas=0;

    public Studentas(String vardas, String pavarde, int id, Grupe gr){
        this.gr = gr;
        this.id = id;
        this.vardas = vardas;
        this.pavarde = pavarde;
        lankomumas = new ArrayList<LocalDate>();
        lankomumasb = new ArrayList<LocalDate>();
        checkBox = new CheckBox();
        checkBox.setSelected(false);
        num = 0;
    }
    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setStateCheckBox(boolean state){
        this.checkBox.setSelected(state);
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
    public static void setDif(int di){
        dif=di;
    }

    public int getId() {
        return id;
    }

    public String getVardas() {
        return vardas;
    }

    public String getPavarde() {
        return pavarde;
    }

    public List<LocalDate> getLankomumasb(){
        return lankomumasb;
    }
    public void addDateb(LocalDate date){
        for(LocalDate d: lankomumasb){
            if(date == d){
                return;
            }else if(date.compareTo(d)<0){
                lankomumasb.add(lankomumasb.indexOf(d), date);
                return;
            }
        }
        lankomumasb.add(date);
    }

    public void remove(LocalDate date){
        lankomumas.remove(date);
        lankomumasb.remove(date);
    }

    public List<LocalDate> getLankomumas(){
        return lankomumas;
    }
    public void addDate(LocalDate date){
        for(LocalDate d: lankomumas){
            if(date == d){
                return;
            }else if(date.compareTo(d)<0){
                lankomumas.add(lankomumas.indexOf(d), date);
                return;
            }
        }
        lankomumas.add(date);
    }
    public void resetLaikinas(){
        laikinas = 0;
    }

    public void changeName(String vardas, String pavarde){
        this.vardas = vardas;
        this.pavarde = pavarde;
    }

    public String getKazkas(){

        if(laikinas>dif)
            laikinas = 0;

        num++;
        for(LocalDate d: lankomumas){
            if(d.compareTo(data.plusDays(laikinas))==0){
                if(num>(dif+1)*gr.getGroupSize()){

                    laikinas++;
                }
                return "Nebuvo";
            }
        }
        for(LocalDate d: lankomumasb){
            if(d.compareTo(data.plusDays(laikinas))==0){
                if(num>(dif+1)*gr.getGroupSize()){

                    laikinas++;
                }
                return "Buvo";
            }
        }

        if(num>(dif+1)*gr.getGroupSize()){
            laikinas++;
        }


        return "";

    }
    public static void sendDate(LocalDate d){
        data=d;
    }
    public static void resetNum(){
        num=0;
    }
}
