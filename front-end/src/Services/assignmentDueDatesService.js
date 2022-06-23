import dayjs from "dayjs";
import { isValidValue } from "./validate";

function getDueDates(sd, courseDurationInWeeks, assignments) {
  isValidValue(courseDurationInWeeks, [24, 36]);
  let startDate = dayjs(sd);

  let nineMonthDueDatesInWeeks = [
    2, 7, 10, 16, 18, 20, 21, 22, 23, 24, 25, 27, 29, 32, 36,
  ];
  let sixMonthDueDatesinWeeks = [
    3, 6, 7, 10, 11, 12, 14, 15, 16, 17, 18, 20, 22, 24, 26,
  ];

  if (courseDurationInWeeks === 24) {
    return sixMonthDueDatesinWeeks.map((weekDue, i) => {
      return {
        name: `#${i + 1}`,
        dueDate: startDate.add(weekDue, "week").format("MMM-DD"),
        value: 6.6,
        color: getColor(assignments, i + 1, startDate.add(weekDue, "week")),
      };
    });
  } else {
    return nineMonthDueDatesInWeeks.map((weekDue, i) => {
      return {
        name: `#${i + 1}`,
        dueDate: startDate.add(weekDue, "week").format("MMM-DD"),
        value: 6.6,
        color: getColor(assignments, i + 1, startDate.add(weekDue, "week")),
      };
    });
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
export { getDueDates };
