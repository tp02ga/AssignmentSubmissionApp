import React from "react";
import logo from "./coders-campus-logo.png"
import {Button} from "react-bootstrap";

const Homepage = () => {
	function goToRegister(){
		window.location="/register";
	}
	function goToLogin(){
		window.location="/login";
	}
  return (	
    <div className="nav">
      <img src={logo} alt="logo" className="logo"/>
      <Button  onClick={goToLogin}/>
      <Button type="button" onClick={goToRegister}/>
    </div>
  )

}
export default Homepage;
