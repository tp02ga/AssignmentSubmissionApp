import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button, Image } from "react-bootstrap";
import logo from "../Images/coders-campus-logo.png";

function NavBar() {
  const navigate = useNavigate();
  function goToRegister() {
    navigate("/register");
  }
  return (
    <div className="NavBar nav d-flex justify-content-end">
      <div style={{ position: "fixed", left: "2em" }}>
        <Link to="/">
          <Image src={logo} alt="logo" className="logo" />
        </Link>
      </div>
      <div className="" style={{ marginRight: "3em" }}>
        <Link to="/login" style={{ marginRight: "3em" }}>
          Login
        </Link>
        <Button onClick={() =>{navigate("/register") }}>Register</Button>
      </div>
    </div>
  );
}

export default NavBar;
