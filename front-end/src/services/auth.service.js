
const setLogin = (userType, authToken) => {
  sessionStorage.setItem("authToken", authToken);
  sessionStorage.setItem("userType", userType);
};

const clearLogin = () => {
  sessionStorage.removeItem("authToken");
  sessionStorage.removeItem("userType");
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

const AuthService = {
  setLogin,
  clearLogin,
  getUser,
  getUserType,
  getAuthToken,
};

export default AuthService;