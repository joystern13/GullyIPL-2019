import React, { Component } from "react";
import { ACCESS_TOKEN, USER_ID } from "../../constants";
import { Redirect } from "react-router-dom";

class OAuth2RedirectHandler extends Component {
  getUrlParameter(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)");

    var results = regex.exec(this.props.location.search);
    return results === null
      ? ""
      : decodeURIComponent(results[1].replace(/\+/g, " "));
  }

  render() {
    const token = this.getUrlParameter("token");
    const userId = this.getUrlParameter("userId");
    const error = this.getUrlParameter("error");
    console.log("token", token);
    console.log("userId", userId);
    if (token) {
      localStorage.setItem(ACCESS_TOKEN, token);
      localStorage.setItem(USER_ID, userId);
      return (
        <Redirect
          to={{
            pathname: "/home",
            state: {
              from: this.props.location,
              loading: true
            }
          }}
        />
      );
    } else {
      return (
        <Redirect
          to={{
            pathname: "/login",
            state: {
              from: this.props.location,
              error: error
            }
          }}
        />
      );
    }
  }
}

export default OAuth2RedirectHandler;
