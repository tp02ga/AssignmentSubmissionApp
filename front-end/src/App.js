import { useState } from "react";
import jwt_decode from "jwt-decode";
import { Route, Routes } from "react-router-dom";
import "./App.css";
import AssignmentView from "./AssignmentView";
import Dashboard from "./Dashboard";
import CodeReviewerDashboard from "./CodeReviewerDashboard";
import Homepage from "./Homepage";
import Login from "./Login";
import PrivateRoute from "./PrivateRoute";
import { useLocalState } from "./util/useLocalStorage";
import "bootstrap/dist/css/bootstrap.min.css";
import CodeReviewerAssignmentView from "./CodeReviewAssignmentView";
import { useEffect } from "react/cjs/react.development";

function App() {
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [roles, setRoles] = useState([]);

  useEffect(() => {
    console.log("JWT has changed");
    setRoles(getRolesFromJWT());
  }, [jwt]);

  function getRolesFromJWT() {
    if (jwt) {
      const decodedJwt = jwt_decode(jwt);
      console.log("decoded JWT:", decodedJwt);

      return decodedJwt.authorities;
    }
    return [];
  }
  return (
    <Routes>
      <Route
        path="/dashboard"
        element={
          roles.find((role) => role === "ROLE_CODE_REVIEWER") ? (
            <PrivateRoute handleJwt={setJwt}>
              <CodeReviewerDashboard />
            </PrivateRoute>
          ) : (
            <PrivateRoute handleJwt={setJwt}>
              <Dashboard />
            </PrivateRoute>
          )
        }
      />
      <Route
        path="/assignments/:id"
        element={
          roles.find((role) => role === "ROLE_CODE_REVIEWER") ? (
            <PrivateRoute>
              <CodeReviewerAssignmentView />
            </PrivateRoute>
          ) : (
            <PrivateRoute>
              <AssignmentView />
            </PrivateRoute>
          )
        }
      />
      <Route path="login" element={<Login />} />
      <Route path="/" element={<Homepage />} />
    </Routes>
  );
}

export default App;
