import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

// Imports for graphstream
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

public class GRAV_Window{
    JPanel graphPanel;
    HashMap<String, GRAV_Node> nodeList;
    Graph graph;
    boolean directedFlag;
    Viewer viewer;
    ViewPanel viewPanel;

    public GRAV_Window(){
        graphPanel = new JPanel();
        graphPanel.setLayout(new GridLayout());
        graphPanel.setVisible(true);
        directedFlag = false;
        init();
    }

    public void setDirectedFlag(Boolean directedFlag){
        this.directedFlag = directedFlag;
    }

    public void init(){
        graph = new SingleGraph("Graph", false, true);
        nodeList = new HashMap<String,GRAV_Node>();
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        viewPanel = viewer.addDefaultView(false);
        displayGraph();
    }

    public void highlightNode(String id, boolean flag){
        if (flag) {
            graph.getNode(id).setAttribute("ui.style", "shape:circle; fill-color:green;" +
                    "text-alignment:center; size:20px; text-size:20px;" +
                    "text-color:red; text-style:bold;");
        }
        else{
            graph.getNode(id).setAttribute("ui.style", "shape:circle; fill-color:blue;" +
                    "text-alignment:center; size:10px; text-size:20px;" +
                    "text-color:red; text-style:bold;");
        }
    }

    /**
     * Creates new node with given id
     * @param id = Node id
     */
    public void addNode(String id){
        nodeList.put(id, new GRAV_Node(id));
        Node node = graph.addNode(id);
        //node.setAttribute("xy",Math.random()*10, Math.random()*10);
        graph.getNode(id).setAttribute("ui.style", "shape:circle; fill-color:blue;" +
                "text-alignment:center; size:10px; text-size:20px;" +
                "text-color:red; text-style:bold;");
        setLabel(id);
    }

    /**
     * Sets the label of given node
     * @param id
     */
    public void setLabel(String id){
        graph.getNode(id).addAttribute("ui.label", "("+id+", C:"+
                (nodeList.get(id).mark.get(Marker.checked)?"T":"F")+", V:"+
                (nodeList.get(id).mark.get(Marker.visited)?"T":"F")+")");
    }

    /**
     * Updates the marker value for the given node.
     * @param id - node id
     * @param marker - Name of the marker (ex. visited, checked)
     * @param flag - True/False
     */
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

    /**
     * Marks the given node as checked = True.
     * @param id - node id
     */
    public void markChecked(String id){
        markNode(id, "checked", true);
    }

    /**
     * Marks the given node as checked = False.
     * @param id - node id
     */
    public void unMarkChecked(String id){
        markNode(id, "checked", false);
    }

    /**
     * Mark the given node as Visited = True
     * @param id - node id
     */
    public void markVisited(String id){
        markNode(id, "visited", true);
    }

    /**
     * Marks the given node as Visited = False
     * @param id - node id
     */
    public void unMarkVisited(String id){
        markNode(id, "visited", false);
    }

    /**
     * Highlight he given node.
     * @param id - node id
     */
    public void highlightNode(String id){
        highlightNode(id, true);
    }

    /**
     * Unhighlight the given node.
     * @param id - node id
     */
    public void unHighlightNode(String id){
        highlightNode(id, false);
    }

    /**
     * returns the graph
     * @return - graph
     */
    public JPanel displayGraph(){
        graphPanel.removeAll();
        graphPanel.add(viewPanel);
        graphPanel.setVisible(true);
        return graphPanel;
    }

    /**
     * Add edge to the graph.
     * @param src - source node
     * @param dest - destination node
     * @param cost - cost
     */
    public void addEdge(String src, String dest, int cost){
        if(!nodeList.containsKey(src)){
            addNode(src);
        }
        if(!nodeList.containsKey(dest)){
            addNode(dest);
        }
        nodeList.get(src).add_neighbour(nodeList.get(dest), cost);
        System.out.println("Adding edge");

        Edge e = graph.addEdge(src + dest, (Node) graph.getNode(src), (Node) graph.getNode(dest), directedFlag);
        System.out.println(e);
        e.addAttribute("ui.label", e.getId());
        e.addAttribute("ui.style", "text-size:20px; text-color:black;"+
                "text-style:bold; fill-color:gray;");
    }

    /**
     * Returns the GraphPanel
     * @return graphPanel
     */
    public JPanel getContent(){
        return graphPanel;
    }

    /*
    /**
     * Driving method for testing the class functionality.
     * @param args
     * @throws InterruptedException
     * #/
    public static void main(String args[]) throws InterruptedException {
        GRAV_Window obj = new GRAV_Window();
        obj.init();
        obj.setDirectedFlag(true);
        obj.init();

        //        obj.addNode("A");
        //        obj.addNode("C");
        //        obj.addNode("B");
        String a = "A";
        obj.addEdge(a, "B", 5);
        obj.addEdge("C", "B", 2);
        obj.addEdge("A", "C", 3);
        //obj.addEdge("C", "A", 3);
        JFrame j = new JFrame("GRAV");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(500,500);
        j.add(obj.displayGraph());
        j.setVisible(true);
        Scanner sc = new Scanner(System.in);
        sc.nextInt();
        obj.addNode("D");
        obj.addEdge("D", "B", 5);
        j.add(obj.displayGraph());
        j.setVisible(true);
        //        sc.nextInt();
        //        obj.markNode("A", "checked", true );
        //        obj.markChecked("A");
        //        obj.highlightNode("A", true);
        //        j.add(obj.displayGraph());
        //        j.setVisible(true);
        //        sc.nextInt();
        //        obj.markNode("A", "visited", true );
        //        obj.markVisited("A");
        //        obj.highlightNode("A", false);
        //        j.add(obj.displayGraph());
        //        j.setVisible(true);
    }
    */
}

/**
 * List of all the markers.
 */
enum Marker{
    checked, visited;
}

/**
 * Node in the graph.
 */
class GRAV_Node{
    String id;

    HashMap<Marker, Boolean> mark;

    HashMap<GRAV_Node, Integer> neighbour;

    public GRAV_Node(String name){
        this.id = name;
        this.mark = new HashMap<>();
        this.mark.put(Marker.checked, false);
        this.mark.put(Marker.visited, false);
        this.neighbour = new HashMap<>();
    }

    /**
     * Adds the neighbours of respective nodes to the Hashmap.
     * @param nbr_ID
     * @param cost
     */
    public void add_neighbour(GRAV_Node nbr_ID, int cost){
        neighbour.put(nbr_ID,cost);
        System.out.println("neighbour added");
    }
}
