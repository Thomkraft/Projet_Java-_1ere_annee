/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stockageDonnees;

/**
 * Classe représentant un vol avec son nom, son heure de départ, sa durée, et les aéroports de départ et d'arrivée
 * 
 * @author thomas
 */
public class Vols {
    public static int nbVols = 0;
    
    private final String nomVol;
    private final int heureDepart;
    private final int minutesDepart;
    private final int duree;
    private final String aeroportDepart;
    private final String aeroportArrivee;
    private int niveau;

    /**
     * Constructeur de la classe Vols.
     *
     * @param nomVol Le nom du vol
     * @param aeroportDepart L'aéroport de départ
     * @param aeroportArrivee L'aéroport d'arrivée
     * @param heureDepart L'heure de départ
     * @param minutesDepart Les minutes de départ
     * @param duree La durée du vol en minutes
     */
    public Vols(String nomVol, String aeroportDepart, String aeroportArrivee, int heureDepart, int minutesDepart, int duree, int niveau) {
        this.nomVol = nomVol;
        this.heureDepart = heureDepart;
        this.minutesDepart = minutesDepart;
        this.duree = duree;
        this.aeroportDepart = aeroportDepart;
        this.aeroportArrivee = aeroportArrivee;
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
                ", aeroportDepart='" + aeroportDepart + '\'' +
                ", aeroportArrivee='" + aeroportArrivee + '\'' +
                ", heureDepart=" + heureDepart +
                ", minutesDepart=" + minutesDepart +
                ", duree=" + duree + '\'' +
                "niveau=" + niveau;
        
    }

    /**
     * Retourne l'aéroport de départ du vol.
     *
     * @author thomas
     * 
     * @return L'aéroport de départ
     */
    public String getAeroportDepart() {
        return aeroportDepart;
    }

    /**
     * Retourne l'aéroport d'arrivée du vol.
     *
     * @author thomas
     * 
     * @return L'aéroport d'arrivée
     */
    public String getAeroportArrivee() {
        return aeroportArrivee;
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
    public int getDuree() {
        return duree;
    }

    /**
     * Retourne l'heure de départ du vol.
     *
     * @author thomas
     * 
     * @return L'heure de départ
     */
    public int getHeureDepart() {
        return heureDepart;
    }

    /**
     * Retourne les minutes de départ du vol.
     *
     * @author thomas
     * 
     * @return Les minutes de départ
     */
    public int getMinutesDepart() {
        return minutesDepart;
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
