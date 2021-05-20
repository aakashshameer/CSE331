package pathfinder;

import graph.DirectedGraph;
import pathfinder.datastructures.Path;

import java.util.*;

public class Dijkstra<T> {
    /**
     * To represent our graph
     */
    private DirectedGraph<T, Double> graph;

    /**
     * constructor  which creates a new graph
     * @param graph passed-in graph
     * @effects new Djikstra's graph is created
     */
    public Dijkstra(DirectedGraph<T, Double> graph){
        this.graph = graph;
    }

    /**
     * minimumPath method that runs the djikstra algorithm
     *
     * @param start  the starting node
     * @param dest the destination node
     * @spec.requires start != null, dest != null
     * @return Path<T> minimum path from start and dest
     */
    public Path<T> minimumPath(T start, T dest){
        if(start == null || dest == null){
            throw new IllegalArgumentException("start and dest cannot be null");
        }
        Queue<Path<T>> active = new PriorityQueue<>();
        Set<T> finished = new HashSet<>();

        Path<T> selfPath =  new Path<>(start);
        selfPath.extend(start, 0.0);
        active.add(selfPath);

        while (!active.isEmpty()){
            Path<T> minPath = active.remove();
            T minDest = minPath.getEnd();
            if(minDest.equals(dest)){
                return minPath;
            }
            if(finished.contains(minDest)){
                continue;
            }

            Set<DirectedGraph.Edge<T, Double>> list = graph.edgesFromNodesOutgoing(minDest);

            for(DirectedGraph.Edge<T, Double> e : list){
                T child = e.getChild();
                Double label = e.getLabel();

                if(finished.contains(child)) {
                    minPath.extend(child, label);
                    active.add(minPath);
                }
            }
            finished.add(minDest);
        }
        return null;

    }

}
