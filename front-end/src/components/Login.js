import React, { useState, useRef } from "react";
import {useNavigate} from "react-router-dom";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import UserService from "../services/user.service";

const required = (value) => {
  if (!value) {
    return (
        <div className="invalid-feedback d-block">
          This field is required!
        </div>
    );
  }
};

const Login = () => {
  const form = useRef();
  const checkBtn = useRef();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const onChangeEmail = (e) => {
    const email = e.target.value;
    setEmail(email);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const handleLogin = (e) => {
    e.preventDefault();

    setMessage("");

    form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0) {
      UserService.login(email, password).then(
          () => {
            navigate("/");
            window.location.reload();
          },
          (error) => {
            const resMessage =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();


            setMessage(resMessage);
          }
      );
    } else {

    }
  };

  return (
      <div className="col-md-12">
        <div className="card card-container">
          <img
              src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
              alt="profile-img"
              className="profile-img-card"
          />

          <Form onSubmit={handleLogin} ref={form}>
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <Input
                  type="text"
                  className="form-control"
                  name="email"
                  placeholder="Email"
                  value={email}
                  onChange={onChangeEmail}
                  validations={[required]}
              />
            </div>

            <div className="form-group">
              <label htmlFor="password">Password</label>
              <Input
                  type="password"
                  className="form-control"
                  name="password"
                  placeholder="password"
                  value={password}
                  onChange={onChangePassword}
                  validations={[required]}
              />
            </div>

            <div className="form-group">
              <button className="btn btn-primary btn-block">

                <span>Login</span>
              </button>
            </div>

            {message && (
                <div className="form-group">
                  <div className="alert alert-danger" role="alert">
                    {message}
                  </div>
                </div>
            )}
            <CheckButton style={{ display: "none" }} ref={checkBtn} />
          </Form>
        </div>
      </div>
  );
};

export default Login;