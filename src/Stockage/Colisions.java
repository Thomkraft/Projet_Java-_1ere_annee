/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Stockage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thoma
 */
public class Colisions {
    private Aeroports aeroportDepartVol1;
    private Aeroports aeroportArriveVol1;
    private Aeroports aeroportDepartVol2;
    private Aeroports aeroportArriveVol2;
    private double coefDirecteurVol1;
    private double coefDirecteurVol2;
    
    public int nbColisions = 0;
    
    public Result getCoordColision(Vols vol1,Vols vol2,List<Aeroports> listeAeroports){
        
        //aéroport de meme départ ou meme arrivé mais pas coolinéaire ?? a gérer eventuellement
        //pas besoin de retourner les coordonnées de la colision !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        
        //stockage des vols
        Aeroports[] listeAeroportVol1 = getAeroport(vol1, listeAeroports);
        Aeroports[] listeAeroportVol2 = getAeroport(vol2, listeAeroports);
        
        
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
        
        //System.out.println(coefDirecteurVol1);
        
        
        if(isColinéaire(vol1, vol2)){
            int heureDepartVol1EnMinutes = vol1.getHeureDépart()*60 + vol1.getMinutesDépart();
            int heureDepartVol2EnMinutes = vol2.getHeureDépart()*60 + vol2.getMinutesDépart();

            if(vol1.getAéroportDépart().equals(vol2.getAéroportDépart()) && vol1.getAéroportArrivée().equals(vol2.getAéroportArrivée())){
 
                if (plusDe15Minutes(heureDepartVol1EnMinutes, heureDepartVol2EnMinutes)){
                    return new Result(false);
                }
                nbColisions++;
                //System.out.println(vol1.getAéroportDépart()+"-"+vol1.getAéroportArrivée() + " en conflit avec " + vol2.getAéroportDépart()+"-"+vol2.getAéroportArrivée());
                return new Result(true, new double[] {aeroportDepartVol1.getX(), aeroportDepartVol1.getY()}); 
                
            } else { 

                if((heureDepartVol1EnMinutes+vol1.getDurrée()+15 < heureDepartVol2EnMinutes) || (heureDepartVol2EnMinutes+vol2.getDurrée()+15 < heureDepartVol1EnMinutes)) {
                    return new Result(false);
                } else {
                    nbColisions++;
                    //System.out.println("colinéaire");
                        //System.out.println("Vol1 : " + vol1.getAéroportDépart()+ "/" + vol1.getAéroportArrivée() + " Minutes : " + heureDepartVol1EnMinutes);
                        //System.out.println("Vol2 : " + vol2.getAéroportDépart()+ "/" + vol2.getAéroportArrivée() + " Minutes : " + heureDepartVol2EnMinutes);
                        //System.out.println(nbColisions);
                        //System.out.println("--------------------------------------------");
                    //System.out.println(vol1.getAéroportDépart()+"-"+vol1.getAéroportArrivée() + " en conflit avec " + vol2.getAéroportDépart()+"-"+vol2.getAéroportArrivée());
                    return new Result(true);
                }
            }            
        }
        
        
        //2 segment de vol parallele ?
        //apres le && ajouté en plus a supprimer si ca marche pas
        if(isParallele() && vol1.getAéroportArrivée().equals(vol2.getAéroportDépart()) && vol1.getAéroportDépart().equals(vol2.getAéroportArrivée())){
            //System.out.println("5");
           return new Result(false);
        }
        //double distancepoint = Point.distance(aeroportDepartVol1.getX(),aeroportDepartVol1.getY(),aeroportArriveVol1.getX(),aeroportArriveVol1.getY());

        
        //calcul du millieu des 2 droites
        double millieuVol1 = (aeroportArriveVol1.getY() - aeroportDepartVol1.getY()) / (aeroportArriveVol1.getX() - aeroportDepartVol1.getX());
        double millieuVol2 = (aeroportArriveVol2.getY() - aeroportDepartVol2.getY()) / (aeroportArriveVol2.getX() - aeroportDepartVol2.getX());

        //System.out.println("millieu " + millieuVol1);
        
        //calcul de l'ordonnée à l'origine des 2 droites
        double ordonneeAOrigineVol1 = aeroportDepartVol1.getY() - (millieuVol1 * aeroportDepartVol1.getX());
        double ordonneeAOrigineVol2 = aeroportDepartVol2.getY() - (millieuVol2 * aeroportDepartVol2.getX());

        //calcul des coordonnée du point d'intersection
        double coordXIntersection = (ordonneeAOrigineVol2 - ordonneeAOrigineVol1) / (millieuVol1 - millieuVol2);
        double coordYIntersection = (coefDirecteurVol1 * coordXIntersection) + ordonneeAOrigineVol1;

        //System.out.println(coordXIntersection);
        //System.out.println(coordYIntersection);
        
        //calcul de la distance entre l'aéroport de départ et le point potentiel de colision
        double distanceColisionVol1 = Math.sqrt((coordXIntersection - aeroportDepartVol1.getX())*(coordXIntersection - aeroportDepartVol1.getX())+(coordYIntersection - aeroportDepartVol1.getY())*(coordYIntersection - aeroportDepartVol1.getY()));
        double distanceColisionVol2 = Math.sqrt((coordXIntersection - aeroportDepartVol2.getX())*(coordXIntersection - aeroportDepartVol2.getX())+(coordYIntersection - aeroportDepartVol2.getY())*(coordYIntersection - aeroportDepartVol2.getY()));
        
        //calcul la distance entre laéroport de départ et d'arrivé
        double distanceInitialeVol1 = Math.sqrt((aeroportArriveVol1.getX() - aeroportDepartVol1.getX())*(aeroportArriveVol1.getX() - aeroportDepartVol1.getX())+(aeroportArriveVol1.getY() - aeroportDepartVol1.getY())*(aeroportArriveVol1.getY() - aeroportDepartVol1.getY()));
        double distanceInitialeVol2 = Math.sqrt((aeroportArriveVol2.getX() - aeroportDepartVol2.getX())*(aeroportArriveVol2.getX() - aeroportDepartVol2.getX())+(aeroportArriveVol2.getY() - aeroportDepartVol2.getY())*(aeroportArriveVol2.getY() - aeroportDepartVol2.getY()));
        

        
        
        
        //si la distance au point dintersection des droites est supérieur a la distance
        
        //entre les 2 aeroports du vol alors pas de colisions
        //apres le && ajouté en plus a supprimer si ca marche pas
        if(!(vol1.getAéroportArrivée().equals(vol2.getAéroportArrivée())) && !(vol1.getAéroportArrivée().equals(vol2.getAéroportDépart())) && !(vol1.getAéroportDépart().equals(vol2.getAéroportArrivée()))) {
            //meme aeroport darrivée ca rendre dedans !!!!!!!!!!!!!
            if(distanceColisionVol1 > distanceInitialeVol1 || distanceColisionVol2 > distanceInitialeVol2){
                //System.out.println("3");
                return new Result(false);
            }
        }
        
        //produit en croix pour trouver le temps pour arriver aux point d'intersection
        int tempsArriverCroisementVol1 = (int) ((vol1.getDurrée()*distanceColisionVol1)/distanceInitialeVol1);
        int tempsArriverCroisementVol2 = (int) ((vol2.getDurrée()*distanceColisionVol2)/distanceInitialeVol2);
        
        //bonus
        if(tempsArriverCroisementVol1 > vol1.getDurrée() || tempsArriverCroisementVol2 > vol2.getDurrée()){
            //System.out.println("1");
            return new Result(false);
        }
        
        //Temps total en minutes pour aller de l'aéroport de départ au point potentiel de colision
        int tempsTotal = ajoutMinutes(vol1.getHeureDépart(),vol1.getMinutesDépart() , tempsArriverCroisementVol1);
        int tempsTotal2 = ajoutMinutes(vol2.getHeureDépart(),vol2.getMinutesDépart() , tempsArriverCroisementVol2);   
        
        //System.out.println(tempsTotal);
        //System.out.println(tempsTotal2);
        
        //condition si les 2 vols ont un risque de colision ou pas ( <15min )
        if(plusDe15Minutes(tempsTotal, tempsTotal2)){
            return new Result(false);
        }
        this.nbColisions += 1;
        
        //System.out.println(vol1.getAéroportDépart()+"-"+vol1.getAéroportArrivée() + " en conflit avec " + vol2.getAéroportDépart()+"-"+vol2.getAéroportArrivée());

        //vraiment utile les coordonnée en retour de fonction ?
        return new Result(true, new double[] {coordXIntersection,coordYIntersection});
    }
    
    
    private boolean isParallele(){
        //System.out.println(coefDirecteurVol1);
        //System.out.println(coefDirecteurVol2);
       return (coefDirecteurVol1 == coefDirecteurVol2);
    }
    
    public boolean isColinéaire(Vols vol1,Vols vol2){
        //System.out.println("cooli");
        return (vol1.getAéroportDépart().equals(vol2.getAéroportDépart()) && vol1.getAéroportArrivée().equals(vol2.getAéroportArrivée())) ||
                (vol1.getAéroportDépart().equals(vol2.getAéroportArrivée()) && vol1.getAéroportArrivée().equals(vol2.getAéroportDépart()));
    }

    
    private Aeroports[] getAeroport(Vols vol, List<Aeroports> listeAeroports) {
        Aeroports aeroportDepart = new Aeroports();
        Aeroports aeroportArrive = new Aeroports();

        for (Aeroports aeroport : listeAeroports) {
            if (aeroport.getCodeAeroport().equals(vol.getAéroportDépart())) {
                aeroportDepart = aeroport;      
            }
            if (aeroport.getCodeAeroport().equals(vol.getAéroportArrivée())) {
                aeroportArrive = aeroport;
            }
        }

        return new Aeroports[]{aeroportDepart,aeroportArrive};
    }

    private int ajoutMinutes(int heures, int minutes, int minutesAAjouter){
        int minutesTotal = (heures * 60) + minutesAAjouter + minutes;
        
        return minutesTotal;
    }
    

    private boolean plusDe15Minutes(int minutes1, int minutes2){

        
        return (Math.abs(minutes1-minutes2) >= 15);
    }
    
    public double averageDegree(List<String> listeColisions){
        
        Map<String, Integer> degreeMap = new HashMap<>();
        
        if(listeColisions.isEmpty()){
            return 0;
        }
        
        for (String value:listeColisions){
            String[] separation = value.split(" ");
            
            degreeMap.put(separation[0], degreeMap.getOrDefault(separation[0], 0) + 1);
            degreeMap.put(separation[1], degreeMap.getOrDefault(separation[1], 0) + 1);
            
        }
        int size = degreeMap.size();
        double degréeMoyen = (double) nbColisions * 2 / size;
        

        
        return degréeMoyen;
    }

    public void setNbColisions(int nbColisions) {
        this.nbColisions = nbColisions;
    }
    
    
    
    
}
