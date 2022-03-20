import React, {useEffect} from "react";
import styled from "styled-components";
import {InnerPageContainer, PageContainer} from "../../components/pageCointainer";
import {TopSection} from "./topSection";
import {DonatePage} from "../../components/DonatePage";
import ServiceCard from "../../components/servicesProvided/index";
import AuthService from "../../services/auth.service";
const Title = styled.h1`
  font-weight: 500;
  color: #2fbb97;
`;

//here is the main page for better organization - Thoybur
export function HomePage(props) {

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (<PageContainer>
      <TopSection>
      </TopSection>
      <InnerPageContainer>
        <Title> What services we offer</Title>
        <ServiceCard/>
        <DonatePage/>
      </InnerPageContainer>
    </PageContainer>)
}
