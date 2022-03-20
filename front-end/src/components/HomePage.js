import React from "react";
import "../App.css";

export default function HomePage(app) {
    return(
        <div id='body'>
            <Header/>
            <Card
                className='section'
                img='./Capture1.PNG'
                title='About the Company'
                description='Currently, there are no simple solutions which facilitate the donation of technological items to students,
                thus making selling or discarding of devices the simplest option, even if the prior owners would have preferred to donate them.
                For my group we aimed to find a solution to this by developing our platform “TechDon”.To minimize friction for our users and aimed to simplify the process
                by facilitating it on a unified platform with a focus on minimal user involvement. Individuals and organizations can quickly list their devices for donation.
                Students can select the devices they need (e.g., laptops, hard drives, monitors, etc.) and will then be informed upon availability. To reduce fraud, necessary
                identification will be performed on donation recipients, while maximizing the efficacy without being overly complex or invasive.'
            />

            <Card
                className='section bg-grey'
                img='./Capture3.PNG'
                title='The Problem'
                description='Low-income students disproportionately excluded from access to technology
                               Barriers to technology based education.'
            />

            <Card
                className='section'
                img='./Capture1.PNG'
                title='Our Solution'
                description='Repurpose decommissioned digital devices for the education of low-income students
                             Platform where organizations and individuals can donate devices
                             Students select what they need and are informed once it is available.'
            />
            <ContactContainer/>
        </div>
    );
}

const Header = () =>{
    return(
        <div className='header'>
            <span className='header-title'>
                TechDon
            </span>
            <br/>
            <span className="header-text">

                Donate Technology to Support Education
            </span>
        </div>
    );
}







const Card = (props) =>{
    return(
        <div className={props.className} >
            <div className="small-div">
                <i className={props.className}></i>
                <img src={props.img} alt=''/>
            </div>

            <div className="big-div">
                <span className='div-title'>
                    {props.title}
                </span>
                <br/>
                <span>
                    {props.description}
                </span>
            </div>
        </div>
    )
}



const ContactContainer = () => {
    return(
        <div className='contact-container bg-grey'>
            <span className="div-title">Contact us</span>
            <div className='contact-form'>
                <div id='sect1'>
                    <span>Contact us and we will get back to you within 24 hours.</span>
                    <span>
                        <i className="fas fa-map-marker-alt"></i>
                        Brunel University London
                    </span>

                    <span>
                        <i className="far fa-envelope"></i>
                        2026156@Brunel.ac.uk
                    </span>
                </div>

                <div id='sect2'>
                    <span>
                        Contact
                    </span>

                    <input type="text" placeholder="email address" className="input-field"/>
                    <textarea name="" id="" cols="30" rows="10" placeholder="comment"></textarea>
                    <button className="contact-btn">Send</button>
                </div>
            </div>
        </div>
    );

}
