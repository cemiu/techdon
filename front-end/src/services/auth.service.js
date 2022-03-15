import axios from "axios";

const API_URL = "http://localhost:8080/";

const register = (userType, firstName, lastName, email, password, phone , address, university) => {
  return axios.post(API_URL + "api/account/register", {
      userType,
      firstName,
      lastName,
      email,
      password,
      address,
      university
  })
      .then((response) => {
          console.log(response)});
};

const login = (email, password) => {
  return axios
      .post(API_URL + "api/account/login", {
        email,
        password,
      })
    .then((response) => {
        console.log(response);
      if (response.status === 200) {
          const authToken = response.data.authToken;
          const userType = response.data.userType;
          sessionStorage.setItem("authToken", authToken);
          sessionStorage.setItem("userType", userType);
          console.log(authToken);
          console.log(userType);
      }

      return response.data;
    });
};

const logout = () => {
  sessionStorage.removeItem("authToken");
    sessionStorage.removeItem("userType");
  return axios.post(API_URL + "signout").then((response) => {
    return response.data;
  });
};

const getCurrentUser = () => {
  return JSON.parse(sessionStorage.getItem("userType"));
};

const AuthService = {
  register,
  login,
  logout,
  getCurrentUser,
}

export default AuthService;
