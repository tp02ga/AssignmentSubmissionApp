import React from "react";
import logo from "./coders-campus-logo.png"
import Button from "../Components/Button.js";

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
      <Button text="log-in" onClick={goToLogin }/>
      <Button text="register" onClick={goToRegister} className="btn"/>
    </div>
  )

}
export default Homepage;
