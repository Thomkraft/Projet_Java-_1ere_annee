package coloration;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author tom
 * 
 */
public class AppCouleurs {
    private final Graph graphe;
    private final int nbCouleurs;
    private Color[] couleurs;

    public AppCouleurs(Graph graphe) {
        this.graphe = graphe;
        nbCouleurs = nombreCouleurs();

        initialiserCouleurs();
        colorationVisuel();
        testDepassementKmax();
    }

    public void initialiserCouleurs() {
        // Initialisation du nombre de couleurs
        couleurs = new Color[nbCouleurs];

        for (int i = 0; i < nbCouleurs; i++) {
            couleurs[i] = Color.getHSBColor(i * 0.1f, 1.0f, 1.0f);
        }
    }

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

    private void testDepassementKmax() {
        // Affichage dans la console si dépassement du kmax donné dans le fichier de test
        int kmaxGraphe = graphe.getAttribute("kmax");
        if (nbCouleurs > kmaxGraphe) {
            graphe.addAttribute("nouvKmax", nbCouleurs);
            System.out.println("Remarque : Dépassement du kmax donné | kmax initial : " + kmaxGraphe + "; nouveau kmax : " + nbCouleurs + "\n");
        }
    }
}

