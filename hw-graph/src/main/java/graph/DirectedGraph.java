package graph;

import java.util.Set;
import java.util.*;

/**
    DirectedGraph is a class for a mutable directed graph with nodes and edges.
  */
public class DirectedGraph {
    //Representation Invariant:
    //  DirectedGraph != null, every node and edge in the graph will not be null.
    //  If an edge exist between node(s), the node(s) must be in DirectedGraph

    //Abstraction Function:
    //  AF(this) = Directed Graph graph s.t:
    //      {} if the graph is empty
    //      {x = [], ...} if x is a node in the graph with no outgoing edges
    //      {x = [y(XY), z(XZ),.... , y = [], z = []} if y and z are children node of x with the labels XY and XZ respectively.

    private final static boolean DEBUG = false;
    //our directed graph as a Map
    private final Map<String, HashSet<Edge>> directedGraph;

     /**
      * Creates an new Directed Graph which is empty
      *
      * @spec.effects add a new empty directed graph
      */
    public DirectedGraph(){
        //construct new Map
        //throw new RuntimeException("Not yet implemented");
        directedGraph = new HashMap<String, HashSet<Edge>>();
        checkRep();
    }


    /**
    * addNode method adds node to the graph if it isn't already present.
    *
    * @param node node to add to the graph
    * @throws IllegalArgumentException if node is null
    * @return true if the node is successfully added and false otherwise
     * @spec.requires node != null
    * @spec.modifies this
    * @spec.effects add new node to this if it isn't already present
     */
    public boolean addNode (String node) {
        checkRep();
        if (node == null) {
            throw new IllegalArgumentException("Passed in node cannot be null");
        }
            if (!directedGraph.containsKey(node)) {
                HashSet<Edge> newSet = new HashSet<Edge>();
                directedGraph.put(node, newSet);
                checkRep();
                return true;
            }

        checkRep();
        return false;

    }

     /**
      * addEdge method that adds an edge from two nodes
      *
      * @param origin node which is the start of the edge
      * @param dest node which is the end of the edge
      * @param label label for this new edge
      * @throws IllegalArgumentException if origin or dest does not exist in our graph and if the origin, node  or
      *         label is null
      * @return true if edge is successful added, false if edge already exist or can't be added
      * @spec.requires origin != null, dest != null, label != null
      * @spec.modifies this.edge
      * @spec.effects add edge from origin to dest to the graph if the edge didn't already exist
      */
     public boolean addEdge (String origin, String dest, String label){
         checkRep();

         if(origin == null || dest == null || label == null){
             throw new IllegalArgumentException("Parent node, child node and label cannot be null");
         }

         if(!directedGraph.containsKey(origin) || !directedGraph.containsKey(dest)){
             throw new IllegalArgumentException("Parent node or child node passed in does not exist in our graph");
         }

         HashSet<Edge> originEdges = directedGraph.get(origin);
         Edge e = new Edge(label, origin, dest);

         if(!(originEdges.contains(e))){
             originEdges.add(e);
             checkRep();
             return true;
         }

         checkRep();
         return false;


    }

    /**
     * removeEdge method that removes an edge from two nodes
     *
     * @param origin the start of the edge
     * @param dest the end of the edge
     * @param label the label of the edge that we want to remove
     * @throws IllegalArgumentException if origin, dest or label does not exist and if the origin, dest or label is null
     * @return String the edge that was removed
     * @spec.requires origin != null, dest != null, label != null
     * @spec.modifies this.edge
     * @spec.effects remove an edge from origin to dest to the graph if the edge already exist
     */
    public Edge removeEdge (String origin, String dest, String label){
        checkRep();

        if(origin == null || dest == null || label == null){
            throw new IllegalArgumentException("Passed in value cannot be null");
        }

        if(!directedGraph.containsKey(origin) || !directedGraph.containsKey(dest)){
            throw new IllegalArgumentException("parent or child node doesn't exist in our graph.");
        }

        HashSet<Edge> edgesFromOrigin = directedGraph.get(origin);
        Edge e = new Edge (label, origin, dest);
        if(edgesFromOrigin.contains(e)){
            edgesFromOrigin.remove(e);
            checkRep();
            return e;
        }

        checkRep();
        return null;
    }

    /**
     * getChildren method that returns a Set of nodes that the parent node is pointing to
     *
     * @param node node that we are trying to find the nodes that it is pointing to
     * @throws IllegalArgumentException if the node is not in our graph and if the node is null
     * @return a set of nodes that the parent node is pointing to
     * @spec.requires node != null
     */
    public Set<String> getChildren (String node){
        if(node == null){
            throw new IllegalArgumentException("Passed in node cannot be null.");
        }

        if (!directedGraph.containsKey(node)){
            throw new IllegalArgumentException("Node does not exist in our graph");
        }
        HashSet<String> childrenNodes = new HashSet<>();
        HashSet<Edge> edgesFrom = directedGraph.get(node);

        for(Edge e : edgesFrom){
            String child = e.getChild();
            childrenNodes.add(child);
        }

        return childrenNodes;
    }



    /**
      * containsNode method that determine if the passed-in node is in the graph
      *
      * @param node the node that we are trying to locate
      * @throws IllegalArgumentException if node is null
      * @return true if the node is in our nodes and false otherwise
      * @spec.requires node != null
      */
     public boolean containsNode (String node) {
         checkRep();

         if(node == null){
             throw new IllegalArgumentException("Node cannot be null");
         }


         boolean returnValue = directedGraph.containsKey(node);
         checkRep();
         return returnValue;
    }



     /**
      * getNodes method that returns all nodes from the graph
      *
      * @return a Set of Strings that represents all nodes in the graph
      */
     public Set<String> getNodes () {

         checkRep();
         Set<String> nodes = directedGraph.keySet();
         checkRep();
         return nodes;
    }



     /**
      * getSize method to determine the number of nodes in the graph
      *
      * @return an int that represents the number of nodes in the graph
      */
     public int getSize() {
         checkRep();
         int size = directedGraph.size();
         checkRep();
         return size;
    }

     /**
      * isEmpty method to determine whether the graph is empty or not
      *
      * @return true if the graph is empty and false otherwise
      */
     public boolean isEmpty(){

         checkRep();
         boolean empty = directedGraph.isEmpty();
         checkRep();
         return empty;
    }

    /**
     * edgesFromNodesOutgoing method that returns a set of outgoing edges from the passed node
     *
     * @param node node that we want to find the edges going out from it
     * @return the Set of Strings of outgoing edges from the node
     * @throws IllegalArgumentException if the node is not in our graph
     * @spec.requires node != null
     */
    public Set<Edge> edgesFromNodesOutgoing (String node){
        checkRep();

        if(node == null || !containsNode(node)){
            throw new IllegalArgumentException("Passed node cannot either be null or not in our graph");
        }

        HashSet<Edge> edges = directedGraph.get(node);

        checkRep();
        return new HashSet<Edge>(edges);
    }

    /**
     * toString method that represents the graph as a String
     *
     * @return String that represents the graph
     */

    public String toString (){
        checkRep();
        String returnString = directedGraph.toString();
        checkRep();
        return returnString;
    }

    /**
     * numberOfEdges method that return the number of edges between origin and dest nodes
     *
     * @param origin the origin of the edge
     * @param dest the destination of the edge
     * @return the number of edges between the origin and the dest
     * @throws IllegalArgumentException if the passed origin and destination node is not in our graph
     * @spec.requires origin != null, dest != null
     */
    public int numberOfEdges (String origin, String dest){
        checkRep();

        if(origin == null || dest == null){
            throw new IllegalArgumentException("Passed in parent and children node cannot be null");
        }

        if(!directedGraph.containsKey(origin) || !directedGraph.containsKey(dest)){
            throw new IllegalArgumentException("Passed in parent and children node does not exist");
        }
        int nOfEdges = 0;

        HashSet<Edge> edges = directedGraph.get(origin);
        for(Edge e : edges){
            if(e.getChild().equals(dest)){
                nOfEdges++;
            }
        }
        checkRep();
        return nOfEdges;

    }

    /**
     * checkRep method that checks if the representation invariant hold
     *
     */

    private void checkRep(){
        if(directedGraph == null){
            throw new RuntimeException("Our directed graph cannot be null");
        }

        if(DEBUG) {
            Set<String> allNodes = directedGraph.keySet();

            for (String node : allNodes) {
                if (node == null) {
                    throw new RuntimeException("Our node cannot be null");
                }
                HashSet<Edge> edgesFromNode = directedGraph.get(node);

                for (Edge e : edgesFromNode) {
                    if (e == null) {
                        throw new RuntimeException("Our edges cannot be null");
                    }

                    if (!directedGraph.containsKey(e.getParent())) {
                        throw new RuntimeException("Parent node of the edge must exist before the edge");
                    }

                    if (!directedGraph.containsKey(e.getChild())) {
                        throw new RuntimeException("Child node of the edge must exist before the edge");
                    }


                }
            }
        }
    }

    /**
     * private class to for the edges with the label of the edge with the parent and child node.
     *
     * field: label the label of the edge
     * field: parent the parent node
     * field: child the child node
     *
     * @author Aakash Shameer Bin Srazali
     * @version 05/06/2021
     */
    public static class Edge {
        //Representation invariant:
        //  label != null && parent != null && child != null

        //Abstraction function:
        //  AF(this): a labeled edge with origin(parent) and destination(child) node such that
        //      edge.parent = this.parent
        //      edge.child = this.child
        //      edge.label = this.label

        //fields
        private final String label; //label of the edge
        private final String parent; //parent(origin) of the edge
        private final String child; //child(destination) of the edge


        /**
         * constructor that creates an edge
         *
         * @param label label of the edge
         * @param parent parent(origin) of the edge
         * @param child child(destination) of the edge
         * @spec.requires label != null, parent != null, child != null
         * @spec.effects constructs an edge with a parent(origin) and child(destination) node
         */
        public Edge(String label, String parent, String child) {
            if(label == null || parent == null || child == null){
                throw new IllegalArgumentException("Passed in label, parent and child cannot be null");
            }
            this.label = label;
            this.parent = parent;
            this.child = child;
            checkRep();
        }

        /**
         * getParent method returns the parent node of the edge
         *
         * @return the parent(origin) node of the edge
         */
        public String getParent (){
            checkRep();
            return parent;
        }

        /**
         * getChild method returns the child node of the edge
         *
         * @return the child(destination) node of the edge
         */
        public String getChild (){
            checkRep();
            return child;
        }

        /**
         * getLabel method returns the label of the edge
         *
         * @return the label of the edge
         */
        public String getLabel (){
            checkRep();
            return label;
        }

        /**
         * toString method represents the edge as a string and returns a String
         *
         * @return a String representing the edge
         */
        @Override
        public String toString (){
            checkRep();
            String result = "";
            result = getChild().toString() + "(" + getLabel().toString() + ")";
            checkRep();
            return result;
        }

        /**
         * equals method test the equality between two edges which returns true if n is the same as this and false otherwise
         *
         * @param n edge to be compared with
         * @return true of this and n are equal (same parent, child and label) and false otherwise
         */
        @Override
        public boolean equals (Object n){
            checkRep();
            if(!(n instanceof Edge)){
                return false;
            }
            Edge e = (Edge) n;
            boolean returnValue = getParent().equals(e.getParent()) && getChild().equals(e.getChild()) && getLabel().equals(e.getLabel());
            checkRep();
            return returnValue;
        }

        /**
         * hashCode method that returns the hash number of this edge
         *
         * @return the hash number of this edge
         */
        @Override
        public int hashCode(){
            checkRep();
            int returnValue = getLabel().hashCode() + getParent().hashCode() + getChild().hashCode();
            checkRep();
            return returnValue;
        }

        /**
         * checkRep method that checks the representation invariant of this class
         *
         */
        private void checkRep (){
            if(label == null){
                throw new RuntimeException("Edge label cannot be null");
            }

            if(parent == null){
                throw new RuntimeException("Parent node cannot be null");
            }
            if(child == null){
                throw new RuntimeException("Children node cannot be null");
            }

        }


    }

}
