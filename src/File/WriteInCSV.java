/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File;

import Interface.Fenetre;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.graphstream.graph.Graph;

/**
 *
 * @author thoma
 */
public class WriteInCSV {
    
    private ArrayList<String> listLastColoFileUpdates = new ArrayList<>();
    private ArrayList<Graph> listLastGraphColo = new ArrayList<>();
    private Fenetre fen;

    public WriteInCSV(ArrayList<String> listLastColoFileUpdates,ArrayList<Graph> listLastGraphColo, Fenetre fen) {
        
        this.listLastGraphColo = listLastGraphColo;
        this.listLastColoFileUpdates = listLastColoFileUpdates;
        this.fen = fen;
    }
    
    public void WriteFinalCSVColoFile(String path) throws IOException{
        boolean csvCreated = false;
        
        int i = 0;
        
        String fileName = "coloration-groupe2.Y";
        
        String value = JOptionPane.showInputDialog(fen, "Entrez le nom de votre fichier (coloration-groupe2.Y par defaut) :");
        
        if (value.length() >= 1) {
            fileName = value;
        }
        
        for (Graph g: listLastGraphColo){
            CSVWriter writer = null;
            
            if (!csvCreated){
                    writer = new CSVWriter(new FileWriter(path + fileName + ".csv"));
                    csvCreated = true;
            } else {

                writer = new CSVWriter(new FileWriter(path + fileName + ".csv", true));
            }
            
            String filePath = listLastColoFileUpdates.get(i);
            
            String[] separation = filePath.split("\\\\");
            
            String[] values = new String[]{separation[separation.length-1] + ";" + g.getAttribute("nbConflits").toString()};
            writer.writeNext(values);

            writer.close();
                
            i++;
        }
    }
    
}
