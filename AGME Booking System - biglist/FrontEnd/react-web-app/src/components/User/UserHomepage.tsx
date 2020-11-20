import React, { useState, useEffect, } from 'react'
import "../../../src/App.scss"
import { useParams } from 'react-router-dom';
import HomePageHeader from '../HomePage/HomePageHeader';
import { BrowserRouter as Router,Link } from "react-router-dom";
import {Redirect} from "react-router-dom";
import {getInstance} from "../../actions/axiosInstance";

interface RouterParams {
    username: string
}

export default function UserHomepage() {
    useEffect(() => {
        fetchUser();
    }, []);
    const { username } = useParams<RouterParams>();
    const [user, setUser] = useState({
        userName: "",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        homeAddress: "",
        createdAt: ""
    });

    const fetchUser = async () => {
        let axiostInst = getInstance();
        try {
            const fetchUser = await axiostInst.get(`http://localhost:8080/api/user/${username}`);
            const user = fetchUser.data;
            setUser(user);
        } catch {
            window.location.href = '/Unauthorized';
        }
    };

    var history = <div>
        <div className="box">
            <div className="title">
                <span style={{fontSize: "3vh"}}>{user.firstName} PlaceHolder</span>
            </div>
        </div>
    </div>

    var userinfo = <div className="columns is-mobile is-gapless" style={{paddingLeft: "2%"}}>
        <div className="column">
            <div>
                <span style={{fontWeight: "bold"}}>Username:</span>
            </div>
            <div>
                <span style={{fontWeight: "bold"}}>Phone:</span>
            </div>
            <div>
                <span style={{fontWeight: "bold"}}>Address:</span>
            </div>
            <div>
                <span style={{fontWeight: "bold", paddingRight: "3px"}}>Created:</span>
            </div>
        </div>
        <div className="column">
            <div>
                {user.userName}
            </div>
            <div>
                {user.phoneNumber}
            </div>
            <div>
                {user.homeAddress}
            </div>
            <div>
                {user.createdAt}
            </div>
        </div>
    </div>

    return (
        <div>
            <HomePageHeader />
            <div className="content" id="hpcontent">
                <section className="header" id="userherobanner">
                    <div className="container" style={{height: "130px", paddingTop: "30px"}}>
                        <h1 className="title has-text-centered">
                            <span style={{ fontWeight: "bold", color: "white", fontSize: "3.2vh"}}>{user.firstName} {user.lastName}'s Dashboard</span>
                        </h1>
                    </div>
                </section>
                <div className="columns is-gapless">
                    <div className="column is-3">
                        <div className="container is-fluid" id="usercontainer">
                            <div id="userprofile">
                                <span style={{fontWeight: "bold"}}>{user.firstName} {user.lastName}</span>
                                <div style={{width: "89%", border: "solid rgb(179, 179, 179) 0.5px", paddingLeft: "0px", marginTop: "5px",
                                            marginLeft: "20px", marginRight: "-20px"}}/>
                                <div style={{textAlign: "left", paddingLeft: "20px", paddingTop: "20px"}}>
                                    {userinfo}
                                    <p></p>
                                    <Link to={"/UserHomePage/EditUser/"+username}>
                                        <div className="button" id="submitbutton" style={{marginLeft: "23%"}}>Change Password</div>
                                    </Link>
                                    <div id="bushide">{/*Placeholder*/}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="column">
                        <div className="container is-fluid" id="searchcontainer">
                            <div className="box" id="searchlist" style={{marginLeft: "0px"}}>
                                <div className="heading">{user.userName}'s bookings</div>
                                {history}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

