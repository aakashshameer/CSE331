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
import "./MapView.css";

interface MapViewProps {
    builds : string[];
    paths : string[];
}
interface MapViewState {
    backgroundImage: HTMLImageElement | null;
}

class MapView extends Component<MapViewProps, MapViewState> {

    // NOTE:
    // This component is a suggestion for you to use, if you would like to.
    // It has some skeleton code that helps set up some of the more difficult parts
    // of getting <canvas> elements to display nicely with large images.
    //
    // If you don't want to use this component, you're free to delete it.

    canvas: React.RefObject<HTMLCanvasElement>;

    constructor(props: MapViewProps) {
        super(props);
        this.state = {
            backgroundImage: null
        };
        this.canvas = React.createRef();
    }

    componentDidMount() {
        // Might want to do something here?
        this.fetchAndSaveImage();
        this.drawBackgroundImage();
        //this.draw();
    }

    componentDidUpdate() {
        // Might want something here too...
        this.drawBackgroundImage();
        this.draw();

    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    drawBackgroundImage() {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");
        //
        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }

        // let paths :string[] = this.props.paths;
        // console.log(paths);
        // for(var j = 0; j < paths.length; j+=5){
        //     let x1 = parseInt(paths[j]);
        //     let y1 = parseInt(paths[j+1]);
        //     let x2 = parseInt(paths[j+2]);
        //     let y2 = parseInt(paths[j+3]);
        //
        //     ctx!.strokeStyle = "red";
        //     ctx!.lineWidth = 15;
        //     ctx!.beginPath();
        //     ctx!.moveTo(x1,y1);
        //     ctx!.lineTo(x2,y2);
        //     ctx!.stroke();
        //
        // }

    }

    draw = async() => {

        try {
            let path = "http://localhost:4567/shortestPath?start=" + this.props.builds[0] +
                "&end=" + this.props.builds[1];
            let response = await fetch(path);
            if(!response.ok) {
                alert("This status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            //console.log(response);
            let newText = await response.json();
            console.log(newText);
            let paths : string[] = [];
            for(var i = 0; i < newText["path"].length; i++){
                paths.push(newText["path"][i].start.x);
                paths.push(newText["path"][i].start.y);
                paths.push(newText["path"][i].end.x);
                paths.push(newText["path"][i].end.y);
                paths.push(newText["path"][i].cost);

            }
            console.log(paths);
            let canvas = this.canvas.current;
            let ctx = canvas!.getContext("2d");

            for(var j = 0; j < paths.length; j+=5){
                let x1 = parseInt(paths[j]);
                let y1 = parseInt(paths[j+1]);
                let x2 = parseInt(paths[j+2]);
                let y2 = parseInt(paths[j+3]);

                ctx!.strokeStyle = "red";
                ctx!.lineWidth = 15;
                ctx!.beginPath();
                ctx!.moveTo(x1,y1);
                ctx!.lineTo(x2,y2);
                ctx!.stroke();

            }



        } catch (e) {
            alert("Server Error");
        }

    }



    render() {
        return (
            <canvas ref={this.canvas}/>
        )
    }
}

export default MapView;
