import React, { useState } from "react";
import '../App.css';
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";

const useStyles = makeStyles((theme) => ({
    root: {},
    searchbar: {
        color: "#ffffff"
    }
}));

const Search = ({ handleSearchChange }) => {
    const classes = useStyles();

    return (
        <div>
            <TextField
                id="outlined-basic"
                label="Outlined"
                variant="outlined"
                className={classes.searchbar}
                onChange={handleSearchChange}
            />
        </div>
    );
};

export default Search;
