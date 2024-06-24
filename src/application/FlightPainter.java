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
 *
 * @author tom
 *  
 */
public class FlightPainter {
    private List<FlightPath> flightPaths;

    public FlightPainter() {
        flightPaths = new ArrayList<>();
    }

    public void setFlight(GeoPosition start, GeoPosition end) {
        flightPaths.clear(); // Effacer les trajets précédents

        FlightPath flightPath = new FlightPath(start, end);
        flightPaths.add(flightPath);
    }

    public List<FlightPath> getFlightPaths() {
        return flightPaths;
    }

    public Painter<JXMapViewer> getPainter() {
        return new Painter<JXMapViewer>() {
            @Override
            public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set the color and stroke for the flight path
                g.setColor(Color.RED);
                g.setStroke(new BasicStroke(2));

                for (FlightPath flightPath : flightPaths) {
                    Path2D path = new Path2D.Double();
                    boolean first = true;

                    for (GeoPosition geo : flightPath.getWaypoints()) {
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
