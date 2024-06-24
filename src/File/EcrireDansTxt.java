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
 *CLass EcrireDansTxt pour écrire dans un fichier texte les infos comme 
 * les resultats de colorations ou les résultats de colisions
 * 
 * @author thoma
 */
public class EcrireDansTxt {
    private int kMaximum = -1;
    private ArrayList<String> listDernierFichierMAJ = new ArrayList<>();
    private ArrayList<String> listDernierColoFileMAJ = new ArrayList<>();
    private ArrayList<Graph> listDernierGraphColoMAJ = new ArrayList<>();
    
    /**
     * Crée un dossier ResultatsColisions a la racine du projet si il n'existe pas deja
     * 
     * @author thomas
     */
    public static void createResultatsColisionsDirectory() {
        File chemin = new File("./ResultatsColisions");
        if (!chemin.exists()) {
            if (chemin.mkdir()) {
                System.out.println("Répertoire ResultatsColisions créé avec succès.");
            } else {
                System.out.println("Erreur lors de la création du répertoire ResultatsColisions.");
            }
        } else {
            System.out.println("Le répertoire ResultatsColisions existe déjà.");
        }
    }
    
    /**
     * Crée un fichier txt avec le nom spécifié dans le répertoire "ResultatsColisions".
     * 
     * @author thomas
     * 
     * @param nomFichier le nom du fichier à créer
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    public static void créationFichier(String nomFichier) throws IOException {
        File monFichier = new File("./ResultatsColisions/" + nomFichier + ".txt");
        
        if(monFichier.createNewFile()){
            System.out.println("Fichier " + nomFichier + ".txt créé avec succès dans ResultatsColisions.");
        } else {
            System.out.println("Le fichier " + nomFichier + ".txt existe déjà dans ResultatsColisions.");
        }
    }

    /**
     * Écrit les résultats de la coloration des graphes dans des fichiers dans le répertoire "ResultatColoration".
     * Les données de chaque graphe sont écrites dans un fichier séparé.
     * 
     * @author thomas
     * 
     * @param graphes une liste d'objets Graph à traiter et écrire dans les fichiers
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    public void ecrireDansFichierResultatColoration(List<Graph> graphes) throws IOException{
        
        this.listDernierColoFileMAJ = new ArrayList<>();
        this.listDernierGraphColoMAJ = new ArrayList<>();
        
        File chemin = new File("./ResultatColoration");
        if (!chemin.exists()) {
            if (chemin.mkdir()) {
                System.out.println("Répertoire ResultatColoration créé avec succès.");
            } else {
                System.out.println("Erreur lors de la création du répertoire ResultatColoration.");
                return;
            }
        } else {
            System.out.println("Le répertoire ResultatColoration existe déjà.");
        }
        
        
        for (Graph g : graphes) {
            
            
            String nomFichier = g.getAttribute("nomFichier").toString();
            String[] separation = nomFichier.split("\\\\");
            separation = separation[separation.length-1].split("/");
            
            
            System.out.println(separation[separation.length-1]);
            File monFichier = new File("./ResultatColoration/" + separation[separation.length-1]);
            
            if(monFichier.createNewFile()){
                    System.out.println("Le fichier : " + separation[separation.length-1] + " A bien été crée");
                } else {
                    System.out.println("Le fichier " + separation[separation.length-1] + " existe déjà dans ResultatsColisions.");
            }
            
            String cheminFichier = "./ResultatColoration/" + separation[separation.length-1];
            FileWriter lecteurFichier = new FileWriter(cheminFichier);
            
           
            
            for (int i = 0; i < g.getNodeCount(); i++){
                Object couleur = g.getNode(i).getAttribute("couleur");
                String couleurString = couleur.toString();
                int intCouleur = Integer.parseInt(couleurString) + 1;
                lecteurFichier.write(g.getNode(i) + " ; " + intCouleur +"\n");
            }
            
            lecteurFichier.close();
            
            listDernierGraphColoMAJ.add(g);
            listDernierColoFileMAJ.add(monFichier.getAbsolutePath());
            
            
            
            System.out.println("Écriture réussie dans le fichier " + separation[separation.length-1] + ".txt.");
        }
        
        
        
    }
    
    /**
     * Écrit les données de collision et d'autres informations dans un fichier dans le répertoire "ResultatsColisions".
     * 
     * @author thomas
     * 
     * @param nomFichier le nom du fichier à créer
     * @param listeColisions une liste de chaînes de données de collisions à écrire
     * @param nbVols le nombre de vols
     * @param kMax la valeur de kmax
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    public void ecritureDansFichier(String nomFichier, ArrayList<String> listeColisions, int nbVols, int kMax) throws IOException {
        // Chemin relatif du répertoire de résultats
        String cheminFichierResultat = "./ResultatsColisions/";
        
        // Création du répertoire s'il n'existe pas
        File dossier = new File(cheminFichierResultat);
        if (!dossier.exists()) {
            boolean creer = dossier.mkdirs();
            if (creer) {
                System.out.println("Répertoire ResultatsColisions créé avec succès.");
            } else {
                System.err.println("Erreur lors de la création du répertoire ResultatsColisions.");
                // Vous pouvez lancer une exception ou gérer l'erreur selon votre besoin
            }
        }
        
        // Chemin absolu du fichier de sortie
        String cheminFichier = cheminFichierResultat + nomFichier + ".txt";
        
        // Écriture dans le fichier
        FileWriter monFichier = new FileWriter(cheminFichier);
        monFichier.write(Integer.toString(kMax));
        monFichier.write("\n" + Integer.toString(nbVols));
        for(String value : listeColisions){
            monFichier.write("\n" + value);
        }
        monFichier.close();
        listDernierFichierMAJ.add(cheminFichier);
        
        System.out.println("Écriture réussie dans le fichier " + nomFichier + ".txt.");
    }
    
    /**
     * Écrit les données de collision et les informations des aéroports dans un fichier dans le répertoire "ResultatsColisions".
     * 
     * @author thomas
     * 
     * @param fileName le nom du fichier à créer
     * @param listeColisions une liste de chaînes de données de collisions à écrire
     * @param listeVols une liste d'objets Vols contenant les données de vol
     * @param nbVols le nombre de vols
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    public void ecrireDansFichierAéroport(String fileName, ArrayList<String> listeColisions, ArrayList<Vols> listeVols, int nbVols) throws IOException {
        FileWriter monFichier = new FileWriter("./ResultatsColisions/" + fileName + ".txt");
        
        monFichier.write(Integer.toString(nbVols));
        
        for(String value : listeColisions){
            String[] separation = value.split(" ");
            System.out.println("Dans le write in file aeroports");
            System.out.println(separation[0]);
            System.out.println(separation[1]);
            
            String nomAeroport1Départ = listeVols.get(listeVols.indexOf(separation[0])).getAéroportDépart();
            String nomAeroport1Arrivée = listeVols.get(listeVols.indexOf(separation[0])).getAéroportArrivée();
            String nomAeroport2Départ = listeVols.get(listeVols.indexOf(separation[1])).getAéroportDépart();
            String nomAeroport2Arrivée = listeVols.get(listeVols.indexOf(separation[1])).getAéroportArrivée();
            
            monFichier.write("\n" + nomAeroport1Départ + " " + nomAeroport1Arrivée);
            monFichier.write("\n" + nomAeroport2Départ + " " + nomAeroport2Arrivée);
        }
        
        monFichier.close();
        System.out.println("Écriture réussie dans le fichier " + fileName + ".txt.");
    }
    
    /**
     * Obtient la valeur maximale de k.
     * 
     * @author thomas
     * 
     * @return la valeur maximale de k
     */
    public int getkMax() {
        return kMaximum;
    }
    
    /**
     * Définit la valeur de kmax
     * 
     * @author thomas
     * 
     * @param kMax la valeur de kmax à définir
     */
    public void setkMax(int kMax) {
        this.kMaximum = kMax;
    }
    
    /**
     * Obtient la liste des derniers fichiers mis à jour.
     * 
     * @author thomas
     * 
     * @return une liste des chemins des derniers fichiers mis à jour
     */
    public List<String> getlistDernierFichierMAJ() {
        return listDernierFichierMAJ;
    }
    
    /**
     * Définit la liste des derniers fichiers mis à jour.
     * 
     * @author thomas
     * 
     * @param listLastFileUpdated la liste des chemins des derniers fichiers mis à jour à définir
     */
    public void setlistDernierFichierMAJ(ArrayList<String> listLastFileUpdated) {
        this.listDernierFichierMAJ = listLastFileUpdated;
    }

    /**
     * Obtient la liste des derniers fichiers de coloration mis à jour.
     * 
     * @author thomas
     * 
     * @return une liste des chemins des derniers fichiers de coloration mis à jour
     */
    public ArrayList<String> getlistDernierColoFileMAJ() {
        return listDernierColoFileMAJ;
    }

    /**
     * Obtient la liste des derniers objets de coloration de graphes mis à jour.
     * 
     * @author thomas
     * 
     * @return une liste des derniers objets de coloration de graphes mis à jour
     */
    public ArrayList<Graph> getListDernierGraphColoMAJ() {
        return listDernierGraphColoMAJ;
    }
    
    
}
