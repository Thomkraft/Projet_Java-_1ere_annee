/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File;

import Stockage.Aeroports;
import Stockage.Vols;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thoma
 */
public class OpenCsv {

    private List<Vols> listeVols;

    
    public OpenCsv(){
        System.out.println("Fichié des vols ouvert");
        listeVols = new ArrayList<>();
    }
    
    public List<Vols> LectureCsvVols(String fichierCSV) throws FileNotFoundException, IOException, CsvValidationException{
        try {
            CSVReader reader = new CSVReader(new FileReader(fichierCSV));
            String[] ligne;


            //aéroport de départ de type aéroports ?
            while ((ligne = reader.readNext()) != null){
                if (ligne[0].length()> 15) {
                    ligne = ligne[0].split(";");
                }
                String nomVol = ligne[0];
                String aeroportDepart = ligne[1];
                String aeroportArrivee = ligne[2];
                int heureDepart = Integer.parseInt(ligne[3]);
                int minutesDepart = Integer.parseInt(ligne[4]);
                int duree = Integer.parseInt(ligne[5]);

                Vols vol = new Vols(nomVol, aeroportDepart, aeroportArrivee, heureDepart, minutesDepart, duree);
                listeVols.add(vol);

            }
        } catch (NumberFormatException ex) {
            System.err.println("le fichier de vol" + fichierCSV + " n'est pas un .csv ou n'est pas formaté comme il faut !");
            return null;
        }
        
      
        return listeVols;
        
    }

}
