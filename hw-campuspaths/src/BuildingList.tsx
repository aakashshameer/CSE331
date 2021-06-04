import React, {Component} from 'react';

interface BuildingListProps {
    onChange(edges: any): void;  // called when a new edge list is ready
                                 // once you decide how you want to communicate the edges to the App, you shoul// change the type of edges so it isn't `any`

    campusBuildings : string[];
}

interface BuildingListState {
    value : string;
    builds: string[];

}

class BuildingList extends Component<BuildingListProps, BuildingListState> {
    constructor(props : BuildingListProps) {
        super(props);
        this.state = {value : "", builds : []};
    }

    onInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        const newText : string = event.target.value;

        this.setState({value : newText})
    }

    separateText = (event : any) => {
        let newBuilds :string[] = [];
        let arr = this.state.value.split("\n");
        var alert: string = "";
        var error: boolean = false;
        if (arr.length > 1) {
            window.alert("only enter a single line input");
            error = true;
        }


        let buildings = arr[0].split(",");

        if (buildings.length !== 2) {
            window.alert("Something is wrong with your input. \n" + "The correct form is start, end. \n");
            error = true;
        }
        let start = buildings[0];
        let end = buildings[1];

        if (!this.props.campusBuildings.includes(start)) {
            window.alert("start building is invalid");
            error = true;
        }
        if (!this.props.campusBuildings.includes(end)) {
            window.alert("end building is invalid");
            error = true;
        }



        if (!error) {
            newBuilds.push(start);
            newBuilds.push(end);

            this.setState({value: arr[0]});

            this.props.onChange(newBuilds);
        }



    }

    clear = (event: any) => {
        this.setState({value : ""});
        this.props.onChange(this.state.value);
    }

    printBuildings = () => {
        let arr :string[] = [];
        arr.push(this.props.campusBuildings[0])
        for(var i = 1; i < this.props.campusBuildings.length; i++){

            arr.push(", " + this.props.campusBuildings[i]);
        }

        return arr;

    }


    render() {

        return (
            <div id="building-list">
                <p>Enter your start and end UW buildings in "start,end" format:</p>
                <p> Choose from the list of buildings: {this.printBuildings()}</p>
                <br/>
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
export default BuildingList;