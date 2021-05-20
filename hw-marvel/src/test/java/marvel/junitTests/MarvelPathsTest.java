package marvel.junitTests;

import graph.DirectedGraph;
import marvel.MarvelPaths;
import org.junit.Test;

public class MarvelPathsTest {
    private DirectedGraph<String, String> graph;

    public void setUp () throws Exception {
        graph = MarvelPaths.buildGraph("hw-marvel/src/main/resources/data/football.csv");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildNullGraph () throws Exception{
        MarvelPaths.buildGraph(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSNullGraph () throws Exception {
        MarvelPaths.shortestPath(null, "Aubameyang", "Saka");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullStartPath (){
        MarvelPaths.shortestPath(graph, null, "Aubameyang");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullEndPath (){
        MarvelPaths.shortestPath(graph, "Aubameyang", null);
    }

}

