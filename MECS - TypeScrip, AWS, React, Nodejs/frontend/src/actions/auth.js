import {
    REGISTER_SUCCESS,
    REGISTER_FAIL,
    LOGIN_SUCCESS,
    LOGIN_FAIL,
    LOGOUT,
    SET_MESSAGE,
    CLEAR_MESSAGE,
} from "./types";

import AuthService from "../services/auth.service";
export const register = (firstname, lastname, email, password, ahpraNum, providerNum, prescriberNum) => (dispatch) => {
return AuthService.register(firstname, lastname, email, password, ahpraNum, providerNum, prescriberNum).then(
    (data) => {
    dispatch({
        type: REGISTER_SUCCESS,
        payload: { user: data },
    });
    dispatch({
        type: CLEAR_MESSAGE,
    });
    return Promise.resolve();
    },
    (error) => {
        var message = "";
        if (error.message === "Could not create account") {
            message = error.message;
        } else if (error.response.data.errors.msg) {
            message = error.response.data.errors.msg
        } else if (error.response.data.errors[0].msg) {
            message = error.response.data.errors[0].msg
        } else {
            console.log(error)
        }
    dispatch({
        type: REGISTER_FAIL,
    });
    dispatch({
        type: SET_MESSAGE,
        payload: message,
    });
    if (message !== "") {
        console.log(message)
        return message;
    }
    return Promise.reject(message);
    }
);
};
export const login = (username, password) => (dispatch) => {
return AuthService.login(username, password).then(
    (data) => {
    dispatch({
        type: LOGIN_SUCCESS,
        payload: { user: data },
    });
    dispatch({
        type: CLEAR_MESSAGE,
    });
    return Promise.resolve();
    },
    (error) => {
        var message = "";
        if (error.message === "Invalid Credentials") {
            message = error.message;
        } else if (error.response.data.errors.msg) {
            message = error.response.data.errors.msg
        } else {
            message = error.response.data.errors[0].msg
        }
    dispatch({
        type: LOGIN_FAIL,
    });
    dispatch({
        type: SET_MESSAGE,
        payload: message,
    });
    if (message !== "") {
        console.log(message)
        return message;
    }
    return Promise.reject(message);
    }
);
};
export const logout = () => (dispatch) => {
    AuthService.logout();
    dispatch({
        type: LOGOUT,
    });
    dispatch({
        type: CLEAR_MESSAGE,
    });
};