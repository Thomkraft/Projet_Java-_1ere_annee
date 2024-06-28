package coloration;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.awt.Color;
import java.util.ArrayList;

/**
 * La classe AppCouleurs gère la coloration des sommets d'un graphe et vérifie
 * si le nombre de couleurs utilisées dépasse un maximum donné (kmax).
 * Les sommets du graphe sont colorés et visualisés à l'aide de la bibliothèque GraphStream.
 *
 * @author alec
 */
public class AppCouleurs {
    private final Graph graphe;
    private final int nbCouleurs;
    private Color[] couleurs;

    /**
     * Constructeur de la classe AppCouleurs.
     * Initialise le graphe, calcule le nombre de couleurs nécessaires,
     * initialise les couleurs, colorie les sommets et vérifie le dépassement du kmax.
     *
     * @param graphe Le graphe dont les sommets doivent être colorés.
     * @author alec
     */
    public AppCouleurs(Graph graphe) {
        this.graphe = graphe;
        nbCouleurs = nombreCouleurs();

        initialiserCouleurs();
        colorationVisuel();
        testDepassementKmax();
    }

    /**
     * Initialise le tableau des couleurs en utilisant des nuances de couleurs différentes.
     *
     * @author alec
     */
    public void initialiserCouleurs() {
        // Initialisation du nombre de couleurs
        couleurs = new Color[nbCouleurs];

        for (int i = 0; i < nbCouleurs; i++) {
            couleurs[i] = Color.getHSBColor(i * 0.1f, 1.0f, 1.0f);
        }
    }

    /**
     * Colorie visuellement les sommets du graphe en fonction de l'attribut "couleur" de chaque sommet.
     * Ajoute également un label pour numéroter les sommets.
     * Gère les exceptions lorsque des sommets n'ont pas de couleur attribuée.
     *
     * @author alec
     */
    public void colorationVisuel() {
        try {
            // Coloration des sommets
            int couleurAttribuee;

            for (Node n : graphe) {
                // Attribution des couleurs
                couleurAttribuee = n.getAttribute("couleur");

                // Ajout d'un label pour numéroter les sommets
                n.setAttribute("ui.label", n.getId());

                // Exception si attribut "couleur" du sommet est -1 (soit aucune couleur)
                if (couleurAttribuee < 0) {
                    throw new ArrayIndexOutOfBoundsException("Aucune couleur n'est attribuée au sommet " + n.getId());
                }

                // Ajout de la couleur visuel au sommet avec du CSS
                n.setAttribute("ui.style", "fill-color: rgba(" +
                        couleurs[couleurAttribuee].getRed() + "," +
                        couleurs[couleurAttribuee].getGreen() + "," +
                        couleurs[couleurAttribuee].getBlue() + ",255);" +
                        "size: 15px;");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Erreur : " + e.getMessage() + "\n");
        }
    }

    /**
     * Calcule le nombre de couleurs différentes utilisées dans le graphe en inspectant l'attribut "couleur" de chaque sommet.
     *
     * @return Le nombre de couleurs différentes utilisées dans le graphe.
     * @author alec
     */
    private int nombreCouleurs() {
        ArrayList<Integer> couleursSommets = new ArrayList<>();
        int nbCouleurs = 0;

        for (Node n : graphe) {
            int couleurSommet = n.getAttribute("couleur");
            if (!(couleursSommets.contains(couleurSommet))) {
                couleursSommets.add(couleurSommet);
                nbCouleurs += 1;
            }
        }

        return nbCouleurs;
    }

    /**
     * Vérifie si le nombre de couleurs utilisées dépasse le kmax donné dans le graphe.
     * Si c'est le cas, met à jour le kmax du graphe et affiche un message dans la console.
     *
     * @author alec
     */
    private void testDepassementKmax() {
        // Affichage dans la console si dépassement du kmax donné dans le fichier de test
        int kmaxGraphe = graphe.getAttribute("kmax");
        if (nbCouleurs > kmaxGraphe) {
            graphe.addAttribute("nouvKmax", nbCouleurs);
            System.out.println("Remarque : Dépassement du kmax donné | kmax initial : " + kmaxGraphe + "; nouveau kmax : " + nbCouleurs + "\n");
        }
    }
}

