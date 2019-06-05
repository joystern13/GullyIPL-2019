import React, { Component } from "react";
import { Link, NavLink } from "react-router-dom";

// Externals
import classNames from "classnames";
import PropTypes from "prop-types";

// Material helpers
import { withStyles } from "@material-ui/core";

// Material components
import {
  Avatar,
  Divider,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  ListSubheader,
  Typography
} from "@material-ui/core";

// Material icons
import {
  InfoOutlined as InfoIcon,
  HomeRounded as HomeIcon,
  HowToVoteRounded as VoteIcon,
  StarsRounded as StarsIcon,
  EqualizerRounded as RankingIcon
} from "@material-ui/icons";

// Component styles
import styles from "./styles";

class Sidebar extends Component {
  constructor(props) {
    super(props);
    console.log("Sidebar", props);
  }

  render() {
    const { classes, className } = this.props;

    const rootClassName = classNames(classes.root, className);

    return (
      <nav className={rootClassName}>
        <div className={classes.profile}>
          <Link to="/account">
            <Avatar
              alt={this.props.currentUser.name}
              className={classes.avatar}
              src={this.props.currentUser.imageUrl}
            />
          </Link>
          <Typography className={classes.nameText} variant="h6">
            {this.props.currentUser.name}
          </Typography>
        </div>
        <Divider className={classes.profileDivider} />
        <List component="div" disablePadding>
          <ListItem
            activeClassName={classes.activeListItem}
            className={classes.listItem}
            component={NavLink}
            to="/home"
            style={{ textDecoration: "none" }}
          >
            <ListItemIcon className={classes.listItemIcon}>
              <HomeIcon />
            </ListItemIcon>
            <ListItemText
              classes={{ primary: classes.listItemText }}
              primary="Home"
            />
          </ListItem>
          <ListItem
            activeClassName={classes.activeListItem}
            className={classes.listItem}
            component={NavLink}
            to="/vote"
            style={{ textDecoration: "none" }}
          >
            <ListItemIcon className={classes.listItemIcon}>
              <VoteIcon />
            </ListItemIcon>
            <ListItemText
              classes={{ primary: classes.listItemText }}
              primary="Vote Now"
            />
          </ListItem>
          <ListItem
            activeClassName={classes.activeListItem}
            className={classes.listItem}
            component={NavLink}
            to="/votestats"
            style={{ textDecoration: "none" }}
          >
            <ListItemIcon className={classes.listItemIcon}>
              <VoteIcon />
            </ListItemIcon>
            <ListItemText
              classes={{ primary: classes.listItemText }}
              primary="Vote Stats"
            />
          </ListItem>
          <ListItem
            activeClassName={classes.activeListItem}
            className={classes.listItem}
            component={NavLink}
            to="/mom"
            style={{ textDecoration: "none" }}
          >
            <ListItemIcon className={classes.listItemIcon}>
              <StarsIcon />
            </ListItemIcon>
            <ListItemText
              classes={{ primary: classes.listItemText }}
              primary="Guess MoM"
            />
          </ListItem>
          <ListItem
            activeClassName={classes.activeListItem}
            className={classes.listItem}
            component={NavLink}
            to="/rankings"
            style={{ textDecoration: "none" }}
          >
            <ListItemIcon className={classes.listItemIcon}>
              <RankingIcon />
            </ListItemIcon>
            <ListItemText
              classes={{ primary: classes.listItemText }}
              primary="Rankings"
            />
          </ListItem>
        </List>
        {/*<Divider className={classes.listDivider} />
        <List
          component="div"
          disablePadding
          subheader={
            <ListSubheader className={classes.listSubheader}>
              Support
            </ListSubheader>
          }
        >
          <ListItem
            className={classes.listItem}
            component="a"
            href="https://devias.io/contact-us"
            target="_blank"
            style={{ textDecoration: "none" }}
          >
            <ListItemIcon className={classes.listItemIcon}>
              <InfoIcon />
            </ListItemIcon>
            <ListItemText
              classes={{ primary: classes.listItemText }}
              primary="Customer support"
            />
          </ListItem>
        </List>*/}
      </nav>
    );
  }
}

Sidebar.propTypes = {
  className: PropTypes.string,
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Sidebar);
