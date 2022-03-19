import axios from "axios";

const API_URL = "http://localhost:8080/";

const register = (userType, firstName, lastName, email, password, phone , address, university) => {
    const registerRequest = {
        userType,
        firstName,
        lastName,
        email,
        phone,
        password,
        address
    }

    if (userType === "student")
        registerRequest.university = university;

    console.log(registerRequest);

    return axios.get(API_URL + "api/account/register", { params: registerRequest})
        .then((response) => {
            console.log(response)});
};

const login = (email, password) => {
    console.log(email);
    console.log(password);
    return axios
        .get(API_URL + "/api/user/login", { params: {
                email,
                password,
            }})
       .then((response) => {
            console.log(response);
            if (response.status === 200) {
                const authToken = response.data.authToken;
                const userType = response.data.userType;
                sessionStorage.setItem("authToken", authToken);
                sessionStorage.setItem("userType", userType);
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
    return sessionStorage.getItem("userType");
};

const AuthService = {
    register,
    login,
    logout,
    getCurrentUser,
}

export default AuthService;