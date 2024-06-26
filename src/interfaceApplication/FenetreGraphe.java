package interfaceApplication;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.Viewer;

/**
 * Fenetre contenant les graphs
 * @author tom, alec
 */
public class FenetreGraphe extends JInternalFrame {
    private final Viewer viewer;
    private final DefaultView view;
    private final JButton btnMoins = new JButton("-");
    private final JButton btnPlus = new JButton("+");
    private final JButton btEcran = new JButton("Agrandir");
    private final JButton btnPrevious = new JButton("<");
    private final JButton btnNext = new JButton(">");
    private final JTextField txtGraphNumber = new JTextField(3);
    private boolean isFullScreen = false;
    private JFrame fullScreenFrame;
    private final Fenetre fenetre;
    private String fileName;

    /**
     * Constructeur pour FenetreGraphe.
     * @param graph le graphique à afficher
     * @param fenetre la fenêtre parente
     * @param fileName le nom du fichier
     * @author tom
     */
    public FenetreGraphe(Graph graph, Fenetre fenetre, String fileName) {
        super(fileName, false, false, false, false);
        this.fenetre = fenetre;
        this.fileName = fileName; 
        setSize(1170, 400);

        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        view = (DefaultView) viewer.addDefaultView(false);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(view, BorderLayout.CENTER);

        txtGraphNumber.setHorizontalAlignment(JTextField.CENTER);
        afficherBoutons(getContentPane());

        this.setResizable(true);
    }

    /**
     * Maximiser la fenêtre pour qu'elle prenne tout l'écran.
     * @author tom
     */
    private void maximizeWindow() {
        if (!isFullScreen) {
            isFullScreen = true;
            btEcran.setText("Retrecir");

            setVisible(false);

            fullScreenFrame = new JFrame();
            fullScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            fullScreenFrame.setUndecorated(true);
            fullScreenFrame.setLayout(new BorderLayout());
            fullScreenFrame.add(view, BorderLayout.CENTER);

            afficherBoutons(fullScreenFrame.getContentPane());

            fullScreenFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    restoreWindow();
                }
            });

            fullScreenFrame.setVisible(true);
        }
    }

    /**
     * Restaurer la fenêtre à sa taille normale après avoir été en plein écran.
     * @author tom
     */
    public void restoreWindow() {
        if (isFullScreen) {
            isFullScreen = false;
            btEcran.setText("Agrandir");

            fullScreenFrame.dispose();

            setVisible(true);

            fenetre.afficherGraphiqueCourant();
        }
    }

    /**
     * Ajouter un écouteur d'action au bouton précédent.
     * @param listener l'écouteur d'action à ajouter
     * @author tom
     */
    public void addPrecButtonListener(ActionListener listener) {
        btnPrevious.addActionListener(listener);
    }

    /**
     * Ajouter un écouteur d'action au champ de texte de l'indice de graphique.
     * @param listener l'écouteur d'action à ajouter
     * @author tom
     */
    public void addIndiceTxtListener(ActionListener listener) {
        txtGraphNumber.addActionListener(listener);
    }

    /**
     * Ajouter un écouteur d'action au bouton suivant.
     * @param listener l'écouteur d'action à ajouter
     * @author tom
     */
    public void addNextButtonListener(ActionListener listener) {
        btnNext.addActionListener(listener);
    }

    /**
     * Définir le numéro du graphique affiché.
     * @param number le numéro du graphique
     * @author tom
     */
    public void setGraphNumber(int number) {
        txtGraphNumber.setText(Integer.toString(number));
    }

    /**
     * Obtenir le numéro du graphique affiché.
     * @return le numéro du graphique
     * @author tom
     * @throws NumberFormatException si le texte n'est pas un nombre valide
     */
    public int getGraphNumber() {
        try {
            return Integer.parseInt(txtGraphNumber.getText());
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    /**
     * Vérifier si la fenêtre est en plein écran.
     * @return true si la fenêtre est en plein écran, sinon false
     * @author tom
     */
    public boolean isFullScreen() {
        return isFullScreen;
    }

    /**
     * Définir l'indice du graphique affiché.
     * @param index l'indice du graphique
     * @author tom
     */
    public void setGraphIndex(int index) {
        txtGraphNumber.setText(Integer.toString(index));
    }

    /**
     * Afficher les boutons de contrôle sur le conteneur donné.
     * @param container le conteneur sur lequel afficher les boutons
     * @author tom
     */
    private void afficherBoutons(Container container) {
        btnMoins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getCamera().setViewPercent(view.getCamera().getViewPercent() * 1.1);
            }
        });

        btnPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getCamera().setViewPercent(view.getCamera().getViewPercent() * 0.9);
            }
        });

        btEcran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isFullScreen) {
                    restoreWindow();
                } else {
                    maximizeWindow();
                }
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.add(btnPrevious);
        leftPanel.add(txtGraphNumber);
        leftPanel.add(btnNext);

        JPanel CenterPanel = new JPanel();
        CenterPanel.add(btnMoins);
        CenterPanel.add(btnPlus);

        JPanel rightPanel = new JPanel();
        rightPanel.add(btEcran);

        controlPanel.add(leftPanel, BorderLayout.WEST);
        controlPanel.add(CenterPanel, BorderLayout.CENTER);
        controlPanel.add(rightPanel, BorderLayout.EAST);

        container.add(controlPanel, BorderLayout.SOUTH);
    }
}
