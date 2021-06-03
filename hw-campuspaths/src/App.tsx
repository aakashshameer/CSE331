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

import React, {Component} from 'react';
import MapView from './MapView';
import BuildingList from './BuildingList';

import "./App.css";

interface AppState {
    value : string;
    campusBuildings: string[];
}

class App extends Component<{}, AppState> {

    constructor(props : any) {
        super(props);
        this.state = {
            value : "",
            campusBuildings : []
        };

    }

    componentDidMount() {
        this.fetchBuilding();
    }

    fetchBuilding = async() => {
        try{
            let response = await fetch("http://localhost:4567/buildings");
            console.log(response);
            if(!response.ok) {
                alert("This status is wrong! Expected: 200, Was: " + response.status);
                return;
            }

            let text = await response.text();
            let array : string[] = text.split(",");
            let buildings: string[] = [];

            for(let i = 0; i < array.length; i++){
                let building = array[i].replace("\"", "");
                building = building.replace("\"", "");
                building = building.replace("{", "");
                building = building.replace("}", "");
                building = building.replace("\"", " ");
                building = building.replace("\"", "");
                let buildingName = building.split(":");
                buildings.push(buildingName[0]);
                console.log(building);
                // buildings.push(array[i]);
                // console.log(buildings);
            }

            this.setState({campusBuildings : buildings})
        } catch (e) {
            alert("There was an error contacting the server")
        }

    };

    updateBuildingList = (newBuilding : string) => {
        this.setState({value : newBuilding});
    }

    draw = () => {

    }



    render() {
        return (
            <div>
                <h1 id="app-title">Welcome to UW GPS! </h1>
                <MapView> </MapView>
                <BuildingList onChange={this.updateBuildingList} campusBuildings={this.state.campusBuildings}/>
            </div>
        );
    }

}

export default App;
