import React, { Component } from "react";

import { Portlet, PortletContent } from "../../components";
import { Typography } from "@material-ui/core";
import { withStyles } from "@material-ui/core";

import classNames from "classnames";
import styles from "./styles";

class RulesPortlet extends Component {
  constructor(props) {
    super(props);
  }

  state = {};
  render() {
    const { classes, className, ...rest } = this.props;
    const rootClassName = classNames(classes.root, className);

    return (
      <Portlet {...rest} className={rootClassName}>
        <PortletContent>
          <Typography className={classes.title} variant="h4">
            RULES:
          </Typography>
          <br />
          <Typography
            className={classes.title}
            component={"span"}
            variant="body1"
          >
            <ol>
              <li>
                For each correct guess, points equivalent to 1 point per losing
                player divided by number of winning players will be awarded to
                each winner.
              </li>
              <li>Each incorrect guess will cost you 1 point.</li>
              <li>
                In case a match results in a draw, no points will be rewarded or
                deducted.
              </li>
              <li>
                In case of no votes for any of the team, either winning or
                losing, no points will be deducted or rewarded.
              </li>
            </ol>
          </Typography>
        </PortletContent>
      </Portlet>
    );
  }
}

export default withStyles(styles)(RulesPortlet);
