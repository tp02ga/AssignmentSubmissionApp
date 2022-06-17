import dayjs from "dayjs";
import { isValidValue } from "./validate";

function getDueDates(sd, courseDurationInWeeks, assignments) {
  isValidValue(courseDurationInWeeks, [24, 36]);
  let startDate = dayjs(sd);

  console.log("Assignments: ", assignments);
  let readings = [
    {
      name: `#1`,
      dueDate: startDate.add(3, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 1, startDate.add(3, "week")),
    },
    {
      name: "#2",
      dueDate: startDate.add(6, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 2, startDate.add(6, "week")),
    },
    {
      name: "#3",
      dueDate: startDate.add(7, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 3, startDate.add(7, "week")),
    },
    {
      name: "#4",
      dueDate: startDate.add(10, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 4, startDate.add(10, "week")),
    },
    {
      name: "#5",
      dueDate: startDate.add(11, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 5, startDate.add(11, "week")),
    },
    {
      name: "#6",
      dueDate: startDate.add(12, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 6, startDate.add(12, "week")),
    },
    {
      name: "#7",
      dueDate: startDate.add(14, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 7, startDate.add(14, "week")),
    },
    {
      name: "#8",
      dueDate: startDate.add(15, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 8, startDate.add(15, "week")),
    },
    {
      name: "#9",
      dueDate: startDate.add(16, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 9, startDate.add(16, "week")),
    },
    {
      name: "#10",
      dueDate: startDate.add(17, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 10, startDate.add(17, "week")),
    },
    {
      name: "#11",
      dueDate: startDate.add(18, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 11, startDate.add(18, "week")),
    },
    {
      name: "#12",
      dueDate: startDate.add(20, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 12, startDate.add(20, "week")),
    },
    {
      name: "#13",
      dueDate: startDate.add(22, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 13, startDate.add(22, "week")),
    },
    {
      name: "#14",
      dueDate: startDate.add(24, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 14, startDate.add(24, "week")),
    },
    {
      name: "Final",
      dueDate: startDate.add(26, "week").format("MMM-DD"),
      value: 6.6,
      color: getColor(assignments, 15, startDate.add(26, "week")),
    },
  ];

  return readings;
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
