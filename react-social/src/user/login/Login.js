import React, { Component } from "react";
import "./Login.css";
import { GOOGLE_AUTH_URL, ACCESS_TOKEN, USER_ID } from "../../constants";
import { login } from "../../util/APIUtils";
import { Link, Redirect } from "react-router-dom";
import googleLogo from "../../img/google-logo.png";
import Alert from "react-s-alert";
import { Card, CardBody } from "reactstrap";

class Login extends Component {
  componentDidMount() {
    // If the OAuth2 login encounters an error, the user is redirected to the /login page with an error.
    // Here we display the error and then remove the error query parameter from the location.
    if (this.props.location.state && this.props.location.state.error) {
      setTimeout(() => {
        Alert.error(this.props.location.state.error, {
          timeout: 5000
        });
        this.props.history.replace({
          pathname: this.props.location.pathname,
          state: {}
        });
      }, 100);
    }
  }

  render() {
    if (this.props.authenticated) {
      return (
        <Redirect
          to={{
            pathname: "/",
            state: { from: this.props.location }
          }}
        />
      );
    }

    return (
      <React.Fragment>
        <div style={{ height: 25 }} />
        <div
          className="login-container"
          style={{
            display: "flex",
            justifyContent: "center",
            paddingLeft: 10,
            paddingRight: 10
          }}
        >
          <Card className="text-center shadow" style={{ width: 400 }}>
            <CardBody>
              <h1 className="login-title">Login to GullyCricket</h1>
              <SocialLogin />
              <div className="or-separator">
                <span className="or-text">OR</span>
              </div>
              <LoginForm {...this.props} />
              <span className="signup-link">
                New user? <Link to="/signup">Sign up!</Link>
              </span>
            </CardBody>
          </Card>
        </div>
      </React.Fragment>
    );
  }
}

class SocialLogin extends Component {
  render() {
    return (
      <Card className="shadow-sm">
        <a className="btn social-btn google" href={GOOGLE_AUTH_URL}>
          <img src={googleLogo} alt="Google" /> Log in with Google
        </a>
      </Card>
    );
  }
}

class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: ""
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleInputChange(event) {
    const target = event.target;
    const inputName = target.name;
    const inputValue = target.value;

    this.setState({
      [inputName]: inputValue
    });
  }

  handleSubmit(event) {
    event.preventDefault();

    const loginRequest = Object.assign({}, this.state);

    login(loginRequest)
      .then(response => {
        localStorage.setItem(ACCESS_TOKEN, response.accessToken);
        localStorage.setItem(USER_ID, response.id);
        this.props.loadCurrentlyLoggedInUser();
        this.props.history.push({
          pathname: "/home",
          state: { authenticated: true }
        });
      })
      .catch(error => {
        Alert.error(
          (error && error.message) ||
            "Oops! Something went wrong. Please try again!"
        );
      });
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <div className="form-item">
          <input
            type="email"
            name="email"
            className="form-control"
            placeholder="Email"
            value={this.state.email}
            onChange={this.handleInputChange}
            required
          />
        </div>
        <div className="form-item">
          <input
            type="password"
            name="password"
            className="form-control"
            placeholder="Password"
            value={this.state.password}
            onChange={this.handleInputChange}
            required
          />
        </div>
        <div className="form-item">
          <button type="submit" className="btn btn-block btn-primary">
            Login
          </button>
        </div>
      </form>
    );
  }
}

export default Login;
