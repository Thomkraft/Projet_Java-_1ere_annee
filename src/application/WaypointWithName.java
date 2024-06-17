package application;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class WaypointWithName extends DefaultWaypoint {
    private final String name;

    public WaypointWithName(GeoPosition coord, String name) {
        super(coord);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
