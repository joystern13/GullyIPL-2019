import React, { Component } from "react";
import { Dashboard as DashboardLayout } from "../layouts";
import { Grid } from "@material-ui/core";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

// Material helpers
import { withStyles } from "@material-ui/core";

import voteImg from "../img/vote.png";
import momImg from "../img/mom.png";
import rankingImg from "../img/ranking.png";
import HomepageCard from "./homepagecards/homepagecard";
import Portlet from "./portlet/rulesportlet";

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
  state = {
    homepage_cards: [
      { id: 1, text: "Vote Now", img: voteImg, link: "/vote" },
      { id: 2, text: "Guess MoM", img: momImg, link: "/mom" },
      { id: 3, text: "Rankings", img: rankingImg, link: "#" }
    ]
  };

  constructor(props) {
    super(props);
  }

  render() {
    const { classes } = this.props;

    return (
      <DashboardLayout
        title="Homepage"
        currentUser={this.props.currentUser}
        handleLogout={this.props.handleLogout}
      >
        <div className={classes.root}>
          <Grid container spacing={3}>
            <Grid item lg={12} md={12} xl={9} xs={12}>
              <Portlet />
            </Grid>
            {this.state.homepage_cards.map(homepage_card => (
              <Grid item key={homepage_card.id} lg={4} md={6} sm={6} xs={12}>
                <Link to={homepage_card.link}>
                  <HomepageCard homepage_card={homepage_card} />
                </Link>
              </Grid>
            ))}
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
