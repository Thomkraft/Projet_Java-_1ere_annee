package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author tom
 * 
 */
public class Aéroports {
    public static List<WaypointWithName> createWaypoints(String filePath) {
        List<WaypointWithName> waypoints = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 10) {
                    double latitude = parseCoordinate(parts[2], parts[3], parts[4], parts[5]);
                    double longitude = parseCoordinate(parts[6], parts[7], parts[8], parts[9]);
                    String airportName = parts[1];
                    WaypointWithName waypoint = new WaypointWithName(new GeoPosition(latitude, longitude), airportName);
                    waypoints.add(waypoint);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return waypoints;
    }

    private static double parseCoordinate(String degreeStr, String minuteStr, String secondStr, String direction) {
        double degrees = Double.parseDouble(degreeStr);
        double minutes = Double.parseDouble(minuteStr);
        double seconds = Double.parseDouble(secondStr);
        double coordinate = degrees + (minutes / 60) + (seconds / 3600);
        if (direction.equals("S") || direction.equals("O")) {
            coordinate = -coordinate;
        }
        return coordinate;
    }
    
    public static GeoPosition findAirportPositionByName(String airportName, List<WaypointWithName> waypoints) {
        for (WaypointWithName waypoint : waypoints) {
            if (waypoint.getName().equalsIgnoreCase(airportName)) {
                return waypoint.getPosition();
            }
        }
        return null;
    }
    
    public static List<String> lireNomsAeroports(String cheminFichier) {
        List<String> nomsAeroports = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(";");
                if (parties.length >= 2) {
                    String nomAeroport = parties[1];
                    nomsAeroports.add(nomAeroport);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nomsAeroports;
    }
}
