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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;


public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.

        CampusMap<String> map = new CampusMap<>("campus_buildings.csv", "campus_paths.csv");

        Spark.get("/shortestPath", (request, response) ->{

            String startString = request.queryParams("start");
            String endString = request.queryParams("end");

            if(startString == null || endString == null) {

                Spark.halt(400, "must have start and end");
            }

            if(!map.shortNameExists(startString)){
                Spark.halt(400, "start building is invalid. ");
            }

            if(!map.shortNameExists(endString)){
                Spark.halt(400, "end building is invalid. ");
            }

            Path<Point> shortestPath = map.findShortestPath(startString, endString);

            Gson gson = new Gson();
            return gson.toJson(shortestPath);

        });

        Spark.get("/buildings", (request, response) -> {

            Map<String, String> buildings = map.buildingNames();


            Gson gson = new Gson();
            return gson.toJson(buildings);
        });

    }

}
