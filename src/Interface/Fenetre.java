package Interface;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import org.graphstream.graph.Graph;

public class Fenetre extends JFrame {
    JLabel lbInsertListe = new JLabel("Insérez une liste :");
    JLabel lbKmax = new JLabel("Valeur de Kmax :");
    JTextField txtInsertListe = new JTextField("Chemin du/des fichiers...");
    JTextField txtKmax = new JTextField("Entrez un entier...");
    JTextArea txtConsole = new JTextArea();
    JButton btParcourir = new JButton("Parcourir");
    JButton btImporter = new JButton("Importer");
    JButton btTraiter = new JButton("Traiter");
    JButton btExporter = new JButton("Exporter");

    JPanel mainPanel = new JPanel(new GridBagLayout());
    JDesktopPane graphPanel = new JDesktopPane();
    
    private int currentGraphIndex = 0;
    private List<Graph> graphes;
    
    public Fenetre() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialisationVue();
    }

    public void initialisationVue() {
        // Title
        this.setTitle("Projet_Crash");

        GridBagConstraints cont = new GridBagConstraints();
        cont.insets = new Insets(5, 5, 5, 5);
        
        // Ajout de la bordure autour de la fenêtre principale
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Label Insert List
        cont.fill = GridBagConstraints.HORIZONTAL;
        cont.gridx = 0;
        cont.gridy = 0;
        cont.gridwidth = 2;
        cont.weightx = 1;
        mainPanel.add(lbInsertListe, cont);

        // Label Kmax
        cont.gridx = 5;
        cont.gridy = 0;
        cont.gridwidth = 1;
        cont.weightx = 0.5;
        mainPanel.add(lbKmax, cont);

        // Text Insert List
        cont.gridx = 0;
        cont.gridy = 1;
        cont.gridwidth = 3;
        cont.weightx = 1;
        mainPanel.add(txtInsertListe, cont);

        // Bouton Parcourir
        cont.gridx = 3;
        cont.gridy = 1;
        cont.gridwidth = 1;
        cont.weightx = 0.1; // Réduire la largeur du bouton
        mainPanel.add(btParcourir, cont);

        // Text Kmax
        cont.gridx = 5;
        cont.gridy = 1;
        cont.gridwidth = 1;
        cont.weightx = 0.5;
        mainPanel.add(txtKmax, cont);

        // Graph Panel
        cont.gridx = 0;
        cont.gridy = 2;
        cont.gridwidth = 6;
        cont.weightx = 1;
        cont.weighty = 1;
        cont.fill = GridBagConstraints.BOTH;
        mainPanel.add(graphPanel, cont);

        // Console
        JScrollPane consoleScrollPane = new JScrollPane(txtConsole);
        cont.gridx = 0;
        cont.gridy = 4;
        cont.gridwidth = 6;
        cont.weightx = 1;
        cont.weighty = 0.1; // Réduire le poids pour limiter la hauteur
        cont.fill = GridBagConstraints.BOTH;
        mainPanel.add(consoleScrollPane, cont);

        // Bouton Traiter
        cont.gridx = 3;
        cont.gridy = 6;
        cont.gridwidth = 2;
        cont.weighty = 0;
        cont.weightx = 0.5;
        mainPanel.add(btTraiter, cont);

        // Bouton Exporter
        cont.gridx = 5;
        cont.gridy = 6;
        cont.gridwidth = 1;
        cont.weighty = 0;
        cont.weightx = 0.5;
        mainPanel.add(btExporter, cont);
        
        // Label Insertion Liste Aeroport
        JLabel lbInsertionListeAeroport = new JLabel("Insertion Liste Aeroport :");
        cont.gridx = 4;
        cont.gridy = 0;
        cont.gridwidth = 1;
        cont.weightx = 0.5;
        mainPanel.add(lbInsertionListeAeroport, cont);

        // Deuxième bouton Parcourir pour la sélection d'une liste d'aéroports
        JButton btParcourirListeAeroport = new JButton("Parcourir");
        cont.gridx = 4; // Même colonne que le premier bouton Parcourir
        cont.gridy = 1; // Juste en dessous du premier bouton Parcourir
        cont.gridwidth = 1;
        cont.weightx = 0.5;
        mainPanel.add(btParcourirListeAeroport, cont);

        // Bouton Précédent
        JButton btnPrevious = new JButton("Précédent");
        cont.gridx = 0;
        cont.gridy = 3;
        cont.gridwidth = 1;
        cont.weightx = 0.5;
        graphPanel.add(btnPrevious, cont);

        // Bouton Suivant
        JButton btnNext = new JButton("Suivant");
        cont.gridx = 1;
        cont.gridy = 3; // Position verticale dans graphPanel
        cont.gridwidth = 1;
        cont.weightx = 0.5;
        graphPanel.add(btnNext, cont);

        // Ajoutez des écouteurs d'événements pour les boutons de navigation
        btnPrevious.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (currentGraphIndex > 0) {
                currentGraphIndex--;
                afficherGraphiqueCourant();
            }
        }
        });

        btnNext.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (currentGraphIndex < graphes.size() - 1) {
                currentGraphIndex++;
                afficherGraphiqueCourant();
            }
        }
        });

            this.setContentPane(mainPanel);
            this.setSize(1200, 800);
            this.setLocationRelativeTo(null);
        }

        // Modifiez votre méthode afficherGraphes pour qu'elle prenne en compte les graphiques passés
        public void afficherGraphes(List<Graph> graphes) {
            this.graphes = graphes;
            afficherGraphiqueCourant();
        }

        // Ajoutez cette méthode pour afficher le graphique courant
        private void afficherGraphiqueCourant() {
            graphPanel.removeAll();
            FenetreGraph frame = new FenetreGraph(graphes.get(currentGraphIndex));
            graphPanel.add(frame);
            frame.setVisible(true);
            try {
                frame.setMaximum(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            graphPanel.revalidate();
            graphPanel.repaint();
        }
}
