/* eslint-disable jsx-a11y/alt-text */
import React from "react";
import styled from "styled-components";
//import TopSectionBackground from "../../im"
import TopSectionBackgroundImg from "../../images/logo_transparent.png"
import student from "../../images/Teacher_Isometric.png"
import {BrandLogo} from "../../components/Logo";
import {Marginer} from "../../components/Marginer";
import {Button} from "../../components/Button-Front-page";
import Donor from "../Donor";


// Here we are making the top sectiong img background - Thoybur
const TopSectionCointainer = styled.div`
  width: 100%;
  height: 230px;
  background: url(${TopSectionBackgroundImg}) 0px -40px;
  background-size: cover;
  margin-bottom: 20px;
`;

//Here we are making the Background filter - Thoybur
const BackgroundFilter = styled.div`
  width: 100%;
  height: 100%;
  background-color: rgba(57, 122, 122, 0.7);
  display: flex;
  flex-direction: column;
`;
//Here we are making the cointainer for the inner cointainer
const TopSectionInnerContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
`;


// Here we are shaping the img which is in our top secrion - Thoybur
const StandoutImg = styled.div `
  width: 20em;
  height: 18em;
  margin-left: -155px;
  //here we are putting the img height
  img {
    width: 100%;
    height: 100%;
  }
`;
const Link = styled.a`
  text-decoration: none;
  color: #ffffff;
  cursor: pointer;
  text-underline: none;

`;
const LogoCenter = styled.div `
  display: flex;
  flex-direction: column;
`;

const Slogan = styled.div`
  margin: 0;
  line-height: 0;
  color: whitesmoke;
  text-align: left;
  font-size: 35px;
  font-weight: initial;
`;


export function Banner (props){

    return (

        <TopSectionCointainer>
            <BackgroundFilter>

                <TopSectionInnerContainer>
                    <LogoCenter>
                        <BrandLogo Logosize={200} titleSize={60} logoMargin={-2} />
                        <Marginer direction={"vertical"} margin={0}/>
                        <Slogan>Registration </Slogan>
                        <Marginer direction={"vertical"} margin={15}/>
                    </LogoCenter>
                </TopSectionInnerContainer>
            </BackgroundFilter>
        </TopSectionCointainer>
    )
}
export default Banner;