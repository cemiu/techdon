/* eslint-disable no-unused-vars */
/* eslint-disable jsx-a11y/anchor-is-valid */
import React, {useEffect, useState} from "react";
import {Link, Navigate, Route, Routes, useLocation} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import FAQApp from "./components/FAQ/ACCapp";
import AuthService from "./services/auth.service";
import {HomePage} from "./cointainers/HomePage";
import Login from "./components/Login";
import Register from "./components/Register";
import Settings from "./components/Settings";
import DeviceNew from "./components/UploadDevice";
import MultiCheckBox from "./components/MultiCheckBox";
import StudentDevices from "./components/StudentDevices";

import DonorDevices from "./components/DonorDevices";

import {Footer} from "./components/Footer";
import {NavDropdown} from "react-bootstrap";
import UserService from "./services/user.service";
import StudentDevicesAccept from "./components/StudentDevicesAccept";

const App = () => {
  const [showStudent, setStudent] = useState(false);
  const [showDonor, setDonor] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);
  const [goHome, setGoHome] = useState(false);

  const location = useLocation();

  useEffect(() => {
    const userRefresh = (user) => {
      if (user) {
        setCurrentUser(user);
        setStudent(user === "student");
        setDonor(user === "donor");
      } else {
        setCurrentUser(undefined);
        setStudent(false);
        setDonor(false);
      }
    };

    userRefresh(AuthService.getUserType());
    AuthService.addUserListener("app", userRefresh);
  }, []);

  return (
    <div>
      {goHome && location.pathname === "/" && setGoHome(false)}
      {goHome && <Navigate to="/"/>}
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/"} className="navbar-brand">
          TechDon
        </Link>
        {showStudent && (
          <div className="navbar-nav mr-auto">
            <Link to={"/apply"} className="nav-link">
              Apply
            </Link>
            <Link to={"/offered"} className="nav-link">
              Offered Devices
            </Link>
          </div>
        )}

        {showDonor && (
          <div className="navbar-nav mr-auto">
            <Link to={"/devices/add"} className="nav-link">
              Donate
            </Link>
            <Link to={"/devices"} className="nav-link">
              Donated Devices
            </Link>
          </div>
        )}

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/settings"} className="nav-link">
                Settings
              </Link>
            </li>
            <li className="nav-item">
              <a role="button" name="logout" className="nav-link" onClick={() => {
                UserService.logout().then(() => {
                  setGoHome(true);
                });
              }}>
                Logout
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <NavDropdown title="Dropdown" id="collasible-nav-dropdown">
              <NavDropdown.Item href="/login">Login</NavDropdown.Item>
              <NavDropdown.Divider/>
              <NavDropdown.Item href="/register">Register</NavDropdown.Item>
            </NavDropdown>
          </div>
        )}
      </nav>
      <Routes>
        <Route exact path={"/"} element={<HomePage/>}/>
        <Route exact path={"/home"} element={<HomePage/>}/>

        <Route exact path="/login" element={<Login/>}/>
        <Route exact path="/register" element={<Register/>}/>
        <Route exact path="/settings" element={<Settings/>}/>

        <Route exact path="/apply" element={<MultiCheckBox/>}/>
        <Route exact path="/offered" element={<StudentDevices/>}/>
        <Route exact path="/offered/accept" element={<StudentDevicesAccept/>}/>

        <Route exact path="/devices/add" element={<DeviceNew/>}/>
        <Route exact path="/devices" element={<DonorDevices/>}/>
        <Route exact path="/FAQ" element={<FAQApp/>}/>
      </Routes>
      <Footer/>
    </div>
  );
};

export default App;
