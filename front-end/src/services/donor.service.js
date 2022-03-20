import axios from "axios";
import AuthService from "./auth.service";

const API_URL = "http://localhost:8080/api/donor/";

const newDevice = (deviceType, deviceName, deviceDescription, deviceLocation) => {
  const authToken = AuthService.getAuthToken();
  const deviceRequest = { authToken, deviceType, deviceName };
  if (deviceDescription) deviceRequest.deviceDescription = deviceDescription;
  if (deviceLocation) deviceRequest.deviceLocation = deviceLocation;

  return axios.post(API_URL + "device/new", null, { params: deviceRequest });
};

const getListedDevices = () => {
  const authToken = AuthService.getAuthToken();
  return axios.get(API_URL + "device/listedDevices", { params: { authToken } });
};

const loadDevice = deviceId => {
  const authToken = AuthService.getAuthToken();
  return axios.get(API_URL + "device/load", {
    params: { authToken, deviceId }
  });
};

const deleteDevice = deviceId => {
  const authToken = AuthService.getAuthToken();
  return axios.delete(API_URL + "device/remove", {
    params: { authToken, deviceId }
  });
};

const updateDevice = (deviceId, deviceType, deviceName, deviceDescription, deviceLocation) => {
  const authToken = AuthService.getAuthToken();
  const deviceRequest = { authToken, deviceId };
  if (deviceType) deviceRequest.deviceType = deviceType;
  if (deviceName) deviceRequest.deviceName = deviceName;
  if (deviceDescription) deviceRequest.deviceDescription = deviceDescription;
  if (deviceLocation) deviceRequest.deviceLocation = deviceLocation;

  return axios.post(API_URL + "device/update", null, { params: deviceRequest });
};

const isDonor = () => {
  return AuthService.getUserType() === "donor";
};

const DonorService = {
  newDevice,
  getListedDevices,
  loadDevice,
  deleteDevice,
  updateDevice,
  isDonor
};

export default DonorService;
