import React, { Component } from "react";
import "./Home.css";
import { Container } from "reactstrap";

class Home extends Component {
  render() {
    return (
      <Container>
        <div className="container">
          <div className="graf-bg-container">
            <div className="graf-layout">
              <div className="graf-circle" />
              <div className="graf-circle" />
              <div className="graf-circle" />
              <div className="graf-circle" />
              <div className="graf-circle" />
              <div className="graf-circle" />
              <div className="graf-circle" />
              <div className="graf-circle" />
              <div className="graf-circle" />
              <div className="graf-circle" />
              <div className="graf-circle" />
            </div>
          </div>
          <h1 className="home-title">Welcome to GullyCricket!</h1>
        </div>
      </Container>
    );
  }
}

export default Home;
