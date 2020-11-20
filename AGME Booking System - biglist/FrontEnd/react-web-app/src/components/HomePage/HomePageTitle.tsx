import React, { Component } from 'react'
import '../../App.scss';
import { BrowserRouter as Router,Link } from "react-router-dom";


export default class HomePageTitle extends Component {
    render() {
        return (
            <div>
                <Link to="/"><span className="title" id="brand">AGME</span></Link>
            </div>
        );
    }
}