package Interface;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tom
 *  
 */
public class Visualisation extends JFrame {
    private JComboBox<String> aeroportComboBox;
    private JComboBox<Integer> niveauComboBox;
    private JPanel visualisationPanel;
    private Graph graph;
    private JTable volTable;
    private DefaultTableModel tableModel;

    public Visualisation(Graph graph, List<String> listeAeroport, String filePath) {
        this.graph = graph;
        setTitle("Visualisation des Niveaux de Vol");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initUI(listeAeroport);
        lireFichierSommets(filePath);
        
        aeroportComboBox.addActionListener(e -> {
            String selectedAeroport = (String) aeroportComboBox.getSelectedItem();
            visualiserNiveauxParAeroport(selectedAeroport);
        });

        niveauComboBox.addActionListener(e -> {
            int selectedNiveau = (int) niveauComboBox.getSelectedItem();
            visualiserVolsParNiveau(selectedNiveau);
        });
    }

    public JComboBox<String> getAeroportComboBox() {
        return aeroportComboBox;
    }

    public JComboBox<Integer> getNiveauComboBox() {
        return niveauComboBox;
    }

    public JPanel getVisualisationPanel() {
        return visualisationPanel;
    }

    public Graph getGraph() {
        return graph;
    }

    private void initUI(List<String> listeAeroports) {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Table setup
        String[] columnNames = {"Vol", "Aéroport départ", "Niveau"};
        tableModel = new DefaultTableModel(columnNames, 0);
        volTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(volTable);

        visualisationPanel = new JPanel(new BorderLayout());
        visualisationPanel.setBackground(Color.WHITE);
        visualisationPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel pour les contrôles
        JPanel controlPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel aeroportLabel = new JLabel("Sélectionner un aéroport:");
        aeroportComboBox = new JComboBox<>();
        for (String aeroport : listeAeroports) {
            aeroportComboBox.addItem(aeroport);
        }

        JLabel niveauLabel = new JLabel("Sélectionner un niveau:");
        niveauComboBox = new JComboBox<>();

        controlPanel.add(aeroportLabel);
        controlPanel.add(aeroportComboBox);
        controlPanel.add(niveauLabel);
        controlPanel.add(niveauComboBox);

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(visualisationPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void lireFichierSommets(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int maxCouleur = 0;
            Map<String, Integer> sommetCouleurMap = new HashMap<>();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String sommet = parts[0].trim();
                    int couleur = Integer.parseInt(parts[1].trim());
                    sommetCouleurMap.put(sommet, couleur);
                    if (couleur > maxCouleur) {
                        maxCouleur = couleur;
                    }
                }
            }

            // Remplir la niveauComboBox avec le nombre maximum de couleurs
            niveauComboBox.removeAllItems();
            for (int i = 1; i <= maxCouleur; i++) {
                niveauComboBox.addItem(i);
            }

            // Mettre à jour les sommets du graphe avec les niveaux
            for (Map.Entry<String, Integer> entry : sommetCouleurMap.entrySet()) {
                String sommetId = entry.getKey();
                int couleur = entry.getValue();
                Node node = graph.getNode(sommetId);
                if (node != null) {
                    node.setAttribute("niveau", couleur);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void visualiserNiveauxParAeroport(String aeroport) {
        tableModel.setRowCount(0); // Clear the table

        for (Node node : graph) {
            String aeroportNode = node.getAttribute("aeroport");
            Integer niveau = node.getAttribute("niveau");
            if (aeroportNode != null && niveau != null && aeroportNode.equals(aeroport)) {
                tableModel.addRow(new Object[]{node.getId(), aeroportNode, niveau});
            }
        }
    }

    public void visualiserVolsParNiveau(int niveau) {
        tableModel.setRowCount(0); // Clear the table

        for (Node node : graph) {
            Integer nodeNiveau = node.getAttribute("niveau");
            if (nodeNiveau != null && nodeNiveau == niveau) {
                tableModel.addRow(new Object[]{node.getId(), node.getAttribute("aeroport"), nodeNiveau});
            }
        }
    }
}
