# GRAV (GRAph Visualization Tool)

This project is for visualizing the graphs while performing various DFS, BFS like operations. Usually it a common to find difficulty in keeping track/ visualizing the graph. This project provides the Intellij plugin which can visualize the graphs generated through the python code. 

This tool provides various features for creating and visualizing the graphs. The details about the functionality are in the description below. 

# Required things:
1. Python interpreter must be available.
2. py4j package must be installed.
 
# Other details:
1. Build version - Intellij tool version 10.8
2. Java 10.0.2

# Automatically downloaded packages:
1. GraphStream
2. py4j for java

# Steps to create jar file for the plugin:
1. import the project in intellij.
2. open gradle window.
3. GRAV --> Tasks --> Build --> jar (Build the jar)
4. Above step will create the 'GRAV-1.0-SNAPSHOT.jar' file under the following directory.
   ([ProjectPath]/build/libs)
   
# Steps to install plugin in Pycharm:
1. open settings --> Plugin.
2. click on the setting icon on the upper right corner. 
3. select install plugin from disk.
4. select the generated jar file from the above process.
5. restart the pycharm IDE.

# Functions and its usage:
1. void init() - 
   Initialize all graph and the graph window.
   
2. highlightNode(String id, boolean flag) - 
   Highlight or un-Highlight the given node depending on the flag. 
   If flag = True, node will be highlighted. 
   If flag = False, node will be un-highlighted.
   
3. void addNode(String id) - 
   Create a new node with given id and add it to the graph.
   
4. setLabel(String id) -
   Sets the label of the given node.
   
5. void markNode(String id, String marker, Boolean flag ) - 
   Updates the marker value for the given node. Marker can be Visited, Checked.
   Flag can be True or False. Depending on the marker status user can add specific functionality to the algorithm they are implementing.
   
6. void markChecked(String id) - 
   Marks the given node as checked = True.
   
7. void unMarkChecked(String id) -
   Marks the given node as checked = False.
   
8. void markVisited(String id) - 
   Mark the given node as Visited = True.
   
9. void unMarkVisited(String id) - 
   Marks the given node as Visited = False.
  
10. void highlightNode(String id) - 
   Highlight the given node.

11. void unHighlightNode(String id) - 
   Unhighlight the given node.
   
12. JPanel displayGraph() - 
   Returns the graph object to toolWindow.

13. void addEdge(String src, String dest, int cost) - 
   Creates new edge and Add it to the graph.

14. JPanel getContent() -
   Returns the graphPanel.
   
15. void add_neighbour(GRAV_Node nbr_ID, int cost) - 
   This method belongs to GRAV_Node class. It adds the neighbours for a respective node. It maintains the list of edges to the adjacent nodes.
