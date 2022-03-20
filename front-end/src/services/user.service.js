import axios from "axios";
import AuthService from "./auth.service";

const API_URL = "http://localhost:8080/api/user/";

const register = (userType, firstName, lastName, email, password, phone, address, university) => {
  const registerRequest = {
    userType, firstName, lastName, email, phone, password, address
  }

  if (userType === "student") registerRequest.university = university;

  return axios.get(API_URL + "register", {params: registerRequest})
    .then((response) => {
      if (response.status === 200) {
        const data = response.data;
        AuthService.setLogin(data.userType, data.authToken);
      }
    });
};

const login = (email, password) => {
  return axios
    .get(API_URL + "login", {params: {email, password}})
    .then((response) => {
      if (response.status === 200) {
        const data = response.data;
        AuthService.setLogin(data.userType, data.authToken);
      }
    });
};

const logout = () => {
  const authToken = AuthService.getAuthToken();
  return axios.post(API_URL + "logout", null, {params: {authToken}})
    .then(() => {
      return true;
    }).catch((err) => {
      if (err.response.status === 401)
        return true;
      return false;
    });
    // .then((response) => {
    //   console.log(response);
    //   // if (response.status === 200) AuthService.clearLogin();
    //   // return response;
    // });
};

const deleteUser = () => {
  const authToken = AuthService.getAuthToken();
  return axios.delete(API_URL + "delete", {params: {authToken}})
    .then(response => {
      if (response.status === 200) {
        sessionStorage.removeItem("authToken");
        sessionStorage.removeItem("userType");
        return true;
      }
      return false;
    });
};

const getPreferences = () => {
  const authToken = AuthService.getAuthToken();
  return axios.get(API_URL + "account/settings/get", {params: {authToken}});
};

const isStudent = () => {
  const user = AuthService.getUserType();
  if (!user) return undefined;
  return user === 'student';
};

const getUserType = () => {
  switch (AuthService.getUserType()) {
    case 'student':
      return 'Student';
    case 'donor':
      return 'Donor';
    default:
      return undefined;
  }
};

const UserService = {
  register, login, logout, deleteUser, getPreferences, isStudent, getUserType,
};

export default UserService;
