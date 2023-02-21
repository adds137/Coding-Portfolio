import axios from "axios";
import authHeader from "../services/auth-header"

const API_URL = "https://9xgzyeg1c1.execute-api.ap-southeast-2.amazonaws.com/staging/User";

const register = (firstname, lastname, email, password, ahpraNum, providerNum, prescriberNum) => {
  var params = {firstname, lastname, email, password}
  if (ahpraNum !== "") {
    params.ahpra_num = ahpraNum;
  }
  if (providerNum !== "") {
    params.provider_num = providerNum;
  }
  if (prescriberNum !== "") {
    params.prescriber_num = prescriberNum;
  }
  return axios.post(API_URL, params)
  .then((response) => {
    if (response.data.accessToken) {
      localStorage.setItem("user", JSON.stringify(response.data.accessToken));
      return response.data;
    }
    throw new Error("Could not create account")
  });
};

const login = (email, password) => {
  return axios.post(API_URL + "/login", {
      email,
      password,
    })
    .then((response) => {
      if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data.accessToken));
        return response.data;
      }
      throw new Error("Invalid Credentials");
    });
};

const patchUser = (userId, firstname, lastname, email, password, ahpra_num, provider_num, prescriber_num) => {
  var params = {firstname,lastname};
  if (email !== "") {
    params.email = email;
  }
  if (password !== "") {
    params.password = password;
  }
  if (ahpra_num !== "") {
    params.ahpra_num = ahpra_num;
  }
  if (provider_num !== "") {
    params.provider_num = provider_num;
  }
  if (prescriber_num !== "") {
    params.prescriber_num = prescriber_num;
  }
  console.log(params)
  return axios.patch(API_URL + "/" + userId, 
    params,
    authHeader()
  )
  .then((response) => {
    console.log(response.data)
    if (response.data.newAccessToken) {
      localStorage.setItem("user", JSON.stringify(response.data.newAccessToken));
      return response.data;
    }
    throw new Error("Could not change details")
  },
  (error) => {
      var message = "";
      if (error.message === "Error Updating Details") {
          message = error.message
      } else if (error.response.data.errors.msg) {
          message = error.response.data.errors.msg
      } else {
          message = error.response.data.errors[0].msg
      }
      if (message !== "") {
        console.log(message)
        return message;
      }
      return error;
  });
};

const deleteUser = (userId) => {
  return axios.delete(API_URL + "/" + userId,
    authHeader()
  )
  .then((response) => {
    console.log(response.data)
    if (response.data === "") {
      localStorage.removeItem("user");
      return true;
    } else {
      return false;
    }
  }, (error) => {
    console.log(error)
    return false;
  });
};

const logout = () => {
  localStorage.removeItem("user");
};

const AuthService = {
  register,
  login,
  patchUser,
  deleteUser,
  logout,
}

export default AuthService;
