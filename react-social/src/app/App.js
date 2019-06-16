import React, { Component } from "react";
import { Route, Switch } from "react-router-dom";
import Login from "../user/login/Login";
import Signup from "../user/signup/Signup";
import Profile from "../user/profile/Profile";
import OAuth2RedirectHandler from "../user/oauth2/OAuth2RedirectHandler";
import NotFound from "../common/NotFound";
import LoadingIndicator from "../common/LoadingIndicator";
import { getCurrentUser, updateMatches } from "../util/APIUtils";
import { ACCESS_TOKEN, USER_ID } from "../constants";
import PrivateRoute from "../common/PrivateRoute";
import Alert from "react-s-alert";
import "bootstrap/dist/css/bootstrap.css";
import "react-s-alert/dist/s-alert-default.css";
import "react-s-alert/dist/s-alert-css-effects/slide.css";
import "./App.css";
// Material helpers
import { ThemeProvider } from "@material-ui/styles";

// Theme
import theme from "../theme";

import Homepage from "../homepage/homepage";
import vote from "../vote/vote";
import votestats from "../vote/votestats";
import ManOfTheMatch from "../mom/mom";
import rankings from "../rankings/index";

class App extends Component {
  constructor(props) {
    console.log("in props constructor");
    super(props);
    this.state = {
      authenticated: false,
      currentUser: null,
      loading: true,
      collapsed: true
    };

    this.loadCurrentlyLoggedInUser = this.loadCurrentlyLoggedInUser.bind(this);
    //this.handleLogout = this.handleLogout.bind(this);

    this.toggleNavbar = this.toggleNavbar.bind(this);
  }

  toggleNavbar() {
    this.setState({
      collapsed: !this.state.collapsed
    });
  }

  loadCurrentlyLoggedInUser() {
    this.setState({
      loading: true
    });

    getCurrentUser()
      .then(response => {
        this.setState({
          currentUser: response,
          authenticated: true,
          loading: false
        });
      })
      .catch(error => {
        console.log("Removing access token");
        localStorage.removeItem(ACCESS_TOKEN);
        localStorage.removeItem(USER_ID);
        this.setState({
          loading: false
        });
      });

    console.log(this.state.currentUser);
  }

  updateAllMatches() {
    this.setState({
      loading: true
    });

    updateMatches()
      .then(response => {
        this.setState({
          loading: false
        });
        console.log("All Matches Updated");
      })
      .catch(error => {
        this.setState({
          loading: false
        });
        console.log("Error in updating matches");
      });
  }

  handleLogout = () => {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(USER_ID);
    this.setState({
      authenticated: false,
      currentUser: null
    });
    Alert.success("You're safely logged out!");
  };

  componentDidMount() {
    console.log("in componentDidMount");
    this.loadCurrentlyLoggedInUser();
    this.updateAllMatches();
  }

  render() {
    if (this.state.loading) {
      return <LoadingIndicator />;
    }

    if (localStorage.getItem(ACCESS_TOKEN) && !this.state.authenticated) {
      this.loadCurrentlyLoggedInUser();
    }

    console.log("App.js", this.state);
    console.log("App.js", this.props);

    return (
      <ThemeProvider theme={theme}>
        <div
          className="app"
          style={{ background: "linear-gradient(to right, #000428, #006e92)" }}
        >
          <div className="app-body">
            <Switch>
              <PrivateRoute
                exact
                path="/"
                authenticated={this.state.authenticated}
                currentUser={this.state.currentUser}
                handleLogout={this.handleLogout}
                component={Homepage}
              />
              <PrivateRoute
                path="/profile"
                authenticated={this.state.authenticated}
                currentUser={this.state.currentUser}
                component={Profile}
              />
              <PrivateRoute
                path="/vote"
                authenticated={this.state.authenticated}
                currentUser={this.state.currentUser}
                handleLogout={this.handleLogout}
                component={vote}
              />
              <PrivateRoute
                path="/votestats"
                authenticated={this.state.authenticated}
                currentUser={this.state.currentUser}
                handleLogout={this.handleLogout}
                component={votestats}
              />
              <PrivateRoute
                path="/home"
                authenticated={this.state.authenticated}
                currentUser={this.state.currentUser}
                handleLogout={this.handleLogout}
                component={Homepage}
              />
              <PrivateRoute
                path="/rankings"
                authenticated={this.state.authenticated}
                currentUser={this.state.currentUser}
                handleLogout={this.handleLogout}
                component={rankings}
              />
              <PrivateRoute
                path="/mom"
                authenticated={this.state.authenticated}
                currentUser={this.state.currentUser}
                handleLogout={this.handleLogout}
                component={ManOfTheMatch}
              />
              <Route
                path="/login"
                render={props => (
                  <Login
                    authenticated={this.state.authenticated}
                    loadCurrentlyLoggedInUser={this.loadCurrentlyLoggedInUser}
                    {...props}
                  />
                )}
              />
              <Route
                path="/signup"
                render={props => (
                  <Signup authenticated={this.state.authenticated} {...props} />
                )}
              />
              <Route
                path="/oauth2/redirect"
                component={OAuth2RedirectHandler}
                authenticated={this.state.authenticated}
                currentUser={this.state.currentUser}
                handleLogout={this.handleLogout}
              />
              <Route component={NotFound} />
            </Switch>
          </div>
          <Alert
            stack={{ limit: 3 }}
            timeout={3000}
            position="top-right"
            effect="slide"
            offset={65}
          />
        </div>
      </ThemeProvider>
    );
  }
}

export default App;
