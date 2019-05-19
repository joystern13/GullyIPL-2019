import React, { Component } from "react";
import { Route, Switch } from "react-router-dom";
import AppHeader from "../common/AppHeader";
import Home from "../home/Home";
import Login from "../user/login/Login";
import Signup from "../user/signup/Signup";
import Profile from "../user/profile/Profile";
import OAuth2RedirectHandler from "../user/oauth2/OAuth2RedirectHandler";
import NotFound from "../common/NotFound";
import LoadingIndicator from "../common/LoadingIndicator";
import { getCurrentUser } from "../util/APIUtils";
import { ACCESS_TOKEN } from "../constants";
import PrivateRoute from "../common/PrivateRoute";
import Alert from "react-s-alert";
import "bootstrap/dist/css/bootstrap.css";
import "react-s-alert/dist/s-alert-default.css";
import "react-s-alert/dist/s-alert-css-effects/slide.css";
import "./App.css";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      authenticated: false,
      currentUser: null,
      loading: false
    };

    this.loadCurrentlyLoggedInUser = this.loadCurrentlyLoggedInUser.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
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
        this.setState({
          loading: false
        });
      });
  }

  handleLogout() {
    localStorage.removeItem(ACCESS_TOKEN);
    this.setState({
      authenticated: false,
      currentUser: null
    });
    Alert.success("You're safely logged out!");
  }

  componentDidMount() {
    this.loadCurrentlyLoggedInUser();
  }

  render() {
    if (this.state.loading) {
      return <LoadingIndicator />;
    }

    return (
      <div className="app">
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
          <div className="container">
            <a className="navbar-brand" href="#">
              GullyCricket
            </a>
            <div className="collapse navbar-collapse" id="navbarResponsive">
              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <a className="nav-link" href="/login">
                    Home
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="/vote">
                    Voting
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="#">
                    Rankings
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="#">
                    Sign Out
                  </a>
                </li>
              </ul>
            </div>
            <button
              className="navbar-toggler"
              type="button"
              data-toggle="collapse"
              data-target="#navbarResponsive"
              aria-controls="navbarResponsive"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon" />
            </button>
          </div>
        </nav>
        <div className="app-top-box">
          <AppHeader
            authenticated={this.state.authenticated}
            onLogout={this.handleLogout}
          />
        </div>
        <div className="app-body">
          <Switch>
            <Route exact path="/" component={Home} />
            <PrivateRoute
              path="/profile"
              authenticated={this.state.authenticated}
              currentUser={this.state.currentUser}
              component={Profile}
            />
            <Route
              path="/login"
              render={props => (
                <Login authenticated={this.state.authenticated} {...props} />
              )}
            />
            <Route
              path="/signup"
              render={props => (
                <Signup authenticated={this.state.authenticated} {...props} />
              )}
            />
            <Route path="/oauth2/redirect" component={OAuth2RedirectHandler} />
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
    );
  }
}

export default App;
