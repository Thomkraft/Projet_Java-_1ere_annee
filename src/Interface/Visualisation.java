package Interface;

import Stockage.Vols;
import org.graphstream.graph.Graph;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Visualisation extends JFrame {
    private JComboBox<String> aeroportComboBox;
    private JComboBox<Integer> niveauComboBox;
    private JPanel visualisationPanel;
    private final Graph graph;
    private JTable volTable;
    private DefaultTableModel tableModel;
    private List<Vols> listeVol;

    public Visualisation(Graph graph, List<String> listeAeroport, String cheminAeroport, String cheminTxtColo, List<Vols> listeVol) {
        this.graph = graph;
        this.listeVol = listeVol;
        setTitle("Visualisation des Niveaux de Vol");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI(listeAeroport, cheminTxtColo);

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

    private void initUI(List<String> listeAeroports, String cheminTxtColo) {
        System.out.println("Chemin du fichier de colo pour carte = " + cheminTxtColo);
        JPanel mainPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Vol", "Aéroport", "Niveau"};
        tableModel = new DefaultTableModel(columnNames, 0);
        volTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(volTable);

        visualisationPanel = new JPanel(new BorderLayout());
        visualisationPanel.setBackground(Color.WHITE);
        visualisationPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel aeroportLabel = new JLabel("Sélectionner un aéroport:");
        aeroportComboBox = new JComboBox<>();
        for (String aeroport : listeAeroports) {
            aeroportComboBox.addItem(aeroport);
        }

        JLabel niveauLabel = new JLabel("Sélectionner un niveau:");
        niveauComboBox = new JComboBox<>();
        int kmax = getKmax(cheminTxtColo);
        niveauComboBox.removeAllItems();
        for (int i = 1; i <= kmax; i++) {
            niveauComboBox.addItem(i);
        }

        controlPanel.add(aeroportLabel);
        controlPanel.add(aeroportComboBox);
        controlPanel.add(niveauLabel);
        controlPanel.add(niveauComboBox);

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(visualisationPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private int getKmax(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int maxNiveau = 0;
            Map<String, Integer> sommetNiveauMap = new HashMap<>();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("; ");
                if (parts.length == 3) {
                    String vol = parts[0].trim();
                    String aeroport = parts[1].trim();
                    int niveau = Integer.parseInt(parts[2].trim());
                    sommetNiveauMap.put(vol, niveau);
                    if (niveau > maxNiveau) {
                        maxNiveau = niveau;
                    }
                }
            }
            ajouterNiveauAuxVols(sommetNiveauMap);
            return maxNiveau;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void ajouterNiveauAuxVols(Map<String, Integer> sommetNiveauMap) {
        for (Vols vol : listeVol) {
            if (sommetNiveauMap.containsKey(vol.getNomVol())) {
                vol.setNiveau(sommetNiveauMap.get(vol.getNomVol()));
            }
        }
    }

    public void visualiserNiveauxParAeroport(String aeroport) {
        tableModel.setRowCount(0);
        for (Vols vol : listeVol) {
            String aeroportD = vol.getAéroportDépart().trim();
            String aeroportA = vol.getAéroportArrivée().trim();
            if (aeroportD.equals(aeroport) || aeroportA.equals(aeroport)) {
                tableModel.addRow(new Object[]{vol.getNomVol(), aeroportD, aeroportA, vol.getNiveau()});
            }
        }
    }

    public void visualiserVolsParNiveau(int niveau) {
        tableModel.setRowCount(0);
        for (Vols vol : listeVol) {
            if (vol.getNiveau() == niveau) {
                tableModel.addRow(new Object[]{vol.getNomVol(), vol.getAéroportDépart(), vol.getAéroportArrivée(), vol.getNiveau()});
            }
        }
    }

    public void afficherTousLesVols() {
        tableModel.setRowCount(0);
        for (Vols vol : listeVol) {
            tableModel.addRow(new Object[]{vol.getNomVol(), vol.getAéroportDépart(), vol.getAéroportArrivée(), vol.getNiveau()});
        }
    }
}
