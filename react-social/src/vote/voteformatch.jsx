import React, { Component } from "react";
import "./radio_css.css";
import Alert from "react-s-alert";

// Externals
import classNames from "classnames";
import PropTypes from "prop-types";
import ReactRadioButtonGroup from "react-radio-button-group";
import { Grid, Typography } from "@material-ui/core";
import Moment from "react-moment";
import LoadingIndicator from "../common/LoadingIndicator";

// Material helpers
import { withStyles } from "@material-ui/core";

// Shared components
import { Portlet, PortletContent } from "../components";

// Component styles
import styles from "./styles";
import { USER_ID, MATCH_BASE_URL } from "../constants";

import { castVote, getUserVotes } from "../util/APIUtils";

class VoteForMatch extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      selectedOption: "option1",
      error: null,
      isLoaded: false,
      matches: [],
      matchVoteMap: new Map()
    };
  }

  handleOptionChange = changeEvent => {
    var json = JSON.parse(changeEvent);

    const { matchVoteMap } = this.state;
    matchVoteMap.set(json.matchId, changeEvent);
    this.setState({
      matchVoteMap: matchVoteMap
    });

    castVote(changeEvent)
      .then(result => {
        if (result.status === 200) Alert.success("Voting Successful");
        else if (result.status === 403) {
          Alert.error("Voting Gates Closed");
        }
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    var votesMap = new Map();
    getUserVotes()
      .then(res => res.json())
      .then(result => {
        result.map(vote => {
          votesMap.set(vote.matchId, vote.teamId);
        });

        fetch(MATCH_BASE_URL + "/upcoming")
          .then(res => res.json())
          .then(
            result => {
              const { matchVoteMap } = this.state;
              result.map(match => {
                matchVoteMap.set(
                  match.matchId,
                  JSON.stringify({
                    userId: localStorage.getItem(USER_ID),
                    matchId: match.matchId,
                    teamId: votesMap.get(match.matchId)
                  })
                );
              });
              this.setState({
                isLoaded: true,
                matches: result,
                matchVoteMap: matchVoteMap
              });
            },

            error => {
              this.setState({
                isLoaded: true,
                error
              });
            }
          );
      });
  }

  render() {
    const { classes, className, ...rest } = this.props;
    const { isLoaded, matches } = this.state;

    const rootClassName = classNames(classes.root, className);

    if (!isLoaded) {
      return (
        <div className="container">
          <LoadingIndicator />
        </div>
      );
    } else {
      return (
        <div className="container">
          <form>
            {matches.map(match => {
              var value1 = {
                userId: localStorage.getItem(USER_ID),
                matchId: match.matchId,
                teamId: match.team1Info.teamId
              };
              var value2 = {
                userId: localStorage.getItem(USER_ID),
                matchId: match.matchId,
                teamId: match.team2Info.teamId
              };
              return (
                <div className="cc-selector">
                  <Grid item lg={12} md={12} xl={9} xs={12}>
                    <Portlet {...rest} className={rootClassName}>
                      <PortletContent>
                        <Typography variant="body1" className="matchText">
                          <b>{match.matchDescription}</b> : {match.venueName}
                          {match.venueLocation},
                          <Moment unix>{match.startTime}</Moment>
                        </Typography>
                        <ReactRadioButtonGroup
                          options={[
                            {
                              value: JSON.stringify(value1),
                              label: match.team1Info.teamCode,
                              itemClassName: "team1",
                              labelClassName:
                                "drinkcard-cc " + match.team1Info.teamCode
                            },
                            {
                              value: JSON.stringify(value2),
                              label: match.team2Info.teamCode,
                              itemClassName: "team2",
                              labelClassName:
                                "drinkcard-cc " + match.team2Info.teamCode
                            }
                          ]}
                          name={"rdbTeam" + match.matchId}
                          onChange={this.handleOptionChange}
                          value={this.state.matchVoteMap.get(match.matchId)}
                        />
                      </PortletContent>
                    </Portlet>
                  </Grid>
                </div>
              );
            })}
          </form>
        </div>
      );
    }
  }
}

VoteForMatch.propTypes = {
  className: PropTypes.string,
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(VoteForMatch);
