/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stockageDonnees;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe représentant les collisions entre vols et calculant si deux vols se croisent potentiellement
 * 
 * @author thomas
 */
public class Colisions {
    private StockageAeroports aeroportDepartVol1;
    private StockageAeroports aeroportArriveVol1;
    private StockageAeroports aeroportDepartVol2;
    private StockageAeroports aeroportArriveVol2;
    private double coefDirecteurVol1;
    private double coefDirecteurVol2;
    private int marge = 15;
    
    public int nbColisions = 0;
    
    /**
     * Détermine si deux vols se croisent en utilisant leurs coordonnées.
     *
     * @author thomas
     * 
     * @param vol1 Le premier vol
     * @param vol2 Le deuxième vol
     * @param listeAeroports La liste des aéroports
     * @return true si les vols se croisent, false sinon
     */
    public boolean getCoordColision(Vols vol1,Vols vol2,List<StockageAeroports> listeAeroports){
        
        //stockage des vols
        StockageAeroports[] listeAeroportVol1 = getAeroport(vol1, listeAeroports);
        StockageAeroports[] listeAeroportVol2 = getAeroport(vol2, listeAeroports);
        
            
        //stock l'aéroport de départ du vol 1
        this.aeroportDepartVol1 = listeAeroportVol1[0];
        //stock l'aéroport d'arrivé du vol 1x
        this.aeroportArriveVol1 = listeAeroportVol1[1];
        //de meme pour le vol 2
        this.aeroportDepartVol2 = listeAeroportVol2[0];
        this.aeroportArriveVol2 = listeAeroportVol2[1];
        
        //calcul du coef directeur des 2 droites
        this.coefDirecteurVol1 = (aeroportArriveVol1.getY()-aeroportDepartVol1.getY())/(aeroportArriveVol1.getX()-aeroportDepartVol1.getX());  
        this.coefDirecteurVol2 = (aeroportArriveVol2.getY()-aeroportDepartVol2.getY())/(aeroportArriveVol2.getX()-aeroportDepartVol2.getX());
        
        if(isColineaire(vol1, vol2)){
            int heureDepartVol1EnMinutes = vol1.getHeureDepart()*60 + vol1.getMinutesDepart();
            int heureDepartVol2EnMinutes = vol2.getHeureDepart()*60 + vol2.getMinutesDepart();

            if(vol1.getAeroportDepart().equals(vol2.getAeroportDepart()) && vol1.getAeroportArrivee().equals(vol2.getAeroportArrivee())){
 
                if (plusDe15Minutes(heureDepartVol1EnMinutes, heureDepartVol2EnMinutes)){
                    //return new Result(false);
                    return false;
                }
                nbColisions++;
                
                //return new Result(true, new double[] {aeroportDepartVol1.getX(), aeroportDepartVol1.getY()}); 
                return true;
            } else { 

                if((heureDepartVol1EnMinutes+vol1.getDuree()+15 < heureDepartVol2EnMinutes) || (heureDepartVol2EnMinutes+vol2.getDuree() + 15 < heureDepartVol1EnMinutes)) {
                    //return new Result(false);
                    return false;
                } else {
                    nbColisions++;
                    //return new Result(true);
                    return true;
                }
            }            
        }
        
        if(isParallele() && vol1.getAeroportArrivee().equals(vol2.getAeroportDepart()) && vol1.getAeroportDepart().equals(vol2.getAeroportArrivee())){
           
           //return new Result(false);
           return false;
        }
        
        //calcul du millieu des 2 droites
        double millieuVol1 = (aeroportArriveVol1.getY() - aeroportDepartVol1.getY()) / (aeroportArriveVol1.getX() - aeroportDepartVol1.getX());
        double millieuVol2 = (aeroportArriveVol2.getY() - aeroportDepartVol2.getY()) / (aeroportArriveVol2.getX() - aeroportDepartVol2.getX());

        //calcul de l'ordonnée à l'origine des 2 droites
        double ordonneeAOrigineVol1 = aeroportDepartVol1.getY() - (millieuVol1 * aeroportDepartVol1.getX());
        double ordonneeAOrigineVol2 = aeroportDepartVol2.getY() - (millieuVol2 * aeroportDepartVol2.getX());

        //calcul des coordonnée du point d'intersection
        double coordXIntersection = (ordonneeAOrigineVol2 - ordonneeAOrigineVol1) / (millieuVol1 - millieuVol2);
        double coordYIntersection = (coefDirecteurVol1 * coordXIntersection) + ordonneeAOrigineVol1;

        //calcul de la distance entre l'aéroport de départ et le point potentiel de colision
        double distanceColisionVol1 = Math.sqrt((coordXIntersection - aeroportDepartVol1.getX())*(coordXIntersection - aeroportDepartVol1.getX())+(coordYIntersection - aeroportDepartVol1.getY())*(coordYIntersection - aeroportDepartVol1.getY()));
        double distanceColisionVol2 = Math.sqrt((coordXIntersection - aeroportDepartVol2.getX())*(coordXIntersection - aeroportDepartVol2.getX())+(coordYIntersection - aeroportDepartVol2.getY())*(coordYIntersection - aeroportDepartVol2.getY()));
        
        //calcul la distance entre laéroport de départ et d'arrivé
        double distanceInitialeVol1 = Math.sqrt((aeroportArriveVol1.getX() - aeroportDepartVol1.getX())*(aeroportArriveVol1.getX() - aeroportDepartVol1.getX())+(aeroportArriveVol1.getY() - aeroportDepartVol1.getY())*(aeroportArriveVol1.getY() - aeroportDepartVol1.getY()));
        double distanceInitialeVol2 = Math.sqrt((aeroportArriveVol2.getX() - aeroportDepartVol2.getX())*(aeroportArriveVol2.getX() - aeroportDepartVol2.getX())+(aeroportArriveVol2.getY() - aeroportDepartVol2.getY())*(aeroportArriveVol2.getY() - aeroportDepartVol2.getY()));
        
     
        //si la distance au point dintersection des droites est supérieur a la distance
        //entre les 2 aeroports du vol alors pas de colisions
        if(!(vol1.getAeroportArrivee().equals(vol2.getAeroportArrivee())) && !(vol1.getAeroportArrivee().equals(vol2.getAeroportDepart())) && !(vol1.getAeroportDepart().equals(vol2.getAeroportArrivee()))) {
            if(distanceColisionVol1 > distanceInitialeVol1 || distanceColisionVol2 > distanceInitialeVol2){
                //return new Result(false);
                return false;
            }
        }
        
        //produit en croix pour trouver le temps pour arriver aux point d'intersection
        int tempsArriverCroisementVol1 = (int) ((vol1.getDuree()*distanceColisionVol1)/distanceInitialeVol1);
        int tempsArriverCroisementVol2 = (int) ((vol2.getDuree()*distanceColisionVol2)/distanceInitialeVol2);
        

        if(tempsArriverCroisementVol1 > vol1.getDuree() || tempsArriverCroisementVol2 > vol2.getDuree()){
            //return new Result(false);
            return false;
        }
        
        //Temps total en minutes pour aller de l'aéroport de départ au point potentiel de colision
        int tempsTotal = ajoutMinutes(vol1.getHeureDepart(),vol1.getMinutesDepart() , tempsArriverCroisementVol1);
        int tempsTotal2 = ajoutMinutes(vol2.getHeureDepart(),vol2.getMinutesDepart() , tempsArriverCroisementVol2);
        
        //condition si les 2 vols ont un risque de colision ou pas ( <15min )
        if(plusDe15Minutes(tempsTotal, tempsTotal2)){
            //return new Result(false);
            return false;
        }
        this.nbColisions += 1;

        //return new Result(true, new double[] {coordXIntersection,coordYIntersection});
        return true;
    }
    
    
    private boolean isParallele(){
       return (coefDirecteurVol1 == coefDirecteurVol2);
    }
    
    private boolean isColineaire(Vols vol1,Vols vol2){
        return (vol1.getAeroportDepart().equals(vol2.getAeroportDepart()) && vol1.getAeroportArrivee().equals(vol2.getAeroportArrivee())) ||
                (vol1.getAeroportDepart().equals(vol2.getAeroportArrivee()) && vol1.getAeroportArrivee().equals(vol2.getAeroportDepart()));
    }

    
    private StockageAeroports[] getAeroport(Vols vol, List<StockageAeroports> listeAeroports) {
        StockageAeroports aeroportDepart = new StockageAeroports();
        StockageAeroports aeroportArrive = new StockageAeroports();

        for (StockageAeroports aeroport : listeAeroports) {
            if (aeroport.getCodeAeroport().equals(vol.getAeroportDepart())) {
                aeroportDepart = aeroport;      
            }
            if (aeroport.getCodeAeroport().equals(vol.getAeroportArrivee())) {
                aeroportArrive = aeroport;
            }
        }

        return new StockageAeroports[]{aeroportDepart,aeroportArrive};
    }

    private int ajoutMinutes(int heures, int minutes, int minutesAAjouter){
        int minutesTotal = (heures * 60) + minutesAAjouter + minutes;
        
        return minutesTotal;
    }
    

    private boolean plusDe15Minutes(int minutes1, int minutes2){

        
        return (Math.abs(minutes1-minutes2) >= marge);
    }

    public void setMarge(int marge) {
        this.marge = marge;
    }
    
   

    public void setNbColisions(int nbColisions) {
        this.nbColisions = nbColisions;
    }
    
    
    
    
}
