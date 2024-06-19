package Interface;

import Stockage.Aeroports;
import Stockage.Vols;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author tom
 */
public class Visualisation extends JFrame {
    private JComboBox<String> aeroportComboBox;
    private JComboBox<Integer> niveauComboBox;
    private JPanel visualisationPanel;
    private Graph graph;

    public Visualisation(Graph graph, List<Aeroports> listeAeroports, int kMax) {
        this.graph = graph;
        setTitle("Visualisation des Niveaux de Vol");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI(listeAeroports, kMax);
    }

    private void initUI(List<Aeroports> listeAeroports, int kMax) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        visualisationPanel = new JPanel();
        visualisationPanel.setBackground(Color.WHITE);

        // Panel pour les contrôles
        JPanel controlPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel aeroportLabel = new JLabel("Sélectionner un aéroport:");
        aeroportComboBox = new JComboBox<>();
        for (Aeroports aeroport : listeAeroports) {
            aeroportComboBox.addItem(aeroport.getNom());
        }
        JButton btnVisualiserAeroport = new JButton("Visualiser");

        JLabel niveauLabel = new JLabel("Sélectionner un niveau:");
        niveauComboBox = new JComboBox<>();
        for (int i = 1; i <= kMax; i++) {
            niveauComboBox.addItem(i);
        }
        JButton btnVisualiserNiveau = new JButton("Visualiser");

        controlPanel.add(aeroportLabel);
        controlPanel.add(aeroportComboBox);
        controlPanel.add(btnVisualiserAeroport);
        controlPanel.add(niveauLabel);
        controlPanel.add(niveauComboBox);
        controlPanel.add(btnVisualiserNiveau);

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(visualisationPanel), BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Action Listeners
        btnVisualiserAeroport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAeroport = (String) aeroportComboBox.getSelectedItem();
                visualiserNiveauxParAeroport(selectedAeroport);
            }
        });

        btnVisualiserNiveau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedNiveau = (int) niveauComboBox.getSelectedItem();
                visualiserVolsParNiveau(selectedNiveau);
            }
        });
    }

    private void visualiserNiveauxParAeroport(String aeroport) {
        visualisationPanel.removeAll();
        visualisationPanel.revalidate();
        visualisationPanel.repaint();

        for (Node node : graph) {
            String aeroportNode = node.getAttribute("aeroport");
            int niveau = node.getAttribute("niveau");
            if (aeroportNode.equals(aeroport)) {
                JLabel volLabel = new JLabel(node.getId() + " - Niveau: " + niveau);
                volLabel.setOpaque(true);
                volLabel.setBackground(getColorByLevel(niveau));
                volLabel.setPreferredSize(new Dimension(200, 30));
                visualisationPanel.add(volLabel);
            }
        }

        visualisationPanel.revalidate();
        visualisationPanel.repaint();
    }

    private void visualiserVolsParNiveau(int niveau) {
        visualisationPanel.removeAll();
        visualisationPanel.revalidate();
        visualisationPanel.repaint();

        for (Node node : graph) {
            int nodeNiveau = node.getAttribute("niveau");
            if (nodeNiveau == niveau) {
                JLabel volLabel = new JLabel(node.getId() + " - Aéroport: " + node.getAttribute("aeroport"));
                volLabel.setOpaque(true);
                volLabel.setBackground(getColorByLevel(niveau));
                volLabel.setPreferredSize(new Dimension(200, 30));
                visualisationPanel.add(volLabel);
            }
        }

        visualisationPanel.revalidate();
        visualisationPanel.repaint();
    }

    private Color getColorByLevel(int niveau) {
        // Exemple simple de coloration selon le niveau
        switch (niveau) {
            case 1: return Color.RED;
            case 2: return Color.BLUE;
            case 3: return Color.GREEN;
            case 4: return Color.YELLOW;
            case 5: return Color.MAGENTA;
            default: return Color.GRAY;
        }
    }
}
