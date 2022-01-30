import { Navigate } from "react-router-dom";
import { useLocalState } from "../util/useLocalStorage";
import { useState, useEffect } from "react";
import ajax from "../Services/fetchService";
import jwt_decode from "jwt-decode";

const PrivateRoute = (props) => {
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [isLoading, setIsLoading] = useState(true);
  const [isValid, setIsValid] = useState(null);
  const { handleJwt, children } = props;

  useEffect(() => {
    handleJwt(jwt);
  }, [jwt]);

  if (jwt) {
    ajax(`/api/auth/validate?token=${jwt}`, "get", jwt).then((isValid) => {
      setIsValid(isValid);
      setIsLoading(false);
    });
  } else {
    return <Navigate to="/login" />;
  }

  return isLoading ? (
    <div>Loading...</div>
  ) : isValid === true ? (
    children
  ) : (
    <Navigate to="/login" />
  );
};

export default PrivateRoute;
