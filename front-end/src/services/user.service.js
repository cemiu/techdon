import axios from "axios";
import AuthService from "./auth.service";

const API_URL = "http://localhost:8080/api/";

const getPreferences = () => {
  const authToken = AuthService.getAuthToken();
  return axios.get(API_URL + "user/account/settings/get", { params: { authToken } });
};

const isStudent = () => {
  const user = AuthService.getCurrentUser();
  if (!user)
    return undefined;
  return user === 'student';
};

const getUserType = () => {
  switch (AuthService.getCurrentUser()) {
    case 'student':
      return 'Student';
    case 'donor':
      return 'Donor';
    default:
      return undefined;
  }
};

const deleteUser = () => {
  const authToken = AuthService.getAuthToken();
  return axios.delete(API_URL + "user/delete", { params: { authToken } })
    .then(response => {
      console.log("abc");
      if (response.status === 200) {
        sessionStorage.removeItem("authToken");
        sessionStorage.removeItem("userType");
        return true;
      }
      return false;
    });
};

const UserService = {
  getPreferences,
  isStudent,
  getUserType,
  deleteUser,
}

export default UserService;
