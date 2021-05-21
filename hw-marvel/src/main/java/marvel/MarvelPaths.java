package marvel;

import graph.DirectedGraph;

import java.util.*;

/**
 * This class contains a method to build a graph using input from a file,
 * a method to find the shortest path from one node to another, and allow
 * the user to find the shortest path between 2 characters from the Marvel Cinematic Universe
 * by typing in the names of 2 characters.
 */
public class MarvelPaths {

    /**
     * buildGraph method builds graph using the input from the file.
     * @param filename file to be used to build the graph
     * @spec.requires filename != null
     * @return a graph, DirectedGraph, which contains all the data from file passed in
     * @throws Exception if fail to read data from the specified
     *         file or the format of the file does not match the expected format
     */
    public static DirectedGraph<String, String> buildGraph (String filename) throws Exception{
        if(filename == null){
            throw new IllegalArgumentException("filename cannot be null");
        }

        Map<String, List<String>> books = MarvelParser.parseData(filename);

        DirectedGraph<String, String> graphMarvel = new DirectedGraph<String, String>();

        for(String book : books.keySet()){
            for(String nParent: books.get(book)) {
                graphMarvel.addNode(nParent);
                for(String nChild : books.get(book)) {
                    graphMarvel.addNode(nChild);
                    if(!nParent.equals(nChild)) {
                        graphMarvel.addEdge(nParent, nChild, book);
                        graphMarvel.addEdge(nChild, nParent, book);
                    }
                }
            }
        }

        return graphMarvel;
    }

    /**
     * shortestPath method that finds the shortest path from one character to another character.
     *
     * @param graph: the graph used to find shortest path from start to end
     * @param origin: start Node
     * @param dest: destination Node
     * @spec.requires graph != null, origin != null, dest != null, origin and dest are both in g
     * @return the shortest path from start to end, or null if
     *         no path exists from start to end
     */
    public static List<DirectedGraph.Edge<String, String>> shortestPath (DirectedGraph<String, String> graph, String origin, String dest){
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

        Map<String, ArrayList<DirectedGraph.Edge<String, String>>> map = new HashMap<String, ArrayList<DirectedGraph.Edge<String, String>>>();

        map.put(origin, new ArrayList<DirectedGraph.Edge<String, String>>());
        nodesToVisit.add(origin);

        while(!nodesToVisit.isEmpty()){
            String n = nodesToVisit.removeFirst();
            if(n.equals(dest)){
                ArrayList<DirectedGraph.Edge<String, String>> path = map.get(n);
                return new ArrayList<DirectedGraph.Edge<String, String>>(path);
                //return map.get(origin);
            }

            ArrayList<DirectedGraph.Edge<String, String>> edgeSet = new ArrayList<DirectedGraph.Edge<String, String>>();
            Comparator<DirectedGraph.Edge<String, String>> compare = compareAndSortEdges();
            edgeSet.addAll(graph.edgesFromNodesOutgoing(n));
            Collections.sort(edgeSet, compare);

            for(DirectedGraph.Edge<String, String> e : edgeSet){
                String end = e.getChild();

                if(!(map.containsKey(end))){
                    ArrayList<DirectedGraph.Edge<String, String>> path_old = map.get(n);
                    ArrayList<DirectedGraph.Edge<String, String>> path_new = new ArrayList<DirectedGraph.Edge<String, String>>(path_old);
                    path_new.add(e);
                    map.put(end, path_new);
                    nodesToVisit.add(end);

                }
            }

        }
        return null;
    }

    /**
     * compareAndSortEdges method compare two Edges
     * @requires the two Edges that are compared to not be null
     * @return negative integer, zero or positive integer if first
     * Edge is less, equal or greater than the second Edge
     */
    private static Comparator<DirectedGraph.Edge<String, String>> compareAndSortEdges(){
        Comparator<DirectedGraph.Edge<String, String>> c = new Comparator<DirectedGraph.Edge<String, String>>() {
            @Override
            public int compare(DirectedGraph.Edge<String, String> o1, DirectedGraph.Edge<String, String> o2) {
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

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args ) throws Exception{
        DirectedGraph<String, String> g = buildGraph("marvel.csv");
        System.out.println("Welcome to the Marvel Cinematic and Comic Book Universe of Characters.");
        System.out.println();
        System.out.println("I am Female Replacement Intelligent Digital Assistant Youth or F.R.I.D.A.Y for short, at your service. ");
        System.out.println("Give me any two characters in my universe and I will tell you the relationship between them");
        Scanner in = new Scanner(System.in);
        System.out.print("Your first character: ");
        String node1 = in.nextLine();
        System.out.println();
        System.out.print("Your second character: ");
        String node2 = in.nextLine();

        String result = "";
        if(!g.containsNode(node1) && !g.containsNode(node2)){
            result += "unknown: " + node1;
            result += "\n" + "unknown: " + node2;

        } else if (!g.containsNode(node1)){
            result += "unknown: " + node1;
        } else if (!g.containsNode(node2)){
            result += "unknown: " + node2;
        } else {
            List<DirectedGraph.Edge<String, String>> path = MarvelPaths.shortestPath(g, node1, node2);
            result += "path from " + node1 + " to " + node2 + ":";
            String curr = node1;
            if(path == null){
                result += "\n" + "no path found";
            } else if(node1.equals(node2)){
                result += "";
            } else {
                for(DirectedGraph.Edge<String, String> e: path){
                    result  += "\n" + curr + " to " + e.getChild() + " via " + e.getLabel();
                    curr = e.getChild();
                }
            }
        }
        System.out.println(result);

    }

}
