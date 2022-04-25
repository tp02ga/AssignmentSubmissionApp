function getButtonsByStatusAndRole(currentStatus, role) {
  let buttons = [];
  if (currentStatus === "Pending Submission" && role === "student") {
    buttons.push(getButton("Save"));
    buttons.push(getButton("Submit"));
  } else if (currentStatus === "Submitted" && role === "student") {
    buttons.push(getButton("Un-submit"));
  } else if (currentStatus === "Needs Update" && role === "student") {
    buttons.push(getButton("Resubmit Assignment"));
  } else if (currentStatus === "In Review" && role === "code_reviewer") {
    buttons.push(getButton("Complete Review"));
    buttons.push(getButton("Reject Assignment"));
  } else if (currentStatus === "Completed" && role === "code_reviewer") {
    buttons.push(getButton("Re-claim"));
  } else if (currentStatus === "Needs Update" && role === "code_reviewer") {
    buttons.push(getButton("Re-claim"));
  } else if (currentStatus === "Resubmitted" && role === "student") {
    buttons.push(getButton("Un-submit"));
  }

  return buttons;
}

function getButton(type, currentStatus) {
  let button = {};
  button.text = type;
  switch (type) {
    case "Save":
      button.variant = "secondary";
      button.nextStatus = "Same";
      break;
    case "Submit":
      button.variant = "primary";
      button.nextStatus = "Submitted";
      break;
    case "Un-submit":
      button.variant = "secondary";
      button.nextStatus = "Pending Submission";
      break;
    case "Resubmit Assignment":
      button.variant = "primary";
      button.nextStatus = "Resubmitted";
      break;
    case "Complete Review":
      button.variant = "primary";
      button.nextStatus = "Completed";
      break;
    case "Reject Assignment":
      button.variant = "danger";
      button.nextStatus = "Needs Update";
      break;
    case "Re-claim":
      if (currentStatus === "Submitted") {
        button.nextStatus = "Pending Submission";
      } else if (currentStatus === "Needs Update" || "Completed") {
        button.nextStatus = "In Review";
      }
      button.variant = "secondary";
      break;
    default:
      button.variant = "info";
      break;
  }
  return button;
}

export { getButtonsByStatusAndRole };
