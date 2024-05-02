package application;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.Iterator;

public class ColorationGrapheV3 {
    public static void coloration(Graph g) {
        // Ajout d'un attribut "couleur" pour chaque sommet, initialisé à 0
        for (Node n : g) {
            n.addAttribute("couleur", 0);
        }

        // On stocke les couleurs déjà utilisées dans un tableau
        int kMax = (int) g.getAttribute("kmax");    // Nombre de noeuds dans le graphe

        System.out.println("kmax ->" + kMax);

        int[] couleursUtilisees = new int[kMax];

        int val = 1;

        for (Node n : g) {
            n.setAttribute("ui.label", val);
            n.setAttribute("ui.style", "text-size: 20px;");
            val += 1;

            // On initialise le tableau des couleurs utilisées à 0
            for (int couleur : couleursUtilisees) {
                couleursUtilisees[couleur] = 0;
            }

            // Création d'un itérateur pour itérer sur les voisins du noeud testé
            Iterator<Node> it = n.getNeighborNodeIterator();

            // On teste si les voisins du noeud testé ont une couleur attribué
            while (it.hasNext()) {
                Node voisin = it.next();

                if ((int) voisin.getAttribute("couleur") > 0) {
                    // On marque 1 à l'index correspondant à la bonne couleur dans le tableau des couleurs utilsées
                    couleursUtilisees[(int) voisin.getAttribute("couleur") - 1] = 1;
                }
            }

            // On trouve la première couleur non utilisée dans le tableau des couleurs utilisées
            for (int i = 0; i < kMax; i++) {
                if (couleursUtilisees[i] == 0) {
                    n.setAttribute("couleur", i + 1);
                    break;
                }
            }

            colorierGraphe(g, "couleur");
        }
    }

    public static void colorierGraphe(Graph g, String attribut) {
        int max = g.getNodeCount();
        Color[] cols = new Color[max + 1];
        for (int i = 0; i <= max; i++) {
            cols[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
        }

        for (Node n : g) {
            int col = n.getAttribute(attribut);
            if (n.hasAttribute("ui.style")) {
                n.setAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
            } else {
                n.addAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
            }
        }
    }
}
