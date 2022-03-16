/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";
import {HomePage} from "./cointainers/HomePage";
import Login from "./components/Login";
import Register from "./components/Register";
//import Home from "./components/Home";

import Profile from "./components/Profile";
import Student from "./components/Student";
import Donor from "./components/Donor";
import Admin from "./components/Admin";

// import AuthVerify from "./common/AuthVerify";
import EventBus from "./common/EventBus";
import {Footer} from "./components/Footer";
import {NavDropdown} from "react-bootstrap";

const App = () => {
  const [showStudent, setStudent] = useState(false);
  const [showDonor, setDonor] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      setStudent(user.type === "student");
      setDonor(user.type === "donor");
      setShowAdminBoard(user.type === "admin");
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    setStudent(false);
    setDonor(false);
    setShowAdminBoard(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/"} className="navbar-brand">
          TechDon
        </Link>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/home"} className="nav-link">
              Home
            </Link>
          </li>

          {showDonor && (
            <li className="nav-item">
              <Link to={"/Donor"} className="nav-link">
                Donor
              </Link>
            </li>
          )}

          {showAdminBoard && (
            <li className="nav-item">
              <Link to={"/admin"} className="nav-link">
                Admin
              </Link>
            </li>
          )}

          {currentUser && (
            <li className="nav-item">
              <Link to={"/user"} className="nav-link">
                User
              </Link>
            </li>
          )}
        </div>

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                {currentUser.username}
              </Link>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={logOut}>
                LogOut
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <NavDropdown title="Dropdown" id="collasible-nav-dropdown">
              <NavDropdown.Item href="/login">Login</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="/register">Register</NavDropdown.Item>
            </NavDropdown>
          </div>
        )}
      </nav>
        <Routes>
          <Route exact path={"/"} element={<HomePage />} />
          <Route exact path={"/home"} element={<HomePage/>} />
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
          <Route exact path="/profile" element={<Profile />} />
          <Route path="/user" element={<Student />} />
          <Route path="/mod" element={<Donor />} />
          <Route path="/admin" element={<Admin />} />
        </Routes>

      <Footer/>



      {/* <AuthVerify logOut={logOut}/> */}
    </div>
  );
};

export default App;
