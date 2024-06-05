package Application;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChargerGraph {
    public static List<Graph> charger_graphes(List<String> noms_fichiers) {
        List<Graph> graphes = new ArrayList<>();

        for (String nom_fichier : noms_fichiers) {
            Graph graph = new SingleGraph(nom_fichier); // Utilise le nom du fichier comme identifiant pour le graphe

            try (BufferedReader br = new BufferedReader(new FileReader(nom_fichier))) {
                // Lire la première ligne pour obtenir kmax
                int kmax = Integer.parseInt(br.readLine());
                // Stocke kmax comme attribut du graphe
                graph.addAttribute("kmax", kmax); 

                // Lire les arêtes
                String line;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(" ");
                    if (tokens.length >= 2) {
                        
                        if(graph.getNode(tokens[0]) == null) {
                            graph.addNode(tokens[0]);
                        }
                        
                        if(graph.getNode(tokens[1]) == null) {
                            graph.addNode(tokens[1]);
                        }
                        
                        System.out.println(tokens[0]);
                        String node1 = tokens[0];
                        System.out.println(tokens[1]);
                        String node2 = tokens[1];
                        //condition si sommet existe si elle existe pas :
                        //crée le sommet
                        graph.addEdge(node1 + "_" + node2, node1, node2);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            graphes.add(graph);
        }

        return graphes;
    }
}
