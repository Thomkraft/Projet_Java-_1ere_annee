package coloration;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;

/**
 * Classe TriSommets effectue le tri des sommets d'un graphe par ordre décroissant de leur degré.
 * Le tri est effectué à l'aide de l'algorithme de tri par insertion.
 *
 * @author alec
 */
public class TriSommets {
    private final Graph graph;
    private  final ArrayList<Node> listeSommets;

    /**
     * Constructeur de la classe TriSommets.
     * Initialise le graphe et la liste des sommets, puis effectue le tri par insertion des sommets.
     *
     * @param graphe Le graphe contenant les sommets à trier.
     * @param listeSommets La liste initiale des sommets, qui sera remplie et triée.
     * @author alec
     */
    public TriSommets(Graph graphe, ArrayList<Node> listeSommets) {
        this.graph = graphe;
        this.listeSommets = listeSommets;

        triInsertion();
    }

    /**
     * Méthode privée qui effectue le tri des sommets par ordre décroissant de leur degré
     * en utilisant l'algorithme de tri par insertion.
     *
     * @author alec
     */
    private void triInsertion() {
        // Ajout des sommets dans la liste listeSommets
        for (Node n : graph) {
            listeSommets.add(n);
        }
        
        // Tri
        int tailleListe = listeSommets.size();

        for (int i = 1; i < tailleListe; i++) {
            Node cle = listeSommets.get(i);
            int j = i - 1;

            while ((j > -1) && (listeSommets.get(j).getDegree() < cle.getDegree())) {
                listeSommets.set(j + 1, listeSommets.get(j));
                j -= 1;
            }
            listeSommets.set(j + 1, cle);
        }
    }
}
