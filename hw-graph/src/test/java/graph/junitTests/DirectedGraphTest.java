package graph.junitTests;

import graph.DirectedGraph;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.rules.Timeout;

public class DirectedGraphTest {
    @Rule public Timeout globalTimeout = Timeout.seconds(10);

    private DirectedGraph directedGraph;
    private Set<String> nodes;
    private Set<DirectedGraph.Edge> edges;

    private final String Node_X = "X";
    private final String Node_Y = "Y";
    private final String Node_Z = "Z";
    private final String Self_Edge_XX = "XX";
    private final String Edge_XY = "XY";
    private final String Edge_XY2 = "XY2";
    private final String Edge_YX = "YX";
    private final String Edge_XZ = "XZ";

    public DirectedGraphTest(){
        directedGraph = new DirectedGraph();
        nodes = new HashSet<String>();
        edges = new HashSet<DirectedGraph.Edge>();
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

    @Test
    public void testInitialToString() {
        assertEquals("{}", directedGraph.toString());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddNullNode(){
        directedGraph.addNode(null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testContainsNullNode(){
        directedGraph.containsNode(null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testChildrenNullNode(){
        directedGraph.getChildren(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetChildrenOnEmptyGraph (){
        directedGraph.getChildren(Node_X);
    }


    @Test()
    public void testContainsNodeOnEmptyGraph() {
        assertFalse(directedGraph.containsNode(Node_X));
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

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeFromNullToNullWithNullLabel (){
        directedGraph.removeEdge(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testRemoveEdgeFromNodeToNullWithNullLabel(){
        directedGraph.removeEdge(Node_X, null, null);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testRemoveEdgeFromNodeToNodeWithNullLabel(){
        directedGraph.removeEdge(Node_X, Node_Y, null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNumberOfEdgesFromNullToNull (){
        directedGraph.numberOfEdges(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberOfEdgesFromNodeToNull (){
        directedGraph.numberOfEdges(Node_X, null);
    }

    @Test
    public void testAddFirstNode(){
        directedGraph.addNode(Node_X);
    }

    @Test
    public void testToStringAfterAddingOneNode (){
        testAddFirstNode();
        assertEquals("{X=[]}", directedGraph.toString());
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
        assertTrue(directedGraph.getChildren(Node_X).isEmpty());
    }

    @Test
    public void testAddNodeThatIsAlreadyAdded() {
        testAddFirstNode();
        assertFalse(directedGraph.addNode(Node_X));
    }

    @Test
    public void testToStringAfterAddingANodeThatIsAlreadyAdded (){
        testAddNodeThatIsAlreadyAdded();
        assertEquals("{X=[]}", directedGraph.toString());
    }

    @Test
    public void testSizeAfterAddingDuplicateNodes (){
        testAddNodeThatIsAlreadyAdded();
        assertEquals(1, directedGraph.getSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingEdgeWithNonExistingNode (){
        testAddFirstNode();
        assertFalse(directedGraph.addEdge(Node_X, Node_Z, Edge_XZ));
    }

    @Test
    public void testAddingSecondNode (){
        testAddFirstNode();
        assertTrue(directedGraph.addNode(Node_Y));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberOfEdgesAfterAddingEdgeWithNonExistingNode (){
        testAddingEdgeToNonExistingNode();
        directedGraph.numberOfEdges(Node_X, Node_Y);
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
    public void testNumberOfEdgesBetweenTwoNodesWithoutAddingEdge (){
        testAddingSecondNode();
        assertEquals(0, directedGraph.numberOfEdges(Node_X, Node_Y));
    }

    @Test
    public void testAddingSelfEdge (){
        testAddFirstNode();
        assertTrue(directedGraph.addEdge(Node_X, Node_X, Self_Edge_XX));
    }

    @Test
    public void testToStringAfterAddingSelfEdge (){
        testAddingSelfEdge();
        assertEquals("{X=[X(XX)]}", directedGraph.toString());
    }

    @Test
    public void testNumberOfEdgesAfterAddingSelfEdge (){
        testAddingSelfEdge();
        assertEquals(1, directedGraph.numberOfEdges(Node_X, Node_X));
    }

    @Test
    public void testRemoveSelfEdge (){
        testAddingSelfEdge();
        assertEquals(new DirectedGraph.Edge(Self_Edge_XX, Node_X, Node_X),
                directedGraph.removeEdge(Node_X, Node_X, Self_Edge_XX));
    }

    @Test
    public void testRemoveEdgeWithWrongLabel (){
        testAddingSelfEdge();
        assertTrue(null == directedGraph.removeEdge(Node_X, Node_X, Edge_XY));
    }

    @Test
    public void testToStringAfterRemovingSelfEdge (){
        testRemoveSelfEdge();
        assertEquals("{X=[]}", directedGraph.toString());
    }

    @Test
    public void testGetChildrenAfterRemovingSelfEdge (){
        testRemoveSelfEdge();
        assertEquals(nodes, directedGraph.getChildren(Node_X));
    }

    @Test
    public void testAddEdgeBetweenTwoDiffNodes (){
        testAddingSecondNode() ;
        assertTrue(directedGraph.addEdge(Node_X, Node_Y, Edge_XY));
    }

    @Test
    public void testEdgesFromOutgoingNodeXAfterAddingEdgeBetweenTwoDiffNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        edges.add(new DirectedGraph.Edge(Edge_XY, Node_X, Node_Y));
        assertEquals(edges, directedGraph.edgesFromNodesOutgoing(Node_X));
    }

    @Test
    public void testEdgesFromOutgoingNodeYAfterAddingEdgeBetweenTwoDiffNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        assertEquals(edges, directedGraph.edgesFromNodesOutgoing(Node_Y));
    }

    @Test
    public void testGetChildrenAfterAddingTwoNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        nodes.add(Node_Y);
        assertEquals(nodes, directedGraph.getChildren(Node_X));
    }

    @Test
    public void testNumberOfEdgesNodeXAfterAddingEdgeBetweenTwoDiffNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        assertEquals(1, directedGraph.numberOfEdges(Node_X, Node_Y));
    }

    @Test
    public void testNumberOfEdgesNodeYAfterAddingEdgeBetweenTwoDiffNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        assertEquals(0, directedGraph.numberOfEdges(Node_Y, Node_X));
    }

    @Test
    public void testAddingExistingEdgeBetweenTwoNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        assertFalse(directedGraph.addEdge(Node_X, Node_Y, Edge_XY));
    }

    @Test
    public void testEdgesFromOutgoingAfterAddingExistingEdge(){
        testAddingExistingEdgeBetweenTwoNodes();
        edges.add(new DirectedGraph.Edge(Edge_XY, Node_X, Node_Y));
        assertEquals(edges, directedGraph.edgesFromNodesOutgoing(Node_X));
    }

    @Test
    public void testAddingReverseEdgeBetweenTwoDiffNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        assertTrue(directedGraph.addEdge(Node_Y, Node_X, Edge_YX));
    }

    @Test
    public void testEdgesFromOutgoingNodeXAfterAddingReverseEdge (){
        testAddingReverseEdgeBetweenTwoDiffNodes();
        edges.add(new DirectedGraph.Edge(Edge_XY, Node_X, Node_Y));
        assertEquals(edges, directedGraph.edgesFromNodesOutgoing(Node_X));
    }

    @Test
    public void testEdgesFromOutgoingNodeYAfterAddingReverseEdge (){
        testAddingReverseEdgeBetweenTwoDiffNodes();
        edges.add(new DirectedGraph.Edge(Edge_YX, Node_Y, Node_X));
        assertEquals(edges, directedGraph.edgesFromNodesOutgoing(Node_Y));
    }


    @Test
    public void testAddingMultipleEdgesWithTwoNodes (){
       testAddingSecondNode();
       assertTrue(directedGraph.addEdge(Node_X, Node_Y, Edge_XY));
       assertTrue(directedGraph.addEdge(Node_Y, Node_X, Edge_YX));
       assertTrue(directedGraph.addEdge(Node_X, Node_X, Self_Edge_XX));

    }

    @Test
    public void testEdgesFromOutgoingNodeXAfterAddingMultipleEdgesWithTwoNodes (){
        testAddingMultipleEdgesWithTwoNodes();
        edges.add(new DirectedGraph.Edge (Self_Edge_XX, Node_X, Node_X));
        edges.add(new DirectedGraph.Edge(Edge_XY, Node_X, Node_Y));
        assertEquals(edges, directedGraph.edgesFromNodesOutgoing(Node_X));
    }

    @Test
    public void testEdgesFromOutgoingNodeYAfterAddingMultipleEdgesWithTwoNodes (){
        testAddingMultipleEdgesWithTwoNodes();
        edges.add(new DirectedGraph.Edge(Edge_YX, Node_Y, Node_X));
        assertEquals(edges, directedGraph.edgesFromNodesOutgoing(Node_Y));
    }

    @Test
    public void testNumberOfEdgesFromNodeXtoNodeYAfterAddingMultipleEdgesWithTwoNodes (){
        testAddingMultipleEdgesWithTwoNodes();
        assertEquals(1, directedGraph.numberOfEdges(Node_X, Node_Y));
    }

    @Test
    public void testNumberOfEdgesFromNodeXtoNodeXAfterAddingMultipleEdgesWithTwoNodes (){
        testAddingMultipleEdgesWithTwoNodes();
        assertEquals(1, directedGraph.numberOfEdges(Node_X, Node_X));
    }

    @Test
    public void testNumberOfEdgesFromNodeYtoNodeXAfterAddingMultipleEdgesWithTwoNodes (){
        testAddingMultipleEdgesWithTwoNodes();
        assertEquals(1, directedGraph.numberOfEdges(Node_Y, Node_X));
    }

    @Test
    public void testSizeAfterAddingMultipleEdges (){
        testAddingMultipleEdgesWithTwoNodes();
        assertEquals(2, directedGraph.getSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingEdgeToNonExistingNode (){
        testAddingSecondNode();
        assertFalse(directedGraph.addEdge(Node_X, Node_Z, Edge_XZ));
    }

    @Test
    public void testAddingMultipleEdgesBetweenTwoDiffNodes (){
        testAddEdgeBetweenTwoDiffNodes();
        assertTrue(directedGraph.addEdge(Node_X, Node_Y, Edge_XY2));
    }

    @Test
    public void testEdgesFromOutgoingAfterAddingMultipleEdgesBetweenTwoDiffNodes (){
        testAddingMultipleEdgesBetweenTwoDiffNodes();
        edges.add(new DirectedGraph.Edge(Edge_XY, Node_X, Node_Y));
        edges.add(new DirectedGraph.Edge(Edge_XY2, Node_X, Node_Y));
        assertEquals(edges, directedGraph.edgesFromNodesOutgoing(Node_X));
    }
}
