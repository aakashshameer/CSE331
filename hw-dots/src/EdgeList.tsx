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
}

interface EdgeListState {
    value: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {value : ""};
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

            }



            map.set(i, entries);
        }

        if(error){
            window.alert(alert);
        }

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
                <button onClick={() => {console.log('Clear onClick was called');}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
