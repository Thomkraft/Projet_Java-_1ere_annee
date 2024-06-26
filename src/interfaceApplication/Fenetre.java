package interfaceApplication;

import fichier.OuvrirCsv;
import fichier.OuvrirTxt;
import fichier.EcrireDansCSV;
import fichier.EcrireDansTxt;
import stockageDonnees.StockageAeroports;
import stockageDonnees.Colisions;
import stockageDonnees.Vols;
import application.ChargerGraphe;
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


/**
 * Fenêtre principale de l'application, permettant l'importation et la gestion des graphes et des vols.
 * 
 * @author tom
 */
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


    private int indiceGraphCourant = 0;
    private List<Graph> graphes;
    private List<String> nomFichiers;
    private List<Vols> listeVol = new ArrayList<>();
    private List<Vols> listeVolPourCarte = new ArrayList<>();

    private String fichierAeroport = null;
    private StringBuilder fileVolPaths = new StringBuilder();
    
    private ArrayList<String> listLastColoFileUpdates = new ArrayList<>();
    private ArrayList<Graph> listLastGraphColo = new ArrayList<>();

    /**
     * Constructeur de la classe Fenetre.
     * Initialise l'interface graphique et les écouteurs d'événements.
     * @author tom
     */
    public Fenetre() {
        //Style fenetre
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialisationVue();
        ajouterActionListeners();
    }

    /**
     * Initialise les composants de l'interface graphique.
     * @author tom
    */

    private void initialisationVue() {
        this.setTitle("Projet_Crash");

        GridBagConstraints cont = new GridBagConstraints();
        cont.insets = new Insets(5, 5, 5, 5);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        txtKmax.setText("1");
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

    /**
     * Ajoute les écouteurs d'événements aux boutons de l'interface graphique.
     * @author tom
     */
    private void ajouterActionListeners() {
        btParcourirVols.addActionListener(new ActionListener() {
            /**
             * Action à réaliser lors du clic sur le bouton "Parcourir Vols".
             * Ouvre une fenêtre de dialogue pour sélectionner les fichiers de vols.
             * @author tom/thomas
             * @param e l'événement de clic
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setMultiSelectionEnabled(true);
                int option = fileChooser.showOpenDialog(Fenetre.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File[] fichiers = fileChooser.getSelectedFiles();
                    fileVolPaths = new StringBuilder();
                    StringBuilder nomFichier = new StringBuilder();
                    for (File fichier : fichiers) {
                        fileVolPaths.append(fichier.getAbsolutePath()).append(";");

                        String[] separation = fichier.getAbsolutePath().split("\\\\");
                        nomFichier.append(separation[separation.length-1]).append(";");


                    }
                    txtInsertVol.setText(nomFichier.toString());

                    System.out.println(fileVolPaths);

                }


            }
        });

        btParcourirAeroports.addActionListener(new ActionListener() {
            /**
             * Action à réaliser lors du clic sur le bouton "Parcourir Aéroports".
             * Ouvre une fenêtre de dialogue pour sélectionner le fichier des aéroports.
             * @author tom/thomas
             * @param e l'événement de clic
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int option = fileChooser.showOpenDialog(Fenetre.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File fichier = fileChooser.getSelectedFile();

                    fichierAeroport = fichier.getAbsolutePath();

                    String[] separation = fichier.getAbsolutePath().split("\\\\");

                    lbInsertionListeAeroport.setText("liste chargé : " + separation[separation.length-1]);


                }
            }
        });
        
        btnImportGraph.addActionListener(new ActionListener() {

            /**
             * Action à réaliser lors du clic sur le bouton "Importer Graph".
             * Ouvre une fenêtre de dialogue pour sélectionner le fichier de graph.
             * @author tom/thomas
             * @param e l'événement de clic
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setMultiSelectionEnabled(true);
                int option = fileChooser.showOpenDialog(Fenetre.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File[] fichiers = fileChooser.getSelectedFiles();
                    ArrayList<String> cheminsFichiers = new ArrayList<>();
                    ArrayList<String> nomsFichiers = new ArrayList<>();

                    for (File fichier : fichiers) {
                        cheminsFichiers.add(fichier.getAbsolutePath());
                        nomsFichiers.add(fichier.getName());
                    }

                    // Coloration du ou des graphes
                    List<Graph> graphes = ChargerGraphe.charger_graphes(cheminsFichiers);
                    
                    
                    EcrireDansTxt txtWriter = new EcrireDansTxt();
                    try {
                        txtWriter.ecrireDansFichierResultatColoration(graphes);
                        listLastColoFileUpdates = txtWriter.getlistDernierColoFileMAJ();
                        listLastGraphColo = txtWriter.getListDernierGraphColoMAJ();
                    } catch (IOException ex) {
                        System.out.println("erreur");
                    }
                    
                    Fenetre.this.afficherGraphes(graphes, nomsFichiers);
                    
                    System.out.println("-------------------------------------------------------------------------");
                }
            }
        });


        
        // Ajouter un ActionListener pour le bouton Afficher Carte dans Fenetre
        btCarte.addActionListener(new ActionListener() {
            /**
             * Action à réaliser lors du clic sur le bouton "Afficher la Carte".
             * Affiche une carte graphique des aéroports (et des vols).
             * @author tom
             * @param e l'événement de clic
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String cheminTxtColo = getListLastColoFileUpdates();
                SwingUtilities.invokeLater(() -> {
                    
                    // Vérifier si aucun graphe n'a été chargé
                    if (graphes == null || graphes.isEmpty()) {
                        JOptionPane.showMessageDialog(Fenetre.this, "Veuillez charger un graph avant d'afficher la carte.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Vérifier si le fichier d'aéroport n'est pas chargé
                    if (fichierAeroport == null || fichierAeroport.isEmpty()) {
                        JOptionPane.showMessageDialog(Fenetre.this, "Veuillez charger un fichier d'aéroport avant d'afficher la carte.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    System.out.println("Vols Chargés :");
                    for(Vols vol : listeVolPourCarte) {
                        System.out.println(vol);
                    }
                    
                    // Utiliser le fichier d'aéroport chargé pour afficher la carte
                    Carte carte = new Carte(fichierAeroport,cheminTxtColo,listeVolPourCarte);
                    carte.setVisible(true);
                    carte.afficherCarteAvecVolPredefini();
                });
            }
        });




        btTraiter.addActionListener(new ActionListener() {
            /**
             * Action à réaliser lors du clic sur le bouton "Traiter".
             * Traite les données des vols et affiche les résultats.
             * @author tom/thomas
             * @param e l'événement de clic
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Traiter les fichiers et utiliser kmax si spécifié
                String file;

                OuvrirCsv ouvrirCsv = new OuvrirCsv();
                OuvrirTxt ouvrirTxt = new OuvrirTxt();
                Colisions colision = new Colisions();
                EcrireDansTxt lecteurTxt = new EcrireDansTxt();
                listeVol = null;
                listeVolPourCarte = new ArrayList<>();
                
                ArrayList<String> listeColisionVol = new ArrayList<>();
                List<StockageAeroports> listeAeroport = null;
                
                List<String> listeFichierErrone = new ArrayList<>();
                try {
                    listeAeroport = ouvrirTxt.LectureTxtAeroports(fichierAeroport);
                    if(listeAeroport == null) {
                        throw new Exception();
                    }

                } catch (IOException | CsvValidationException ex) {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Fenetre.this, "Mauvais format pour le fichier d'aéroport !","Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    if (Integer.parseInt(txtKmax.getText()) <= 0){
                        lecteurTxt.setkMax(1);

                    } else {
                        lecteurTxt.setkMax(Integer.parseInt(txtKmax.getText()));
                    }

                }catch (NumberFormatException ex) {
                    lecteurTxt.setkMax(1);
                }

                String[] separationCheminVol = fileVolPaths.toString().split(";");
                String[] nomFichierResultat;
                //Boucle pour comparer chaque vols a tous les vols
                //tous les fichiers
                boolean traite = false;
                for(int k = 0; k <= separationCheminVol.length-1; k++){
                    file = separationCheminVol[k];
                    nomFichierResultat = file.split("-");
                    String nomFichier = nomFichierResultat[nomFichierResultat.length-1];

                    try {
                        listeVol = ouvrirCsv.LectureCsvVols(file);
                        if (listeVol == null){
                            throw new Exception();
                        }

                    } catch (IOException | CsvValidationException ex) {
                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);

                    } catch (Exception ex) {
                        String[] separation = file.split("\\\\");
                        listeFichierErrone.add("le fichier " + separation[separation.length-1] + " n'est pas un .csv ou n'est pas formaté comme il faut !");
                        continue;
                    }

                    if (!traite){
                        int marge = 15;
                        try {
                            String reponse = JOptionPane.showInputDialog(null, "Donnez la marge pour colisions (15 minutes si rien n'est modifié ou erreur de format) : ", "Changement marge de colision", JOptionPane.QUESTION_MESSAGE);
                            int reponseINT = Integer.parseInt(reponse);
                            if (reponseINT != 15 && reponseINT > 0) {
                                colision.setMarge(reponseINT);
                            } else {
                                colision.setMarge(marge);
                            }

                        } catch (NumberFormatException ex) {
                            colision.setMarge(marge);
                        }
                        traite = true;
                    }
                        
                    for(int i = 0; i < listeVol.size() ;  i++){
                        for(int j = i + 1 ; j < listeVol.size() ; j++){
                           
                            
                            boolean result = colision.getCoordColision(listeVol.get(i), listeVol.get(j), listeAeroport);
                            
                            if(result){
                                listeColisionVol.add(listeVol.get(i).getNomVol()+ " " + listeVol.get(j).getNomVol());
                            }
                            
                        }
                    }


                    try {
                        lecteurTxt.ecritureDansFichier("ColisionVol-" + nomFichier, listeColisionVol, Vols.nbVols, lecteurTxt.getkMax());
                    } catch (IOException ex) {
                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    for(Vols vol : listeVol) {
                        listeVolPourCarte.add(vol);
                    }
                    
                    listeColisionVol.clear();
                    colision.setNbColisions(0);
                    listeVol.clear();
                    Vols.setNbVols(0);
                    
                    
                }

                StringBuilder fichierErronneMessage = new StringBuilder();
                
                for (String message: listeFichierErrone) {
                    fichierErronneMessage.append(message).append("\n");
                }
                if (!fichierErronneMessage.toString().isEmpty()){
                    JOptionPane.showMessageDialog(Fenetre.this, fichierErronneMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                
                System.out.println("------------------");
                ArrayList<String> listPathFileUpdated;

                listPathFileUpdated = (ArrayList<String>) lecteurTxt.getlistDernierFichierMAJ();


                
                ArrayList<String> listNomFichier = new ArrayList<>();

                
                for (String dernierFichier : listPathFileUpdated) {
                    String[] separation = dernierFichier.split("\\\\");
                    listNomFichier.add(separation[separation.length-1]);
                    
                }
                
                // Coloration du ou des graphes
                List<Graph> graphes = ChargerGraphe.charger_graphes(listPathFileUpdated);
                lecteurTxt = new EcrireDansTxt();
                try {
                    
                    lecteurTxt.ecrireDansFichierResultatColoration(graphes);
                    listLastColoFileUpdates = lecteurTxt.getlistDernierColoFileMAJ();
                    listLastGraphColo = lecteurTxt.getListDernierGraphColoMAJ();
                    
                } catch (IOException ex) {
                    System.out.println("erreur");
                }
                
                Fenetre.this.afficherGraphes(graphes, listNomFichier);
                    
                System.out.println("-------------------------------------------------------------------------");
                
        }
        });

        btExporter.addActionListener(new ActionListener() {
            /**
             * Action à réaliser lors du clic sur le bouton "Exporter".
             * Ouvre une fenêtre de dialogue pour sélectionner le dosser de destination du fichier csv de coloration
             * @author tom/thomas
             * @param e l'événement de clic
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                
                int option = fileChooser.showSaveDialog(Fenetre.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File dossier = fileChooser.getSelectedFile();
                    System.out.println(dossier.getAbsolutePath());
                    
                    // Exporter les résultats dans le fichier
                    EcrireDansCSV csvWriter = new EcrireDansCSV(listLastColoFileUpdates,listLastGraphColo,Fenetre.this);
                    
                    try {
                        csvWriter.EcrireDernierCSVFichierColo(dossier.getAbsolutePath()+ "\\\\");
                    } catch (IOException ex) {
                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        
        
    }
    
    /**
    * Affiche les graphes à partir des listes spécifiées et affiche le graphique courant.
    * 
    * @author tom
    * @param graphes Liste des graphes à afficher
    * @param fileNames Liste des noms de fichiers associés aux graphes
    */
    public void afficherGraphes(List<Graph> graphes, List<String> fileNames) {
        this.graphes = graphes;
        this.nomFichiers = fileNames;
        afficherGraphiqueCourant();
    }
    
    private String getNomFichierDepuisUnGraph(int index) {
        
        return nomFichiers.get(index);
        
    }

    /**
    * Affiche le graphique courant dans le panneau graphique.
    * Supprime tous les composants graphiques actuellement présents dans graphPanel et ajoute FenetreGraphe correspondant.
    * Met à jour l'indice du graphique dans FenetreGraphe et gère les actions des boutons et champs de texte associés.
    * Affiche également les informations dans la console.
    * 
    * @author tom
    */
    public void afficherGraphiqueCourant() {
        // Supprimer tous les composants graphiques actuellement présents dans graphPanel
        graphPanel.removeAll();

        // Create an instance of FenetreGraphe with the current graph and the file name
        String nomFichier = getNomFichierDepuisUnGraph(indiceGraphCourant);
        FenetreGraphe fenetreGraph = new FenetreGraphe(graphes.get(indiceGraphCourant), this, nomFichier); // Pass the file name

        // Mettre à jour l'indice du graphique dans fenetreGraph
        fenetreGraph.setIndiceGraph(indiceGraphCourant + 1); // Les indices des graphiques commencent à 1 pour l'utilisateur

        // Ajouter FenetreGraph à graphPanel
        graphPanel.add(fenetreGraph);

        // Rendre FenetreGraph visible
        fenetreGraph.setVisible(true);

        try {
            // Essayer de définir la taille maximale de FenetreGraph
            fenetreGraph.setMaximum(true);
        } catch (Exception e) {
            // Gérer toute exception qui pourrait survenir lors de la définition de la taille maximale
            System.err.println(e.getMessage());
        }

        // Valider et rafraîchir graphPanel pour refléter les changements
        graphPanel.revalidate();
        graphPanel.repaint();
        
        
       
        new InfosConsole(graphes.get(indiceGraphCourant));
        
        
        
        // Ajouter un ActionListener pour le champ txtGraphNumber dans FenetreGraph
        fenetreGraph.addIndiceTxtListener(new ActionListener() {
            /**
            * Ajuste l'indice du graph courant
            * @author tom
            * @param e l'événement de clic
            */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer le texte saisi dans le JTextField
                int inputText = fenetreGraph.getNumGraph();
                try {
                    int newIndice = inputText - 1; // Soustraire 1 car les indices commencent à 0
                    
                    // Vérifier si l'indice est valide
                    if (newIndice >= 0 && newIndice < graphes.size()) {
                        indiceGraphCourant = newIndice;
                        // Vérifier si FenetreGraph est en mode plein écran et le restaurer si nécessaire
                        if (fenetreGraph.isFullScreen()) {
                            fenetreGraph.restaurerFenetre();
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
            /**
            * permet de passer au graph precedent
            * @author tom
            * @param e l'événement de clic
            */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceGraphCourant > 0) {
                    // Décrémenter l'index du graphique courant
                    indiceGraphCourant--;

                    // Vérifier si FenetreGraph est en mode plein écran et le restaurer si nécessaire
                    if (fenetreGraph.isFullScreen()) {
                        fenetreGraph.restaurerFenetre();
                    }

                    // Afficher le graphique courant
                    afficherGraphiqueCourant();
                }
            }
        });

        // Ajouter un ActionListener pour le bouton Suivant de FenetreGraph
        fenetreGraph.addSuivButtonListener(new ActionListener() {
            /**
            * permet de passer au graph suivant
            * @author tom
            * @param e l'événement de clic
            */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceGraphCourant < graphes.size() - 1) {
                    // Incrémenter l'index du graphique courant
                    indiceGraphCourant++;

                    // Vérifier si FenetreGraph est en mode plein écran et le restaurer si nécessaire
                    if (fenetreGraph.isFullScreen()) {
                        fenetreGraph.restaurerFenetre();
                    }

                    // Afficher le graphique courant
                    afficherGraphiqueCourant();
                }
            }
        });
    }
    
    /**
    * Renvoie le composant JTextPane utilisé pour afficher les informations dans la console.
    * 
    * @author tom
    */
    public static JTextPane getTxtConsole() {
        return txtConsole;
    }
    
    /**
    * Renvoie le dernier fichier de mise à jour des collisions.
    * 
    * @author tom
    */
    public String getListLastColoFileUpdates() {
        //return listLastColoFileUpdates.getLast();
        return listLastColoFileUpdates.get(listLastColoFileUpdates.size()-1);
    }
}

