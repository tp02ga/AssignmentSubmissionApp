import React from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import { Button, Image } from "react-bootstrap";
import logo from "../Images/coders-campus-logo.png";
import { useUser } from "../UserProvider";

function NavBar() {
  const navigate = useNavigate();
  const { pathname } = useLocation();
  const user = useUser();

  return (
    <div className="NavBar nav d-flex justify-content-end">
      <div style={{ position: "fixed", left: "2em" }}>
        <Link to="/">
          <Image src={logo} alt="logo" className="logo" />
        </Link>
      </div>
      <div className="" style={{ marginRight: "3em" }}>
        {user && user.jwt ? (
          <span
            className="link"
            style={{ marginRight: "3em" }}
            onClick={() => {
              user.setJwt(null);
              navigate("/");
            }}
          >
            Logout
          </span>
        ) : pathname !== "/login" ? (
          <Button
            variant="primary"
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
