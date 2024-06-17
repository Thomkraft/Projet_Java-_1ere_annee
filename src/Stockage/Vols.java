/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Stockage;

/**
 *
 * @author thoma
 */
public class Vols {
    public static int nbVols = 0;
    
    private String nomVol;
    private int heureDépart;
    private int minutesDépart;
    private int durrée;
    private String aéroportDépart;
    private String aéroportArrivée;

    public Vols(String nomVol, String aéroportDépart, String aéroportArrivée, int heureDépart, int minutesDépart, int durrée) {
        this.nomVol = nomVol;
        this.heureDépart = heureDépart;
        this.minutesDépart = minutesDépart;
        this.durrée = durrée;
        this.aéroportDépart = aéroportDépart;
        this.aéroportArrivée = aéroportArrivée;
        nbVols++;
    }
    
    
    @Override
    public String toString() {
        return "Vol : " +
                "nomVol='" + nomVol + '\'' +
                ", aeroportDepart='" + aéroportDépart + '\'' +
                ", aeroportArrivee='" + aéroportArrivée + '\'' +
                ", heureDepart=" + heureDépart +
                ", minutesDepart=" + minutesDépart +
                ", duree=" + durrée;
    }

    public String getAéroportDépart() {
        return aéroportDépart;
    }

    public String getAéroportArrivée() {
        return aéroportArrivée;
    }

    public String getNomVol() {
        return nomVol;
    }

    public int getDurrée() {
        return durrée;
    }

    public int getHeureDépart() {
        return heureDépart;
    }

    public int getMinutesDépart() {
        return minutesDépart;
    }

    public static void setNbVols(int nbVols) {
        Vols.nbVols = nbVols;
    }
    
    
}
