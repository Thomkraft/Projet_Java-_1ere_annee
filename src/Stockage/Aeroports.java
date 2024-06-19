/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Stockage;

/**
 *
 * @author thoma
 * 
 */
public class Aeroports {
    private String codeAeroport;
    private String lieu;
    private double x;
    private double y;
    
    public Aeroports(String codeAeroport, String lieu, String latitude, String longitude) {
        this.codeAeroport = codeAeroport;
        this.lieu = lieu;
        
        double valeurLatitude = (double) Math.toRadians(intoDecimaux(latitude));
        double valeurLongitude = (double) Math.toRadians(intoDecimaux(longitude));
        
        this.x = (6371 * Math.cos(valeurLatitude) * Math.sin(valeurLongitude));
        this.y = (6371 * Math.cos(valeurLatitude) * Math.cos(valeurLongitude));
    }

    public Aeroports() {
        
    }
    
    @Override
    public String toString() {
        return "Aeroport :" +
                "codeAeroport ='" + codeAeroport + '\'' +
                ", Lieu ='" + lieu + '\'' +
                ", coord x ='" + x + '\'' +
                ", coord y =" + y;
    }

    public String getCodeAeroport() {
        return codeAeroport;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
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
