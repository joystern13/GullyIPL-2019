import React, { Component } from "react";
import { Dashboard as DashboardLayout } from "../layouts";
import { Grid, Typography } from "@material-ui/core";
import PropTypes from "prop-types";

// Material helpers
import { withStyles } from "@material-ui/core";

import classNames from "classnames";

import { Portlet, PortletContent, Paper } from "../components";
import VoteForMatch from "./voteformatch";

// Component styles
const styles = theme => ({
  root: {
    padding: theme.spacing(4)
  },
  item: {
    height: "100%"
  }
});

class Vote extends Component {
  state = {};

  render() {
    const { classes, className, ...rest } = this.props;
    const rootClassName = classNames(classes.root, className);

    return (
      <DashboardLayout
        title="Vote"
        currentUser={this.props.currentUser}
        handleLogout={this.props.handleLogout}
      >
        <div className={classes.root}>
          <Grid container spacing={4}>
            <Grid item lg={12} md={12} xl={9} xs={12}>
              <Portlet {...rest} className={rootClassName}>
                <PortletContent>
                  <Typography className={classes.title} variant="h4">
                    RULES:
                  </Typography>
                  <br />
                  <Typography className={classes.title} variant="body1">
                    <ol>
                      <li>
                        For each correct guess, points equivalent to 1 point per
                        losing player divided by number of winning players will
                        be awarded to each winner.
                      </li>
                      <li>Each incorrect guess will cost you 1 point.</li>
                      <li>
                        In case a match results in a draw, no points will be
                        rewarded or deducted.
                      </li>
                      <li>
                        In case of no votes for any of the team, either winning
                        or losing, no points will be deducted or rewarded.
                      </li>
                    </ol>
                  </Typography>
                </PortletContent>
              </Portlet>
            </Grid>

            <VoteForMatch />
          </Grid>
        </div>
      </DashboardLayout>
    );
  }
}

Vote.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Vote);
