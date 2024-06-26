/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stockageDonnées;

/**
 * Classe représentant un vol avec son nom, son heure de départ, sa durée, et les aéroports de départ et d'arrivée
 * 
 * @author thomas
 */
public class Vols {
    public static int nbVols = 0;
    
    private String nomVol;
    private int heureDépart;
    private int minutesDépart;
    private int durrée;
    private String aéroportDépart;
    private String aéroportArrivée;
    private int niveau;

    /**
     * Constructeur de la classe Vols.
     *
     * @param nomVol Le nom du vol
     * @param aéroportDépart L'aéroport de départ
     * @param aéroportArrivée L'aéroport d'arrivée
     * @param heureDépart L'heure de départ
     * @param minutesDépart Les minutes de départ
     * @param durrée La durée du vol en minutes
     */
    public Vols(String nomVol, String aéroportDépart, String aéroportArrivée, int heureDépart, int minutesDépart, int durrée, int niveau) {
        this.nomVol = nomVol;
        this.heureDépart = heureDépart;
        this.minutesDépart = minutesDépart;
        this.durrée = durrée;
        this.aéroportDépart = aéroportDépart;
        this.aéroportArrivée = aéroportArrivée;
        this.niveau = niveau;
        nbVols++;
    }
    
    /**
     * Retourne une représentation sous forme de chaîne de caractères du vol.
     *
     * @author thomas
     * 
     * @return Une chaîne de caractères représentant le vol
     */
    @Override
    public String toString() {
        return "Vol : " +
                "nomVol='" + nomVol + '\'' +
                ", aeroportDepart='" + aéroportDépart + '\'' +
                ", aeroportArrivee='" + aéroportArrivée + '\'' +
                ", heureDepart=" + heureDépart +
                ", minutesDepart=" + minutesDépart +
                ", duree=" + durrée + '\'' +
                "niveau=" + niveau;
        
    }

    /**
     * Retourne l'aéroport de départ du vol.
     *
     * @author thomas
     * 
     * @return L'aéroport de départ
     */
    public String getAéroportDépart() {
        return aéroportDépart;
    }

    /**
     * Retourne l'aéroport d'arrivée du vol.
     *
     * @author thomas
     * 
     * @return L'aéroport d'arrivée
     */
    public String getAéroportArrivée() {
        return aéroportArrivée;
    }

    /**
     * Retourne le nom du vol.
     *
     * @author thomas
     * 
     * @return Le nom du vol
     */
    public String getNomVol() {
        return nomVol;
    }

    /**
     * Retourne la durée du vol en minutes.
     *
     * @author thomas
     * 
     * @return La durée du vol
     */
    public int getDurrée() {
        return durrée;
    }

    /**
     * Retourne l'heure de départ du vol.
     *
     * @author thomas
     * 
     * @return L'heure de départ
     */
    public int getHeureDépart() {
        return heureDépart;
    }

    /**
     * Retourne les minutes de départ du vol.
     *
     * @author thomas
     * 
     * @return Les minutes de départ
     */
    public int getMinutesDépart() {
        return minutesDépart;
    }

    /**
     * Définit le nombre de vols.
     *
     * @author thomas
     * 
     * @param nbVols Le nombre de vols
     */
    public static void setNbVols(int nbVols) {
        Vols.nbVols = nbVols;
    }
    
    /**
     * Retourne le niveau du vol
     *
     * @author tom
     */
    public int getNiveau() {
        return niveau;
    }

    /**
     * Définit le niveau du vol.
     *
     * @author tom
     * 
     * @param niveau Le niveau du vols
     */
    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }
    
    
}
