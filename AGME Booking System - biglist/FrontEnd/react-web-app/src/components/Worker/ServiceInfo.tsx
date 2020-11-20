import React, { Component } from 'react'
import './Worker.scss'
import 'bulma/css/bulma.css'
import * as Constants from "../../../src/constants"


class ServiceInfo extends Component<any, any> {

    constructor(props : any) {
        super(props);
        this.state = {
            items: [],
            isLoaded: false,
        };
    }

    componentDidMount() {
        fetch(Constants.BACKEND_URL + '/api/worker/' + this.props.workerId).then(res => res.json())
        .then(json => {
            this.setState({
                isLoaded: true,
                items: json,
            })
        });
    }

    render() {
        return(<div>asd</div>);
    }
}

export default ServiceInfo;