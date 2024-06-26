/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fichier;

import interfaceApplication.Fenetre;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.graphstream.graph.Graph;

/**
 *Class WriteInCsv pour écrire dans des fichiers csv ainsi que les créer au besoin
 * ecrit pour la coloration ou bien les colisions
 * 
 * @author thoma
 */
public class EcrireDansCSV {
    
    private final ArrayList<String> listLastColoFileUpdates;
    private final ArrayList<Graph> listLastGraphColo;
    private final Fenetre fen;

    /**
     * Constructeur de la class WriteInTxt et initialise les attributs
     * 
     * @author Thomas
     * 
     * @param listLastColoFileUpdates contient une liste des dernier fichier txt crée contenant les info de colo sur un graphe
     * @param listLastGraphColo contient une liste des dernier graphe colorié en parrallele des fichiers txt
     * @param fen contient la fenetre parent
     */
    public EcrireDansCSV(ArrayList<String> listLastColoFileUpdates,ArrayList<Graph> listLastGraphColo, Fenetre fen) {
        
        this.listLastGraphColo = listLastGraphColo;
        this.listLastColoFileUpdates = listLastColoFileUpdates;
        this.fen = fen;
    }
    
    /**
     * Methode EcrireDernierCSVFichierColo, va écrire dans un dossier donné en paramètre un fichier .csv (nommé par une entrée utilisateur et par defaut coloration-groupe2.Y.csv) contenant pour chaque
 ligne le un fichier .txt ; nombre de conflict dans ce fichier
     * 
     * @author Thomas
     * 
     * @param path chemin ou l'on veut ecrire le .csv 
     * @throws IOException si erreur d'entrée sortis / utilise le nom de fichier par default si erreur
     */
    public void EcrireDernierCSVFichierColo(String path) throws IOException{
        boolean csvCree = false;
        
        int i = 0;
        
        String NomFichier = "coloration-groupe2.Y";
        
        String Valeur = JOptionPane.showInputDialog(fen, "Entrez le nom de votre fichier (coloration-groupe2.Y par defaut) :");
        
        if (!Valeur.isEmpty()) {
            NomFichier = Valeur;
        }
        
        for (Graph g: listLastGraphColo){
            CSVWriter writer;
            
            if (!csvCree){
                    writer = new CSVWriter(new FileWriter(path + NomFichier + ".csv"));
                    csvCree = true;
            } else {

                writer = new CSVWriter(new FileWriter(path + NomFichier + ".csv", true));
            }
            
            String cheminFichier = listLastColoFileUpdates.get(i);
            
            String[] separation = cheminFichier.split("\\\\");
            
            String[] valeur = new String[]{separation[separation.length-1] + ";" + g.getAttribute("nbConflits").toString()};
            writer.writeNext(valeur);

            writer.close();
                
            i++;
        }
    }
    
}
