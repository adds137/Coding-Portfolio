import React, { Component } from 'react'
import "../../App.scss"
import HomePageHeader from '../HomePage/HomePageHeader';
import SearchForm from './SearchForm';


export default class SearchPage extends Component {
    constructor(props) {
        super(props)
        this.state = {
            searchterm: ''
        }
    }

    // The pathname is dependent on the NavSearch component, and used to determine the searchterm
    async componentDidMount() {
        // const pathname = window.location.pathname;
        // const sub = pathname.indexOf("Search/");
        // const tofind = pathname.substring(pathname.length, sub+7);
        // const tofind  = this.props.searchterm
        const tofind = this.props.match.params.searchid
        this.setState({
            searchterm: tofind,
            searchprop: this.props.searchterm
        })
    
    }

    render() {
        return(
            <div>
                <div className="content" id="hpcontent">
                    <HomePageHeader/>
                    <div className="hero is-primary">
                        <div className="hero-body" id="searchhero">
                            <h1 className="title" id="searchtitle" style={{fontWeight: "bold", fontSize: "4vh"}}>Search the AGME database</h1>
                        </div>
                    </div>
                    <div className="container is-fluid" id="searchcontainer">
                        <div className="box" id="searchlist">
                            <SearchForm navset={true} searchid={this.props.match.params.searchid}/>
                        </div>
                        <div className="space"></div>
                    </div>
                </div>
            </div>
        );
    }
}