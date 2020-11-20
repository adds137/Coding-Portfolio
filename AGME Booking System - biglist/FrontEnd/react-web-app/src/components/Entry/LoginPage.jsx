import React, { Component } from 'react'
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import HomePageHeader from "../HomePage/HomePageHeader";
import axios from "axios";
import { TOKEN_PREFIX } from "../../actions/types";
import { getJWT, getUsername } from '../../actions/JWT';

class LoginPage extends Component {
    constructor() {
        super();
        this.state = {
            userName: "",
            password: "",
            error: false,
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }

    async onSubmit(e) {
        e.preventDefault();
        const loginUser = {
            userName: this.state.userName,
            password: this.state.password
        }
        let success = await loginFunction(loginUser);
        if (success) {
            this.props.history.push("/")
        } else {
            this.setState({
                error: true
            });
        };
    }
    
    render() {
        let errorMessage;
        if (this.state.error) {
            errorMessage = (<article className="message is-danger">
                <div class="message-body">
                    User credentials were incorrect!! Try again!
            </div>
            </article>);
        }
        return (
            <div>
                <HomePageHeader />
                <div id="hpcontent" style={{ height: "100vh" }}>
                    <section className="header" id="userherobanner">
                        <div className="container" style={{ height: "115px", paddingTop: "30px" }}>
                            <h1 className="title has-text-centered">
                                <span style={{ fontWeight: "bold", color: "white", fontSize: "3.2vh" }}>Login</span>
                            </h1>
                        </div>
                    </section>
                    <p></p>
                    <br></br>
                    <p></p>
                    <br></br>

                    <div className="container has-text-centered">
                        {errorMessage}
                        {this.loginForm()}
                        <p className="has-text-grey">
                            <Link to="/Signup">
                                <span style={{ color: "dodgerblue" }}>Sign Up</span>&nbsp;·&nbsp;
                            </Link>
                            <Link to="/Contact">
                                <span style={{ color: "dodgerblue" }}>Contact Us</span>
                            </Link>
                        </p>
                    </div>
                </div>
            </div>
        )
    }

    loginForm() {
        if (getUsername()) {
            return (<div className="column is-8 is-offset-2">
                <div className="box">
                    <div className="title has-text-grey is-5">
                        You're already logged in! Please Log out first!
                    </div>
                </div>
            </div>);
        } else {
            return (<div className="column is-8 is-offset-2">
                <div className="box">
                    <div className="box">
                        <Link to="/" >
                            <span className="title" id="brand" style={{ color: "black" }}>AGME</span>
                        </Link>
                    </div>
                    <div className="title has-text-grey is-5">
                        Please enter your username and password.
                                </div>
                    <form id="login" onSubmit={this.onSubmit}>
                        <div className="field">
                            <div className="control">
                                <input className="input is-medium" type="text" placeholder="Username"
                                    name="userName" value={this.state.userName} onChange={this.onChange}>
                                </input>
                            </div>
                        </div>
                        <div className="field">
                            <div className="control">
                                <input className="input is-medium" type="password" placeholder="Password"
                                    name="password" value={this.state.password} onChange={this.onChange}>
                                </input>
                            </div>
                        </div>
                        <button className="button is-block is-medium is-fullwidth has-text-white" style={{
                            backgroundColor: "rgb(247, 71, 71)",
                            fontWeight: "bold"
                        }}>Login</button>
                    </form>
                </div>
            </div>);
        }
    }
}

// Returns false if the loginfailed. Returns true if the login succeeded.
async function loginFunction(loginUser) {
    try {
        const formData = new FormData();
        formData.append("username", loginUser.userName);
        formData.append("password", loginUser.password);
        const response = await axios.post("http://localhost:8080/login", formData, { withCredentials: true });
    } catch {
        return false;
    }
    return true;
};

export default LoginPage;
