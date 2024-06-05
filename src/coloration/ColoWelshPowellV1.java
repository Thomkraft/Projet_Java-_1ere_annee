package coloration;

import TEST.Debug;
import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;

public class ColoWelshPowellV1 {
    // Initialisation des attributs
    private final ArrayList<Node> listeSommets = new ArrayList<>();
    private final Graph graphe;

    public ColoWelshPowellV1(Graph graphe) {
        this.graphe = graphe;

        colorerGraphe();
    }

    // Méthode de coloration de graphes
    private void colorerGraphe() {
        // Tri des sommets selon la valeur décroissante de leur degré dans la liste listeSommets
        new TriSommets(graphe, listeSommets);


        // Initialisation de couleurs fictives selon l'algorithme de Welsh et Powell
        WelshPowell wp = new WelshPowell("couleur");
        wp.init(graphe);
        wp.compute();

        // Application visuel des couleurs au graphe
        new AppCouleurs(graphe);


        // #DEBUG : Affichage des degrés des noeuds triés par ordre décroissant
        Debug debug = new Debug();
        debug.afficherDegresSommets(listeSommets);
    }
}
