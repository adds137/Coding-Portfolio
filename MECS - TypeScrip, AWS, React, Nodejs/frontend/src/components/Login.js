import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navigate } from 'react-router-dom';

import { login } from "../actions/auth";
import { logout } from "../actions/auth";

const Login = () => {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const { isLoggedIn } = useSelector(state => state.auth);
  const { message } = useSelector(state => state.message);

  const dispatch = useDispatch();
  
  const onChangeEmail = (e) => {
    const email = e.target.value;
    setEmail(email);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    dispatch(login(email, password))
  };

  if (isLoggedIn && (localStorage.getItem("user") === null)) {
    dispatch(logout())
  }

  if (isLoggedIn) {
    return <Navigate to="/landing" />;
  }

  return(
    <div className="pt-20">
      <h1 className="text-center text-4xl">Login</h1>
      <form
        onSubmit={handleSubmit}
        className="w-full max-w-lg mx-auto py-2"
      >
        <div className="flex flex-wrap -mx-3 mb-6">
          <div className="w-full px-3">
            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              E-mail
            </label>
            <input
              className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
              type="text"
              placeholder="Enter your e-mail"
              id="email"
              value={email}
              onChange={onChangeEmail}
              required
            />
          </div>
        </div>
        
        <div className="flex flex-wrap -mx-3 mb-6">
          <div className="w-full px-3">
          <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
            Password
          </label>
          <input
            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
            type="password"
            placeholder="Password"
            id="password"
            value={password}
            onChange={onChangePassword}
            required
          />
          {message && (
            <div className="text-red-500 text-s italic" role="alert">
            {message}
            </div>
          )}
          </div>
        </div>
        
        <div className="md:flex md:items-center">
          <div className="md:w-1/2"></div>
            <div className="md:w-2/3">
              <div className="buttons">
              <button className="button" type="submit">Login</button>
            </div>	
          </div>
        </div>
      </form>
    </div>
  );
}

export default Login;

