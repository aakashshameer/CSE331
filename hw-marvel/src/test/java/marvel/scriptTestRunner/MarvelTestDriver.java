/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel.scriptTestRunner;

import graph.DirectedGraph;
import marvel.MarvelPaths;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    // TODO for the student: Uncomment and parameterize the next line correctly:
    private final Map<String, DirectedGraph<String, String>> graphs = new HashMap<String, DirectedGraph<String, String>>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new GraphTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
        // TODO: Implement this, reading commands from `r` and writing output to `w`.
        // See GraphTestDriver as an example.
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests() throws IOException{
        // TODO: Implement this.
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }



    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "LoadGraph":
                    loadGraph(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {

        graphs.put(graphName, new DirectedGraph<String, String>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        DirectedGraph<String, String> graph = graphs.get(graphName);
        graph.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        DirectedGraph<String, String> g = graphs.get(graphName);
        g.addEdge(parentName, childName, edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName +
                " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        DirectedGraph<String, String> graph = graphs.get(graphName);
        Set<String> n = new TreeSet<String>(graph.getNodes());
        String result = graphName + " contains:";
        for(String node : n){
            result += " " + node;
        }

        output.println(result);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        DirectedGraph<String, String> g = graphs.get(graphName);
        String res = "the children of " + parentName + " in " + graphName + " are:";
        Set<DirectedGraph.Edge<String, String>> list = g.edgesFromNodesOutgoing(parentName);

        Map<String, String> m = new HashMap<>();
        for (DirectedGraph.Edge<String, String> e : list) {
            m.put(e.getChild(), e.getLabel());
        }
        List<String> childNames = new ArrayList<>();
        for (DirectedGraph.Edge<String, String> e : list) {
            childNames.add(e.getChild());
        }
        Collections.sort(childNames);

        for (int i = 0; i < childNames.size(); i++) {
            String curr = childNames.get(i);
            res += " " + curr + "(" + m.get(curr) + ")";
        }
        output.println(res);
//        DirectedGraph<String, String> graph = graphs.get(graphName);
//        String result = "the children of " + parentName + " in " + graphName + " are:";
//        List<String > nodesList = new ArrayList<>();
//        Set<String> nodes = graph.getChildren(parentName);
//        nodesList.addAll(nodes);
//        List<DirectedGraph.Edge<String, String> > node_edges_list = new ArrayList<>();
//        Set<DirectedGraph.Edge<String, String>> node_edges = graph.edgesFromNodesOutgoing(parentName);
//        node_edges_list.addAll(node_edges);
//
//        Collections.sort(nodesList);
//        int i = 0;
//            for(DirectedGraph.Edge<String, String> e: node_edges){
//                String n = "";
//                if(i < nodesList.size()) {
//                    n = nodesList.get(i);
//                }
//
//                result += " " + n + "(" + e.getLabel() + ")";
//                i++;
//
//        }
//
//
//        output.println(result);
    }

    private void loadGraph(List<String> arguments) throws Exception {
        if(arguments.size() != 2){
            throw new CommandException("Bad arguments to loadGraph: " + arguments);
        }
        String graphName = arguments.get(0);
        String filename = arguments.get(1);
        loadGraph(graphName, filename);
    }

    private void loadGraph(String graphName, String filename) throws Exception{
        //filename = "marvel.csv" + filename;
        graphs.put(graphName, MarvelPaths.buildGraph(filename));
        output.println("loaded graph " + graphName);
    }

    private void findPath(List<String> arguments){
        if(arguments.size() != 3){
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String node1 = arguments.get(1);
        String node2 = arguments.get(2);
        findPath(graphName, node1, node2);
    }

    private void findPath(String graphName, String node1, String node2){
        DirectedGraph<String, String> g= graphs.get(graphName);

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
        output.println(result);
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }

}
