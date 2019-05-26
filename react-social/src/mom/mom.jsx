import React, { Component } from "react";
import classNames from "classnames";

import { withStyles } from "@material-ui/core";
import { Typography } from "@material-ui/core";
import { Grid } from "@material-ui/core";

import { Dashboard as DashboardLayout } from "../layouts";
import { Portlet, PortletContent } from "../components";

const styles = theme => ({
  root: {
    padding: theme.spacing(4)
  },
  item: {
    height: "100%"
  }
});

class ManOfTheMatch extends Component {
  state = {};

  render() {
    const { classes, className, ...rest } = this.props;
    const rootClassName = classNames(classes.root, className);
    return (
      <DashboardLayout
        title="Guess Man of the Match"
        currentUser={this.props.currentUser}
      >
        <div className={classes.root}>
          <Grid container spacing={3}>
            <Grid item lg={12} md={12} xl={9} xs={12}>
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
          </Grid>
        </div>
      </DashboardLayout>
    );
  }
}

export default withStyles(styles)(ManOfTheMatch);
