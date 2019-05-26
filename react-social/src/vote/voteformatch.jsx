import React, { Component } from "react";
import "./radio_css.css";

// Externals
import classNames from "classnames";
import PropTypes from "prop-types";

// Material helpers
import { withStyles } from "@material-ui/core";

// Material components
import { Button } from "@material-ui/core";

// Shared components
import {
  Portlet,
  PortletHeader,
  PortletLabel,
  PortletToolbar,
  PortletContent,
  PortletFooter
} from "../components";

// Component styles
import styles from "./styles";

class VoteForMatch extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedOption: "option1"
    };
  }

  handleOptionChange = changeEvent => {
    alert("--" + changeEvent.target.value + "--");
    this.setState({
      selectedOption: changeEvent.target.value
    });
  };

  handleFormSubmit = formSubmitEvent => {
    formSubmitEvent.preventDefault();

    console.log("You have submitted:", this.state.selectedOption);
  };

  render() {
    const { classes, className, ...rest } = this.props;

    const rootClassName = classNames(classes.root, className);
    const bg1 = require("../img/teams/AFG.png");
    const bg2 = require("../img/teams/AUS.png");

    return (
      <div className="container">
        <div className="row">
          <div className="col-sm-12">
            <form onSubmit={this.handleFormSubmit}>
              <div>
                <div className="form-check">
                  <label
                    className="drinkcard-cc"
                    style={{ backgroundImage: "url(" + bg1 + ")" }}
                    htmlFor="rdbTeam1"
                  >
                    <input
                      type="radio"
                      id="rdbTeam1"
                      name="react-tips"
                      value="option1"
                      checked={this.state.selectedOption === "option1"}
                      onChange={this.handleOptionChange}
                      className="form-check-input"
                    />
                  </label>
                </div>
                <div className="form-check">
                  <label
                    className="drinkcard-cc"
                    style={{ backgroundImage: "url(" + bg2 + ")" }}
                    htmlFor="rdbTeam2"
                  >
                    <input
                      type="radio"
                      id="rdbTeam2"
                      name="react-tips"
                      value="option2"
                      checked={this.state.selectedOption === "option2"}
                      onChange={this.handleOptionChange}
                      className="form-check-input"
                    />
                  </label>
                </div>
              </div>
              <button className="btn btn-default" type="submit">
                Save
              </button>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

VoteForMatch.propTypes = {
  className: PropTypes.string,
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(VoteForMatch);
