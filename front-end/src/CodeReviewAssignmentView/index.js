import React, { useEffect, useRef, useState } from "react";
import { Button, Col, Container, Form, Row } from "react-bootstrap";
import CommentContainer from "../CommentContainer";
import NavBar from "../NavBar";
import ajax from "../Services/fetchService";
import { getButtonsByStatusAndRole } from "../Services/statusService";
import StatusBadge from "../StatusBadge";
import { useUser } from "../UserProvider";

const CodeReviewerAssignmentView = () => {
  const user = useUser();
  const assignmentId = window.location.href.split("/assignments/")[1];
  const [assignment, setAssignment] = useState({
    branch: "",
    githubUrl: "",
    number: null,
    status: null,
    codeReviewVideoUrl: null,
  });
  const [assignmentEnums, setAssignmentEnums] = useState([]);
  const [assignmentStatuses, setAssignmentStatuses] = useState([]);
  const [errorMsg, setErrorMsg] = useState("");
  const prevAssignmentValue = useRef(assignment);

  function updateAssignment(prop, value) {
    const newAssignment = { ...assignment };
    newAssignment[prop] = value;
    setAssignment(newAssignment);
  }

  function save(status) {
    setErrorMsg("");
    console.log(
      `Status: ${status}, codeReviewVideoUrl: ${assignment.codeReviewVideoUrl}`
    );
    if (
      status === "Completed" &&
      (assignment.codeReviewVideoUrl === null ||
        assignment.codeReviewVideoUrl === "")
    ) {
      setErrorMsg(
        "Please insert the URL to the video review for the student to watch."
      );
      return;
    }
    if (status && assignment.status !== status) {
      updateAssignment("status", status);
    } else {
      persist();
    }
  }

  function persist() {
    ajax(`/api/assignments/${assignmentId}`, "PUT", user.jwt, assignment).then(
      (assignmentData) => {
        setAssignment(assignmentData);
      }
    );
  }
  useEffect(() => {
    if (prevAssignmentValue.current.status !== assignment.status) {
      persist();
    }
    prevAssignmentValue.current = assignment;
  }, [assignment]);

  useEffect(() => {
    ajax(`/api/assignments/${assignmentId}`, "GET", user.jwt).then(
      (assignmentResponse) => {
        let assignmentData = assignmentResponse.assignment;
        if (assignmentData.branch === null) assignmentData.branch = "";
        if (assignmentData.githubUrl === null) assignmentData.githubUrl = "";
        console.log("Assignment data: ", assignmentData);
        setAssignment(assignmentData);
        setAssignmentEnums(assignmentResponse.assignmentEnums);
        setAssignmentStatuses(assignmentResponse.statusEnums);
      }
    );
  }, []);

  return (
    <>
      <NavBar />
      <Container className="mt-5">
        <Row className="d-flex align-items-center">
          <Col>
            {assignment.number ? (
              <h1>Assignment {assignment.number} </h1>
            ) : (
              <></>
            )}
          </Col>
          <Col>
            <StatusBadge text={assignment.status} />
          </Col>
        </Row>
        <Row>
          <Col className="error">{errorMsg}</Col>
        </Row>

        {assignment ? (
          <>
            {assignment.user ? (
              <Form.Group as={Row} className="my-3" controlId="githubUrl">
                <Form.Label column sm="3" md="2">
                  Student Name:
                </Form.Label>
                <Col sm="9" md="8" lg="6">
                  <Form.Control
                    type="text"
                    readOnly
                    value={assignment.user.name}
                  />
                </Col>
              </Form.Group>
            ) : (
              <></>
            )}
            <Form.Group as={Row} className="my-3" controlId="githubUrl">
              <Form.Label column sm="3" md="2">
                GitHub URL:
              </Form.Label>
              <Col sm="9" md="8" lg="6">
                <Form.Control
                  onChange={(e) =>
                    updateAssignment("githubUrl", e.target.value)
                  }
                  type="url"
                  readOnly
                  value={assignment.githubUrl}
                  placeholder="https://github.com/username/repo-name"
                />
              </Col>
            </Form.Group>

            <Form.Group as={Row} className="mb-3" controlId="branch">
              <Form.Label column sm="3" md="2">
                Branch:
              </Form.Label>
              <Col sm="9" md="8" lg="6">
                <Form.Control
                  type="text"
                  readOnly
                  placeholder="example_branch_name"
                  onChange={(e) => updateAssignment("branch", e.target.value)}
                  value={assignment.branch}
                />
              </Col>
            </Form.Group>

            <Form.Group as={Row} className="my-3" controlId="githubUrl">
              <Form.Label column sm="3" md="2">
                Video Review URL:
              </Form.Label>
              <Col sm="9" md="8" lg="6">
                <Form.Control
                  onChange={(e) =>
                    updateAssignment("codeReviewVideoUrl", e.target.value)
                  }
                  type="url"
                  value={assignment.codeReviewVideoUrl}
                  placeholder="https://screencast-o-matic.com/something"
                />
              </Col>
            </Form.Group>

            <div className="d-flex gap-5">
              {getButtonsByStatusAndRole(
                assignment.status,
                "code_reviewer"
              ).map((btn) => (
                <Button
                  size="lg"
                  variant={btn.variant}
                  onClick={() => save(btn.nextStatus)}
                >
                  {btn.text}
                </Button>
              ))}
            </div>

            <CommentContainer assignmentId={assignmentId} />
          </>
        ) : (
          <></>
        )}
      </Container>
    </>
  );
};

export default CodeReviewerAssignmentView;
