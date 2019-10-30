import android.widget.LinearLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;
//-----------------------------------
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
//----------------------------------

public class GRAV_Window{
    JPanel graphPanel;
    HashMap<String, GRAV_Node> nodeList;
    Graph graph = new SingleGraph("Tutorial", false, true);

    public GRAV_Window(){
        // My initializations
        graphPanel = new JPanel();
        graphPanel.setLayout(new GridLayout());
        graphPanel.setVisible(true);

        // graph nodes
        nodeList = new HashMap<String,GRAV_Node>();
    }

    public void highlightNode(String id, boolean flag){
        if (flag) {
            graph.getNode(id).setAttribute("ui.style", "shape:circle; fill-color:green;" +
                    "text-alignment:center; size:20px; text-size:20px;");
        }
        else{
            graph.getNode(id).setAttribute("ui.style", "shape:circle; fill-color:black;" +
                    "text-alignment:center; size:10px; text-size:20px;");
        }
    }

    public void addNode(String id){
        nodeList.put(id, new GRAV_Node(id));
        Node node = graph.addNode(id);
        node.setAttribute("xy",Math.random()*10, Math.random()*10);
        graph.getNode(id).setAttribute("ui.style", "shape:circle; fill-color:black;" +
                    "text-alignment:center; size:10px; text-size:20px;");
        setLabel(id);
    }

    public void setLabel(String id){
        graph.getNode(id).addAttribute("ui.label", "("+id+", C:"+
                (nodeList.get(id).mark.get(Marker.checked)?"T":"F")+", V:"+
                (nodeList.get(id).mark.get(Marker.visited)?"T":"F")+")");
    }


    public void markNode(String id, String marker, Boolean flag ){
        switch(marker){
            case "checked":
                nodeList.get(id).mark.put(Marker.checked, flag);
                setLabel(id);
                break;
            case "visited":
                nodeList.get(id).mark.put(Marker.visited, flag);
                setLabel(id);
                break;
            default:
                break;
        }
    }

    public JPanel displayGraph(){
        graphPanel.removeAll();
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        ViewPanel viewPanel = viewer.addDefaultView(false);
        graphPanel.add(viewPanel);
        graphPanel.setVisible(true);
        return graphPanel;
    }

    public void addEdge(String src, String dest, int cost){
        if(!nodeList.containsKey(src)){
            nodeList.put(src, new GRAV_Node(src));
        }
        if(!nodeList.containsKey(dest)){
            nodeList.put(dest, new GRAV_Node(dest));
        }
        nodeList.get(src).add_neighbour(nodeList.get(dest), cost);
        // ------------------------------------------------------------------
        Edge e = graph.addEdge(src + dest, (Node) graph.getNode(src), (Node) graph.getNode(dest));
        e.addAttribute("ui.label", e.getId());
        e.addAttribute("ui.style", "text-size:20px;");
    }

    public JPanel getContent(){
        return graphPanel;
    }

    public static void main(String args[]) throws InterruptedException {
        GRAV_Window obj = new GRAV_Window();
        obj.addNode("A");
        obj.addNode("C");
        obj.addNode("B");
        obj.addEdge("A", "B", 5);
        obj.addEdge("C", "B", 2);
        obj.addEdge("A", "C", 3);
        JFrame j = new JFrame("GRAV");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(500,500);
        j.add(obj.displayGraph());
        j.setVisible(true);
        Scanner sc = new Scanner(System.in);
//        sc.nextInt();
//        obj.addNode("D");
//        obj.addEdge("D", "B", 5);
//        j.add(obj.displayGraph());
//        j.setVisible(true);
        sc.nextInt();
        obj.markNode("A", "checked", true );
        obj.highlightNode("A", true);
        j.add(obj.displayGraph());
        j.setVisible(true);
        sc.nextInt();
        obj.markNode("A", "visited", true );
        obj.highlightNode("A", false);
        j.add(obj.displayGraph());
        j.setVisible(true);
    }
}

enum Marker{
    checked, visited;
}

class GRAV_Node{
    // int x, y;
    String id;

    HashMap<Marker, Boolean> mark;

    HashMap<GRAV_Node, Integer> neighbour;

    public GRAV_Node(String name){
        // this.x = 30;
        // this.y = 30;
        this.id = name;
        this.mark = new HashMap<>();
        this.mark.put(Marker.checked, false);
        this.mark.put(Marker.visited, false);
        this.neighbour = new HashMap<>();
    }

    public void add_neighbour(GRAV_Node nbr_ID, int cost){
        neighbour.put(nbr_ID,cost);
    }

//    public void paint(Graphics g) {
//        g.setColor(Color.RED);
//        g.drawOval(30, 30, 20, 20);
//        g.drawString(id, 30, 30);
//        g.drawLine(50, 50, 100, 100);
//    }
}
