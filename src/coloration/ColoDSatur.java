package coloration;

import TEST.Debug;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Iterator;

public class ColoDSatur {
    // Attributs
    private final ArrayList<Node> listeSommets = new ArrayList<>();
    private final Graph graphe;

    // Constructeur
    public ColoDSatur(Graph graphe) {
        this.graphe = graphe;

        colorerGraphe();
    }

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

        // Initialisation de couleurs fictives selon l'algorithme de DSatur
        while (!(estGrapheColorie())) {
            // Attribution du DSAT de chaque sommet selon les deux règles suivantes :
            // Si aucun voisin du sommet x est colorié, DSAT(x) = degré de x
            // Sinon, DSAT(x) = nombre de couleurs différentes au voisinage de x
            attributionDSATSommets();

            // Choix du sommet non coloré avec un DSAT maximum
            Node choixSommet = sommetDSATMax();

            // Initialisation d'un itérateur pour tous les voisins du premier sommet
            Iterator<Node> itVoisinsSommet = choixSommet.getNeighborNodeIterator();

            // Initialisation d'un tableau des couleurs utilisées par les voisins du sommet choisi
            ArrayList<Integer> couleursUtilisees = new ArrayList<>();

            while (itVoisinsSommet.hasNext()) {
                Node voisinTeste = itVoisinsSommet.next();
                couleursUtilisees.add(voisinTeste.getAttribute("couleur"));
            }

            // Assignation de la plus petite couleur possible au sommet choisi
            int couleurSommet;
            int couleurTeste = 0;

            do {
                couleurSommet = choixSommet.getAttribute("couleur");

                if (!(couleursUtilisees.contains(couleurTeste))) {
                    choixSommet.setAttribute("couleur", couleurTeste);
                }
                couleurTeste += 1;
            } while (couleurSommet == -1);


            // Ajout du sommet colorié à la liste des sommets coloriés
            ajoutSommetColorie(choixSommet);
        }

        // Fin du chronometre pour la coloration du graphe
        long finChrono = System.currentTimeMillis();
        graphe.addAttribute("finChrono", finChrono);


        // #DEBUG : Affichage des noeuds et de leur couleur
        Debug debug = new Debug();
        debug.afficherCouleursSommets(graphe);


        // Application visuel des couleurs au graphe
        new AppCouleurs(graphe);
    }


    private boolean estGrapheColorie() {
        return listeSommets.isEmpty();
    }


    private void attributionDSATSommets() {
        ArrayList<Integer> couleursUtilisees = new ArrayList<>();
        Iterator<Node> itVoisinsSommet;
        Node sommetTeste;
        int dsat = 0, couleurSommetTeste;

        for (Node n : listeSommets) {
            itVoisinsSommet = n.getNeighborNodeIterator();
            while (itVoisinsSommet.hasNext()) {
                sommetTeste = itVoisinsSommet.next();
                couleurSommetTeste = sommetTeste.getAttribute("couleur");
                if (couleurSommetTeste != -1 && !(couleursUtilisees.contains(couleurSommetTeste))) {
                    dsat += 1;
                    couleursUtilisees.add(couleurSommetTeste);
                }
            }

            // Ajout du dsat correspondant au sommet
            if (dsat == 0) {
                n.addAttribute("dsat", n.getDegree());

            } else {
                n.addAttribute("dsat", dsat);
            }
        }
    }


    private Node sommetDSATMax() {
        Node sommetDsatMax = listeSommets.getFirst();

        for (Node n : listeSommets) {
            int dsatSommetTeste = n.getAttribute("dsat");
            int couleurSommetTeste = n.getAttribute("couleur");
            if (dsatSommetTeste >= (int) sommetDsatMax.getAttribute("dsat") && couleurSommetTeste != -1) {
                sommetDsatMax = n;
            }
        }
        return sommetDsatMax;
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
    }
}
