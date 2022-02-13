import React from "react";
import Login from "../Login"
import logo from "./coders-campus-logo.png"
import {Container, Row, Col} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css'
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import styled from "styled-components";

const Button = styled.button`
  background-color: #243f93;
  color: white;
  font-size: 20px;
  padding: 10px 60px;
  border-radius: 5px;
  margin: 10px 0px;
  cursor: pointer;
`;

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
          <Col md>
            <Router>
              <nav> 
                <Link to="/login">Login</Link> 
              </nav>
              <Routes>
                <Route path="/login" element={<Login />}/> 
              </Routes>
            </Router>
          </Col>
          <Col md><Button variant="success" onClick={goToRegister}> Register </Button></Col>
        </Row>
      </Container>
    </div>
    
  )

}
export default Homepage;
