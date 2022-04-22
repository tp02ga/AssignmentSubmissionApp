import React from "react";
import { Container } from "react-bootstrap";
import NavBar from "../NavBar";

const Homepage = () => {
  return (
    <>
      <NavBar />
      <Container className="mt-5">
        <h1>Welcome Fellow Coders</h1>
      </Container>
    </>
  );
};

export default Homepage;
