import React from "react";
import '../App.css';
import {
    Card,
    Button,
    CardContent,
    CardActions,
    Typography
} from "@material-ui/core";

import ComputerIcon from '@mui/icons-material/Computer';
import DevicesIcon from '@mui/icons-material/Devices';
import UsbIcon from '@mui/icons-material/Usb';

import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
    root: {
        height: "16rem",
        width: "12rem"
    },
    icon: {
        "& svg": {
            width: "2em",
            height: "2em",
            fontSize: "2rem"
        },
        paddingBottom: "1rem",
        borderBottom: "1px solid #252525"
    },
    productName: {
        marginTop: "1rem"
    },
    actions: {
        justifyContent: "center",
        padding: "1rem",
        paddingTop: "0"
    }
}));

function getIconFromName(name) {
    switch (name) {
        case "Computer":
            return <ComputerIcon />;
        case "USB":
            return < UsbIcon/>;
        case "Laptop":
            return <DevicesIcon />;
        default:
            return <></>;
    }
}

const Item = ({ name, price, addItemToShoppingCart }) => {
    const classes = useStyles();

    return (
        <Card className={classes.root}>
            <CardContent>
                <div className={classes.icon}>{getIconFromName(name)}</div>
                <Typography className={classes.productName} variant="h5">
                    {name}
                </Typography>
            </CardContent>
            <CardActions className={classes.actions}>
                <Button
                    onClick={addItemToShoppingCart}
                    variant="contained"
                    color="primary"
                    size="small"
                >
                    Apply
                </Button>
            </CardActions>
        </Card>
    );
};

export default Item;
