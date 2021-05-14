package marvel;

import graph.DirectedGraph;

import java.util.*;

public class MarvelPath {

    public static DirectedGraph buildGraph (String filename) throws Exception{
        if(filename == null){
            throw new IllegalArgumentException("filename cannot be null");
        }

        Map<String, List<String>> books = MarvelParser.parseData(filename);

        DirectedGraph graphMarvel = new DirectedGraph();

        for(String book : books.keySet()){
            for(String nParent: books.get(book)) {
                graphMarvel.addNode(nParent);
                for(String nChild : books.get(book)) {
                    graphMarvel.addNode(nChild);
                    if(!nChild.equals(nParent)) {
                        graphMarvel.addEdge(nParent, nChild, book);
                        graphMarvel.addEdge(nChild, nParent, book);
                    }
                }
            }
        }



    return graphMarvel;
    }

    public static ArrayList<DirectedGraph.Edge> shortestPath (DirectedGraph graph, String origin, String dest){
        if(graph == null | origin == null | dest == null){
            throw new IllegalArgumentException("graph, origin or dest cannot be null");
        }

        if(!(graph.containsNode(origin))){
            throw new IllegalArgumentException("the character " + origin + " is not in the graph");
        }

        if(!(graph.containsNode(dest))){
            throw new IllegalArgumentException("the character " + dest + " is not in the graph" );
        }

        LinkedList<String> nodesToVisit = new LinkedList<String>();

        HashMap<String, ArrayList<DirectedGraph.Edge>> pathList = new HashMap<String, ArrayList<DirectedGraph.Edge>>();

        //List<String[]> path = new LinkedList<String[]>();

        pathList.put(origin, new ArrayList<DirectedGraph.Edge>());
        nodesToVisit.add(origin);

        while(!nodesToVisit.isEmpty()){
            String n = nodesToVisit.removeFirst();
            if(n.equals(dest)){
                ArrayList<DirectedGraph.Edge> path = pathList.get(n);
                return new ArrayList<DirectedGraph.Edge>(path);
            }

            ArrayList<DirectedGraph.Edge> edgeSet = new ArrayList<DirectedGraph.Edge>();
            Comparator<DirectedGraph.Edge> compare = compareAndSortEdges();
            edgeSet.addAll(graph.edgesFromNodesOutgoing(n));

            for(DirectedGraph.Edge e : edgeSet){
                String end = e.getChild();

                if(!(pathList.keySet().contains(end))){
                    ArrayList<DirectedGraph.Edge> pa_old = pathList.get(n);
                    ArrayList<DirectedGraph.Edge> pa_new = new ArrayList<DirectedGraph.Edge>(pa_old);
                    pa_new.add(e);
                    pathList.put(end, pa_new);
                    nodesToVisit.add(end);
                }
            }

        }
        return pathList.get(origin);


    }

    private static Comparator<DirectedGraph.Edge> compareAndSortEdges (){
        Comparator<DirectedGraph.Edge> c = new Comparator<DirectedGraph.Edge>() {
            @Override
            public int compare(DirectedGraph.Edge o1, DirectedGraph.Edge o2) {
                if(!(o1.getParent().equals(o2.getParent()))){
                    return o1.getParent().compareTo(o2.getParent());
                }

                if(!(o1.getChild().equals(o2.getChild()))){
                    return o1.getChild().compareTo(o2.getChild());
                }

                if(!(o1.getLabel().equals(o2.getLabel()))){
                    return o1.getLabel().compareTo(o2.getLabel());
                }
                return 0;
            }
        };
        return c;
    }



}
