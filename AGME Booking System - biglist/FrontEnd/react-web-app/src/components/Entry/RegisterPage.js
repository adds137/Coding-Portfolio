import React, { Component } from 'react'
import { BrowserRouter as Router,Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import {registerAction} from "../../actions/registerAction";
import HomePageHeader from '../HomePage/HomePageHeader';
import * as Constants from "../../../src/constants"

class RegisterPage extends Component {
    constructor(){
        super();
        this.state ={
            userName:"",
            firstName:"",
            lastName:"",
            phoneNumber:"",
            homeAddress:"",
            password: "",
            passwordConfirmation: "",

            businessName: "",
            businessblurb: "",
            businessdescription: "",
            businessAddress: "",
            businessPhoneNumber: "",

            businesses:[],
            loading: true,
            id: 1
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    async componentDidMount(){
        // Gets all businesses and stores them in this.state.businesses, the loading state is set to false.
        fetch(Constants.BACKEND_URL + "/api/Business/")
            .then(response => response.json())
            .then(data => {
                this.setState({
                    businesses: data,
                    loading: false
                })
            })
            console.log(this.state.businesses);
    }

    onChange(e){
        this.setState({[e.target.name]: e.target.value});
    }
    onSubmit(e){
        e.preventDefault();
        const newUser = {
            userName: this.state.userName,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            phoneNumber: this.state.phoneNumber,
            homeAddress: this.state.homeAddress,
            password: this.state.password,
            passwordConfirmation: this.state.passwordConfirmation
        }

        const newBusiness = {
            name: this.state.businessName,
            blurb: this.state.businessblurb,
            description: this.state.businessdescription,
            address: this.state.businessAddress,
            phoneNumber: this.state.businessPhoneNumber,
        }

        const newWorkerDetails = {
            id: this.state.id
        }
        console.log(newUser);
        console.log(newBusiness);
        console.log(newWorkerDetails);

        if(document.getElementById("customer").checked){
            this.props.registerAction(newUser, newBusiness, newWorkerDetails, 1, this.props.history);
        } else if(document.getElementById("business").checked){
            this.props.registerAction(newUser, newBusiness, newWorkerDetails, 2, this.props.history);
        }
        else if(document.getElementById("worker").checked){
            this.props.registerAction(newUser, newBusiness, newWorkerDetails, 3, this.props.history);
        }
    }

    checkboxchange(e){
        var bussection = document.getElementById("Sectionbusiness");
        var custsection = document.getElementById("Sectioncustomer");
        var workersection = document.getElementById("Sectionworker");
        bussection.style.display = "none";
        custsection.style.display = "none";
        workersection.style.display = "none";

        if(document.getElementById("customer").checked){
            custsection.style.display = "block";
        }
        else if(document.getElementById("business").checked){
            bussection.style.display = "block";
        }
        else if(document.getElementById("worker").checked){
            workersection.style.display = "block";
        }
    }
    render() {
        const slice = this.state.businesses.map(business =>
            <option key={business.id} value={business.id} onChange = {this.onChange}>{business.name}</option>
        )
        return (
            <div>
                <HomePageHeader/>
                <div id="hpcontent" style={{height: "100vh"}}>
                    <section className="header" id="userherobanner">
                        <div className="container" style={{height: "115px", paddingTop: "30px"}}>
                            <h1 className="title has-text-centered">
                                <span style={{ fontWeight: "bold", color: "white", fontSize: "3.2vh"}}>Register</span>
                            </h1>
                        </div>
                    </section>
                        <div className = "container has-text-centered" id="businesslistcontainer">
                            <div className = "column is-8 is-offset-2">
                                <div className = "box" style={{overflowY: "auto", height: "75vh"}}>
                                    <div className = "box">
                                    <Link to="/" >
                                        <span className="title" id="brand" style={{color: "black"}}>AGME</span>
                                    </Link>
                                    </div>
                                    <div className = "title has-text-grey is-5">
                                        Please fill in your details.
                                    </div>
                                    <form id="register" onSubmit={this.onSubmit}>
                                        <div className = "field">
                                            <div className = "control has-text-left">
                                                Username:
                                                <input className="input is-small" type="text" placeholder="Username" 
                                                name="userName" value={this.state.userName} onChange = {this.onChange}>
                                                </input>
                                            </div>
                                        </div>
                                        <div className = "field">
                                            <div className = "control has-text-left">
                                                First Name:
                                                <input className = "input is-small" type="text" placeholder="First Name" 
                                                name="firstName" value={this.state.firstName} onChange = {this.onChange}>
                                                </input>
                                            </div>
                                        </div>
                                        <div className = "field">
                                            <div className = "control has-text-left">
                                                Last Name:
                                                <input className = "input is-small" type="text" placeholder="Last Name" 
                                                name="lastName" value={this.state.lastName} onChange = {this.onChange}>
                                                </input>
                                            </div>
                                        </div>
                                        <div className = "field">
                                            <div className = "control has-text-left">
                                                Phone Number:
                                                <input className = "input is-small" type="text" placeholder="Phone Number" 
                                                name="phoneNumber" value={this.state.phoneNumber} onChange = {this.onChange}>
                                                </input>
                                            </div>
                                        </div>
                                        <div className = "field">
                                            <div className = "control has-text-left">
                                                Address:
                                                <input className = "input is-small" type="text" placeholder="Home Address" 
                                                name="homeAddress" value={this.state.homeAddress} onChange = {this.onChange}>
                                                </input>
                                            </div>
                                        </div>
                                        <div className = "field">
                                            <div className = "control has-text-left">
                                                Password:
                                                <input className = "input is-small" type="password" placeholder="Password"
                                                name="password" value={this.state.password} onChange = {this.onChange}>
                                                </input>
                                            </div>
                                        </div>
                                        <div className = "field">
                                            <div className = "control has-text-left">
                                                Confirm Password:
                                                <input className = "input is-small" type="password" placeholder="Password"
                                                name="passwordConfirmation" value={this.state.passwordConfirmation} onChange = {this.onChange}>
                                                </input>
                                            </div>
                                        </div>
                                        <div className="control" onChange={this.checkboxchange}>
                                            <label className="is-size-6 mx-5">User Type:</label>
                                            <br></br>
                                            <label className="radio mx-5">
                                            <input type="radio" name="usertype" id="customer"></input>
                                            &nbsp;Customer
                                            </label>
                                            <label className="radio mx-5">
                                            <input type="radio" name="usertype" id="business"></input>
                                            &nbsp;Business Owner
                                            </label>
                                            <label className="radio mx-5">
                                            <input type="radio" name="usertype" id="worker"></input>
                                            &nbsp;Worker
                                            </label>
                                        </div>
                                        <div id="Sectionbusiness" hidden>
                                            <div className = "field">
                                                <div className = "control has-text-left">
                                                    Business Name:
                                                    <input className="input is-small" type="text" placeholder="Business Name" 
                                                    name="businessName" value={this.state.businessName} onChange = {this.onChange}>
                                                    </input>
                                                </div>
                                            </div>
                                            <div className = "field">
                                                <div className = "control has-text-left">
                                                    Business Blurb:
                                                    <textarea className="textarea is-small" type="text" placeholder="Business Blurb" 
                                                    name="businessblurb" value={this.state.businessblurb} onChange = {this.onChange}>
                                                    </textarea>
                                                </div>
                                            </div>
                                            <div className="field">
                                                <div className = "control has-text-left">
                                                    Business description:
                                                    <textarea className="textarea is-small" type="text" placeholder="Business Description" 
                                                    name="businessdescription" value={this.state.businessdescription} onChange = {this.onChange}>
                                                    </textarea>
                                                </div>
                                            </div>
                                            <div className = "field">
                                                <div className = "control has-text-left">
                                                    Business Address:
                                                    <input className="input is-small" type="text" placeholder="Business Address" 
                                                    name="businessAddress" value={this.state.businessAddress} onChange = {this.onChange}>
                                                    </input>
                                                </div>
                                            </div>
                                            <div className = "field">
                                                <div className = "control has-text-left">
                                                    Business Phone Number:
                                                    <input className="input is-small" type="text" placeholder="Business Phone Number" 
                                                    name="businessPhoneNumber" value={this.state.businessPhoneNumber} onChange = {this.onChange}>
                                                    </input>
                                                </div>
                                            </div>
                                            <button type="submit" className="button is-block is-medium has-text-white is-fullwidth" style={{backgroundColor: "rgb(247, 71, 71)",
                                                fontWeight: "bold"}}>Sign Up Business</button>
                                        </div>
                                        <div id="Sectionworker" hidden>
                                        <div className = "control has-text-left">
                                            Select a business:
                                            <select className = "input is-small" type="business" placeholder="Business"
                                            name="id" value={this.state.id} onChange = {this.onChange}>
                                            {slice}
                                            </select>
                                        </div>
                                        <button type="submit" className="button is-block has-text-white is-medium is-fullwidth" style={{backgroundColor: "rgb(247, 71, 71)",
                                            fontWeight: "bold"}}>Sign Up Worker</button>
                                    </div>
                                        <div id="Sectioncustomer" hidden>
                                            <button type="submit" className="button is-block is-medium has-text-white is-fullwidth" style={{backgroundColor: "rgb(247, 71, 71)",
                                                fontWeight: "bold"}}>Sign Up Customer</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <p className="has-text-grey">
                                Already Signed Up?&nbsp;·&nbsp;
                                <Link to="/Login">
                                <a>Login</a>
                                </Link>
                                <div>
                                    <br></br>
                                </div>
                            </p>
                        </div>
                    </div>
            </div>
        )
    }
}
RegisterPage.propTypes = {
    createProject: PropTypes.func.isRequired
  };
export default connect(
    null,
    { registerAction }
  )(RegisterPage);