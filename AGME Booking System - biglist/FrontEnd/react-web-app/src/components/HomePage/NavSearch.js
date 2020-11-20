import React, { Component } from 'react'
import '../../App.scss';
import { BrowserRouter as Router,Link } from "react-router-dom";
import * as Constants from "../../../src/constants"

// Searchbar component within <HomePageHeader> to handle searches from any page the header is used in.
export default class NavSearch extends Component {
    constructor(props) {
        super(props);
        this.state = {
            search: '',
            set: false,
        };
    
        this.handleChange = this.handleChange.bind(this);
        this.reset = this.reset.bind(this);
    }
    
    // Handles the changes of the input field
    async handleChange(event) {
        var ser = event.target.value;
        this.setState({search: ser})
    }

    /*
     * On click of the search icon (mimicking submit), the URL is reset if on the /Search/ page,
     * else the value within the search input is reset.
    */
    reset() {
        if(window.location.pathname.includes("/Search/")) {
            window.history.replaceState(null, null, "/Search/"+this.state.search)
            window.location.reload()
        }
        this.state.search = ''
    }

    render() {
        return (
            <div className="field">
                <form action={Constants.FRONTEND_URL + "/Search/"+this.state.search}>
                    <div className="control">
                        <input className="text is-primary" id="navsearchbar" value={this.state.search} 
                            onChange={this.handleChange} placeholder="Search for a business" autoComplete="off"></input>
                        <label>
                            <Link to={{
                                pathname: "/Search/"+this.state.search,
                                state: {searchterm: this.state.search},
                            }} id="navsearchlink">
                                <span onClick={this.reset} id="searchspan">  <div className="fa fa-search" /></span>
                            </Link>
                        </label>
                    </div>
                </form>
            </div>
        )
    }
};