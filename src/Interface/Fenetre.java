package Interface;

import File.OpenCsv;
import File.OpenTxt;
import File.WriteInTxt;
import Stockage.Aeroports;
import Stockage.Colisions;
import Stockage.Result;
import Stockage.Vols;
import application.ChargerGraph;
import coloration.ColoDSatur;
import com.opencsv.exceptions.CsvValidationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

    private String file2 = null;
    private StringBuilder fileVolPaths = new StringBuilder();


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
                    fileVolPaths = new StringBuilder();
                    StringBuilder fileName = new StringBuilder();
                    for (File file : files) {
                        fileVolPaths.append(file.getAbsolutePath()).append(";");

                        String[] separation = file.getAbsolutePath().split("\\\\");
                        fileName.append(separation[separation.length-1]).append(";");


                    }
                    txtInsertVol.setText(fileName.toString());

                    System.out.println(fileVolPaths);

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
                    //txtInsertVol.setText(file.getAbsolutePath());

                    file2 = file.getAbsolutePath();

                    String[] separation = file.getAbsolutePath().split("\\\\");

                    lbInsertionListeAeroport.setText("liste chargé : " + separation[separation.length-1]);


                }
            }
        });
        
        btnImportGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int option = fileChooser.showOpenDialog(Fenetre.this);
                StringBuilder nameChargedFIle = new StringBuilder();
                if (option == JFileChooser.APPROVE_OPTION) {
                    File[] files = fileChooser.getSelectedFiles();
                    ArrayList<String> filePaths = new ArrayList<>();

                    for (File file : files) {
                        filePaths.add(file.getAbsolutePath());

                        String[] separation = file.getAbsolutePath().split("\\\\");
                        nameChargedFIle.append(separation[separation.length-1]).append(";");
                    }

                    // Coloration du ou des graphes
                    List<Graph> graphes = ChargerGraph.charger_graphes(filePaths);
                    Fenetre.this.afficherGraphes(graphes);

                    //lbImportGraph.setText("Liste de graph chargé : "+ nameChargedFIle.toString());
                }
            }
        });

        
        // Ajouter un ActionListener pour le bouton Afficher Carte dans Fenetre
        btCarte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    String filePath = "C:\\Users\\thoma\\Documents\\Data_Test_txt/aeroports.txt";
                    Carte carte = new Carte(filePath);
                    carte.setVisible(true);
                    carte.afficherCarteAvecVolPredefini();
                });
            }
        });



        btTraiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Traiter les fichiers et utiliser kmax si spécifié


                String file;
                //String file2 = "C:\\Users\\thoma\\Documents\\IUT\\1ère_année\\2-semestre\\2-Sae_crash\\Data_Test_txt/aeroports.txt";


                OpenCsv openCsv = new OpenCsv();
                OpenTxt openTxt = new OpenTxt();
                Colisions colision = new Colisions();
                WriteInTxt txtWriter = new WriteInTxt();

                List<Vols> listeVol = null;
                List<String> listeColisionVol = new ArrayList<>();
                List<Aeroports> listeAeroport = null;

                try {
                    listeAeroport = openTxt.LectureTxtAéroports(file2);
                    if(listeAeroport == null) {
                        throw new Exception();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CsvValidationException ex) {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    System.err.println("Mauvais format pour le fichier d'aéroport !");
                    return;
                }
                try {
                    if (Integer.parseInt(txtKmax.getText()) <= 0){
                        txtWriter.setkMax(0);
                    }else {
                        txtWriter.setkMax(Integer.parseInt(txtKmax.getText()));
                    }
                }catch (NumberFormatException ex) {
                    txtWriter.setkMax(0);
                }

                String[] separationVolPath = fileVolPaths.toString().split(";");
                String[] resultFileName;
                //Boucle pour comparer chaque vols a tous les vols
                //tous les fichiers
                for(int k = 0; k <= separationVolPath.length-1; k++){
                    file = separationVolPath[k];
                    resultFileName = file.split("-");
                    String fileName = resultFileName[resultFileName.length-1];

                    try {
                        listeVol = openCsv.LectureCsvVols(file);
                        if (listeVol == null){
                            throw new Exception();
                        }


                    } catch (IOException ex) {
                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (CsvValidationException ex) {
                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        String[] separation = file.split("\\\\");
                        txtConsole.setText(txtConsole.getText() + "\n" + "le fichier " + separation[separation.length-1] + " n'est pas un .csv ou n'est pas formaté comme il faut !");
                        continue;
                    }

                    for(int i = 0; i < listeVol.size() ;  i++){
                        for(int j = i + 1 ; j < listeVol.size() ; j++){

                            //recupere si colision ou pas et si colision recupere aussi les coordonnée de la colision
                            Result resultat = colision.getCoordColision(listeVol.get(i), listeVol.get(j), listeAeroport);

                            //si colision alors ajout du vol dans la liste des vols en colision
                            if(resultat.isInColision()){
                                listeColisionVol.add(listeVol.get(i).getNomVol()+ " " + listeVol.get(j).getNomVol());
                            }
                        }
                    }

                    try {
                        txtWriter.writeInFile("ColisionVol-" + fileName, (ArrayList<String>) listeColisionVol, Vols.nbVols, txtWriter.getkMax());
                    } catch (IOException ex) {
                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("Fichier : " + file);
                    System.out.println("Nombre de colisions : " + colision.nbColisions);
                    System.out.println("nombre de vols : " + Vols.nbVols);
                    listeColisionVol.clear();
                    listeVol.clear();
                    colision.setNbColisions(0);
                    Vols.setNbVols(0);
                }

                System.out.println("------------------");
                ArrayList<String> listPathFileUpdated = new ArrayList<>();
                int m = 1;
                listPathFileUpdated = (ArrayList<String>) txtWriter.getListLastFileUpdated();

                for(String path: listPathFileUpdated){
                    System.out.println("Fichier "+ m + " : " + path);
                    m++;
                }

        }
        });

        btExporter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                //fileChooser.setCurrentDirectory("..//");
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
        FenetreGraphe fenetreGraph = new FenetreGraphe(graphes.get(currentGraphIndex), this); // Pass Fenetre object
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

