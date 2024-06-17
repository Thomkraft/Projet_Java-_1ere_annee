/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Stockage;

/**
 *
 * @author thoma
 * @param <T>
 */
public class Result {
        
    private boolean Colision;
    private double[] coordColision;

    public Result(boolean colision, double[] coordColision){
        this.Colision = colision;
        this.coordColision = coordColision;
    }
    
    public Result(boolean colision){
        this.Colision = colision;
    }

    public boolean isInColision(){
        return Colision;
    }

    public double[] getColisionCoordValue(){
        return coordColision;
    }

    
}
