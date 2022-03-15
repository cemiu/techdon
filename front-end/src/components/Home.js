import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";
import HomePage from "./HomePage";
//import HomePage from "./HomePage";


const Home = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    UserService.getPublicContent().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        setContent(_content);
      }
    );
  }, []);

  return (
    <div className="container">
      <header className="jumbotron">
          <HomePage/>
      </header>

    </div>

  );
};

export default Home;
