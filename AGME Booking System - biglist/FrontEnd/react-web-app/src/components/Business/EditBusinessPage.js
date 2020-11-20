import React from 'react'
import "../../App.scss"
import HomePageHeader from '../HomePage/HomePageHeader'
import * as Constants from "../../../src/constants"

export default class EditBusinessPage extends React.Component{
    constructor(props){
        super(props)
        this.state = {
            businessTime: [],
            busid: this.props.match.params.id
        }
        this.updateBusiness = this.updateBusiness.bind(this)
        this.updateBusinessTime = this.updateBusinessTime.bind(this)
    }

    //API call for getting the business info 
    componentDidMount(){
        var businessApi = Constants.BACKEND_URL + "/api/Business/findById=" + this.state.busid;

        fetch(businessApi)
            .then(response => response.json())
            .then(data => {
                this.setState({
                    business: data
                });
                document.getElementById("name").value = data.name;
                document.getElementById("blurb").value = data.blurb;
                document.getElementById("description").value = data.description;
                document.getElementById("address").value = data.address;
                document.getElementById("phoneNumber").value = data.phoneNumber;
            })
        var businessTimeApi = Constants.BACKEND_URL + "/api/BusinessHours/findByBusId=" + this.state.busid;

        fetch(businessTimeApi)
            .then(response => response.json())
            .then(data =>{
                var dataArray = []

                for(var i = 0; i < 7;i++){
                    dataArray.push(data[i])
                }
                this.setState({
                    businessTime: dataArray
                })

                for(var i = 1; i <= 7;i++){
                    var opening = i + "Opening"
                    var closing = i + "Closing";
                    document.getElementById(opening).value = data[i - 1].openingTime;
                    document.getElementById(closing).value = data[i - 1].closingTime;
                }
            }) 
    }

    // API call of put to update the business info
    updateBusiness(){
        var updateBusiness = {
            id: this.state.busid,
            name: document.getElementById("name").value,
            blurb: document.getElementById("blurb").value,
            description: document.getElementById("description").value,
            address: document.getElementById("address").value,
            phoneNumber: document.getElementById("phoneNumber").value
        }

        var apiupdate = Constants.BACKEND_URL + '/api/Business/update=' + this.state.busid
        fetch(apiupdate,{
            method: 'PUT',
            headers: {
                'Accept': "application/json, text/plain, */*",
                'Content-Type': "application/json;charset=utf-8"
            },
            body: JSON.stringify(updateBusiness)
        })
        this.updateBusinessTime();
    }

    // does 7 businessAPI call to update the businessHours
    updateBusinessTime(){
        for(var i = 1; i < 8;i++){
            var apicall = Constants.BACKEND_URL + '/api/BusinessHours/update=' + this.state.businessTime[i - 1].id
            var opening = i + "Opening"
            var closing = i + "Closing"
            var updatebusinessTime = {
                id: this.state.businessTime[i - 1].id,
                day: i,
                business_id: this.state.businessTime[i - 1].business_id,
                openingTime: document.getElementById(opening).value + ":00",
                closingTime: document.getElementById(closing).value + ":00"
            }
            fetch(apicall,{
                method: 'PUT',
                headers: {
                    'Accept': "application/json, text/plain, */*",
                    'Content-Type': "application/json;charset=utf-8"
                },
                body: JSON.stringify(updatebusinessTime)
            })
        }
    }

    // html render on page
    render(){
        return(
            <div>
                <HomePageHeader/>
                <div id="hpcontent" style={{height: "100vh"}}>
                    <section className="header" id="userherobanner">
                        <div className="container" style={{height: "115px", paddingTop: "30px"}}>
                            <h1 className="title has-text-centered">
                                <span style={{ fontWeight: "bold", color: "white", fontSize: "3.8vh"}}>Edit Business</span>
                            </h1>
                        </div>
                    </section>
                    <div className="container has-text-centered" id="businesslistcontainer">
                        <div className = "column is-12">
                            <div className="box" style={{height: "75vh", overflowY: "auto"}}>
                                <form onSubmit={this.updateBusiness}>
                                    <div className = "container">
                                        <div className="columns is-ventered">
                                            <div className="column is-narrow">
                                                <h3 className="subtitle is-2 has-text-centered">General info:</h3>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Name:</label>
                                                    </div>
                                                    <div className="control is-expanded">
                                                        <input 
                                                            className="input" 
                                                            type="text" 
                                                            id="name" 
                                                            placeholder="Business Name" 
                                                            required>
                                                        </input>
                                                    </div>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Blurb: </label>
                                                    </div>
                                                    <div className="control is-expanded">
                                                        <textarea 
                                                            className="textarea"
                                                            id="blurb" 
                                                            placeholder="Business Blurb" 
                                                            required>
                                                        </textarea>
                                                    </div>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Description:</label>
                                                    </div>
                                                    <div className="control is-expanded">
                                                        <textarea 
                                                            className="textarea" 
                                                            id="description" 
                                                            placeholder="Business Blurb" 
                                                            required>
                                                        </textarea>
                                                    </div>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Address:</label>
                                                    </div>
                                                    <div className="control is-expanded">
                                                        <input 
                                                            className="input" 
                                                            type="text" 
                                                            id="address" 
                                                            placeholder="Business Address" 
                                                            required>
                                                        </input>
                                                    </div>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Phone Number:</label>
                                                    </div>
                                                    <div className="control">
                                                        <input 
                                                            className="input" 
                                                            type="tel" 
                                                            id="phoneNumber" 
                                                            placeholder="Business Phone Number" 
                                                            required>   
                                                        </input>
                                                    </div>
                                                </div>
                                                <div className="field is-grouped">
                                                    <div className="control">
                                                        <button className="button" id="submitbutton" style={{height: "40px"}} type="submit">Submit</button>
                                                    </div>
                                                    <div className="control">
                                                        <button className="button is-light" type="reset">Reset</button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="column is-6 is-offset-1">
                                                <div className="pt-5">
                                                    <h3 className="subtitle is-2 has-text-centered">Business Hours:</h3>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Monday:</label>
                                                    </div>
                                                    <div className="field-body">
                                                        <input type="time" id="1Opening"></input>
                                                    </div>
                                                    <label className="label mx-3">TO</label>
                                                    <div className="field-body">
                                                        <input type="time" id="1Closing"></input>
                                                    </div>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Tuesday:</label>
                                                    </div>
                                                    <div className="field-body">
                                                        <input type="time" id="2Opening"></input>
                                                    </div>
                                                    <label className="label mx-3">TO</label>
                                                    <div className="field-body">
                                                        <input type="time" id="2Closing"></input>
                                                    </div>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Wednesday:</label>
                                                    </div>
                                                    <div className="field-body">
                                                        <input type="time" id="3Opening"></input>
                                                    </div>
                                                    <label className="label mx-3">TO</label>
                                                    <div className="field-body">
                                                        <input type="time" id="3Closing"></input>
                                                    </div>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Thursday:</label>
                                                    </div>
                                                    <div className="field-body">
                                                        <input type="time" id="4Opening"></input>
                                                    </div>
                                                    <label className="label mx-3">TO</label>
                                                    <div className="field-body">
                                                        <input type="time" id="4Closing"></input>
                                                    </div>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Friday:</label>
                                                    </div>
                                                    <div className="field-body">
                                                        <input type="time" id="5Opening"></input>
                                                    </div>
                                                    <label className="label mx-3">TO</label>
                                                    <div className="field-body">
                                                        <input type="time" id="5Closing"></input>
                                                    </div>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Saturday:</label>
                                                    </div>
                                                    <div className="field-body">
                                                        <input type="time" id="6Opening"></input>
                                                    </div>
                                                    <label className="label mx-3">TO</label>
                                                    <div className="field-body">
                                                        <input type="time" id="6Closing"></input>
                                                    </div>
                                                </div>
                                                <div className="field is-horizontal">
                                                    <div className="field-label">
                                                        <label className="label">Sunday:</label>
                                                    </div>
                                                    <div className="field-body">
                                                        <input type="time" id="7Opening"></input>
                                                    </div>
                                                    <label className="label mx-3">TO</label>
                                                    <div className="field-body">
                                                        <input type="time" id="7Closing"></input>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}