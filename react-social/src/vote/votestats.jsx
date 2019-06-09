import React, { Component } from "react";
import { Dashboard as DashboardLayout } from "../layouts";
import {
  Grid,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableHead,
  ExpansionPanel,
  ExpansionPanelSummary,
  ExpansionPanelDetails,
  TableRow
} from "@material-ui/core";
import PropTypes from "prop-types";
import Moment from "react-moment";

// Material helpers
import { withStyles } from "@material-ui/core";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";

import classNames from "classnames";

import { Portlet, PortletContent } from "../components";
import {
  getVotingClosedMatches,
  getVotingStats,
  getActiveUsers
} from "../util/APIUtils";
import ReactRadioButtonGroup from "react-radio-button-group";

// Component styles
const styles = theme => ({
  root: {
    padding: theme.spacing(4)
  },
  item: {
    height: "100%"
  }
});

class VoteStats extends Component {
  constructor(props) {
    super(props);
    this.state = {
      matches: [],
      matchVoteInfoMap: new Map()
    };
  }

  componentDidMount() {
    var matchVoteInfoMap = new Map();
    getVotingClosedMatches()
      .then(res => res.json())
      .then(result => {
        this.setState({
          matches: result
        });
        var matchIds = [];
        result.map(match => {
          matchIds.push(match.matchId);
          matchVoteInfoMap.set(match.matchId, match);
        });
        getVotingStats(matchIds)
          .then(res => res.json())
          .then(votes => {
            getActiveUsers()
              .then(res => res.json())
              .then(users => {
                matchIds.forEach(matchId => {
                  var matchVoteInfo = matchVoteInfoMap.get(matchId);
                  matchVoteInfo.team1votes = votes.filter(
                    vote =>
                      vote.matchId === matchId &&
                      matchVoteInfo.team1Info.teamId === vote.teamId
                  );
                  matchVoteInfo.team2votes = votes.filter(
                    vote =>
                      vote.matchId === matchId &&
                      matchVoteInfo.team2Info.teamId === vote.teamId
                  );
                  matchVoteInfo.team1votes.forEach(team1vote => {
                    team1vote.user = users.filter(
                      user => user.id === team1vote.userId
                    )[0];
                  });
                  matchVoteInfo.team2votes.forEach(team2vote => {
                    team2vote.user = users.filter(
                      user => user.id === team2vote.userId
                    )[0];
                  });
                  matchVoteInfoMap.set(matchId, matchVoteInfo);
                  this.setState({
                    matchVoteInfoMap: matchVoteInfoMap
                  });
                });
              });
          });
      });
  }

  render() {
    const { classes, className, ...rest } = this.props;
    const rootClassName = classNames(classes.root, className);
    const { matches, matchVoteInfoMap } = this.state;

    return (
      <DashboardLayout
        title="Voting Stats"
        currentUser={this.props.currentUser}
        handleLogout={this.props.handleLogout}
      >
        <div className={classes.root}>
          <Grid container spacing={4}>
            {matches.map(match => {
              return (
                <Grid item lg={12} md={12} xl={9} xs={12}>
                  <Portlet {...rest} className={rootClassName}>
                    <PortletContent>
                      <Typography variant="body1" className="matchText">
                        <b>{match.matchDescription}</b> : {match.venueName}
                        {match.venueLocation},
                        <Moment unix>{match.startTime}</Moment>
                      </Typography>
                      <Table>
                        <ReactRadioButtonGroup
                          options={[
                            {
                              value: match.matchId + "1",
                              label: match.team1Info.teamCode,
                              itemClassName: "team1",
                              labelClassName:
                                "drinkcard-cc-checked " +
                                match.team1Info.teamCode
                            },
                            {
                              value: match.matchId,
                              label: match.team2Info.teamCode,
                              itemClassName: "team2",
                              labelClassName:
                                "drinkcard-cc-checked " +
                                match.team2Info.teamCode
                            }
                          ]}
                          name={"rdbTeam" + match.matchId}
                          value={match.matchId}
                        />
                      </Table>
                      <ExpansionPanel>
                        <ExpansionPanelSummary
                          expandIcon={<ExpandMoreIcon />}
                          aria-controls="panel1a-content"
                          id="panel1a-header"
                        >
                          <Typography className={classes.heading}>
                            View Votes
                          </Typography>
                        </ExpansionPanelSummary>
                        <ExpansionPanelDetails>
                          <Table className={classes.table}>
                            <TableHead>
                              <TableRow>
                                <TableCell
                                  width="40%"
                                  align="center"
                                  style={{
                                    textAlign: "center",
                                    verticalAlign: "top"
                                  }}
                                >
                                  {match.team1Info.teamName}
                                </TableCell>
                                <TableCell width="20%" />
                                <TableCell
                                  width="40%"
                                  align="center"
                                  style={{
                                    textAlign: "center",
                                    verticalAlign: "top"
                                  }}
                                >
                                  {match.team2Info.teamName}
                                </TableCell>
                              </TableRow>
                            </TableHead>
                            <TableBody>
                              <TableRow>
                                <TableCell
                                  width="40%"
                                  align="center"
                                  style={{
                                    textAlign: "center",
                                    verticalAlign: "top"
                                  }}
                                >
                                  {matchVoteInfoMap.has(match.matchId) &&
                                  matchVoteInfoMap.get(match.matchId)
                                    .team1votes ? (
                                    matchVoteInfoMap
                                      .get(match.matchId)
                                      .team1votes.map(vote => {
                                        return (
                                          <React.Fragment>
                                            {vote.user.name}
                                            <br />
                                          </React.Fragment>
                                        );
                                      })
                                  ) : (
                                    <div />
                                  )}
                                </TableCell>
                                <TableCell width="20%" />
                                <TableCell
                                  width="40%"
                                  align="center"
                                  style={{
                                    textAlign: "center",
                                    verticalAlign: "top"
                                  }}
                                >
                                  {matchVoteInfoMap.has(match.matchId) &&
                                  matchVoteInfoMap.get(match.matchId)
                                    .team2votes ? (
                                    matchVoteInfoMap
                                      .get(match.matchId)
                                      .team2votes.map(vote => {
                                        return (
                                          <React.Fragment>
                                            {vote.user.name}
                                            <br />
                                          </React.Fragment>
                                        );
                                      })
                                  ) : (
                                    <div />
                                  )}
                                </TableCell>
                              </TableRow>
                            </TableBody>
                          </Table>
                        </ExpansionPanelDetails>
                      </ExpansionPanel>
                    </PortletContent>
                  </Portlet>
                </Grid>
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
