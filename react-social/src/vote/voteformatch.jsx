import React, { Component } from "react";
import "./radio_css.css";

// Externals
import classNames from "classnames";
import PropTypes from "prop-types";
import ReactRadioButtonGroup from "react-radio-button-group";
import { Typography } from "@material-ui/core";

// Material helpers
import { withStyles } from "@material-ui/core";

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
    //alert("--" + changeEvent.target.value + "--");
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

    return (
      <div className="container">
        <form onSubmit={this.handleFormSubmit}>
          <div className="cc-selector">
            <Typography variant="body1" className="matchText">
              1st Warm-up game County Ground Bristol, England, 24 May 2019 10:30
              AM GMT
            </Typography>
            <ReactRadioButtonGroup
              options={[
                {
                  value: "AFG",
                  label: "AFG",
                  itemClassName: "team1",
                  labelClassName: "drinkcard-cc AFG"
                },
                {
                  value: "AUS",
                  label: "AUS",
                  itemClassName: "team2",
                  labelClassName: "drinkcard-cc AUS"
                }
              ]}
              name="rdbTeam"
              isStateful={true}
            />
          </div>
        </form>
      </div>
    );
  }
}

VoteForMatch.propTypes = {
  className: PropTypes.string,
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(VoteForMatch);
