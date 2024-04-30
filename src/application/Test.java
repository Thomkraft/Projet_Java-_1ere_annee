package Application;

import java.util.ArrayList;
import java.util.List;
import org.graphstream.graph.Graph;

public class Test {
    public static void main(String[] args) {
        List<String> noms_fichiers = new ArrayList<>();
        noms_fichiers.add("C:\\Users\\totom\\OneDrive\\Cours\\S2\\projet_crash\\Data Test\\graph-test0.txt");
        noms_fichiers.add("C:\\Users\\totom\\OneDrive\\Cours\\S2\\projet_crash\\Data Test\\graph-test1.txt");
        // Ajoutez d'autres noms de fichiers au besoin

        List<Graph> graphes = GraphLoader.charger_graphes(noms_fichiers); // Utilisation de charger_graphe depuis Application
        for (Graph graph : graphes) {
            graph.display(); // Affiche chaque graphe
        }
    }
}

