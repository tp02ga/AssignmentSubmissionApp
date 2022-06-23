import React from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import { Button, Image } from "react-bootstrap";
import logo from "../Images/coders-campus-logo.png";
import { useUser } from "../UserProvider";
import ajax from "../Services/fetchService";

function NavBar() {
  const navigate = useNavigate();
  const { pathname } = useLocation();
  const user = useUser();

  return (
    <div className="NavBar nav d-flex justify-content-around justify-content-lg-between">
      <div className="ms-md-5">
        <Link to="/">
          <Image src={logo} alt="logo" className="logo" />
        </Link>
      </div>
      <div>
        {user && user.jwt ? (
          <span
            className="link"
            onClick={() => {
              // TODO: have this delete cookie on server side
              fetch("/api/auth/logout").then((response) => {
                if (response.status === 200) {
                  user.setJwt(null);
                  navigate("/");
                }
              });
            }}
          >
            Logout
          </span>
        ) : pathname !== "/login" ? (
          <Button
            variant="primary"
            className="me-5"
            onClick={() => {
              navigate("/login");
            }}
          >
            Login
          </Button>
        ) : (
          <></>
        )}

        {user && user.jwt ? (
          <Button
            className="ms-5 ms-md-5 me-md-5"
            onClick={() => {
              navigate("/dashboard");
            }}
          >
            Dashboard
          </Button>
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}

export default NavBar;
