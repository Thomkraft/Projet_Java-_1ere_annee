package Interface;

import application.FlightPainter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.CompoundPainter;

import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;

import application.Aéroports;
import application.WaypointWithName;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Carte extends JFrame {

    private JXMapViewer mapViewer;
    private FlightPainter flightPainter;
    private List<WaypointWithName> waypoints;
    private JMenuItem item1 = new JMenuItem("Aeroport -> niveau des vols");
    private JMenuItem item2 = new JMenuItem("niveau -> Lister les vols");
    
    public Carte(String filePath) {
        init(filePath);
    }

    private void init(String filePath) {
        mapViewer = new JXMapViewer();
        flightPainter = new FlightPainter();
        
        //Menu
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
        waypoints = Aéroports.createWaypoints(filePath);

        // Créer un WaypointPainter et ajouter les waypoints
        WaypointPainter<WaypointWithName> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(Set.copyOf(waypoints));

        // Créer un CompoundPainter avec le waypointPainter et le painter des trajectoires de vol
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(waypointPainter, flightPainter.getPainter());
        mapViewer.setOverlayPainter(compoundPainter);

        // Ajouter des écouteurs pour la navigation à la souris
        PanMouseInputListener panMouseInputListener = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(panMouseInputListener);
        mapViewer.addMouseMotionListener(panMouseInputListener);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        // Écouteur pour mettre à jour la position courante lors du relâchement de la souris
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                GeoPosition currentPosition = mapViewer.getCenterPosition();
                mapViewer.setAddressLocation(currentPosition);
            }
        });

        getContentPane().add(mapViewer);
        setSize(1000, 800);
        setLocationRelativeTo(null);
    }

    public void afficherCarteAvecVolPredefini() {
        GeoPosition start = new GeoPosition(48.856613, 2.352222); // Paris
        GeoPosition end = new GeoPosition(45.75, 4.85); // Lyon

        // Calculer le centre géographique entre les deux points
        GeoPosition center = calculateCenter(start, end);

        // Ajouter le vol prédéfini en utilisant le nouveau FlightPainter
        flightPainter.setFlight(start, end);

        // Centrer la carte sur le centre calculé
        mapViewer.setAddressLocation(center);

        // Ajuster le niveau de zoom pour voir les deux points
        mapViewer.setZoom(13); // Réglez le zoom approprié pour voir la France

        mapViewer.repaint();
    }

    private GeoPosition calculateCenter(GeoPosition start, GeoPosition end) {
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
