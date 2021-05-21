package pathfinder.junitTests.datastructures;

import graph.DirectedGraph;
import org.junit.Test;
import pathfinder.Dijkstra;

public class DijkstraTest {
    private final DirectedGraph<String, Double> graph;
    Dijkstra<String> dijkstra;

    public DijkstraTest (){
        graph = new DirectedGraph<>();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B", 5.0);
        graph.addEdge("B", "A", 5.0);
        graph.addEdge("A", "C", 10.0);
        graph.addEdge("C", "A", 10.0);
        dijkstra = new Dijkstra<>(graph);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinPathWithUnknownNode (){
        dijkstra.minimumPath("D", "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinPathWithNullStartNode (){
        dijkstra.minimumPath(null, "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinPathWithNullDestNode (){
        dijkstra.minimumPath("A", null);
    }

}
