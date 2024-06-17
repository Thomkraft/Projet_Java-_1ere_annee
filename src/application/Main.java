package Application;

import Interface.Fenetre;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Fenetre fenetre = new Fenetre();
            fenetre.setVisible(true);
     
        });
    }
}
