import React from 'react';
import {NavLink} from 'react-router-dom'
import styled from "styled-components";

const Button = styled.button`
  background-color: #243f93;
  color: white;
  font-size: 15px;
  padding: 10px 30px;
  border-radius: 5px;
 
 
`;
function goToRegister(){
    window.location = "/register";
}
function NavBar(){
    return(
        <div className="NavBar"> 
            <NavLink className="navLink" to="/login" >Login</NavLink>
            <Button onClick={goToRegister}>Register</Button>
        </div>
    )
}

export default NavBar