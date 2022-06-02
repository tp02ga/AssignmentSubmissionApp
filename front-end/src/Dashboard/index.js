import React, { useEffect, useState } from "react";
import { Button, Card, Col, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import NavBar from "../NavBar";
import ajax from "../Services/fetchService";
import StatusBadge from "../StatusBadge";
import { useUser } from "../UserProvider";

const Dashboard = () => {
  const navigate = useNavigate();
  const user = useUser();
  const [assignments, setAssignments] = useState(null);

  useEffect(() => {
    console.log("Value of user", user);
    ajax("api/assignments", "GET", user.jwt).then((assignmentsData) => {
      setAssignments(assignmentsData);
    });
    if (!user.jwt) {
      console.warn("No valid jwt found, redirecting to login page");
      navigate("/login");
    }
  }, [user.jwt]);

  function createAssignment() {
    ajax("api/assignments", "POST", user.jwt).then((assignment) => {
      navigate(`/assignments/${assignment.id}`);
      // window.location.href = `/assignments/${assignment.id}`;
    });
  }
  return (
    <>
      <NavBar />
      <div style={{ margin: "2em" }}>
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
              <Card key={assignment.id} style={{ width: "18rem" }}>
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

                  {assignment && assignment.status === "Completed" ? (
                    <>
                      <Button
                        onClick={() => {
                          window.open(assignment.codeReviewVideoUrl);
                        }}
                        className="mb-4"
                      >
                        Watch Review
                      </Button>
                      <Button
                        variant="secondary"
                        onClick={() => {
                          navigate(`/assignments/${assignment.id}`);
                        }}
                      >
                        View
                      </Button>
                    </>
                  ) : (
                    <Button
                      variant="secondary"
                      onClick={() => {
                        navigate(`/assignments/${assignment.id}`);
                      }}
                    >
                      Edit
                    </Button>
                  )}
                </Card.Body>
              </Card>
              // </Col>
            ))}
          </div>
        ) : (
          <></>
        )}
      </div>
    </>
  );
};

export default Dashboard;
