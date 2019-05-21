import React, { Component } from "react";
import "./Signup.css";
import { Link, Redirect } from "react-router-dom";
import { GOOGLE_AUTH_URL } from "../../constants";
import { signup } from "../../util/APIUtils";
import googleLogo from "../../img/google-logo.png";
import Alert from "react-s-alert";
import { Card, CardBody } from "reactstrap";

class Signup extends Component {
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
      <div
        className="signup-container"
        style={{
          display: "flex",
          justifyContent: "center",
          paddingLeft: 10,
          paddingRight: 10
        }}
      >
        <Card className="text-center shadow" style={{ width: 400 }}>
          <CardBody>
            <h1 className="login-title">Sign up to GullyCricket</h1>
            <SocialSignup />
            <div className="or-separator">
              <span className="or-text">OR</span>
            </div>
            <SignupForm {...this.props} />
            <span className="signup-link">
              New user? <Link to="/signup">Sign up!</Link>
            </span>
          </CardBody>
        </Card>
      </div>
    );
  }
}

class SocialSignup extends Component {
  render() {
    return (
      <Card className="shadow-sm">
        <a className="btn social-btn google" href={GOOGLE_AUTH_URL}>
          <img src={googleLogo} alt="Google" /> Sign up with Google
        </a>
      </Card>
    );
  }
}

class SignupForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
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

    const signUpRequest = Object.assign({}, this.state);

    signup(signUpRequest)
      .then(response => {
        Alert.success(
          "You're successfully registered. Please login to continue!"
        );
        this.props.history.push("/login");
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
            type="text"
            name="name"
            className="form-control"
            placeholder="Name"
            value={this.state.name}
            onChange={this.handleInputChange}
            required
          />
        </div>
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
            Sign Up
          </button>
        </div>
      </form>
    );
  }
}

export default Signup;
