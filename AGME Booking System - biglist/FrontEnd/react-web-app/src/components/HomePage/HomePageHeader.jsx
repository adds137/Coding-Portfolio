import React, { Component, useEffect } from 'react'
import '../../App.scss';
import { BrowserRouter as Router, Link } from "react-router-dom";
import NavSearch from './NavSearch';
import HomePageTitle from './HomePageTitle';
import { getUsername } from '../../actions/JWT';
import { Redirect } from "react-router-dom";
import  Cookies  from 'js-cookie';
import { TOKEN_NAME } from '../../actions/types';

export default class HomePageHeader extends Component {

    constructor(props) {
        super(props);

        this.state = {
            isLoggedOut: false,
        }

        this.userLoginSignup = this.userLoginSignup.bind(this);
        this.userLogout = this.userLogout.bind(this);
        this.UserContext = this.UserContext.bind(this);
        this.onLogout = this.onLogout.bind(this);
    }

    onLogout = () => {
        Cookies.remove(TOKEN_NAME)
        this.setState({
            isLoggedOut: true
        })
    }

    render() {

        let redirect;
        if (this.state.isLoggedOut) {
            redirect = <Redirect to="/" />;
        }

        let currentUser = getUsername();
        let userDash;
        if (currentUser) {
            userDash = 
            (<div className="navbar-item">
                <Link to={"/UserHomePage/" + currentUser}>Your Dashboard</Link>
            </div>);
        }
        return (
            <div>
                {redirect}
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />

                <nav className="navbar" role="navigation" aria-label="main navigation">
                    <div className="navbar-brand">
                        <div className="navbar-item">
                            <HomePageTitle />
                        </div>
                    
                        <label
                            role="button"
                            className="navbar-burger burger"
                            aria-label="menu"
                            aria-expanded="false"
                            htmlFor="nav-toggle-state"
                        >
                            <span aria-hidden="true" />
                            <span aria-hidden="true" />
                            <span aria-hidden="true" />
                        </label>
                    </div>
                    <input type="checkbox" id="nav-toggle-state" />

                    <div className="navbar-menu">
                        <div className="navbar-start">
                            <div className="navbar-item" id="burgerzoomed">
                                <NavSearch />
                            </div>
                            {userDash}
                            <div className="navbar-item">
                                <Link to="/Worker">Worker</Link>
                            </div>
                            <div className="navbar-item">
                                <Link to="/BusinessPage/">BusinessPageTemp</Link>
                            </div>
                            <div className="navbar-item has-dropdown is-hoverable">
                                <div className="navbar-link" id="dropdown">
                                    More
                                    </div>
                                <div className="navbar-dropdown">
                                    <div className="navbar-item" id="bushide">
                                        <Link to="/Contact">Contact Us</Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <this.UserContext />
                    </div>
                </nav>
            </div>
        )
    }

    userLoginSignup() {
        return (
            <div className="buttons">
                <div className="button is-primary" id="signup">
                    <Link to="/Signup"><span style={{ fontWeight: "bold", color: "white" }}>Sign up</span></Link>
                </div>
                <div className="button is-light">
                    <Link to="/Login">Log in</Link>
                </div>
            </div>
        )
    }

    userLogout() {
        return (
            <div className="buttons">
                <div className="button is-primary" onClick={this.onLogout}>
                    <span style={{ fontWeight: "bold", color: "white" }}>Log Out</span>
                </div>
            </div>
        )
    }

    UserContext() {
        if (getUsername()) {
            return this.userLogout();
        } else {
            return this.userLoginSignup();
        }
    }
}