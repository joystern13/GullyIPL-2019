import React from "react";
import { css } from "@emotion/core";
import { RingLoader } from "react-spinners";

const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;

export default function LoadingIndicator(props) {
  return (
    <div className="sweet-loading">
      <RingLoader
        css={override}
        sizeUnit={"px"}
        size={150}
        color={"#123abc"}
        loading={props.loading}
      />
    </div>
  );
}
