import React, { Component } from "react";
import { Link } from 'react-router-dom';
import background from '../../images/landing-background.jpg';
import '../../css/LandingPage.css';

class LandingPage extends Component {
    render() {
        return(
            <div className="main-div">

            <img className="fit-picture" src={background} alt="computer-background"></img>

            <div className="overlay"></div>
                <div className="heading">
                    <h1 className="head">WELCOME <span>HOME</span></h1>
                    <br></br>
                    <h3 className="subheading">Welcome back to the app! Click the button below to view your files.</h3>	
                    <div className="buttons">
                        <Link to="/myfiles">
                            <button className="button">View my files</button>
                        </Link>
                    </div>	
                </div>
                
            </div>       
        );
    }
}

export default LandingPage;