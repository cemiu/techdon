/* eslint-disable no-unused-vars */
import React from "react";
import styled from "styled-components";
import {
    Marginer
} from "../Marginer";
import {
    BrandLogo
} from "../Logo";
import studentOnLaptop from "../../images/grad.jpg"
import {
    Button
} from "../Button-Front-page";
import AuthService from "../../services/auth.service";


const SpecialistAdContainer = styled.div`
  width: 100%;
  height: 600px;
  display: flex;
  background: url(${studentOnLaptop}) 0px -100px;
  background-size: cover;
  align-items: center;
  justify-content: center;
`;

const ContentContainer = styled.div`
  width: 100%;
  height: 100%;
  background-color: rgba(57, 122, 122, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
`;

const SloganContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: flex-start;
  margin-right: 5em;

`;

const Slogan = styled.h2`
  margin: 0;
  font-size: 25px;
  color: #fff;
  font-weight: 700;
  line-height: 1.3;
  text-align: start;
`;

const StandoutImage = styled.div`
  width: 35em;
  height: 29em;
  img {
    width: 50%;
    height: 50%;
  }
`;
const Link = styled.a`
  text-decoration: none;
  color: #ffffff;
  cursor: pointer;
  text-underline: none;

`;

export function DonatePage(props) {


    return (
        <SpecialistAdContainer>
            <ContentContainer>
                <SloganContainer>
                    <BrandLogo Logosize={150} titleSize={0} logoMargin={0}/>
                    <Marginer direction="vertical" margin="0.1em"/>
                   <SloganContainer>
                        <Slogan> HELP REUSE  </Slogan>
                        <Slogan>RECYCLE</Slogan>
                        <Slogan>EDUCATION</Slogan>
                    </SloganContainer>
                    <Marginer direction="vertical" margin="0.1em" />
                  {
                    !AuthService.getUser() && (
                      <Button >
                        <Link  href="/Register">Sign up</Link >
                      </Button>
                    )
                  }
               
                </SloganContainer>
                <StandoutImage>
                </StandoutImage>
                <StandoutImage>
                </StandoutImage>
            </ContentContainer>
        </SpecialistAdContainer>
    );
}