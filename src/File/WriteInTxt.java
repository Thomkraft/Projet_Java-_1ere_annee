package File;

import Stockage.Vols;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thoma
 */
public class WriteInTxt {
    private int kMaximum = -1;
    private ArrayList<String> listLastFileUpdated = new ArrayList<>();
    
    public static void createResultatsColisionsDirectory() {
        File directory = new File("./ResultatsColisions");
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("Répertoire ResultatsColisions créé avec succès.");
            } else {
                System.out.println("Erreur lors de la création du répertoire ResultatsColisions.");
            }
        } else {
            System.out.println("Le répertoire ResultatsColisions existe déjà.");
        }
    }
    
    public static void createFile(String fileName) throws IOException {
        File myFile = new File("./ResultatsColisions/" + fileName + ".txt");
        
        if(myFile.createNewFile()){
            System.out.println("Fichier " + fileName + ".txt créé avec succès dans ResultatsColisions.");
        } else {
            System.out.println("Le fichier " + fileName + ".txt existe déjà dans ResultatsColisions.");
        }
    }
    
    // Gestion d'erreur si kmax = -1 alors erreur
    public void writeInFile(String fileName, ArrayList<String> listeColisions, int nbVols, int kMax) throws IOException {
        // Chemin relatif du répertoire de résultats
        String resultsDirectory = "./ResultatsColisions/";
        
        // Création du répertoire s'il n'existe pas
        File directory = new File(resultsDirectory);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Répertoire ResultatsColisions créé avec succès.");
            } else {
                System.err.println("Erreur lors de la création du répertoire ResultatsColisions.");
                // Vous pouvez lancer une exception ou gérer l'erreur selon votre besoin
            }
        }
        
        // Chemin absolu du fichier de sortie
        String filePath = resultsDirectory + fileName + ".txt";
        
        // Écriture dans le fichier
        FileWriter myFile = new FileWriter(filePath);
        myFile.write(Integer.toString(kMax));
        myFile.write("\n" + Integer.toString(nbVols));
        for(String value : listeColisions){
            myFile.write("\n" + value);
        }
        myFile.close();
        listLastFileUpdated.add(filePath);
        
        System.out.println("Écriture réussie dans le fichier " + fileName + ".txt.");
    }
    
    public void writeInFileAeroport(String fileName, ArrayList<String> listeColisions, ArrayList<Vols> listeVols, int nbVols) throws IOException {
        FileWriter myFile = new FileWriter("./ResultatsColisions/" + fileName + ".txt");
        
        myFile.write(Integer.toString(nbVols));
        
        for(String value : listeColisions){
            String[] separation = value.split(" ");
            System.out.println("Dans le write in file aeroports");
            System.out.println(separation[0]);
            System.out.println(separation[1]);
            
            String nomAeroport1Départ = listeVols.get(listeVols.indexOf(separation[0])).getAéroportDépart();
            String nomAeroport1Arrivée = listeVols.get(listeVols.indexOf(separation[0])).getAéroportArrivée();
            String nomAeroport2Départ = listeVols.get(listeVols.indexOf(separation[1])).getAéroportDépart();
            String nomAeroport2Arrivée = listeVols.get(listeVols.indexOf(separation[1])).getAéroportArrivée();
            
            myFile.write("\n" + nomAeroport1Départ + " " + nomAeroport1Arrivée);
            myFile.write("\n" + nomAeroport2Départ + " " + nomAeroport2Arrivée);
        }
        
        myFile.close();
        System.out.println("Écriture réussie dans le fichier " + fileName + ".txt.");
    }
    
    public int getkMax() {
        return kMaximum;
    }
    
    public void setkMax(int kMax) {
        this.kMaximum = kMax;
    }
    
    public List<String> getListLastFileUpdated() {
        return listLastFileUpdated;
    }
    
    public void setListLastFileUpdated(ArrayList<String> listLastFileUpdated) {
        this.listLastFileUpdated = listLastFileUpdated;
    }
}
