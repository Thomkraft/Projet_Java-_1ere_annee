package File;

import Stockage.Vols;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.graphstream.graph.Graph;

/**
 *
 * @author thoma
 */
public class WriteInTxt {
    private int kMaximum = -1;
    private ArrayList<String> listLastFileUpdated = new ArrayList<>();
    private ArrayList<String> listLastColoFileUpdates = new ArrayList<>();
    private ArrayList<Graph> listLastGraphColo = new ArrayList<>();
    
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

    
    public void writeInFileResultColoration(List<Graph> graphes) throws IOException{
        
        this.listLastColoFileUpdates = new ArrayList<>();
        this.listLastGraphColo = new ArrayList<>();
        
        File directory = new File("./ResultatColoration");
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("Répertoire ResultatColoration créé avec succès.");
            } else {
                System.out.println("Erreur lors de la création du répertoire ResultatColoration.");
                return;
            }
        } else {
            System.out.println("Le répertoire ResultatColoration existe déjà.");
        }
        
        boolean csvCreated = false;
        for (Graph g : graphes) {
            
            
            String nomFichier = g.getAttribute("nomFichier").toString();
            String[] separation = nomFichier.split("\\\\");
            separation = separation[separation.length-1].split("/");
            
            
            System.out.println(separation[separation.length-1]);
            File myFile = new File("./ResultatColoration/" + separation[separation.length-1]);
            
            if(myFile.createNewFile()){
                    System.out.println("Le fichier : " + separation[separation.length-1] + " A bien été crée");
                } else {
                    System.out.println("Le fichier " + separation[separation.length-1] + " existe déjà dans ResultatsColisions.");
            }
            
            String filePath = "./ResultatColoration/" + separation[separation.length-1];
            FileWriter myFileWriter = new FileWriter(filePath);
            
           
            
            for (int i = 0; i < g.getNodeCount(); i++){
                Object couleur = g.getNode(i).getAttribute("couleur");
                String couleurString = couleur.toString();
                int intCouleur = Integer.parseInt(couleurString) + 1;
                myFileWriter.write(g.getNode(i) + " ; " + intCouleur +"\n");
            }
            
            myFileWriter.close();
            CSVWriter writer = null;
            
            listLastGraphColo.add(g);
            listLastColoFileUpdates.add(myFile.getAbsolutePath());
            
            
            if (!csvCreated){
                writer = new CSVWriter(new FileWriter("./ResultatColoration/coloration-groupe2.Y.csv"));
                csvCreated = true;
            } else {
            
                writer = new CSVWriter(new FileWriter("./ResultatColoration/coloration-groupe2.Y.csv", true));
            }
            String[] values = new String[]{separation[separation.length-1] + ";" + g.getAttribute("nbConflits").toString()};
            writer.writeNext(values);
        
            writer.close();
            
            
            System.out.println("Écriture réussie dans le fichier " + separation[separation.length-1] + ".txt.");
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

    public ArrayList<String> getListLastColoFileUpdates() {
        return listLastColoFileUpdates;
    }

    public ArrayList<Graph> getListLastGraphColo() {
        return listLastGraphColo;
    }
    
    
}
