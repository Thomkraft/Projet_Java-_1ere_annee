/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fichier;

import stockageDonnées.Vols;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
        
/**
 *Class OuvrirCsv pour ouvrir un fichier .csv d'une liste de vols
 * 
 * @author thomas
 * 
 */
public class OuvrirCsv {

    private static int nbLignes;
    private List<Vols> listeVols;

    /**
     * Constructeur de la class OpenCsv
     * Initialise la liste de vol;
     */
    public OuvrirCsv(){
        System.out.println("Fichié des vols ouvert");
        listeVols = new ArrayList<>();
    }
    
    /**
     * Mhétode pour lire le fichier .csv contenant les vols
     * 
     * @author thomas
     * 
     * @param fichierCSV contient le chemin du fichier csv a lire
     * @return Renvoie la une arraylist contenant les informations sur les différents vols
     * @throws FileNotFoundException si le fichier n'est pas trouvé
     * @throws IOException si erreure d'entrée sortie
     * @throws CsvValidationException si le fichier n'est pas un CSV
     * @throws NumberFormatException Si le fichier n'est pas un .csv ou est mal formaté
     */
    public List<Vols> LectureCsvVols(String fichierCSV) throws FileNotFoundException, IOException, CsvValidationException, Exception{
        try {
            nbLignes = 0;
            CSVReader lecteur = new CSVReader(new FileReader(fichierCSV));
            String[] ligne;
            if (fichierCSV.contains(".txt")){
                throw new IllegalArgumentException("le fichier de vol" + fichierCSV + " n'est pas un .csv");
            } 

            while ((ligne = lecteur.readNext()) != null){
                try {
                    if (ligne[0].length()> 15) {
                        ligne = ligne[0].split(";");
                    }
                    String nomVol = ligne[0];
                    String aeroportDepart = ligne[1];
                    String aeroportArrivee = ligne[2];
                    int heureDepart = Integer.parseInt(ligne[3]);
                    int minutesDepart = Integer.parseInt(ligne[4]);
                    int duree = Integer.parseInt(ligne[5]);

                    Vols vol = new Vols(nomVol, aeroportDepart, aeroportArrivee, heureDepart, minutesDepart, duree, -1);
                    listeVols.add(vol);
                    nbLignes++;
                } catch (NumberFormatException ex){
                    continue;
                }
                
                if (nbLignes == 0){
                    throw new Exception("Mauvais format pour toutes les lignes du fichier CSV !");
                   
                }

            }
        } catch (IllegalArgumentException ex) {
            System.err.println("Erreur : " + ex.getMessage());
            return null;
         
        } catch (Exception ex){
            System.err.println("Erreur : " + ex.getMessage());
            return null;
        }
        
      
        return listeVols;
        
    }

    public static int getNbLignes() {
        return nbLignes;
    }
    

}
