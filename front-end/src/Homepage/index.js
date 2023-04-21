import React from "react";
import { Container } from "react-bootstrap";
import NavBar from "../NavBar";

import './homepage.css';

const Homepage = () => {
  
  return (
    <>
      <NavBar />
      <div className="wrapper">
        <Container >
          <div className="top-half">
            <h1 className="welcome">Welcome Fellow Coders</h1>
            <h2 className="welcome-description">
              Coders Campus Assignment Submission Form is a tool to make turning in assignments more convenient. 
              Students can insert Github links of their code for each individual assignment so a reviewer can clone code.
            </h2>
            </div>
          </Container>
          <Container>
            <div className="bottom-half">
              <h2 className="welcome-text">With these tools students can get personalized video feedback from coding experts. </h2>
            </div>
          </Container>
      </div>
    </>
  );
};

export default Homepage;
