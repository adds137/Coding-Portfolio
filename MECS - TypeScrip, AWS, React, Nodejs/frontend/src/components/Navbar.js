import React, { useCallback, useState } from "react";
import { NavLink } from "react-router-dom";
import logo from '../images/logo-white.png'
import { useSelector, useDispatch } from "react-redux";


import { clearMessage } from "../actions/message";
import Logout from "./Modal/Logout";

function Navbar() {
  const dispatch = useDispatch();

  const clear = useCallback(() => {
    dispatch(clearMessage());
  }, [dispatch]);

  const { user: currentUser } = useSelector((state) => state.auth);

  const [showLogoutDialog, setShowLogoutDialog] = useState(false);

  const handleLogout = () => {
    setShowLogoutDialog(true);
  }

  return (
    <div>
      {
        showLogoutDialog && 
        <Logout show={showLogoutDialog} setShow={setShowLogoutDialog}/>
      }
      <nav>
        <div className="bg-purple-600 flex justify-between">
          <NavLink to="/" className="text-white text-center font-bold px-4 py-2 m-2">
            <img className="logo" src={logo} style={{ height: '1.6em', position: 'absolute'}} alt="PeterMac App"></img>
          </NavLink>
          <div className="flex justify-end">
            <div className="text-white text-center hover:bg-purple-700 px-4 py-2 m-2 rounded-full">
              <NavLink to="/landing"> Home</NavLink>
            </div>
            {currentUser ? (
              <div className="flex flex-row">
                <div className="text-white text-center hover:bg-purple-700 px-4 py-2 m-2 rounded-full">
                  <NavLink to="/myfiles"> My Files</NavLink>
                </div>
                <div className="text-white text-center hover:bg-purple-700 px-4 py-2 m-2 rounded-full">
                  <NavLink to="/accountDetails"> Account Details</NavLink>
                </div>
                <div className="text-white text-center hover:bg-purple-700 px-4 py-2 m-2 rounded-full">
                  <NavLink to={{}} onClick={handleLogout}> Logout</NavLink>
                </div>
              </div>
            ) : (
              <div className="flex flex-row">
                <div className="text-white text-center hover:bg-purple-700 px-4 py-2 m-2 rounded-full">
                  <NavLink to="/login" onClick={clear}> Login</NavLink>
                </div>
                <div className="text-white text-center hover:bg-purple-700 px-4 py-2 m-2 rounded-full">
                  <NavLink to="/register" onClick={clear}> Register</NavLink>
                </div>
              </div>
            )}
          </div>
        </div>
      </nav>
    </div>
  );
}

export default Navbar;
