import React from 'react'
import '../../App.css'
import './service.css'


export default function servicesProvided() {
    return (
        <div className='homePage'>
            <div className='goals-section'>
                <div className='goals'>
                    <div className='goals-title'>The Problem</div>
                    <div className='goals-text'>
                       -  Low-income students disproportionately excluded from access to technology
                       -  Barriers to technology based education
                    </div>
                </div>
                <div className='goals'>
                    <div className='goals-title'>The Solution</div>
                    <div className='goals-text'>
                        Repurpose decommissioned digital devices for the education of low-income students
                        Platform where organizations and individuals can donate devices
                        Students select what they need and are informed once it is available

                    </div>
                </div>
                <div className='goals'>
                    <div className='goals-title'>Education</div>
                    <div className='goals-text'>
                        To make everyone more open to the idea of recycling, we aim to help educate those to help others be educated.

                    </div>
                </div>
            </div>
            <div className='goals-header'> About Us </div>
            <div className='about-section'>
                <div className='about'>
                    <div className='about-title'>Who Are We?</div>
                    <div className='about-text'>
                        Currently, there are no simple solutions which facilitate the donation of technological items to students, thus making selling or discarding
                        of devices the simplest option, even if the prior owners would have preferred to donate them. For my group we aimed to find a solution to this by
                        developing our platform “TechDonate”.  To minimize friction for our users and aimed to simplify the process by facilitating it on a unified platform
                        with a focus on minimal user involvement. Individuals and organizations can quickly list their devices for donation. Students can select the devices they
                        need (e.g., laptops, hard drives, monitors, etc.) and will then be informed upon availability. To reduce fraud, necessary identification will be performed on donation recipients,
                        while maximizing the efficacy without being overly complex or invasive.
                    </div>

                </div>
            </div>
        </div>
    )
}