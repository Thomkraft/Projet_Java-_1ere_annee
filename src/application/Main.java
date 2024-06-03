package Application;

import Interface.Fenetre;
import org.graphstream.graph.Graph;
import javax.swing.SwingUtilities;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> nomsFichiers = List.of(
                "C:\\Users\\totom\\OneDrive\\Cours\\S2\\projet_crash\\Data Test\\colision-vol2.txt",
                "C:\\Users\\totom\\OneDrive\\Cours\\S2\\projet_crash\\Data Test\\graph-test0.txt"
        );

        List<Graph> graphes = ChargerGraph.charger_graphes(nomsFichiers);

        SwingUtilities.invokeLater(() -> {
            Fenetre fenetre = new Fenetre();
            fenetre.setVisible(true);
            fenetre.afficherGraphes(graphes, 1);
        });
    }
}
