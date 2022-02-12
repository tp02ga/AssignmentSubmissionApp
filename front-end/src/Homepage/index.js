import React from "react";
import logo from "./coders-campus-logo.png"
import {Button, Container, Row, Col} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css'
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

const Homepage = () => {
	function goToRegister(){
	  window.location="/register";
	 }
	function goToLogin(){
	 	window.location="/login";
	}
  return (	
    <div className="nav">
      <Container>
        <Row>
          <Col md> <img src={logo} alt="logo" className="logo"/> </Col> 
          <Col md><Button variant="primary" onClick={goToRegister}> Register </Button></Col>
          <Col md><Button variant="primary" onClick={goToLogin}> Login </Button></Col>
        </Row>
      </Container>
    </div>
    
  )

}
export default Homepage;
