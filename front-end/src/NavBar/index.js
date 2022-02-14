import React from 'react';
import {NavLink} from 'react-router-dom';
import styled from "styled-components";
import {Button} from "react-bootstrap";

function goToRegister(){
    window.location = "/register";
}
function NavBar(){
    return(
        <div className="NavBar"> 
            <NavLink className="navLink" to="/login" >Login</NavLink>
            <Button variant="outline-primary" onClick={goToRegister}>Register</Button>
        </div>
    )
}

export default NavBar