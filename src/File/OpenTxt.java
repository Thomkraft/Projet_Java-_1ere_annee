/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File;

import Stockage.Aeroports;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thoma
 */
public class OpenTxt {
    
    private List<Aeroports> listeAeroports;
    
    public OpenTxt(){
        
        listeAeroports = new ArrayList<>();
    }
    
    public List<Aeroports> LectureTxtAéroports(String fichierTXT) throws FileNotFoundException, IOException, CsvValidationException{
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(fichierTXT));
            String ligne;


            while ((ligne = br.readLine()) != null){

                String[] separation = ligne.split(";");


                String codeAeroport = separation[0];
                String lieu = separation[1];
                String latitude = separation[2] + ";" + separation[3] + ";" + separation[4] + ";"  + separation[5];
                String longitude = separation[6] + ";" + separation[7] + ";" + separation[8] + ";"  + separation[9];


                Aeroports aeroport = new Aeroports(codeAeroport, lieu, latitude, longitude);
                listeAeroports.add(aeroport);

            }
            
            
        } catch (Exception ex) {
            
            return null;
        }
        System.out.println("Fichié des aéroports ouvert");
        return listeAeroports;
    }
}
