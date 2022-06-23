import dayjs from "dayjs";
import { isValidValue } from "./validate";

const nineMonthDueDatesInWeeks = [
  2, 7, 10, 16, 18, 20, 21, 22, 23, 24, 25, 27, 29, 32, 36,
];
const sixMonthDueDatesinWeeks = [
  3, 6, 7, 10, 11, 12, 14, 15, 16, 17, 18, 20, 22, 24, 26,
];

function getDueDates(sd, courseDurationInWeeks, assignments) {
  isValidValue(courseDurationInWeeks, [24, 36]);
  const startDate = dayjs(sd);

  if (courseDurationInWeeks === 36) {
    return nineMonthDueDatesInWeeks.map((weekDue, i) => {
      return {
        name: `#${i + 1}`,
        dueDate: startDate.add(weekDue, "week").format("MMM-DD"),
        value: 6.6,
        color: getColor(assignments, i + 1, startDate.add(weekDue, "week")),
      };
    });
  } else {
    return sixMonthDueDatesinWeeks.map((weekDue, i) => {
      return {
        name: `#${i + 1}`,
        dueDate: startDate.add(weekDue, "week").format("MMM-DD"),
        value: 6.6,
        color: getColor(assignments, i + 1, startDate.add(weekDue, "week")),
      };
    });
  }
}

function numAssignmentsThatShouldBeCompleted(sd, courseDurationInWeeks) {
  const startDate = dayjs(sd);
  const now = dayjs();
  const weekDiff = now.diff(startDate, "week");
  let assignmentNumber = 15;

  if (courseDurationInWeeks === 36) {
    for (let i = 0; i < nineMonthDueDatesInWeeks.length; i++) {
      if (weekDiff < nineMonthDueDatesInWeeks[i]) {
        assignmentNumber = i + 1;
        break;
      }
    }
    return assignmentNumber;
  } else {
    for (let i = 0; i < sixMonthDueDatesinWeeks.length; i++) {
      if (weekDiff < sixMonthDueDatesinWeeks[i]) {
        assignmentNumber = i + 1;
        break;
      }
    }
    return assignmentNumber;
  }
}

function getColor(assignments, num, dueDate) {
  const assignment = assignments.filter((a) => a.number === num);
  const now = dayjs();
  if (assignment.length > 0) {
    if (assignment[0].status === "Completed") return "green";

    if (now.isAfter(dueDate)) {
      if (assignment[0].status === "Submitted") {
        return "rgb(255, 193, 7)";
      } else if (assignment[0].status === "Needs Update") {
        return "orange";
      } else {
        return "rgb(220, 53, 69)";
      }
    } else return "grey";
  } else {
    if (now.isAfter(dueDate)) return "rgb(220, 53, 69)";
    else return "grey";
  }
}
export { getDueDates, numAssignmentsThatShouldBeCompleted };
