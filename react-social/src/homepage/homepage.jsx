import React, { Component } from "react";
import { Dashboard as DashboardLayout } from "../layouts";
import { Grid, Typography } from "@material-ui/core";
import PropTypes from "prop-types";

// Material helpers
import { withStyles } from "@material-ui/core";

import classNames from "classnames";

import { Portlet, PortletContent, Paper } from "../components";

// Component styles
const styles = theme => ({
  root: {
    padding: theme.spacing(4)
  },
  item: {
    height: "100%"
  }
});

class Homepage extends Component {
  state = {};

  render() {
    const { classes, className, ...rest } = this.props;
    const rootClassName = classNames(classes.root, className);

    return (
      <DashboardLayout title="Homepage">
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
            <Grid item lg={3} sm={6} xl={3} xs={12}>
              <Paper {...rest} className={classes.item}>
                <div className={classes.content}>
                  <div className={classes.details}>
                    <Typography className={classes.title} variant="body2">
                      BUDGET
                    </Typography>
                  </div>
                </div>
              </Paper>
            </Grid>
            <Grid item lg={9} md={12} xl={9} xs={12}>
              <Paper {...rest} className={classes.item}>
                <div className={classes.content}>
                  <div className={classes.details}>
                    <Typography className={classes.title} variant="body2">
                      BUDGET
                    </Typography>
                  </div>
                </div>
              </Paper>
            </Grid>
          </Grid>
        </div>
      </DashboardLayout>
    );
  }
}

Homepage.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Homepage);
