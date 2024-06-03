package Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.Viewer;

public class FenetreGraph extends JInternalFrame {
    private final Viewer viewer;
    private final DefaultView view;
    private final JButton btAgrandir = new JButton("Agrandir");
    private final JButton btRetrecir = new JButton("Retrecir");
    private final JButton btnPrevious = new JButton("Précédent");
    private final JButton btnNext = new JButton("Suivant");
    private final JButton btZoomIn = new JButton("Zoom +");
    private final JButton btZoomOut = new JButton("Zoom -");
    private final JTextField txtGraphNumber = new JTextField(3);
    private JFrame fullScreenFrame;
    private final JPanel controlPanel;
    private final JPanel fullScreenControlPanel;

    public FenetreGraph(Graph graph, Fenetre parentWindow) {
        super("Graphes", false, false, false, false);
        setSize(1170, 400);

        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        view = (DefaultView) viewer.addDefaultView(false);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(view, BorderLayout.CENTER);

        btAgrandir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maximizeWindow();
            }
        });

        btRetrecir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restoreWindow();
            }
        });

        btnPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int graphNumber = parentWindow.currentGraphIndex;
                if (graphNumber > 0) {
                    graphNumber--; // Decrement to move to the previous graph
                    txtGraphNumber.setText(Integer.toString(graphNumber + 1));
                    parentWindow.afficherGraphiqueCourant(graphNumber);
                }
            }
        });

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int graphNumber = parentWindow.currentGraphIndex;
                if (graphNumber < parentWindow.graphes.size() - 1) {
                    graphNumber++; // Increment to move to the next graph
                    txtGraphNumber.setText(Integer.toString(graphNumber + 1));
                    parentWindow.afficherGraphiqueCourant(graphNumber);
                }
            }
        });

        txtGraphNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int graphNumber = getGraphNumber();
                if (graphNumber >= 0 && graphNumber < parentWindow.graphes.size()) {
                    txtGraphNumber.setText(Integer.toString(graphNumber + 1));
                    parentWindow.afficherGraphiqueCourant(graphNumber);
                } else {
                    // Si le numéro de graphique saisi n'est pas valide, réinitialiser le champ de texte
                    txtGraphNumber.setText(Integer.toString(parentWindow.currentGraphIndex + 1));
                }
            }
        });

        // Boutons de zoom
        btZoomIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomIn();
            }
        });

        btZoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomOut();
            }
        });

        // Panneau de contrôle pour le mode fenêtré
        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(btnPrevious);
        leftPanel.add(txtGraphNumber);
        leftPanel.add(btnNext);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(btZoomIn);
        centerPanel.add(btZoomOut);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(btAgrandir);

        controlPanel.add(leftPanel, BorderLayout.WEST);
        controlPanel.add(centerPanel, BorderLayout.CENTER);
        controlPanel.add(rightPanel, BorderLayout.EAST);

        // Panneau de contrôle pour le mode plein écran
        fullScreenControlPanel = new JPanel();
        fullScreenControlPanel.setLayout(new BorderLayout());
        fullScreenControlPanel.add(btRetrecir, BorderLayout.EAST);

        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        this.setResizable(true);
    }

    private void maximizeWindow() {
        btAgrandir.setVisible(false);

        fullScreenFrame = new JFrame();
        fullScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fullScreenFrame.setUndecorated(true);
        fullScreenFrame.setLayout(new BorderLayout());
        fullScreenFrame.add(view, BorderLayout.CENTER);

        fullScreenFrame.add(fullScreenControlPanel, BorderLayout.SOUTH);
        fullScreenFrame.setVisible(true);
        this.setVisible(false);
    }

    private void restoreWindow() {
        btAgrandir.setVisible(true);

        fullScreenFrame.setVisible(false);
        this.add(view, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    private void zoomIn() {
        // Implémentation du zoom avant
        view.getCamera().setViewPercent(view.getCamera().getViewPercent() * 0.9);
    }

    private void zoomOut() {
        // Implémentation du zoom arrière
        view.getCamera().setViewPercent(view.getCamera().getViewPercent() / 0.9);
    }

    public int getGraphNumber() {
        try {
            return Integer.parseInt(txtGraphNumber.getText()) - 1; // Adjust to zero-based index
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void updateGraphNumber(int graphNumber) {
        txtGraphNumber.setText(Integer.toString(graphNumber));
    }
}
