import React, { Component } from 'react'
import "../../App.scss"
import HomePageHeader from '../HomePage/HomePageHeader';
import * as Constants from "../../../src/constants"
import axios from 'axios'
import { BrowserRouter as Router,Link } from "react-router-dom";

export default class Booking extends Component {
    constructor() {
        super()
        this.state = {
            worker: [],
            fname: ""
        }
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    async componentDidMount() {
        const id = this.props.match.params.id
        const {bid} = this.props.location.state
        const {w} = this.props.location.state
        const {bookingdate} = this.props.location.state
        this.setState({
            worker: w,
            fname: w.user.firstName,
            businessid: bid,
            date: bookingdate,
            customer: 0,
        }, () => console.log("out: " + this.state.businessid + " " + this.state.worker.user.firstName))
    }

    onChange(e){
        this.setState({[e.target.name]: e.target.value});
    }
    onSubmit(e){
        e.preventDefault();
        // Booking creation here
    }

    render() {
        return(
            <div>
                <div className="content" id="hpcontent">
                    <HomePageHeader/>
                    <div className="hero is-primary">
                        <div className="hero-body" id="searchhero">
                            <h1 className="title" id="searchtitle" >Booking For: <span style={{fontWeight: "bold", fontSize: "4vh"}}>{this.state.date}</span></h1>
                        </div>
                    </div>
                    <div className="container is-fluid" id="searchcontainer">
                        <div className="box" id="searchlist" style={{marginLeft: "15%", marginRight: "15%"}}>
                            
                            <br></br>
                            <form id="register" onSubmit={this.onSubmit} style={{ marginLeft: "15%", marginRight: "15%"}}>

                                    <div className = "field">
                                        <div className = "control has-text-left">
                                            <span style={{fontWeight: "bold"}}>Worker:</span>
                                            <input className="input is-medium" type="text" placeholder="." 
                                            name="workerName" value={this.state.fname} readOnly>
                                            </input>
                                        </div>
                                    </div>

                                    <div className = "field">
                                        <div className = "control has-text-left">
                                            <span style={{fontWeight: "bold"}}>Start Time:</span>
                                            <input className = "input is-medium" type="text" placeholder="Booking Start Time" 
                                            name="firstName" value={this.state.bookingstart} onChange = {this.onChange}>
                                            </input>
                                        </div>
                                    </div>

                                    <div className = "field">
                                        <div className = "control has-text-left">
                                            <span style={{fontWeight: "bold"}}>End Time:</span>
                                            <input className = "input is-medium" type="text" placeholder="Booking End Time" 
                                            name="lastName" value={this.state.bookingend} onChange = {this.onChange}>
                                            </input>
                                        </div>
                                    </div>

                                </form>
                                <br></br>
                            <Link to="/">
                                <div className="button" style={{backgroundColor: "rgb(247, 71, 71)", color: "white", fontWeight: "bold"}}>Confirm</div>
                            </Link>
                            <Link to={"/BusinessPage/" + this.state.businessid}>
                                <div className="button is-light ml-2" style={{border: "1px solid rgb(221, 221, 221)"}}>Back</div>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}