/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fichier;

import stockageDonnees.StockageAeroports;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class OuvrirTxt pour ouvrir les fichiers texte d'aéroports 
 * 
 * @author thoma
 */
public class OuvrirTxt {
    
    private final List<StockageAeroports> listeAeroports;
    
    /**
     * Constructeur pour la class OpenTxt qui initialise la liste d'aéroports
     */
    public OuvrirTxt(){
        
        listeAeroports = new ArrayList<>();
    }
    
    /**
     * @author thomas
     * 
     * @param fichierTXT contient le chemin du fichier txt a lire
     * @return Renvoie une ArrayList contenant tous les aéroports chargés
     * @throws FileNotFoundException si le fichier n'est pas trouvé
     * @throws IOException si erreure d'entrée sortie
     */
    public List<StockageAeroports> LectureTxtAeroports(String fichierTXT) throws FileNotFoundException, IOException, CsvValidationException{
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(fichierTXT));
            String ligne;


            while ((ligne = br.readLine()) != null){

                String[] separation = ligne.split(";");


                String codeAeroport = separation[0];
                String lieu = separation[1];
                String latitude = separation[2] + ";" + separation[3] + ";" + separation[4] + ";"  + separation[5];
                String longitude = separation[6] + ";" + separation[7] + ";" + separation[8] + ";"  + separation[9];


                StockageAeroports aeroport = new StockageAeroports(codeAeroport, lieu, latitude, longitude);
                listeAeroports.add(aeroport);

            }
            
            
        } catch (Exception ex) {
            
            return null;
        }
        System.out.println("Fichié des aéroports ouvert");
        return listeAeroports;
    }
}
