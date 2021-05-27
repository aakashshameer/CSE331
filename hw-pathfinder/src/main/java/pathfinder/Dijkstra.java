package pathfinder;

import graph.DirectedGraph;
import pathfinder.datastructures.Path;

import java.util.*;

public class Dijkstra<T> {
    /**
     * To represent our graph
     */
    private final DirectedGraph<T, Double> graph;

    /**
     * constructor  which creates a new graph
     * @param graph passed-in graph
     * @spec.effects new Dijkstra's graph is created
     */
    public Dijkstra(DirectedGraph<T, Double> graph){
        this.graph = graph;
    }

    /**
     * minimumPath method that runs the djikstra algorithm
     *
     * @param start  the starting node
     * @param dest the destination node
     * @spec.requires start != null, dest != null, start and dest to be in our graph
     * @return Path minimum path from start and dest
     */
    public Path<T> minimumPath(T start, T dest){
        if(start == null || dest == null){
            throw new IllegalArgumentException("start and dest cannot be null");
        }

        if(!graph.containsNode(start) || !graph.containsNode(dest)){
            throw new IllegalArgumentException("start or dest node have to be in our graph");
        }

        Queue<Path<T>> active = new PriorityQueue<>(new Comparator<Path<T>>() {
            @Override
            public int compare(Path<T> o1, Path<T> o2) {
                if(!o1.equals(o2)){
                    return o1.hashCode() - o2.hashCode();
                }
                return 0;
            }
        });
        Set<T> finished = new HashSet<>();

        Path<T> selfPath =  new Path<>(start);
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

                if(!finished.contains(child)) {
                    Path<T> newPath = minPath.extend(child, label);
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }
        return null;

    }

}
