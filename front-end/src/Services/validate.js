/**
 * Is the `input` value inside the set of `validValues`?
 * @param {*} input the input value that we're trying to validate
 * @param {*} validValues an array of valid values for the input
 */
function isValidValue(input, validValues) {
  const valid =
    validValues.filter((validValue) => validValue == input).length > 0;
  if (!valid) {
    throw new Error(
      `Input value ${input} not in set of valid values ${validValues}`
    );
  }
}

export { isValidValue };
