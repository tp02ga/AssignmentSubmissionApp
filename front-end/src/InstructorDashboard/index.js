import React, { useEffect, useState } from "react";
import NavBar from "../NavBar";
import { Col, Container, Row } from "react-bootstrap";
import ajax from "../Services/fetchService";
import { useUser } from "../UserProvider";
import { numAssignmentsThatShouldBeCompleted } from "../Services/assignmentDueDatesService";
import dayjs from "dayjs";
import "./instructor-dashboard.css";
import InstructorStudentEditModal from "../InstructorStudentEditModal";

const InstructorDashboard = () => {
  const user = useUser();
  const [userAssignments, setUserAssignments] = useState(null);
  const [nonConfiguredUsers, setNonConfiguredUsers] = useState(null);
  const [bootcampStudents, setBootcampStudents] = useState(null);
  const [studentEditModal, setStudentEditModal] = useState(<></>);

  const handleCloseStudentEdit = () => {
    setStudentEditModal(<></>);
  };

  const handleSaveStudent = (data) => {
    ajax(`/api/users/${data.email}`, "put", user.jwt, data).then(() => {
      handleCloseStudentEdit();
    });
  };

  useEffect(() => {
    ajax("/api/assignments/all", "get", user.jwt).then((data) => {
      setUserAssignments(data);
    });

    ajax("/api/users/bootcamp-students", "get", user.jwt).then((data) => {
      setBootcampStudents(data);
    });
  }, []);

  useEffect(() => {
    if (userAssignments && bootcampStudents) {
      const nonConfigured = bootcampStudents.filter((student) => {
        let found = false;
        Object.entries(userAssignments).forEach((entry) => {
          const key = JSON.parse(entry[0]);
          if (key.email === student.email) {
            found = true;
          }
        });
        return !found;
      });
      setNonConfiguredUsers(nonConfigured);
    }
  }, [userAssignments, bootcampStudents]);

  const getColor = (delta) => {
    if (delta <= -1) {
      return "#b2e0b2"; // green
    } else if (delta === 1) {
      return "yellow"; // yellow
    } else if (delta === 2) {
      return "orange"; // orange
    } else if (delta === 3) {
      return "#f65555"; // red
    } else if (delta >= 4) {
      return "#c70000";
    }
  };
  return (
    <>
      <NavBar />
      <Container>
        {studentEditModal}
        <Row className="mt-4">
          <Col>
            <div className="h1">Instructor Dashboard</div>
          </Col>
        </Row>
        {userAssignments && nonConfiguredUsers ? (
          <table className="mt-5">
            <thead>
              <tr>
                <th>Cohort</th>
                <th>Name</th>
                <th>Email</th>
                <th>Assignments Submitted</th>
                <th>Assignments Expected</th>
              </tr>
            </thead>
            <tbody>
              {nonConfiguredUsers
                .filter((student) => {
                  return student.bootcampDurationInWeeks && student.startDate;
                })
                .map((student) => {
                  let numAssignmentsToBeDone =
                    numAssignmentsThatShouldBeCompleted(
                      student.startDate,
                      student.bootcampDurationInWeeks
                    );
                  let numAssignmentsDone = 0;
                  return (
                    <tr key={`${student.email}-active`}>
                      <td>
                        {dayjs(student.startDate, "YYYY-M-D").format(
                          "MMM YYYY"
                        )}
                      </td>
                      <td
                        style={{
                          backgroundColor: getColor(
                            numAssignmentsToBeDone - numAssignmentsDone
                          ),
                        }}
                      >
                        {student.name}
                      </td>
                      <td>{student.email}</td>
                      <td className="text-center">{numAssignmentsDone}</td>
                      <td className="text-center">{numAssignmentsToBeDone}</td>
                    </tr>
                  );
                })}
              {Object.entries(userAssignments).map((entry) => {
                const [keyData, value] = entry;
                let key = JSON.parse(keyData);
                let numAssignmentsToBeDone =
                  numAssignmentsThatShouldBeCompleted(
                    key.startDate,
                    key.bootcampDurationInWeeks
                  );
                let numAssignmentsDone = value.length;
                return (
                  <tr key={`${key.email}-active`}>
                    <td>
                      {dayjs(key.startDate, "YYYY-M-D").format("MMM YYYY")}
                    </td>
                    <td
                      style={{
                        backgroundColor: getColor(
                          numAssignmentsToBeDone - numAssignmentsDone
                        ),
                      }}
                    >
                      {key.name}
                    </td>
                    <td>{key.email}</td>
                    <td className="text-center">{numAssignmentsDone}</td>
                    <td className="text-center">{numAssignmentsToBeDone}</td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        ) : (
          <></>
        )}

        {nonConfiguredUsers ? (
          <>
            <h2 className="mt-5">Non Configured Users</h2>
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {nonConfiguredUsers
                  .filter((u) => !u.bootcampDurationInWeeks || !u.startDate)
                  .map((u) => (
                    <tr key={`${u.email}-inactive}`}>
                      <td>{u.name}</td>
                      <td>{u.email}</td>
                      <td id={`${u.email}-configure`}>
                        <div
                          className="blue-link"
                          onClick={() => {
                            setStudentEditModal(
                              <InstructorStudentEditModal
                                emitClose={handleCloseStudentEdit}
                                studentEmail={u.email}
                                emitSave={handleSaveStudent}
                              />
                            );
                          }}
                        >
                          configure
                        </div>
                      </td>
                    </tr>
                  ))}
              </tbody>
            </table>
          </>
        ) : (
          <></>
        )}
      </Container>
    </>
  );
};

export default InstructorDashboard;
