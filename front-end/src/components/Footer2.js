import React from 'react';
import {makeStyles} from '@material-ui/core'
import { useNavigate } from 'react-router-dom';

const useStyles = makeStyles({

    footerStyles: {
        color: 'white',
        background: '#2fbb97',
    },
    linkContainer: {
        display: 'flex',

    },
    links: {
        display: 'flex',
        flexDirection: 'column',
        padding: '2%',
        paddingTop: '1%'
    },
    aStyles: {
        color:'white',
        textDecoration: 'none',
        '&:hover': {
            textDecoration: 'underline'
        },
        lineHeight: '180%'
    },
    bottom: {
        textAlign: 'center',
        padding: '1%'
    }
})

export default function Footer() {

    const classes = useStyles();

    return (
        <footer className={classes.footerStyles}>
            <div className={classes.linkContainer}>
                <div className={classes.links}>
                    <h3>Company</h3>
                    <a href='#' className={classes.aStyles}>About Us</a>
                    <a href='#' className={classes.aStyles}>Donate</a>
                </div>
                <div className={classes.links}>
                    <h4>Support</h4>
                    <a href='#' className={classes.aStyles}>FAQs</a>
                    <a href='#' className={classes.aStyles}>Email Us</a>
                </div>
                <div className={classes.links}>
                    <h4>Partnerships</h4>
                    <a href='/register' className={classes.aStyles}>Sign Up</a>
                    <a href='/Login' className={classes.aStyles}>Login</a>
                </div>
            </div>
            <h5 className={classes.bottom}>Brunel University London Group 50</h5>
        </footer>
    );
}
