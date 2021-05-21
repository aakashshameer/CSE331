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
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that contains the CampusMap which can find two points between buildings.
 *
 */
public class CampusMap<T> implements ModelAPI<T> {

    //list of buildings
    private final List<CampusBuilding> campusBuildings;

    //list of paths
    private final List<CampusPath> campusPaths;

    /**
     * constructor that enables the list of buildings and path
     *
     * @param fileContainingBuildings filename with the buildings
     * @param fileContainingPaths filename with the paths
     * @spec.effects list of buildings and list of paths
     */
    public CampusMap (String fileContainingBuildings, String fileContainingPaths){
        campusBuildings = CampusPathsParser.parseCampusBuildings(fileContainingBuildings);
        campusPaths = CampusPathsParser.parseCampusPaths(fileContainingPaths);

    }

    /**
     * shortName method that returns true if the abbreatied name exist
     *
     * @param shortName The short name of a building to query.
     * @return true iff the short name provided exists in this campus map and false otherwise
     */
    @Override
    public boolean shortNameExists(String shortName) {
        for(CampusBuilding c : campusBuildings){
            if(shortName.equals(c.getShortName())){
                return true;
            }
        }
        return false;
    }

    /**
     * longNameForShort method that returns the long name of a building when we pass in the short name
     *
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
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
    /**
     * buildingName that returns a Map cotaining the building short name to long name
     *
     * @return A mapping from all the buildings' short names to their long names in this campus map.
     */
    @Override
    public Map<String, String> buildingNames() {

        Map<String, String> buildings = new HashMap<>();

        for(CampusBuilding c : campusBuildings){
            buildings.put(c.getShortName(), c.getLongName());
        }
        return buildings;

    }

    /**
     * Finds the shortest path, by distance, between the two provided buildings.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
     * if none exists.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) throws IllegalArgumentException{
        if (startShortName == null || endShortName == null) {
            throw new IllegalArgumentException("Invalid Buildings");
        }

        Path<Point> path = new Path<Point>(new Point(1, 1));
        DirectedGraph<Point, Double> graph = new DirectedGraph<>();
        Point start = new Point(-1, -1);
        Point end = new Point(-1, -1);

        for (CampusBuilding campusBuilding : campusBuildings) {
            Point point = new Point(campusBuilding.getX(), campusBuilding.getY());
            graph.addNode(point);
            if (campusBuilding.getShortName().equals(startShortName)) {
                start = new Point(campusBuilding.getX(), campusBuilding.getY());
            }
            if (campusBuilding.getShortName().equals(endShortName)) {
                end = new Point(campusBuilding.getX(), campusBuilding.getY());
            }
        }

        if (start.getX() < 0 || end.getX() < 0) {
            throw new IllegalArgumentException("Invalid Buildings");
        }

        for (CampusPath campusPath : campusPaths) {
            Point n1 = new Point(campusPath.getX1(), campusPath.getY1());
            Point n2 = new Point(campusPath.getX2(), campusPath.getY2());
            if (!graph.containsNode(n1)) {
                graph.addNode(n1);
            }
            if (!graph.containsNode(n2)) {
                graph.addNode(n2);
            }
            graph.addEdge(n1, n2, campusPath.getDistance());
            graph.addEdge(n2, n1, campusPath.getDistance());
        }

        Dijkstra<Point> dijkstra = new Dijkstra<>(graph);
        path = dijkstra.minimumPath(start, end);
        return path;
    }

}
