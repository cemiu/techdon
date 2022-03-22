/* eslint-disable no-unused-vars */
import React from "react";
import styled from "styled-components";
import {
    BrandLogo
} from "../Logo";

//
const FooterContainer = styled.div`
  width: 100%;
  min-height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 5em 3em;
  padding-bottom: 0;
  border-top: 0.6px solid rgb(0, 0, 0, 0.3);

`;

const TopContainer = styled.div`
  width: 100%;
  display: flex;
  margin-bottom: 1em;
  border-top: 0.6px solid rgb(0, 0, 0, 0.3);
  padding-top: 18px;
  align-items: center;
`;

const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  &:not(:last-of-type) {
    margin-right: 3%;
  }
`;

const BottomContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
  border-top: 0.6px solid rgb(0, 0, 0, 0.3);
  padding: 0 10px;

`;

const RightBottomContainer = styled.div`
  display: flex;
`;

const LeftBottomContainer = styled.div`
  display: flex;
`;

const Title = styled.h2`
  margin: 0;
  margin-bottom: 13px;
  color: #000;
  font-weight: 600;
  font-size: 20px;
`;

const Link = styled.a`
  text-decoration: none;
  color: #6f6f6f;
  font-weight: 500;
  font-size: 15px;
  cursor: pointer;
  &:not(:last-of-type) {
    margin-bottom: 8px;
  }
`;



const SocialIcon = styled.div`
  color: #8a8a8a;
  font-size: 20px;
  cursor: pointer;
  transition: background-color, 200ms ease-in-out;
  &:not(:last-of-type) {
    margin-right: 10px;
  }
  &:hover {
    color: #777777;
  }

`;

const PrivacyText = styled.h6`
  color: #a3a3a3;
  font-size: 11px;
  margin: 0;
  margin-left: 10px;
  display: flex;
  margin-top: 15px;
  align-items: center;

`;


function FontAwsomeIcon(props) {
    return null;
}


function FontAwesomeIcon(props) {
    return null;
}

export function Footer (props){
    return(
        <FooterContainer>
            <TopContainer>
                <ContentContainer>
                    <Title>Join Today </Title>
                    <Link>Create Account As A Downer</Link>
                    <Link>Create Account As A Student</Link>
                </ContentContainer>
                <ContentContainer>
                    <Title>What we have to offer</Title>
                    <Link>Who are we </Link>
                    <Link>Donate</Link>
                    <a href='/FAQ' className='FAQPAGE'>FAQ</a>
                </ContentContainer>
            </TopContainer>
            <BottomContainer>
                <LeftBottomContainer>
                <BrandLogo hideLogo Logosize={200} titleSize={0} color={"#8A8A8A"}/>

                    <PrivacyText> &#169; All Rights Reserved. 2022</PrivacyText>
                </LeftBottomContainer>
                <RightBottomContainer>


                </RightBottomContainer>
            </BottomContainer>

        </FooterContainer>
    )
}

