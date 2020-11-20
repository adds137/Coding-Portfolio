import React, { Component } from 'react'
import "../../App.scss"
import HomePageHeader from '../HomePage/HomePageHeader';
//import { BrowserRouter as Router,Link } from "react-router-dom";

export default class ContactPage extends Component {
    
    render() {
        return(
            <div>
            <HomePageHeader/>
            <div className="content" id="hpcontent">
                <div className="columns is-desktop is-gapless">
                    <div className="column is-one-fifths">
                        <div className="header is-primary" id="herobanner2small">
                            <span style={{ textAlign: "center" }}>AGME</span> <br />
                            <span style={{ fontWeight: "normal", fontSize: "1rem" }}>Booking Made Easy</span></div>
                        <section className="hero is-primary" id="herobanner2">
                            <div className="hero-body">
                                <div className="container" id="herobanner">
                                    <h1 className="title has-text-centered">
                                        <span style={{ fontWeight: "bold" }} id="herotitle">AGME</span><br />
                                        <span id="subtitle">Booking Made Easy</span>
                                    </h1>
                                </div>
                            </div>
                        </section>
                    </div>
                    <div className="column is-four-fifths" id="businesscolumn">
                        <div className="container is-fluid" id="businesslistcontainer">
                            <div className="box" id="businesslist" style={{overflowX: "hidden"}}>
                                <div className="title" style={{fontWeight: "bold"}}>The Team Behind AGME</div>
                                <div style={{width: "100%", border: "solid rgb(179, 179, 179) 0.5px", paddingLeft: "0px"}}/>
                                <table style={{border: "none", textAlign: "left", marginLeft: "5vw"}} id="">
                                    <tr>
                                        <th style={{border: "none"}}> </th>
                                        <th style={{border: "none"}}> </th>
                                    </tr>
                                    <tr style={{border: "none"}}>
                                        <td ><div className="subtitle" style={{fontWeight: "bold"}}>Callum Ackland</div></td>
                                        <td style={{border: "none"}}>s3791362</td>
                                    </tr>
                                    <tr>
                                        <td ><div className="subtitle" style={{fontWeight: "bold"}}>Giacomo Pirrone</div></td>
                                        <td style={{border: "none"}}>s3658746</td>
                                    </tr>
                                    <tr>
                                        <td ><div className="subtitle" style={{fontWeight: "bold"}}>Matthew Walters</div></td>
                                        <td style={{border: "none"}}>s3780012</td>
                                    </tr>
                                    <tr>
                                        <td ><div className="subtitle" style={{fontWeight: "bold"}}>Sovatharo Huy</div></td>
                                        <td style={{border: "none"}}>s3783867</td>
                                    </tr>
                                    <tr>
                                        <td ><div className="subtitle" style={{fontWeight: "bold"}}>Adrian Vong</div></td>
                                        <td style={{border: "none"}}>s3721092</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        )
    }
}