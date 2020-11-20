import React, { Component } from 'react'
import "../../App.scss"
import HomePageBusinessBox from './HomePageBusinessBox';
import HomePageHeader from './HomePageHeader';
import axios from 'axios';
import * as Constants from "../../../src/constants"

export default class HomePageContent extends Component {
    constructor(props) {
        super(props);
     
        this.state = {
           loading: true,
           businesses: [],
        };
      }
    
    async componentDidMount(){
        // Gets all businesses and stores them in this.state.businesses, the loading state is set to false.
        await axios
        .get(Constants.BACKEND_URL + '/api/Business')
        .then(({ data })=>{
            this.setState({
                businesses: data,
                loading: false
            })
        })

        // fetch("http://localhost:8080/api/Business/")
        //     .then(response => response.json())
        //     .then(data => {
        //         this.setState({
        //             businesses: data,
        //             loading: false
        //         })
        //     })
    }

    render() {
        /*
         * Slices the businesses from the getAllBusinesses API so that only the first 5 are displayed.
         * As a unique key is required, the business name and phone number are added together.
         * The HomePageBusinessBox is created for each of the 5 businesses.
        */ 
        const slice = this.state.businesses.slice(0, 5).map(business =>
            <div key={business.id}>
                <HomePageBusinessBox name={business.name} id={business.id} desc={business.description} phoneNumber={business.phoneNumber}/>
            </div>   
        )

        // State is loading if the component is accessing, or unable to access, the API
        if(this.state.loading) {
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
                            <div className="box" id="businesslist">
                                <p id="loading"> Loading Businesses . . . </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
            );
        }
        // business.length is ! when the component can access the API but no businesses are present
        if(!this.state.businesses.length) {
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
                            <div className="box" id="businesslist">
                                <p id="none"> No Businesses Found </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
            );
        }
        // If the component's loading state is false and there are businesses present, the slice constant is rendered
        return (
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
                            <div className="box" id="businesslist">
                                <span className="title" style={{fontWeight: "bold"}}>Popular Today</span>
                                <p></p>
                                <span className="heading" id="bushide">Book an appointment with one of these trending service providers</span>
                                <p></p>
                                {slice}
                                {/* {this.state.businesses.map(business => (
                                    <div key={business.name + business.phoneNumber}>
                                        <HomePageBusinessBox name={business.name} id={business.id} desc={business.description} />
                                    </div>
                                ))} */}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        )
    }
}