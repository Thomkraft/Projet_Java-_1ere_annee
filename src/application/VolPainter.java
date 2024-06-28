package application;

import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.painter.Painter;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.jxmapviewer.JXMapViewer;

/**
 * La classe FlightPainter est un Painter utilisé pour dessiner les trajets de vol
 * sur une carte JXMapViewer. Elle utilise une liste de FlightPath pour stocker
 * les trajets et fournit une méthode paint pour dessiner ces trajets sur la carte.
 * 
 * @author tom
 * @version 1.0
 */
public class VolPainter {
    private final List<CheminVol> cheminVols;

    /**
     * Constructeur par défaut qui initialise la liste des trajets de vol.
     * @author tom
     */
    public VolPainter() {
        cheminVols = new ArrayList<>();
    }

    /**
     * Définit le trajet de vol entre deux positions géographiques (départ et arrivée).
     * Efface tous les trajets précédents et ajoute un nouveau trajet.
     * @author tom
     * @param start La position géographique de départ du vol
     * @param end La position géographique d'arrivée du vol
     */
    public void setVol(GeoPosition start, GeoPosition end) {
        cheminVols.clear(); // Effacer les trajets précédents

        CheminVol cheminVol = new CheminVol(start, end);
        cheminVols.add(cheminVol);
    }

    /**
     * Récupère la liste des trajets de vol actuellement définis.
     * @author tom
     * @return Une liste de FlightPath représentant les trajets de vol
     */
    public List<CheminVol> getCheminVols() {
        return cheminVols;
    }

    /**
     * Retourne un PainterJXMapViewer utilisé pour dessiner les trajets de vol sur la carte.
     * @author tom
     * @return Un PainterJXMapViewer pour dessiner les trajets de vol
     */
    public Painter<JXMapViewer> getPainter() {
        return new Painter<JXMapViewer>() {
            @Override
            public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set the color and stroke for the flight path
                g.setColor(Color.RED);
                g.setStroke(new BasicStroke(2));

                for (CheminVol cheminVol : cheminVols) {
                    Path2D path = new Path2D.Double();
                    boolean first = true;

                    for (GeoPosition geo : cheminVol.getWaypoints()) {
                        Point2D pt = map.getTileFactory().geoToPixel(geo, map.getZoom());

                        if (first) {
                            path.moveTo(pt.getX(), pt.getY());
                            first = false;
                        } else {
                            path.lineTo(pt.getX(), pt.getY());
                        }
                    }

                    g.draw(path);
                }
            }
        };
    }
}
