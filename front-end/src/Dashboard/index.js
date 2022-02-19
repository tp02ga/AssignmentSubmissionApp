import React, { useEffect, useState } from "react";
import { Badge, Button, Card, Col, Row } from "react-bootstrap";
import { Link, Navigate, useNavigate } from "react-router-dom";
import ajax from "../Services/fetchService";
import StatusBadge from "../StatusBadge";
import { useUser } from "../UserProvider";
import { useLocalState } from "../util/useLocalStorage";

const Dashboard = () => {
  const navigate = useNavigate();
  const user = useUser();
  const [assignments, setAssignments] = useState(null);

  useEffect(() => {
    ajax("api/assignments", "GET", user.jwt).then((assignmentsData) => {
      setAssignments(assignmentsData);
    });
    if (!user.jwt) navigate("/login");
  }, [user.jwt]);

  function createAssignment() {
    ajax("api/assignments", "POST", user.jwt).then((assignment) => {
      navigate(`/assignments/${assignment.id}`);
      // window.location.href = `/assignments/${assignment.id}`;
    });
  }
  return (
    <div style={{ margin: "2em" }}>
      <Row>
        <Col>
          <div
            className="d-flex justify-content-end"
            style={{ cursor: "pointer" }}
            onClick={() => {
              user.setJwt(null);
            }}
          >
            Logout
          </div>
        </Col>
      </Row>
      <div className="mb-5">
        <Button size="lg" onClick={() => createAssignment()}>
          Submit New Assignment
        </Button>
      </div>
      {assignments ? (
        <div
          className="d-grid gap-5"
          style={{ gridTemplateColumns: "repeat(auto-fit, 18rem)" }}
        >
          {assignments.map((assignment) => (
            // <Col>
            <Card
              key={assignment.id}
              style={{ width: "18rem", height: "18rem" }}
            >
              <Card.Body className="d-flex flex-column justify-content-around">
                <Card.Title>Assignment #{assignment.number}</Card.Title>
                <div className="d-flex align-items-start">
                  <StatusBadge text={assignment.status} />
                </div>

                <Card.Text style={{ marginTop: "1em" }}>
                  <p>
                    <b>GitHub URL</b>: {assignment.githubUrl}
                  </p>
                  <p>
                    <b>Branch</b>: {assignment.branch}
                  </p>
                </Card.Text>

                <Button
                  variant="secondary"
                  onClick={() => {
                    navigate(`/assignments/${assignment.id}`);
                    // window.location.href = `/assignments/${assignment.id}`;
                  }}
                >
                  Edit
                </Button>
              </Card.Body>
            </Card>
            // </Col>
          ))}
        </div>
      ) : (
        <></>
      )}
    </div>
  );
};

export default Dashboard;
