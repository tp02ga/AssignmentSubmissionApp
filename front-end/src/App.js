import { useState, useEffect } from "react";
import jwt_decode from "jwt-decode";
import { Route, Routes } from "react-router-dom";
import "./App.css";
import AssignmentView from "./AssignmentView";
import Dashboard from "./Dashboard";
import CodeReviewerDashboard from "./CodeReviewerDashboard";
import Homepage from "./Homepage";
import Login from "./Login";
import PrivateRoute from "./PrivateRoute";
import "./custom.scss";
import CodeReviewerAssignmentView from "./CodeReviewAssignmentView";
import { useUser } from "./UserProvider";

function App() {
  const [roles, setRoles] = useState([]);
  const user = useUser();

  useEffect(() => {
    setRoles(getRolesFromJWT());
  }, [user.jwt]);

  function getRolesFromJWT() {
    if (user.jwt) {
      const decodedJwt = jwt_decode(user.jwt);

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
            <PrivateRoute>
              <CodeReviewerDashboard />
            </PrivateRoute>
          ) : (
            <PrivateRoute>
              <Dashboard />
            </PrivateRoute>
          )
        }
      />
      <Route
        path="/assignments/:assignmentId"
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
