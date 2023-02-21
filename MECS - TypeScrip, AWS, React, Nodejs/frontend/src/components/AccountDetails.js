import React, { Component } from "react";
import '../css/AccountDetails.css';
import '../css/Button.css';
import jwt_decode from 'jwt-decode';
import AuthService from "../services/auth.service";
import { Navigate } from "react-router-dom";

class AccountDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            firstname: '',
            lastname: '',
            userid: '',
            ahpra_num: '',
            provider_num: '',
            prescriber_num: '',
            newEmail: '',
            confirmEmail: '',
            newPassword: '',
            confirmPassword: '',
            newAhpraNum: '',
            newProviderNum: '',
            newPrescriberNum: '',
            message: ''
        };
    }
    componentDidMount() {
        // check if user access token is in local storage
        const accessToken = localStorage.getItem("user");
        if (accessToken !== null) {
            const data = jwt_decode(accessToken);
            this.setState({
                email: data.email,
                firstname: data.first_name,
                lastname: data.last_name,
                userid: data.userid,
                ahpra_num: data.ahpra_num,
                provider_num: data.provider_num,
                prescriber_num: data.prescriber_num
            })
        }
    }

    handleChange = (e) => {
        this.setState({[e.target.id]: e.target.value});
    }

    setMsg(msg) {
        this.setState({message:msg});
    }

    checkEmailMatch = () => {
        if (this.state.newEmail !== this.state.confirmEmail) {
            this.setMsg("Emails do not match")
          return false;
        }
        return true;
    };

    checkPasswordMatch = () => {
        if (this.state.newPassword !== this.state.confirmPassword) {
            this.setMsg("Passwords do not match")
          return false;
        }
        return true;
    };

    handleSubmit = (e) => {
        e.preventDefault()
        if (this.checkEmailMatch() && this.checkPasswordMatch()) {
            this.setMsg("please wait...");
            AuthService.patchUser(this.state.userid,this.state.firstname,this.state.lastname,this.state.newEmail,this.state.newPassword,this.state.newAhpraNum,this.state.newProviderNum,this.state.newPrescriberNum)
                .then((res) => {
                    if(res.updateOutcome === true) {
                        this.setMsg(<div className="text-green-500">Details Changed Successfully, refresh to see changes</div>)
                    } else {
                        this.setMsg(res)
                    }
                })
        }
        
    }

    deleteAccount = () => {
        if (window.confirm("Are you sure you want to delete your account?")) {
            AuthService.deleteUser(this.state.userid)
            .then((res)=> {
                if (res) {
                    window.location.reload()
                }
            })
        }
    }

    render() {
        // redirect to login if not logged in
        if (localStorage.getItem("user") === null) {
            return <Navigate to="/login"/>
        }

        function openForm() {
            document.getElementById("change-form").style.visibility = "visible";
            document.getElementById("change-form").style.opacity = "1";
            document.getElementById("open-form-button").style.display = "none";
        }
        
        function closeForm() {
            document.getElementById("change-form").style.visibility = "hidden";
            document.getElementById("change-form").style.opacity = "0";
            document.getElementById("open-form-button").style.display = "block";
        }

        return(
            <div className="pt-4">
                <div className="w-full max-w-lg mx-auto py-4">
                    <h1 className="text-center text-4xl font-bold py-3">Account Details</h1>
                    <h3 className="text-center text-2xl">Your account details</h3>

                    <div className="flex flex-wrap -mx-3 mb-6">
                        <div className="w-full px-3">
                            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                First Name
                            </label>
                            <p className="appearance-none block w-full text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight">
                                {this.state.firstname}
                            </p>
                        </div>
                    </div>
                    <div className="flex flex-wrap -mx-3 mb-6">
                        <div className="w-full px-3">
                            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                Last Name
                            </label>   
                            <p className="appearance-none block w-full text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight">
                                {this.state.lastname}
                            </p>
                        </div>
                    </div>
                    <div className="flex flex-wrap -mx-3 mb-6">
                        <div className="w-full px-3">
                            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                Your E-mail
                            </label>
                            <p className="appearance-none block w-full text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight">
                                {this.state.email}
                            </p>
                        </div>
                    </div>
                    {/* User ID can be removed if user does not need to see it */}
                    <div className="flex flex-wrap -mx-3 mb-6">
                        <div className="w-full px-3">
                            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                User ID
                            </label>   
                            <p className="appearance-none block w-full text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight">
                                {this.state.userid}
                            </p>
                        </div>
                    </div>
                    {(this.state.ahpra_num || this.state.provider_num || this.state.prescriber_num) ? 
                        (<div>
                            <h1 className="text-center text-lg font-bold">Medical IDs</h1><hr/><br/>
                        </div>
                    ): null}
                    {/* Medical IDs*/}
                    {this.state.ahpra_num ? (
                    <div className="flex flex-wrap -mx-3 mb-6">
                        <div className="w-full px-3">
                            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                AHPRA Number
                            </label>   
                            <p className="appearance-none block w-full text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight">
                                {this.state.ahpra_num}
                            </p>
                        </div>
                    </div>) : null}
                    {this.state.provider_num ? (
                    <div className="flex flex-wrap -mx-3 mb-6">
                        <div className="w-full px-3">
                            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                Provider Number
                            </label>   
                            <p className="appearance-none block w-full text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight">
                                {this.state.provider_num}
                            </p>
                        </div>
                    </div>) : null}
                    {this.state.prescriber_num ? (
                    <div className="flex flex-wrap -mx-3 mb-6">
                        <div className="w-full px-3">
                            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                Prescriber Number
                            </label>   
                            <p className="appearance-none block w-full text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight">
                                {this.state.prescriber_num}
                            </p>
                        </div>
                    </div>) : null}
                </div>
                
                <hr className="w-full max-w-lg mx-auto py-3"></hr>
               
                <div className="w-full max-w-lg mx-auto py-4">
                    <div className="md:flex md:items-center">
                        
                            <button id="open-form-button" className="button" type="submit" onClick={openForm}>Change details</button>
                            <button id="delete-account-button" className="delete-account" type="submit" onClick={this.deleteAccount}>Delete Account</button>
                       
                    </div>    
                </div>
                
                <div id="change-form" className="changeDetailsForm">
                    <h3 className="text-center text-2xl py-3">Change your account details</h3>
                        <form class = "changeDetailsForm" className="w-full max-w-lg mx-auto py-2"  onSubmit={this.handleSubmit}>
                            <div className="flex flex-wrap -mx-3 mb-6">
                                <div className="block uppercase tracking-wide text-gray-700 text-s font-bold mb-2">Change Your E-mail</div>
                                    <div className="w-full px-3">
                                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                            Enter New E-mail
                                        </label>
                                        <input
                                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                            type="email"
                                            placeholder="Change e-mail"
                                            id="newEmail"
                                            onChange={this.handleChange}
                                        />
                                    </div>
                                </div>
                                <div className="flex flex-wrap -mx-3 mb-6">
                                    <div className="w-full px-3">
                                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                            Confirm E-mail
                                        </label>
                                        <input
                                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                            type="email"
                                            placeholder="Confirm change "
                                            id="confirmEmail"
                                            onChange={this.handleChange}
                                        />
                                    </div>
                                </div>
                            <div className="flex flex-wrap -mx-3 mb-6">
                                <div className="block uppercase tracking-wide text-gray-700 text-s font-bold mb-2">Set a New Password</div>
                                <div className="w-full px-3">
                                    <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                        Enter New Password
                                    </label>
                                    <input
                                        className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                        type="password"
                                        placeholder="**********"
                                        id="newPassword"
                                        onChange={this.handleChange}
                                    />
                                </div>
                            </div>
                            <div className="flex flex-wrap -mx-3 mb-6">
                                <div className="w-full px-3">
                                    <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                        Confirm Password
                                    </label>
                                    <input
                                        className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                        type="password"
                                        placeholder="**********"
                                        id="confirmPassword"
                                        onChange={this.handleChange}
                                    />
                                </div>
                            </div>
                            <div>
                                <h1 className="text-center text-lg font-bold">Medical IDs</h1><hr/><br/>
                            </div>
                            <div className="flex flex-wrap -mx-3 mb-6">
                                <div className="w-full px-3">
                                    <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                        AHPRA Number
                                    </label>
                                    <input
                                        className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                        type="text"
                                        placeholder={this.state.ahpra_num}
                                        id="newAhpraNum"
                                        onChange={this.handleChange}
                                    />
                                </div>
                            </div>
                            <div className="flex flex-wrap -mx-3 mb-6">
                                <div className="w-full px-3">
                                    <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                        Provider Number
                                    </label>
                                    <input
                                        className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                        type="text"
                                        placeholder={this.state.provider_num}
                                        id="newProviderNum"
                                        onChange={this.handleChange}
                                    />
                                </div>
                            </div>
                            <div className="flex flex-wrap -mx-3 mb-6">
                                <div className="w-full px-3">
                                    <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                                        Prescriber Number
                                    </label>
                                    <input
                                        className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                                        type="text"
                                        placeholder={this.state.prescriber_num}
                                        id="newPrescriberNum"
                                        onChange={this.handleChange}
                                    />
                                </div>
                            </div>
                            {/* Additional fields can be added for other account details */}
                            <div className="md:flex md:items-center">
                                <div className="buttons">
                                    <button className="button" type="submit">Submit changes</button>
                                    <button className="btn-cancel" type="button" onClick={closeForm}>Close</button>
                                </div>
                            </div>
                            {this.state.message && (
                                <div className="text-red-500 text-s italic" role="alert">
                                    {this.state.message}
                                </div>
                            )}
                        </form>
                        
                </div> 

                <div className="w-full max-w-lg mx-auto py-4">
                <div className="md:flex md:items-center">
                            <div className="buttons">
                           
                        </div>
                    </div>    
                </div>
            </div>
            
        );
    }
}

export default AccountDetails;