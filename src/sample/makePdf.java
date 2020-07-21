package sample;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.Days360;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class makePdf {
    private LocalDate nuo, iki;
    private Grupe grupe;

    public makePdf(LocalDate nuo, LocalDate iki, Grupe grupe, AnchorPane anchor) throws IOException {
        this.nuo = nuo;
        this.iki = iki;
        this.grupe = grupe;
        try {

            FileChooser fc = new FileChooser();
            fc.setInitialFileName("Lankomumas");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            System.out.println("SDASDASD");
            File sd = fc.showSaveDialog((Stage)anchor.getScene().getWindow());
            if(sd != null) {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(sd.getAbsolutePath()));
                document.open();
                addMetaData(document);
                addContent(document);
                document.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMetaData(Document document) {
        document.addTitle("Lankomumas");
    }

    private void addContent(Document document) throws DocumentException {

        int kol=6;
        if(DAYS.between(nuo, iki)<3){
            kol = (int) (2+DAYS.between(nuo, iki)+1);
        }
        PdfPTable table = new PdfPTable(kol);

        table.setSplitLate(false);

        int a= 0;
        table.addCell("Vardas");
        table.addCell("Pavarde");
        LocalDate rowStart = nuo;
        int laikinas = 0;
        for(LocalDate d=nuo; d.compareTo(iki)<=0; d=d.plusDays(1)) {
            if(d.compareTo(nuo)>3){
                if(d.compareTo(nuo)==4+6*laikinas){
                    rowStart=d;
                    laikinas++;
                }
            }
            table.addCell(d.toString());
            if(d.compareTo(iki)==0){

                if(kol==6 && DAYS.between(nuo,iki)>3)
            for(int i=0; i<(5*(DAYS.between(nuo, iki)+1+2)%6)%6; i++){
                System.out.println("ADD");
                table.addCell("");
            }
                if(d.minusDays(3).compareTo(nuo)<0){
                    for(Studentas s: grupe.getStud()){
                        System.out.println(s.getVardas() + " " + s.getPavarde());
                        table.addCell(s.getVardas());
                        table.addCell(s.getPavarde());
                        for(int i = 0; i< DAYS.between(nuo,iki) +1; i++){
                            int papuoliau = 2;
                            for(LocalDate ld: s.getLankomumas()) {
                                if (ld.compareTo(rowStart.plusDays(i)) == 0) {
                                    papuoliau --;
                                    table.addCell("Nebuvo");
                                    System.out.print("NEBUVO");
                                    break;
                                }
                            }
                            for(LocalDate ld: s.getLankomumasb()) {
                                if (ld.compareTo(rowStart.plusDays(i)) == 0) {
                                    papuoliau --;
                                    table.addCell("Buvo");
                                    System.out.print("BUVO");
                                    break;
                                }
                            }
                            if(papuoliau==2){
                                table.addCell("");
                                System.out.print("NoN");
                            }
                        }
                        System.out.println("");
                    }
                }else{

                for(Studentas s: grupe.getStud()){

                    for(int i=0; i<(DAYS.between(nuo, iki)+1+2)%6; i++){
                        int papuoliau = 2;
                        for(LocalDate ld: s.getLankomumas()) {
                            if (ld.compareTo(rowStart.plusDays(i)) == 0) {
                                papuoliau --;
                                table.addCell("Nebuvo");
                                System.out.print("NEBUVO");
                                break;
                            }
                        }
                        for(LocalDate ld: s.getLankomumasb()) {
                            if (ld.compareTo(rowStart.plusDays(i)) == 0) {
                                papuoliau --;
                                table.addCell("Buvo");
                                System.out.print("BUVO");
                                break;
                            }
                        }
                        if(papuoliau==2){
                            table.addCell("");
                            System.out.print("NoN");
                        }
                    }
                    for(int i=0; i<(5*(DAYS.between(nuo, iki)+1+2)%6)%6; i++){
                        table.addCell("");
                    }
                }
            }}
            if(d.minusDays(3).compareTo(nuo)==0){
                for(Studentas s: grupe.getStud()){
                    table.addCell(s.getVardas());
                    table.addCell(s.getPavarde());
                    for(int i=0; i<4; i++){
                        int papuoliau = 2;
                        for(LocalDate ld: s.getLankomumas()) {
                            if (ld.compareTo(rowStart.plusDays(i)) == 0) {
                                papuoliau --;
                                table.addCell("Nebuvo");
                                System.out.print("NEBUVO");
                                break;
                            }
                        }
                        for(LocalDate ld: s.getLankomumasb()) {
                            if (ld.compareTo(rowStart.plusDays(i)) == 0) {
                                papuoliau --;
                                table.addCell("Buvo");
                                System.out.print("BUVO");
                                break;
                            }
                        }
                        if(papuoliau==2){
                            table.addCell("");
                            System.out.print("NoN");
                        }
                    }
                }
            }else if(d.minusDays(9+6*a).compareTo(nuo)==0){
                a++;

                for(Studentas s: grupe.getStud()){
                    for(int i=0; i<6; i++){
                        int papuoliau = 2;
                        for(LocalDate ld: s.getLankomumas()) {
                            if (ld.compareTo(rowStart.plusDays(i)) == 0) {
                                papuoliau --;
                                table.addCell("Nebuvo");
                                System.out.print("NEBUVO");
                                break;
                            }
                        }
                        for(LocalDate ld: s.getLankomumasb()) {
                            if (ld.compareTo(rowStart.plusDays(i)) == 0) {
                                papuoliau --;
                                table.addCell("Buvo");
                                System.out.print("BUVO");
                                break;
                            }
                        }
                        if(papuoliau==2){
                            table.addCell("");
                            System.out.print("NoN");
                        }
                    }
                }
            }
        }

       document.add(table);
    }
}
