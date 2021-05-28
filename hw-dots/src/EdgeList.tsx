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

interface EdgeListProps {
    onChange(edges: any): void;  // called when a new edge list is ready
                                 // once you decide how you want to communicate the edges to the App, you shoul// change the type of edges so it isn't `any`
    maxSize : number;
}

interface EdgeListState {
    value: string;
    map: Map<number, string>;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {value : "", map : new Map()};
    }

    onInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        const newText : string = event.target.value;
        this.setState({value : newText})
    }

    separateText = (event : any) => {
        let map = new Map();
        let arr =  this.state.value.split("\n");
        var alert : string = "Something is wrong with your input. \n" + "The correct form is x1,x2 y1,y2 color \n";
        var error : boolean = false;

        for(var i = 0; i < arr.length; i++){
            let entries = arr[i].split(" ");
            if(entries.length != 3){
                error = true;
                alert += "\nLine " + (i + 1) + ": Missing a portion of line, or missing a space.";
            } else {

                let entry0 = entries[0].split(",");
                let entry1 = entries[1].split(",");

                if(isNaN(parseInt(entry0[0], 10)) || isNaN(parseInt(entry0[1], 10)) ||
                    isNaN(parseInt(entry1[0], 10)) || isNaN(parseInt(entry1[1], 10)) ){
                    error = true;
                    alert += "\nLine " + (i + 1) + ": Coordinates contain non-integer value(s).";
                }

                if(parseInt(entry0[0]) >= this.props.maxSize || parseInt(entry0[1]) >= this.props.maxSize ||
                   parseInt(entry1[0]) >= this.props.maxSize || parseInt(entry1[1]) >= this.props.maxSize){
                    var max = Math.max(parseInt(entry0[0]), parseInt(entry0[1]), parseInt(entry1[0] ), parseInt(entry1[1]));
                    error = true;
                    alert = "We cannot draw the edges because the grid size is too small. The grid must be at least size " + (max + 1) + ".";

                }

            }

            map.set(i, entries);
        }

        if(error){
            window.alert(alert);
        }
        this.setState({map : map})
        this.props.onChange(this.state.map);
    }

    clear = (event: any) => {
        this.setState({map : new Map ()});
        this.props.onChange(this.state.map);
    }

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(this.onInputChange)}// => {console.log('textarea onChange was called');}}
                    value={this.state.value}
                /> <br/>
                <button onClick={(this.separateText)}>Draw</button>
                <button onClick={(this.clear) }>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
