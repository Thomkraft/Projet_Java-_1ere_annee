package application;

import org.jxmapviewer.viewer.GeoPosition;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tom
 * 
 */
public class FlightPath {
    private List<GeoPosition> waypoints;

    public FlightPath(GeoPosition start, GeoPosition end) {
        this.waypoints = new ArrayList<>();
        waypoints.add(start);
        waypoints.add(end);
    }

    public List<GeoPosition> getWaypoints() {
        return waypoints;
    }

    public void addWaypoint(GeoPosition waypoint) {
        waypoints.add(waypoint);
    }
}
