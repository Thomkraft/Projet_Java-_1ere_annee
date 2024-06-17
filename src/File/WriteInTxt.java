/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File;

import Stockage.Aeroports;
import Stockage.Vols;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thoma
 */
public class WriteInTxt {
    private int kMaximum = -1;
    private ArrayList<String> listLastFileUpdated = new ArrayList<>();
    
    public static void createFile(String fileName) throws IOException{
        File myFile = new File("../ResultatsColisions/"+fileName+".txt");
        File myDir = new File("../ResultatsColisions");
        
        if (!myDir.exists()){
            myDir.mkdirs();
            System.out.println("Dossier crée !");
        }
        
        if(myFile.createNewFile()){
            System.out.println("fichier crée !");
        }
    }
    
    //gestion derreur si kmax = -1 alors erreure 
    public void writeInFile(String fileName,ArrayList<String> listeColisions, int nbVols, int kMax) throws IOException{
        File myDir = new File("../ResultatsColisions");
        if (!myDir.exists()){
            myDir.mkdirs();
        }
        
        FileWriter myFile = new FileWriter("../ResultatsColisions/"+fileName+".txt");
        File fileToAdd = new File("ResultatsColisions/"+fileName+".txt");
        
        myFile.write(Integer.toString(kMax));
        myFile.write("\n" + Integer.toString(nbVols));
        
        for(String value:listeColisions){
            
            myFile.write("\n"+value);
        }
        
        myFile.close();
        listLastFileUpdated.add(fileToAdd.getAbsolutePath());
        
        System.out.println("Successfully wrote to the file.");
        
    }
    
    public void writeInFileAeroport(String fileName,ArrayList<String> listeColisions, ArrayList<Vols> listeVols, int nbVols) throws IOException{
        File myDir = new File("../ResultatsAéroports");
        if (!myDir.exists()){
            myDir.mkdirs();
        }
        
        FileWriter myFile = new FileWriter("../ResultatsAéroports/"+fileName+".txt");
        
        myFile.write(Integer.toString(nbVols));
        
        for(String value:listeColisions){
            String[] separation = value.split(" ");
            System.out.println("Dans le write in file aeroports");
            System.out.println(separation[0]);
            System.out.println(separation[1]);
            
            
            

            String nomAeroport1Départ = listeVols.get(listeVols.indexOf(separation[0])).getAéroportDépart();
            String nomAeroport1Arrivée = listeVols.get(listeVols.indexOf(separation[0])).getAéroportArrivée();
            String nomAeroport2Départ = listeVols.get(listeVols.indexOf(separation[1])).getAéroportDépart();
            String nomAeroport2Arrivée = listeVols.get(listeVols.indexOf(separation[1])).getAéroportArrivée();
            
            myFile.write("\n"+nomAeroport1Départ + " " + nomAeroport1Arrivée);
            myFile.write("\n"+nomAeroport2Départ + " " + nomAeroport2Arrivée);

        }
        
        myFile.close();
        System.out.println("Successfully wrote to the file.");
        
    }
    
    
    

    public int getkMax() {
        return kMaximum;
    }

    /*
    private void addFileInList(String absolutePath){
        if(listLastFileUpdated.contains(absolutePath)) {
            System.out.println("fichier deja présent");
        } else {
            listLastFileUpdated.add(absolutePath);
        }
    }
    */
    public void setkMax(int kMax) {
        this.kMaximum = kMax;
    }

    public List<String> getListLastFileUpdated() {
        return listLastFileUpdated;
    }

    public void setListLastFileUpdated(ArrayList<String> listLastFileUpdated) {
        this.listLastFileUpdated = listLastFileUpdated;
    }
    
    
}
