package interfaceApplication;

import application.NomWaypoint;
import stockageDonnees.Vols;
import application.Aeroports;
import application.VolPainter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;
import org.jxmapviewer.painter.CompoundPainter;

/**
 * Classe représentant une fenêtre affichant une carte avec des aéroports et des vols.
 * Utilise JXMapViewer pour la visualisation cartographique et permet de visualiser les vols
 * en fonction des aéroports et des niveaux sélectionnés.
 * @author tom
 */
public class Carte extends JFrame {
    private JXMapViewer mapViewer;
    private VolPainter volPainter;
    private final JMenuItem item1 = new JMenuItem("Aeroport -> niveau des vols");
    private final JMenuItem item2 = new JMenuItem("niveau -> Lister les vols");
    private final List<Vols> listeVol;
    /**
     * Constructeur de la classe Carte.
     * Initialise la carte avec les aéroports, les vols et les options de visualisation.
     *
     * @param cheminAeroports Chemin vers le fichier des aéroports.
     * @param cheminTxtColo   Chemin vers le fichier de texte coloré.
     * @param listeVol Liste des vols à afficher.
     * @author tom
     */
    public Carte(String cheminAeroports, String cheminTxtColo, List<Vols> listeVol) {
        this.listeVol = listeVol;
        init(cheminAeroports, cheminTxtColo);
    }

    /**
     * Initialise la carte avec les composants graphiques et les écouteurs nécessaires.
     * @author tom
     * @param cheminAeroports Chemin vers le fichier des aéroports.
     * @param cheminTxtColo   Chemin vers le fichier de texte coloré.
     */
    private void init(String cheminAeroports, String cheminTxtColo) {
        mapViewer = new JXMapViewer();
        volPainter = new VolPainter();

        // Menu
        JMenu Outils = new JMenu("Outils");
        Outils.add(item1);
        Outils.add(item2);
        JMenuBar jmb = new JMenuBar();
        jmb.add(Outils);
        this.setJMenuBar(jmb);

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Récupérer les waypoints depuis Aéroports
        List<NomWaypoint> waypoints = Aeroports.createWaypoints(cheminAeroports);

        // Créer un WaypointPainter et ajouter les waypoints
        WaypointPainter<NomWaypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(Set.copyOf(waypoints));

        // Créer un CompoundPainter avec le waypointPainter et le painter des trajectoires de vol
        mapViewer.setOverlayPainter(new CompoundPainter<>(waypointPainter, volPainter.getPainter()));

        // Ajouter des écouteurs pour la navigation à la souris
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                GeoPosition currentPosition = mapViewer.convertPointToGeoPosition(e.getPoint());
                mapViewer.setAddressLocation(currentPosition);
            }
        });

        getContentPane().add(mapViewer);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        // Action listener pour item1 (Aeroport -> niveau des vols)
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cmd = 1;
                Visualisation visualisationFrame = new Visualisation(Aeroports.lireAeroports(cheminAeroports), cheminAeroports, cheminTxtColo, listeVol, cmd);
                visualisationFrame.setVisible(true);
            }
        });

        // Action listener pour item2 (niveau -> Lister les vols)
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cmd = 2;
                Visualisation visualisationFrame = new Visualisation(Aeroports.lireAeroports(cheminAeroports), cheminAeroports, cheminTxtColo, listeVol, cmd);
                visualisationFrame.setVisible(true);
            }
        });
    }

    /**
     * Affiche la carte avec un vol prédéfini entre deux positions géographiques.
     * Utilise un FlightPainter pour dessiner le vol et centre la carte sur la trajectoire.
     * Ajuste également le niveau de zoom pour afficher correctement les points.
     * @author tom
     */
    public void afficherCarteAvecVolPredefini() {
        GeoPosition start = new GeoPosition(48.856613, 2.352222); // Paris
        GeoPosition end = new GeoPosition(45.75, 4.85); // Lyon

        // Calculer le centre géographique entre les deux points
        GeoPosition center = calculerCentre(start, end);

        // Ajouter le vol prédéfini en utilisant le nouveau FlightPainter
        volPainter.setFlight(start, end);

        // Centrer la carte sur le centre calculé
        mapViewer.setAddressLocation(center);

        // Ajuster le niveau de zoom pour voir les deux points
        mapViewer.setZoom(13); // Réglez le zoom approprié pour voir la France

        mapViewer.repaint();
    }

    /**
     * Calcule le centre géographique entre deux positions géographiques.
     * @author tom
     * @param start Position géographique de départ.
     * @param end   Position géographique de fin.
     * @return Le centre géographique entre les deux positions.
     */
    private GeoPosition calculerCentre(GeoPosition start, GeoPosition end) {
        double lat1 = Math.toRadians(start.getLatitude());
        double lon1 = Math.toRadians(start.getLongitude());
        double lat2 = Math.toRadians(end.getLatitude());
        double lon2 = Math.toRadians(end.getLongitude());

        double bx = Math.cos(lat2) * Math.cos(lon2 - lon1);
        double by = Math.cos(lat2) * Math.sin(lon2 - lon1);

        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2),
                Math.sqrt((Math.cos(lat1) + bx) * (Math.cos(lat1) + bx) + by * by));
        double lon3 = lon1 + Math.atan2(by, Math.cos(lat1) + bx);

        return new GeoPosition(Math.toDegrees(lat3), Math.toDegrees(lon3));
    }
}
