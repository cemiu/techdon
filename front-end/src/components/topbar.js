import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import {
    AppBar,
    Toolbar,
    IconButton,
    Badge,
    Typography,
    Menu,
    MenuItem
} from "@material-ui/core";
import ShoppingCartIcon from "@material-ui/icons/ShoppingCart";

const useStyles = makeStyles((theme) => ({
    appBar: {
        display: "flex",
        justifyContent: "space-between"
    },
    menu: {
        marginTop: 36
    }
}));

const Topbar = ({ count, handleClearCart }) => {
    const classes = useStyles();

    const [anchorEl, setAnchorEl] = useState(null);

    const handleOpenMenu = (e) => {
        setAnchorEl(e.currentTarget);
    };

    const onClearCart = () => {
        handleClearCart();
        setAnchorEl(null);
    };

    return (
        <React.Fragment>

                <Toolbar className={classes.appBar}>
                    <Typography component="h2">Items to Shopping Cart</Typography>
                    <IconButton aria-label="show 4 new mails" color="inherit">
                        <Badge
                            onClick={handleOpenMenu}
                            badgeContent={count}
                            color="secondary"
                        >
                            <ShoppingCartIcon />
                        </Badge>
                    </IconButton>
                </Toolbar>
            <Menu
                open={Boolean(anchorEl)}
                anchorEl={anchorEl}
                className={classes.menu}
            >
                <MenuItem onClick={onClearCart}>Clear cart</MenuItem>
            </Menu>
        </React.Fragment>
    );
};

export default Topbar;
