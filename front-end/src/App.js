/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from "react";
import {Routes, Route, Link, Navigate} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";
import {HomePage} from "./cointainers/HomePage";
import Login from "./components/Login";
import Register from "./components/Register";
import Settings from "./components/Settings";
import MultiCheckBox from "./components/MultiCheckBox";

import EventBus from "./common/EventBus";
import {Footer} from "./components/Footer";
import {NavDropdown} from "react-bootstrap";
import UserService from "./services/user.service";
import StudentService from "./services/student.service";

const App = () => {
  const [showStudent, setStudent] = useState(false);
  const [showDonor, setDonor] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);
  const [goToHome, setGoToHome] = useState(false);

  useEffect(() => {
    const user = AuthService.getUserType();

    if (user) {
      setCurrentUser(user);
      setStudent(user === "student");
      setDonor(user === "donor");
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    UserService.logout().then(res => {
      if (res) {
        setCurrentUser(undefined);
        setStudent(false);
        setDonor(false);
        setGoToHome(true);
      }
    });
  };

  return (
    <div>
      {goToHome && <Navigate to='/'/> && setGoToHome(false)}
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/"} className="navbar-brand">
          TechDon
        </Link>
        <div className="navbar-nav mr-auto">
          {showStudent && (
              <li className="nav-item">
                <Link to={"/apply"} className="nav-link">
                  Apply
                </Link>
              </li>
          )}
        </div>

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/settings"} className="nav-link">
                Settings
              </Link>
            </li>
            <li className="nav-item">
              <a className="nav-link" onClick={logOut}>
                Logout
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
          <Route exact path="/settings" element={<Settings />} />
          <Route path="/apply" element={<MultiCheckBox/>} />
        </Routes>
      <Footer/>
    </div>
  );
};

export default App;
