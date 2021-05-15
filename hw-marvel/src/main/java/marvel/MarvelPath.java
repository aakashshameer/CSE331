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

    public static List<DirectedGraph.Edge> shortestPath (DirectedGraph graph, String origin, String dest){
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


//        Queue<String> visitNodes = new LinkedList<>();
//
//        Map<String, List<String[]>> map = new HashMap<>();
//        visitNodes.add(origin);
//        map.put(origin, new ArrayList<>());
//
//        while(!visitNodes.isEmpty()){
//            String node = visitNodes.remove();
//            if(node.equals(dest)){
//                return map.get(node);
//            }
//
//            List<String[]> sortedNode = outgoingEdges(graph, node);
//            List<String[]> path = map.get(node);
//
//            for(String[] child: sortedNode){
//                if(!map.containsKey(child[0])){
//                    List<String[]> newPath = new LinkedList<>();
//                    newPath.addAll(path);
//                    newPath.add(child);
//                    map.put(child[0], newPath);
//                    visitNodes.add(child[0]);
//                }
//            }
//        }
//        return null;
    }

    private static Comparator<DirectedGraph.Edge> compareAndSortEdges(){
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

//    private static List<String[]> outgoingEdges (DirectedGraph graph, String n){
//        List<String[]> sortedNode = new ArrayList<>();
//        Map<String, String[]> childEdgeMap = new HashMap<>();
//        Set<String> keys = graph.getChildren(n);
//        Set<DirectedGraph.Edge> edges = graph.edgesFromNodesOutgoing(n);
//
//        for(String node : keys){
//
//
//            for(DirectedGraph.Edge e : edges){
//                String[] childEdge = new String[2];
//                childEdge[0] = node;
//                childEdge[1] = e.getLabel();
//                sortedNode.add(childEdge);
//            }
//
//        }
//        return sortedNode;
//
//
//    }



}
