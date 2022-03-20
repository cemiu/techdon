
const userListeners = {};

const setLogin = (userType, authToken) => {
  sessionStorage.setItem("authToken", authToken);
  sessionStorage.setItem("userType", userType);
  Object.values(userListeners).forEach(listener => listener(userType));
};

const clearLogin = () => {
  sessionStorage.removeItem("authToken");
  sessionStorage.removeItem("userType");
  Object.values(userListeners).forEach(listener => listener(undefined));
};

const getUser = () => {
  const userType = sessionStorage.getItem("userType");
  const authToken = sessionStorage.getItem("authToken");

  if (!userType || !authToken) return undefined;

  return { userType, authToken };
};

const getUserType = () => {
  return sessionStorage.getItem("userType");
};

const getAuthToken = () => {
  return sessionStorage.getItem("authToken");
};

const addUserListener = (id, callback) => {
  userListeners.id = callback;
};

const AuthService = {
  setLogin,
  clearLogin,
  getUser,
  getUserType,
  getAuthToken,
  addUserListener
};

export default AuthService;