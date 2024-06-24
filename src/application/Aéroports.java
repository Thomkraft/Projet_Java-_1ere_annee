package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jxmapviewer.viewer.GeoPosition;


/**
 * La classe Aéroports contient des méthodes pour la manipulation des aéroports.
 * Elle permet de créer des points de passage (waypoints), de trouver la position d'un aéroport par son nom,
 * et de lire les noms des aéroports à partir d'un fichier spécifié.
 * Les coordonnées des aéroports sont parsées à partir des degrés, minutes, secondes et direction.
 * 
 * @author tom
 * @version 1.0
 */
public class Aéroports {
    /**
     * Crée une liste de points de passage (waypoints) à partir du fichier spécifié.
     * Chaque waypoint contient une position géographique (latitude, longitude) et un nom d'aéroport.
     * @author tom
     * @param filePath Le chemin vers le fichier contenant les données des aéroports
     * @return Une liste de WaypointWithName contenant les positions et noms des aéroports
     */
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

    /**
     * Parse les coordonnées géographiques (latitude ou longitude) à partir des degrés, minutes, secondes et direction.
     * @author tom
     * @param degreeStr Chaîne représentant les degrés
     * @param minuteStr Chaîne représentant les minutes
     * @param secondStr Chaîne représentant les secondes
     * @param direction Direction (N, S, E, O) indiquant si la latitude ou longitude est positive ou négative
     * @return La coordonnée géographique parsée sous forme de double
     */
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
    
    /**
     * Recherche et retourne la position géographique d'un aéroport par son nom dans la liste de waypoints spécifiée.
     * @author tom
     * @param airportName Le nom de l'aéroport à rechercher
     * @param waypoints La liste de WaypointWithName contenant les positions et noms des aéroports
     * @return La position géographique (GeoPosition) de l'aéroport, ou null si non trouvé
     */
    public static GeoPosition findAirportPositionByName(String airportName, List<WaypointWithName> waypoints) {
        for (WaypointWithName waypoint : waypoints) {
            if (waypoint.getName().equalsIgnoreCase(airportName)) {
                return waypoint.getPosition();
            }
        }
        return null;
    }
    
    /**
     * Lit les noms des aéroports à partir du fichier spécifié et les retourne sous forme de liste de chaînes.
     * @author tom
     * @param cheminFichier Le chemin vers le fichier contenant les noms des aéroports
     * @return Une liste de chaînes contenant les noms des aéroports lus depuis le fichier
     */
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
