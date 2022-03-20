import axios from "axios";
import AuthService from "./auth.service";

const API_URL = "http://localhost:8080/api/student/devices/";

const getDonationPreferences = () => {
  const authToken = AuthService.getAuthToken();
  return axios.get(API_URL + "loadPreferences", { params: { authToken }});
};

const setDonationPreferences = (deviceList) => {
  const authToken = AuthService.getAuthToken();
  const deviceTypes = deviceList.join();
  return axios.post(API_URL + "setPreferences", null, { params: { authToken, deviceTypes }});
};

const getOfferedDevices = () => {
  const authToken = AuthService.getAuthToken();
  return axios.get(API_URL + "offeredDevices", { params: { authToken }});
};

const loadDevice = (deviceId) => {
  const authToken = AuthService.getAuthToken();
  return axios.get(API_URL + "load", { params: { authToken, deviceId }});
};

const claimDevice = (deviceId) => {
  const authToken = AuthService.getAuthToken();
  return axios.get(API_URL + "claim", { params: { authToken, deviceId }});
};

const declineDevice = (deviceId) => {
  const authToken = AuthService.getAuthToken();
  return axios.post(API_URL + "decline", null, { params: { authToken, deviceId }});
};

const isStudent = () => {
  return AuthService.getUserType() === "student";
};

const StudentService = {
  getDonationPreferences,
  setDonationPreferences,
  getOfferedDevices,
  loadDevice,
  claimDevice,
  declineDevice,
  isStudent
};

export default StudentService;
