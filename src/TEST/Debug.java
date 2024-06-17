package TEST;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;

public class Debug {
    // Affichage des degrés des noeuds triés par ordre décroissant
    public void afficherDegresSommets(ArrayList<Node> listeSommets) {
        System.out.println("Affichage des degrés des noeuds triés par ordre décroissant:");
        for (Node n : listeSommets) {
            System.out.println(n.getDegree());
        }
    }

    public void afficherCouleursSommets(Graph graphe) {
        // Affichage des noeuds et de leur couleur
        System.out.println("FICHIER DE TEST : " + graphe.getId());
        System.out.println("Affichage des noeuds et de leur couleur:");
        for (Node n : graphe) {
            System.out.println("Sommet " + n.getId() + " : " + "degré : " + n.getDegree() + " : " + "couleur " + n.getAttribute("couleur"));
        }
        System.out.println();

    }
}
