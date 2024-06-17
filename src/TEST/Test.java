package TEST;

import java.util.ArrayList;
import java.util.List;

import coloration.ColoDSatur;
import coloration.ColoWelshPowellV2;
import org.graphstream.graph.Graph;

public class Test {
    public static void main(String[] args) {
        // Création d'une nouvelle fenêtre
        //MaFenetre fen=new MaFenetre();
        //fen.setVisible(true);

        // Importation des fichiers de test
        List<String> noms_fichiers = new ArrayList<>();

        noms_fichiers.add("C:/Users/alecp/OneDrive - etu.univ-lyon1.fr/IUT Info/Java/SAE.201/data_test/graph-test0.txt");


        // Ajoutez d'autres noms de fichiers au besoin
        noms_fichiers.add("C:/Users/alecp/OneDrive - etu.univ-lyon1.fr/IUT Info/Java/SAE.201/data_test/graph-test1.txt");
        noms_fichiers.add("C:/Users/alecp/OneDrive - etu.univ-lyon1.fr/IUT Info/Java/SAE.201/data_test/graph-test2.txt");
        noms_fichiers.add("C:/Users/alecp/OneDrive - etu.univ-lyon1.fr/IUT Info/Java/SAE.201/data_test/graph-test3.txt");
        noms_fichiers.add("C:/Users/alecp/OneDrive - etu.univ-lyon1.fr/IUT Info/Java/SAE.201/data_test/graph-test4.txt");
        noms_fichiers.add("C:/Users/alecp/OneDrive - etu.univ-lyon1.fr/IUT Info/Java/SAE.201/data_test/graph-test5.txt");


        // Génération graphique du graphe
        List<Graph> graphes = GraphLoader.chargerGraphes(noms_fichiers); // Utilisation de charger_graphe depuis Application
        for (Graph g : graphes) {
            // Coloration des sommets
            //new ColoWelshPowellV2(g);
            new ColoDSatur(g);

            // Affichage des graphes
            g.display();
        }


    }
}
