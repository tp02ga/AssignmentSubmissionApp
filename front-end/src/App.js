import { useState, useEffect } from "react";
import jwt_decode from "jwt-decode";
import { Route, Routes } from "react-router-dom";
import "./App.css";
import AssignmentView from "./Components/AssignmentView";
import Dashboard from "./Components/Dashboard";
import CodeReviewerDashboard from "./Components/CodeReviewerDashboard";
import Homepage from "./Components/Homepage";
import Login from "./Components/Login";
import Register from "./Components/Register"
import PrivateRoute from "./Components/PrivateRoute";
import "bootstrap/dist/css/bootstrap.min.css";
import CodeReviewerAssignmentView from "./Components/CodeReviewAssignmentView";
import { useUser } from "./Components/UserProvider";
import DarkMode from "./Components/DarkMode";
/**
 *
 * /AssignmentSubmissionApp/
 *   - back-end/
 *     - src/main/java/
 *     - Dockerfile
 *   - front-end/
 *     - src/Services/
 *     - Dockerfile
 *   - docker-compose.yml
 */

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
      <Route path="/" element={ <><DarkMode/> <Homepage /></>} />
      <Route path="/register" element={<Register/>} />
    </Routes>
  );
}

export default App;
