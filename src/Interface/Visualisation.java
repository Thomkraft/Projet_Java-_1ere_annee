package Interface;

import Stockage.Vols;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Fenêtre de visualisation des niveaux de vol pour les aéroports et les niveaux sélectionnés.
 * Cette classe affiche les vols en fonction des sélections d'aéroport ou de niveau.
 *
 * @author Tom
 */
public final class Visualisation extends JFrame {
    private JComboBox<String> aeroportComboBox;
    private JComboBox<Integer> niveauComboBox;
    private JButton actualiserButton = new JButton("Actualiser");
    private JPanel visualisationPanel;
    private JTable volTable;
    private DefaultTableModel tableModel;
    private List<Vols> listeVol;
    private int cmd; // cmd = 1 for airport, 2 for level
    private String cheminAeroport;
    private List<String[]> listeAeroport;

    /**
     * Constructeur de la fenêtre de visualisation.
     * @author Tom
     * @param listeAeroport Liste des aéroports sous forme de tableau de chaînes de caractères [code, nom].
     * @param cheminAeroport Chemin du fichier d'aéroports.
     * @param cheminTxtColo Chemin du fichier de niveaux de vol.
     * @param listeVol Liste des vols à afficher.
     * @param cmd Mode d'affichage : 1 pour aéroport, 2 pour niveau.
     */
    public Visualisation(List<String[]> listeAeroport, String cheminAeroport, String cheminTxtColo, List<Vols> listeVol, int cmd) {
        this.listeVol = listeVol;
        this.cmd = cmd;
        this.cheminAeroport = cheminAeroport;
        this.listeAeroport = listeAeroport;
        setTitle("Visualisation des Niveaux de Vol");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI(listeAeroport, cheminTxtColo);

        setNiveauForVolFromFile(cheminTxtColo);
        System.out.println("Liste des vols chargés : " + listeVol);
        actualiserButton.addActionListener(e -> {
            if (cmd == 1) {
                String selectedAeroport = (String) aeroportComboBox.getSelectedItem();
                visualiserNiveauxParAeroport(selectedAeroport);
            } else if (cmd == 2) {
                int selectedNiveau = (int) niveauComboBox.getSelectedItem();
                visualiserVolsParNiveau(selectedNiveau);
            }
        });
    }

    /**
     * Initialise l'interface utilisateur avec les composants graphiques.
     * @author Tom
     * @param listeAeroport Liste des aéroports.
     * @param cheminTxtColo Chemin du fichier de niveaux de vol.
     */
    private void initUI(List<String[]> listeAeroport, String cheminTxtColo) {
        JPanel mainPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Vol", "Aéroport Départ", "Aéroport Arrivée", "Niveau"};
        tableModel = new DefaultTableModel(columnNames, 0); // Tableau vide au départ
        volTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(volTable);

        visualisationPanel = new JPanel(new BorderLayout());
        visualisationPanel.setBackground(Color.WHITE);
        visualisationPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if (cmd == 1) {
            JLabel aeroportLabel = new JLabel("Sélectionner un aéroport:");
            aeroportComboBox = new JComboBox<>();
            for (String[] nomAeroport : listeAeroport) {
                aeroportComboBox.addItem(nomAeroport[1]);
            }
            gbc.gridx = 0;
            gbc.gridy = 0;
            controlPanel.add(aeroportLabel, gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            controlPanel.add(actualiserButton, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            controlPanel.add(aeroportComboBox, gbc);

        } else if (cmd == 2) {
            JLabel niveauLabel = new JLabel("Sélectionner un niveau:");
            niveauComboBox = new JComboBox<>();
            int kmax = getKmax(cheminTxtColo);
            niveauComboBox.removeAllItems();
            for (int i = 1; i <= kmax; i++) {
                niveauComboBox.addItem(i);
            }
            gbc.gridx = 0;
            gbc.gridy = 0;
            controlPanel.add(niveauLabel, gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            controlPanel.add(actualiserButton, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            controlPanel.add(niveauComboBox, gbc);
        }

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(visualisationPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    /**
     * Récupère le niveau maximum de vol à partir du fichier spécifié.
     * @author Tom
     * @param filePath Chemin du fichier de niveaux de vol.
     * @return Le niveau maximum de vol.
     */
    private int getKmax(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int maxCouleur = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String couleurStr = parts[1].trim();
                    int couleur = Integer.parseInt(couleurStr);
                    System.out.println("Couleur : " + couleur);
                    if (couleur > maxCouleur) {
                        maxCouleur = couleur;
                    }
                }
            }
            return maxCouleur;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Charge les niveaux de vol à partir du fichier spécifié et met à jour la liste des vols.
     * @author Tom
     * @param filePath Chemin du fichier de niveaux de vol.
     */
    public void setNiveauForVolFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String nomVol = parts[0].trim();
                    int niveau = Integer.parseInt(parts[1].trim());

                    // Print for debugging purposes
                    System.out.println("Nom Vol: " + nomVol + ", Niveau: " + niveau);

                    // Find the corresponding flight in listeVol and update its level
                    boolean volTrouve = false;
                    for (Vols vol : listeVol) {
                        if (vol.getNomVol().equals(nomVol)) {
                            vol.setNiveau(niveau);
                            volTrouve = true;
                            break; // Exit loop after finding the corresponding flight
                        }
                    }

                    if (!volTrouve) {
                        System.err.println("Vol non trouvé dans listeVol pour nomVol: " + nomVol);
                        // Vous pouvez ajouter un traitement ici si le vol n'est pas trouvé
                    }
                }
            }
            // After updating, print listeVol to check its contents
            System.out.println("Liste des vols après mise à jour : ");
            for (Vols vol : listeVol) {
                System.out.println(vol);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche les vols en fonction de l'aéroport sélectionné.
     * Vide d'abord la table avant de remplir avec les vols correspondants.
     * @author Tom
     * @param aeroportNom Nom de l'aéroport sélectionné.
     */
    public void visualiserNiveauxParAeroport(String aeroportNom) {
        tableModel.setRowCount(0); // Vide la table avant de la remplir

        // Parcourir la liste des vols
        for (Vols vol : listeVol) {
            String aeroportD = vol.getAéroportDépart().trim();
            String aeroportA = vol.getAéroportArrivée().trim();
            boolean found = false;

            // Trouver le code de l'aéroport sélectionné
            String codeAeroportSelectionne = null;
            for (String[] aeroportInfo : listeAeroport) {
                if (aeroportInfo[1].equalsIgnoreCase(aeroportNom)) {
                    codeAeroportSelectionne = aeroportInfo[0];
                    break;
                }
            }

            // Vérifier si l'aéroport sélectionné correspond à l'un des aéroports du vol
            if (codeAeroportSelectionne != null) {
                if (aeroportD.equals(codeAeroportSelectionne) || aeroportA.equals(codeAeroportSelectionne)) {
                    tableModel.addRow(new Object[]{vol.getNomVol(), aeroportD, aeroportA, vol.getNiveau()});
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Aucun vol trouvé pour l'aéroport sélectionné : " + aeroportNom);
            }
        }
    }

    /**
     * Affiche les vols en fonction du niveau sélectionné.
     * Vide d'abord la table avant de remplir avec les vols correspondants.
     * @author Tom
     * @param niveau Niveau de vol sélectionné.
     */
    public void visualiserVolsParNiveau(int niveau) {
        tableModel.setRowCount(0); // Vide la table avant de la remplir
        for (Vols vol : listeVol) {
            if (vol.getNiveau() == niveau) {
                tableModel.addRow(new Object[]{vol.getNomVol(), vol.getAéroportDépart(), vol.getAéroportArrivée(), vol.getNiveau()});
            }
        }
    }
}
