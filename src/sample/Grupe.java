package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Grupe {
    private String pavadinimas;
    private int GroupSize;
    private ObservableList<Studentas> stud = FXCollections.observableArrayList();

    public Grupe(String pavadinimas){
        this.pavadinimas = pavadinimas;
        this.GroupSize = 0;
    }

    public void addStudent(String vardas, String pavarde){
        stud.add(new Studentas(vardas, pavarde, stud.size(), this));
    }
    public void remStudent(int index){
        stud.remove(index);
    }


    public String getPavadinimas() {
        return pavadinimas;
    }

    public ObservableList<Studentas> getStud() {
        return stud;
    }

    public int getGroupSize() {
        return GroupSize;
    }

    public void setGroupSize(int groupSize) {
        GroupSize = groupSize;
    }
}
