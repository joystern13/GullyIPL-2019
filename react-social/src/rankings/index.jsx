import React, { Component } from "react";

// Externals
import PropTypes from "prop-types";

// Material helpers
import { withStyles } from "@material-ui/core";

// Material components
import { CircularProgress, Typography } from "@material-ui/core";

// Shared layouts
import { Dashboard as DashboardLayout } from "../layouts";

// Shared services
//import { getUsers } from "services/user";

// Custom components
import { UsersTable } from "./components";

// Component styles
import styles from "./style";
import { getRankings, getActiveUsers } from "../util/APIUtils";

class Rankings extends Component {
  signal = true;

  state = {
    isLoading: false,
    limit: 10,
    users: [],
    selectedUsers: [],
    error: null
  };

  async getUsers() {
    try {
      this.setState({ isLoading: true });

      const { limit } = this.state;

      getRankings()
        .then(res => res.json())
        .then(res => {
          var users = res;

          getActiveUsers()
            .then(res => res.json())
            .then(activeUsers => {
              users.map(user => {
                user.info = activeUsers.filter(
                  activeUser => activeUser.id === user.userid
                )[0];
              });
              console.log(users);
              this.setState({
                isLoading: false,
                users
              });
            });
        });
    } catch (error) {
      if (this.signal) {
        this.setState({
          isLoading: false,
          error
        });
      }
    }
  }

  componentDidMount() {
    this.signal = true;
    this.getUsers();
  }

  componentWillUnmount() {
    this.signal = false;
  }

  handleSelect = selectedUsers => {
    this.setState({ selectedUsers });
  };

  renderUsers() {
    const { classes } = this.props;
    const { isLoading, users, error } = this.state;

    if (isLoading) {
      return (
        <div className={classes.progressWrapper}>
          <CircularProgress />
        </div>
      );
    }

    if (error) {
      return <Typography variant="h6">{error}</Typography>;
    }

    if (users.length === 0) {
      return <Typography variant="h6">There are no users</Typography>;
    }

    return (
      <UsersTable
        //
        onSelect={this.handleSelect}
        users={users}
      />
    );
  }

  render() {
    const { classes } = this.props;
    const { selectedUsers } = this.state;

    return (
      <DashboardLayout
        title="Rankings"
        authenticated={this.props.authenticated}
        currentUser={this.props.currentUser}
        handleLogout={this.props.handleLogout}
      >
        <div className={classes.root}>
          <div className={classes.content}>{this.renderUsers()}</div>
        </div>
      </DashboardLayout>
    );
  }
}

Rankings.propTypes = {
  className: PropTypes.string,
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Rankings);
