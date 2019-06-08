import React, { Component } from "react";
import { Dashboard as DashboardLayout } from "../layouts";
import {
  Grid,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from "@material-ui/core";
import ReactTable from "react-table";
import "react-table/react-table.css";
import PropTypes from "prop-types";

import { getRankings } from "../util/APIUtils";

class Rankings extends Component {
  constructor() {
    super();
    this.state = {
      data: getRankings()
    };
  }
  render() {
    const { data } = this.state;
    return (
      <div>
        <ReactTable
          data={data}
          columns={[
            {
              Header: "Name",
              columns: [
                {
                  Header: "First Name",
                  accessor: "firstName"
                }
              ]
            },
            {
              Header: "Info",
              columns: [
                {
                  Header: "Profile Progress",
                  accessor: "progress",
                  Cell: row => (
                    <div
                      style={{
                        width: "100%",
                        height: "100%",
                        backgroundColor: "#dadada",
                        borderRadius: "2px"
                      }}
                    >
                      <div
                        style={{
                          width: `${row.value}%`,
                          height: "100%",
                          backgroundColor:
                            row.value > 66
                              ? "#85cc00"
                              : row.value > 33
                              ? "#ffbf00"
                              : "#ff2e00",
                          borderRadius: "2px",
                          transition: "all .2s ease-out"
                        }}
                      />
                    </div>
                  )
                },
                {
                  Header: "Status",
                  accessor: "status",
                  Cell: row => (
                    <span>
                      <span
                        style={{
                          color:
                            row.value === "relationship"
                              ? "#ff2e00"
                              : row.value === "complicated"
                              ? "#ffbf00"
                              : "#57d500",
                          transition: "all .3s ease"
                        }}
                      >
                        &#x25cf;
                      </span>{" "}
                      {row.value === "relationship"
                        ? "In a relationship"
                        : row.value === "complicated"
                        ? `It's complicated`
                        : "Single"}
                    </span>
                  )
                }
              ]
            }
          ]}
          defaultPageSize={10}
          className="-striped -highlight"
        />
      </div>
    );
  }
}

Rankings.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Rankings);