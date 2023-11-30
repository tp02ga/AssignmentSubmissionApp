import React from "react";
import { Container } from "react-bootstrap";
import NavBar from "../NavBar";
import assignment from "../Images/coders-campus-assignment-example.png";

import './homepage.css';

const Homepage = () => {
  
  return (
    <div className="main-container">
      <NavBar />
      <div className="wrapper">
        <div className="top-half">
          <Container >
              <h1 className="welcome">Welcome Fellow Coders</h1>
              <h2>
                Coders Campus Assignment Submission Form is a tool to make turning in assignments more convenient. 
                Students can insert Github links of their code for each individual assignment so a reviewer can clone code.
              </h2>
            </Container>
          </div>
          <div className="bottom-half">
            <Container className="bottom-container">
              <h2 className="welcome-text">With these tools students can get personalized video feedback from coding experts. <span className="right-arrow">&#8594;</span> </h2>
              <img src={assignment} alt="assignment-example" className="assignment" />
            </Container>
          </div>
      </div>
    </div>
  );
};

export default Homepage;
