package coloration;

import java.util.Iterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class ColoGloutonne {

    public ColoGloutonne(Graph g) {
        for(Node n : g) {
            n.addAttribute("dominant",0); // Tous les sommets initialisés à 0
        }
        for(Node n : g) {
            boolean traite = false;
            Iterator<Node> it = n.getNeighborNodeIterator();
            while(it.hasNext()) {
                Node voisin = it.next();
                if((int)voisin.getAttribute("dominant") == 1) {
                    traite = true;
                }
            }
            if(traite == false) {
                n.setAttribute("dominant",1);
            }
        }
        for(Node n : g) {
            if((int)n.getAttribute("dominant") == 1) {
                n.setAttribute("ui.style", "fill-color : red , size:20px;");
            }
        }
        g.display();
    }
}