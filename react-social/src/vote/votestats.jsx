import React, { Component } from "react";
import { Dashboard as DashboardLayout } from "../layouts";
import {
  Grid,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from "@material-ui/core";
import PropTypes from "prop-types";
import Moment from "react-moment";
import Img from "react-image";

// Material helpers
import { withStyles } from "@material-ui/core";

import classNames from "classnames";

import { Portlet, PortletContent } from "../components";
import { getVotingClosedMatches } from "../util/APIUtils";

// Component styles
const styles = theme => ({
  root: {
    padding: theme.spacing(4)
  },
  item: {
    height: "100%"
  },
  table: {
    minWidth: 650
  }
});

class VoteStats extends Component {
  constructor(props) {
    super(props);
    this.state = {
      matches: []
    };
  }

  componentDidMount() {
    getVotingClosedMatches()
      .then(res => res.json())
      .then(result => {
        this.setState({
          matches: result
        });
      });
  }

  render() {
    const { classes, className, ...rest } = this.props;
    const rootClassName = classNames(classes.root, className);
    const { matches } = this.state;

    return (
      <DashboardLayout
        title="Vote Stats"
        currentUser={this.props.currentUser}
        handleLogout={this.props.handleLogout}
      >
        <div className={classes.root}>
          <Grid container spacing={4}>
            {matches.map(match => {
              return (
                <div>
                  <Grid item lg={12} md={12} xl={9} xs={12}>
                    <Portlet {...rest} className={rootClassName}>
                      <PortletContent>
                        <Typography variant="body1" className="matchText">
                          <b>{match.matchDescription}</b> : {match.venueName}
                          {match.venueLocation},
                          <Moment unix>{match.startTime}</Moment>
                        </Typography>
                        <Table className={classes.table}>
                          <TableHead>
                            <TableRow>
                              <TableCell>
                                <Img
                                  src={
                                    "../img/teams/" +
                                    match.team1Info.teamCode +
                                    ".png"
                                  }
                                />
                              </TableCell>
                              <TableCell>
                                <Img
                                  src={
                                    "../img/teams/" +
                                    match.team2Info.teamCode +
                                    ".png"
                                  }
                                />
                              </TableCell>
                            </TableRow>
                          </TableHead>
                          {/* <TableBody>
                            {rows.map(row => (
                              <TableRow key={row.name}>
                                <TableCell component="th" scope="row">
                                  {row.name}
                                </TableCell>
                                <TableCell align="right">
                                  {row.calories}
                                </TableCell>
                                <TableCell align="right">{row.fat}</TableCell>
                                <TableCell align="right">{row.carbs}</TableCell>
                                <TableCell align="right">
                                  {row.protein}
                                </TableCell>
                              </TableRow>
                            ))}
                          </TableBody> */}
                        </Table>
                      </PortletContent>
                    </Portlet>
                  </Grid>
                </div>
              );
            })}
          </Grid>
        </div>
      </DashboardLayout>
    );
  }
}

VoteStats.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(VoteStats);
