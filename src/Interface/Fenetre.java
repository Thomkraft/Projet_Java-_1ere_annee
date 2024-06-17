package Interface;

import application.ChargerGraph;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import application.ChargerGraph;
import org.graphstream.graph.Graph;

public class Fenetre extends JFrame {
    JLabel lbImportGraph = new JLabel("Importer un graph:");
    JButton btnImportGraph = new JButton("Importer");
    JLabel lbInsertVol = new JLabel("Insérez une liste de vol :");
    JLabel lbInsertionListeAeroport = new JLabel("Inserez une liste d'aéroport :");
    JTextField txtInsertVol = new JTextField("");
    JLabel lbKmax = new JLabel("Valeur de Kmax :");
    JTextField txtKmax = new JTextField("");
    static JTextPane txtConsole = new JTextPane();
    JButton btParcourirVols = new JButton("Parcourir");
    JButton btTraiter = new JButton("Traiter");
    JButton btExporter = new JButton("Exporter");
    JButton btCarte = new JButton("Afficher la carte");
    JButton btParcourirAeroports = new JButton("Parcourir");

    JPanel mainPanel = new JPanel(new GridBagLayout());
    JDesktopPane graphPanel = new JDesktopPane();

    private int currentGraphIndex = 0;
    private List<Graph> graphes;

    public Fenetre() {
        //Style fenetre
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialisationVue();
        ajouterActionListeners();
    }

    private void initialisationVue() {
        this.setTitle("Projet_Crash");

        GridBagConstraints cont = new GridBagConstraints();
        cont.insets = new Insets(5, 5, 5, 5);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Label Insert List vol
        cont.fill = GridBagConstraints.HORIZONTAL;
        cont.gridx = 0;
        cont.gridy = 0;
        cont.gridwidth = 1;
        mainPanel.add(lbInsertVol, cont);

        // Text Insert List vol
        txtInsertVol.setEditable(false);
        cont.gridx = 0;
        cont.gridy = 1;
        cont.gridwidth = 1;
        cont.weightx = 1;
        mainPanel.add(txtInsertVol, cont);
        
        // Fixer la taille préférée du champ de texte de la liste de vols
        Dimension txtInsertVolPreferredSize = new Dimension(200, 20);
        txtInsertVol.setPreferredSize(txtInsertVolPreferredSize);

        // Bouton Parcourir vol
        cont.gridx = 1;
        cont.gridy = 1;
        cont.weightx = 0;
        mainPanel.add(btParcourirVols, cont);

        // Label Insertion Liste Aeroport
        cont.gridx = 3;
        cont.gridy = 0;
        cont.gridwidth = 1;
        cont.weightx = 1;
        mainPanel.add(lbInsertionListeAeroport, cont);

        // Bouton Parcourir aéroports
        cont.gridx = 3;
        cont.gridy = 1;
        cont.gridwidth = 1;
        cont.weightx = 1;
        cont.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(btParcourirAeroports, cont);
        
        // Label Importer un graph
        cont.gridx = 4;
        cont.gridy = 0;
        cont.gridwidth = 1;
        mainPanel.add(lbImportGraph, cont);

        // Bouton Importer un graph
        cont.gridx = 4;
        cont.gridy = 1;
        cont.gridwidth = 1;
        mainPanel.add(btnImportGraph, cont);
        
        // Label Kmax
        cont.gridx = 5;
        cont.gridy = 0;
        cont.gridwidth = 1;
        mainPanel.add(lbKmax, cont);
        
         // Text Kmax
        cont.gridx = 5;
        cont.gridy = 1;
        cont.gridwidth = 1;
        mainPanel.add(txtKmax, cont);

        // Graph Panel
        cont.gridx = 0;
        cont.gridy = 3;
        cont.gridwidth = 6;
        cont.weightx = 1;
        cont.weighty = 1;
        cont.fill = GridBagConstraints.BOTH;
        mainPanel.add(graphPanel, cont);

        // Console
        JScrollPane consoleScrollPane = new JScrollPane(txtConsole);
        consoleScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Politique de défilement horizontal
        consoleScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Politique de défilement vertical

        txtConsole.setBorder(BorderFactory.createEmptyBorder(7, 10, 7, 10));
        txtConsole.setFont(new Font("Arial", Font.PLAIN, 14));
        txtConsole.setEditable(false);

        cont.gridx = 0;
        cont.gridy = 4;
        cont.gridwidth = 6;
        cont.weightx = 1;
        cont.weighty = 0.1;
        cont.fill = GridBagConstraints.BOTH;
        mainPanel.add(consoleScrollPane, cont);
        
        // Fixer la taille préférée de la console
        Dimension consolePreferredSize = new Dimension(0, 100); // Taille souhaitée (largeur automatique, hauteur 150 pixels)
        consoleScrollPane.setPreferredSize(consolePreferredSize);

        // Bouton Afficher Carte
        cont.gridx = 0;
        cont.gridy = 6;
        cont.gridwidth = 2;
        cont.weighty = 0;
        mainPanel.add(btCarte, cont);

        // Bouton Traiter
        cont.gridx = 3;
        cont.gridy = 6;
        cont.gridwidth = 2;
        cont.weighty = 0;
        mainPanel.add(btTraiter, cont);

        // Bouton Exporter
        cont.gridx = 5;
        cont.gridy = 6;
        cont.gridwidth = 1;
        cont.weighty = 0;
        mainPanel.add(btExporter, cont);

        this.setContentPane(mainPanel);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
    }

    private void ajouterActionListeners() {
        btParcourirVols.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int option = fileChooser.showOpenDialog(Fenetre.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File[] files = fileChooser.getSelectedFiles();
                    StringBuilder filePaths = new StringBuilder();
                    for (File file : files) {
                        filePaths.append(file.getAbsolutePath()).append(";");
                    }
                    txtInsertVol.setText(filePaths.toString());
                }
            }
        });

        btParcourirAeroports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(Fenetre.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                }
            }
        });
        
        btnImportGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int option = fileChooser.showOpenDialog(Fenetre.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File[] files = fileChooser.getSelectedFiles();
                    List<String> filePaths = new ArrayList<>();
                    for (File file : files) {
                        filePaths.add(file.getAbsolutePath());
                    }
                    // Appeler la méthode pour charger les graphes
                    List<Graph> graphes = ChargerGraph.charger_graphes(filePaths);
                    // Afficher les graphes dans la fenêtre
                    afficherGraphes(graphes);
                }
            }
        });

        
        // Ajout d'action listeners pour les autres boutons
        btCarte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Afficher la carte
                // Cette fonctionnalité reste à implémenter
            }
        });

        btTraiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Traiter les fichiers et utiliser kmax si spécifié
                // Cette fonctionnalité reste à implémenter
            }
        });

        btExporter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(Fenetre.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    // Exporter les résultats dans le fichier
                    // Cette fonctionnalité reste à implémenter
                }
            }
        });
        
        
    }

    public void afficherGraphes(List<Graph> graphes) {
        this.graphes = graphes;
        afficherGraphiqueCourant();
    }

    void afficherGraphiqueCourant() {
        // Supprimer tous les composants graphiques actuellement présents dans graphPanel
        graphPanel.removeAll();

        // Créer une instance de FenetreGraph avec le graph courant
        FenetreGraph fenetreGraph = new FenetreGraph(graphes.get(currentGraphIndex), this); // Pass Fenetre object
        fenetreGraph.setGraphIndex(currentGraphIndex + 1); // Set the current graph index

        // Ajouter FenetreGraph à graphPanel
        graphPanel.add(fenetreGraph);

        // Rendre FenetreGraph visible
        fenetreGraph.setVisible(true);

        try {
            // Essayer de définir la taille maximale de FenetreGraph
            fenetreGraph.setMaximum(true);
        } catch (Exception e) {
            // Gérer toute exception qui pourrait survenir lors de la définition de la taille maximale
            e.printStackTrace();
        }

        // Valider et rafraîchir graphPanel pour refléter les changements
        graphPanel.revalidate();
        graphPanel.repaint();


        new InfosConsole(graphes.get(currentGraphIndex));

        
        // Ajouter un ActionListener pour le champ txtGraphNumber dans FenetreGraph
        fenetreGraph.addIndiceTxtListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer le texte saisi dans le JTextField
                int inputText = fenetreGraph.getGraphNumber();
                try {
                    int newIndex = inputText - 1; // Soustraire 1 car les indices commencent à 0
                    
                    // Vérifier si l'indice est valide
                    if (newIndex >= 0 && newIndex < graphes.size()) {
                        currentGraphIndex = newIndex;
                        // Vérifier si FenetreGraph est en mode plein écran et le restaurer si nécessaire
                        if (fenetreGraph.isFullScreen()) {
                            fenetreGraph.restoreWindow();
                        }
                        // Afficher le graphique correspondant
                        afficherGraphiqueCourant();
                    } else {
                        // Afficher un message d'erreur si l'indice est invalide
                        JOptionPane.showMessageDialog(null, "Indice de graph invalide.");
                    }
                } catch (NumberFormatException ex) {
                    // Afficher un message d'erreur si la conversion échoue
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre valide.");
                }
            }
        });

        // Ajouter un ActionListener pour le bouton Précédent de FenetreGraph
        fenetreGraph.addPrecButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentGraphIndex > 0) {
                    // Décrémenter l'index du graphique courant
                    currentGraphIndex--;

                    // Vérifier si FenetreGraph est en mode plein écran et le restaurer si nécessaire
                    if (fenetreGraph.isFullScreen()) {
                        fenetreGraph.restoreWindow();
                    }

                    // Afficher le graphique courant
                    afficherGraphiqueCourant();
                }
            }
        });

        // Ajouter un ActionListener pour le bouton Suivant de FenetreGraph
        fenetreGraph.addNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentGraphIndex < graphes.size() - 1) {
                    // Incrémenter l'index du graphique courant
                    currentGraphIndex++;

                    // Vérifier si FenetreGraph est en mode plein écran et le restaurer si nécessaire
                    if (fenetreGraph.isFullScreen()) {
                        fenetreGraph.restoreWindow();
                    }

                    // Afficher le graphique courant
                    afficherGraphiqueCourant();
                }
            }
        });
    }

    public static JTextPane getTxtConsole() {
        return txtConsole;
    }
}

