import { Form, Button, Modal } from "react-bootstrap";
import React, { useEffect, useState } from "react";
import { useUser } from "../UserProvider";
import ajax from "../Services/fetchService";

const InstructorStudentEditModal = (props) => {
  const { emitClose, emitSave, studentEmail } = props;
  const user = useUser();
  const [student, setStudent] = useState(null);
  useEffect(() => {
    ajax(`/api/users/${studentEmail}`, "get", user.jwt).then((data) => {
      if (!data) {
        let yesNo = window.confirm(
          "User doesn't exist in the Assignment Submission app, do you want to create the user?"
        );
        if (yesNo) {
          ajax(`/api/users/${studentEmail}`, "put", user.jwt, {
            email: studentEmail,
          }).then((data) => {
            emitClose();
          });
        } else {
          emitClose();
        }
      }
      data.email = data.username;
      setStudent(data);
    });
  }, []);

  const updateStudentData = (field, value) => {
    const studentCopy = { ...student };
    studentCopy[field] = value;
    setStudent(studentCopy);
  };
  return (
    <>
      <Modal show={true} onHide={emitClose}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Student Info</Modal.Title>
        </Modal.Header>
        {student ? (
          <>
            <Modal.Body>
              <Form>
                <Form.Group controlId="name">
                  <Form.Label>Name</Form.Label>
                  <Form.Control
                    type="text"
                    value={student.name}
                    onChange={(e) => updateStudentData("name", e.target.value)}
                  ></Form.Control>
                </Form.Group>
                <Form.Group controlId="email">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="email"
                    value={student.username}
                    readOnly
                  ></Form.Control>
                </Form.Group>
                <Form.Group controlId="startDate">
                  <Form.Label>Start Date</Form.Label>
                  <Form.Control
                    type="date"
                    value={student.cohortStartDate}
                    onChange={(e) =>
                      updateStudentData("startDate", e.target.value)
                    }
                  ></Form.Control>
                </Form.Group>
                <Form.Group controlId="duration">
                  <Form.Label>Bootcamp Duration (Weeks)</Form.Label>
                  <Form.Control
                    type="number"
                    value={student.bootcampDurationInWeeks}
                    onChange={(e) =>
                      updateStudentData(
                        "bootcampDurationInWeeks",
                        e.target.value
                      )
                    }
                  ></Form.Control>
                </Form.Group>
              </Form>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={emitClose}>
                Close
              </Button>
              <Button
                variant="primary"
                onClick={() => {
                  if (
                    student.bootcampDurationInWeeks == 24 ||
                    student.bootcampDurationInWeeks == 36
                  ) {
                    emitSave(student);
                  } else {
                    alert("Bootcamp Duration in Weeks must be 24 or 36");
                  }
                }}
              >
                Save Changes
              </Button>
            </Modal.Footer>
          </>
        ) : (
          <div className="p-5">Loading...</div>
        )}
      </Modal>
    </>
  );
};

export default InstructorStudentEditModal;
