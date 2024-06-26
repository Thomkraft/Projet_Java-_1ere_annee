package fichier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *CLass EcrireDansTxt pour écrire dans un fichier texte les infos comme 
 * les resultats de colorations ou les résultats de colisions
 * 
 * @author thoma
 */
public class EcrireDansTxt {
    private int kMaximum = -1;
    private final ArrayList<String> listDernierFichierMAJ = new ArrayList<>();
    private ArrayList<String> listDernierColoFileMAJ = new ArrayList<>();
    private ArrayList<Graph> listDernierGraphColoMAJ = new ArrayList<>();

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
           
           if (g.getNodeCount() <= 100) {
            
           // Récupération de tous les sommets dans une liste
            ArrayList<Node> nodes = new ArrayList<>();
            for (int i = 0; i < g.getNodeCount(); i++) {
                nodes.add(g.getNode(i));
            }

            
            // Tri des sommets par leur identifiant
            Collections.sort(nodes, new Comparator<Node>() {
                @Override
                public int compare(Node n1, Node n2) {
                    String s1 = n1.toString();
                    String s2 = n2.toString();

                    int i1 = 0, i2 = 0;

                    while (i1 < s1.length() && i2 < s2.length()) {
                        char c1 = s1.charAt(i1);
                        char c2 = s2.charAt(i2);

                        if (Character.isDigit(c1) && Character.isDigit(c2)) {
                            //alors comparer des int
                            int start1 = i1;
                            int start2 = i2;

                            while (i1 < s1.length() && Character.isDigit(s1.charAt(i1))) i1++;
                            while (i2 < s2.length() && Character.isDigit(s2.charAt(i2))) i2++;

                            String num1 = s1.substring(start1, i1);
                            String num2 = s2.substring(start2, i2);

                            int diff = Integer.compare(Integer.parseInt(num1), Integer.parseInt(num2));
                            if (diff != 0) {
                                return diff;
                            }
                        } else {
                            // alors comparer des Strinf type AF quelque chose
                            if (c1 != c2) {
                                return c1 - c2;
                            }
                            i1++;
                            i2++;
                        }
                    }

                    return s1.length() - s2.length();
                }


            }); 
            
            for (Node node : nodes) {
                Object couleur = node.getAttribute("couleur");
                String couleurString = couleur.toString();
                int intCouleur = Integer.parseInt(couleurString) + 1;
                lecteurFichier.write(node + " ; " + intCouleur + "\n");
            }
            
           } else {
            
                for (int i = 0; i < g.getNodeCount(); i++){
                    Object couleur = g.getNode(i).getAttribute("couleur");
                    String couleurString = couleur.toString();
                    int intCouleur = Integer.parseInt(couleurString) + 1;
                    lecteurFichier.write(g.getNode(i) + " ; " + intCouleur +"\n");
                }
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
        monFichier.write("\n" + nbVols);
        for(String value : listeColisions){
            monFichier.write("\n" + value);
        }
        monFichier.close();
        listDernierFichierMAJ.add(cheminFichier);
        
        System.out.println("Écriture réussie dans le fichier " + nomFichier + ".txt.");
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
