package coloration;

import TEST.Debug;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Iterator;

public class ColoWelshPowellV2 {
    // Initialisation des attributs
    private final ArrayList<Node> listeSommets = new ArrayList<>();
    private final ArrayList<Node> sommetsColories = new ArrayList<>();
    private int nbCouleursUtilisees = 0;
    private final Graph graphe;

    public ColoWelshPowellV2(Graph graphe) {
        this.graphe = graphe;

        colorerGraphe();
    }

    // Méthode de coloration de graphes
    private void colorerGraphe() {
        // Tri des sommets selon la valeur décroissante de leur degré dans la liste listeSommets
        new TriSommets(graphe, listeSommets);

        // Tous les sommets sont initialisés avec la même couleur fictive -1
        for (Node n : graphe) {
            n.addAttribute("couleur", -1);
        }

        // Coloration fictive du premier sommet de degré maximum avec la première couleur
        listeSommets.getFirst().setAttribute("couleur", 0);
        ajoutSommetColorie(listeSommets.getFirst());

        // Initialisation de couleurs fictives selon l'algorithme de Welsh et Powell
        int compteur = 0;
        while (!(estGrapheColorie()) && compteur < 11) {
            // Choix du sommet non coloré et non adjacent
            Node choixSommet = choixSommet();

            if (choixSommet != null) {
                choixSommet.setAttribute("couleur", nbCouleursUtilisees);

            } else {
                nbCouleursUtilisees += 1;
            }
            compteur += 1;
        }


        // #DEBUG : Affichage des degrés des noeuds triés par ordre décroissant
        Debug debug = new Debug();
        debug.afficherCouleursSommets(graphe);

        // Application visuel des couleurs au graphe
        new AppCouleurs(graphe);
    }

    private boolean estGrapheColorie() {
        return listeSommets.isEmpty();
    }

    private Node choixSommet() {
        Node choixSommet = null;
        Iterator<Node> itListeSommets = listeSommets.iterator();

        while (itListeSommets.hasNext() && choixSommet == null) {
            Node sommetTeste = itListeSommets.next();
            int couleurSommet = sommetTeste.getAttribute("couleur");

            if (couleurSommet != -1) {
                boolean sommetChoisi = true;

                for (Node nColories : sommetsColories) {
                    Iterator<Node> itVoisinsSommetsColories = nColories.getNeighborNodeIterator();

                    while (itVoisinsSommetsColories.hasNext() && sommetChoisi) {
                        Node sommetVoisinTeste = itVoisinsSommetsColories.next();
                        if (sommetVoisinTeste.equals(sommetTeste)) {
                            sommetChoisi = false;
                        }
                    }
                }
                if (sommetChoisi) {
                    choixSommet = sommetTeste;
                }
            }
        }
        return choixSommet;
    }


    private void ajoutSommetColorie(Node sommetColorie) {
        // Suppression du sommet de la liste des sommets
        Iterator<Node> itSommets = listeSommets.iterator();
        boolean sommetSupprime = false;

        while (itSommets.hasNext() && !(sommetSupprime)) {
            Node sommetTeste = itSommets.next();
            if (sommetTeste.equals(sommetColorie)) {
                listeSommets.remove(sommetTeste);
                sommetSupprime = true;
            }
        }

        // Ajout du sommet dans la liste des sommets colorés
        sommetsColories.add(sommetColorie);
    }
}