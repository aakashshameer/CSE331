package marvel.junitTests;

import graph.DirectedGraph;
import marvel.MarvelPath;
import org.junit.Test;

public class MarvelPathTest {
    private DirectedGraph graph;

    public void setUp () throws Exception {
        graph = MarvelPath.buildGraph("hw-marvel/src/main/resources/data/football.csv");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildNullGraph () throws Exception{
        MarvelPath.buildGraph(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSNullGraph () throws Exception {
        MarvelPath.shortestPath(null, "Aubameyang", "Saka");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullStartPath (){
        MarvelPath.shortestPath(graph, null, "Aubameyang");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullEndPath (){
        MarvelPath.shortestPath(graph, "Aubameyang", null);
    }

}

