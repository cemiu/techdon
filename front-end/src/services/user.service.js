import axios from "axios";

const API_URL = "http://localhost:3000/api/";

const getPublicContent = () => {
  return axios.get(API_URL + "all");
};

const getStudent = () => {
  return axios.get(API_URL + "Student");
};

const getDonat = () => {
  return axios.get(API_URL + "Donator");
};

const getAdmin = () => {
  return axios.get(API_URL + "Admin");
};

const UserService = {
  getPublicContent,
  getStudent,
  getDonat,
  getAdmin,
}

export default UserService;
