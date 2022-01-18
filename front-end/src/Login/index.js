import React, { useState } from "react";
import { Button, Col, Container, Row, Form } from "react-bootstrap";
import { useLocalState } from "../util/useLocalStorage";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [jwt, setJwt] = useLocalState("", "jwt");

  function sendLoginRequest() {
    const reqBody = {
      username: username,
      password: password,
    };

    fetch("api/auth/login", {
      headers: {
        "Content-Type": "application/json",
      },
      method: "post",
      body: JSON.stringify(reqBody),
    })
      .then((response) => {
        if (response.status === 200)
          return Promise.all([response.json(), response.headers]);
        else return Promise.reject("Invalid login attempt");
      })
      .then(([body, headers]) => {
        setJwt(headers.get("authorization"));
        window.location.href = "dashboard";
      })
      .catch((message) => {
        alert(message);
      });
  }
  return (
    <>
      <Container className="mt-5">
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label htmlFor="username" className="fs-4">
            Username
          </Form.Label>
          <Form.Control
            type="email"
            id="username"
            size="lg"
            placeholder="joe@gmail.com"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label htmlFor="password" className="fs-4">
            Password
          </Form.Label>
          <Form.Control
            type="password"
            id="password"
            size="lg"
            placeholder="Type in your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </Form.Group>
        <Row>
          <Col className="mt-2">
            <Button
              id="submit"
              type="button"
              size="lg"
              onClick={() => sendLoginRequest()}
            >
              Login
            </Button>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default Login;
