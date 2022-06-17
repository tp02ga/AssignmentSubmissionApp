import React from "react";
import "./multicolor-progress-bar.css";

class MultiColorProgressBar extends React.Component {
  render() {
    const parent = this.props;

    console.log("MultiProgressBar with ", parent);
    let values =
      parent.readings &&
      parent.readings.length &&
      parent.readings.map(function (item, i) {
        if (item.value > 0) {
          return (
            <div
              className="value"
              style={{ color: item.color, width: item.value + "%" }}
              key={i}
            >
              <div>{item.name}</div>
              <div>{item.dueDate}</div>
            </div>
          );
        }
      }, this);

    let calibrations =
      parent.readings &&
      parent.readings.length &&
      parent.readings.map(function (item, i) {
        if (item.value > 0) {
          return (
            <div
              className="graduation"
              style={{ color: item.color, width: item.value + "%" }}
              key={i}
            >
              <span>|</span>
            </div>
          );
        }
      }, this);

    let bars =
      parent.readings &&
      parent.readings.length &&
      parent.readings.map(function (item, i) {
        if (item.value > 0) {
          return (
            <div
              className="bar"
              style={{ backgroundColor: item.color, width: item.value + "%" }}
              key={i}
            ></div>
          );
        }
      }, this);

    let legends =
      parent.readings &&
      parent.readings.length &&
      parent.readings.map(function (item, i) {
        if (item.value > 0) {
          return (
            <div className="legend" key={i}>
              <span className="dot" style={{ color: item.color }}>
                ●
              </span>
              <span className="label">{item.name}</span>
            </div>
          );
        }
      }, this);

    return (
      <div className="multicolor-bar">
        <div className="values">{values == "" ? "" : values}</div>
        <div className="scale">{calibrations == "" ? "" : calibrations}</div>
        <div className="bars">{bars == "" ? "" : bars}</div>
      </div>
    );
  }
}

export default MultiColorProgressBar;
