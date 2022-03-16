import React from "react"
import styled from "styled-components";
import LogoImg from "../../images/logo_transparent.png"



const BrandLogoContainer =styled.div`
display: flex;
  align-items: center;
  margin-left: -15px;
`;

const Logoimage= styled.div `
    width: ${({size})=> size? size + "px" : "5em"};
    height: ${({size})=> size? size + "px" : "5em"};
  padding: 0;
  margin-left: ${({ ml }) => `${ml}px`};
  margin-bottom: -20px;
  
  
  img{
    width: 100%;
    height: 100%;
  }
`;

const LogoTitle = styled.h2`
  margin: 0;
  font-size: ${({size})=> size? size + "px" : "27px"};
  color: ${({color})=> color? color : "#fff"};
  font-weight: bold;
  margin-bottom: -20px;
  
`;
export function BrandLogo(prop){

    const {Logosize ,titleSize , logoMargin , color,hideLogo} = prop;
return(
    <BrandLogoContainer>
        {!hideLogo && <Logoimage size ={Logosize} ml ={logoMargin}>
            <img src={LogoImg} alt={"logo"} />
        </Logoimage>}
        <LogoTitle size={titleSize} color={color}>TechDon</LogoTitle>
    </BrandLogoContainer>

)
}