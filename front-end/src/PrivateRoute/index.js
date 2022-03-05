import { Navigate } from "react-router-dom";
import { useUser } from "../UserProvider";
import { useState, useEffect } from "react";
import ajax from "../Services/fetchService";

const PrivateRoute = (props) => {
  const user = useUser();
  const [isLoading, setIsLoading] = useState(true);
  const [isValid, setIsValid] = useState(null);
  const { children } = props;

  if (user) {
    ajax(`/api/auth/validate`, "get", user.jwt).then((isValid) => {
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
