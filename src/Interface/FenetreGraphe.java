package Interface;

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
 *
 * @author tom,alec
 * 
 */
public class FenetreGraphe extends JInternalFrame {
    private Viewer viewer;
    private DefaultView view;
    private JButton btnMoins = new JButton("-");
    private JButton btnPlus = new JButton("+");
    private JButton btEcran = new JButton("Agrandir");
    private JButton btnPrevious = new JButton("<");
    private JButton btnNext = new JButton(">");
    private JTextField txtGraphNumber = new JTextField(3);
    private boolean isFullScreen = false;
    private JFrame fullScreenFrame;
    private Fenetre fenetre;
    private String fileName;

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

    private void maximizeWindow() {
        if (!isFullScreen) {
            isFullScreen = true;
            btEcran.setText("Retrecir");

            // Cacher la fenêtre actuelle
            setVisible(false);

            // Créer une nouvelle fenêtre en plein écran
            fullScreenFrame = new JFrame();
            fullScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            fullScreenFrame.setUndecorated(true);
            fullScreenFrame.setLayout(new BorderLayout());
            fullScreenFrame.add(view, BorderLayout.CENTER);

            // Ajouter les boutons au panneau de contrôle du mode plein écran
            afficherBoutons(fullScreenFrame.getContentPane());

            // Ajouter un écouteur pour restaurer la fenêtre normale lorsque la fenêtre en mode plein écran est fermée
            fullScreenFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    restoreWindow();
                }
            });

            fullScreenFrame.setVisible(true);
        }
    }

    public void restoreWindow() {
        if (isFullScreen) {
            isFullScreen = false;
            btEcran.setText("Agrandir");

            // Fermer la fenêtre en mode plein écran
            fullScreenFrame.dispose();

            // Afficher la fenêtre FenetreGraph
            setVisible(true);

            fenetre.afficherGraphiqueCourant();
        }
    }

    public void addPrecButtonListener(ActionListener listener) {
        btnPrevious.addActionListener(listener);
    }

    public void addIndiceTxtListener(ActionListener listener) {
        txtGraphNumber.addActionListener(listener);
    }

    public void addNextButtonListener(ActionListener listener) {
        btnNext.addActionListener(listener);
    }

    public void setGraphNumber(int number) {
        txtGraphNumber.setText(Integer.toString(number));
    }

    public int getGraphNumber() {
        try {
            return Integer.parseInt(txtGraphNumber.getText());
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setGraphIndex(int index) {
        txtGraphNumber.setText(Integer.toString(index));
    }

    private void afficherBoutons(Container container) {
        // Ajout des ActionListeners pour les boutons Zoom moins et Zoom plus
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
