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
            Graph graph = new SingleGraph(nom_fichier); 

            try (BufferedReader br = new BufferedReader(new FileReader(nom_fichier))) {
                int kmax = Integer.parseInt(br.readLine());
                graph.addAttribute("kmax", kmax); 

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
