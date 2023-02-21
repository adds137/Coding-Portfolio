import React, { useState, useCallback} from "react";
import { useDispatch, useSelector } from "react-redux";
import { register } from "../actions/auth";
import { setMessage } from "../actions/message";
import { Navigate } from 'react-router-dom';
// import bcrypt from 'bcryptjs'

// const salt = bcrypt.genSaltSync(10);

const Register = () => {
  const [firstname, setFirstname] = useState("");
  const [lastname, setLastname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [ahpraNum, setAhpraNum] = useState("");
  const [providerNum, setProviderNum] = useState("");
  const [prescriberNum, setPrescriberNum] = useState("");
  

  const { isLoggedIn } = useSelector(state => state.auth);
  const { message } = useSelector(state => state.message);
  
  const dispatch = useDispatch();

  const onChangeFirstname = (e) => {
    const firstname = e.target.value;
    setFirstname(firstname);
  };

  const onChangeLastname = (e) => {
    const lastname = e.target.value;
    setLastname(lastname);
  };

  const onChangeEmail = (e) => {
    const email = e.target.value;
    setEmail(email);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const onChangeConfirmPassword = (e) => {
    const confirmPassword = e.target.value;
    setConfirmPassword(confirmPassword);
  };
  
  const onChangeAhpraNum = (e) => {
    const ahpraNum = e.target.value;
    setAhpraNum(ahpraNum);
  };

  const onChangeProviderNum = (e) => {
    const providerNum = e.target.value;
    setProviderNum(providerNum);
  };

  const onChangePrescriberNum = (e) => {
    const prescriberNum = e.target.value;
    setPrescriberNum(prescriberNum);
  };

  const handleSubmit = (e) => {
    e.preventDefault()
    
    if (checkPasswordMatch()) {
      // const hashedPassword = bcrypt.hashSync(password, salt)
      dispatch(register(firstname,lastname,email,password,ahpraNum,providerNum,prescriberNum))
    }
  };

  const setMsg = useCallback((message) => {
    dispatch(setMessage(message));
  }, [dispatch]);

  const checkPasswordMatch = () => {
    if (password !== confirmPassword) {
      setMsg("Passwords do not match")
      return false;
    }
    return true;
  };

  if (isLoggedIn) {
    return <Navigate to="/landing" />;
  }

  return(
    <div className="pt-10">
      <h1 className="text-center text-4xl pb-4">Register</h1>
      <form
        onSubmit={handleSubmit}
        className="w-full max-w-lg mx-auto py-2"
      >
        <div className="flex flex-wrap -mx-3 mb-6">
          <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
            <label
              className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
            >
              First Name <b className="text-red-500">*</b>
            </label>
            <input
              className="appearance-none block w-full bg-gray-200 text-gray-700  rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
              type="text"
              placeholder="e.g. John"
              id="firstname"
              onChange={onChangeFirstname}
              required
            />
          </div>
          <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
            <label
              className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
            >
              Last Name <b className="text-red-500">*</b>
            </label>
            <input
              className="appearance-none block w-full bg-gray-200 text-gray-700  rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
              type="text"
              placeholder="e.g. Smith"
              id="lastname"
              onChange={onChangeLastname}
              required
            />
          </div>
        </div>
        <div className="flex flex-wrap -mx-3 mb-6">
          <div className="w-full px-3">
            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              E-mail <b className="text-red-500">*</b>
            </label>
            <input
              className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
              type="email"
              placeholder="e.g. johnsmith@hotmail.com"
              id="email"
              onChange={onChangeEmail}
              required
            />
          </div>
        </div>
        <div className="flex flex-wrap -mx-3 mb-6">
          <div className="w-full px-3">
            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Password <b className="text-red-500">*</b>
            </label>
            <input
              className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
              type="password"
              placeholder="**********"
              id="password"
              onChange={onChangePassword}
              required
            />
          </div>
        </div>
        <div className="flex flex-wrap -mx-3 mb-6">
          <div className="w-full px-3">
            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Confirm Password <b className="text-red-500">*</b>
            </label>
            <input
              className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
              type="password"
              placeholder="**********"
              id="confirmPassword"
              onChange={onChangeConfirmPassword}
              required
            />
          </div>
        </div>
        <div className="flex flex-wrap -mx-3 mb-6">
          <div className="w-full px-3">
              <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                AHPRA Number <em className="capitalize text-gray-500">- Optional</em>
              </label>
              <input
                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                type="text"
                id="ahpraNum"
                onChange={onChangeAhpraNum}
              />
          </div>
        </div>
        <div className="flex flex-wrap -mx-3 mb-6">
          <div className="w-full px-3">
              <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                Provider Number <em className="capitalize text-gray-500">- Optional</em>
              </label>
              <input
                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                type="text"
                id="providerNum"
                onChange={onChangeProviderNum}
              />
          </div>
        </div>
        <div className="flex flex-wrap -mx-3 mb-6">
          <div className="w-full px-3">
              <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                Prescriber Number <em className="capitalize text-gray-500">- Optional</em>
              </label>
              <input
                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                type="text"
                id="presriberNum"
                onChange={onChangePrescriberNum}
              />
          </div>
        </div>
        {message && (
            <div className="text-red-500 text-s italic" role="alert">
            {message}
            </div>
          )}
        <div className="md:flex md:items-center pb-5">
          <div className="md:w-1/2"></div>
          <div className="md:w-2/3">
          <button className="button" type="submit">Register</button>
          </div>
        </div>
      </form>
    </div>
  );
}

export default Register;

