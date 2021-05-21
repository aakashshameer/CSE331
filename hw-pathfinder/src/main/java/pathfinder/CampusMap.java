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

package pathfinder;

import graph.DirectedGraph;
import marvel.MarvelPaths;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;
import pathfinder.textInterface.CoordinateProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampusMap<T> implements ModelAPI<T> {

    private final List<CampusBuilding> campusBuildings;

    private final List<CampusPath> campusPaths;

    private final DirectedGraph<CampusBuilding, Double> campusGraph;

    private final Dijkstra<CampusBuilding> dijkstra;

    public CampusMap (String fileContainingBuildings, String fileContainingPaths){


        campusBuildings = CampusPathsParser.parseCampusBuildings(fileContainingBuildings);
        campusPaths = CampusPathsParser.parseCampusPaths(fileContainingPaths);
        campusGraph = new DirectedGraph<>();
        dijkstra = new Dijkstra<>(campusGraph);

    }

    @Override
    public boolean shortNameExists(String shortName) {
        for(CampusBuilding c : campusBuildings){
            if(shortName.equals(c.getShortName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String longNameForShort(String shortName) throws IllegalArgumentException{
        if(shortName == null){
            throw new IllegalArgumentException("shortName cannot be null");
        }

        if(!shortNameExists(shortName)){
            throw new IllegalArgumentException(shortName + " building does not exist");
        }
        for(CampusBuilding c : campusBuildings){
            if(shortName.equals(c.getShortName())){
                return c.getLongName();
            }
        }
        return null;
    }

    @Override
    public Map<String, String> buildingNames() {

        Map<String, String > buildings = new HashMap<>();

        for(CampusBuilding c : campusBuildings){
            buildings.put(c.getShortName(), c.getLongName());
        }
        return buildings;

    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (startShortName == null || endShortName == null) {
            throw new IllegalArgumentException("Invalid Buildings");
        }

        Path<Point> path = new Path<Point>(new Point(1, 1));
        DirectedGraph<Point, Double> graph = new DirectedGraph<>();
        Point start = new Point(-1, -1);
        Point end = new Point(-1, -1);

        for (int i = 0; i < campusBuildings.size(); i++) {
            Point point = new Point(campusBuildings.get(i).getX(), campusBuildings.get(i).getY());
            graph.addNode(point);
            if (campusBuildings.get(i).getShortName().equals(startShortName)) {
                start = new Point(campusBuildings.get(i).getX(), campusBuildings.get(i).getY());
            }
            if (campusBuildings.get(i).getShortName().equals(endShortName)) {
                end = new Point(campusBuildings.get(i).getX(), campusBuildings.get(i).getY());
            }
        }

        if (start.getX() < 0 || end.getX() < 0) {
            throw new IllegalArgumentException("Invalid Buildings");
        }

        for (int i = 0; i < campusPaths.size(); i++) {
            Point n1 = new Point(campusPaths.get(i).getX1(), campusPaths.get(i).getY1());
            Point n2 = new Point(campusPaths.get(i).getX2(), campusPaths.get(i).getY2());
            if (!graph.containsNode(n1)) {
                graph.addNode(n1);
            }
            if (!graph.containsNode(n2)) {
                graph.addNode(n2);
            }
            graph.addEdge(n1, n2, campusPaths.get(i).getDistance());
            graph.addEdge(n2, n1, campusPaths.get(i).getDistance());
        }

        Dijkstra<Point> dijkstra = new Dijkstra<>(graph);
        path = dijkstra.minimumPath(start, end);
        return path;
    }

}
