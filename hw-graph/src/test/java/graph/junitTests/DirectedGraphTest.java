package graph.junitTests;

import graph.DirectedGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirectedGraphTest {

    private DirectedGraph<String, String> directedGraph;

    private final String Node_X = "X";
    private final String Node_Y = "Y";

    public DirectedGraphTest(){
        directedGraph = new DirectedGraph<String, String>();
    }

    public void testInitialSize(){
        assertEquals(0, directedGraph.getSize());
    }

    public void testInitialIsEmpty(){
        assertTrue(directedGraph.isEmpty());
    }



    //testInitialGetNodes

    //testInitialToString

    public void testAddNullNode(){
        directedGraph.addNode(null);
    }

    public void testContainsNullNode(){
        directedGraph.containsNode(null);
    }

    public void testChildrenNullNode(){
        directedGraph.pointingTo(null;
    }

    public void testAddEdgeFromNullToNullWithNullLabel(){
        directedGraph.addEdge(null, null, null);
    }

    public void testAddEdgeFromNodeToNullWithNullLabel(){
        directedGraph.addEdge(Node_X, null, null);
    }

    public void testAddEdgeFromNodeToNodeWithNullLabel(){
        directedGraph.addEdge(Node_X, Node_Y, null);
    }

    public void testRemoveEdgeFromNullToNullWithNullLabel(){
        directedGraph.removeEdge(null, null, null);
    }
}
