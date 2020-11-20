import React, { Component } from 'react'
import './Worker.scss'
import * as Constants from "../../../src/constants"

class Shifts extends Component<any, any> {

    constructor(props : any) {
        super(props);
        this.state = {
            items: [],
            isLoaded: false,
            shiftStartTimes: [],
        };
    }

    componentDidMount() {
        fetch(Constants.BACKEND_URL + '/api/worker/' + this.props.workerId).then(res => res.json())
        .then(json => {
            this.setState({
                isLoaded: true,
                items: json,
                shiftStartTimes: json.shiftStartTimes,
                shiftEndTimes: json.shiftEndTimes
            })
        });
    }

    render() {
        var {isLoaded, items, shiftStartTimes, shiftEndTimes} = this.state;
        var shiftInfo;
        var days = ["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"]
        console.log(this.props.futureShiftCount);
        return (
            <div className="columns is-mobile shifts">
                
                {this.props.futureShiftCount === 0 ? <div className="center">No shifts for this now!</div> : shiftStartTimes.map((shiftInfo, i)=> {

                    var shiftStartDate = new Date(shiftStartTimes[i].toString());
                    var shiftEndDate = new Date(shiftEndTimes[i].toString());
                    var startHours = shiftStartDate.getHours();
                    var startMinutes = shiftStartDate.getMinutes();
                    var endHours = shiftEndDate.getHours();
                    var endMinutes = shiftEndDate.getMinutes();

                    var startAmPm = startHours >= 12 ? 'pm' : 'am';
                    var endAmPm = endHours >= 12 ? 'pm' : 'am';

                    startHours = startHours % 12;
                    startHours = startHours === 0 ? 12 : startHours;
                    endHours = endHours % 12;
                    endHours = endHours === 0 ? 12 : endHours;
                    
                    console.log(startMinutes < 10);
                    
                    var startMinutesToString;
                    var endMinutesToString;

                    if(startMinutes < 10){
                        startMinutesToString = "0"+startMinutes;
                    }
                    else{
                        startMinutesToString = startMinutes;
                    }
                    if(endMinutes < 10){
                        endMinutesToString = "0"+endMinutes;
                    }
                    else{
                        endMinutesToString = endMinutes;
                    }

                    var startTimeFormatted = startHours + ':' + startMinutesToString + ' ' + startAmPm;
                    var endTimeFormatted = endHours + ':' + endMinutesToString + ' ' + endAmPm;

                    var dateToday = new Date();
                    //If this date is from the past then don't show it
                    if(shiftEndDate >= dateToday) {
                        return  (
                            <div className="column is-2-tablet is--mobile">
                                <div className="card">
                                    <div className="card-image">
                                        <figure className="image is-4by3">
                                            <img alt="" src="http://placehold.it/300x225"/>
                                        </figure>
                                    </div>
                                    <div className="card-content">
                                        <div className="container">
                                            <span className="tag is-dark subtitle">{days[shiftStartDate.getDay()]}</span>
                                            <p>{shiftStartDate.getDate() + "/" + (shiftStartDate.getMonth()+1) + "/" + shiftStartDate.getFullYear()}</p>
                                            <p>{startTimeFormatted +  " - " + endTimeFormatted}</p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                            </div>
                        )
                    }          
                })}
            </div>
        );
    }
}

export default Shifts;