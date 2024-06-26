package application;

import org.jxmapviewer.viewer.GeoPosition;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe FlightPath représente un trajet de vol entre deux positions géographiques (waypoints).
 * Elle stocke une liste de GeoPosition représentant les points de passage du trajet.
 * 
 * @author tom
 * @version 1.0
 */
public class CheminVol {
    private final List<GeoPosition> waypoints;

    /**
     * Constructeur initialisant le trajet de vol avec deux points de passage (départ et arrivée).
     * 
     * @param start La position géographique de départ du trajet
     * @param end La position géographique d'arrivée du trajet
     */
    public CheminVol(GeoPosition start, GeoPosition end) {
        this.waypoints = new ArrayList<>();
        waypoints.add(start);
        waypoints.add(end);
    }

    /**
     * Retourne la liste des points de passage (waypoints) du trajet de vol.
     * 
     * @return Une liste de GeoPosition représentant les points de passage du trajet
     */
    public List<GeoPosition> getWaypoints() {
        return waypoints;
    }
}
