package coloration;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;

/**
 *
 * @author alec
 * 
 */
public class TriSommets {
    private final Graph graph;
    private  final ArrayList<Node> listeSommets;

    public TriSommets(Graph graph, ArrayList<Node> listeSommets) {
        this.graph = graph;
        this.listeSommets = listeSommets;

        triInsertion();
    }

    // Méthode de tri  par insertion
    private ArrayList<Node> triInsertion() {
        // Ajout des sommets dans la liste listeSommets
        for (Node n : graph) {
            listeSommets.add(n);
        }

        // Tri
        int tailleListe = listeSommets.size();

        for (int i = 1; i < tailleListe; i++) {
            Node clé = listeSommets.get(i);
            int j = i - 1;

            while ((j > -1) && (listeSommets.get(j).getDegree() < clé.getDegree())) {
                listeSommets.set(j + 1, listeSommets.get(j));
                j -= 1;
            }
            listeSommets.set(j + 1, clé);
        }
        return listeSommets;
    }
}
