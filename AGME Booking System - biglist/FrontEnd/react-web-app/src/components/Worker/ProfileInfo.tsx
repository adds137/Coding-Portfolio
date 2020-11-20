import React, { Component } from 'react'
import './Worker.scss'
import 'bulma/css/bulma.css'
import * as Constants from "../../../src/constants"


class ProfileInfo extends Component<any, any> {

    constructor(props : any) {
        super(props);
        this.state = {
            items: [],
            isLoaded: false,
            business: {}
        };
    }

    componentDidMount() {
        //Fetch worker information
        fetch(Constants.BACKEND_URL + '/api/worker/' + this.props.workerId).then(res => res.json())
        .then(json => {
            this.setState({
                isLoaded: true,
                items: json,
            })
            //Fetch the business that belongs to the worker
            fetch(Constants.BACKEND_URL + "/api/Business/findById=" + json.busId)
            .then(response => response.json())
            .then(json => {
                this.setState({
                    business: json
                })
            })
        });
    }

    render() {
        var business;
        //State items of this component
        var {isLoaded, items, user, business} = this.state;
        var worker = this.state.items;

        //Will be assigned based on whether the fetch was successful or not
        var user = null;
        var business = null;

        //Will check if the data has been fetched correctly
        if(items.user === undefined && business === undefined){
            user = "not found"
            business = "business not found"
        }
        //If fetched correctly then get the first name and last name of this worker
        else{
            user = items.user
            business = this.state.business;
        }

        return(
            <div className="worker-profile-info">
                <p><div className="subtitle">User Name:</div> {" " + user.userName}</p>
                <p><div className="subtitle">Name:</div>{" " + user.firstName}</p>
                <p><div className="subtitle">Last Name:</div> {" " + user.lastName}</p>
                <p><div className="subtitle">Account Creation Date:</div> {" " + items.createdAt}</p>
                {
                    items.updatedAt != null ?
                    <p><div className="subtitle">Last Account Update:</div> {" " + items.updatedAt}</p>
                    :<p><div className="subtitle">Last Account Update:</div>{" never"}</p>
                }
                <p><div className="subtitle">Businesses:</div> {" " + business.name}</p>
            </div>
        )
    }
}

export default ProfileInfo;