package application;

import coloration.ColoDSatur;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe ChargerGraph permet de charger plusieurs graphes à partir de fichiers spécifiés,
 * d'ajouter des attributs aux graphes tels que le nombre maximal de sommets, le nombre de sommets,
 * le nombre d'arêtes, et de les colorier en utilisant l'algorithme DSatur.
 * Elle gère également les erreurs liées à la lecture des fichiers ou à l'ajout des arêtes.
 * 
 * @author tom
 * @version 1.0
 */
public class ChargerGraph {
    /**
     * Charge une liste de graphes à partir de fichiers spécifiés.
     * Pour chaque fichier, un graphe est créé et des attributs tels que le nombre maximal de sommets,
     * le nombre de sommets, le nombre d'arêtes sont ajoutés au graphe. Ensuite, les graphes sont coloriés
     * en utilisant l'algorithme DSatur.
     * @author tom/Alec
     * @param noms_fichiers La liste des chemins vers les fichiers contenant les données des graphes
     * @return Une liste de graphes (instances de Graph) chargés et coloriés
     */
    public static List<Graph> charger_graphes(List<String> noms_fichiers) {
        List<Graph> graphes = new ArrayList<>();

        for (String nom_fichier : noms_fichiers) {
            Graph graph = new MultiGraph(nom_fichier);

            try (BufferedReader br = new BufferedReader(new FileReader(nom_fichier))) {
                
                BufferedReader br2 = new BufferedReader(new FileReader(nom_fichier));
                br2.readLine();
                int nbMaxSommet = Integer.parseInt(br2.readLine());
                
                // Ajout du chemin du fichier de test du graphe au graphe en tant qu'attribut
                graph.addAttribute("nomFichier", nom_fichier);

                int kmax = Integer.parseInt(br.readLine());
                graph.addAttribute("kmax", kmax);

                // Ajout du nombre de sommets du graphe en tant qu'attribut
                int nbSommets = Integer.parseInt(br.readLine());
                graph.addAttribute("nbSommets", nbSommets);

                String line;
                int nbArretes = 0;
                int edgeCounter = 0;
                
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(" ");
                    if (tokens.length >= 2) {
                        if(graph.getNode(tokens[0]) == null) {
                            graph.addNode(tokens[0]);
                        }
                        
                        if(graph.getNode(tokens[1]) == null) {
                            graph.addNode(tokens[1]);
                        }
                        
                        String node1 = tokens[0];
                        String node2 = tokens[1];
                        graph.addEdge(node1 + "_" + node2 + "_" + edgeCounter++, node1, node2);
                        nbArretes += 1;
                    }
                }
                
                
                // Ajout du nombre d'arretes du graphe en tant qu'attribut
                graph.addAttribute("nbArretes", nbArretes);

                while (nbMaxSommet > graph.getNodeCount()){
                    graph.addNode(Integer.toString(graph.getNodeCount()));
                }
                
            } catch (IOException | EdgeRejectedException e) {
                String txtErreur = "Erreur : " + e.getMessage();
                System.err.println(txtErreur);
                graph.addAttribute("erreurs", txtErreur);
            }

            // Ajout d'un chronomètre pour mesurer le temps de coloration du graphe
            long debutChrono = System.currentTimeMillis();
            graph.addAttribute("debutChrono", debutChrono);

            // Coloration des graphes
            new ColoDSatur(graph);
            
            
            // Ajout des graphes dans une ArrayList
            graphes.add(graph);
        }

        return graphes;
    }
}
