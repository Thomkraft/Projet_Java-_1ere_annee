package application;

import Interface.Fenetre;
import org.graphstream.graph.Graph;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> nomsFichiers = new ArrayList<>();

        // Ajout de tous les fichiers de test dans la liste des fichiers
        for (int i = 0; i <= 19; i++) {
            nomsFichiers.add("C:\\Users\\alecp\\OneDrive - etu.univ-lyon1.fr\\IUT Info\\Java\\SAE.201\\data_test\\graph-test" + i + ".txt");
        }


        // Création et insertion des graphes dans une liste
        List<Graph> graphes = ChargerGraph.charger_graphes(nomsFichiers);


        SwingUtilities.invokeLater(() -> {
            Fenetre fenetre = new Fenetre();
            fenetre.setVisible(true);

            fenetre.afficherGraphes(graphes);
        });
    }
}
