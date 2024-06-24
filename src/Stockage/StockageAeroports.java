/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Stockage;

/**
 * Classe représentant un aéroport avec son code, son lieu et ses coordonnées géographiques.
 * 
 * @author thomas
 */
public class StockageAeroports {
    private String codeAeroport;
    private String lieu;
    private double x;
    private double y;
    
    /**
     * Constructeur de la classe StockageAeroports.
     *
     * @author thomas
     * 
     * @param codeAeroport Le code de l'aéroport
     * @param lieu Le lieu de l'aéroport
     * @param latitude La latitude de l'aéroport
     * @param longitude La longitude de l'aéroport
     */
    public StockageAeroports(String codeAeroport, String lieu, String latitude, String longitude) {
        this.codeAeroport = codeAeroport;
        this.lieu = lieu;
        
        double valeurLatitude = (double) Math.toRadians(intoDecimaux(latitude));
        double valeurLongitude = (double) Math.toRadians(intoDecimaux(longitude));
        
        this.x = (6371 * Math.cos(valeurLatitude) * Math.sin(valeurLongitude));
        this.y = (6371 * Math.cos(valeurLatitude) * Math.cos(valeurLongitude));
    }
    
    /**
     * Constructeur par défaut de la classe StockageAeroports.
     * 
     * @author thomas
     */
    public StockageAeroports() {
        
    }
    
    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'aéroport
     *
     * @author thomas
     * 
     * @return Une chaîne de caractères représentant l'aéroport
     */
    @Override
    public String toString() {
        return "Aeroport :" +
                "codeAeroport ='" + codeAeroport + '\'' +
                ", Lieu ='" + lieu + '\'' +
                ", coord x ='" + x + '\'' +
                ", coord y =" + y;
    }

    /**
     * Retourne le code de l'aéroport.
     *
     * @author thomas
     * 
     * @return Le code de l'aéroport
     */
    public String getCodeAeroport() {
        return codeAeroport;
    }

    /**
     * Retourne la coordonnée x de l'aéroport.
     *
     * @author thomas
     * 
     * @return La coordonnée x de l'aéroport
     */
    public double getX() {
        return x;
    }

    /**
     * Retourne la coordonnée y de l'aéroport.
     *
     * @author thomas
     * 
     * @return La coordonnée y de l'aéroport
     */
    public double getY() {
        return y;
    }
    
    /**
     * Retourne le lieu de l'aéroport.
     *
     * @author thomas
     * 
     * @return Le lieu de l'aéroport
     */
    public String getNom() {
        return lieu;
    }

    private double intoDecimaux(String value){
        int coef;
        
        String[] valueSplit = value.split(";");
        if(valueSplit[3].equals("N") || valueSplit[3].equals("E")){
            coef = 1;
        } else {
            coef = -1;
        }
        
        return (coef*(Double.parseDouble(valueSplit[0]) + Double.parseDouble(valueSplit[1])/60 + Double.parseDouble(valueSplit[2])/3600));
    }
    
    
}
