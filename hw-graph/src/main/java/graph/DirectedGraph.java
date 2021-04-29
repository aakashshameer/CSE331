package graph;

import java.util.Comparator;
import java.util.Set;

/**
    DirectedGraph is a class for a mutable directed graph with nodes and edges.
  */
public class DirectedGraph<String, String1> {

     /**
      * Creates an new Directed Graph which is empty
      * @spec.effects add a new empty directed graph
      */
    public DirectedGraph(){
        //construct new Map
        throw new RuntimeException("Not yet implemented");
    }


    /**
    * addNode method adds node to the graph if it isn't already present.
    *
    * @param node node to add to the graph
    * @return true if the node is successfully added and false otherwise
     * @spec.requires node != null
    * @spec.modifies this
    * @spec.effects add new node to this if it isn't already present
     */
    public boolean addNode (String node) {
        throw new RuntimeException("Not yet implemented");
    }

     /**
      * removedNode method that removes node from the graph if it is present
      *
      * @param node node to remove from the graph
      * @return true if the node is succesfully removed and false otherwise
      * @spec.requires node != null
      * @spec.modifies this.node
      * @spec.effects remove node from this.node if it is already present on the graph
      */
     public boolean removeNode (String node){
        throw new RuntimeException("Not yet implemented");
    }

     /**
      *addEdge method that adds an edge from two nodes
      *
      * @param origin node which is the start of the edge
      * @param dest node which is the end of the edge
      * @param label label for this new edge
      * @throws IllegalArgumentException if origin or dest does not exist in our graph
      * @return true if edge is successful added, false if edge already exit
      * @spec.requires origin != null, dest != null, label != null
      * @spec.modifies this.edge
      * @spec.effects add edge from origin to dest to the graph if the edge didn't already exist
      */
     public boolean addEdge (String origin, String dest, String label){
        throw new RuntimeException("Not yet implemented");
    }

    /**
     * removeEdge method that removes an edge from two nodes
     *
     * @param origin the start of the edge
     * @param dest the end of the edge
     * @throws IllegalArgumentException if origin or dest does not exist
     * @return true if edge is successful removed, false if edge doesn't exist
     * @spec.requires origin != null, dest != null, label != null
     * @spec.modifies this.edge
     * @spec.effects remove an edge from origin to dest to the graph if the edge already exist
     */
    public String removeEdge (String origin, String dest, String label){
        throw new RuntimeException("Not yet implemented");
    }


    /**
     * pointingTo method that returns a Set of nodes that the parent node is pointing to
     *
     * @param node node that we are trying to find the nodes that it is pointing to
     * @throws IllegalArgumentException if the node is not in our graph
     * @return a set of nodes that the parent node is pointing to
     * @spec.requires node != null
     */
    public Set<String> pointingTo (String node){
        throw new RuntimeException("Not yet implemented");
    }


     /**
      * methods that determine if the node is in the graph
      *
      * @param node the node that we are trying to locate
      * @return true if the node is in our nodes and false otherwise
      * @spec.requires node != null
      */
     public boolean containsNode (String node) {
        throw new RuntimeException("Not yet implemented");
    }

    /**
     * methods that determine if the edge is in the graph
     *
     * @param edge the edge that we are trying to locate
     * @return true if the edge is in our nodes and false otherwise
     * @spec.requires edge != null
     */
    public boolean containsEdge (String edge) {
        throw new RuntimeException("Not yet implemented");
    }



     /**
      * getNodes method that returns all nodes from the graph
      *
      * @return a Set of Strings that represents all nodes in the graph
      */
     public Set<String> getNodes () {
        throw new RuntimeException("Not yet implemented");
    }



     /**
      * method to determine the number of nodes in the graph
      *
      * @return an int that represents the number of nodes in the graph
      */
     public int getSize() {
        throw new RuntimeException("Not yet implemented");
    }

     /**
      * method to determine whether the graph is empty or not
      *
      * @return true if the graph is empty and false otherwise
      */
     public boolean isEmpty(){
        throw new RuntimeException("Not yet implemented");
    }

    /**
     * method that returns a set of outgoing edges from the passed node
     *
     * @param node node that we want to find the edges going out from it
     * @return the Set of Strings of outgoing edges from the node
     */
    public Set<String> edgesFromNodesOutgoing (String node){
        throw new RuntimeException("Not yet implemented");
    }


    /**
     * method that returns a set of incoming edges from the passed node
     *
     * @param node node that we want to find the edges going into it
     * @return the Set of Strings of incoming edges from the node
     */
    public Set<String> edgesToNodesIncoming (String node){
        throw new RuntimeException("Not yet implemented");
    }

}
