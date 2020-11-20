import React, { Component } from 'react'
import "../../App.scss"
import { BrowserRouter as Router,Link } from "react-router-dom";

interface BBProps {
    name: string;
    id: number;
    desc: string;
    phoneNumber: string;
}
interface BBState {
    renderl: string;
    name: string;
    id: number;
    desc: string;
    phoneNumber: string;
}

export default class HomePageBusinessBox extends Component<BBProps, BBState> {

    componentDidMount() {
        this.setState({
            name: this.props.name,
            id: this.props.id,
            desc: this.props.desc,
            phoneNumber: this.props.phoneNumber,
        })
    }

    render() {
        // if(this.props.desc.length > 50) {
        //     const temp = this.props.desc.substring(0, 50) + "...";
        // }else{
        //     const desc = this.props.desc
        // }

        return (
            <div>
                <Link to={{
                    pathname: "/BusinessPage/"+this.props.id,
                    state: {businessId: this.props.id},
                }}>
                    <div className="columns is-desktop" id="buslvl">
                    <div className="column is-5">
                            <div>
                                <p className="heading">Business</p>
                                <p className="title" id="paddingtest">{this.props.name}</p>
                            </div>
                        </div>
                        <div className="column is-1" id="bushide" >
                            <div>
                                <p className="heading">Phone</p>
                                <p style={{textAlign: "left"}}>{this.props.phoneNumber}</p>
                            </div>
                        </div>
                        <div className="column is-1" id="bushide">
                            <div>
                                <p></p>
                            </div>
                        </div>
                        <div className="column is-5" id="bushide">
                            <div>
                                <p className="heading">About</p>
                                <p>{this.props.desc.length < 50 ? this.props.desc : this.props.desc.substring(0,50)+"..."}</p>
                            </div>
                        </div>
                    </div>

                    <div className="columns" id="onlyname">
                        <div className="column">
                            <div>
                                <p className="heading">Business</p>
                                <p className="title" id="paddingtest">{this.props.name}</p>
                            </div>
                        </div>
                    </div>

                </Link>
            </div>
        )
    };
}
        // return (
        //     <div className="businessbox">
        //         <Link to={{
        //             pathname: "/BusinessPage/"+this.props.id,
        //             state: {businessId: this.props.id},
        //         }}>
        //         <div className="box" id="businessboxcontent">
        //             <article className="media">
        //                 <div className="media-left">
        //                     <figure className="image is-128x128" id="busimg">
        //                         <img src={require("./Images/businessimg.png")} alt="bus-img"/>
        //                     </figure>
        //                 </div>
        //                 <div className="media-content">
        //                     <div className="content">
        //                         <h1>{this.props.name}</h1>
        //                         <p id="bustext">{this.props.desc}</p>
        //                     </div>
        //                 </div>
        //             </article>
        //         </div>
        //         <p></p>
        //         </Link>
        //     </div>
        // )

        // return (
        //     <div>
                // <Link to={{
                //     pathname: "/BusinessPage/"+this.props.id,
                //     state: {businessId: this.props.id},
                // }}>
        //             <div className="level" id="buslvl">
        //                 <div className="level-item">
        //                     <div>
        //                         <figure className="image is-128x128" id="busimg">
        //                             <img src={require("./Images/businessimg.png")} alt="bus-img"/>
        //                         </figure>
        //                     </div>
        //                 </div>

        //                 <div className="level-item has-text-centered">
        //                     <div>
        //                         <p className="heading">Business</p>
        //                         <p className="title">{this.props.name}</p>
        //                     </div>
        //                 </div>

        //                 {/* <div className="level-item">
        //                     <p></p>    
        //                 </div> */}

        //                 <div className="level-item">
        //                     <div>
        //                         <p className="heading">Description</p>
        //                         <p>{this.props.desc.length < 50 ? this.props.desc : this.props.desc.substring(0,50)+"..."}</p>
        //                     </div>
        //                 </div>
                        
        //                 <div className="level-item">
        //                     <p></p>
        //                 </div>
        //             </div>
        //         </Link>
        //     </div>
        // )