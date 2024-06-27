package application;

import interfaceApplication.Fenetre;


/**
 * La classe principale `Main` est responsable de démarrer l'application de visualisation de cartes et de graphes.
 * Elle crée une instance de la classe `Fenetre` et rend la fenêtre principale visible.
 * 
 * @author tom
 * @version 1.0
 */
public class Main {
    /**
     * Méthode principale `main` qui démarre l'application.
     * @author tom
     * @param args Les arguments de la ligne de commande (non utilisés dans cette application)
     */
    public static void main(String[] args) {
        
            Fenetre fenetre = new Fenetre();
            fenetre.setVisible(true);
    }
}
