import React, { useEffect, useState } from "react";
import { Button, Card, Col, Container, Row } from "react-bootstrap";
import jwt_decode from "jwt-decode";
import ajax from "../Services/fetchService";
import StatusBadge from "../StatusBadge";
import { useNavigate } from "react-router-dom";
import { useUser } from "../UserProvider";
import NavBar from "../NavBar";
import moment from "moment";

const CodeReviewerDashboard = () => {
  const navigate = useNavigate();
  const user = useUser();
  const [assignments, setAssignments] = useState(null);

  useEffect(() => {
    if (!user.jwt) navigate("/login");
  });
  function editReview(assignment) {
    navigate(`/assignments/${assignment.id}`);
    // window.location.href = `/assignments/${assignment.id}`;
  }

  function claimAssignment(assignment) {
    const decodedJwt = jwt_decode(user.jwt);
    const codeReviewer = {
      username: decodedJwt.sub,
    };

    assignment.codeReviewer = codeReviewer;
    // TODO: don't hardcode this status
    assignment.status = "In Review";
    ajax(`/api/assignments/${assignment.id}`, "PUT", user.jwt, assignment).then(
      (updatedAssignment) => {
        //TODO: update the view for the assignment that changed
        const assignmentsCopy = [...assignments];
        const i = assignmentsCopy.findIndex((a) => a.id === assignment.id);
        assignmentsCopy[i] = updatedAssignment;
        setAssignments(assignmentsCopy);
      }
    );
  }

  useEffect(() => {
    ajax("api/assignments", "GET", user.jwt).then((assignmentsData) => {
      setAssignments(assignmentsData);
      console.log(assignmentsData);
    });
  }, [user.jwt]);

  return (
    <>
      <NavBar />
      <Container>
        <Row>
          <Col>
            <div className="h1">Code Reviewer Dashboard</div>
          </Col>
        </Row>
        <div className="assignment-wrapper in-review">
          <div className="assignment-wrapper-title h3 px-2">In Review</div>
          {assignments &&
          assignments.filter((assignment) => assignment.status === "In Review")
            .length > 0 ? (
            <div
              className="d-grid gap-5"
              style={{ gridTemplateColumns: "repeat(auto-fit, 18rem)" }}
            >
              {assignments
                .filter((assignment) => assignment.status === "In Review")
                .map((assignment) => (
                  <Card key={assignment.id} style={{ width: "18rem" }}>
                    <Card.Body className="d-flex flex-column justify-content-around">
                      <Card.Title>Assignment #{assignment.number}</Card.Title>
                      {assignment.name ? (
                        <Card.Subtitle style={{ marginBottom: "0.5em" }}>
                          {assignment.name}
                        </Card.Subtitle>
                      ) : (
                        <></>
                      )}
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
                        <p>
                          <b>Student</b>: {assignment.user.name}
                        </p>
                      </Card.Text>
                      <Button
                        variant="secondary"
                        onClick={() => {
                          editReview(assignment);
                        }}
                      >
                        Edit
                      </Button>
                    </Card.Body>
                  </Card>
                ))}
            </div>
          ) : (
            <div>No assignments found</div>
          )}
        </div>

        <div className="assignment-wrapper submitted">
          <div className="assignment-wrapper-title h3 px-2">
            Awaiting Review
          </div>
          {assignments &&
          assignments.filter(
            (assignment) =>
              assignment.status === "Submitted" ||
              assignment.status === "Resubmitted"
          ).length > 0 ? (
            <div
              className="d-grid gap-5"
              style={{ gridTemplateColumns: "repeat(auto-fit, 18rem)" }}
            >
              {assignments
                .filter(
                  (assignment) =>
                    assignment.status === "Submitted" ||
                    assignment.status === "Resubmitted"
                )
                .sort((a, b) => {
                  if (a.status === "Resubmitted") return -1;
                  if (a.submittedDate && b.submittedDate) {
                    return (
                      new Date(a.submittedDate) - new Date(b.submittedDate)
                    );
                  } else return 1;
                })
                .map((assignment) => (
                  <Card key={assignment.id} style={{ width: "18rem" }}>
                    <Card.Body className="d-flex flex-column justify-content-around">
                      <Card.Title>Assignment #{assignment.number}</Card.Title>

                      {assignment.name ? (
                        <Card.Subtitle style={{ marginBottom: "0.5em" }}>
                          {assignment.name}
                        </Card.Subtitle>
                      ) : (
                        <></>
                      )}
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
                        <p>
                          <b>Student</b>: {assignment.user.name}
                        </p>
                        <p>
                          <b>Submitted</b>:{" "}
                          {assignment.submittedDate
                            ? moment(assignment.submittedDate).format(
                                "MMMM Do YYYY"
                              )
                            : "No submitted date"}
                        </p>
                      </Card.Text>

                      <Button
                        variant="secondary"
                        onClick={() => {
                          claimAssignment(assignment);
                        }}
                      >
                        Claim
                      </Button>
                    </Card.Body>
                  </Card>
                ))}
            </div>
          ) : (
            <div>No assignments found</div>
          )}
        </div>

        <div className="assignment-wrapper needs-update">
          <div className="assignment-wrapper-title h3 px-2">Needs Update</div>
          {assignments &&
          assignments.filter(
            (assignment) => assignment.status === "Needs Update"
          ).length > 0 ? (
            <div
              className="d-grid gap-5"
              style={{ gridTemplateColumns: "repeat(auto-fit, 18rem)" }}
            >
              {assignments
                .filter((assignment) => assignment.status === "Needs Update")
                .map((assignment) => (
                  <Card key={assignment.id} style={{ width: "18rem" }}>
                    <Card.Body className="d-flex flex-column justify-content-around">
                      <Card.Title>Assignment #{assignment.number}</Card.Title>
                      {assignment.name ? (
                        <Card.Subtitle style={{ marginBottom: "0.5em" }}>
                          {assignment.name}
                        </Card.Subtitle>
                      ) : (
                        <></>
                      )}
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
                        <p>
                          <b>Student</b>: {assignment.user.name}
                        </p>
                      </Card.Text>

                      <Button
                        variant="secondary"
                        onClick={() => {
                          navigate(`/assignments/${assignment.id}`);
                        }}
                      >
                        View
                      </Button>
                    </Card.Body>
                  </Card>
                ))}
            </div>
          ) : (
            <div>No assignments found</div>
          )}
        </div>
      </Container>
    </>
  );
};

export default CodeReviewerDashboard;
