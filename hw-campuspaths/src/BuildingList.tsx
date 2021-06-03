import React, {Component} from 'react';

interface BuildingListProps {
    onChange(edges: any): void;  // called when a new edge list is ready
                                 // once you decide how you want to communicate the edges to the App, you shoul// change the type of edges so it isn't `any`

    campusBuildings : string[];
}

interface BuildingListState {
    value : string;

}

class BuildingList extends Component<BuildingListProps, BuildingListState> {
    constructor(props : BuildingListProps) {
        super(props);
        this.state = {value : ""};

    }

    onInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        const newText : string = event.target.value;


        this.setState({value : newText})
    }

    separateText = (event : any) => {
        let arr = this.state.value.split("\n");
        if (arr.length > 1) {
            window.alert("only enter a single line input");
        }
        var alert: string = "";
        var error: boolean = false;

        let buildings = arr[0].split(",");

        if (buildings.length !== 2) {
            alert = "Something is wrong with your input. \n" + "The correct form is start, end. \n";
            error = true;
        }
        let start = buildings[0];
        let end = buildings[1];

        if (!this.props.campusBuildings.includes(start)) {
            alert = "start building is invalid";
            error = true;
        }
        if (!this.props.campusBuildings.includes(end)) {
            alert = "end building is invalid";
            error = true;
        }


        if (error) {
            window.alert(alert);
        }
        this.setState({value: arr[0]});
        this.props.onChange(this.state.value);

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