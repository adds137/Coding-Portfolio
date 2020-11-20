import React, { Component } from 'react'
import { BrowserRouter as Router,Link } from "react-router-dom";
import "../../App.scss"

export default class NoMatch extends Component {
    

    render() {
        return (
            <div>
                <div className="title has-text-centered" style={{marginTop: "50px", fontWeight: "bold"}}>404 Page Not Found</div>
                <div className="has-text-centered">
                    <Link to="/"><div className="button" id="submitbutton" style={{width: "150px"}}>Agme Homepage</div></Link>
                </div>
            </div>
        );
    }
}