import React, { Component } from "react";
import { Paper } from "../../components";
import { Typography } from "@material-ui/core";
import { withStyles } from "@material-ui/core";
import classNames from "classnames";
import PropTypes from "prop-types";
import styles from "./styles";

class HomepageCard extends Component {
  constructor(props) {
    super(props);
  }
  state = {};
  render() {
    const { classes, className } = this.props;
    const rootClassName = classNames(classes.root, className);

    return (
      <Paper className={rootClassName}>
        <img
          alt={this.props.homepage_card.text}
          className={classes.image}
          src={this.props.homepage_card.img}
        />
        <div className={classes.details}>
          <Typography className={classes.title} variant="h4">
            {this.props.homepage_card.text}
          </Typography>
        </div>
      </Paper>
    );
  }
}

HomepageCard.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(HomepageCard);
