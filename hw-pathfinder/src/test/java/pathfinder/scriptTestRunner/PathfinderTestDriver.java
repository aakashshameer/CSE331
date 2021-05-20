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

package pathfinder.scriptTestRunner;

import graph.DirectedGraph;
import pathfinder.Dijkstra;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    private final Map<String, DirectedGraph<String, Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new GraphTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    public PathfinderTestDriver(Reader r, Writer w) {
        // TODO: Implement this, reading commands from `r` and writing output to `w`.
        // See GraphTestDriver as an example.
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
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

        graphs.put(graphName, new DirectedGraph<String, Double>());
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
        DirectedGraph<String, Double> graph = graphs.get(graphName);
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

        addEdge(graphName, parentName, childName, Double.valueOf(edgeLabel));
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {
        DirectedGraph<String, Double> g = graphs.get(graphName);
        g.addEdge(parentName, childName, edgeLabel);
        output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to " + childName +
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
        DirectedGraph<String, Double> graph = graphs.get(graphName);
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
        DirectedGraph<String, Double> g = graphs.get(graphName);
        String res = "the children of " + parentName + " in " + graphName + " are:";
        Set<DirectedGraph.Edge<String, Double>> list = g.edgesFromNodesOutgoing(parentName);

        Map<String, Double> m = new HashMap<>();
        for (DirectedGraph.Edge<String, Double> e : list) {
            m.put(e.getChild(), e.getLabel());
        }
        List<String> childNames = new ArrayList<>();
        for (DirectedGraph.Edge<String, Double> e : list) {
            childNames.add(e.getChild());
        }
        Collections.sort(childNames);

        for (String curr : childNames) {
            res += " " + curr + "(" + String.format("%.3f", m.get(curr)) + ")";
        }
        output.println(res);

//        DirectedGraph<String, Double> graph = graphs.get(graphName);
//        String result = "the children of " + parentName + " in " + graphName + " are:";
//        List<String > nodesList = new ArrayList<>();
//        Set<String> nodes = graph.getChildren(parentName);
//        nodesList.addAll(nodes);
//        List<DirectedGraph.Edge<String, Double> > node_edges_list = new ArrayList<>();
//        Set<DirectedGraph.Edge<String, Double>> node_edges = graph.edgesFromNodesOutgoing(parentName);
//        node_edges_list.addAll(node_edges);
//
//        Collections.sort(nodesList);
//        Collections.sort(node_edges_list);
//
//        int i = 0;
//        for(DirectedGraph.Edge<String, Double> e: node_edges){
//            String n = "";
//            if(i < nodesList.size()) {
//                n = nodesList.get(i);
//            }
//
//            result += " " + n + "(" + String.format("%.3f", e.getLabel())+ ")";
//            i++;
//
//        }
//
//        output.println(result);
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
        DirectedGraph<String, Double> g= graphs.get(graphName);

        String result = "";
        Dijkstra<String> dijkstraGraph = new Dijkstra<String>(g);
        if(!g.containsNode(node1) && !g.containsNode(node2)){
            result += "unknown: " + node1;
            result += "\n" + "unknown: " + node2;

        } else if (!g.containsNode(node1)){
            result += "unknown: " + node1;
        } else if (!g.containsNode(node2)){
            result += "unknown: " + node2;
        } else {
            Path<String> paths = dijkstraGraph.minimumPath(node1, node2);
            result += "path from " + node1 + " to " + node2 + ":";
                if (paths == null) {
                    result += "\n" + "no path found";
                } else if (node1.equals(node2)) {
                    result += "\n" + "Total cost: 0.000";
                } else {
                    for (Path<String>.Segment p : paths) {
                        result += "\n" + p.getStart() + " to " + p.getEnd() + " with weight " + String.format("%.3f", p.getCost());
                    }
                    result += "\n" + "total cost: " + String.format("%.3f", paths.getCost());
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

