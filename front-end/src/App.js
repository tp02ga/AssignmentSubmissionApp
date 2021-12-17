import { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [jwt, setJwt] = useState("");

  useEffect(() => {
    const reqBody = {
      username: "trevor",
      password: "asdfasdf",
    };

    fetch("api/auth/login", {
      headers: {
        "Content-Type": "application/json",
      },
      method: "post",
      body: JSON.stringify(reqBody),
    })
      .then((response) => Promise.all([response.json(), response.headers]))
      .then(([body, headers]) => {
        setJwt(headers.get("authorization"));
      });
  }, []);

  useEffect(() => {
    console.log(`JWT is: ${jwt}`);
  }, [jwt]);

  return (
    <div className="App">
      <h1>Hello world</h1>
      <div>JWT Value is {jwt}</div>
    </div>
  );
}

export default App;
