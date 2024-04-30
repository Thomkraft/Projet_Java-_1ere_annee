package Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class GraphLoader {
    public static List<Graph> charger_graphes(List<String> noms_fichiers) {
        List<Graph> graphes = new ArrayList<>();

        for (String nom_fichier : noms_fichiers) {
            Graph graph = new SingleGraph(nom_fichier); // Utilise le nom du fichier comme identifiant pour le graphe

            try (BufferedReader br = new BufferedReader(new FileReader(nom_fichier))) {
                // Lire la première ligne pour obtenir kmax
                int kmax = Integer.parseInt(br.readLine());
                graph.addAttribute("kmax", kmax); // Stocke kmax comme attribut du graphe

                // Lire la deuxième ligne pour obtenir le nombre de sommets
                int numberOfVertices = Integer.parseInt(br.readLine());

                // Ajouter des nœuds au graph
                for (int i = 1; i <= numberOfVertices; i++) {
                    graph.addNode(Integer.toString(i));
                }

                // Lire les arêtes
                String line;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(" ");
                    if (tokens.length >= 2) {
                        String node1 = tokens[0];
                        String node2 = tokens[1];
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
