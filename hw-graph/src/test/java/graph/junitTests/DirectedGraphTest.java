package graph.junitTests;

import graph.DirectedGraph;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DirectedGraphTest {

    private DirectedGraph<String, String> directedGraph;
    private Set<String> nodes;

    private final String Node_X = "X";
    private final String Node_Y = "Y";
    private final String Node_Z = "Z";
    private final String Self_Edge_XX = "XX";
    private final String Edge_XY = "XY";
    private final String Edge_XY2 = "XY2";
    private final String Edge_YX = "YX";
    private final String Edge_XZ = "XZ";

    public DirectedGraphTest(){
        directedGraph = new DirectedGraph<String, String>();
        nodes = new HashSet<String>();
    }

    @Test
    public void testInitialSize(){
        assertEquals(0, directedGraph.getSize());
    }

    @Test
    public void testInitialIsEmpty(){
        assertTrue(directedGraph.isEmpty());
    }

    @Test
    public void testInitialGetNodes(){
        assertEquals(nodes, directedGraph.getNodes());
    }


    @Test(expected = IllegalArgumentException.class )
    public void testAddNullNode(){
        directedGraph.addNode(null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testContainsNullNode(){
        directedGraph.containsNode(null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testChildrenNullNode(){
        directedGraph.pointingTo(null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testAddEdgeFromNullToNullWithNullLabel(){
        directedGraph.addEdge(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testAddEdgeFromNodeToNullWithNullLabel(){
        directedGraph.addEdge(Node_X, null, null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testAddEdgeFromNodeToNodeWithNullLabel(){
        directedGraph.addEdge(Node_X, Node_Y, null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testRemoveEdgeFromNullToNullWithNullLabel() {
        directedGraph.removeEdge(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testRemoveEdgeFromNodeToNullWithNullLabel(){
        directedGraph.addEdge(Node_X, null, null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testRemoveEdgeFromNodeToNodeWithNullLabel(){
        directedGraph.addEdge(Node_X, Node_Y, null);
    }

    @Test
    public void testAddFirstNode(){
        directedGraph.addNode(Node_X);
    }

    @Test
    public void testRemoveNonExistingNode (){
        testAddFirstNode();
        assertFalse(directedGraph.removeNode(Node_Y));
    }

    @Test
    public void testSizeAfterAddingOneNode (){
        testAddFirstNode();
        assertEquals(1, directedGraph.getSize());
    }

    @Test
    public void testEmptyAfterAddingOneNode (){
        testAddFirstNode();
        assertFalse(directedGraph.isEmpty());
    }

    @Test
    public void testGetNodesAfterAddingOneNode (){
        testAddFirstNode();
        nodes.add("X");
        assertEquals(nodes, directedGraph.getNodes());
    }

    @Test
    public void testContainsCorrectNodeAfterAddingOneNode (){
        testAddFirstNode();
        assertTrue(directedGraph.containsNode("X"));
    }

    @Test
    public void testContainsWrongNodeAfterAddingOneNOde (){
        testAddFirstNode();
        assertFalse(directedGraph.containsNode("Y"));
    }

    @Test
    public void testInitialChildrenBeforeAddingEdges(){
        testAddFirstNode();
        assertTrue(directedGraph.pointingTo(Node_X).isEmpty());
    }

    @Test
    public void testAddNodeThatIsAlreadyAdded() {
        testAddFirstNode();
        assertFalse(directedGraph.addNode(Node_X));
    }

    @Test
    public void testSizeAfterAddingDuplicateNodes (){
        testAddNodeThatIsAlreadyAdded();
        assertEquals(1, directedGraph.getSize());
    }

    @Test
    public void testAddingSecondNode (){
        testAddFirstNode();
        assertTrue(directedGraph.addNode(Node_Y));
    }

    @Test
    public void testAddingEdgeWithNonExistingNode (){
        testAddingSecondNode();
        assertFalse(directedGraph.addEdge(Node_X, Node_Z, Edge_XZ));
    }

    @Test
    public void testSizeAfterAddingSecondNode (){
        testAddingSecondNode();
        assertEquals(2, directedGraph.getSize());
    }

    @Test
    public void testGetNodesAfterAddingSecondNode (){
        testAddingSecondNode();
        nodes = new HashSet<String>();
        nodes.add(Node_X);
        nodes.add(Node_Y);
        assertEquals(nodes, directedGraph.getNodes());
    }

    @Test
    public void testAddingSelfEdge (){
        testAddFirstNode();
        assertTrue(directedGraph.addEdge(Node_X, Node_X, Self_Edge_XX));
    }

    @Test
    public void testGetEdgeAfterAddingSelfEdge (){
        testAddingSelfEdge();
        assertTrue(directedGraph.containsEdge(Self_Edge_XX));
    }

    @Test
    public void testContainsEdgeAfterAddingSelfEdge (){
        testAddingSelfEdge();
        assertTrue(directedGraph.containsEdge(Self_Edge_XX));
    }

    @Test
    public void testRemoveSelfEdge (){
        testAddingSelfEdge();
        assertEquals(Self_Edge_XX, directedGraph.removeEdge(Node_X, Node_X, Self_Edge_XX));
    }

    @Test
    public void testRemoveEdgeWithWrongLabel (){
        testAddingSelfEdge();
        assertTrue(null == directedGraph.removeEdge(Node_X, Node_X, Edge_XY));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveNonExistingEdge (){
        testAddingSelfEdge();
        directedGraph.removeEdge(Node_X, Node_Y, Self_Edge_XX);
    }

    @Test
    public void testAddEdgeBetweenTwoDiffNodes (){
        testAddingSecondNode() ;
        assertTrue(directedGraph.addEdge(Node_X, Node_Y, Edge_XY));
    }

    @Test
    public void testAddingExistingEdgeBetweenTwoNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        assertFalse(directedGraph.addEdge(Node_X, Node_Y, Edge_XY));
    }

    @Test
    public void testAddingReverseEdgeBetweenTwoDiffNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        assertTrue(directedGraph.addEdge(Node_Y, Node_X, Edge_YX));
    }

    @Test
    public void testAddingMultipleEdgesWithTwoNodes (){
       testAddingSecondNode();
       assertTrue(directedGraph.addEdge(Node_X, Node_Y, Edge_XY));
       assertTrue(directedGraph.addEdge(Node_Y, Node_X, Edge_YX));
       assertTrue(directedGraph.addEdge(Node_X, Node_X, Self_Edge_XX));

    }

    @Test
    public void testSizeAfterAddingMultipleEdges (){
        testAddingMultipleEdgesWithTwoNodes();
        assertEquals(2, directedGraph.getSize());
    }

    @Test
    public void testAddingEdgeToNonExistingNode (){
        testAddingSecondNode();
        assertFalse(directedGraph.addEdge(Node_X, Node_Z, Edge_XZ));
    }

    @Test
    public void testAddingMultipleEdgesBetweenTwoDiffNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        assertTrue(directedGraph.addEdge(Node_X, Node_Y, Edge_XY2));
    }



}
