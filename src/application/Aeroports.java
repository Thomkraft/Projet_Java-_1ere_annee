
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
public class Aeroports {

    /**
     * Crée une liste de points de passage (waypoints) à partir du fichier spécifié.
     * Chaque waypoint contient une position géographique (latitude, longitude) et un nom d'aéroport.
     * 
     * @param cheminFichier Le chemin vers le fichier contenant les données des aéroports
     * @return Une liste de WaypointWithName contenant les positions et noms des aéroports
     */
    public static List<NomWaypoint> createWaypoints(String cheminFichier) {
        List<NomWaypoint> waypoints = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length >= 10) {
                    double latitude = parseCoordinate(parts[2], parts[3], parts[4], parts[5]);
                    double longitude = parseCoordinate(parts[6], parts[7], parts[8], parts[9]);
                    String nomAeroport = parts[1];
                    NomWaypoint waypoint = new NomWaypoint(new GeoPosition(latitude, longitude), nomAeroport);
                    waypoints.add(waypoint);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return waypoints;
    }

    /**
     * Parse les coordonnées géographiques (latitude ou longitude) à partir des degrés, minutes, secondes et direction.
     * 
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
        double coordonnée = degrees + (minutes / 60) + (seconds / 3600);
        if (direction.equals("S") || direction.equals("O")) {
            coordonnée = -coordonnée;
        }
        return coordonnée;
    }
    
    /**
     * Lit les noms et les codes des aéroports à partir du fichier spécifié et les retourne sous forme de tableau à double dimension.
     * 
     * @param cheminFichier Le chemin vers le fichier contenant les noms et les codes des aéroports
     * @return Un tableau à double dimension contenant les codes et les noms des aéroports
     */
    public static List<String[]> lireAeroports(String cheminFichier) {
        List<String[]> listeAeroport = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(";");
                if (parties.length >= 2) {
                    String codeAeroport = parties[0];
                    String nomAeroport = parties[1];
                    listeAeroport.add(new String[] { codeAeroport, nomAeroport });
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return listeAeroport;
    }

}
