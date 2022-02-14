import React from "react";
import logo from "./coders-campus-logo.png"
import {Container, Row, Col} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css'
import NavBar from "../NavBar"


const Homepage = () => {
  return (	
    <div className="nav">
      <Container>
        <Row>
          <Col md> <img src={logo} alt="logo" className="logo"/> </Col> 
          <Col md>
            <NavBar />
          </Col>
        </Row>
      </Container>
    </div>
    
  )

}
export default Homepage;
