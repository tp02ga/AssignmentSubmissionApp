import React from "react";
import { useLocalState } from "../util/useLocalStorage";

const Dashboard = () => {
  const [jwt, setJwt] = useLocalState("", "jwt");

  return (
    <div>
      <h1>Hello world</h1>
      <div>JWT Value is {jwt}</div>
    </div>
  );
};

export default Dashboard;
