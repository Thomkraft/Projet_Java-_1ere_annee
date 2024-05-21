package Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.Viewer;

public class FenetreGraph extends JInternalFrame {
    private Viewer viewer;
    private DefaultView view;
    private JButton toggleButton;
    private JButton btnPrevious;
    private JButton btnNext;
    private JTextField txtGraphNumber; // Utilisé pour saisir le numéro du graphique
    private boolean isFullScreen = false;

    public FenetreGraph(Graph graph) {
        super("Graph Frame", false, false, false, false); // Boutons de fermeture et de réduction désactivés, redimensionnement activé
        setSize(1170, 400);

        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        view = (DefaultView) viewer.addDefaultView(false);

        // Utilisation d'un BorderLayout pour occuper tout l'espace disponible
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(view, BorderLayout.CENTER);

        // Ajouter un bouton pour maximiser et restaurer
        toggleButton = new JButton("Plein écran");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isFullScreen) {
                    restoreWindow();
                } else {
                    maximizeWindow();
                }
            }
        });

        // Ajout des boutons de navigation
        btnPrevious = new JButton("Précédent");
        btnNext = new JButton("Suivant");

        txtGraphNumber = new JTextField(3); // Réduire la taille de la colonne à 3 pour le champ de texte

        // Utilisation d'un BoxLayout pour mieux contrôler les espaces
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.add(btnPrevious);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Ajouter un espace fixe
        leftPanel.add(txtGraphNumber); // Ajouter le champ de texte
        leftPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Ajouter un espace fixe
        leftPanel.add(btnNext);

        controlPanel.add(leftPanel, BorderLayout.WEST);
        controlPanel.add(toggleButton, BorderLayout.EAST);

        getContentPane().add(controlPanel, BorderLayout.SOUTH);

        this.setResizable(true);
    }

    private void maximizeWindow() {
        toggleButton.setText("Restaurer");
        isFullScreen = true;
    }

    private void restoreWindow() {
        toggleButton.setText("Plein écran");
        isFullScreen = false;
    }

    // Ajouter des écouteurs pour les boutons de navigation
    public void addPreviousButtonListener(ActionListener listener) {
        btnPrevious.addActionListener(listener);
    }

    public void addNextButtonListener(ActionListener listener) {
        btnNext.addActionListener(listener);
    }

    // Méthode pour obtenir le numéro de graphique saisi par l'utilisateur
    public int getGraphNumber() {
        try {
            return Integer.parseInt(txtGraphNumber.getText());
        } catch (NumberFormatException e) {
            return 1; // Si aucune valeur n'est saisie, retourne 1 par défaut
        }
    }
}
