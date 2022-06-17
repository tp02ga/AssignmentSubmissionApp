import React, { useEffect, useState } from "react";
import { Button, Card, Col, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import NavBar from "../NavBar";
import ajax from "../Services/fetchService";
import StatusBadge from "../StatusBadge";
import { useUser } from "../UserProvider";
import MultiColorProgressBar from "../MultiColorProgressBar";
import jwt_decode from "jwt-decode";
import { getDueDates } from "../Services/assignmentDueDatesService";

const Dashboard = () => {
  const navigate = useNavigate();
  const user = useUser();
  const [assignments, setAssignments] = useState(null);
  const [userData, setUserData] = useState(null);
  const [asignmentDueDates, setAssignmentDueDates] = useState(null);

  useEffect(() => {
    const decodedJwt = jwt_decode(user.jwt);
    if (!userData && assignments) {
      ajax("api/users/" + decodedJwt.sub, "GET", user.jwt).then((data) => {
        setUserData(data);
        let dueDates = getDueDates(
          data.cohortStartDate,
          data.bootcampDurationInWeeks,
          assignments
        );

        setAssignmentDueDates(dueDates);
      });
    }
  }, [user, userData, assignments]);

  useEffect(() => {
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
        {asignmentDueDates ? (
          <MultiColorProgressBar readings={asignmentDueDates} />
        ) : (
          <></>
        )}

        <div style={{ clear: "both", marginBottom: "3rem" }}></div>
        <div
          className="d-grid gap-5"
          style={{ gridTemplateColumns: "repeat(auto-fit, 18rem)" }}
        >
          {assignments &&
            assignments.map((assignment) => (
              // <Col>
              <Card
                key={assignment.id}
                style={{ width: "18rem", height: "20rem" }}
              >
                <Card.Body className="d-flex flex-column justify-content-around">
                  <Card.Title>Assignment #{assignment.number}</Card.Title>
                  <div className="d-flex align-items-start">
                    <StatusBadge text={assignment.status} />
                  </div>

                  <Card.Text style={{ marginTop: "1em" }}>
                    <b>GitHub URL</b>: {assignment.githubUrl}
                    <br />
                    <b>Branch</b>: {assignment.branch}
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
          <Card style={{ width: "18rem", height: "20rem" }}>
            <Card.Body className="d-flex flex-column justify-content-around">
              <Button size="lg" onClick={() => createAssignment()}>
                Submit New Assignment
              </Button>
            </Card.Body>
          </Card>
        </div>
      </div>
    </>
  );
};

export default Dashboard;
