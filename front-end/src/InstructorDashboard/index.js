import React, { useEffect, useState } from "react";
import NavBar from "../NavBar";
import { Col, Container, Row } from "react-bootstrap";
import ajax from "../Services/fetchService";
import { useUser } from "../UserProvider";
import { numAssignmentsThatShouldBeCompleted } from "../Services/assignmentDueDatesService";
import dayjs from "dayjs";
import "./instructor-dashboard.css";

const InstructorDashboard = () => {
  const user = useUser();
  const [userAssignments, setUserAssignments] = useState(null);
  const [nonConfiguredUsers, setNonConfiguredUsers] = useState(null);

  useEffect(() => {
    ajax("/api/assignments/all", "get", user.jwt).then((data) => {
      setUserAssignments(data);
    });
    ajax("/api/users/non-configured", "get", user.jwt).then((data) => {
      setNonConfiguredUsers(data);
    });
  }, []);

  useEffect(() => {
    if (userAssignments) {
      console.log("User assignments: ", userAssignments);
      Object.entries(userAssignments).forEach((entry) => {
        const [key, value] = entry;
        console.log("key: ", key);
        console.log("value: ", value);
      });
    }
  }, [userAssignments]);

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
        <Row className="mt-4">
          <Col>
            <div className="h1">Instructor Dashboard</div>
          </Col>
        </Row>
        {userAssignments ? (
          <table className="mt-5">
            <tr>
              <th>Cohort</th>
              <th>Name</th>
              <th>Email</th>
              <th>Assignments Submitted</th>
              <th>Assignments Expected</th>
            </tr>

            {Object.entries(userAssignments).map((entry, index) => {
              const [keyData, value] = entry;
              let key = JSON.parse(keyData);
              let numAssignmentsToBeDone = numAssignmentsThatShouldBeCompleted(
                key.startDate,
                key.bootcampDurationInWeeks
              );
              let numAssignmentsDone = value.length;
              return (
                <tr>
                  <td>{dayjs(key.startDate, "YYYY-M-D").format("MMM YYYY")}</td>
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
          </table>
        ) : (
          <></>
        )}

        {nonConfiguredUsers ? (
          <>
            <h2 className="mt-5">Non Configured Users</h2>
            <table>
              <tr>
                <th>Name</th>
                <th>Email</th>
              </tr>
              {nonConfiguredUsers.map((u) => (
                <tr>
                  <td>{u.name}</td>
                  <td>{u.email}</td>
                </tr>
              ))}
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
