import React, { Component } from 'react'
// import { start } from 'repl';
import "../../App.scss"
import { BrowserRouter as Router,Link } from "react-router-dom";
import axios from 'axios';
import * as Constants from "../../../src/constants"

class WorkerAvailabilities extends Component<any, any> {

    constructor(props : any) {
        super(props);
        this.state = {
            business: {},
            workers: [],
        };

    }

    async componentDidMount() {
        var busId = this.props.busId;
        await axios
        .get(Constants.BACKEND_URL + '/api/Business/findById=' + busId)
        .then(({ data })=>{
            this.setState({
                business: data,
                workers: data.workers
            })
        })
    }

    initiateView(){
        var worker0 = document.getElementById("worker-0");
        var worker1 = document.getElementById("worker-1");
        var worker2 = document.getElementById("worker-2");

        if(worker0!=null){
            worker0.style.display = "block";
        }
        if(worker1!=null) {
            worker1.style.display = "block";
        }
        if(worker2!=null) {
            worker2.style.display = "block";
        }
    }

    getNextWorkers(){
        var amountOfGroups = Math.ceil(this.state.workers.length/3);

        if(this.state.page < amountOfGroups){
            var startIndex = this.state.page*this.state.workersPerPage;
            Array.from(Array(3), (e, j) => {
                var worker = document.getElementById("worker-"+(startIndex+j));
                if(worker != null){
                    worker.style.display = "none";
                    
                }
            }
        )}
        else{
            //alert("You have reached the end");
        }
    }

    getPrevWorkers(){

    }

    render(){
        var workers = this.state.workers;
        var days = ["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"]
        var shiftStartDate;
        var shiftEndDate;
        var daysInMonth = 31;
        var page = 0;
        var workersPerPage = 3;
        return(
            <div> 
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
                <h3 className="subtitle is-4 availability-title" style={{fontWeight: "bold", paddingTop: "10px"}}>Worker Availabilities</h3>
                {
                    workers.map((worker,i)=> {
                        var currentDate = new Date();
                        //Odd bug which won't let me use the date increment in the for loop below so I have to do it like this for now
                        currentDate.setDate(currentDate.getDate()-1);
                        var dateIncrement = 0;
                        return(
                            <div className="container worker-availability" id={"worker-" + i} style={{display:'none'}}>
                                <h4 className="subtitle worker-name"><span style={{fontWeight: "bold"}}>{worker.user.firstName + " " + worker.user.lastName}:</span></h4>
                                <p></p>
                                <br></br>
                                <br></br>
                                <br></br>
                                <div className="columns is-mobile shifts">
                                {
                                    Array.from(Array(daysInMonth), (e, j) => {
                                        currentDate.setDate(currentDate.getDate()+1);
                                        var uniqueDate = (currentDate.getDate() + "/" + (currentDate.getMonth()+1) + "/" + currentDate.getFullYear());
                                        var link = uniqueDate.replace(/\//g, "")
                                        dateIncrement++;
                                        return(
                                        <div className="column worker-date-card">
                                            <div className="card" style={{width: "200px", height: "180px", border: "1px solid rgb(179, 179, 179)", borderRadius: "7px", overflowY: "auto"}}>
                                                
                                                <Link to={{
                                                    pathname: "/Booking/" + link,
                                                    state: {
                                                        bid: this.state.business.id,
                                                        w: worker,
                                                        bookingdate: uniqueDate,
                                                        bookingstart: 0,
                                                        bookingend: 0,
                                                        customer: "worker from request",
                                                    }
                                                }}>
                                                    <div className="card-content" >
                                                        <div className="container" >
                                                            <span className="tag is-dark subtitle">{days[(currentDate.getDay())]}</span>
                                                            <p id={uniqueDate+ " " + i}><span style={{fontWeight: "bold"}}>{uniqueDate}<br></br></span>Unavailable Times:</p>
                                                        </div>
                                                    </div>
                                                </Link>


                                            </div>
                                        </div>
                                        )
                                    })
                                }
                                </div>
                                {

                                    worker.shiftStartTimes.map((shiftTime,k)=> {
                                        
                                        var shiftStartDate = new Date(worker.shiftStartTimes[k]);
                                        var shiftEndDate = new Date(worker.shiftEndTimes[k]);
                                        var shiftDate = (shiftStartDate.getDate()) + "/" + (shiftStartDate.getMonth()+1) + "/" + shiftStartDate.getFullYear();
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
                                        var workingTimes = startTimeFormatted + " - " + endTimeFormatted;
                                        var theDiv = document.getElementById(shiftDate + " " + i);
                                        var pElement = document.createElement('p');
                                        var pElement2 = <div style={{backgroundColor: "red"}}></div>
                                        pElement.className = workingTimes;
                                        pElement.innerText = workingTimes;
                                        pElement.style.fontSize = "0.8rem";
                                        if(theDiv != null){
                                            console.log(startHours);
                                            if(theDiv.getElementsByClassName(workingTimes)[0] === null){
                                                theDiv.appendChild(pElement);
                                            }
                                        }
                                        })
                                }                                            
                            </div>
                        )
                    })}
                <div className="pagination">
                    <button className="worker-nav-button" onClick={()=>{
                            var amountOfGroups = Math.ceil(this.state.workers.length/workersPerPage);

                            if(page > 0){
                                var startIndex = page*workersPerPage;
                                Array.from(Array(workersPerPage), (e, j) => {
                                    var worker = document.getElementById("worker-"+(startIndex+j));
                                    if(worker != null){
                                        worker.style.display = "none";
                                    }
                                },
                            )
                            page = page - 1;
                            startIndex = page*workersPerPage;
    
                            Array.from(Array(workersPerPage), (e, j) => {
                                var worker = document.getElementById("worker-"+(startIndex+j));
                                if(worker != null){
                                    worker.style.display = "block";
                                }
                            },
                        )
                        }
                        else{
                            //alert("Cannot go further back");
                        }
                        
                        }}>Prev</button>


                    <button className="worker-nav-button" onClick={()=>{
                        var amountOfGroups = Math.ceil(this.state.workers.length/workersPerPage);

                        if(page < amountOfGroups-1){
                            var startIndex = page*workersPerPage;
                            Array.from(Array(workersPerPage), (e, j) => {
                                var worker = document.getElementById("worker-"+(startIndex+j));
                                if(worker != null){
                                    worker.style.display = "none";
                                }
                            },
                        )
                        page = page + 1;
                        startIndex = page*workersPerPage;

                        Array.from(Array(workersPerPage), (e, j) => {
                            var worker = document.getElementById("worker-"+(startIndex+j));
                            if(worker != null){
                                worker.style.display = "block";
                            }
                        },
                    )
                    }
                    else{
                        //alert("You have reached the end");
                    }
                    }}>Next</button>
                </div>
                {this.initiateView()}
            </div>
        )
    }

}export default WorkerAvailabilities;