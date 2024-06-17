package Interface;

import org.graphstream.graph.Graph;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class InfosConsole {
    JTextPane txtConsole = Fenetre.getTxtConsole();

    // Constructeur
    public InfosConsole(Graph g) {
        afficherInfosGraphes(g);
    }

    private void afficherInfosGraphes(Graph g) {
        // Effacement des informations déjà présentes dans la console
        txtConsole.setText("");

        // Initilaisation d'un style pour les textes d'erreur et de remarque
        StyledDocument docConsole = txtConsole.getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet attributsConsole = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);


        try {
            // Ajout de remarques s'il y en a
            String nbConflits;
            if ((int) g.getAttribute("nbConflits") > 0) {
                nbConflits = "REMARQUE : Présence de " + g.getAttribute("nbConflits") + " conflits !";
                docConsole.insertString(0, nbConflits + "\n", attributsConsole);
            }

            // Ajout du temps d'execution de la coloration du graphe
            long debutChrono = g.getAttribute("debutChrono");
            long finChrono = g.getAttribute("finChrono");
            long tempsColo = finChrono - debutChrono;

            String tempsExecColo = "Temps d'execution de la coloration du graphe : " + tempsColo + " ms";
            docConsole.insertString(0, tempsExecColo + "\n", null);

            // Ajout d'infos globales sur le graphe
            String infosGlobales = "Nombre de sommets : " + g.getAttribute("nbSommets") + " | " +
                    "Nombre d'arrêtes : " + g.getAttribute("nbArretes") + " | " +
                    "Kmax initial : " + g.getAttribute("kmax");
            docConsole.insertString(0, infosGlobales + "\n", null);

            // Ajout du nom de l'algo de coloration utilisé à la console
            //String algoUtilise = g.getAttribute("algoColo");

            // Ajout des erreurs s'il y en a
            if (g.getAttribute("erreurs") != null) {
                docConsole.insertString(0, g.getAttribute("erreurs") + "\n", attributsConsole);
            }

            // Ajout du fichier de test du graphe à la console
            String nomFichier = "Fichier testé : " + g.getAttribute("nomFichier");
            docConsole.insertString(0, nomFichier + "\n", null);

        } catch (ClassCastException | BadLocationException e) {
            System.err.println(e.getMessage());
        }
    }
}
