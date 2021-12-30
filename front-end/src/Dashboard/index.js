import React from "react";
import { useLocalState } from "../util/useLocalStorage";

const Dashboard = () => {
  const [jwt, setJwt] = useLocalState("", "jwt");

  function createAssignment() {
    fetch("api/assignments", {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwt}`,
      },
      method: "POST",
    })
      .then((response) => {
        if (response.status === 200) return response.json();
      })
      .then((assignment) => {
        window.location.href = `/assignments/${assignment.id}`;
      });
  }
  return (
    <div style={{ margin: "2em" }}>
      <button onClick={() => createAssignment()}>Submit New Assignment</button>
    </div>
  );
};

export default Dashboard;
