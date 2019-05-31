import React, { Component } from "react";
import "./radio_css.css";

// Externals
import classNames from "classnames";
import PropTypes from "prop-types";
import ReactRadioButtonGroup from "react-radio-button-group";
import { Grid, Typography } from "@material-ui/core";
import Moment from "react-moment";

// Material helpers
import { withStyles } from "@material-ui/core";

// Shared components
import { Portlet, PortletContent } from "../components";

// Component styles
import styles from "./styles";
import { USER_ID, VOTING_BASE_URL } from "../constants";

import { castVote, getUserVotes } from "../util/APIUtils";

class VoteForMatch extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedOption: "option1",
      error: null,
      isLoaded: false,
      matches: [],
      matchVote: []
    };
  }

  handleOptionChange = changeEvent => {
    var json = JSON.parse(changeEvent);

    const { matchVote } = this.state;
    matchVote[json.id] = JSON.stringify(json);
    this.setState({
      matchVote: matchVote
    });

    var vote = {
      userId: json.userId,
      matchId: json.matchId,
      teamId: json.teamId
    };
    castVote(vote)
      .then(result => console.log(result))
      .catch(error => {
        console.log(error);
      });
    // console.log("matchId: " + json.matchId);
    // console.log(changeEvent);
    // var { matchVote } = this.state;
    // matchVote.set(json.matchId, json);
    // this.setState(
    //   {
    //     matchVote: matchVote
    //   },
    //   () => console.log(this.state)
    // );
    // console.log(matchVote);
    // console.log(this.state);
  };

  componentDidMount() {
    console.log("User Id: " + localStorage.getItem(USER_ID));
    // axios
    //   .get("http://mapps.cricbuzz.com/cbzios/series/2697/matches")
    //   .then(function(response) {
    //     console.log(response);
    //   });
    fetch("http://localhost:8181/gullyipl/matches/upcoming")
      .then(res => res.json())
      .then(
        result => {
          this.setState({
            isLoaded: true,
            matches: result
          });
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        error => {
          this.setState({
            isLoaded: true,
            error
          });
        }
      );
    fetch(VOTING_BASE_URL + "/get/" + localStorage.getItem(USER_ID))
      .then(res => res.json())
      .then(
        result => {
          console.log(result);
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        error => {
          console.log(error);
        }
      );
  }

  render() {
    const { classes, className, ...rest } = this.props;
    const { isLoaded, matches } = this.state;

    const rootClassName = classNames(classes.root, className);
    var i = -1;

    // if (error) {
    //   return <div>Error: {error.message}</div>;
    // } else
    if (!isLoaded) {
      return <div>Loading...</div>;
    } else {
      return (
        <div className="container">
          <form>
            {matches.map(match => {
              i++;
              this.state.matchVote.push(match.matchId);
              var value1 = {
                id: i,
                userId: localStorage.getItem(USER_ID),
                matchId: match.matchId,
                teamId: match.team1Info.teamId
              };
              var value2 = {
                id: i,
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
                          value={this.state.matchVote[i]}
                        />
                      </PortletContent>
                    </Portlet>
                  </Grid>
                </div>
              );
            })}
            {/* <Typography variant="body1" className="matchText">
                1st Warm-up game County Ground Bristol, England, 24 May 2019
                10:30 AM GMT
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
              /> */}
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
