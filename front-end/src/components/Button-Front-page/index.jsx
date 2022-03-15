import React from "react";
import styled from "styled-components";

const ButtonWrapper = styled.button`
  width: fit-content;
  margin-top: 10px;
  border: none;
  outline: none;
  color: #fff;
  padding: 6px 2em;
  font-size: ${({size}) => (size ? size + "px" : "18px")};
  font-weight: 600;
  border-radius: 3px;
  background-color: rgb(7, 91, 91);
  cursor: pointer;
  transition: all 300ms ease-in-out;

  &:hover {
    background-color: #2fbb97;
  }

  &:focus {
    outline: none;
  }
`;

export function Button(props) {
    const { size } = props;

    return (
        <ButtonWrapper size={size} >
            {props.children}
        </ButtonWrapper>
    );
}