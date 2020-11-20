import React, { Component } from 'react'
import "../../../src/App.scss"
import { useParams } from 'react-router-dom';
import HomePageHeader from '../HomePage/HomePageHeader';
import { BrowserRouter as Router,Link } from "react-router-dom";
import * as Constants from "../../../src/constants"

export default class EditUser extends Component {
    constructor() {
        super();
        this.state = {
            user: []
        }

        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    async componentDidMount() {
        const id = this.props.match.params.id;
        await fetch(Constants.BACKEND_URL + "/api/user/" + id)
            .then(response => response.json())
            .then(data => {
            this.setState({
                userid: id,
                user: data,
            })
        })
    }

    handleChange(e) {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit(e) {
        if(!(this.state.old === this.state.user.password)) {
            alert("Old Password Is Incorrect")
        }
        if(this.state.new !== this.state.confirmnew) {
            alert("New Passwords Do Not Match")
        }

        e.preventDefault()
    }

    render() {
        return (
            <div id="hpcontent">
                <HomePageHeader/>
                <div style={{height: "100vh"}}>
                    <section className="header" id="userherobanner">
                        <div className="container" style={{height: "130px", paddingTop: "30px"}}>
                            <h1 className="title has-text-centered">
                                <span style={{ fontWeight: "bold", color: "white", fontSize: "3.2vh"}}>Change Password</span>
                            </h1>
                        </div>
                    </section>
                    <div className="container is-fluid" id="searchcontainer">
                        <div className="box" id="pwdchange">
                            <form onSubmit={this.handleSubmit} name='pwdchanger'>
                                <div className="field">
                                    <label className="label" style={{textAlign: "left", fontSize: "1.1rem"}}>Enter Old Password</label>
                                    <div className="control">
                                        <input className="input " type="text" value={this.state.tempval} onChange={this.handleChange} name="old" placeholder="Enter old password"></input>
                                    </div>
                                </div>
                                <div className="field">
                                    <label className="label" style={{textAlign: "left", fontSize: "1.1rem"}}>Enter New Password</label>
                                    <div className="control">
                                        <input className="input " type="text" value={this.state.tempval2} onChange={this.handleChange} name="new" placeholder="Enter new password"></input>
                                    </div>
                                </div>
                                <div className="field">
                                    <label className="label" style={{textAlign: "left", fontSize: "1.1rem"}}>Confirm New Password</label>
                                    <div className="control">
                                        <input className="input " type="text" value={this.state.tempval3} onChange={this.handleChange} name="confirmnew" placeholder="Confirm new password"></input>
                                    </div>
                                    <br></br>
                                    <input className="button is-light is-small" type="submit" value="Update" name="submitbutton" onSubmit={this.handleSubmit} id="submitbutton"></input>
                                </div>
                                
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}


